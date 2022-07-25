package com.subscribed.clock.clients;

import com.google.gson.Gson;
import com.subscribed.clock.models.TimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
public class HttpClient {
    public void notifyClient(String url) {
        var now = Calendar.getInstance();
        log.info("Notifying registered URL: {} at Timestamp: {}.", url, now.getTime());

        var client = HttpClients.createDefault();
        var post = new HttpPost(url);
        var gson = new Gson();
        var response = new TimeResponse(now.getTime().toString());

        try {
            var entity = new StringEntity(gson.toJson(response));
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            client.execute(post);
            client.close();
        } catch (Exception e) {
            log.error("POST error, URL: {} is invalid or connection error.", url);
        }
    }
}
