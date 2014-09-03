package com.alphaforce.powerplan.ui.adapter;

import java.util.HashMap;
import java.util.List;

import com.alphaforce.powerplan.R;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallLogAdapter extends BaseAdapter{

	private Context mContext;
	
	private	List<HashMap<String,Object>> data;
	//private Holder holder;
	public CallLogAdapter(Context context,List<HashMap<String,Object>> data) {
		this.mContext = context;
		this.data = data;
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup viewGroup) {
		String phonenumber;
		Holder holder;
		if (view == null) {
			holder = new Holder();
			view = LayoutInflater.from(mContext).inflate(R.layout.call_log_list,null);	
			holder.info = (TextView) view.findViewById(R.id.info); 
		    holder.tv =  (TextView) view.findViewById(R.id.tv);
		    holder.drawable = (ImageView) view.findViewById(R.id.img);
		    view.setTag(holder); 
		}else {
			holder = (Holder)view.getTag();  
		}
		String numberKey = "number"+pos;
		String nameKey = "name"+pos;
		String dateKey = "date"+pos;
		
		HashMap<String,Object> item = data.get(pos);
		
		String name = (String) item.get(nameKey);
		final String number = (String) item.get(numberKey);
		String date = (String) item.get(dateKey);
		
		if (name != null) {
			holder.info.setText(name);
		}else {
			holder.info.setText(number);
		} 
		
		view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {//jie ouhe
				 Intent in2 = new Intent();  
	                in2.setAction(Intent.ACTION_CALL);  
	                in2.setData(Uri.parse("tel:"+number));  
	                mContext.startActivity(in2);
	                //startActivity(in2);  
			}
			
			
		});
		
		return view;
	}
	 class Holder  
    {  
        public TextView tv;  
        public TextView info;
        public ImageView drawable;
  
    }  

}
