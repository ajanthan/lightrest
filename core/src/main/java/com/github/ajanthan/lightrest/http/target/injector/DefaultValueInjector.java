package com.github.ajanthan.lightrest.http.target.injector;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajanthan on 6/30/16.
 */
public class DefaultValueInjector implements Injector {
    private Class type;

    public DefaultValueInjector(Class type) {
        this.type = type;
    }

    @Override
    public Object injector(LightRestRequestContext restRequestContext) {

        try {
            return type.isPrimitive() ? DEFAULT_VALUES.get(type) : type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Map<Class, Object> DEFAULT_VALUES = Collections.unmodifiableMap(new HashMap<Class, Object>() {
        // Default primitive values
        private boolean b;
        private byte by;
        private char c;
        private double d;
        private float f;
        private int i;
        private long l;
        private short s;

        {
            for (final Field field : getClass().getDeclaredFields()) {
                try {
                    put(field.getType(), field.get(this));
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    });


}
