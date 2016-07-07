package com.github.ajanthan.lightrest.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajanthan on 6/25/16.
 */
public class RuntimeDelegateImpl extends RuntimeDelegate {
    private static final Logger log = LoggerFactory.getLogger(RuntimeDelegateImpl.class);
    private Map<Class<?>, HeaderDelegate> providers = new HashMap<>();

    public RuntimeDelegateImpl() {
        providers.put(MediaType.class, new HeaderDeligateImpl());
    }

    @Override
    public UriBuilder createUriBuilder() {
        return null;
    }

    @Override
    public Response.ResponseBuilder createResponseBuilder() {
        log.debug("Creating response builder");

        System.out.println("Creating response builder");
        return new ResponseImpl.ResponseBuilderImpl();
    }

    @Override
    public Variant.VariantListBuilder createVariantListBuilder() {
        return null;
    }

    @Override
    public <T> T createEndpoint(Application application, Class<T> aClass) throws IllegalArgumentException, UnsupportedOperationException {
        return null;
    }

    @Override
    public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> aClass) throws IllegalArgumentException {
        return providers.get(aClass);
    }


    @Override
    public Link.Builder createLinkBuilder() {
        return null;
    }
}
