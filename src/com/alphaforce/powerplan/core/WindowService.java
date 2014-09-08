package com.alphaforce.powerplan.core;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WindowService extends Service{

	private AlarmManager mAlarmManager;
	private PendingIntent mScreenGuardStart;
	private PendingIntent mSmsPhoneStart;
	private PendingIntent mSmsPhoneEnd;
	private PendingIntent mScreenGuardEnd;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		
		
		Log.i("denghanmin", "WindowService onStartCommand");
		mAlarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        Intent it = new Intent();
        it.setAction("Service");
        it.putExtra("status", "start");
        mSmsPhoneStart = PendingIntent.getService(this, 0, it, 0);
        
        Intent it2 = new Intent();
        it2.setAction("com.alphaforce.powerplan.core.SCREEN_GUARD_SERVICE");
        it2.putExtra("status", "start");
        mScreenGuardStart = PendingIntent.getService(this, 1, it2, 0);
        
		//startTime是开始时间 endTime是结束时间
		long startTime = System.currentTimeMillis() + 2000;
		long endTime = System.currentTimeMillis() + 10000;
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, startTime, mSmsPhoneStart);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, startTime, mScreenGuardStart);
		
		Intent it3 = new Intent();
        it3.setAction("Service");
        it3.putExtra("status", "end");
        mSmsPhoneEnd = PendingIntent.getService(this, 2, it3, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, endTime, mSmsPhoneEnd);
		
		Intent it4 = new Intent();
        it4.setAction("com.alphaforce.powerplan.core.SCREEN_GUARD_SERVICE");
        it4.putExtra("status", "end");
        mScreenGuardEnd = PendingIntent.getService(this, 3, it4, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, endTime, mScreenGuardEnd);
		return START_STICKY;
	}
	
	@Override
	public boolean onUnbind(Intent it){
		return true;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}	
}
