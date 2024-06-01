package com.servletfromscratch.httpserver.config;

import com.servletfromscratch.httpserver.util.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileReader;
import java.io.IOException;

// the singleton class
public class ConfigurationManager {

    // initialize configManager and config
    private static ConfigurationManager instance;
    private static Configuration config;

    // make it a singleton to ensure only one
    // instance available to avoid error
    private ConfigurationManager() {}

    // create a new config manager if null
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }


    // load or set the configuration settings
    // into the java object;
    public static void loadConfiguration(String filePath) {
        FileReader configFile = null;
        // get file from path
        try {
            configFile = new FileReader(filePath);
        } catch (Exception e) {
            throw new HttpConfigurationException(e+" unable to ger file " +
                    "from path: "+ filePath);
        }

        // read the file as a string
        StringBuilder sb = new StringBuilder();
        int i;

        try {
            while((i = configFile.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e+" error from reading file");
        }

        // convert the json string to jsonNode
        // for easy accessibility
        JsonNode node = null;
        try {
            node = json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException(e+" this error is from " +
                    "json parser");
        }

        try {
            config = json.fromJson(node, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException(e+" unable to load jsonNode to config");
        }

    }

    // get the configuration loaded config setting from
    // the java object.
    public static Configuration getConfiguration() {
        if (config == null) {
            throw new HttpConfigurationException("No configuration found");
        }
        return config;
    }
}
