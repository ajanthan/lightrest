package com.github.ajanthan.lightrest.http.response;

/**
 * Created by ajanthan on 7/2/16.
 */
public class LightRestServerErrorMessage {
    private String errorMessage;
    private String detailMessage;

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
