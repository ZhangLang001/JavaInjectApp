package com.mtn.hookutil;

import java.lang.reflect.Field;
import java.util.HashMap;

import android.os.IBinder;
import android.util.Log;

public class HookJava {

	private static final Boolean DEBUG = true;
	private static final String TAG = "HookJava";

	public static boolean Hook(String paramString, IBinder paramIBinder) {
		Boolean retBoolean = false;
		try {
			Field localField = Class.forName("android.os.ServiceManager")
					.getDeclaredField("sCache");
			localField.setAccessible(true);
			((HashMap) localField.get(null)).put(paramString, paramIBinder);
			retBoolean = true;
			return retBoolean;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "NoSuchFieldException:" + e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			HookLog.d(DEBUG, TAG, "ClassNotFoundException:" + e.toString());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			HookLog.d(DEBUG, TAG, "IllegalArgumentException:" + e.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			HookLog.d(DEBUG, TAG, "IllegalAccessException:" + e.toString());
		}
		return retBoolean;
	}
}
