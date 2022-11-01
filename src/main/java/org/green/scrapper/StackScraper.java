package org.green.scrapper;

import io.prometheus.client.Gauge;
import org.green.stack.StackServiceClient;
import org.green.stack.Stack;

import javax.inject.Inject;
import javax.inject.Named;

public class StackScraper implements Scraper {
    private final StackServiceClient client;
    private final Gauge collector;
    private final Long scrapingInterval;

    @Inject
    public StackScraper(StackServiceClient client, @Named("stackGauge") Gauge collector,
                        @Named("stackScrappingInterval") Long scrapingInterval) {
        this.client = client;
        this.collector = collector;
        this.scrapingInterval = scrapingInterval;
    }

    @Override
    public Long getScrappingIntervalMillis() {
        return scrapingInterval;
    }

    public void scrape() {
        System.out.println("Scraping stack");
        this.client.getStackInfo().thenAcceptAsync((stacks) -> stacks.forEach(this::reportMetrics));
    }

    private void reportMetrics(Stack stack) {
        stack.getResources().forEach(r -> collector.labels(stack.getRegion(),
                stack.getId().toString(),
                r.getResourceType(), r.getName()).set(1));
    }
}
