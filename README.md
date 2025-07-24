
# 🌐 DarkCrawler - Tor Hidden Service Crawler

DarkCrawler is a powerful and modular Java-based dark web crawler that uses the Tor network to fetch and recursively explore `.onion` sites. 
It supports multithreaded crawling, seed URL input, and HTML/data saving capabilities — perfect for dark web researchers and cyber threat intelligence professionals.

---

## 📖 About This Project

This project is built for educational and research purposes to explore and understand how hidden services operate on the Tor network. 
It demonstrates how `.onion` websites can be programmatically discovered, parsed, and stored using Java, OkHttp, and Tor SOCKS5 proxies.

DarkCrawler allows you to:

- Study the structure of hidden websites
- Build a database of `.onion` URLs
- Extend crawling behavior for threat intelligence, search indexing, or malware hunting (with caution and legality in mind)

---

## 🚀 Features

- ✅ Tor-enabled crawling using SOCKS5 (via `localhost:9050`)
- ✅ Reads seed URLs from a `.txt` file (`onion_seeds.txt`)
- ✅ Saves crawled HTML content locally
- ✅ Logs unique `.onion` URLs to a central file
- ✅ Modular structure (TorManager, CrawlerEngine, DataStorage, etc.)
- ✅ Multithreaded crawling (coming soon)

---



## 📂 Project Structure

```
project/
├── data/                      # Saved HTML pages
├── onion\_seeds.txt            # Input file with seed .onion URLs
└── src/main/java/com/crawlx/darkcrawler/
├── core/                  # CrawlerEngine.java
├── engine/                # CrawlLauncher.java (entry point)
├── storage/               # DataStorage.java
├── util/                  # OnionLinkLogger.java
└── tor/                   # TorManager.java
```

---

## ⚙️ Requirements

- **Java 11+**
- **Maven**
- **Tor service running** on `127.0.0.1:9050` (default port)

---

## 🛠️ Build and Run

### 1. Clone the repo

```bash
git clone https://github.com/yourusername/darkcrawler.git
cd darkcrawler
````

### 2. Add `.onion` URLs

Add one `.onion` URL per line in `onion_seeds.txt`:

```text
http://example1.onion
http://example2.onion
```

### 3. Start Tor (on Kali/Linux)

```bash
sudo systemctl start tor
```

### 4. Run the crawler

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.crawlx.darkcrawler.engine.CrawlLauncher"
```

---

## 📌 Notes

* Ensure Tor is running and listening on port 9050.
* Some `.onion` sites may be slow or unresponsive — handle timeouts gracefully.
* Avoid crawling illegal content. This tool is for **educational and security research only**.


