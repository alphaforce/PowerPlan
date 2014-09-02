package com.alphaforce.powerplan.model;

public class Location {
	private int mId;
	private String mAddress;
	private Double mLongitude;
	private Double mLatitude;

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public Double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(Double longitude) {
		mLongitude = longitude;
	}

	public Double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(Double latitude) {
		mLatitude = latitude;
	}
}
