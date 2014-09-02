package com.alphaforce.powerplan.model;

import java.util.Date;
import java.util.List;

public class Plan {
	private long mId;
	private String mName;
	private String mContent;
	private Date mStartTime;
	private Date mEndTime;
	private long mLocactionId;
	private String mAuthor;
	private String mAddress;
	private Double mLongitude;
	private Double mLatitude;
	private List<Integer> mStatus;
	
	public long getId() {
		return mId;
	}
	public void setId(long id) {
		mId = id;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getContent() {
		return mContent;
	}
	public void setContent(String content) {
		mContent = content;
	}
	public Date getStartTime() {
		return mStartTime;
	}
	public void setStartTime(Date startTime) {
		mStartTime = startTime;
	}
	public Date getEndTime() {
		return mEndTime;
	}
	public void setEndTime(Date endTime) {
		mEndTime = endTime;
	}
	public long getLocactionId() {
		return mLocactionId;
	}
	public void setLocactionId(long locactionId) {
		mLocactionId = locactionId;
	}
	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String author) {
		mAuthor = author;
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
	public List<Integer> getStatus() {
		return mStatus;
	}
	public void setStatus(List<Integer> status) {
		mStatus = status;
	}
	
	
	
}
