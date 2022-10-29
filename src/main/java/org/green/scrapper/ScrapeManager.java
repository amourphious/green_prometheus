package org.green.scrapper;

import io.vertx.core.Vertx;

import javax.inject.Inject;
import java.util.List;

public class ScrapeManager {
    private final List<Scraper> scrapers;
    private final Vertx vertx;
    private final long[] timerIds;

    @Inject
    public ScrapeManager(List<Scraper> scrapers, Vertx vertx) {
        this.scrapers = scrapers;
        timerIds = new long[scrapers.size()];
        this.vertx = vertx;
    }

    public void start() {
        for(int i = 0; i < scrapers.size(); i++) {
            final Scraper s = scrapers.get(i);
            timerIds[i] = vertx.setPeriodic(s.getScrappingIntervalMillis(),
                    l -> s.scrape());
            s.scrape();
        }
    }

    public void stop() {
        for(long timerId: timerIds)
            vertx.cancelTimer(timerId);
    }
}
