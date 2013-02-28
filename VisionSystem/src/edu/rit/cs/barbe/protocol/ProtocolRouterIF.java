package edu.rit.cs.barbe.protocol;

public interface ProtocolRouterIF {

    public abstract int getPortNumber();

    public abstract void close();

    public abstract void register(Byte commandId, ProtocolNotifyIF notify);

    public abstract void send(Byte commandId, Byte deviceId, int data);
    
    public abstract void send(Byte commandId, Byte deviceId, Byte data);
    
    public abstract void send(Byte commandId, Byte deviceId, Byte[] data);
    
    public abstract void send(Byte commandId);

    /**
     * @return the connected
     */
    public abstract boolean isConnected();

}