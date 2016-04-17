package com.mtn.javainject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;

public class LocalSocketTest {

	public static final String SOCKET_NAME = "test.socket";
	private static final String TAG = "test";
	private LocalServerSocket mServerSocket = null;

	private void startEchoThread(final LocalSocket socket) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					InputStreamReader isr = new InputStreamReader(is);
					while (true) {
						char[] data = new char[128];

						int ret = isr.read(data);
						// for(int i=0;i<ret;i++){
						Log.d(TAG, " service recv data:" + new String(data,0,ret));
						// }
						// byte[] values = TypeUtils.float2Byte(-1234567.122f);
						// float fd = -1234567.122f;
						// Log.d(TAG, " fd="+fd);
						// for(int i=0;i<values.length;i++){
						// Log.d(TAG, "values["+i+"]="+values[i]);
						// }
						Log.d(TAG, "write  ok ");
						os.write("ok".getBytes("utf8"));
						os.flush();
						os.close();
						is.close();
						Log.d(TAG, "after write: ");
						 return;
					}
				} catch (IOException e) {
					Log.d(TAG, "in echo thread loop: " + e.getMessage());
				}
			}
		};
		t.start();
	}

	public void init() {
		try {
			mServerSocket = new LocalServerSocket(SOCKET_NAME);
			Log.d(TAG, "new LocalServerSocket");
		} catch (IOException e) {
			Log.d(TAG, "in onCreate, making server socket: " + e);
			return;
		}

		Thread t = new Thread() {
			@Override
			public void run() {
				LocalSocket socket = null;
				while (true) {
					try {
						Log.d(TAG, "Waiting for connection...");
						socket = mServerSocket.accept();
						Log.d(TAG, ".....Got socket: " + socket);
						if (socket != null) {
							startEchoThread(socket);
						} else {
							return; // socket shutdown?
						}
					} catch (IOException e) {
						Log.d(TAG, "in accept: " + e);
					}
				}
			}
		};
		t.start();
	}
}
