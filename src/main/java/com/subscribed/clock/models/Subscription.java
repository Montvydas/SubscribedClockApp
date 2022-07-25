package com.subscribed.clock.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Calendar;

@Data
@AllArgsConstructor
public class Subscription {
    private String url;
    private Calendar date;
    private int interval;
}
