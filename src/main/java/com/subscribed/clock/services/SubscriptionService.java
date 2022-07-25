package com.subscribed.clock.services;

import com.subscribed.clock.exceptions.InvalidIntervalException;
import com.subscribed.clock.exceptions.InvalidUrlException;
import com.subscribed.clock.notifications.Notifier;
import com.subscribed.clock.exceptions.DuplicateEntryException;
import com.subscribed.clock.exceptions.UrlNotFoundException;
import com.subscribed.clock.validators.IntervalValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubscriptionService {
    private final Notifier notifier;

    public SubscriptionService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void register(String url, int interval) {
        if (!IntervalValidator.getInstance().isValid(interval)) {
            log.error("Register failed, Interval: {} is outside the limits of {}", interval,
                    IntervalValidator.limits());
            throw new InvalidIntervalException(String.format(
                    "Register failed, Interval: %d is outside the limits of %s.", interval,
                    IntervalValidator.limits()));
        }

        url = url.toLowerCase();
        if (!UrlValidator.getInstance().isValid(url)) {
            log.error("Register failed, URL: {} is not a valid URL.", url);
            throw new InvalidUrlException(String.format("Register failed, URL: %s  is not a valid URL.", url));
        }

        if (notifier.getSubscription(url).isPresent()) {
            log.error("Register failed, URL: {} already exists.", url);
            throw new DuplicateEntryException(String.format("Register failed, URL: %s already exists.", url));
        }
        notifier.addSubscription(url, interval);
    }

    public void deregister(String url) {
        if (notifier.getSubscription(url).isEmpty()) {
            log.error("Deregister failed, URL: {} not found", url);
            throw new UrlNotFoundException(String.format("Deregister failed, URL: %s not found", url));
        }

        notifier.removeSubscription(url);
    }

    public void setInterval(String url, int interval) {
        if (!IntervalValidator.getInstance().isValid(interval)) {
            log.error("Setting interval failed, Interval: {} is outside the limits of {}", interval,
                    IntervalValidator.limits());
            throw new InvalidIntervalException(String.format(
                    "Setting interval failed, Interval: %d is outside the limits of %s.", interval,
                    IntervalValidator.limits()));
        }

        url = url.toLowerCase();
        if (notifier.getSubscription(url).isEmpty()) {
            log.error("Setting interval failed, URL: {} not found", url);
            throw new UrlNotFoundException(String.format("Setting interval failed, URL: %s not found", url));
        }
        notifier.addSubscription(url, interval);
    }
}
