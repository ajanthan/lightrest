package com.github.ajanthan.lightrest.servlet.response;

import com.github.ajanthan.lightrest.http.LightRestResponseContext;
import com.github.ajanthan.lightrest.http.providers.ProviderFactory;
import com.github.ajanthan.lightrest.http.response.EntityContext;
import com.github.ajanthan.lightrest.http.response.LightRestServerErrorMessage;
import com.github.ajanthan.lightrest.http.response.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ajanthan on 7/2/16.
 */
public class DefaultResponseWriter implements ResponseWriter {
    private static final Logger log = LoggerFactory.getLogger(DefaultResponseWriter.class);
    private HttpServletResponse httpServletResponse;
    private ProviderFactory providerFactory;

    public DefaultResponseWriter(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
        this.providerFactory = ProviderFactory.getIntance();
    }

    @Override
    public void writeResponse(LightRestResponseContext restResponseContext) {
        int responseStatus = restResponseContext.getStatus();
        EntityContext entityContext = restResponseContext.getEntityContext();

        if (entityContext.getEntityClass().equals(Response.class)) {
            //handle response object
            Response response = (Response) entityContext.getEntityObject();
            responseStatus = response.getStatus();
            if (response.getEntity() != null) {
                entityContext.setEntityObject(response.getEntity());
                entityContext.setEntityClass(response.getEntity().getClass());
                entityContext.setEntityType(response.getEntity().getClass());
            } else {
                entityContext = null;
                log.debug("Response entity is null for Response object");
            }
            log.debug("handled Response object successfully");
        }

        if (responseStatus > 0) {
            log.debug("Setting response status code {}", responseStatus);
            httpServletResponse.setStatus(responseStatus);
        } else {
            httpServletResponse.setStatus(200);
            log.debug("Setting default response status code 200");
        }
        if (entityContext == null) {
            return;

        } else if (entityContext.getEntityObject() == null) {
            return;
        }
        MediaType responseType = restResponseContext.getMediaType();

        MessageBodyWriter writer = providerFactory.getMessageBodyWriter(entityContext.getEntityClass(), entityContext.getEntityType(), null, responseType);
        if (writer != null) {
            OutputStream outputStream = null;
            try {
                outputStream = httpServletResponse.getOutputStream();
                httpServletResponse.setContentType(responseType.toString());
                writer.writeTo(entityContext.getEntityObject(), entityContext.getEntityClass(), entityContext.getEntityType(), null, responseType, null, outputStream);
                log.debug("Handled response with {} for {}", writer, responseType);
            } catch (IOException e) {

                String msg = "Error occurred while writing the response to outputstream ";
                log.error(msg, e);
                LightRestServerErrorMessage errorMessage = new LightRestServerErrorMessage();
                errorMessage.setErrorMessage(msg);
                errorMessage.setDetailMessage(e.getMessage());
                try {
                    writer.writeTo(entityContext.getEntityObject(), entityContext.getEntityClass(), entityContext.getEntityType(), null, responseType, null, outputStream);
                } catch (IOException e1) {
                    log.error("Error in writing error message into response", e);
                    try {
                        httpServletResponse.sendError(500, "Error in writing error message into response");
                    } catch (IOException e2) {
                        //ignore
                    }
                }

            } finally {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        } else {
            log.debug("Appropriate response writer is not found");
        }

    }
}
