package com.mtn.binder;

import java.io.FileDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mtn.hookutil.HookUtil;

import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class HookNotificationBinder implements IBinder {

	private static final String TAG = "hooknotification";
	private IBinder binder;

	public HookNotificationBinder(IBinder bbBinder) {
		// TODO Auto-generated constructor stub
		this.binder = bbBinder;
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
		try {
			Class class1 = Class.forName("android.os.IBinder");
			Method method = class1.getDeclaredMethod("dumpAsync", new Class[] {
					FileDescriptor.class, String[].class });
			method.setAccessible(true);
			method.invoke(this.binder, new Object[] { arg0, arg1 });
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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
		int enqueueNotificationWithTag = HookUtil.getStaticFiled(
				"android.app.INotificationManager$Stub",
				"TRANSACTION_enqueueNotificationWithTag");
		Log.d(TAG, "arg0===>" + arg0);
		if (arg0 == enqueueNotificationWithTag) {
			Log.d(TAG, "enqueueNotificationWithTag:" + arg0+" arg1 pos:"+arg1.dataPosition());
 
			arg1.setDataPosition(0);
			java.lang.String _arg0;
			if(Build.VERSION.SDK_INT > 8)
		    {
				arg1.readInt();
				arg1.readString();
		     
		    }else {
		    	arg1.readString();
			}
		    
			_arg0 = arg1.readString();
			java.lang.String _arg1;
			_arg1 = arg1.readString();
			int _arg2;
			_arg2 = arg1.readInt();
			android.app.Notification _arg3;
			if ((0 != arg1.readInt())) {
				_arg3 = android.app.Notification.CREATOR.createFromParcel(arg1);
			} else {
				_arg3 = null;
			}
			int[] _arg4;
			_arg4 = arg1.createIntArray();
			//arg1.setDataPosition(0);
			Log.d(TAG, "arg0:"+arg0+" _arg0:"+_arg0);
			if(null!=_arg3)
			{
			Log.d(TAG, "arg0:"+arg0+" _arg0:"+_arg0+" _arg1:"+_arg1+" _arg2:"+_arg2+"  notification:"+_arg3.tickerText);
			}else {
				Log.d(TAG, "arg0:"+arg0+" _arg0:"+_arg0+" _arg1:"+_arg1+" _arg2:"+_arg2+" notification:null");
			}
			//this.enqueueNotificationWithTag(_arg0, _arg1, _arg2, _arg3, _arg4);
			//arg2.writeNoException();
			//arg2.writeIntArray(_arg4);
			//return true;

		}
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.binder.unlinkToDeath(arg0, arg1);
	}

}
