package com.servletfromscratch;

import com.servletfromscratch.httpserver.config.Configuration;
import com.servletfromscratch.httpserver.config.ConfigurationManager;
import com.servletfromscratch.httpserver.core.ServerListenerThread;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("Starting HTTP Server.......");


        ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");

        Configuration config = ConfigurationManager.getInstance().getConfig();

        LOGGER.info("using port: " + config.getPort());
        LOGGER.info("using webroot: " + config.getWebroot());
        LOGGER.info("using jsonPath: " + config.getJson());

        // Test run a simple server

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(),
                    config.getWebroot(), config.getJson());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
