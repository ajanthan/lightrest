package com.github.ajanthan.lightrest.http.target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ajanthan on 6/30/16.
 */
public class URITemplateMatcher {
    private static final Logger log = LoggerFactory.getLogger(URITemplateMatcher.class);

    public static String ID_PATTERN = "(?:\\{(?<name>[^}]+)\\})";
    public static String URL_PATH_PATTERN = "(?<name>[^/]+)";

    private String httpMethod;
    private Pattern pattern;
    private Map<String, String> contextParams = new LinkedHashMap<>();
    private boolean isParamsResolved = false;

    public URITemplateMatcher(String contextPath, String httpMethod) {

        this.httpMethod = httpMethod;
        Pattern idPattern = Pattern.compile(ID_PATTERN);
        Matcher matcher;
        String idName;
        String localContextPath = "";
        String urlPattern;
        log.debug("Received {} for matching with method {}", contextPath, httpMethod);
        contextPath = contextPath.startsWith("/") ? contextPath.substring(1) : contextPath;
        String[] contexts = contextPath.split("/");
        for (String context : contexts) {
            urlPattern = context;
            matcher = idPattern.matcher(context);
            if (matcher.matches()) {
                idName = matcher.group("name");
                urlPattern = URL_PATH_PATTERN.replace("name", idName);
                contextParams.put(idName, null);
                log.debug("Context segment {} contains id pattern with parameter name {} ", context, idName);
            }
            localContextPath = localContextPath + "/" + urlPattern;
        }


        if (!localContextPath.endsWith("/")) {
            localContextPath = localContextPath.concat("(?:/*)");
        }

        log.debug("Compiled {} pattern for the request pattern {}", localContextPath, contextPath);
        this.pattern = Pattern.compile(localContextPath);
    }

    public boolean match(String requestedPath, String httpMethod) {

        Matcher matcher;
        if (this.httpMethod.equals(httpMethod) && (matcher = pattern.matcher(requestedPath)).matches()) {
            log.debug("Successfully matched request {} against {} ", httpMethod + " : " + requestedPath, this.httpMethod + " : " + this.pattern.pattern());
            resolveContextParams(matcher);
            return true;
        }
        log.debug("Failure in matching request {} against {} ", httpMethod + " : " + requestedPath, this.httpMethod + " : " + this.pattern.pattern());
        return false;
    }

    public Map<String, String> getContextParams() {
        return isParamsResolved ? this.contextParams : null;
    }

    private void resolveContextParams(Matcher matcher) {

        for (String param : contextParams.keySet()) {
            contextParams.put(param, matcher.group(param));
            log.debug("Adding context param {} : {}", param, matcher.group(param));
        }

        this.isParamsResolved = true;
    }

    @Override
    public String toString() {
        return "URITemplateMatcher{" +
                "pattern=" + pattern.pattern() +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
