package com.mtn.binder;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import com.mtn.hookutil.HookUtil;

public class HookPhone {
	private static final String TAG="hookphone";
	public static void hook() {
		Log.i(TAG, "HOOK PHONE START********");
		IBinder oldBinder = HookUtil.getCacheBinder(Context.TELEPHONY_SERVICE);
		String binderName = oldBinder.getClass().getName();
		Log.i(TAG, "hook PHONE before binder name:" + binderName);
		if (binderName.equals("android.os.BinderProxy")) {
			HookPhoneBinder hookPhoneBinder = new HookPhoneBinder(
					oldBinder);
			HookUtil.addTosCache(Context.TELEPHONY_SERVICE, hookPhoneBinder);
			Log.i(TAG, "HOOK PHONE FINISHED");
		}
		IBinder hookBinder = HookUtil.getCacheBinder(Context.TELEPHONY_SERVICE);
		Log.i(TAG, "after hook binder name:" + hookBinder.getClass().getName());
		Log.i(TAG, "hook PHONE end*******");
	}
}
