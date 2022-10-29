package org.green.metrics;

import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class PrometheusVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        //Bind metrics handler to /metrics
        Router router = Router.router(vertx);
        router.get("/metrics").handler(new MetricsHandler());

        //Start httpserver on localhost:9000
        vertx.createHttpServer().requestHandler(router).listen(2112);
    }
}
