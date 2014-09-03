package com.alphaforce.powerplan.sqllite;

import java.util.List;

import com.alphaforce.powerplan.model.Location;
import com.alphaforce.powerplan.model.Plan;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.LocationCursor;
import com.alphaforce.powerplan.sqllite.PowerPlanDatabaseHelper.PlanCursor;

import android.content.Context;

public class PowerPlanDataSource {

	private static PowerPlanDataSource sDataSource;
	private Context mContext;
	private PowerPlanDatabaseHelper mDBHelper;

	private PowerPlanDataSource(Context context) {
		mContext = context;
		mDBHelper = new PowerPlanDatabaseHelper(context);
	}

	public PowerPlanDataSource get(Context context) {
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

	public PlanCursor queryAllPlans() {
		return mDBHelper.queryAllPlans();
	}

	public Plan queryPlanbyId(int id) {
		Plan plan = null;
		PlanCursor cursor = mDBHelper.queryPlanById(id);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			plan = cursor.getPlan();
		}
		cursor.close();
		return plan;
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
