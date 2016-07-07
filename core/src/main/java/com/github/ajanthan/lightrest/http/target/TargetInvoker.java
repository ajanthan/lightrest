package com.github.ajanthan.lightrest.http.target;

import com.github.ajanthan.lightrest.http.LightRestRequestContext;
import com.github.ajanthan.lightrest.http.LightRestResponseContext;

/**
 * Created by ajanthan on 6/30/16.
 */
public interface TargetInvoker {

    void invoke(LightRestRequestContext context, LightRestResponseContext restResponseContext);

}
