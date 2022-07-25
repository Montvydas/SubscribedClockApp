package com.subscribed.clock.notifications;

import com.subscribed.clock.clients.HttpClient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncNotify {
    private final ArrayBlockingQueue<Runnable> boundedQueue = new ArrayBlockingQueue<Runnable>(1000);
    private final ExecutorService executor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, boundedQueue, new ThreadPoolExecutor.AbortPolicy());

    public void run(String url, HttpClient client) {
        executor.submit(() -> {
            client.notifyClient(url);
        });
    }
}
