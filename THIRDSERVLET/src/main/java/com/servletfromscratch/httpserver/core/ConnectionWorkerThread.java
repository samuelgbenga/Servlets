package com.servletfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ConnectionWorkerThread extends Thread{

    private final Socket socket;
    private final String webroot;
    private final String getJson;
    private final static Logger LOGGER = LoggerFactory.getLogger(ConnectionWorkerThread.class);


    public ConnectionWorkerThread(Socket socket, String webroot, String getJson) {
        this.socket = socket;
        this.webroot = webroot;
        this.getJson = getJson;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // gets the clients http request
            byte[] buffer = new byte[1024];
            int read = inputStream.read(buffer);
            String request = new String(buffer, 0, read);

            // parse the request line to
            // and gets the requested path.
            String[] requestLines = request.split("\r\n");
            String[] requestLine = requestLines[0].split(" ");
            String path = requestLine[1];



            // read the html file
            File file = new File("src/main/resources/index.html");
            byte[] htmlByte = Files.readAllBytes(file.toPath());
            String html = new String(htmlByte);

            //Testing buffer Reader as solution and it worked


            // read from json file
            File fileJson = new File("src/main/resources/getJson.json");
            byte[] jsonBytes = Files.readAllBytes(fileJson.toPath());
            String jsonResponse = new String(jsonBytes);

            // initialize response and CRLF
            String response;
            String CRLF = "\r\n";

            if (path.equals(webroot)) {
                response = "HTTP/1.1 200 OK " + CRLF +
                        "Content-Type: text/html" + CRLF +
                        "Content-Length: " + html.length() + CRLF + CRLF +
                        html + CRLF + CRLF;

            } else if (path.equals(getJson)) {
                response = "HTTP/1.1 200 OK " + CRLF +
                        "Content-Type: application/json" + CRLF +
                        "Content-Length: " + jsonResponse.length() + CRLF + CRLF +
                        jsonResponse + CRLF + CRLF;
            } else {
                String notFoundHtml = "<html><head><title>404 Not Found</title></head>" +
                        "<body><h1>404 Not Found</h1></body></html>";
                response = "HTTP/1.1 404 Not Found" + CRLF +
                        "Content-Type: text/html" + CRLF +
                        "Content-Length: " + notFoundHtml.length() + CRLF + CRLF +
                        notFoundHtml + CRLF;
            }


            outputStream.write(response.getBytes());

            LOGGER.info("Connection processed completed");
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException _) {}
            }
            if(outputStream != null){
                try {
                   outputStream.close();
                } catch (IOException _) {}
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException _) {}
            }
        }
    }



////    @Override
//    public void run() {
//
//        try (InputStream stream = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
//            // get the file path
//            File file = new File("src/main/resources/index.html");
//            File fileJson = new File("src/main/resources/getJson.json");
//
//
//            // convert to array of bytes
//            byte[] htmlBytes = Files.readAllBytes(file.toPath());
//            byte[] jsonBytes = Files.readAllBytes(fileJson.toPath());
//
//            // convert back to string
//            String html = new String(htmlBytes);
//            String json = new String(jsonBytes);
//
//            // init CRLF
//            String CRLF = "\r\n";
//            String response;
//
//            // get the http path from the client get request
//            byte[] responseBytes = new byte[1024];
//            int read = stream.read(responseBytes);
//
//            String request = null;
//            String path = "";
//            try {
//                request = new String(responseBytes, 0, read);
//                String[] requestSplit = request.split(CRLF);
//                String[] lineSplit = requestSplit[0].split(" ");
//                 path = lineSplit[1];
//            } catch (Exception e) {
//                LOGGER.error("out of byte bound");
//            }
//
//            // get the path having gotten the request
//
//
//            // implement the ifs to get result
//
//            if (webRoot.equals(path)) {
//                response = "HTTP/1.1 200 OK" + CRLF +
//                        "Content-Type: text/html" + CRLF +
//                        "Content-Length: " + html.length() + CRLF + CRLF+
//                        html + CRLF + CRLF;
//            } else if (getJson.equals(path)) {
//                response = "HTTP/1.1 200 OK" + CRLF +
//                        "Content-Length: " + json.length() + CRLF +
//                        "Content-Type: application/json" + CRLF + CRLF+
//                        json + CRLF + CRLF;
//            } else {
//                String defaultHtml = "<html><body>Page Not Founder</body></html>";
//                response = "HTTP/1.1 404 Not Found" + CRLF +
//                        "Content-Type: text/html" + CRLF +
//                        "Content-Length: " + defaultHtml.length() + CRLF + CRLF+
//                        defaultHtml + CRLF + CRLF;
//            }
//
//
//            out.write(response.getBytes());
//
//            LOGGER.info("CONNECTION COMPLETED");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException _) {
//                }
//            }
//        }
//
//
//    }



}
