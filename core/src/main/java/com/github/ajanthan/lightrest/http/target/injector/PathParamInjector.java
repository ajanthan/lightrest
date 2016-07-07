package com.github.ajanthan.lightrest.http.target.injector;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ajanthan on 6/30/16.
 */
public class PathParamInjector implements Injector {
    private static final Logger log = LoggerFactory.getLogger(PathParamInjector.class);
    private String paramName;
    protected Class type;

    public PathParamInjector(String paramName, Class type) {
        this.paramName = paramName;
        this.type = type;
    }


    @Override
    public Object injector(LightRestRequestContext restRequestContext) {
        String stringValue = restRequestContext.getContextParameters().get(paramName);
        Object actualValue = null;
        if (type.equals(String.class)) {
            actualValue = stringValue;
        } else {

            try {
                Method method = type.getMethod("valueOf", String.class);
                actualValue = method.invoke(null, stringValue);
            } catch (NoSuchMethodException e) {
                log.error("ValueOf method is not supported by {}," +
                        "Please use any type which support valueOf as context parameter", type);
            } catch (InvocationTargetException e) {
                log.error("Unable to invoke valueOf method on {} ", type);
            } catch (IllegalAccessException e) {
                log.error("Unable to access valueOf method of {}.It may be a private/protected method ", type);
            }
        }

        return actualValue;
    }


}
