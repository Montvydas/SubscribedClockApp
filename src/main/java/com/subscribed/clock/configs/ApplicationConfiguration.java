package com.subscribed.clock.configs;

import com.subscribed.clock.notifications.Notifier;
import com.subscribed.clock.clients.HttpClient;
import com.subscribed.clock.services.NotifyPoller;
import com.subscribed.clock.services.SubscriptionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Notifier notifier(HttpClient httpClient) {
        return new Notifier(httpClient);
    }

    @Bean
    public SubscriptionService subscriptionService(Notifier notifier) {
        return new SubscriptionService(notifier);
    }

    @Bean
    public NotifyPoller notifyTimer(int pollingInterval, Timer timer, Notifier notifier) {
        return new NotifyPoller(pollingInterval, timer, notifier);
    }

    @Bean
    public int pollingInterval() {
        return 1;
    }

    @Bean
    public Timer timer() {
        return new Timer();
    }
}
