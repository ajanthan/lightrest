package com.github.ajanthan.lightrest.http.target;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.target.injector.*;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ajanthan on 6/30/16.
 */
public class Target {
    private static final Logger log = getLogger(Target.class);
    private Method method;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<Injector> getArgs() {
        return args;
    }

    public void setArgs(List<Injector> args) {
        this.args = args;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Target(Method method) {
        log.debug("Processing java method {}", method.getName());
        this.method = method;
        int i = 0;
        boolean resolvedCurrent = false;
        boolean bodyConsumed = false;
        for (Class paramClass : method.getParameterTypes()) {
            for (Annotation paramAnnotation : method.getParameterAnnotations()[i++]) {
                if (paramAnnotation instanceof PathParam) {
                    PathParam pathParam = (PathParam) paramAnnotation;
                    args.add(new PathParamInjector(pathParam.value(), paramClass));
                    resolvedCurrent = true;
                    break;
                } else if (paramAnnotation instanceof QueryParam) {
                    QueryParam queryParam = (QueryParam) paramAnnotation;
                    args.add(new QueryParamInjector(queryParam.value()));
                    resolvedCurrent = true;
                    break;
                }
            }
            if (!resolvedCurrent) {
                if (!bodyConsumed) {
                    Consumes consumesAnotatiomFromMethod = method.getAnnotation(Consumes.class);
                    String[] consumesFromMethod = new String[0];
                    if (consumesAnotatiomFromMethod != null) {
                        consumesFromMethod = consumesAnotatiomFromMethod.value();
                    }


                    Consumes consumesAnotatiomFromClass = method.getDeclaringClass().getAnnotation(Consumes.class);
                    String[] consumesFromClass = new String[0];
                    if (consumesAnotatiomFromClass != null) {
                        consumesFromClass = consumesAnotatiomFromClass.value();
                    }


                    MediaType[] mergedConsumes = new MediaType[consumesFromClass.length + consumesFromMethod.length];

                    int j = 0;

                    for (String consume : consumesFromClass) {
                        mergedConsumes[j++] = MediaType.valueOf(consume);

                    }
                    for (String consume : consumesFromMethod) {
                        mergedConsumes[j++] = MediaType.valueOf(consume);

                    }

                    args.add(new BodyParamInjector(paramClass, mergedConsumes));
                    bodyConsumed = true;
                } else {
                    args.add(new DefaultValueInjector(paramClass));
                }
            }
        }
        log.debug("Processed {} of parameters for the method", i);
    }

    private List<Injector> args = new LinkedList<>();
    private Object returnObject;


    public void resolveParameters(LightRestRequestContext context) {
        //
    }

    @Override
    public String toString() {
        return "Target{" +
                "method=" + method.getName() +
                '}';
    }
}
