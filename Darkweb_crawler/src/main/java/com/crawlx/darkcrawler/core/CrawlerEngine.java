package com.crawlx.darkcrawler.crawler;

import com.crawlx.darkcrawler.tor.TorManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerEngine {

    private final TorManager torManager;
    private final Set<String> visitedLinks;
    private final Queue<String> queue;

    public CrawlerEngine() {
        this.torManager = new TorManager();
        this.visitedLinks = new HashSet<>();
        this.queue = new LinkedList<>();
    }

    public void crawl(String startUrl, int maxDepth) {
        System.out.println("[*] Starting crawl on: " + startUrl + " with depth: " + maxDepth);

        queue.add(startUrl);
        visitedLinks.add(startUrl);
        int currentDepth = 0;

        while (!queue.isEmpty() && currentDepth < maxDepth) {
            String url = queue.poll();
            System.out.println("[+] Crawling: " + url);

            try {
                String content = torManager.fetchPage(url);
                if (content != null && !content.isEmpty()) {
                    saveContent(url, content);
                    Set<String> links = extractOnionLinks(content);
                    for (String link : links) {
                        if (!visitedLinks.contains(link)) {
                            visitedLinks.add(link);
                            queue.add(link);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("[-] Failed to fetch: " + url + " - " + e.getMessage());
            }

            currentDepth++;
        }

        System.out.println("[✓] Crawl complete.");
    }

    private void saveContent(String url, String content) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdir();

            String fileName = url.replaceAll("[^a-zA-Z0-9]", "_");
            File file = new File(dir, fileName + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }

            System.out.println("[+] Saved content to: " + file.getPath());
        } catch (IOException e) {
            System.err.println("[-] Error saving content: " + e.getMessage());
        }
    }

    private Set<String> extractOnionLinks(String html) {
        Set<String> links = new HashSet<>();
        Pattern pattern = Pattern.compile("http[s]?://[a-zA-Z0-9]{16,56}\\.onion");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }
}
