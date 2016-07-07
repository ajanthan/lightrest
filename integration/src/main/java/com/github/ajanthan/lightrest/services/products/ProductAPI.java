package com.github.ajanthan.lightrest.services.products;

import com.github.ajanthan.lightrest.annotations.API;
import com.github.ajanthan.lightrest.services.products.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ajanthan on 6/27/16.
 */
@Path("/product")
@Produces("application/json")
@Consumes("application/json")
@API
public class ProductAPI {
    public static Map<String, Product> productMap = new ConcurrentHashMap<>();

    @POST
    public Response addProduct(Product product) {
        Product prod = productMap.put(product.getName(), product);
        System.out.println("Added product " + product);
        return Response.accepted(product).build();
    }

    @GET
    @Path("/{name}")
    public Product getProduct(@PathParam("name") String name) {
        return productMap.get(name);
    }
}
