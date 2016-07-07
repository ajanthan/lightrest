package com.github.ajanthan.lightrest.http.target.injector;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.providers.ProviderFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;

/**
 * Created by ajanthan on 6/30/16.
 */
public class BodyParamInjector implements Injector {
    private Class type;
    private MediaType[] supportedMediatypes;
    private ProviderFactory providerFactory;

    public BodyParamInjector(Class type, MediaType[] mergedConsumes) {
        this.type = type;
        this.providerFactory = ProviderFactory.getIntance();
        this.supportedMediatypes = mergedConsumes;
    }

    @Override
    public Object injector(LightRestRequestContext restRequestContext) {
        MediaType mediaType = supportedMediatypes[0];
        MessageBodyReader reader = providerFactory.getMessageBodyReader(type, type, null, mediaType);
        Object paramterObject = null;
        try {
            paramterObject = reader.readFrom(type, type, null, mediaType, null, restRequestContext.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paramterObject;

    }
}
