package com.github.ajanthan.lightrest.http.target;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.LightRestResponseContext;
import com.github.ajanthan.lightrest.http.response.EntityContext;
import com.github.ajanthan.lightrest.http.response.LightRestServerErrorMessage;
import com.github.ajanthan.lightrest.http.target.injector.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ajanthan on 7/2/16.
 */
public class DefaultTargetInvoker implements TargetInvoker {
    private static final Logger log = LoggerFactory.getLogger(DefaultTargetInvoker.class);
    private Target target;

    public DefaultTargetInvoker(Target target) {
        this.target = target;
    }

    @Override
    public void invoke(LightRestRequestContext restRequestContext, LightRestResponseContext restResponseContext) {
        Method serviceMethod = target.getMethod();
        log.debug("Invoking default target {} invoker ", target);
        Object returnObject;
        Object serviceObject;
        Class serviceClass = serviceMethod.getDeclaringClass();
        try {
            serviceObject = serviceClass.newInstance();
        } catch (InstantiationException e) {
            String msg = String.format("Resource class %s do not have no arg constructor", serviceClass);
            log.error(msg, e);
            createErrorMessage(msg, e, restResponseContext);
            return;
        } catch (IllegalAccessException e) {
            String msg = String.format("Resource class %s do not have accessible constructor", serviceClass);
            log.error(msg, e);
            createErrorMessage(msg, e, restResponseContext);
            return;
        }
        Object[] args = new Object[target.getArgs().size()];
        int i = 0;
        for (Injector injector : target.getArgs()) {
            args[i] = injector.injector(restRequestContext);

        }
        try {
            returnObject = serviceMethod.invoke(serviceObject, args);
        } catch (IllegalAccessException e) {
            String msg = String.format("Could not access resource method %s from %s ", serviceMethod.getName(), serviceClass);
            log.error(msg, e);
            createErrorMessage(msg, e, restResponseContext);
            return;
        } catch (InvocationTargetException e) {
            String msg = String.format("Could not execute service method %s from %s", serviceMethod, serviceClass);
            log.error(msg, e);
            createErrorMessage(msg, e, restResponseContext);
            return;
        }

        if (returnObject != null) {
            EntityContext entityContext = new EntityContext();
            entityContext.setEntityClass(serviceMethod.getReturnType());
            entityContext.setEntityType(serviceMethod.getGenericReturnType());
            entityContext.setEntityObject(returnObject);

            restResponseContext.setEntityContext(entityContext);

            //resolve media type
            String availableTypes[];
            if (serviceMethod.isAnnotationPresent(Produces.class)) {
                Produces producesAnnotation = serviceMethod.getAnnotation(Produces.class);
                availableTypes = producesAnnotation.value();
                // need to check accept header


            } else if (serviceMethod.getDeclaringClass().isAnnotationPresent(Produces.class)) {
                Produces producesAnnotation = serviceMethod.getDeclaringClass().getAnnotation(Produces.class);
                availableTypes = producesAnnotation.value();
            } else {
                availableTypes = new String[]{MediaType.APPLICATION_JSON};
            }
            restResponseContext.setMediaType(MediaType.valueOf(availableTypes[0]));
        }

    }

    private void createErrorMessage(String msg, Throwable e, LightRestResponseContext restResponseContext) {
        LightRestServerErrorMessage errorMessage = new LightRestServerErrorMessage();
        errorMessage.setErrorMessage(msg);
        errorMessage.setDetailMessage(e.getMessage());
        EntityContext entityContext = new EntityContext();
        entityContext.setEntityClass(errorMessage.getClass());
        entityContext.setEntityType(errorMessage.getClass());
        entityContext.setEntityObject(errorMessage);

        restResponseContext.setEntityContext(entityContext);
        restResponseContext.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        restResponseContext.setStatus(500);
    }
}
