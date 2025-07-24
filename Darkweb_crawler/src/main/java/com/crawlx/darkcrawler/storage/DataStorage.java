
// stores results in disk/db

package com.crawlx.darkcrawler.storage;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataStorage {

    private static final String OUTPUT_DIR = "data";

    static {
        // Create the data directory if it doesn't exist
        File directory = new File(OUTPUT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void savePage(String url, String content) {
        try {
            String sanitizedFileName = sanitizeFilename(url) + "_" + System.currentTimeMillis() + ".html";
            File file = new File(OUTPUT_DIR, sanitizedFileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                System.out.println("[+] Saved page to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("[-] Failed to save page for URL: " + url);
            e.printStackTrace();
        }
    }

    private static String sanitizeFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}
