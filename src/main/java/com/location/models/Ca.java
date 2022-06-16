package com.location.models;

public class Ca {
	private int locId;
	private int ca;
	public Ca() {
		locId = 0;
		ca = 0;
	}
	public Ca(int locId, int ca) {
		this.locId = locId;
		this.ca = ca;
	}
	public int getLocId() {
		return locId;
	}
	public void setLocId(int locId) {
		this.locId = locId;
	}
	public int getCa() {
		return ca;
	}
	public void setCa(int ca) {
		this.ca = ca;
	}
}
