package com.servletfromscratch.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class json {

    // the objectMapper itself
    private static final ObjectMapper myObjectMapper = defaultObjectMapper();

    // create and instance of the Object mapper
    // and make sure it did not fail during deserialization
    // when an unknown property is found in the json file that
    // is not in the configuration setup/entity
    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;

    }

    // in convert the json string to a node that can be
    // readily accessible unlike the string format
    public static JsonNode parse(String jsonString) throws IOException {
        return myObjectMapper.readTree(jsonString);
    }

    // method to deserialize jsonNode into a java object
    // and provides a types-safe and reusable way of doing
    // deserialization into the specified java object.
    public static <T> T fromJson(JsonNode jsonNode, Class<T> clazz) throws IOException {
        return myObjectMapper.treeToValue(jsonNode, clazz);
    }

   // method do serialize java object into jsonNode
   public static JsonNode toJson(Object obj) throws IOException {
        return myObjectMapper.valueToTree(obj);
   }

   //calls generateJson with pretty = false
   public static String stringify(Object obj) throws JsonProcessingException {
        return generateJson(obj, false);
   }

  // convert to a more pretty string
   public static String stringifyPretty(Object obj) throws JsonProcessingException {
        return generateJson(obj, true);
   }

    // generate the json String from java object
    // a serialization process
    private static String generateJson(Object obj, boolean pretty)throws JsonProcessingException {
        ObjectWriter ow = myObjectMapper.writer();
        if(pretty){
            ow = ow.with(SerializationFeature.INDENT_OUTPUT);
        }
       return ow.writeValueAsString(obj);
    }




}
