package com.alphaforce.powerplan.core;


import com.alphaforce.powerplan.R;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ScreenGuardService extends Service{

	private View mView;
	private View mSubView ;
	private TelephonyManager mTeleManager;
	private WindowManager mWindowManager;
	private String mEmergeNumber;
	private MyBroadcastReceiver mReceiver;
	private MyPhoneCallListener mListen;
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
	public int onStartCommand(Intent intent, int flags, int startId){
		
		mStatus = intent.getStringExtra("status");
		if (mStatus.equals("start")){
			
			//注册广播监听拨打电话
			mReceiver = new MyBroadcastReceiver();
			registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
					
			//注册电话状态监听电话挂断
			mTeleManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			mListen = new MyPhoneCallListener();
			mTeleManager.listen(mListen, PhoneStateListener.LISTEN_CALL_STATE);
			initVIew();
		}
		else{
			mWindowManager.removeView(mView);
			unregisterReceiver(mReceiver);
			mTeleManager.listen(mListen, PhoneStateListener.LISTEN_NONE);
		}
		return START_STICKY;
	}
	
	@Override
	public void onDestroy(){
		unregisterReceiver(mReceiver);
		mTeleManager.listen(mListen, PhoneStateListener.LISTEN_NONE);
	}
	
	private void initVIew(){
		mWindowManager = (WindowManager) getSystemService("window");
		WindowManager.LayoutParams wmPara = new WindowManager.LayoutParams();
		wmPara.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		wmPara.format = 1;
		wmPara.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		
		LayoutInflater inflater = LayoutInflater.from(this);
		mView = inflater.inflate(R.layout.shield_view,null);
		mSubView = inflater.inflate(R.layout.call_panel,null);
		mSubView.setVisibility(View.GONE);
		((ViewGroup)mView).addView(mSubView);
		
		mEmergeNumber = "10086";		//需要接口
		TextView numText = (TextView) mView.findViewById(R.id.number); 
		numText.setText(mEmergeNumber);
		Button call = (Button) mView.findViewById(R.id.call_in);
		
		call.setOnClickListener(new CallEmergeContacts());
		mView.setOnClickListener(new PreporCall());
		mWindowManager.addView(mView, wmPara);
	}
	
	class CallEmergeContacts implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.CALL");
			intent.setData(Uri.parse("tel:" + mEmergeNumber));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			mSubView.setVisibility(View.GONE);
			mView.setVisibility(View.GONE);
		}
	}
	
	class PreporCall implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			mSubView.setVisibility(View.VISIBLE);
		}
	}
	
	class MyBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){ 
				 mSubView.setVisibility(View.GONE); 
				 mView.setVisibility(View.GONE); 
			 }
		}
	}
	
	class MyPhoneCallListener extends PhoneStateListener{
	    @Override
	    public void onCallStateChanged(int state, String incomingNumber){
	        switch (state){
	            case TelephonyManager.CALL_STATE_OFFHOOK:                  
	            break;
	            case TelephonyManager.CALL_STATE_RINGING:   
	            	mSubView.setVisibility(View.GONE); 
	            	mView.setVisibility(View.GONE); 
	            break;
	            case TelephonyManager.CALL_STATE_IDLE:
	            	mSubView.setVisibility(View.VISIBLE); 
	            	mView.setVisibility(View.VISIBLE); 
	            break;
	        }
	        super.onCallStateChanged(state, incomingNumber);
	    }
	}
}
