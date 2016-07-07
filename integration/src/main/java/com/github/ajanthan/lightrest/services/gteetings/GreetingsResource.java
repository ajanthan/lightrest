package com.github.ajanthan.lightrest.services.gteetings;

import com.github.ajanthan.lightrest.annotations.API;
import com.github.ajanthan.lightrest.services.gteetings.model.Greetings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by ajanthan on 6/25/16.
 */
@API
@Path("/test")
public class GreetingsResource {
    @Produces("application/json")
    @GET
    @Path("/{name}")
    public Response sayHello(@PathParam("name") String name) {
        return Response.accepted(new Greetings(name)).build();
    }
}
