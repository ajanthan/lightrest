package com.github.ajanthan.lightrest.http.providers;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by ajanthan on 6/30/16.
 */
public class ProviderFactory {
    private static ProviderFactory instance = new ProviderFactory();
    private List<MessageBodyReader> messageBodyReaderList;
    private List<MessageBodyWriter> messageBodyWriterList;

    private ProviderFactory() {
        messageBodyReaderList = new ArrayList<>();
        messageBodyWriterList = new ArrayList<>();
        ServiceLoader<MessageBodyReader> messageBodyReaderServiceLoader = ServiceLoader.load(MessageBodyReader.class);
        ServiceLoader<MessageBodyWriter> messageBodyWritersServiceLoader = ServiceLoader.load(MessageBodyWriter.class);

        for (MessageBodyWriter messageBodyWriter : messageBodyWritersServiceLoader) {
            messageBodyWriterList.add(messageBodyWriter);
        }
        for (MessageBodyReader messageBodyReader : messageBodyReaderServiceLoader) {
            messageBodyReaderList.add(messageBodyReader);
        }
    }

    public MessageBodyReader getMessageBodyReader(Class<?> classType, Type type, Annotation[] annotations, MediaType mediaType) {
        for (MessageBodyReader reader : messageBodyReaderList) {
            if (reader.isReadable(classType, type, annotations, mediaType)) {
                return reader;
            }
        }
        return null;
    }

    public MessageBodyWriter getMessageBodyWriter(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        for (MessageBodyWriter writer : messageBodyWriterList) {
            if (writer.isWriteable(aClass, type, annotations, mediaType)) {
                return writer;
            }
        }
        return null;
    }

    public static ProviderFactory getIntance() {
        return instance;
    }
}
