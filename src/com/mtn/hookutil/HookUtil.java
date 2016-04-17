package com.mtn.hookutil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.os.Build;
import android.os.IBinder;

public class HookUtil {

	private static final Boolean DEBUG = true;
	private static final String TAG = "HookUtil";

	public static IBinder getCacheBinder(String name) {

		IBinder retBinder = null;
		try {
			// Ibind
			Class class1 = Class.forName("android.os.ServiceManager");
			Method method = class1.getDeclaredMethod("getService",
					new Class[] { java.lang.String.class });
			method.setAccessible(true);
			retBinder = (IBinder) method.invoke(class1, new Object[] { name });
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "NoSuchMethodException:" + e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "NoSuchMethodException:" + e.toString());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "IllegalArgumentException:" + e.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "IllegalAccessException:" + e.toString());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "InvocationTargetException:" + e.toString());
		}
		return retBinder;

	}

	public static Boolean addTosCache(String paramString, IBinder paramIBinder) {
		Boolean retBoolean = false;
		try {
			Field localField = Class.forName("android.os.ServiceManager")
					.getDeclaredField("sCache");
			localField.setAccessible(true);
			((HashMap<String, IBinder>) localField.get(null)).put(paramString,
					paramIBinder);
			retBoolean = true;
			return retBoolean;
		} catch (Exception e) {
			HookLog.d(DEBUG, TAG, "Exception:" + e.toString());
		}
		return retBoolean;
	}

	public static Boolean getModel() {
		String str1 = Build.MODEL;
		if (null != str1) {
			String str2 = str1.toUpperCase();
			if ((str2.equals("XT800")) || (str2.equals("XT800+"))
					|| (str2.equals("XT806")) || (str2.equals("XT882")))
				return true;
		}
		return false;
	}

	public static Object getFiled(String p1, String p2, Object obj) {

		try {
			Field field = Class.forName(p1).getDeclaredField(p2);
			field.setAccessible(true);
			Object localObject = field.get(obj);
			return localObject;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "NoSuchFieldException:" + e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "ClassNotFoundException:" + e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "IllegalArgumentException:" + e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HookLog.d(DEBUG, TAG, "IllegalAccessException:" + e);
		}
		return null;
	}

	public static void setStaticFiled(String className, String filedName,
			Object p1, Object p2) {
		try {
			Class class1 = Class.forName(className);
			Field field = class1.getDeclaredField(filedName);
			field.setAccessible(true);
			Object object = field.get(p1);
			if (null != object) {
				field.set(p1, p2);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getStaticFiled(String parm1, String parm2) {
		try {
			int ret = ((Integer) getFiled(parm1, parm2, null)).intValue();
			return ret;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return -1;
	}

	public static Object invokeMethod(String paramString1, String paramString2,
			Object paramObject, Class[] paramArrayOfClass,
			Object[] paramArrayOfObject) {
		try {
			Method localMethod = Class.forName(paramString1).getDeclaredMethod(
					paramString2, paramArrayOfClass);
			localMethod.setAccessible(true);
			Object localObject2 = localMethod.invoke(paramObject,
					paramArrayOfObject);
			return localObject2;
		} catch (InvocationTargetException localInvocationTargetException) {
			HookLog.d(DEBUG,TAG,"InvocationTargetException:"+localInvocationTargetException.toString());
			return null;
		} catch (ClassNotFoundException localClassNotFoundException) {
			localClassNotFoundException.printStackTrace();
			HookLog.d(DEBUG,TAG,"ClassNotFoundException:"+localClassNotFoundException.toString());
			return null;
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
			HookLog.d(DEBUG,TAG,"SecurityException:"+localSecurityException.toString());
			return null;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			localNoSuchMethodException.printStackTrace();
			HookLog.d(DEBUG,TAG,"NoSuchMethodException:"+localNoSuchMethodException.toString());
			return null;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
			HookLog.d(DEBUG,TAG,"IllegalArgumentException:"+localIllegalArgumentException.toString());
			return null;
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
			HookLog.d(DEBUG,TAG,"IllegalAccessException:"+localIllegalAccessException.toString());
		}
		return null;
	}

}
