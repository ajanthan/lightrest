package com.github.ajanthan.lightrest.http;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by ajanthan on 6/30/16.
 */
public class LightRestRequestContext {
    private String path;
    private InputStream inputStream;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;

    public Map<String, String> getContextParameters() {
        return contextParameters;
    }

    public void setContextParameters(Map<String, String> contextParameters) {
        this.contextParameters = contextParameters;
    }

    private Map<String, String> contextParameters;
    private String httpMethod;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static LightRestRequestContextBuilder getBuilder() {
        return new LightRestRequestContextBuilder();
    }

    public static class LightRestRequestContextBuilder {
        private LightRestRequestContext restRequestContext;

        public LightRestRequestContextBuilder() {
            this.restRequestContext = new LightRestRequestContext();
        }

        public LightRestRequestContextBuilder withPath(String path) {
            this.restRequestContext.setPath(path);
            return this;
        }

        public LightRestRequestContextBuilder withInputStream(InputStream inputStream) {
            this.restRequestContext.setInputStream(inputStream);
            return this;
        }

        public LightRestRequestContextBuilder withHeaders(Map<String, String> headers) {
            this.restRequestContext.setHeaders(headers);
            return this;
        }

        public LightRestRequestContextBuilder withHttpMethod(String httpMethod) {
            this.restRequestContext.setHttpMethod(httpMethod);
            return this;
        }

        public LightRestRequestContextBuilder withQueryParameters(Map<String, String> queryParameters) {
            this.restRequestContext.setQueryParameters(queryParameters);
            return this;
        }

        public LightRestRequestContext build() {
            return this.restRequestContext;
        }
    }

    @Override
    public String toString() {
        return "LightRestRequestContext{" +
                "path='" + path + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
