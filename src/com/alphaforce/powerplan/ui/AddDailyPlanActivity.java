package com.alphaforce.powerplan.ui;

import com.example.testdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddDailyPlanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan_add_daily);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_temporory, menu);
		return true;
	}

}