#coding=utf-8

# Start urls.
#
urls = ['https://dixy.ru/akcii/skidki-nedeli/']
url_core = 'https://dixy.ru'


# Item attributes.
#
item = '//div[contains(@class, "elem-product ")]'
name = 'div[@class="elem-product__description"]/div[contains(@class, "product-name")]/text()'
category = 'div[@class="elem-product__description"]/div[@class="product-category"]/child::text()[2]'
img = 'div[@class="elem-product__info"]/div[@class="elem-product__image"]/img/@src'

# Pagination selector.
#
next_page = '//li[@class="next"]/a/@href'


# EOF

