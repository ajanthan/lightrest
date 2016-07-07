package com.github.ajanthan.lightrest.services.gteetings.model;

/**
 * Created by ajanthan on 6/25/16.
 */
public class Greetings {
    private String greeting;

    public Greetings(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
