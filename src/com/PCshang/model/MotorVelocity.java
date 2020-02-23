package com.PCshang.model;

public class MotorVelocity {
	
	private int id;
	private String time_log;
	private Float v_motor_a;
	private Float v_motor_b;
	private Float v_motor_c;
	
	public MotorVelocity() {
		super();
	}

	public MotorVelocity(Float v_motor_a, Float v_motor_b, Float v_motor_c) {
		super();
		this.v_motor_a = v_motor_a;
		this.v_motor_b = v_motor_b;
		this.v_motor_c = v_motor_c;
	}

	public MotorVelocity(String time_log, Float v_motor_a, Float v_motor_b, Float v_motor_c) {
		super();
		this.time_log = time_log;
		this.v_motor_a = v_motor_a;
		this.v_motor_b = v_motor_b;
		this.v_motor_c = v_motor_c;
	}

	
	
	public MotorVelocity(int id, String time_log, Float v_motor_a, Float v_motor_b, Float v_motor_c) {
		super();
		this.id = id;
		this.time_log = time_log;
		this.v_motor_a = v_motor_a;
		this.v_motor_b = v_motor_b;
		this.v_motor_c = v_motor_c;
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

	public Float getV_motor_a() {
		return v_motor_a;
	}

	public void setV_motor_a(Float v_motor_a) {
		this.v_motor_a = v_motor_a;
	}

	public Float getV_motor_b() {
		return v_motor_b;
	}

	public void setV_motor_b(Float v_motor_b) {
		this.v_motor_b = v_motor_b;
	}

	public Float getV_motor_c() {
		return v_motor_c;
	}

	public void setV_motor_c(Float v_motor_c) {
		this.v_motor_c = v_motor_c;
	}
	
	
	
}
