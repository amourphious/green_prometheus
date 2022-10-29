package org.green.modules;

import com.google.inject.*;
import com.google.inject.name.Names;
import io.prometheus.client.Gauge;
import io.swagger.client.ApiClient;
import io.swagger.client.api.CarbonAwareApi;
import org.green.scrapper.CarbonAPIScrapperProvider;
import org.green.scrapper.Scraper;

import java.util.List;

public class EmissionsModule extends AbstractModule {

    private final String endpoint;
    private final Long scrappingInterval;
    private final List<String> locations;

    public EmissionsModule(String endpoint, Long scrappingInterval, List<String> locations) {
        this.endpoint = endpoint;
        this.scrappingInterval = scrappingInterval;
        this.locations = locations;
    }

    @Override
    public void configure() {
        bind(Key.get(Gauge.class, Names.named("carbonEmissionGauge")))
                .toInstance(Gauge.build("carbonEmission", "Gauge to record carbon emissions")
                        .labelNames("location", "period")
                        .create().register());
        bind(Key.get(new TypeLiteral<List<String>>(){}, Names.named("locations"))).toInstance(locations);
        bind(Key.get(Long.class, Names.named("scrapingInterval"))).toInstance(scrappingInterval);
        bind(new TypeLiteral<List<Scraper>>(){}).toProvider(CarbonAPIScrapperProvider.class);
    }

    @Provides
    @Singleton
    private CarbonAwareApi getCarbonAwareApi() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(endpoint);
        return new CarbonAwareApi(apiClient);
    }
}
