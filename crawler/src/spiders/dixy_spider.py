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

    def parse(self, response):
        for item in response.xpath(sel.item):
            yield {
                # TODO: Add other selectors.
                #
                'name': proc.process(item.xpath(sel.name).extract_first()),
            }

        next_page = response.xpath(sel.next_page).extract_first()

        if next_page is not None:
            yield response.follow(next_page, self.parse)


# EOF

