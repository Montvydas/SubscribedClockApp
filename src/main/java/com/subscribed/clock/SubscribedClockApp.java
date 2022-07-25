package com.subscribed.clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
public class SubscribedClockApp {
    private static final Optional<String> host;
    private static final Optional<String> port;

    static {
        host = Optional.ofNullable(System.getenv("HOSTNAME"));
        port = Optional.ofNullable(System.getenv("PORT"));
    }

    public static void main(String[] args) {
        var appProps = new Properties();
        appProps.setProperty("server.address", host.orElse("localhost"));
        appProps.setProperty("server.port", port.orElse("8080"));

        SpringApplication app = new SpringApplication(SubscribedClockApp.class);
        app.setDefaultProperties(appProps);
        app.run(args);
    }
}
