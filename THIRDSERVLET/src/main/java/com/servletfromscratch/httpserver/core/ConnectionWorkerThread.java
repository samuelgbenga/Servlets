package com.servletfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
}
