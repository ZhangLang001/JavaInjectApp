package com.mtn.binder;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import com.mtn.hookutil.HookUtil;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.NeighboringCellInfo;
import android.text.AndroidCharacter;
import android.util.Log;
import android.widget.ArrayAdapter;

public class HookPhoneBinder implements IBinder {

	IBinder binder;

	public HookPhoneBinder(IBinder bb) {
		this.binder = bb;
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
		int getCellLocation = HookUtil.getStaticFiled(
				"com.android.internal.telephony.ITelephony$Stub",
				"TRANSACTION_getCellLocation");
		Log.i("hookphone","transact arg0:"+arg0);
		int getNeighboringCellInfo = HookUtil.getStaticFiled(
				"com.android.internal.telephony.ITelephony$Stub",
				"TRANSACTION_getNeighboringCellInfo");
		Log.i("hookphone","transact arg0:"+arg0);
		if(true)
		{
			Log.i("hookphone","transact arg0  **************return true");
			return true;
		}
		if (arg0 == getCellLocation) {
			Log.i("hookphone","getCellLocation:"+arg0);
			arg2.writeNoException();
			arg2.writeInt(0);
			return true;
		}else if(arg0==getNeighboringCellInfo){
			Log.i("hookphone","getNeighboringCellInfo:"+arg0);
			arg2.writeNoException();
			//arg2.writeInt(0);
			List<NeighboringCellInfo> _result=new ArrayList<NeighboringCellInfo>();
			arg2.writeTypedList(_result);
			return true;
		}
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		// TODO Auto-generated mthod stub
		return this.binder.unlinkToDeath(arg0, arg1);
	}

}
