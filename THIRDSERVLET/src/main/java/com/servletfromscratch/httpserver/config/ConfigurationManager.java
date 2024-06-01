package com.servletfromscratch.httpserver.config;

import com.servletfromscratch.httpserver.util.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// the singleton class
public class ConfigurationManager {

//    // initialize configManager and config
//    private static ConfigurationManager instance;
//    private static Configuration config;
//
//    // make it a singleton to ensure only one
//    // instance available to avoid error
//    private ConfigurationManager() {}
//
//    // create a new config manager if null
//    public static ConfigurationManager getInstance() {
//        if (instance == null) {
//            instance = new ConfigurationManager();
//        }
//        return instance;
//    }
//
//
//    // load or set the configuration settings
//    // into the java object;
//    public void loadConfiguration(String filePath) {
//        FileReader configFile = null;
//        // get file from path
//        try {
//            configFile = new FileReader(filePath);
//        } catch (Exception e) {
//            throw new HttpConfigurationException(e+" unable to ger file " +
//                    "from path: "+ filePath);
//        }
//
//        // read the file as a string
//        StringBuilder sb = new StringBuilder();
//        int i;
//
//        try {
//            while((i = configFile.read()) != -1){
//                sb.append((char)i);
//            }
//        } catch (IOException e) {
//            throw new HttpConfigurationException(e+" error from reading file");
//        }
//
//        // convert the json string to jsonNode
//        // for easy accessibility
//        JsonNode node = null;
//        try {
//            node = json.parse(sb.toString());
//        } catch (IOException e) {
//            throw new HttpConfigurationException(e+" this error is from " +
//                    "json parser");
//        }
//
//        try {
//            config = json.fromJson(node, Configuration.class);
//        } catch (IOException e) {
//            throw new HttpConfigurationException(e+" unable to load jsonNode to config");
//        }
//
//    }
//
//    // get the configuration loaded config setting from
//    // the java object.
//    public Configuration getConfiguration() {
//        if (config == null) {
//            throw new HttpConfigurationException("No configuration found");
//        }
//        return config;
//    }

    private static Configuration config;
    private static ConfigurationManager instance;

    private ConfigurationManager(){}

    public static ConfigurationManager getInstance(){
        if(instance == null){
            instance = new ConfigurationManager();
        }
       return instance;
    }


    // load config file to java object
    public void loadConfig(String filePath){

        // read the file from the file path
        StringBuilder sb = new StringBuilder();
        try(FileReader fileReader = new FileReader(filePath)){
            int i;
            while( (i = fileReader.read()) != -1)
                    sb.append((char)i);
        }catch (Exception e){
            throw new HttpConfigurationException(e+" error on file processing");
        }

        // convert the string to jsonNode
        JsonNode node = null;

        try {
            node = json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException(e+" error parsing jsonNode");
        }

        // finally load the jsonNode to a java object

        try {
            config = json.fromJson(node, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Configuration getConfig(){
        if(config == null) throw new HttpConfigurationException("no config found");
        return config;
    }
}
