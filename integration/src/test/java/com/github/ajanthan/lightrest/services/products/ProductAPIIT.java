package com.github.ajanthan.lightrest.services.products;

import com.github.ajanthan.lightrest.base.RESTTestCase;
import com.github.ajanthan.lightrest.services.products.model.Product;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ajanthan on 6/27/16.
 */
public class ProductAPIIT extends RESTTestCase {

    @Test
    public void TestProductAPI() throws Exception {
        RestAssured.config = RestAssuredConfig.newConfig().httpClient(HttpClientConfig.httpClientConfig());

        Product product = new Product();
        product.setName("laptop");
        product.setAvailable(true);
        product.setPrize((long) 578);
        product.setRating((float) 4.3);
        addProduct(product);
        getProduct(product);
    }

    public void addProduct(Product product) throws Exception {


        given().body(product).contentType(ContentType.JSON).expect().statusCode(Response.Status.ACCEPTED.getStatusCode()).when().post("/api/product").then().log().all();
    }


    public void getProduct(Product product) throws Exception {


        expect().statusCode(200).body("name", equalTo("laptop")).when().get("/api/product/" + product.getName()).then().log().all();
    }

}