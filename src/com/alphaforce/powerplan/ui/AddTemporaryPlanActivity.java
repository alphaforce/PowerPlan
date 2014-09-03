
package com.alphaforce.powerplan.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.alphaforce.powerplan.R;
import com.alphaforce.powerplan.model.Plan;
import com.alphaforce.powerplan.sqllite.PowerPlanDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddTemporaryPlanActivity extends Activity implements OnTouchListener {
	
	private EditText setStartTime;
	private EditText setEndTime;
	private Calendar calendar;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plan_add_temporary);
		
		Button btnSave = (Button) findViewById(R.id.input_save);
		BtnSaveListener(btnSave);
		
		Button btnCancel = (Button) findViewById(R.id.input_cancel);
		BtnCancelListener(btnCancel);
		
		setStartTime = (EditText) this.findViewById(R.id.input_begin_time_edit);   
        setStartTime.setOnTouchListener(this);
        
        setEndTime = (EditText) this.findViewById(R.id.input_end_time_edit);  
        setEndTime.setOnTouchListener(this); 
        
	}

	private void BtnCancelListener(Button btnCancel) {
		btnCancel.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				IntentToStartActivity();
			}
		});
	}

	private void BtnSaveListener(Button btnSave) {
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Plan planItem = new Plan();
				
				planItem.setAuthor("gaowei");
				planItem.setLongitude(123.0);
				planItem.setLatitude(23.0);
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(1001);
				statusList.add(1002);
				planItem.setStatus(statusList);
				
				EditText btnName = (EditText) findViewById(R.id.addPlanName);
				planItem.setName(btnName.getText().toString());
				
				EditText btnContent = (EditText) findViewById(R.id.planName);
				planItem.setContent(btnContent.getText().toString());
				
				EditText btnBegTime = (EditText) findViewById(R.id.input_begin_time_edit);
				Calendar calandarBegin = Calendar.getInstance();
//				long beginTime = beginEndTime(btnBegTime, calandarBegin);
				planItem.setStartTime(123122212l);
								
				EditText btnEndTime = (EditText) findViewById(R.id.input_end_time_edit);
				Calendar calandarEnd = Calendar.getInstance();
//				long endTime = beginEndTime(btnEndTime, calandarEnd);
				planItem.setEndTime(1234541464l);
				
				EditText btnAddress = (EditText) findViewById(R.id.input_plan_address);
				planItem.setAddress(btnAddress.getText().toString());
				
				PowerPlanDataSource planDataSource = PowerPlanDataSource.get(AddTemporaryPlanActivity.this);
				planDataSource.insertPlan(planItem);
				
				IntentToStartActivity();
			}

			private long beginEndTime(EditText editTime,
					Calendar calandarBegin) {
				String strTime = editTime.getText().toString();
				Date date = new Date(strTime);
//				int year = Integer.parseInt(strTime.substring(0, 4));
//				int month = Integer.parseInt(strTime.substring(5, 7));
//				int day = Integer.parseInt(strTime.substring(8, 10));
//				int hour = Integer.parseInt(strTime.substring(12, 13));
//				int minute = Integer.parseInt(strTime.substring(14, strTime.length()));
//				calandarBegin.set(year, month, day, hour, minute,0);
				
//				return calandarBegin.getTimeInMillis();
				return date.getTime();
			}			
		});
	}
	
	public void IntentToStartActivity() {
		Intent intent = new Intent();
		intent.setClass(AddTemporaryPlanActivity.this, StartActivity.class);
		startActivity(intent);
		finish();
	}
	
	public boolean onTouch(View v, MotionEvent event) {  
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	
            View view = View.inflate(this, R.layout.date_time_dialog, null);

        	builder = new AlertDialog.Builder(this);  
            datePicker = (DatePicker) view.findViewById(R.id.date_picker);  
            timePicker = (TimePicker) view.findViewById(R.id.time_picker);  
            builder.setView(view);  
  
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(calendar.get(Calendar.YEAR), 
            		calendar.get(Calendar.MONTH), 
            		calendar.get(Calendar.DAY_OF_MONTH), null); 
  
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);
  
            setDateTime(v, event, builder, datePicker, timePicker);  
        }
        return true;  
    }

	private void setDateTime(View v, MotionEvent event,
			AlertDialog.Builder builder, final DatePicker datePicker,
			final TimePicker timePicker) {
		if (v.getId() == R.id.input_begin_time_edit) {  
			setDateAndTime(event, builder, datePicker, timePicker, setStartTime);
		} 
		else if (v.getId() == R.id.input_end_time_edit) {
			setDateAndTime(event, builder, datePicker, timePicker, setEndTime);
		}
	}
	
	private void setDateAndTime(MotionEvent event, AlertDialog.Builder builder,
			final DatePicker datePicker, final TimePicker timePicker,
			final EditText dateTime) {
		final int inType = setStartTime.getInputType();  
		dateTime.setInputType(InputType.TYPE_NULL);  
		dateTime.onTouchEvent(event);
		dateTime.setInputType(inType);
		dateTime.setSelection(dateTime.getText().length());
		builder.setTitle("选取时间");
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
		    @Override
		    public void onClick(DialogInterface dialog, int which) {  
		    	StringBuffer sb = new StringBuffer();  
		        sb.append(String.format("%d-%02d-%02d",   
		                datePicker.getYear(),   
		                datePicker.getMonth() + 1,   
		                datePicker.getDayOfMonth()));
		        sb.append("  ")
		          .append(timePicker.getCurrentHour())
		          .append(":")
		          .append(timePicker.getCurrentMinute());
		        dateTime.setText(sb);
		        dialog.cancel();  
		    }
		});
		Dialog dialog = builder.create();
		dialog.show();
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
