package com.alphaforce.powerplan.core;

import java.lang.reflect.Method;  
import com.android.internal.telephony.ITelephony;  
import android.app.Service;  
import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.content.SharedPreferences;  
import android.telephony.TelephonyManager;  
import android.util.Log;  

public class PhoneStatReceiver extends BroadcastReceiver{
    String TAG = "tag";  
    TelephonyManager telMgr;  
    
    @Override  
    public void onReceive(Context context, Intent intent) {  
    	SharedPreferences sp = context.getSharedPreferences("status", Context.MODE_WORLD_READABLE);
		boolean stats = sp.getBoolean("phone", false);
    	if (stats){
	        telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);  
	        switch (telMgr.getCallState()) {  
	            case TelephonyManager.CALL_STATE_RINGING:  
	                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);   
	                if (!number.equals("13691762150")){
	                	endCall();  
	                }
	                break;  
	            case TelephonyManager.CALL_STATE_OFFHOOK:                                 
	                break;  
	            case TelephonyManager.CALL_STATE_IDLE:                                 
	                break;  
	        }
        }
    }
    
    private void endCall(){  
        Class<TelephonyManager> c = TelephonyManager.class;           
        try {  
        	//反射找getITelephony方法
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);  
            getITelephonyMethod.setAccessible(true);  
            ITelephony iTelephony = null;  
            Log.e(TAG, "End call.");  
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr, (Object[]) null);  
            iTelephony.endCall();  
        }  
        catch (Exception e){  
            Log.e(TAG, "Fail to answer ring call.", e);  
        }
    }
}
