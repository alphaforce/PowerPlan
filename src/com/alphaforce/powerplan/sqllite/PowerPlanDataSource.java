package com.alphaforce.powerplan.sqllite;

import java.util.List;

import com.alphaforce.powerplan.model.Location;
import com.alphaforce.powerplan.model.Plan;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.LocationCursor;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.PlanCursor;

import android.content.Context;
import android.database.Cursor;

public class PowerPlanDataSource {

	private static PowerPlanDataSource sDataSource;
	private Context mContext;
	private PowerPlanDatabaseHelper mDBHelper;

	private PowerPlanDataSource(Context context) {
		mContext = context;
		mDBHelper = new PowerPlanDatabaseHelper(mContext);
	}

	/**
	 * 单例获取数据源
	 * @param context 上下文
	 * @return
	 */
	public static PowerPlanDataSource get(Context context) {
		if (sDataSource == null) {
			sDataSource = new PowerPlanDataSource(context);
		}
		return sDataSource;
	}

	public long insertPlan(Plan plan) {
		return mDBHelper.insertPlan(plan);
	}

	public int deletePlanById(long id) {
		return mDBHelper.deletePlanById(id);
	}

	public int updatePlan(Plan plan) {
		return mDBHelper.updatePlan(plan);
	}

	/**
	 * 单表查询计划信息，无位置信息及状态
	 * @return
	 */
	public PlanCursor queryAllPlans() {
		return mDBHelper.queryAllPlans();
	}

	/**
	 * 根据ID单表查询计划信息，无位置信息及状态
	 * @param id
	 * @return
	 */
	public Plan queryPlanbyId(long id) {
		Plan plan = null;
		PlanCursor cursor = mDBHelper.queryPlanById(id);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			plan = cursor.getPlan();
		}
		cursor.close();
		return plan;
	}
	
	/**
	 * 多表查询，获取所有计划及相关位置信息
	 * @return
	 */
	public Cursor queryAllPlanInfoes(){
		return mDBHelper.queryAllPlanInfoes();
	}
	
	/**
	 * 多表查询，获取计划及相关位置信息
	 * @param id
	 * @return
	 */
	public Plan queryPlanInfoById(long id){
		return mDBHelper.queryPlanInfoById(id);
	}

	public List<Integer> queryPlanStatusById(long planId) {
		return mDBHelper.queryStatusByPlanId(planId);
	}

	public long insertLocation(Location loc) {
		return mDBHelper.insertLocation(loc);
	}

	public Location queryLocationById(long id) {
		Location loc = null;
		LocationCursor cursor = mDBHelper.queryLocationById(id);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			loc = cursor.getLocation();
		}
		cursor.close();
		return loc;
	}

	public LocationCursor queryAllLocations() {
		return mDBHelper.queryAllLocations();
	}
}
