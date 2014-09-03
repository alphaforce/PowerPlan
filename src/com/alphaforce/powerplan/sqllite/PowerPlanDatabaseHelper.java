package com.alphaforce.powerplan.sqllite;

import java.util.ArrayList;
import java.util.List;

import com.alphaforce.powerplan.model.Location;
import com.alphaforce.powerplan.model.Plan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

public class PowerPlanDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "PowerPlanDatabaseHelper";
	private static final String DB_NAME = "powerplan.db";
	private static final int VERSION = 1;

	private static final String PLAN_TABLE_NAME = "planinfo";
	private static final String PLAN_ID = "_id";
	private static final String PLAN_BEGIN_TIME = "b_time";
	private static final String PLAN_END_TIME = "e_time";
	private static final String PLAN_NAME = "s_name";
	private static final String PLAN_CONTENT = "s_content";
	private static final String PLAN_LOC_ID = "loc_id";
	private static final String PLAN_AUTHOR = "author";

	private static final String LOC_TABLE_NAME = "locationinfo";
	private static final String LOC_ID = "_id";
	private static final String LOC_ADDRESS = "address";
	private static final String LOC_LONGITUDE = "longitude";
	private static final String LOC_LATITUDE = "latitude";

	private static final String STATUS_TABLE_NAME = "statusinfo";
	private static final String STATUS_ID = "_id";
	private static final String STATUS_STATUS = "status";
	private static final String STATUS_PLAN_ID = "s_id";

	private static final String CREATE_TABLE_PLAN = "CREATE TABLE '"
			+ PLAN_TABLE_NAME + "' ('" + PLAN_ID + "' INTEGER NOT NULL, '"
			+ PLAN_NAME + "' TEXT NOT NULL, '" 
			+ PLAN_BEGIN_TIME + "' INTEGER NOT NULL, '"
			+ PLAN_END_TIME + "' INTEGER NOT NULL, '"
			+ PLAN_CONTENT + "' TEXT NOT NULL, '"
			+ PLAN_LOC_ID + "' INTEGER NOT NULL, '" 
			+ PLAN_AUTHOR + "' TEXT NOT NULL, "
			+ "PRIMARY KEY ('" + PLAN_ID + "'));";

	private static final String CREATE_TABLE_LOC = "CREATE TABLE '"
			+ LOC_TABLE_NAME + "' ('" + LOC_ID + "' INTEGER NOT NULL, '"
			+ LOC_ADDRESS + "' TEXT NOT NULL, '"
			+ LOC_LONGITUDE + "' REAL NOT NULL, '"
			+ LOC_LATITUDE + "' REAL NOT NULL, "
			+ "PRIMARY KEY ('" + LOC_ID + "'));";

	private static final String CREATE_TABLE_STATUS = "CREATE TABLE '"
			+ STATUS_TABLE_NAME + "' ('" + STATUS_ID + "' INTEGER NOT NULL, '"
			+ STATUS_STATUS + "' INTEGER NOT NULL, '"
			+ STATUS_PLAN_ID + "' INTEGER NOT NULL,"
			+ " PRIMARY KEY ('" + STATUS_ID + "'));";

	
	public PowerPlanDatabaseHelper(Context context) { 
		super(context, DB_NAME, null, VERSION); 
	}
	  
	@Override 
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_LOC);
		db.execSQL(CREATE_TABLE_PLAN);
		db.execSQL(CREATE_TABLE_STATUS);
	}
	  
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion 
			  + " to " + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + PLAN_TABLE_NAME);
	    db.execSQL("DROP TABLE IF EXISTS " + LOC_TABLE_NAME);
	    db.execSQL("DROP TABLE IF EXISTS " + STATUS_TABLE_NAME);
	    onCreate(db);
	}
	
	public long insertPlan(Plan plan){
		SQLiteDatabase db = getWritableDatabase();
		Location loc = new Location();
		loc.setAddress(plan.getAddress());
		loc.setLongitude(plan.getLongitude());
		loc.setLatitude(plan.getLatitude());
		
		db.beginTransaction();
		long planId;
		try {
			long locid = insertLocation(loc);
			plan.setLocactionId(locid);
			
			ContentValues cv = new ContentValues();
			cv.put(PLAN_NAME, plan.getName());
			cv.put(PLAN_CONTENT, plan.getContent());
			cv.put(PLAN_BEGIN_TIME, DateFormat.format("", plan.getStartTime()).toString());
			cv.put(PLAN_END_TIME, DateFormat.format("", plan.getEndTime()).toString());
			cv.put(PLAN_LOC_ID, plan.getLocactionId());
			cv.put(PLAN_AUTHOR, plan.getAuthor());
			
			planId = db.insert(PLAN_TABLE_NAME, null, cv);
			for(int i : plan.getStatus()){
				insertStatus(planId, i);
			}
		} finally {
			db.endTransaction();
		}
		
		return planId;
	}
	
	public int deletePlanById(long id){
		return getWritableDatabase().delete(PLAN_TABLE_NAME, PLAN_ID, 
				new String[]{String.valueOf(id)});
	}
	
	public int updatePlan(Plan plan){
		ContentValues cv = new ContentValues();
		cv.put(PLAN_NAME, plan.getName());
		cv.put(PLAN_CONTENT, plan.getContent());
		cv.put(PLAN_BEGIN_TIME, DateFormat.format("", plan.getStartTime()).toString());
		cv.put(PLAN_END_TIME, DateFormat.format("", plan.getEndTime()).toString());
		cv.put(PLAN_LOC_ID, plan.getLocactionId());
		cv.put(PLAN_AUTHOR, plan.getAuthor());
		cv.put(PLAN_ID, plan.getId());
		return getWritableDatabase().update(PLAN_TABLE_NAME, cv, PLAN_ID, 
				new String[]{String.valueOf(plan.getId())});
	}
	
	public PlanCursor queryPlanById(long id){
		Cursor cursor = getWritableDatabase().query(PLAN_TABLE_NAME, null, PLAN_ID, 
				new String[]{String.valueOf(id)}, null, null, null);
		return new PlanCursor(cursor);
	}
	
	public PlanCursor queryAllPlans(){
		Cursor cursor = getReadableDatabase().query(PLAN_TABLE_NAME, 
				null, null, null, null, null, PLAN_BEGIN_TIME);
		
		return new PlanCursor(cursor);
	}
	
	public Cursor queryAllPlanInfoes(){
		return getReadableDatabase().rawQuery("SELECT\n" +
				"planinfo._id, planinfo.b_time, planinfo.e_time,\n" +
				"planinfo.s_name, planinfo.s_content, planinfo.loc_id,\n" +
				"planinfo.author, locationinfo._id,\n" +
				"locationinfo.address, locationinfo.longitude,\n" +
				"locationinfo.latitude\n" +
				"FROM planinfo , locationinfo\n" +
				"WHERE planinfo.loc_id = locationinfo._id\n" +
				"ORDER BY planinfo.b_time ASC", null);
	}
	
	public Plan queryPlanInfoById(long id){
		Plan plan = null;
		Cursor cursor = getReadableDatabase().rawQuery("SELECT\n" +
				"planinfo._id, planinfo.b_time,\n" +
				"planinfo.e_time, planinfo.s_name, planinfo.s_content,\n" +
				"planinfo.loc_id, planinfo.author, locationinfo._id,\n" +
				"locationinfo.address, locationinfo.longitude,\n" +
				"locationinfo.latitude\n" +
				"FROM planinfo , locationinfo\n" +
				"WHERE planinfo.loc_id = locationinfo._id AND\n" +
				"planinfo._id = ?" +
				"ORDER BY planinfo.b_time ASC"
				, new String[] {String.valueOf(id)});
		
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			plan = new Plan();
			plan.setId(cursor.getLong(cursor.getColumnIndex("planinfo._id")));
			plan.setName(cursor.getString(cursor.getColumnIndex("planinfo.s_name")));
			plan.setContent(cursor.getString(cursor.getColumnIndex("planinfo.s_content")));
			plan.setStartTime(cursor.getLong(cursor.getColumnIndex("planinfo.b_time")));
			plan.setEndTime(cursor.getLong(cursor.getColumnIndex("planinfo.e_time")));
			plan.setAuthor(cursor.getString(cursor.getColumnIndex("planinfo.author")));
			
			plan.setLocactionId(cursor.getLong(cursor.getColumnIndex("locationinfo._id")));
			plan.setAddress(cursor.getString(cursor.getColumnIndex("locationinfo.address")));
			plan.setLongitude(cursor.getDouble(cursor.getColumnIndex("locationinfo.longitude")));
			plan.setLatitude(cursor.getDouble(cursor.getColumnIndex("locationinfo.latitude")));
		}
		cursor.close();
		
		return plan;
	}
	
	public long insertLocation(Location loc){
		ContentValues cv = new ContentValues();
		cv.put(LOC_ADDRESS, loc.getAddress());
		cv.put(LOC_LONGITUDE, loc.getLongitude());
		cv.put(LOC_LATITUDE, loc.getLatitude());
		return getWritableDatabase().insert(LOC_TABLE_NAME, null, cv);
	}
	
	public int deleteLocationBuId(long id){
		return getWritableDatabase().delete(LOC_TABLE_NAME, LOC_ID, 
				new String[]{String.valueOf(id)});
	}
	
	public int updateLocation(Location loc){
		ContentValues cv = new ContentValues();
		cv.put(LOC_ID, loc.getId());
		cv.put(LOC_ADDRESS, loc.getAddress());
		cv.put(LOC_LONGITUDE, loc.getLongitude());
		cv.put(LOC_LATITUDE, loc.getLatitude());
		return getWritableDatabase().update(LOC_TABLE_NAME, cv, LOC_ID, 
				new String[]{String.valueOf(loc.getId())});
	}
	
	public LocationCursor queryLocationById(long id){
		Cursor cursor = getReadableDatabase().query(LOC_TABLE_NAME, 
				null, LOC_ID, new String[]{String.valueOf(id)},
				null, null, null);
		return new LocationCursor(cursor);
	}
	
	public LocationCursor queryAllLocations(){
		Cursor cursor = getReadableDatabase().query( LOC_TABLE_NAME,
				null, null, null, null, null, null);
		return new LocationCursor(cursor);
	}
	
	public long insertStatus(long planId, int status){
		ContentValues cv = new ContentValues();
		cv.put(STATUS_STATUS, status);
		cv.put(STATUS_PLAN_ID, planId);
		return getWritableDatabase().insert(STATUS_TABLE_NAME, null, cv);
	}
	
	public int deleteStatusByPlanId(long id){
		return getWritableDatabase().delete(STATUS_TABLE_NAME, STATUS_PLAN_ID, 
				new String[]{String.valueOf(id)});
	}
	
	public List<Integer> queryStatusByPlanId(long id){
		Cursor cursor = getReadableDatabase().query(STATUS_TABLE_NAME, new String[]{STATUS_STATUS},
				STATUS_PLAN_ID, new String[]{String.valueOf(id)}, null, null, null);
		List<Integer> list = new ArrayList<Integer>();
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			list.add(cursor.getInt(cursor.getColumnIndex(STATUS_STATUS)));
			cursor.moveToNext();
		}
		return list;
	}
	
	public static class PlanCursor extends CursorWrapper{

		public PlanCursor(Cursor cursor) {
			super(cursor);
		}

		public Plan getPlan(){
			if(isBeforeFirst() || isAfterLast()){
				return null;
			}
			Plan plan = new Plan();
			plan.setId(getLong(getColumnIndex(PLAN_ID)));
			plan.setName(getString(getColumnIndex(PLAN_NAME)));
			plan.setContent(getString(getColumnIndex(PLAN_CONTENT)));
			plan.setStartTime(getLong(getColumnIndex(PLAN_BEGIN_TIME)));
			plan.setEndTime(getLong(getColumnIndex(PLAN_END_TIME)));
			plan.setAuthor(getString(getColumnIndex(PLAN_AUTHOR)));
			return plan;
		}
	}
	
	public static class LocationCursor extends CursorWrapper{

		public LocationCursor(Cursor cursor) {
			super(cursor);
		}
		
		public Location getLocation(){
			if(isBeforeFirst() || isAfterLast()){
				return null;
			}
			
			Location loc = new Location();
			loc.setId(getLong(getColumnIndex(LOC_ID)));
			loc.setAddress(getString(getColumnIndex(LOC_ADDRESS)));
			loc.setLongitude(getDouble(getColumnIndex(LOC_LONGITUDE)));
			loc.setLatitude(getDouble(getColumnIndex(LOC_LATITUDE)));
			return loc;
		}
		
	}
}
