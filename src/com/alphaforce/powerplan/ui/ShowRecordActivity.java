package com.alphaforce.powerplan.ui;

import java.util.HashMap;
import java.util.List;

import com.alphaforce.powerplan.R;
import com.alphaforce.powerplan.sqllite.CallLogContextProvider;
import com.alphaforce.powerplan.sqllite.SMSLogContextProvider;
import com.alphaforce.powerplan.ui.adapter.CallLogAdapter;
import com.alphaforce.powerplan.ui.adapter.SMSLogAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ShowRecordActivity extends Activity implements OnClickListener{
   
	private CallLogContextProvider mCallLogContextProvider;
    private SMSLogContextProvider mMSMLogContextProvider;
    private ListView mCallLogListView;
    private ListView mMSMLogListView;
    private CallLogAdapter mCallLogAdapter;
    private SMSLogAdapter mMSMLogAdapter;
    private List<HashMap<String,Object>> data;
    final long currentTime = System.currentTimeMillis(); 
    private CallLogAsyncTask callLogAsyncTask;
    private MSMLogAsyncTask mSMLogAsyncTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_record);
		Button button_callLog = (Button) findViewById(R.id.bt_callLog);
		Button button_msmLog = (Button) findViewById(R.id.bt_msmLog);
		//final long currentTime = System.currentTimeMillis(); 
		
		button_callLog.setOnClickListener(this);
		button_msmLog.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_callLog:
			//mCallLogContextProvider = new CallLogDate(MainActivity.this);
			callLogAsyncTask = new CallLogAsyncTask();
			mCallLogListView = (ListView) findViewById(R.id.listview);
			callLogAsyncTask.execute();
			break;
			
		case R.id.bt_msmLog:
			mSMLogAsyncTask = new MSMLogAsyncTask();
			mMSMLogListView = (ListView) findViewById(R.id.listview);	
			mSMLogAsyncTask.execute();
			break;
		default:
			break;
		}
	}
private class CallLogAsyncTask extends AsyncTask {
		
		@Override
		protected Object doInBackground(Object... params) {
			mCallLogContextProvider = new CallLogContextProvider(ShowRecordActivity.this);
			System.out.println("--------------"+mCallLogContextProvider);
			data = mCallLogContextProvider.getCallLogDate(currentTime,2500);
			//System.out.println("data= " + data);
			return data;
		}
		
		@Override
		protected void onPostExecute(java.lang.Object result) {
			mCallLogAdapter = new CallLogAdapter(ShowRecordActivity.this,data);
			mCallLogListView.setAdapter(mCallLogAdapter);
		}
	}

	private  class MSMLogAsyncTask extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			mMSMLogContextProvider = new SMSLogContextProvider(ShowRecordActivity.this);
			 data = mMSMLogContextProvider.getMSMLogDate(currentTime,2500);
			return data;
		}
		@Override
		protected void onPostExecute(java.lang.Object result) {
			mMSMLogAdapter = new SMSLogAdapter(ShowRecordActivity.this,data);
			mMSMLogListView.setAdapter(mMSMLogAdapter);
			
		}
		
	}
} 
