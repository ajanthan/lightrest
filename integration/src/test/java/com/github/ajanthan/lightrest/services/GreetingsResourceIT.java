package com.github.ajanthan.lightrest.services;

import com.github.ajanthan.lightrest.base.RESTTestCase;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ajanthan on 6/25/16.
 */
public class GreetingsResourceIT extends RESTTestCase {
    @Test
    public void sayHello() throws Exception {

        RestAssured.config = RestAssuredConfig.newConfig().httpClient(HttpClientConfig.httpClientConfig());
        try {
            expect().
                    body("greeting", equalTo("ajanthan")).
                    statusCode(Response.Status.ACCEPTED.getStatusCode()).
                    when().
                    get("/api/test/ajanthan");
        } finally {
            RestAssured.reset();
        }
    }

}