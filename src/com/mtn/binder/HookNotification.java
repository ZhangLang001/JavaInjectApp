package com.mtn.binder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.os.IBinder;
import android.util.Log;

import com.mtn.hookutil.HookUtil;

public class HookNotification {

	private static final String TAG="hooknotification";
	public static void hook()
	{
		Log.d(TAG, "hook start---->");
		IBinder binder = HookUtil.getCacheBinder("notification");
		String classNameString = binder.getClass().getName();
		Log.d(TAG, "before hook binder class:"
				+ binder.getClass().getName());
		
		try {
			Class  class1=Class.forName("android.app.NotificationManager");
			Field field=class1.getDeclaredField("sService");//.getField("sService");
			field.setAccessible(true);
			field.set(null,null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "ClassNotFoundException---->"+e);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "NoSuchFieldExceptiont---->"+e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "IllegalArgumentException---->"+e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "IllegalAccessException---->"+e);
		}
		
		if (classNameString.equals("android.os.BinderProxy")) {
			HookNotificationBinder binderTest = new HookNotificationBinder(binder);
			HookUtil.addTosCache("notification", binderTest);
		}
		IBinder temp = HookUtil.getCacheBinder("notification");
		Log.d(TAG, "after hook binder class:"
				+ temp.getClass().getName());
		Log.d(TAG, "hook end---->");
	}
}
