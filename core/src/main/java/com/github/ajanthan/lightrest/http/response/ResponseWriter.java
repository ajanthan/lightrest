package com.github.ajanthan.lightrest.http.response;

import com.github.ajanthan.lightrest.http.LightRestResponseContext;

/**
 * Created by ajanthan on 6/30/16.
 */
public interface ResponseWriter {
    void writeResponse(LightRestResponseContext restResponseContext);
}
