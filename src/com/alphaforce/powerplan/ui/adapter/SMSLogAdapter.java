package com.alphaforce.powerplan.ui.adapter;

import java.util.HashMap;
import java.util.List;

import com.alphaforce.powerplan.R;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SMSLogAdapter extends BaseAdapter{

	private Context mContext;
	private	List<HashMap<String,Object>> data;
	public SMSLogAdapter(Context context,List<HashMap<String,Object>> data) {
		this.mContext = context;
		this.data = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int pos) {
		
		return data.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup viewGroup) {
		MSMHolder mHolder;
		if (view == null) {
			mHolder = new MSMHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.sms_log_list,null);
			mHolder.bodyTextView = (TextView) view.findViewById(R.id.body);
			mHolder.nameTextView = (TextView) view.findViewById(R.id.name);
			view.setTag(mHolder);
		}else {
			mHolder = (MSMHolder) view.getTag();
		}
		String nameKey = "name"+pos;
		String bodyKey = "body"+pos;
		
		HashMap<String,Object> item = data.get(pos);
		
	    String nameValue = (String) item.get(nameKey);
		
	    String bodyValue = (String) item.get(bodyKey);
	    
	    mHolder.bodyTextView.setText(bodyValue);
	    
	    mHolder.nameTextView.setText(nameValue);
	   
	    view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				    
				  Intent intent = new Intent();
				  //intent.setClassName("com.android.mms","com.android.mms.ui.ConversationList");
				  intent.setComponent(new ComponentName("com.android.mms","com.android.mms.ui.ConversationList"));
				  mContext.startActivity(intent);	
			    }
	    	
	    		}

			);
	    
	    
	    
		return view;
	}
    class MSMHolder{
    	TextView nameTextView;
    	TextView bodyTextView;
    }
}
