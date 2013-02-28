package edu.rit.cs.barbe.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Connection.java Created on Sep 30, 2004
 * Author: jgramows
 * 
 * Description:
 * 	This is the socket connection to the application code.
 * 
 * History:
 * 
 */

/**
 * Types:
 * 
 */

public class SocketServer extends SocketBase implements ProtocolRouterIF,
        Runnable {
    private ServerSocket server     = null;
    private Thread       readThread = null;
    private Integer port = 0;

    public SocketServer(Integer _port) {
        port = _port;
    }
    public SocketServer(){
        port = 0;
        
    }
    
    public void startServer() {
        startSender();
        try {
            server = new ServerSocket(port, 1);
        } catch (IOException e) {
            System.out.println("Could not find a port to attach.");
            Runtime.getRuntime().exit(10);
        }
        try {
            System.out.println("Waiting for a connection on port: "
                    + server.getLocalPort());
            Socket tmpClient = server.accept();
            server.close();
            if (connected) {
                while (true) {
                    System.out.println("Connection From Another Client.");
                    System.out.println("Closing client.");
                    tmpClient.close();
                    tmpClient = server.accept();
                }
            } else {
                socket = tmpClient;
            }
            System.out.println("Connection From First Client.");
        } catch (IOException e) {

        }
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Read Failed");
            Runtime.getRuntime().exit(11);
        }
        connected = true;
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {

        while (connected) {
            try {
                processMessage();
            } catch (IOException e) {
                // System.out.println("Socket Read Failed");
                connected = false;
                this.close();
                Runtime.getRuntime().exit(12);
            }
        }
    }
}
