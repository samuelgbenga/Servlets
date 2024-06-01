package com.servletfromscratch;

import com.servletfromscratch.httpserver.config.Configuration;
import com.servletfromscratch.httpserver.config.ConfigurationManager;

public class HttpServer {

    public static void main(String[] args) {

        System.out.println("Server starting......");

//        ConfigurationManager.loadConfiguration("src/main/resources/http.json");
//
//        Configuration config = ConfigurationManager.getConfiguration();
//        System.out.println("port: "+config.getPort());
//        System.out.println("webroot: "+config.getWebroot());

        ConfigurationManager instance = ConfigurationManager.getInstance();

        ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");

        Configuration config = ConfigurationManager.getInstance().getConfig();

        System.out.println("using port: " + config.getPort());
        System.out.println("using webroot: " + config.getWebroot());

    }
}
