package com.alphaforce.powerplan.common;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

@SuppressLint("SimpleDateFormat")
public class Data2Time {
	public static String dateToTime(long time){
		Date d = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(d);
	}
}
