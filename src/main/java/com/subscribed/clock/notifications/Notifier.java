package com.subscribed.clock.notifications;


import com.subscribed.clock.clients.HttpClient;
import com.subscribed.clock.models.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
public class Notifier {
    private final HttpClient client;

    public Notifier(HttpClient client) {
        this.client = client;
    }

    private static final ConcurrentHashMap<String, Subscription> activeSubscriptions = new ConcurrentHashMap<>();

    public void addSubscription(String url, int interval) {
        if (url == null) return;
        activeSubscriptions.put(url, new Subscription(url, expiresOn(interval), interval));

        // todo register a webhook service, where the client can receive notifications of updates:
        //  var app = svix.getApplication().create(new ApplicationIn().name("Subscriber Name"));
    }

    private Calendar expiresOn(int interval) {
        var now = Calendar.getInstance();
        now.add(Calendar.SECOND, interval);
        return now;
    }

    public void removeSubscription(String url) {
        // todo remove client webhook
        activeSubscriptions.remove(url);
    }

    public Optional<Subscription> getSubscription(String url) {
        return Optional.ofNullable(activeSubscriptions.get(url));
    }

    public void notifyListeners() {
        for (Subscription subscription : activeSubscriptions.values()) {
            if (!isExpired(subscription.getDate())) continue;
            new AsyncNotify().run(subscription.getUrl(), client);
            addSubscription(subscription.getUrl(), subscription.getInterval());
        }
    }

    private boolean isExpired(Calendar expires) {
        var now = Calendar.getInstance();
        return now.after(expires);
    }
}