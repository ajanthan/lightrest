package com.github.ajanthan.lightrest.http.target.registry;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.target.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajanthan on 6/30/16.
 */
public class TargetRegistry {
    private static final Logger log = LoggerFactory.getLogger(TargetRegistry.class);
    Map<URITemplateMatcher, Target> templateMatcherTargetMap = new HashMap();

    public void addTarget(Target target, URITemplateMatcher matcher) {
        templateMatcherTargetMap.put(matcher, target);
        log.debug("Added {} to Target registry", matcher);
    }

    public TargetInvoker match(LightRestRequestContext restRequestContext) {
        String httpMethod = restRequestContext.getHttpMethod();
        String context = restRequestContext.getPath();
        for (URITemplateMatcher matcher : templateMatcherTargetMap.keySet()) {
            if (matcher.match(context, httpMethod)) {
                Target target = templateMatcherTargetMap.get(matcher);
                restRequestContext.setContextParameters(matcher.getContextParams());
                log.debug("Matched a target for {}", target);
                return new DefaultTargetInvoker(target);
            }
        }
        log.debug("Not Matched any target for {}:{}", context, httpMethod);
        return new NotFoundTargetInvoker();
    }


}
