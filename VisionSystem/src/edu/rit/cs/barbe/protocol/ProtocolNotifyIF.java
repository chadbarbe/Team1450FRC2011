/*
0 * ProtocolNotifyIF.java Created on Apr 21, 2005
 * Author: gramowsk
 * 
 * Description:
 * TODO <Add Description>
 * 
 * History:
 * 
 */
package edu.rit.cs.barbe.protocol;

/**
 * 
 * 
 */

public interface ProtocolNotifyIF {

    public void protocolNotify(Byte commandId, Byte deviceId, Byte[] data);

}
