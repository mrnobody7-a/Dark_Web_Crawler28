package com.crawlx.darkcrawler.core;

import com.crawlx.darkcrawler.storage.DataStorage;
import com.crawlx.darkcrawler.tor.TorManager;
import com.crawlx.darkcrawler.util.OnionLinkLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class CrawlerEngine {

    private final TorManager torManager;
    private final Set<String> visited;

    public CrawlerEngine() {
        this.torManager = new TorManager();
        this.visited = new HashSet<>();
    }

    public void crawl(String url, int depth) {
        if (depth <= 0 || visited.contains(url)) {
            return;
        }

        System.out.println("[*] Crawling: " + url);
        visited.add(url);

        try {
            String html = torManager.fetchPage(url);
            if (html != null) {
                // Save HTML page
                DataStorage.savePage(url, html);

                // Parse and extract links
                Document doc = Jsoup.parse(html, url);
                Elements links = doc.select("a[href]");

                for (Element link : links) {
                    String href = link.attr("href").trim();

                    // Resolve relative URLs to absolute
                    if (!href.startsWith("http")) {
                        href = link.attr("abs:href").trim();
                    }

                    // Filter for .onion links only
                    if (href.contains(".onion")) {
                        href = href.split("#")[0].replaceAll("/+$", ""); // remove fragments/trailing slashes

                        if (!visited.contains(href)) {
                            System.out.println("  [+] Found new .onion link: " + href);
                            OnionLinkLogger.log(href); // Save to log
                            crawl(href, depth - 1); // Recurse
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("[-] Error crawling " + url + ": " + e.getMessage());
        }
    }
}
