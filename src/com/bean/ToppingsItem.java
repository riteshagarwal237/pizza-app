package com.bean;

public class ToppingsItem {

	private String toppingsName;
	private int toppingsRate;
	private boolean checked = false;

	// priavte int
	public String getToppingsName() {
		return toppingsName;
	}

	public void setToppingsName(String toppingsName) {
		this.toppingsName = toppingsName;
	}

	public int getToppingsRate() {
		return toppingsRate;
	}

	public void setToppingsRate(int toppingsRate) {
		this.toppingsRate = toppingsRate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
