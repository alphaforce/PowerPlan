package com.alphaforce.powerplan.ui;

import com.example.testdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SettingActivity extends Activity {
	
	private String[] shield_options = {"短信", "电话", "网络", "应用"};
	private boolean[] shield_boolean = {true, true, false, false};
	private boolean[] shield_ok = {true, true, false, false};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_activity);
		
		Button btnSetting = (Button) findViewById(R.id.btnSetting); 
		btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, StartActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnOption = (Button) findViewById(R.id.model_option); 
		btnOption.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SettingActivity.this);
				alertBuilder.setTitle("屏蔽选项")
					.setIcon(R.drawable.ic_launcher)
					.setMultiChoiceItems(shield_options, shield_boolean, new OnMultiChoiceClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							shield_boolean[which] = isChecked;
						}
				});
				
				alertBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialog, int pos){
						for(int i=0; i<shield_boolean.length; ++i){
							shield_ok[i] = shield_boolean[i];
						}
					}
				});
				alertBuilder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialog, int pos){
						for(int i=0; i<shield_ok.length; ++i){
							shield_boolean[i] = shield_ok[i];
						}
					}
				});
				alertBuilder.create();
				alertBuilder.show();
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}
}