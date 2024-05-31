package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){


        try{
            ServerSocket server = new ServerSocket(8080);
            System.out.println("Listening on port 8080: ....");

            while(true){
               try(Socket client = server.accept()){
                   Date today = new Date();
                   String httpResponse ="HTTP/1.1 200 OK\r\n\r\n" + today.toString() + "\r\n\r\n";
                   client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
               }



                // write to the client


                // get client request
                final Socket client = server.accept();
                InputStream inputStream = client.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bf.readLine()) != null){
                    System.out.println(line);
                }
                client.close();
                bf.close();
                break;
            }

        } catch(Exception e){
            System.out.println(e);
        }

    }
}