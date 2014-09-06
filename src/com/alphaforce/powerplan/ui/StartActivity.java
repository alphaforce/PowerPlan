package com.alphaforce.powerplan.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alphaforce.powerplan.R;
import com.alphaforce.powerplan.model.Plan;
import com.alphaforce.powerplan.sqllite.PowerPlanDataSource;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.PlanCursor;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.CursorAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class StartActivity extends Activity {
	
	private String[] planNames = {"计划1","计划2"};
	private String[] planplaces = {"地点1","地点2"};
	private String[] planModels = {"模式1","模式2"};
	private String[] planBegTimes = {"开始1","开始2"};
	private String[] planEndTimes = {"结束1","结束2"};
	private String[] planContents = {"内容1","内容2"};
	private ListView listview;
	private SimpleAdapter adapter;
	private PopupWindow popWinsowPlan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_activity);
				
		addListViewItem();//显示列表内容
		planOnclickListener();//短按
		planOnLongclickListener();//长按

		Button simple_model = (Button) findViewById(R.id.plan_simple);//简易显示
		btnSimpleListener(simple_model);
		
		Button complete_model = (Button) findViewById(R.id.plan_complete);//完整显示
		btnCompleteListener(complete_model);
		
		Button mainSetting = (Button) findViewById(R.id.mainSetting);//设定
		btnSetListener(mainSetting);
				
		Button btnAdd = (Button) findViewById(R.id.plan_add);//添加计划
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				View root = getLayoutInflater().inflate(R.layout.popup_addplan,null);
				
				DisplayMetrics metric = new DisplayMetrics();
		        getWindowManager().getDefaultDisplay().getMetrics(metric);
		        int width = metric.widthPixels;
		        int height = metric.heightPixels;
		        
		        if(StartActivity.this.getResources().getConfiguration().orientation
		        		== Configuration.ORIENTATION_PORTRAIT)
		        {
		        	popWinsowPlan = new PopupWindow(root,width*3/10,height/6,true);
		            popWinsowPlan.setBackgroundDrawable(new BitmapDrawable());
			        popWinsowPlan.setOutsideTouchable(true);
			        popWinsowPlan.setFocusable(true);
			        popWinsowPlan.showAsDropDown(root,width/20,height*3/4);
		        }
		        if(StartActivity.this.getResources().getConfiguration().orientation
		        		== Configuration.ORIENTATION_LANDSCAPE)
		        {
		        	popWinsowPlan = new PopupWindow(root,height*3/10,width/6,true);
		            popWinsowPlan.setBackgroundDrawable(new BitmapDrawable());
			        popWinsowPlan.setOutsideTouchable(true);
			        popWinsowPlan.setFocusable(true);
			        popWinsowPlan.showAsDropDown(root,height/5,width*3/10);
		        }
		        //日常计划
				root.findViewById(R.id.popup_daily).setOnClickListener(
						new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(StartActivity.this,AddDailyPlanActivity.class);
						startActivity(intent);
						popWinsowPlan.dismiss();
					}
				});
				//临时计划
				root.findViewById(R.id.popup_tempo).setOnClickListener(
						new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(StartActivity.this,AddTemporaryPlanActivity.class);
						startActivity(intent);
						popWinsowPlan.dismiss();
					}
				});
			}
		});
		
	}

	protected void onListItemClick(ListView arg0, View arg1, int arg2, long arg3){
		Intent intent = new Intent();
		intent.setClass(StartActivity.this, AddDailyPlanActivity.class);
		startActivity(intent);;
	}
	
	private void planOnclickListener() {
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, DisplayPlanActivity.class);
				startActivity(intent);
			}
		});
	}

	private void planOnLongclickListener() {
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {				
				Builder alertDialog = new AlertDialog.Builder(StartActivity.this);
				alertDialog.setTitle("当前计划");
				alertDialog.setMessage("");
				alertDialog.setIcon(R.id.action_settings);
				alertDialog.setPositiveButton("取消",null);
				alertDialog.setNegativeButton("删除", 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialog, int pos){
						//从数据库删除数据;
					}
				});
				alertDialog.setNeutralButton("编辑", 
						new DialogInterface.OnClickListener() {   
                    public void onClick(DialogInterface dialog, int which) {   
                    	Intent intent = new Intent();
        				intent.setClass(StartActivity.this, AddDailyPlanActivity.class);
        				startActivity(intent);   
                 	}   	
				});   
				alertDialog.create(); 
			    alertDialog.show();
				return true;
			}
		});
	}

	private void btnSetListener(Button mainSetting) {
		mainSetting.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void btnSimpleListener(Button btn) {
		btn.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				addSimpleListViewItem();
			}
		});
	}
	
	private void btnCompleteListener(Button btn) {
		btn.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				addListViewItem();
			}
		});
	}

	public void addSimpleListViewItem() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for(int i=0; i<planBegTimes.length; ++i)
		{
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("times1", planBegTimes[i]);
			listItem.put("times2", planEndTimes[i]);
			listItem.put("contents", planContents[i]);
			lists.add(listItem);
		}
		adapter = new SimpleAdapter(this, lists, 
				R.layout.plan_item_simple, 
				new String[] {"times1","times2","contents"},
				new int[] {R.id.begin_time_simple,R.id.end_time_simple,R.id.content_simple}); 
		listview = (ListView) this.findViewById(R.id.mylist);
		listview.setAdapter(adapter);
	}
	
	@SuppressWarnings("deprecation")
	public void addListViewItem() {
//		Plan plan = new Plan();
//		PowerPlanDataSource planItem = PowerPlanDataSource.get(this);
//		PlanCursor planCursor = planItem.queryAllPlans();
//		
//		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
//		while(planCursor.moveToFirst())
//		{
//			plan = planCursor.getPlan();
//			Map<String, Object> listItem = new HashMap<String, Object>();
//			listItem.put("names", plan.getName());
//			listItem.put("places", plan.getAddress());
//			listItem.put("authers", plan.getAuthor());
//			listItem.put("beginTimes", plan.getStartTime());
//			listItem.put("endTimes", plan.getEndTime());
//			listItem.put("contents", plan.getContent());
//			lists.add(listItem);
//		}
//				
//		adapter = new SimpleCursorAdapter(
//				StartActivity.this, 
//				R.layout.plan_item_entirely, 
//				planCursor, 
//				new String[]{"names","places","authers",
//						"beginTimes","endTimes","contents"}, 
//				new int[]{R.id.item_name, R.id.item_place, R.id.item_model,
//						R.id.item_begin_time, R.id.item_end_time, R.id.item_content});
//		
		
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for(int i=0; i<planNames.length; ++i)
		{
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("names", planNames[i]);
			listItem.put("places", planplaces[i]);
			listItem.put("times1", planBegTimes[i]);
			listItem.put("times2", planEndTimes[i]);
			listItem.put("contents", planContents[i]);
			listItem.put("models", planModels[i]);
			lists.add(listItem);
		}
		adapter = new SimpleAdapter(this, lists, 
				R.layout.plan_item_entirely, 
				new String[] {"names","places","times1","times2",
					"contents","models"},
				new int[]{R.id.item_name, R.id.item_place, R.id.item_model,
					R.id.item_begin_time, R.id.item_end_time, R.id.item_content});
		listview = (ListView) this.findViewById(R.id.mylist);
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch(item.getItemId()){
		case R.id.about_software:
			intent.setClass(StartActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.para_setting:
			intent.setClass(StartActivity.this, SettingActivity.class);
			startActivity(intent);
			break;		
		}
		return true;
	}
	
	
}
