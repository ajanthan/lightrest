package com.github.ajanthan.lightrest.http.target;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ajanthan on 6/30/16.
 */
public class URITemplateMatcherTest {

    @Test
    public void basicMatch() throws Exception {

        URITemplateMatcher templateMatcher = new URITemplateMatcher("/api/products/{id}", "GET");
        assertTrue("Matching with correct URL and Method is failed", templateMatcher.match("/api/products/prod1", "GET"));
        assertTrue("Matching with correct URL and Method with trailing slash is failed", templateMatcher.match("/api/products/prod2/", "GET"));
        assertEquals("Extracted context params is wrong", "prod2", templateMatcher.getContextParams().get("id"));
        assertFalse("Matching with correct URL and wrong Method is failed", templateMatcher.match("/api/products/prod3", "POST"));
        assertFalse("Matching with wrong URL and  correct Method is failed", templateMatcher.match("/api/products/bad/prod4", "GET"));

        templateMatcher = new URITemplateMatcher("/api/products/{id}/{name}", "GET");
        assertTrue("Correct URL and Method with more than one context parameters is failed", templateMatcher.match("/api/products/prod1/clock1", "GET"));
        assertEquals("Extracted context params is wrong", "prod1", templateMatcher.getContextParams().get("id"));
        assertEquals("Extracted context params is wrong", "clock1", templateMatcher.getContextParams().get("name"));
        assertFalse("Wrong URL and Method with more than one context parameters is failed", templateMatcher.match("/api/products/bad/prod2/ffu1", "GET"));
        assertFalse("Wrong URL(less context) and Method with more than one context parameters is failed", templateMatcher.match("/api/products/bad", "GET"));


    }


}