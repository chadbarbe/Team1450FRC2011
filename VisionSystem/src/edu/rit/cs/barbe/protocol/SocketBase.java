package edu.rit.cs.barbe.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketBase {
    protected DataOutputStream                    out       = null;
    protected DataInputStream                     in        = null;
    protected Map<Byte, Vector<ProtocolNotifyIF>> notifies  = Collections
                                                                    .synchronizedMap(new TreeMap<Byte, Vector<ProtocolNotifyIF>>());
    protected Socket                              socket    = null;
    protected boolean                             connected = false;
    private MessageSender myMessageSender = null;

    private class MessageSender implements Runnable {
        private BlockingQueue<MessageData> messages;
        private boolean enabled = true;
        
        private class MessageData {
            MessageData(SocketBase t, Byte commandId, Byte deviceId, Byte[] data) {
                this.t = t;
                this.commandId = commandId;
                this.deviceId = deviceId;
                this.data = data;
            }
            public SocketBase t;
            public Byte       commandId;
            public Byte       deviceId;
            public Byte[]     data;
        }
        
        public void disable() {
            enabled = false;
        }
        
        public MessageSender() {
            messages = new LinkedBlockingQueue<MessageData>();
        }
            
        public synchronized void SendMessage(SocketBase t, Byte commandId, Byte deviceId, Byte[] data) {
            messages.add(new MessageData(t, commandId, deviceId, data));
        }

        public void run() {
            
            while (enabled) {
                MessageData myMessage = null;
                try {
                    myMessage = messages.take();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                if(myMessage != null) {
                    try {
                        byte[] data_b = new byte[myMessage.data.length];
                        for(int i = 0; i < myMessage.data.length; i++) {
                            data_b[i] = myMessage.data[i];
                        }
                        myMessage.t.getOut().writeByte(myMessage.commandId);
                        myMessage.t.getOut().writeByte(myMessage.deviceId);
                        byte length = (byte) myMessage.data.length;
                        myMessage.t.getOut().writeByte(length);
                        myMessage.t.getOut().write(data_b);
                        myMessage.t.getOut().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Message was not received from blocking queue");
                }
            }
        }
    }
    
    
    
    public SocketBase() {
        
    }
    
    protected void startSender()
    {
        myMessageSender = new MessageSender();
        Thread sendMessage = new Thread(myMessageSender);
        sendMessage.start();
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            socket = null;
            out = null;
            in = null;
            connected = false;
            myMessageSender.disable();
        }
        else {
            System.out.println("Socket was null in close");
            Runtime.getRuntime().exit(4);
        }
    }

    public int getPortNumber() {
        int port = 0;
        if (socket != null)
            port = socket.getLocalPort();
        else {
            System.out.println("Socket was null in get port number");
            Runtime.getRuntime().exit(5);
        }
        return port;
    }

    public boolean isConnected() {
        return socket != null;
    }

    public void register(Byte commandId, ProtocolNotifyIF notify) {
        Byte n = Byte.valueOf(commandId);
        Vector<ProtocolNotifyIF> notifyList = null;
        if (notifies.containsKey(n)) {
            notifyList = notifies.get(n);
        } else {
            notifyList = new Vector<ProtocolNotifyIF>();
            notifies.put(n, notifyList);
        }
        notifyList.add(notify);

    }

    public void send(Byte commandId, Byte deviceId, int data) {
        if (out != null) {
            myMessageSender.SendMessage(this, commandId,
                    deviceId, intToByteArray(data));
        } else {
            System.out.println("Out is null in send");
            Runtime.getRuntime().exit(6);
        }
    }
    
    public void send(Byte commandId, Byte deviceId, Byte data) {
        if (out != null) {
            Byte [] data_array = new Byte[1];
            data_array[0] = data;
            myMessageSender.SendMessage(this, commandId,
                    deviceId, data_array);
        } else {
            System.out.println("Out is null in send");
            Runtime.getRuntime().exit(7);
        }
    }
    
    public void send(Byte commandId, Byte deviceId, Byte[] data) {
        if (out != null) {
            myMessageSender.SendMessage(this, commandId,
                    deviceId, data);
        } else {
            System.out.println("Out is null in send");
            Runtime.getRuntime().exit(8);
        }
    }
    
    public void send(Byte commandId) {
        if (out != null) {
            Byte [] myByteArray = new Byte[0];
            myMessageSender.SendMessage(this, commandId,
                    (byte)0, myByteArray);
        } else {
            System.out.println("Out is null in send");
            Runtime.getRuntime().exit(9);
        }
    }

    protected void processMessage() throws IOException {
        Byte commandId = in.readByte();
        Byte deviceId = in.readByte();
        int length = in.readByte();
        
        Byte[] data = new Byte[length];
        byte[] data_b = new byte[length];
        
        int leftToRead = length;
        while(leftToRead > 0) {
            int read = in.read(data_b, length - leftToRead, leftToRead);
            leftToRead -= read;
        }
        
        for(int i = 0; i < length; i++)
            data[i] = data_b[i];

        if (notifies.containsKey(commandId)) {
            Vector<ProtocolNotifyIF> notifyList = notifies.get(commandId);
            for (Enumeration<ProtocolNotifyIF> e = notifyList.elements(); e
                    .hasMoreElements();) {
                ProtocolNotifyIF notify = (ProtocolNotifyIF) e.nextElement();
                notify.protocolNotify(commandId, deviceId, data);
            }
        } else {
            System.out.println("******Unsupported Command in process message******");
        }
    }

    public DataOutputStream getOut() {
        return out;
    }
    
    private Byte[] intToByteArray(int value) {
        Byte[] b = new Byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
}
