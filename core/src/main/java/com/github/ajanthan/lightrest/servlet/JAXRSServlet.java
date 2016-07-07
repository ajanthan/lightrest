package com.github.ajanthan.lightrest.servlet;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.LightRestResponseContext;
import com.github.ajanthan.lightrest.http.request.RequestProcessor;
import com.github.ajanthan.lightrest.http.target.registry.TargetRegistry;
import com.github.ajanthan.lightrest.servlet.config.ConfigurationContext;
import com.github.ajanthan.lightrest.servlet.response.DefaultResponseWriter;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ajanthan on 6/21/16.
 */
@WebServlet("/api/*")
public class JAXRSServlet extends HttpServlet {
    private static final Logger log = getLogger(JAXRSServlet.class);

    private TargetRegistry targetRegistry;

    public JAXRSServlet() {

    }

    @Override
    public void init() throws ServletException {
        super.init();
        ConfigurationContext configurationContext = ((ConfigurationContext) getServletContext().getAttribute("context"));
        targetRegistry = configurationContext.getTargetRegistry();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getRequestURI();
        String httpMethod = req.getMethod();
        String requestedResource = requestPath.split(req.getContextPath() + req.getServletPath())[1];


        LightRestRequestContext restRequestContext = LightRestRequestContext.getBuilder()
                .withPath(requestedResource)
                .withHttpMethod(httpMethod)
                .withInputStream(req.getInputStream())
                .build();
        LightRestResponseContext restResponseContext = new LightRestResponseContext();
        RequestProcessor requestProcessor = new RequestProcessor(targetRegistry, new DefaultResponseWriter(resp));
        requestProcessor.process(restRequestContext, restResponseContext);
        log.debug("Successfully processed the request");


    }


}
