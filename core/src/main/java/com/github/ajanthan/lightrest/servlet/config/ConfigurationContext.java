package com.github.ajanthan.lightrest.servlet.config;

import com.github.ajanthan.lightrest.http.target.registry.TargetRegistry;
import com.github.ajanthan.lightrest.http.target.registry.TargetRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by ajanthan on 6/24/16.
 */
public class ConfigurationContext {
    private static Logger log = LoggerFactory.getLogger(ConfigurationContext.class);
    private static ConfigurationContext ourInstance;

    public TargetRegistry getTargetRegistry() {
        return targetRegistry;
    }

    public void setTargetRegistry(TargetRegistry targetRegistry) {
        this.targetRegistry = targetRegistry;
    }

    private TargetRegistry targetRegistry;

    public static ConfigurationContext getInstance(Set<Class<?>> classes) {
        if (ourInstance == null) {
            ourInstance = new ConfigurationContext(classes);
        }
        return ourInstance;
    }

    private ConfigurationContext(Set<Class<?>> classes) {


        targetRegistry = TargetRegistryBuilder.buid(classes);
        log.info("Finished initialising configuration context");
    }


}

