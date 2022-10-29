package org.green.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.prometheus.client.Gauge;

import java.net.URI;
import java.net.http.HttpClient;

public class StackModule extends AbstractModule {

    private final String stackServiceEndpoint;
    private final Long stackScrappingInterval;

    public StackModule(String stackServiceEndpoint, Long stackScrappingInterval) {
        this.stackServiceEndpoint = stackServiceEndpoint;
        this.stackScrappingInterval = stackScrappingInterval;
    }

    @Override
    public void configure() {
        bind(URI.class).annotatedWith(Names.named("stackServiceEndpoint")).toInstance(URI.create(stackServiceEndpoint));
        bind(HttpClient.class).toInstance(HttpClient.newBuilder().build());
        bind(ObjectMapper.class).toInstance(new ObjectMapper());
        bind(Gauge.class).annotatedWith(Names.named("stackGauge"))
                .toInstance(Gauge.build("stackInfo", "Tells if the stack is active(1)/inActive(0)")
                .labelNames("region", "s_id", "type", "name")
                .create()
                .register());
        bind(Long.class).annotatedWith(Names.named("stackScrappingInterval")).toInstance(stackScrappingInterval);

    }
}
