package com.crawlx.darkcrawler.engine;

import com.crawlx.darkcrawler.core.CrawlerEngine;

public class CrawlLauncher {
    public static void main(String[] args) {
        String startUrl = "http://hg36q46xemkwvqcod2pfcuwh24aqcpgcbwqibyblu3nt7l562zahatid.onion";
        int depth = 5; // Or more

        CrawlerEngine crawler = new CrawlerEngine();
        crawler.crawl(startUrl, depth);
    }
}
