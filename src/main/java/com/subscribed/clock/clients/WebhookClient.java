package com.subscribed.clock.clients;

import com.google.gson.Gson;
import com.subscribed.clock.models.TimeResponse;
import com.subscribed.clock.utils.Jwt;
import com.svix.Svix;
import com.svix.exceptions.ApiException;
import com.svix.models.MessageIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@Slf4j
@Service
public class WebhookClient {
    public void notifyClient() {
        var gson = new Gson();
        String now = Calendar.getInstance().getTime().toString();
        var response = new TimeResponse(now);

        Svix svix = new Svix("AUTH_TOKEN");
        try {
            svix.getMessage().create("app_Xzx8bQeOB1D1XEYmAJaRGoj0",
                    new MessageIn()
                            .eventType("notification.sent")
                            .eventId("evt_Wqb1k73rXprtTm7Qdlr38G")
                            .payload(gson.toJson(response)));
        } catch (ApiException e) {
            log.error("Webhook failed sending time: {} API error.", now);
            e.printStackTrace();
        }
    }
}