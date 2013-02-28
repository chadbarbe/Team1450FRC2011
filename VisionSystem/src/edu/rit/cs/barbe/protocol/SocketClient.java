package edu.rit.cs.barbe.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient extends SocketBase implements ProtocolRouterIF,
        Runnable {
    private String server     = null;
    private int    port       = 0;
    private Thread readThread = null;

    public SocketClient(String _server, int _port) { // Begin Constructor
        server = _server;
        port = _port;
        try {
            socket = new Socket(server, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            connected = true;
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            Runtime.getRuntime().exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            Runtime.getRuntime().exit(2);
        }
        startSender();
        startReader();
    }
    
    protected void startReader()
    {
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        // Create socket connection

        while (connected) {
            try {
                processMessage();
            } catch (IOException e) {
                System.out.println("Socket Read Failed");
                connected = false;
                this.close();
                Runtime.getRuntime().exit(3);
            }
        }
    }
}