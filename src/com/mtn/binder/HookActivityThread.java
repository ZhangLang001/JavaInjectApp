package com.mtn.binder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class HookActivityThread {

	private static  Object  currentActivityThread=null;
	public  static void initCurrentActivityThreadObject()
	{
		Object localObject;
		try {
			localObject = Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
			 if(null!=localObject)
		     {
		    	 currentActivityThread=localObject;
		     }
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public static void hook()
	{
		initCurrentActivityThreadObject();
		Field field;
		try {
			field = Class.forName("android.app.ActivityThread").getDeclaredField("mH");
			field.setAccessible(true);
			Handler handler=(Handler) field.get(currentActivityThread);
			Field field2=Class.forName("android.os.Handler").getDeclaredField("mCallback");
			field2.setAccessible(true);
			Object oldCallBack=field2.get(handler);
			Log.i("hookactivitythread","hook activity thread start field2:"+field2.getName());
			Log.i("hookactivitythread","hook activity thread start oldcallback:"+oldCallBack);
			Log.i("hookactivitythread","hook activity thread before handler:"+handler.getClass().getName());
			Field application=Class.forName("android.app.ActivityThread").getDeclaredField("mInitialApplication");
			application.setAccessible(true);
			Context  context=(Context)application.get(currentActivityThread);
			Log.i("hookactivitythread","current app pkg is:"+context.getPackageName()+" ***************");
			HookHandlerCallback hookHandlerCallback=new HookHandlerCallback((Handler.Callback)oldCallBack,handler);
			Field field3=Class.forName("android.os.Handler").getDeclaredField("mCallback");
			field3.setAccessible(true);
			field3.set(handler,hookHandlerCallback);
			Field field4 = Class.forName("android.app.ActivityThread").getDeclaredField("mH");
			field4.setAccessible(true);
			Handler handler4=(Handler) field4.get(currentActivityThread);
			Log.i("hookactivitythread","hook activity thread after handler:"+handler4.getClass().getName());
			Log.i("hookactivitythread","hook activity thread end");
		} catch (NoSuchFieldException e) {
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
		}
		
		
	}
	public static Object  getActivityThreadHanderCallBack()
	{
		try {
			Field field=Class.forName("android.app.ActivityThread").getDeclaredField("mH");
			field.setAccessible(true);
			Handler handler=(Handler) field.get(currentActivityThread);
			Field field2=Class.forName("android.os.Handler").getDeclaredField("mCallback");
			field2.setAccessible(true);
			return field2.get(handler);
		} catch (NoSuchFieldException e) {
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
		}
		return null;
	}
}
