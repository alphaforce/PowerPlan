package com.alphaforce.powerplan.ui;

import com.alphaforce.powerplan.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DisplayPlanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_plan);		
		
		Button btnReturn = (Button) findViewById(R.id.btnReturn);
		btnReturnListener(btnReturn);
	}

	private void btnReturnListener(Button btnReturn) {
		btnReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DisplayPlanActivity.this, StartActivity.class);
				startActivity(intent);
			}
		});
	}

}
