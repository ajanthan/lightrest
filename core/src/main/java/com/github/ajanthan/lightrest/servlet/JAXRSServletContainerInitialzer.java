package com.github.ajanthan.lightrest.servlet;

import com.github.ajanthan.lightrest.annotations.API;
import com.github.ajanthan.lightrest.servlet.config.ConfigurationContext;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * Created by ajanthan on 6/25/16.
 */
@HandlesTypes(API.class)
public class JAXRSServletContainerInitialzer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

        ConfigurationContext configurationContext = ConfigurationContext.getInstance(set);
        servletContext.setAttribute("context", configurationContext);
        System.out.println("Finished initialising");
    }
}
