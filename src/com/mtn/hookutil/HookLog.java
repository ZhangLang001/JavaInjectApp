package com.mtn.hookutil;

import android.util.Log;

public class HookLog {
public static final void d(Boolean out,String tag,String msg)
{
	if(out)
	{
		Log.d(tag, msg);
	}
}
}
