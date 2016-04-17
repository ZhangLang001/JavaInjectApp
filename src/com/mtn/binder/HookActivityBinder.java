package com.mtn.binder;

import java.io.FileDescriptor;

import com.mtn.hookutil.HookUtil;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class HookActivityBinder implements IBinder {

	IBinder binder;

	private static final String TAG="hookactivity";
	public HookActivityBinder(IBinder binder) {
		this.binder = binder;
	}

	@Override
	public void dump(FileDescriptor arg0, String[] arg1) throws RemoteException {
		// TODO Auto-generated method stub
		this.binder.dump(arg0, arg1);
	}

	@Override
	public void dumpAsync(FileDescriptor arg0, String[] arg1)
			throws RemoteException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
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
		Log.d(TAG,"arg0=======>"+arg0);
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.binder.unlinkToDeath(arg0, arg1);
	}

}
