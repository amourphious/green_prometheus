package org.green;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import io.vertx.core.Vertx;
import org.green.metrics.PrometheusVerticle;
import org.green.modules.EmissionsModule;
import org.green.modules.StackModule;
import org.green.scrapper.ScrapeManager;
import org.green.scrapper.Scraper;
import org.green.scrapper.StackScraper;

import java.util.List;

import static io.vertx.core.Vertx.vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = vertx();
        EmissionsModule emissionsModule = new EmissionsModule("https://carbon-aware-api.azurewebsites.net",
                300_000L, ImmutableList.of("eastus", "westus"));
        StackModule stackModule = new StackModule("http://localhost:8992", 300_000L);

        Injector injector = Guice.createInjector(emissionsModule, stackModule);

        ImmutableList.Builder<Scraper> scrappers = ImmutableList.builder();
        scrappers.addAll(injector.getProvider(Key.get(new TypeLiteral<List<Scraper>>(){})).get());
        scrappers.add(injector.getInstance(StackScraper.class));

        vertx.deployVerticle(new PrometheusVerticle()).andThen((s) -> {
            if(!s.failed()) {
                System.out.println("server stated successfully on: 2112");
                new ScrapeManager(scrappers.build(), vertx).start();
            }
            else {
                System.out.println("Unable to start server will exit...");
                System.exit(1);
            }
        });
    }
}