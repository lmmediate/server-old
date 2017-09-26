#coding=utf-8

# Start urls.
#
urls = ['https://dixy.ru/akcii/skidki-nedeli/']
url_core = 'https://dixy.ru'


# Item attributes.
#
item = '//div[contains(@class, "elem-product ")]'
name = 'div[@class="elem-product__description"]/div[contains(@class, "product-name")]/text()'
category = '//div[@class="product-category"]/child::text()[2]'
img = 'div[@class="elem-product__info"]/div[@class="elem-product__image"]/img/@src'
new_price_left = 'div[@class="elem-product__info"]/div[@class="elem-product__price-container"]/div[@class="elem-product__prices"]/div[@class="price-left"]/span/text()'
new_price_right = 'div[@class="elem-product__info"]/div[@class="elem-product__price-container"]/div[@class="elem-product__prices"]/div[@class="price-right"]/span/text()'

# Pagination selector.
#
next_page = '//li[@class="next"]/a/@href'


# EOF

