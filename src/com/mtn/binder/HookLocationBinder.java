package com.mtn.binder;

import java.io.FileDescriptor;

import com.mtn.hookutil.HookUtil;

import android.R.integer;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class HookLocationBinder implements IBinder {

	private IBinder binder;

	HookLocationBinder(IBinder iBinder) {
		// TODO Auto-generated constructor stub
		this.binder = iBinder;
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

		Log.i("hooklocation", "transact**********:" + arg0);
		// if(true)
		// {
		// Log.i("hooklocation","transact**********return true");
		// return true;
		// }
		int getLastKnowLocation = HookUtil.getStaticFiled(
				"android.location.ILocationManager$Stub",
				"TRANSACTION_getLastKnownLocation");
		int requestLocationUpdates = HookUtil.getStaticFiled(
				"android.location.ILocationManager$Stub",
				"TRANSACTION_requestLocationUpdates");
		int requestLocationUpdatesPI = HookUtil.getStaticFiled(
				"android.location.ILocationManager$Stub",
				"TRANSACTION_requestLocationUpdatesPI");
		int addProximityAlert = HookUtil.getStaticFiled(
				"android.location.ILocationManager$Stub",
				"TRANSACTION_addProximityAlert");
		int addGpsStatusListener = HookUtil.getStaticFiled(
				"android.location.ILocationManager$Stub",
				"TRANSACTION_addGpsStatusListener");
		Log.i("hooklocation", "********transact arg0:" + arg0);
		if (arg0 == getLastKnowLocation) {
			// Parcel data=arg1;
			// Parcel reply=arg2;
			Log.i("hooklocation", "getLastKnowLocation**********:" + arg0);
			arg2.setDataPosition(0);
			arg1.setDataPosition(0);
			arg1.enforceInterface("android.location.ILocationManager");
			java.lang.String _arg0;
			_arg0 = arg1.readString();
			Log.i("hooklocation", "_arg0:" + _arg0);
			android.location.Location _result = new Location(_arg0);
			_result.setLatitude(1111111111111111.12);
			_result.setAltitude(2222222222222222.12);
			arg2.writeNoException();
			if (_result != null) {
				arg2.writeInt(1);
				_result.writeToParcel(arg2,
						android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
				Log.i("hooklocation", "_result:" + _result);
			} else {
				Log.i("hooklocation", "_result:null");
				arg2.writeInt(0);
			}
			//Log.i("hooklocation", "arg2:" + arg);
			arg2.setDataPosition(0);
			return true;
			/*
			 * Log.i("hooklocation", "getLastKnowLocation**********:" + arg0);
			 * arg2.writeNoException(); arg2.writeInt(0); return true;
			 */
		} else if (arg0 == requestLocationUpdates) {
			Log.i("hooklocation", "requestLocationUpdates*******:" + arg0);
			//arg2.writeNoException();
		//	arg2.writeInt(0);
			//return true;
			// Log.i("hooklocation","return true");
			// return true;
		} else if (arg0 == requestLocationUpdatesPI) {
			Log.i("hooklocation", "requestLocationUpdatesPI*****:" + arg0);
		} else if (arg0 == addProximityAlert)

		{
			Log.i("hooklocation", "addProximityAlert************:" + arg0);
		} else if (arg0 == addGpsStatusListener) {
			Log.i("hooklocation", "addGpsStatusListener*********:" + arg0);
		}
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	
	
	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.binder.unlinkToDeath(arg0, arg1);
	}

}
