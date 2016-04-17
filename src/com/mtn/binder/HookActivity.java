package com.mtn.binder;

import android.os.IBinder;
import android.util.Log;

import com.mtn.hookutil.HookUtil;

public class HookActivity {

	private static final String TAG = "hookactivity";

	public static void hook() {
		Log.d(TAG, "hook start---->");
		IBinder binder = HookUtil.getCacheBinder("activity");
		String classNameString = binder.getClass().getName();
		Log.d(TAG, "before hook binder class:" + binder.getClass().getName());
		if (classNameString.equals("android.os.BinderProxy")) {
			// HookIPhoneSubInfoBinder binderTest = new
			// HookIPhoneSubInfoBinder(binder);
			HookActivityBinder binderTest = new HookActivityBinder(binder);
			HookUtil.addTosCache("activity", binderTest);
		}
		IBinder temp = HookUtil.getCacheBinder("activity");
		Log.d(TAG, "after hook binder class:" + temp.getClass().getName());
		Log.d(TAG, "hook end---->");

	}
}
