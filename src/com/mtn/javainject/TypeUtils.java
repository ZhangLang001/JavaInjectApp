package com.mtn.javainject;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class TypeUtils {
	 public static byte[] floatToByte(float v) {  
	        ByteBuffer bb = ByteBuffer.allocate(4);  
	        byte[] ret = new byte[4];  
	        FloatBuffer fb = bb.asFloatBuffer();  
	        fb.put(v);  
	        bb.get(ret);  
	        return ret;  
	    }  
	  
	    public static byte[] float2Byte(float f) {  
	        byte[] b = new byte[4];  
	        int l = Float.floatToIntBits(f);  
	        for (int i = 0; i < b.length; i++) {  
	            b[i] = new Integer(l).byteValue();  
	            l = l >> 8;  
	        }  
	        return b;  
	    }  
	  
	    public static byte[] doubleToByte(double d) {  
	        byte[] b = new byte[8];  
	        long l = Double.doubleToLongBits(d);  
	        for (int i = 0; i < b.length; i++) {  
	            b[i] = new Long(l).byteValue();  
	            l = l >> 8;  
	        }  
	        return b;  
	    }  
	  
	    public static float byteToFloat(byte[] v) {  
	        ByteBuffer bb = ByteBuffer.wrap(v);  
	        FloatBuffer fb = bb.asFloatBuffer();  
	        return fb.get();  
	    }  
	  
	    public static float byte2Float(byte[] b) {  
	        int l = 0;  
	        l = b[0];  
	        l &= 0xff;  
	        l |= ((int) b[1] << 8);  
	        l &= 0xffff;  
	        l |= ((int) b[2] << 16);  
	        l &= 0xffffff;  
	        l |= ((int) b[3] << 24);  
	        l &= 0xffffffffl;  
	        return Float.intBitsToFloat(l);  
	    }  
}
