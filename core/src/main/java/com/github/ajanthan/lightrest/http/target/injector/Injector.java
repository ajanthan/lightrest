package com.github.ajanthan.lightrest.http.target.injector;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;

/**
 * Created by ajanthan on 6/30/16.
 */
public interface Injector {
    public Object injector(LightRestRequestContext restRequestContext);
}
