#coding=utf-8

import scrapy
import sys


# TODO: Fix relative paths.
#
sys.path.insert(0, './src/selectors/')
sys.path.insert(0, './src/processors/')
import dixy_selectors as sel
import text_processor as proc


class QuotesSpider(scrapy.Spider):
    name = 'dixy'
    start_urls = sel.urls

    def concat_prices(self, left, right):
        return float(str(left) + '.' + str(right))

    def parse(self, response):
        for item in response.xpath(sel.item):
            yield {
                # TODO: Add other selectors.
                #
                'name': proc.process(item.xpath(sel.name).extract_first()),
                'category': proc.process(item.xpath(sel.category).extract_first()),
                'img_url': sel.url_core + item.xpath(sel.img).extract_first(),
                'new_price': self.concat_prices(proc.process(item.xpath(sel.new_price_left).extract_first(default='0')),
                    proc.process(item.xpath(sel.new_price_right).extract_first(default='0'))),
                'old_price': self.concat_prices(proc.process(item.xpath(sel.old_price_left).extract_first(default='0')), 
                    proc.process(item.xpath(sel.old_price_right).extract_first(default='0'))),
            }

        next_page = response.xpath(sel.next_page).extract_first()

        if next_page is not None:
            yield response.follow(next_page, self.parse)


# EOF

