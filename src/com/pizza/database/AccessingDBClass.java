package com.pizza.database;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bean.LocationItem;
import com.bean.OrderListItem;
import com.bean.PizzaBean;
import com.bean.ToppingsItem;
import com.pizza.R;

public class AccessingDBClass {
	protected static final String TAG = "DataAdapter";
	public ArrayList<PizzaBean> pizzaList = new ArrayList<PizzaBean>();
	// public ArrayList<LangaugesBean> genres = new ArrayList<LangaugesBean>();
	ArrayList<String> vod_dates = new ArrayList<String>();
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;
	public String[] veg = { " VEGGIE CRUNCH Pizza", "Cheese Pizza",
			"Olive Pizza", "Peppy Paneer", "Margherita-Single Cheese",
			"GARDEN FRESH", "TOMATINO"

	};
	int[] imageId = { R.drawable.veggie, R.drawable.cheese_pizza,
			R.drawable.olive, R.drawable.panner, R.drawable.margarite,
			R.drawable.ic_launcher, R.drawable.tomatino };
	public int[] veg_regularCost = { 85, 105, 125, 125, 200, 110, 99 };
	public int[] veg_mediumCost = { 120, 135, 170, 155, 250, 135, 149 };
	public int[] veg_largeCost = { 175, 170, 225, 190, 290, 195, 199 };

	public String[] nonveg = { "CHICKEN CRUNCH", "ZESTY CHICKEN",
			"CHICKEN ITALIA", "Chicken Mexicana", "Keema Delight" };
	public int[] nonveg_regularCost = { 150, 160, 180, 190, 200, };
	public int[] nonveg_mediumCost = { 210, 220, 245, 260, 290, };
	public int[] nonveg_largeCost = { 270, 300, 295, 340, 380 };
	int[] nonveg_imageId = { R.drawable.chicken_pizza, R.drawable.cheese_pizza,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, };
	SharedPreferences mpref;
	Editor editor;

	public AccessingDBClass(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
		mpref = PreferenceManager.getDefaultSharedPreferences(mContext);
		// editor=new
	}

	public AccessingDBClass createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + " UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public AccessingDBClass open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			// mDbHelper.close();
			mDb = mDbHelper.getWritableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDb.close();
		mDbHelper.close();
	}

	public ArrayList<OrderListItem> getPizzOrdersList() {
		ArrayList<OrderListItem> orderList = new ArrayList<OrderListItem>();
		orderList.clear();
		String selectQuery = "SELECT *  FROM "
				+ DataBaseHelper.PizzaOrders_TABLE;
		// SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				OrderListItem orderItem = new OrderListItem();
				orderItem.setOrderType(cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.PizzaOrderType)));
				orderItem.setAddress(cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.DeliveryORPickAddress)));
				orderItem.setPrice(cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.OrderTotalAmount)));
				orderList.add(orderItem);
			} while (cursor.moveToNext());
		}
		cursor.close();
		Log.e("+++++++++ orderList  size :", "" + orderList.size());
		return orderList;
	}

	public boolean Login(String username, String password) {
		String[] selectionArgs = new String[] { username, password };
		try {
			int i = 0;
			Cursor c = null;
			c = mDb.rawQuery("select * from " + DataBaseHelper.USERS_TABLE
					+ " where " + DataBaseHelper.USER_MAILID + "= ? and "
					+ DataBaseHelper.USER_PASSWORD + "= ? ", selectionArgs);
			if (c != null && c.moveToFirst()) {
				// c.moveToFirst();
				// i = c.getCount();
				String address = c.getString(c
						.getColumnIndex(DataBaseHelper.USER_ADDRESS));
				editor = mpref.edit();
				editor.putString("userAddress", address);
				editor.commit();
				c.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<PizzaBean> getVegPizzaList(String pizzaType) {
		ArrayList<PizzaBean> vegPizzaList = new ArrayList<PizzaBean>();
		String selectQuery = "SELECT  * FROM  " + DataBaseHelper.PIZZA_TABLE
				+ " WHERE PizzaType =  ?";
		String[] args = new String[] { pizzaType };
		Log.d("From***************8", args[0]);
		Cursor cursor = mDb.rawQuery(selectQuery, args);

		if (cursor.moveToFirst()) {
			do {
				PizzaBean pizzabeanObject = new PizzaBean();

				pizzabeanObject.setItemName(cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_NAME)));
				pizzabeanObject.setItemType(cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_TYPE)));
				pizzabeanObject.setImageID(cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_ImageID)));
				pizzabeanObject.setRegualrPrice(cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_REGULARCOST)));
				pizzabeanObject.setMediumPrice(cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_MEDIUMCOST)));
				pizzabeanObject.setLargePrice(cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.PIZZA_LARGECOST)));

				vegPizzaList.add(pizzabeanObject);
			} while (cursor.moveToNext());
		}

		Log.e("++++++++ Pizza Size :", "++++++++++++++" + vegPizzaList.size());
		cursor.close();

		return vegPizzaList;
	}

	public ArrayList<String> getcityList() {
		ArrayList<String> cityArrayList = new ArrayList<String>();
		cityArrayList.clear();
		String selectQuery = "SELECT DISTINCT CityName FROM Locations_TABLE";
		// SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				cityArrayList.add(cursor.getString(cursor
						.getColumnIndex("CityName")));
			} while (cursor.moveToNext());
		}
		cursor.close();
		Log.e("+++++++++ getcityList  size :", "" + cityArrayList.size());
		return cityArrayList;
	}

	public ArrayList<String> getLocationAreaList(String cityName) {

		Log.e("++++++++++++City Name :", "" + cityName);
		ArrayList<String> locationAreaArrayList = new ArrayList<String>();
		locationAreaArrayList.clear();
		String selectQuery = "SELECT DISTINCT " + DataBaseHelper.LocationArea
				+ " FROM Locations_TABLE  WHERE " + DataBaseHelper.CityName
				+ "=  ?";
		// String selectQuery = "SELECT  * FROM  " + DataBaseHelper.PIZZA_TABLE;

		String[] args = new String[] { cityName };

		// SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = mDb.rawQuery(selectQuery, args);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				locationAreaArrayList.add(cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.LocationArea)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		Log.e("+++++++++ getLocationAreaList  size :", ""
				+ locationAreaArrayList.size());
		return locationAreaArrayList;
	}

	public String getLocationAdressList(String cityName, String locationArea) {
		Log.e("Cityname : " + cityName, "Location Area : " + locationArea);
		String selectQuery = "SELECT  * FROM Locations_TABLE  WHERE "
				+ DataBaseHelper.CityName + "= ? and "
				+ DataBaseHelper.LocationArea + "=?";
		String address = "";
		String[] args = new String[] { cityName, locationArea };
		Cursor cursor = mDb.rawQuery(selectQuery, args);
		if (cursor.moveToFirst()) {
			do {
				address = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.LocationAddress))
						+ "_"
						+ cursor.getString(cursor
								.getColumnIndex(DataBaseHelper.LocationPhoneNo));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return address;
	}

	public int userTableSize() {
		int size = 0;
		String selectQuery = "SELECT  * FROM " + DataBaseHelper.USERS_TABLE;
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				size++;
			} while (cursor.moveToNext());
		}
		cursor.close();
		return size;
	}

	public void saveAdminDetails() {
		ContentValues cv2 = new ContentValues();
		cv2.put(DataBaseHelper.USER_MAILID, "admin@app.com");
		cv2.put(DataBaseHelper.USER_PASSWORD, "admin");
		cv2.put(DataBaseHelper.USER_ADDRESS, "admin");
		if ((mDb.insert(DataBaseHelper.USERS_TABLE, null, cv2)) != -1) {
			Log.d("Saving Admin  Data", "information saved");
		} else {
			Log.d("saving Admin Data", "information not saved");
		}
	}

	public ArrayList<LocationItem> getLocationAddressList() {
		ArrayList<LocationItem> locationArrayList = new ArrayList<LocationItem>();
		// Select All Query
		locationArrayList.clear();
		String selectQuery = "SELECT  * FROM Locations_TABLE";
		// SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				LocationItem locationObject = new LocationItem();
				locationObject.setCity(cursor.getString(cursor
						.getColumnIndex("CityName")));
				locationObject.setLocationArea(cursor.getString(cursor
						.getColumnIndex("LocationArea")));

				locationObject.setLocationAddress(cursor.getString(cursor
						.getColumnIndex("LocationAddress")));

				locationArrayList.add(locationObject);
				String a = cursor.getString(0);
				// System.out.println(a);
			} while (cursor.moveToNext());
		}
		// closing connection
		cursor.close();
		Log.e("+++++++++ Toppings List size :", "" + locationArrayList.size());
		return locationArrayList;
	}

	public boolean insertNewUser(String emailId, String pwd, String address) {

		ContentValues cv1 = new ContentValues();
		cv1.put(DataBaseHelper.USER_MAILID, emailId);
		cv1.put(DataBaseHelper.USER_PASSWORD, pwd);
		cv1.put(DataBaseHelper.USER_ADDRESS, address);
		if ((mDb.insert(DataBaseHelper.USERS_TABLE, null, cv1)) != -1) {
			Log.d("Saving new user Data", "information saved");
			return true;
		} else {
			Log.d("saving new user Data", "information not saved");
			return false;
		}
	}

	public ArrayList<ToppingsItem> getToppingsList() {
		ArrayList<ToppingsItem> toppingsArrayList = new ArrayList<ToppingsItem>();
		// Select All Query
		toppingsArrayList.clear();
		String selectQuery = "SELECT  * FROM TOPPINGS_TABLE";
		// SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ToppingsItem toppingsObject = new ToppingsItem();
				toppingsObject.setToppingsName(cursor.getString(cursor
						.getColumnIndex("Toppings_Name")));
				toppingsObject.setToppingsRate(cursor.getInt(cursor
						.getColumnIndex("Topping_Rate")));
				toppingsArrayList.add(toppingsObject);
				String a = cursor.getString(0);
				// System.out.println(a);
			} while (cursor.moveToNext());
		}
		// closing connection
		cursor.close();

		Log.e("+++++++++ Toppings List size :", "" + toppingsArrayList.size());
		// mDb.close();
		// returning lables
		return toppingsArrayList;
	}


	public void insertNewData(String name, String type, int regularPrice,
			int medium_price, int largeprice) {

		ContentValues cv1 = new ContentValues();
		cv1.put("PizzaName", name);
		cv1.put("PizzaType", type);
		cv1.put("PizzaImageId", 0);
		cv1.put("pizza_regularCost", regularPrice);
		cv1.put("pizza_mediumCost", medium_price);
		cv1.put("pizza_largeCost", largeprice);
		if ((mDb.insert("PIZZA_TABLE", null, cv1)) != -1) {
			Log.d("Saving veg PIZZA_TABLE  Data", "information saved");
		} else {
			Log.d("saving veg PIZZA_TABLE  Data", "information not saved");
		}
		// getVegPizzaList("nonveg");
	}

	public void insertNewPizzaOrder(String orderType, String address,
			int totalPrice) {

		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DataBaseHelper.PizzaOrderType, orderType);
		contentvalues.put(DataBaseHelper.DeliveryORPickAddress, address);
		contentvalues.put(DataBaseHelper.OrderTotalAmount, totalPrice);
		contentvalues.put(DataBaseHelper.PIZZAORDER_DESCRIPTION, "");
		if ((mDb.insert(DataBaseHelper.PizzaOrders_TABLE, null, contentvalues)) != -1) {
			Log.d("Saving veg PIZZA_TABLE  Data", "information saved");
		} else {
			Log.d("saving veg PIZZA_TABLE  Data", "information not saved");
		}
		// getVegPizzaList("nonveg");

	}

	public boolean insertLocationData(String cityName, String locationName,
			String address) {

		ContentValues cv1 = new ContentValues();
		cv1.put("CityName", cityName);
		cv1.put("LocationArea", locationName);
		cv1.put("LocationAddress", address);
		if ((mDb.insert("Locations_TABLE", null, cv1)) != -1) {

			Log.d("Saving  Locations_TABLE  Data", "information saved");
			return true;
		} else {
			Log.d("saving  Locations_TABLE  Data", "information not saved");
			return false;
		}
		// getVegPizzaList("nonveg");

	}

	public boolean insertNewToppings(String toppingsName, int toppingsPrice) {
		ContentValues cv1 = new ContentValues();
		cv1.put("Toppings_Name", toppingsName);
		cv1.put("Topping_Rate", toppingsPrice);
		if ((mDb.insert("TOPPINGS_TABLE", null, cv1)) != -1) {

			Log.d("Saving veg TOPPINGS_TABLE  Data", "information saved");
			return true;
		} else {
			Log.d("saving veg TOPPINGS_TABLE  Data", "information not saved");
			return false;
		}

	}

	public void saveLocationArea(String cityName, String locationArea,
			String locationAddress, String phoneNo) {
		ContentValues cv1 = new ContentValues();
		cv1.put("CityName", cityName);
		cv1.put("LocationArea", locationArea);
		cv1.put("LocationAddress", locationAddress);
		cv1.put("LocationPhoneNo", phoneNo);
		if ((mDb.insert(DataBaseHelper.Locations_TABLE, null, cv1)) != -1) {
			Log.d("Saving  Locations_TABLE  Data", "information saved");
		} else {
			Log.d("saving  Locations_TABLE  Data", "information not saved");
		}

	}

	public void SavePizzaMenu() {

		for (int i = 0; i < veg.length; i++) {
			ContentValues cv1 = new ContentValues();
			cv1.put("PizzaName", veg[i]);
			cv1.put("PizzaType", "veg");
			cv1.put("PizzaImageId", imageId[i]);
			cv1.put("pizza_regularCost", veg_regularCost[i]);
			cv1.put("pizza_mediumCost", veg_mediumCost[i]);
			cv1.put("pizza_largeCost", veg_largeCost[i]);
			if ((mDb.insert("PIZZA_TABLE", null, cv1)) != -1) {
				Log.d("Saving veg PIZZA_TABLE  Data", "information saved");
			} else {
				Log.d("saving veg PIZZA_TABLE  Data", "information not saved");
			}
		}
		for (int i = 0; i < nonveg.length; i++) {
			ContentValues cv1 = new ContentValues();
			cv1.put("PizzaName", nonveg[i]);
			cv1.put("PizzaType", "nonveg");
			cv1.put("PizzaImageId", nonveg_imageId[i]);
			cv1.put("pizza_regularCost", nonveg_regularCost[i]);
			cv1.put("pizza_mediumCost", nonveg_mediumCost[i]);
			cv1.put("pizza_largeCost", nonveg_largeCost[i]);
			if ((mDb.insert("PIZZA_TABLE", null, cv1)) != -1) {
				Log.d("Saving nonveg PIZZA_TABLE  Data", "information saved");
			} else {
				Log.d("saving nonveg PIZZA_TABLE  Data",
						"information not saved");
			}
		}
		String[] toppings = { "Extra Cheese", "Extra Pepper and Onions",
				"Black Olives", "Fresh Tomatoes" };
		int[] toppingsRate = { 30, 50, 120, 20 };

		for (int i = 0; i < toppings.length; i++) {
			ContentValues cv = new ContentValues();
			cv.put("Toppings_Name", toppings[i]);
			cv.put("Topping_Rate", toppingsRate[i]);
			if ((mDb.insert("TOPPINGS_TABLE", null, cv)) != -1) {
				Log.d("Saving Toppings_Name  Data", "information saved");
			} else {
				Log.d("saving Toppings_Name Data", "information not saved");
			}

		}

	}

}
