package com.mtn.binder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.os.IBinder;
import android.util.Log;

import com.mtn.hookutil.HookUtil;

public class HookIPhoneSubInfo {

	/*private static IBinder getIPhoneSubInfoIBinder() {
		IBinder retBinder = null;
		try {
			// Ibind
			Class class1 = Class.forName("android.os.ServiceManager");
			Method method = class1.getMethod("getService",
					new Class[] { java.lang.String.class });
			method.setAccessible(true);
			retBinder = (IBinder) method.invoke(class1,
					new Object[] { "iphonesubinfo" });
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retBinder;
	}*/

	public static void hook() {
		// HookUtil.invokeMethod(paramString1, paramString2, paramObject,
		// paramArrayOfClass, paramArrayOfObject)
		Log.i("hookiphone", "hook start---->");
		IBinder binder = HookUtil.getCacheBinder("iphonesubinfo");
		String classNameString = binder.getClass().getName();
		Log.i("hookiphone", "before hook binder class:"
				+ binder.getClass().getName());
		if (classNameString.equals("android.os.BinderProxy")) {
			HookIPhoneSubInfoBinder binderTest = new HookIPhoneSubInfoBinder(binder);
			HookUtil.addTosCache("iphonesubinfo", binderTest);
		}
		IBinder temp = HookUtil.getCacheBinder("iphonesubinfo");
		Log.i("hookiphone", "after hook binder class:"
				+ temp.getClass().getName());
		Log.i("hookiphone", "hook end---->");
	}
}
