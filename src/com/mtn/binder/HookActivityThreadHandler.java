package com.mtn.binder;

import java.io.FileDescriptor;

import org.apache.http.impl.client.DefaultHttpClient;

import com.mtn.hookutil.HookUtil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HookActivityThreadHandler extends Handler {
	private Handler handler;

	public  HookActivityThreadHandler(Handler hand) {
		this.handler = hand;
	}
  // DefaultHttpClient
	String codeToString(int code) {
		String retString = (String) HookUtil.invokeMethod(
				"android.app.ActivityThread$H", "codeToString", this.handler,
				new Class[] { int.class }, new Object[] { code });
		return retString;
	}

	void maybeSnapshot() {
		HookUtil.invokeMethod("android.app.ActivityThread$H", "maybeSnapshot",
				this.handler, new Class[0], new Object[0]);
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		// super.handleMessage(msg);
		Log.i("hookactivitythread","handleMessage===>"+msg.what);
		handler.handleMessage(msg);
	}

}
