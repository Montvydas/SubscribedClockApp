package com.subscribed.clock.controllers;

import com.subscribed.clock.models.RegisterRequest;
import com.subscribed.clock.services.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        log.info("POST /register with URL: {}, Interval: {}", request.getUrl(), request.getInterval());
        subscriptionService.register(request.getUrl(), request.getInterval());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deregister", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deregister(@RequestParam(name = "url") String url) {
        log.info("DELETE /deregister with URL: {}", url);
        subscriptionService.deregister(url);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/interval", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> setInterval(@RequestBody RegisterRequest request) {
        log.info("PUT /interval with URL: {}, Interval: {}", request.getUrl(), request.getInterval());
        subscriptionService.setInterval(request.getUrl(), request.getInterval());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


