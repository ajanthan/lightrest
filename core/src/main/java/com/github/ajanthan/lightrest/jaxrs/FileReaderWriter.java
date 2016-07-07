package com.github.ajanthan.lightrest.jaxrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by ajanthan on 6/28/16.
 */
@Consumes(MediaType.APPLICATION_OCTET_STREAM)
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class FileReaderWriter implements MessageBodyWriter<InputStream>, MessageBodyReader<InputStream> {
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return InputStream.class.isAssignableFrom(aClass) & mediaType.equals(MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    @Override
    public InputStream readFrom(Class<InputStream> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return inputStream;
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return InputStream.class.isAssignableFrom(aClass) & mediaType.equals(MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    @Override
    public long getSize(InputStream inputStream, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(InputStream inputStream, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        BufferedOutputStream tempStream = new BufferedOutputStream(outputStream);
        byte[] data = new byte[0];
        System.out.println("Writing to out stream.........");
        while (inputStream.read(data) != -1) {

            tempStream.write(data);

        }

    }


}

