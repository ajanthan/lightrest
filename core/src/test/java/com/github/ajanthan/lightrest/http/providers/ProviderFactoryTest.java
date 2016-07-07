package com.github.ajanthan.lightrest.http.providers;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/**
 * Created by ajanthan on 7/2/16.
 */
public class ProviderFactoryTest {
    private ProviderFactory providerFactory;

    @Before
    public void setUp() throws Exception {
        providerFactory = ProviderFactory.getIntance();

    }

    @Test
    public void getMessageBodyReader() throws Exception {

        Product product = new Product();
        product.setId("prod1");
        product.setName("clock");
        product.setPrize(1200.0);
        product.setAvailable(true);
        InputStream ios = null;
        try {
            ios = new ByteArrayInputStream(product.toJSONString().getBytes(StandardCharsets.UTF_8));

            MessageBodyReader reader = providerFactory.getMessageBodyReader(Product.class, Product.class, null, MediaType.APPLICATION_JSON_TYPE);

            Product product1 = (Product) reader.readFrom(Product.class, Product.class, null, MediaType.APPLICATION_JSON_TYPE, null, ios);

            assertEquals(product, product1);
        } finally {
            ios.close();
        }

    }

    @Test
    public void messageBodyWriter() throws Exception {

        Product product = new Product();
        product.setId("prod2");
        product.setName("Bed");
        product.setPrize(6600.0);
        product.setAvailable(true);
        InputStream ios = null;
        OutputStream oos = null;

        try {
            ios = new ByteArrayInputStream(product.toJSONString().getBytes(StandardCharsets.UTF_8));
            oos = new ByteArrayOutputStream();

            MessageBodyWriter writer = providerFactory.getMessageBodyWriter(Product.class, Product.class, null, MediaType.APPLICATION_JSON_TYPE);
            writer.writeTo(product, Product.class, Product.class, null, MediaType.APPLICATION_JSON_TYPE, null, oos);

            assertEquals(product, Product.getProduct(oos.toString()));
        } finally {

            ios.close();
            oos.close();
        }

    }

    static class Product {
        private String id;
        private String name;
        private double prize;
        private boolean available;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrize() {
            return prize;
        }

        public void setPrize(double prize) {
            this.prize = prize;
        }

        public boolean getAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", prize=" + prize +
                    ", available=" + available +
                    '}';
        }

        public String toJSONString() {
            return "{\n" +
                    "\"id\":\"" + id + "\",\n" +
                    "\"name\":\"" + name + "\",\n" +
                    "\"prize\":" + prize + ",\n" +
                    "\"available\":" + available + "\n" +
                    "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Product product = (Product) o;

            if (Double.compare(product.getPrize(), getPrize()) != 0) return false;
            if (getAvailable() != product.getAvailable()) return false;
            if (!getId().equals(product.getId())) return false;
            return getName().equals(product.getName());

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = getId().hashCode();
            result = 31 * result + getName().hashCode();
            temp = Double.doubleToLongBits(getPrize());
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (getAvailable() ? 1 : 0);
            return result;
        }

        public static Product getProduct(String json) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Product product = mapper.readValue(json, Product.class);
                return product;
            } catch (IOException e) {
                return null;
            }
        }

    }

}