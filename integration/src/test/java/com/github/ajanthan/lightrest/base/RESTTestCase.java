package com.github.ajanthan.lightrest.base;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Created by ajanthan on 6/25/16.
 */
@Ignore
public class RESTTestCase {
    private static Server server;


    @BeforeClass
    public static void startServer() {

        int port = 8080;
        server = new Server(port);

        String warDir = System.getProperty("outputDirectory");

        WebAppContext context = new WebAppContext();
        context.setResourceBase(warDir);
        context.setDescriptor(warDir + "WEB-INF/web.xml");
        context.setConfigurations(new Configuration[]{
                new AnnotationConfiguration(), new WebXmlConfiguration(),
                new WebInfConfiguration(), new TagLibConfiguration(),
                new PlusConfiguration(), new MetaInfConfiguration(),
                new FragmentConfiguration(), new EnvConfiguration()});

        context.setContextPath("/");
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*\\.jar$");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        try {
            server.start();
            server.dump(System.err);
            //server.join();
            System.out.println("Started the server");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @AfterClass
    public static void stopServer() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
