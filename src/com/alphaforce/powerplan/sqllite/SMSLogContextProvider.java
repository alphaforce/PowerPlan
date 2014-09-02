package com.alphaforce.powerplan.sqllite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class SMSLogContextProvider {
	private Context mContext;
	  public SMSLogContextProvider(Context context) {
		this.mContext = context;
	}
	public List<HashMap<String,Object>> getMSMLogDate(long currentTime,int mintuses){
		List<HashMap<String,Object>> date = new ArrayList<HashMap<String, Object>>();
		HashMap<String,Object> map = new HashMap<String, Object>();
		long second = mintuses*60*1000;
		Cursor cursor=	mContext.getContentResolver().query(Uri.parse("content://sms/inbox"),new String[]{"address","person","date","body","thread_id"}," read = 0",null,null);
		int i = 0;
		while (cursor.moveToNext()) {
			long time = Long.parseLong(cursor.getString(2));
			if (time > currentTime-second){
				String address = cursor.getString(0);
				String person = cursor.getString(1);
				
				String body = cursor.getString(3);
				String id = cursor.getString(4);
				
				String itemname = "name"+i;
				String itemnumber = "number"+i;
				String itemdate = "date"+i; 
				String itembody = "body"+i;
				String itemid = "id"+i;
				
				
				if (person == null) {
					map.put(itemname,address);
				}else {
					String name =  getNameFromAddress(address);
					if(name != null){
						map.put(itemname,name);
					}else {
						map.put(itemname,address);
					}
				}
				map.put(itemnumber,address);
				map.put(itembody,body);
				map.put(itemid,id);
				i++;
				date.add(map);
			}
		}
		cursor.close();
		return date;

	}
	
	private String getNameFromAddress(String nu){
		String name = null;
		Uri personUri = Uri.withAppendedPath(  
	            ContactsContract.PhoneLookup.CONTENT_FILTER_URI, nu);  
		 Cursor cur = mContext.getContentResolver().query(personUri,  
		            new String[] { PhoneLookup.DISPLAY_NAME },  
		            null, null, null ); 
		 if( cur.moveToFirst() ) {  
		        int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);  
		        name  =   cur.getString(nameIdx); 
		        cur.close();  
	 
		    }  
		return name;
	}
	
}
