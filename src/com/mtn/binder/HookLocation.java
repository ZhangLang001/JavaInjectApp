package com.mtn.binder;

import com.mtn.hookutil.HookUtil;

import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class HookLocation {

	private static final String TAG = "hooklocation";
	private static final String LOCATION_SERVICE = "location";

	public static void hook() {
		Log.i(TAG, "HOOK LOCATION START********");
		IBinder oldBinder = HookUtil.getCacheBinder(LOCATION_SERVICE);
		String binderName = oldBinder.getClass().getName();
		Log.i(TAG, "hook location before binder name:" + binderName);
		if (Build.VERSION.SDK_INT < 14) {
			HookUtil.setStaticFiled("android.app.ContextImpl",
					"sLocationManager", null, null);
		}
		if (binderName.equals("android.os.BinderProxy")) {
			HookLocationBinder hookLocationBinder = new HookLocationBinder(
					oldBinder);
			HookUtil.addTosCache(LOCATION_SERVICE, hookLocationBinder);
			Log.i(TAG, "HOOK LOCATION FINISHED");
		}
		IBinder hookBinder = HookUtil.getCacheBinder(LOCATION_SERVICE);
		Log.i(TAG, "after hook binder name:" + hookBinder.getClass().getName());
		Log.i(TAG, "hook location end*******");
	}
}
