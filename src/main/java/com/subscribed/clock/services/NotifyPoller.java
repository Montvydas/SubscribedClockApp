package com.subscribed.clock.services;

import com.subscribed.clock.notifications.Notifier;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class NotifyPoller {
    private final Notifier notifier;

    public NotifyPoller(int intervalSeconds, Timer timer, Notifier notifier) {
        this.notifier = notifier;
        timer.scheduleAtFixedRate(new NotifyTask(), 0, intervalSeconds * 1000L);
    }

    private class NotifyTask extends TimerTask {
        public void run() {
            notifier.notifyListeners();
        }
    }
}
