package com.mtn.javainject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.mtn.binder.Hook;
import com.mtn.binder.HookActivity;
import com.mtn.binder.HookActivityThread;
import com.mtn.binder.HookIPhoneSubInfo;
import com.mtn.binder.HookLocation;

import dalvik.system.DexClassLoader;

import android.hardware.Camera.ShutterCallback;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button imeiButton;
	private Button imsiButton;
	private Button numberButton;
	private Button hookButton;
	private Button unhookButton;

	private Button notificationButton;
	TelephonyManager telephonyManager = null;
	// NotificationManager
	private final String APP_JAR = "jar";
	private final String DEX_NAME = "mtnbinder.jar";
	private final String SO_NAME = "libmtnbinder.so";
	File dexInternalStoragePath = null;
	File bindersoFilePATH = null;
	File optimizedDexOutputPath = null;// getDir(APP_JAR,Context.MODE_PRIVATE);
	LocationManager locationManager = null;
	LocalSocketTest localSocketTest = null;
	WifiManager  wifiManager;
	
	
	
	
	class WifiReceiver extends BroadcastReceiver { 
		
		

        public void onReceive(Context c, Intent intent) {

               // sb = new StringBuilder();
                //wifiManager.getScanResults();

               
               // mainText.setText(sb); 
        }
 }
	
	/** 
	 * @author wangli 快捷方式工具类 
	 */  
	public static class ShortCutUtils {  
	    /** 
	     * 添加当前应用的桌面快捷方式 
	     *  
	     * @param cx 
	     */  
	    public static void addShortcut(Context cx) {  
	        Intent shortcut = new Intent(  
	                "com.android.launcher.action.INSTALL_SHORTCUT");  
	  
	        Intent shortcutIntent = cx.getPackageManager()  
	                .getLaunchIntentForPackage(cx.getPackageName());  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);  
	        // 获取当前应用名称  
	        String title = null;  
	        try {  
	            final PackageManager pm = cx.getPackageManager();  
	            title = pm.getApplicationLabel(  
	                    pm.getApplicationInfo(cx.getPackageName(),  
	                            PackageManager.GET_META_DATA)).toString();  
	        } catch (Exception e) {  
	        }  
	        // 快捷方式名称  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);  
	        // 不允许重复创建（不一定有效）  
	        shortcut.putExtra("duplicate", false);  
	        // 快捷方式的图标  
	        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(cx,  
	                R.drawable.ic_launcher);  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);  
	  
	        cx.sendBroadcast(shortcut);  
	    }  
	  
	    /** 
	     * 删除当前应用的桌面快捷方式 
	     *  
	     * @param cx 
	     */  
	    public static void delShortcut(Context cx) {  
	        Intent shortcut = new Intent(  
	                "com.android.launcher.action.UNINSTALL_SHORTCUT");  
	  
	        // 获取当前应用名称  
	        String title = null;  
	        try {  
	            final PackageManager pm = cx.getPackageManager();  
	            title = pm.getApplicationLabel(  
	                    pm.getApplicationInfo(cx.getPackageName(),  
	                            PackageManager.GET_META_DATA)).toString();  
	        } catch (Exception e) {  
	        }  
	        // 快捷方式名称  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);  
	        Intent shortcutIntent = cx.getPackageManager()  
	                .getLaunchIntentForPackage(cx.getPackageName());  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);  
	        cx.sendBroadcast(shortcut);  
	    }  
	  
	    /** 
	     * 判断当前应用在桌面是否有桌面快捷方式 
	     *  
	     * @param cx 
	     */  
	    public static boolean hasShortcut(Context cx) {  
	        boolean result = false;  
	        String title = null;  
	        try {  
	            final PackageManager pm = cx.getPackageManager();  
	            title = pm.getApplicationLabel(  
	                    pm.getApplicationInfo(cx.getPackageName(),  
	                            PackageManager.GET_META_DATA)).toString();  
	        } catch (Exception e) {  
	        }  
	  
	        final String uriStr;  
	        if (android.os.Build.VERSION.SDK_INT < 8) {  
	            uriStr = "content://com.android.launcher.settings/favorites?notify=true";  
	        } else {  
	            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";  
	        }  
	        final Uri CONTENT_URI = Uri.parse(uriStr);  
	        final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,  
	                "title=?", new String[] { title }, null);  
	        if (c != null && c.getCount() > 0) {  
	            result = true;  
	        }  
	        return result;  
	    }  
	} 
	
	
	LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			Log.i("hooklocation", "onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Log.i("hooklocation", "onProviderEnabled");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.i("hooklocation", "onProviderDisabled");
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			Log.i("hooklocation",
					"onLocationChanged===>lat:" + location.getLatitude()
							+ "  alt:" + location.getAltitude());
		}
	};

	/*
	 * private void openGPSSettings() { LocationManager alm = (LocationManager)
	 * this .getSystemService(Context.LOCATION_SERVICE); if
	 * (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
	 * Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT) .show(); return; }
	 * 
	 * Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show(); Intent intent
	 * = new Intent(Settings.ACTION_SECURITY_SETTINGS);
	 * startActivityForResult(intent,0); //此为设置完成后返回到获取界面
	 * 
	 * }
	 */
	private void getLocation() {
		// 获取位置管理服务
		// LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		// HookLocation.hook();
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		// Criteria criteria = new Criteria();
		// criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		// criteria.setAltitudeRequired(false);
		// / criteria.setBearingRequired(false);
		// criteria.setCostAllowed(true);
		// criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		// String provider = locationManager.getBestProvider(criteria, true); //
		// 获取GPS信息
		// Location location = locationManager.getLastKnownLocation(provider);
		// // 通过GPS获取位置
		// updateToNewLocation(location);
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
		Location location = locationManager.getLastKnownLocation("gps");
		if (null != location) {
			Toast.makeText(
					this,
					"al:" + location.getAltitude() + "  la:"
							+ location.getLatitude(), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "location is null***********",
					Toast.LENGTH_LONG).show();
		}
	}

	@SuppressLint("NewApi")
	class ClickButton implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.getIMEI:
				
				WifiReceiver receiverWifi = new WifiReceiver(); 
	                registerReceiver(receiverWifi, new IntentFilter(
	 
	                                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	                 wifiManager.startScan(); 
				
				// HookIPhoneSubInfo.hook();
				//wifiManager.setWifiEnabled(true);
				///String idString = telephonyManager.getDeviceId();
				// Log.i("hookiphone", "getDeviceId(IMEI):" + idString);
				// getLocation();
				///Intent intent = new Intent();
				//intent.setAction("android.intent.action.VIEW");
				//Uri content_url = Uri.parse("http://www.cnblogs.com");
				//intent.setData(content_url);
				//startActivity(intent);
				break;
			case R.id.getIMSI:
				//TelephonyManager telephonyManager1 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				//String imsi = telephonyManager.getSubscriberId();
				// Log.i("hookiphone", "getSubscriberId(IMSI):" + imsi);

				break;
			case R.id.getNumber:
				// TelephonyManager telephonyManager2 = (TelephonyManager)
				// getSystemService(Context.TELEPHONY_SERVICE);
				String simOP = telephonyManager.getSimOperatorName();
				telephonyManager.listen(new PhoneStateListener(),
						PhoneStateListener.LISTEN_CELL_LOCATION);
				// String number = telephonyManager.getLine1Number();
				Log.i("hookiphone", "getSimOperatorName(number):" + simOP);
				break;
			case R.id.hook:
				// locationManager=(LocationManager)
				// getSystemService(Context.LOCATION_SERVICE);
				// /Location
				// location=locationManager.getLastKnownLocation("gps");
				// (null!=location)
				// {
				// Log.i("hooklocation","lal:"+location.getLatitude()+" al:"+location.getAltitude());
				// }else {
				// Log.i("hooklocation","null___________");
				// }
				Hook.hook();

				// HookActivityThread.hook();
				// CopyFile.callHook(dexInternalStoragePath.getAbsolutePath(),
				// optimizedDexOutputPath.getAbsolutePath(),
				// "com.mtn.binder.HookIPhoneSubInfo", "hook");
				// HookIPhoneSubInfo.hook();
				/*
				 * final File optimizedDexOutputPath = getDir(APP_JAR,
				 * Context.MODE_PRIVATE); try { DexClassLoader classLoader = new
				 * DexClassLoader( dexInternalStoragePath.getAbsolutePath(),
				 * optimizedDexOutputPath.getAbsolutePath(), null,
				 * getClassLoader()); // com.mtn.binder.HookIPhoneSubInfo
				 * 
				 * Class class1 = classLoader
				 * .loadClass("com.mtn.binder.HookIPhoneSubInfo"); Method method
				 * = class1.getMethod("hook", new Class[] {});
				 * method.invoke(class1, new Object[] {}); } catch
				 * (ClassNotFoundException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } catch (NoSuchMethodException e)
				 * { // TODO Auto-generated catch block e.printStackTrace(); }
				 * catch (IllegalArgumentException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch
				 * (IllegalAccessException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } catch (InvocationTargetException
				 * e) { // TODO Auto-generated catch block e.printStackTrace();
				 * }
				 */
				break;
			case R.id.unhook:
				localSocketTest.init();
				// Log.i("hookiphone","CopyFile.callHookTwo***********");
				// CopyFile.callHookTwo(dexInternalStoragePath.getAbsolutePath(),
				// optimizedDexOutputPath.getAbsolutePath());
				// getLocation();
				// Location
				// location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				// if(location!=null)
				// {
				// Log.i("hooklocation","lat:"+location.getLatitude()+"  alt:"+location.getAltitude());
				// }else {
				// Log.i("hooklocation","sss==========null");
				// }
				break;
			case R.id.notification:
				;
				Intent i = new Intent();
				i.setClass(MainActivity.this, MainActivity.class);
				// 一定要Intent.FLAG_ACTIVITY_NEW_TASK
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				// PendingIntent 是Intent的包装类
				PendingIntent contentIntent = PendingIntent.getActivity(
						MainActivity.this, 1, i,
						PendingIntent.FLAG_UPDATE_CURRENT);
				NotificationCompat.Builder ncb = new NotificationCompat.Builder(
						MainActivity.this);
				ncb.setTicker("第一个Notifiy");
				ncb.setAutoCancel(true);
				ncb.setContentIntent(contentIntent);
				ncb.setDefaults(Notification.DEFAULT_ALL);
				ncb.setContentTitle("hello Tby");
				ncb.setContentText("ContentText");
				ncb.setSmallIcon(R.drawable.ic_launcher);
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(1, ncb.build());

				Hook.hook();
				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HookActivity.hook();
		setContentView(R.layout.activity_main);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		localSocketTest = new LocalSocketTest();
		ShortCutUtils.addShortcut(this);
		wifiManager=(WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		PackageManager packageManager=getPackageManager();
		packageManager.getInstalledApplications(0);
		
		
		// HookLocation.hook();
		// HookIPhoneSubInfo.hook();
		// HookLocation.hook();
		// getLocation();
		/*
		 * InputStream inputStream = getResources().openRawResource(
		 * R.raw.mtnbinder); dexInternalStoragePath = new File( getDir(APP_JAR,
		 * Context.MODE_PRIVATE), DEX_NAME); if
		 * (!dexInternalStoragePath.exists()) { try {
		 * dexInternalStoragePath.createNewFile(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } } try {
		 * 
		 * FileOutputStream fileOutputStream = new FileOutputStream(
		 * dexInternalStoragePath); CopyFile.copyFile(inputStream,
		 * fileOutputStream); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * optimizedDexOutputPath = getDir(APP_JAR,Context.MODE_PRIVATE);
		 * 
		 * InputStream
		 * binderInputStream=getResources().openRawResource(R.raw.libmtnbinder);
		 * bindersoFilePATH=new File(getDir(APP_JAR,
		 * Context.MODE_PRIVATE),SO_NAME); if(!bindersoFilePATH.exists()) { try
		 * { bindersoFilePATH.createNewFile(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } try {
		 * FileOutputStream bindFileOutputStream=new
		 * FileOutputStream(bindersoFilePATH);
		 * CopyFile.copyFile(binderInputStream,bindFileOutputStream); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * System.load(bindersoFilePATH.getAbsolutePath());
		 */
		imeiButton = (Button) findViewById(R.id.getIMEI);
		imsiButton = (Button) findViewById(R.id.getIMSI);
		numberButton = (Button) findViewById(R.id.getNumber);
		hookButton = (Button) findViewById(R.id.hook);
		unhookButton = (Button) findViewById(R.id.unhook);
		notificationButton = (Button) findViewById(R.id.notification);

		imeiButton.setOnClickListener(new ClickButton());
		imsiButton.setOnClickListener(new ClickButton());
		numberButton.setOnClickListener(new ClickButton());
		hookButton.setOnClickListener(new ClickButton());
		unhookButton.setOnClickListener(new ClickButton());
		notificationButton.setOnClickListener(new ClickButton());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
