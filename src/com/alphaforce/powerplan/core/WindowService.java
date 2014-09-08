package com.alphaforce.powerplan.core;

import com.alphaforce.powerplan.model.Plan;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class WindowService extends Service{
	private static final String TAG = "WindowService";
	private AlarmManager mAlarmManager;
	private PendingIntent mScreenGuardStart;
	private PendingIntent mSmsPhoneStart;
	private PendingIntent mSmsPhoneEnd;
	private PendingIntent mScreenGuardEnd;
	private long mStartTime;
	private long mEndTime;
	
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
		
		Bundle bundle = intent.getExtras();
		Plan plan = (Plan) bundle.getSerializable("plan");
		mStartTime = plan.getStartTime();
		mEndTime = plan.getEndTime();
		Log.i(TAG, "" + mStartTime + "   "+ mEndTime);
		
		mAlarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        Intent it = new Intent();
        it.setAction("Service");
        it.putExtra("status", "start");
        mSmsPhoneStart = PendingIntent.getService(this, 0, it, 0);
        
        Intent it2 = new Intent();
        it2.setAction("com.alphaforce.powerplan.core.SCREEN_GUARD_SERVICE");
        it2.putExtra("status", "start");
        mScreenGuardStart = PendingIntent.getService(this, 1, it2, 0);
        
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, mStartTime, mSmsPhoneStart);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, mStartTime, mScreenGuardStart);
		
		Intent it3 = new Intent();
        it3.setAction("Service");
        it3.putExtra("status", "end");
        mSmsPhoneEnd = PendingIntent.getService(this, 2, it3, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, mEndTime, mSmsPhoneEnd);
		
		Intent it4 = new Intent();
        it4.setAction("com.alphaforce.powerplan.core.SCREEN_GUARD_SERVICE");
        it4.putExtra("status", "end");
        mScreenGuardEnd = PendingIntent.getService(this, 3, it4, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, mEndTime, mScreenGuardEnd);
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
