package com.mtn.binder;

import java.io.FileDescriptor;

import com.mtn.hookutil.HookUtil;

import android.R.bool;
import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class HookIPhoneSubInfoBinder implements IBinder {

	private static final String A = "com.android.internal.telephony.IPhoneSubInfo";
	private IBinder binder = null;

	public HookIPhoneSubInfoBinder(IBinder test) {

		binder = test;
		if (null == test) {
			Log.i("hookiphone", "BinderTest constructor:null");
		} else {
			Log.i("hookiphone", "BinderTest constructor:" + test.hashCode());
		}
	}

	@Override
	public void dump(FileDescriptor arg0, String[] arg1) throws RemoteException {
		// TODO Auto-generated method stub
		this.binder.dump(arg0, arg1);
	}

	public void myDumpAsync(FileDescriptor arg0, String[] arg1) {
		HookUtil.invokeMethod("android.os.IBinder", "dumpAsync", this.binder,
				new Class[] { FileDescriptor.class, java.lang.String.class },
				new Object[] { arg0, arg1 });
	}

	@Override
	public String getInterfaceDescriptor() throws RemoteException {
		// TODO Auto-generated method stub
		return this.binder.getInterfaceDescriptor();
	}

	@Override
	public boolean isBinderAlive() {
		// TODO Auto-generated method stub
		return this.binder.isBinderAlive();
	}

	@Override
	public void linkToDeath(DeathRecipient arg0, int arg1)
			throws RemoteException {
		// TODO Auto-generated method stub
		this.binder.linkToDeath(arg0, arg1);
	}

	@Override
	public boolean pingBinder() {
		// TODO Auto-generated method stub
		return this.binder.pingBinder();
	}

	@Override
	public IInterface queryLocalInterface(String arg0) {
		// TODO Auto-generated method stub
		return this.binder.queryLocalInterface(arg0);
	}

	@Override
	public boolean transact(int arg0, Parcel arg1, Parcel arg2, int arg3)
			throws RemoteException {
		// TODO Auto-generated method stub
		Log.i("hookiphone", "transact====arg0:" + arg0 + " arg3:" + arg3);
		
		Boolean isHook = HookUtil.getModel();
		// if (isHook) {
		int getDeviceId = HookUtil.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getDeviceId");
		int getSubscriberId = HookUtil.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getSubscriberId");
		int getLine1Number = HookUtil.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getLine1Number");
		if (arg0 == getDeviceId) {
			Log.i("hookiphone", "transact getDeviceId:" + getDeviceId);
			arg2.writeNoException();
			arg2.writeString("356951040706073");
			return true;
		}
		if (arg0 == getLine1Number) {
			//Log.i("hookiphone", "transact  before getLine1Number  arg2.readString():" + arg2.readString());
			arg2.writeNoException();
			//arg2.setDataPosition(0);
			arg2.writeString("00000000000");
			//Log.i("hookiphone", "transact  after getLine1Number  arg2.readString():" + arg2.readString());
			Log.i("hookiphone", "transact getLine1Number:" + getLine1Number);
			return true;
			
		}
		if (arg0 == getSubscriberId) {
			Log.i("hookiphone", "transact getSubscriberId:" + getSubscriberId);
			arg2.writeNoException();
			arg2.writeString("356951040706073");
			return true;
		} 
		
		//if(arg0==get)

		// } else {
		// return this.binder.transact(arg0, arg1, arg2, arg3);
		// }
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.binder.unlinkToDeath(arg0, arg1);
	}

	@Override
	public void dumpAsync(FileDescriptor fd, String[] args)
			throws RemoteException {
		// TODO Auto-generated method stub

		// com.ijinshan.hookutil.b.a(KSCONST.decrypt("android.os.IBinder"),
		// KSCONST.decrypt("dumpAsync"), this.b, new Class[] {
		// FileDescriptor.class, [Ljava.lang.String.class },
		// new Object[] { paramFileDescriptor, paramArrayOfString });
		HookUtil.invokeMethod("android.os.IBinder", "dumpAsync", this.binder,
				new Class[] { FileDescriptor.class, String[].class },
				new Object[] { fd, args });
	}
}
