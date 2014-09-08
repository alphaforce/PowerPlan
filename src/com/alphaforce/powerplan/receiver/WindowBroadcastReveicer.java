package com.alphaforce.powerplan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WindowBroadcastReveicer extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("gaowei", intent.getSerializableExtra("plan").toString());
		context.startService(new Intent("WindowService"));
	}
	
}
