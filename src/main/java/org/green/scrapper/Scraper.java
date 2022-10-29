package org.green.scrapper;

public interface Scraper {
    Long getScrappingIntervalMillis();
    void scrape();
}
