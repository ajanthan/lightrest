package com.github.ajanthan.lightrest.http.target.injector;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;

/**
 * Created by ajanthan on 6/30/16.
 */
public class QueryParamInjector implements Injector {
    private String paramName;

    public QueryParamInjector(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public Object injector(LightRestRequestContext restRequestContext) {
        return restRequestContext.getQueryParameters().get(paramName);
    }
}
