package com.github.ajanthan.lightrest.http.target.registry;

import com.github.ajanthan.lightrest.http.target.Target;
import com.github.ajanthan.lightrest.http.target.URITemplateMatcher;
import org.slf4j.Logger;

import javax.ws.rs.*;
import java.lang.reflect.Method;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ajanthan on 6/30/16.
 */
public class TargetRegistryBuilder {
    private static final Logger log = getLogger(TargetRegistryBuilder.class);

    public static TargetRegistry buid(Set<Class<?>> classSet) {
        TargetRegistry registry = new TargetRegistry();
        for (Class cl : classSet) {
            String rootPath = getRootPath(cl);

            walkThroughMethods(registry, cl.getMethods(), rootPath);
        }
        return registry;
    }

    private static String getRootPath(Class cl) {
        Path path = (Path) cl.getAnnotation(Path.class);
        if (path != null) {
            return path.value();
        } else {
            return "/";
        }

    }

    private static void walkThroughMethods(TargetRegistry registry, Method[] methods, String rootPath) {
        String httpMethod;
        String context;
        for (Method method : methods) {
            httpMethod = null;
            GET get = method.getAnnotation(GET.class);
            if (get != null) {
                httpMethod = "GET";
            }
            POST post = method.getAnnotation(POST.class);
            if (post != null) {
                httpMethod = "POST";
            }
            PUT put = method.getAnnotation(PUT.class);
            if (put != null) {
                httpMethod = "PUT";
            }
            DELETE delete = method.getAnnotation(DELETE.class);
            if (delete != null) {
                httpMethod = "DELETE";
            }
            if (httpMethod == null) {
                log.debug("Method {} is not a REST resource ", method.getName());
                continue;
            }
            Path path = method.getAnnotation(Path.class);
            if (path != null) {
                context = rootPath + path.value();
            } else {

                context = rootPath;
            }
            log.debug("Adding Method {}  as a REST resource ", method.getName());
            registry.addTarget(new Target(method), new URITemplateMatcher(context, httpMethod));
        }
    }
}
