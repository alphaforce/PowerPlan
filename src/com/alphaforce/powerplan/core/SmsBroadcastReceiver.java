package com.alphaforce.powerplan.core;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsBroadcastReceiver extends BroadcastReceiver {

	private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		mContext = context;
		@SuppressWarnings("deprecation")
		SharedPreferences sp = context.getSharedPreferences("status", Context.MODE_WORLD_READABLE);
		boolean stats = sp.getBoolean("sms", false);
		if (stats && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras();
			if (bundle != null){
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i = 0;i < messages.length;i++){
                    byte[] pdu = (byte[])pdus[i];
                    messages[i] = SmsMessage.createFromPdu(pdu);
                }
                for(SmsMessage msg:messages){
                    String content = msg.getMessageBody();
                    String sender = msg.getOriginatingAddress();
                    insertSMStoDB(sender,content);
                }
            }
			
			// 不再往下传播；
			abortBroadcast();
		}
	}
	
	 public void insertSMStoDB(String number,String text)
	 {
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        //阅读状态
        values.put("read", 0);
        //1为收 2为发
        values.put("type", 1);
        values.put("address", number);
        values.put("body", text);
        mContext.getContentResolver().insert(Uri.parse("content://sms"),values);
	 }
}
