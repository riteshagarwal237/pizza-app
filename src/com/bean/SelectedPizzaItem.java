package com.bean;

import java.util.ArrayList;

public class SelectedPizzaItem {

	private String pizzaName = "";
	private int quantiyNo = 0;

	public String getPizzaName() {
		return pizzaName;
	}

	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public int getPizzaselectedPrice() {
		return pizzaselectedPrice;
	}

	public void setPizzaselectedPrice(int pizzaselectedPrice) {
		this.pizzaselectedPrice = pizzaselectedPrice;
	}

	public ArrayList<ToppingsItem> getToppingsList() {
		return toppingsList;
	}

	public void setToppingsList(ArrayList<ToppingsItem> toppingsList) {
		this.toppingsList = toppingsList;
	}

	public int getQuantiyNo() {
		return quantiyNo;
	}

	public void setQuantiyNo(int quantiyNo) {
		this.quantiyNo = quantiyNo;
	}

	private int pizzaselectedPrice = 0;
	private ArrayList<ToppingsItem> toppingsList = new ArrayList<ToppingsItem>();
	// private String pizzaSizeType="";

}
