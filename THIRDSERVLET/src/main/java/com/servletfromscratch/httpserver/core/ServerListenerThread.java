package com.servletfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{


    private final String webroot;
    private final String getJson;
    private final ServerSocket serverSocket;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public ServerListenerThread(int port, String webroot, String getJson) throws IOException {
        this.webroot = webroot;
        this.getJson = getJson;
        this.serverSocket = new ServerSocket(port);
    }



    @Override
    public void run() {
        try{
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                LOGGER.info("Accepted connection from "+ socket.getInetAddress());

                ConnectionWorkerThread cwt = new ConnectionWorkerThread(socket, webroot, getJson);
                cwt.start();
            }

        }catch (Exception e){
            LOGGER.error("problem with SocketServer: ",e);
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException _) {}
            }
        }
    }
}
