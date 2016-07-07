package com.github.ajanthan.lightrest.http;

import com.github.ajanthan.lightrest.http.response.EntityContext;

import javax.ws.rs.core.MediaType;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by ajanthan on 6/30/16.
 */
public class LightRestResponseContext {
    private int status;
    private Map<String, String> headers;
    private OutputStream outputStream;
    private EntityContext entityContext;
    private MediaType mediaType;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public EntityContext getEntityContext() {
        return entityContext;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public static class LightRestResponseContextBuilder {
        private LightRestResponseContext restResponseContext;

        public LightRestResponseContextBuilder() {
            this.restResponseContext = new LightRestResponseContext();
        }

    }
}
