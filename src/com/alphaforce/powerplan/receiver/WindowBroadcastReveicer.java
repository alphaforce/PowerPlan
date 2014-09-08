package com.alphaforce.powerplan.receiver;

import java.text.SimpleDateFormat;

import com.alphaforce.powerplan.model.Plan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WindowBroadcastReveicer extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Intent it = new Intent();
		it.setAction("WindowService");
		it.putExtras(bundle);
		context.startService(it);
	}
}
