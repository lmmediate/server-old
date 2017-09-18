#!/usr/bin/env python

import requests
from bs4 import BeautifulSoup
import re
import json
import codecs
import time
import smtplib

def log(func):
    def log_and_call(*args, **kwargs):
        print('% {func} {args} {kwargs}'.format(**locals()))
        return func(*args, **kwargs)
    return log_and_call

class Item:
    def __init__(self, category, img_url, old_price, new_price, name, discount, date, condition):
        self.old_price = old_price
        self.new_price = new_price
        self.category = category
        self.date = date
        self.name = name
        self.discount = discount
        self.img_url = img_url
        self.condition = condition


items = []
url_core = 'https://dixy.ru/akcii/skidki-nedeli/'

@log
def get_max_pages(url):
    source_code = requests.get(url)
    source_code.encoding = 'utf-8'
    soup = BeautifulSoup(source_code.text, 'html.parser')
    res = 1
    for link in soup.find_all('ul', class_='page-pagination-list'):
        return int(link.contents[-4].text)


max_pages = get_max_pages(url_core)

@log
def get_max_items(max_pages):
    current_page = 1
    count = 0
    while current_page <= max_pages:
        count += len(BeautifulSoup(
            requests.get(url_core + '?PAGEN_1=' +
                         str(current_page)).text, 'html.parser'
        ).find_all('div', class_='elem-product'))
        current_page += 1
    return count


# Important to execute first.
#
@log
def init_items_categs(max_pages):
    current_page = 1
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        for link in soup.find_all('div', class_='elem-product__description'):
            items.append(Item(link.contents[1].text.strip(), '', '', '', '', '', '', ''))
        current_page += 1

@log
def init_items_imgs(max_pages):
    current_page = 1
    img_urls = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='elem-product__image')
        for i in range(len(products)):
            img_urls.append('https://dixy.ru' + products[i].contents[1].get('src'))
        current_page += 1
    for i in range(len(img_urls)):
        items[i].img_url = img_urls[i]

@log
def init_items_old_price(max_pages):
    current_page = 1
    old_prices = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='price-right')
        for p in products:
            try:
                price_full = p.contents[3]
                old_prices.append(price_full.contents[1].text + '.' + price_full.contents[3].text[:-1])
            except:
                old_prices.append('NO_OLD_PRICE_INFO')
        current_page += 1
    for i in range(len(old_prices)):
        items[i].old_price = old_prices[i]

@log
def init_items_new_prices(max_pages):
    current_page = 1
    prices_rub = []
    prices_cop = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        page_code = requests.get(url)
        page_code.encoding = 'utf-8'
        soup = BeautifulSoup(page_code.text, 'html.parser')
        for link in soup.find_all('span', class_='price-discount__integer'):
            price = link.text
            prices_rub.append(price)
        for link in soup.find_all('span', class_='price-discount__float'):
            cop = link.string
            prices_cop.append(cop)
        current_page += 1
    for i in range(len(prices_rub)):
        items[i].new_price = (str(prices_rub[i]) + '.' + str(prices_cop[i]))


@log
def init_items_discounts(max_pages):
    current_page = 1
    discounts = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='elem-product__price-container')
        for p in products:
            try:
                discounts.append(p.contents[1].contents[0].text[1:] + '%')
            except:
                discounts.append('NO_DISCOUNT_INFO')
        current_page += 1
    for i in range(len(discounts)):
        items[i].discount = discounts[i]

@log
def init_items_names(max_pages):
    current_page = 1
    names = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='elem-product__description')
        for p in products:
            names.append(p.contents[3].text.strip().replace('*', u'').replace(u'\xa0', u''))
        current_page += 1
    for i in range(len(names)):
        items[i].name = names[i]

@log
def init_items_dates(max_pages):
    current_page = 1
    dates = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='elem-badge-cornered')
        for p in products:
            dates.append('/'.join(re.findall(r'\d+', p.text)))
        current_page += 1
    for i in range(len(dates)):
        items[i].date = dates[i]

@log
def init_items_conditions(max_pages):
    current_page = 1
    conditions = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, 'html.parser')
        products = soup.find_all('div', class_='elem-product__description')
        for p in products:
            try:
                if 'conditions' in str(p.contents[5]):
                    conditions.append(re.sub('\s+', ' ', p.contents[5].text.strip().replace('\r', '').replace('\n', '')))
                else:
                    raise Exception
            except:
                conditions.append('NO_CONDITION_INFO')
        current_page += 1
    for i in range(len(conditions)):
        items[i].condition = conditions[i]

@log
def start_crawling():
    # Get maximum number of pages.
    #
    global items
    items = []
    max_pages = get_max_pages(url_core)
    init_items_categs(max_pages)
    init_items_imgs(max_pages)
    init_items_old_price(max_pages)
    init_items_new_prices(max_pages)
    init_items_discounts(max_pages)
    init_items_names(max_pages)
    init_items_dates(max_pages)
    init_items_conditions(max_pages)

@log
def write_results():
    objects = [x.__dict__ for x in items]
    with open('dixy_items.json', 'w') as F:
        json.dump(objects, F)

def run_once():
    start_crawling()
    return [x.__dict__ for x in items]

start_crawling()
write_results()


# EOF

