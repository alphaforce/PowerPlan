package com.alphaforce.powerplan.ui.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.alphaforce.powerplan.R;
import com.alphaforce.powerplan.model.Plan;
import com.alphaforce.powerplan.sqllite.PowerPlanDataSource;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.PlanCursor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlanSimpleAdapter extends BaseAdapter{
	
	private List<Plan> mListPlan = new ArrayList<Plan>();
	private Context mContext;
	private LayoutInflater layoutInflater;
	public PlanSimpleAdapter(Context context,List<Plan> listPlan) {
		this.mContext = context;
		layoutInflater = LayoutInflater.from(context);
		this.mListPlan = listPlan;
	}
	@Override
	public int getCount() {
		return mListPlan.size();
	}

	@Override
	public Object getItem(int position) {
		return mListPlan.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.plan_item_simple, null);
		}
		
		TextView itemBeing = (TextView) convertView.findViewById(R.id.simple_begin_time);
		TextView itemEnd = (TextView) convertView.findViewById(R.id.simple_end_time);
		TextView itemContent = (TextView) convertView.findViewById(R.id.simple_content);
		
		Calendar calendar = Calendar.getInstance();
		Plan plan = new Plan();
		PowerPlanDataSource planItem = PowerPlanDataSource.get(mContext);
		PlanCursor planCursor = planItem.queryAllPlans();
		
		planCursor.moveToPosition(position);
		plan = planCursor.getPlan();
						
		calendar.setTimeInMillis(plan.getStartTime());
		String strBeing = CalendarLongToString(calendar);
		itemBeing.setText(strBeing);
		
		calendar.setTimeInMillis(plan.getEndTime());
		String strEnd = CalendarLongToString(calendar);
		itemEnd.setText(strEnd);
		
		itemContent.setText(plan.getContent());
		
		notifyDataSetChanged();
		
		return convertView;
	}
	
	private String CalendarLongToString(Calendar calendar) {
		String strCalendar = calendar.get(Calendar.YEAR)+"-"+
				(calendar.get(Calendar.MONTH) + 1) + "-"+
				calendar.get(Calendar.DAY_OF_MONTH)+" "+
				calendar.get(Calendar.HOUR_OF_DAY)+":"+
				calendar.get(Calendar.MINUTE);
		return strCalendar;
	}
	
	public void remove(int position) 
	{
		mListPlan.remove(position);
		notifyDataSetChanged();		
	}
}
