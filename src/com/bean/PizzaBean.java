package com.bean;

import com.pizza.R.string;

public class PizzaBean {
	private String itemName;

	private int imageID;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String web) {
		this.itemName = web;
	}

	public string getItemToppings() {
		return itemToppings;
	}

	public void setItemToppings(string itemToppings) {
		this.itemToppings = itemToppings;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getNo_ofQuantity() {
		return no_ofQuantity;
	}

	public void setNo_ofQuantity(int no_ofQuantity) {
		this.no_ofQuantity = no_ofQuantity;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	private string itemToppings;
	private int itemPrice;
	private String itemType;
	private int no_ofQuantity;
	private int regualrPrice;
	public int getRegualrPrice() {
		return regualrPrice;
	}

	public void setRegualrPrice(int regualrPrice) {
		this.regualrPrice = regualrPrice;
	}

	public int getMediumPrice() {
		return mediumPrice;
	}

	public void setMediumPrice(int mediumPrice) {
		this.mediumPrice = mediumPrice;
	}

	public int getLargePrice() {
		return largePrice;
	}

	public void setLargePrice(int largePrice) {
		this.largePrice = largePrice;
	}

	private int mediumPrice;
	private int largePrice;

}
