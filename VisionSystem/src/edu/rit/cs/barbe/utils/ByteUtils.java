package edu.rit.cs.barbe.utils;



public class ByteUtils {
    public static Byte[] intToByteArray(int value) {
        Byte[] b = new Byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
    
    public static final int byteArrayToInt(Byte [] b, int offset) {
        return (b[offset] << 24)
                + ((b[offset+1] & 0xFF) << 16)
                + ((b[offset+2] & 0xFF) << 8)
                + (b[offset+3] & 0xFF);
    }
}
