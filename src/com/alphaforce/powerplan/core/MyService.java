package com.alphaforce.powerplan.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;

public class MyService extends Service{

	private String mStatus;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent it,int flags,int startId){

		mStatus = it.getStringExtra("status");
		if (mStatus.equals("start")){
			SharedPreferences phonenumSP = getApplicationContext()
					.getSharedPreferences("status", Context.MODE_WORLD_READABLE);  
	        Editor editor = phonenumSP.edit();  
	        
	        //putBoolean方法中的boolean值 由从数据库中的值替代
	        editor.putBoolean("sms",true);  
	        editor.putBoolean("phone", true);
	        editor.commit();  
		}else{
			SharedPreferences phonenumSP = getApplicationContext()
					.getSharedPreferences("status", Context.MODE_WORLD_READABLE);  
	        Editor editor = phonenumSP.edit();  
	        editor.putBoolean("sms",false);  
	        editor.putBoolean("phone", false);
	        editor.commit();  
		}
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
