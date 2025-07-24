package com.crawlx.darkcrawler.tor;

import com.crawlx.darkcrawler.storage.DataStorage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * TorManager handles HTTP requests over the Tor network
 * by configuring a SOCKS5 proxy on localhost (127.0.0.1:9050).
 */
public class TorManager {

    private final OkHttpClient client;

    public TorManager() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050));
        client = new OkHttpClient.Builder()
                .proxy(proxy)
                .build();
    }

    public String fetchPage(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "DarkCrawlerBot/1.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                System.err.println("Failed to fetch " + url + ": " + response.code());
                return null;
            }

            String htmlContent = response.body().string();

            // ✅ Save the page using the storage module
            DataStorage.savePage(url, htmlContent);

            return htmlContent;
        }
    }
}

