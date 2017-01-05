package com.pizza.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static String TAG = "DataBaseHelper";
	// destination path (location) of our database on device
	private static String DB_PATH = "";
	private static String DB_NAME = "SERVICE_DATABASE_Divya_10";
	private SQLiteDatabase mDataBase;
	private final Context mContext;
	public static final String PIZZA_TABLE = "PIZZA_TABLE";
	public static final String USERS_TABLE = "USERS_TABLE";
	public static final String Toppings_TABLE = "TOPPINGS_TABLE";
	public static final String Locations_TABLE = "Locations_TABLE";
	public static final String PizzaOrders_TABLE = "PizzaOrders_TABLE";
	public static final String PizzaOrderType = "PizzaOrderType";
	public static final String OrderTotalAmount = "OrderTotalAmount";
	public static final String DeliveryORPickAddress = "DeliveryORPickAddress";
	public static final String Toppings_Name = "Toppings_Name";
	public static final int DATABASE_VERSION = 1;
	public static final String PIZZA_NAME = "PizzaName";
	public static final String PIZZA_TYPE = "PizzaType";
	public static final String PIZZA_ImageID = "PizzaImageId";
	public static final String PIZZA_REGULARCOST = "pizza_regularCost";
	public static final String PIZZA_MEDIUMCOST = "pizza_mediumCost";
	public static final String PIZZA_LARGECOST = "pizza_largeCost";
	public static final String PIZZAORDER_DESCRIPTION = "pizza_orderDescription";
	public static final String USER_MAILID = "MailID";
	public static final String USER_ADDRESS = "UserAddress";
	public static final String USER_PASSWORD = "Password";

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);// 1? its Database Version
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		this.mContext = context;
	}

	public void createDataBase() throws IOException {
		// If database not exists copy it from the assets

		boolean mDataBaseExist = checkDataBase();
		Log.d("DB", "" + mDataBaseExist);
		if (!mDataBaseExist) {
			this.getReadableDatabase();
			this.close();

		}
	}

	// Check that the database exists here: /data/data/your package/databases/Da
	// Name
	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		// Log.v("dbFile", dbFile + "   "+ dbFile.exists());
		return dbFile.exists();
	}

	// Copy the database from assets
	private void copyDataBase() throws IOException {
		InputStream mInput = mContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	// Open the database, so we can query it
	public boolean openDataBase() throws SQLException {
		String mPath = DB_PATH + DB_NAME;
		// Log.v("mPath", mPath);
		mDataBase = SQLiteDatabase.openDatabase(mPath, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		// mDataBase = SQLiteDatabase.openDatabase(mPath, null,
		// SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();
		super.close();
	}

	public static String CityName = "CityName";
	public static String LocationArea = "LocationArea";
	public static String LocationAddress = "LocationAddress";
	public static String LocationPhoneNo = "LocationPhoneNo";

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table if not exists " + PIZZA_TABLE + "( "
				+ PIZZA_NAME + " TEXT NOT NULL," + PIZZA_TYPE
				+ " TEXT NOT NULL," + PIZZA_ImageID + " integer NOT NULL,"
				+ PIZZA_REGULARCOST + " integer not null," + PIZZA_MEDIUMCOST
				+ " integer not null," + PIZZA_LARGECOST
				+ " integer not null);");

		db.execSQL("create table if not exists " + USERS_TABLE + "("
				+ USER_MAILID + " TEXT  NOT NULL primary key," + USER_PASSWORD
				+ " TEXT NOT NULL," + USER_ADDRESS + " TEXT NOT NULL);");

		db.execSQL("create table if not exists " + Toppings_TABLE + " ( "
				+ Toppings_Name
				+ " TEXT NOT NULL primary key,Topping_Rate  integer not null"
				+ ");");

		db.execSQL("create table if not exists " + Locations_TABLE + " ( "
				+ CityName + " TEXT NOT NULL," + LocationArea
				+ " TEXT NOT NULL," + LocationAddress
				+ " TEXT NOT NULL primary key,"
				+ DataBaseHelper.LocationPhoneNo + " TEXT " + ");");

		db.execSQL("create table if not exists " + PizzaOrders_TABLE
				+ " (OrderID  integer NOT NULL primary key," + PizzaOrderType
				+ " TEXT NOT NULL," + DeliveryORPickAddress
				+ " TEXT NOT NULL  ," + DataBaseHelper.OrderTotalAmount
				+ " integer not null ," + PIZZAORDER_DESCRIPTION + "  TEXT);");

		Log.d("SQL", "" + "create table if not exists " + PIZZA_TABLE + "( "
				+ PIZZA_NAME + " TEXT NOT NULL," + PIZZA_TYPE
				+ " TEXT NOT NULL," + PIZZA_ImageID + " integer NOT NULL,"
				+ PIZZA_REGULARCOST + " integer not null," + PIZZA_MEDIUMCOST
				+ " integer not null," + PIZZA_LARGECOST
				+ " integer not null);");
		// db.execSQL("CREATE TABLE IF NOT EXISTS " + ChatWindow + " ("
		// + fromUserName + " TEXT NOT NULL, " + dateAndTime
		// + " TEXT NOT NULL, " + time + " TEXT NOT NULL, " + toUserName
		// + " TEXT NOT NULL, " + fromuserId + " TEXT NOT NULL, "
		// + touserId + " TEXT NOT NULL, " + date + " TEXT NOT NULL, "
		// + message + " TEXT NOT NULL, " + userId + " TEXT NOT NULL, "
		// + status + " TEXT NOT NULL," + userverify + " TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
