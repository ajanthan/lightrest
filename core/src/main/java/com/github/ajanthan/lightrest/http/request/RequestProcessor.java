package com.github.ajanthan.lightrest.http.request;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.LightRestResponseContext;
import com.github.ajanthan.lightrest.http.response.ResponseWriter;
import com.github.ajanthan.lightrest.http.target.TargetInvoker;
import com.github.ajanthan.lightrest.http.target.registry.TargetRegistry;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ajanthan on 7/2/16.
 */
public class RequestProcessor {
    private static final Logger log = getLogger(RequestProcessor.class);
    private TargetRegistry registry;
    private ResponseWriter responseWriter;

    public RequestProcessor(TargetRegistry registry, ResponseWriter responseWriter) {
        this.registry = registry;
        this.responseWriter = responseWriter;
    }

    public void process(LightRestRequestContext restRequestContext, LightRestResponseContext restResponseContext) {
        log.debug("Processing request for {}", restRequestContext);
        TargetInvoker targetInvoker = registry.match(restRequestContext);
        log.debug("Got matching target invoker");
        targetInvoker.invoke(restRequestContext, restResponseContext);
        log.debug("Invoked target");
        responseWriter.writeResponse(restResponseContext);
        log.debug("Done with the request");

    }
}
