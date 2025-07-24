package com.crawlx.darkcrawler.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OnionLinkLogger {
    private static final String FILE_PATH = "onion_links.txt";
    private static final Set<String> logged = new HashSet<>();

    public static synchronized void log(String link) {
        if (logged.contains(link)) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(link);
            writer.newLine();
            logged.add(link);
        } catch (IOException e) {
            System.err.println("[-] Failed to log onion link: " + e.getMessage());
        }
    }
}
