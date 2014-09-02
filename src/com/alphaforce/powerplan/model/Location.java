package com.alphaforce.powerplan.model;

public class Location {
	private long mId;
	private String mAddress;
	private Double mLongitude;
	private Double mLatitude;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
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
