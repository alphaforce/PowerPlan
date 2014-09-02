package com.alphaforce.powerplan.sqllite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.alphaforce.powerplan.common.Data2Time;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CallLogContextProvider {
  private Context mContext;
  
  public CallLogContextProvider(Context context) {
	this.mContext = context;
    }
  public List<HashMap<String,Object>> getCallLogDate(long currentTime,long mintuses){
	  List<HashMap<String,Object>> data = new ArrayList();
	  HashMap<String,Object> map = new HashMap();
	  long second = mintuses*60*1000;
		Cursor cursor=	mContext.getContentResolver().query(Uri.parse("content://call_log/calls"),new String[]{"name","number","date"}," type = 3",null,null);
		int i = 0;
		while (cursor.moveToNext()) {
			long time = Long.parseLong(cursor.getString(2));
			if (time > currentTime-second) {
				String nameString = cursor.getString(0);
				String numberString = cursor.getString(1);
			
				String itemname = "name"+i;
				String itemnumber = "number"+i;
				String itemdate = "date"+i;
				String dateString = Data2Time.dateToTime(time);
				map.put(itemname,nameString);
				map.put(itemnumber,numberString);
				map.put(itemdate,dateString);
			   i++;
			   data.add(map);
			}
			
		}
	  return data;
  }
}
