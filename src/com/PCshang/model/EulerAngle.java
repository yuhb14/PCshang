package com.PCshang.model;

public class EulerAngle {

	//这里为什么要设成private的格式，而不是public，两个的区别
	//
	private int id;
	private String time_log;
	private Float roll;
	private Float yaw;
	private Float pitch;
	
	public EulerAngle() {
		super();
	}	
	
	public EulerAngle(float roll, float yaw, float pitch) {
		super();
		this.roll = roll;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public EulerAngle(String time_log, float roll, float yaw, float pitch) {
		super();
		this.time_log = time_log;
		this.roll = roll;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public EulerAngle(int id, String time_log, Float roll, Float yaw, Float pitch) {
		super();
		this.id = id;
		this.time_log = time_log;
		this.roll = roll;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime_log() {
		return time_log;
	}

	public void setTime_log(String time_log) {
		this.time_log = time_log;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}


}
