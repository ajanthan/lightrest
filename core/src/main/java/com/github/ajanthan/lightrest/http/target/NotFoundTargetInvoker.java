package com.github.ajanthan.lightrest.http.target;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.LightRestResponseContext;
import com.github.ajanthan.lightrest.http.response.EntityContext;
import com.github.ajanthan.lightrest.http.response.LightRestServerErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * Created by ajanthan on 7/2/16.
 */
public class NotFoundTargetInvoker implements TargetInvoker {
    private static final Logger log = LoggerFactory.getLogger(NotFoundTargetInvoker.class);

    @Override
    public void invoke(LightRestRequestContext restRequestContext, LightRestResponseContext restResponseContext) {
        restResponseContext.setStatus(404);
        EntityContext entityContext = new EntityContext();
        LightRestServerErrorMessage errorMessage = new LightRestServerErrorMessage();
        errorMessage.setErrorMessage("Requested resource not found");
        errorMessage.setDetailMessage(String.format("Resource %s not found", restRequestContext.getPath()));
        entityContext.setEntityClass(errorMessage.getClass());
        entityContext.setEntityType(errorMessage.getClass());
        entityContext.setEntityObject(errorMessage);
        restResponseContext.setEntityContext(entityContext);
        restResponseContext.setMediaType(MediaType.APPLICATION_JSON_TYPE);

        log.debug("Invoked NotFoundTargetInvoker for request {}", restRequestContext.getPath());
    }
}
