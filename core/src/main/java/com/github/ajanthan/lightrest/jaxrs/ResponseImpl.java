package com.github.ajanthan.lightrest.jaxrs;

import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;

/**
 * Created by ajanthan on 6/25/16.
 */
public class ResponseImpl extends Response {
    private int status;
    private Object entity;

    public ResponseImpl(int status, Object entity) {
        this.status = status;
        this.entity = entity;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public StatusType getStatusInfo() {
        return null;
    }

    @Override
    public Object getEntity() {
        return this.entity;
    }

    @Override
    public <T> T readEntity(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T readEntity(GenericType<T> genericType) {
        return null;
    }

    @Override
    public <T> T readEntity(Class<T> aClass, Annotation[] annotations) {
        return null;
    }

    @Override
    public <T> T readEntity(GenericType<T> genericType, Annotation[] annotations) {
        return null;
    }

    @Override
    public boolean hasEntity() {
        return this.entity != null;
    }

    @Override
    public boolean bufferEntity() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public MediaType getMediaType() {
        return null;
    }

    @Override
    public Locale getLanguage() {
        return null;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public Set<String> getAllowedMethods() {
        return null;
    }

    @Override
    public Map<String, NewCookie> getCookies() {
        return null;
    }

    @Override
    public EntityTag getEntityTag() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Date getLastModified() {
        return null;
    }

    @Override
    public URI getLocation() {
        return null;
    }

    @Override
    public Set<Link> getLinks() {
        return null;
    }

    @Override
    public boolean hasLink(String s) {
        return false;
    }

    @Override
    public Link getLink(String s) {
        return null;
    }

    @Override
    public Link.Builder getLinkBuilder(String s) {
        return null;
    }

    @Override
    public MultivaluedMap<String, Object> getMetadata() {
        return null;
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        return null;
    }

    @Override
    public String getHeaderString(String s) {
        return null;
    }

    public static class ResponseBuilderImpl extends ResponseBuilder {
        private int status;
        private Object entity;

        @Override
        public javax.ws.rs.core.Response build() {
            Response response = new ResponseImpl(this.status, this.entity);

            return response;
        }

        @Override
        public ResponseBuilder clone() {
            return null;
        }

        @Override
        public ResponseBuilder status(int i) {
            this.status = i;
            return this;

        }

        @Override
        public ResponseBuilder entity(Object o) {
            this.entity = o;
            return this;
        }

        @Override
        public ResponseBuilder entity(Object o, Annotation[] annotations) {
            return null;
        }

        @Override
        public ResponseBuilder allow(String... strings) {
            return null;
        }

        @Override
        public ResponseBuilder allow(Set<String> set) {
            return null;
        }

        @Override
        public ResponseBuilder cacheControl(CacheControl cacheControl) {
            return null;
        }

        @Override
        public ResponseBuilder encoding(String s) {
            return null;
        }

        @Override
        public ResponseBuilder header(String s, Object o) {
            return null;
        }

        @Override
        public ResponseBuilder replaceAll(MultivaluedMap<String, Object> multivaluedMap) {
            return null;
        }

        @Override
        public ResponseBuilder language(String s) {
            return null;
        }

        @Override
        public ResponseBuilder language(Locale locale) {
            return null;
        }

        @Override
        public ResponseBuilder type(MediaType mediaType) {
            return null;
        }

        @Override
        public ResponseBuilder type(String s) {
            return null;
        }

        @Override
        public ResponseBuilder variant(Variant variant) {
            return null;
        }

        @Override
        public ResponseBuilder contentLocation(URI uri) {
            return null;
        }

        @Override
        public ResponseBuilder cookie(NewCookie... newCookies) {
            return null;
        }

        @Override
        public ResponseBuilder expires(Date date) {
            return null;
        }

        @Override
        public ResponseBuilder lastModified(Date date) {
            return null;
        }

        @Override
        public ResponseBuilder location(URI uri) {
            return null;
        }

        @Override
        public ResponseBuilder tag(EntityTag entityTag) {
            return null;
        }

        @Override
        public ResponseBuilder tag(String s) {
            return null;
        }

        @Override
        public ResponseBuilder variants(Variant... variants) {
            return null;
        }

        @Override
        public ResponseBuilder variants(List<Variant> list) {
            return null;
        }

        @Override
        public ResponseBuilder links(Link... links) {
            return null;
        }

        @Override
        public ResponseBuilder link(URI uri, String s) {
            return null;
        }

        @Override
        public ResponseBuilder link(String s, String s1) {
            return null;
        }
    }
}
