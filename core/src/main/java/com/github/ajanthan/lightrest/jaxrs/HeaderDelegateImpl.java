package com.github.ajanthan.lightrest.jaxrs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * Created by ajanthan on 6/28/16.
 */
public class HeaderDelegateImpl implements RuntimeDelegate.HeaderDelegate<MediaType> {
    @Override
    public MediaType fromString(String s) {
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        if (MediaType.APPLICATION_JSON.equals(s)) {
            mediaType = MediaType.APPLICATION_JSON_TYPE;
        } else if (MediaType.APPLICATION_OCTET_STREAM.equals(s)) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
        }
        return mediaType;
    }

    @Override
    public String toString(MediaType mediaType) {
        return mediaType.getType() + "/" + mediaType.getSubtype();
    }
}
