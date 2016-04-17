package com.mtn.javainject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CopyFile {

	
	public static native void defendInject();
	public static native void callHookTwo(String dexPath,String optPath);
	public static native void callHook(String dexPath,String optPath,String className,String methodName);
	public static  void copyFile(InputStream inputStream,FileOutputStream fileOutputStream) throws IOException
	{
		byte[] buffer=new byte[4*1024];
		int count=0;
		while((count=inputStream.read(buffer))>0)
		{
			fileOutputStream.write(buffer,0,count);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}
}
