package org.example.inventory;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class InventoryMain extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        var appArgs = Stream.concat(Stream.of("server", "/config-inventory.yml"), Arrays.stream(args))
                .toArray(String[]::new);
        new InventoryMain().run(appArgs);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        final var service = new InventoryService();
        environment.lifecycle().manage(service);
        final var resource = new InventoryResource(service);
        environment.jersey().register(resource);
        log.info("App running");
    }

}
