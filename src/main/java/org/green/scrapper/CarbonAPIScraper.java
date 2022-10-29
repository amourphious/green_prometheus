package org.green.scrapper;

import io.prometheus.client.Gauge;
import io.swagger.client.ApiException;
import io.swagger.client.api.CarbonAwareApi;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;


import static org.threeten.bp.temporal.ChronoUnit.MINUTES;

public class CarbonAPIScraper implements Scraper {

    private final CarbonAwareApi carbonAwareApi;
    private final String location;

    private final Gauge collector;
    private final Long scrappingInterval;

    public CarbonAPIScraper(CarbonAwareApi carbonAwareApi, String location, Gauge collector, Long scrappingInterval) {
        this.carbonAwareApi = carbonAwareApi;
        this.location = location;
        this.collector = collector;
        this.scrappingInterval = scrappingInterval;
    }

    @Override
    public Long getScrappingIntervalMillis() {
        return scrappingInterval;
    }

    public void scrape() {
        try {
            System.out.println("Scrapping carbon emissions");
            carbonAwareApi.getEmissionsDataForLocationByTime(location,
                    OffsetDateTime.ofInstant(Instant.now().minus(5, MINUTES), ZoneId.of("Z")),
                    OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("Z")))
                    .forEach(ed -> {
                        System.out.printf("Reporting metrics for: %s: %s%n", location, ed.toString());
                        collector.labels(location, ed.getDuration()).set(ed.getRating());
                    });
        } catch (ApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
