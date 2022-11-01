package org.green.scrapper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.prometheus.client.Gauge;
import io.swagger.client.api.CarbonAwareApi;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CarbonAPIScrapperProvider implements Provider<List<Scraper>> {

    private final List<String> locations;
    private final CarbonAwareApi carbonAwareApi;
    private final Gauge collector;
    

    private final Long scrapingInterval;

    @Inject
    public CarbonAPIScrapperProvider(@Named("locations") List<String> locations,
                                     CarbonAwareApi carbonAwareApi,
                                     @Named("carbonEmissionGauge") Gauge collector,
                                     @Named("scrapingInterval") Long scrapingInterval) {
        this.locations = locations;
        this.carbonAwareApi = carbonAwareApi;
        this.collector = collector;
        this.scrapingInterval = scrapingInterval;
    }

    @Override
    public List<Scraper> get() {
        return locations.stream().map(l -> new CarbonAPIScraper(carbonAwareApi, l, collector, scrapingInterval))
        		.collect(Collectors.toList());
    }
}
