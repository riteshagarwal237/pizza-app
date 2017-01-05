package com.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class AddPizzaActivity extends Activity {
	// private static final String USER_ID = null;
	// As class variables - define your buttons
	private RadioButton button1 = null;
	private RadioButton button2 = null;
	// String email=MainActivity.user_mail;
	Intent myIntent;
	// public static final String TABLE_NAME = "cholestral_log";

	private String ynvalue;
	String st1, st2;
	int st3 = 0, st4 = 0, st5 = 0, st6 = 0;
	SQLiteOpenHelper db = null;
	AlertDialog show;
	Button addpizzaButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert_layout);
		addpizzaButton=(Button)findViewById(R.id.addpizzaButton);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		addpizzaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText chol = (EditText) findViewById(R.id.chol_value);
				EditText hdl1 = (EditText) findViewById(R.id.hdl_value);
				EditText ldl1 = (EditText) findViewById(R.id.ldl_value);
				EditText tri1 = (EditText) findViewById(R.id.tri_value);
				button1 = (RadioButton) findViewById(R.id.yes);
				button2 = (RadioButton) findViewById(R.id.no);
				if (button1.isChecked()) {
					ynvalue = button1.getText().toString();
				} else if (button2.isChecked()) {
					ynvalue = button2.getText().toString();

				}

				Calendar ctaq = Calendar.getInstance();
				SimpleDateFormat dfaq = new SimpleDateFormat("dd-MMM-yyyy");
				String cdate = dfaq.format(ctaq.getTime());
				System.out.println("cdate");

				String totChol = chol.getText().toString();
				int hdl = Integer.parseInt(hdl1.getText().toString());
				int ldl = Integer.parseInt(ldl1.getText().toString());
				int triglycerides = Integer.parseInt(tri1.getText().toString());
				dbobject.createDatabase();
				dbobject.open();
				// selectedPizzaList.clear();
				// testadapter.SaveLanguages();
				dbobject.insertNewData(totChol, ynvalue, hdl, ldl, triglycerides);
				// pizzaList.addAll(dbobject.getVegPizzaList("nonveg"));
				dbobject.close();
				// if (email.length() == 0 || totChol.length() == 0
				// || hdl.length() == 0 || ldl.length() == 0
				// || triglycerides.length() == 0) {
				// show = new AlertDialog.Builder(this).setTitle("Oops..!")
				// .setMessage("You must fill all fields")
				// .setPositiveButton("OK", null).show();
				// } else if (totChol.length() < 2 || totChol.length() > 3
				// || totChol.equals("0")) {
				// show = new AlertDialog.Builder(this)
				// .setTitle("Oops..!")
				// .setMessage(
				// "Total cholesterol should be 2-3 digits and it should not be zero")
				// .setPositiveButton("OK", null).show();
				// } else if ((hdl.length() < 1 || hdl.length() > 2)
				// || hdl.equals("0")) {
				// show = new AlertDialog.Builder(this)
				// .setTitle("Oops..!")
				// .setMessage(
				// "Total cholesterol should be 2-3 digits and it should not be zero")
				// .setPositiveButton("OK", null).show();
				// } else if (ldl.length() < 1 || ldl.length() > 2 ||
				// ldl.equals("0")) {
				// show = new AlertDialog.Builder(this)
				// .setTitle("Oops..!")
				// .setMessage(
				// "ldl should be 2-3 digits and it should not be zero")
				// .setPositiveButton("OK", null).show();
				// } else if (triglycerides.length() < 2 || triglycerides.length() >
				// 3
				// || triglycerides.equals("0")) {
				// show = new AlertDialog.Builder(this)
				// .setTitle("Oops..!")
				// .setMessage(
				// "Total cholesterol should be 2-3 digits and it should not be zero")
				// .setPositiveButton("OK", null).show();
				// }

				// else {
				// DatabaseHelper db = new DatabaseHelper(this);
				//
				// SQLiteDatabase sq = db.getWritableDatabase();
				// ContentValues cv = new ContentValues();
				// cv.put(DatabaseHelper.USER_ID, email);
				// cv.put(DatabaseHelper.DATE, cdate);
				// cv.put(DatabaseHelper.TOTAL_CHOL, totChol);
				// cv.put(DatabaseHelper.HDL, hdl);
				// cv.put(DatabaseHelper.LDL, ldl);
				// cv.put(DatabaseHelper.TRI_G, triglycerides);
				// cv.put(DatabaseHelper.FASTING, ynvalue);
				// if ((sq.insert(DatabaseHelper.TABLE_NAME, null, cv)) != -1) {
				// Toast.makeText(this, "Cholesterol log Successfully Inserted",
				// 2000).show();
				//
				// // Intent intent = new Intent(this, Home.class);
				// // startActivity(intent);
				// // finish();
				// } else {
				// Toast.makeText(this, "Insert Error", 2000).show();
				// }
				// db.close();
				// return true;
				// }

				
			}
		});
		// Button bt=(Button) findViewById(R.id.options_id);
		// registerForContextMenu(bt);
		dbobject = new AccessingDBClass(AddPizzaActivity.this);
		/*
		 * int st3=Integer.parseInt(total_chol.getText().toString()); int
		 * st4=Integer.parseInt(hdl.getText().toString()); int
		 * st5=Integer.parseInt(ldl.getText().toString()); int
		 * st6=Integer.parseInt(tri.getText().toString());
		 */
		button1 = (RadioButton) findViewById(R.id.yes);
		button2 = (RadioButton) findViewById(R.id.no);
		if (button1.isChecked()) {
			ynvalue = button1.getText().toString();
		} else if (button2.isChecked()) {
			ynvalue = button2.getText().toString();

		}

	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = new MenuInflater(this);
	// inflater.inflate(R.menu.chol_log_menu, menu);
	// return true;
	// }
	AccessingDBClass dbobject;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addpizzaButton:
			// EditText date = (EditText) findViewById(R.id.date_value);
			// EditText user_id = (EditText) findViewById(R.id.userID_value);

			EditText chol = (EditText) findViewById(R.id.chol_value);
			EditText hdl1 = (EditText) findViewById(R.id.hdl_value);
			EditText ldl1 = (EditText) findViewById(R.id.ldl_value);
			EditText tri1 = (EditText) findViewById(R.id.tri_value);
			button1 = (RadioButton) findViewById(R.id.yes);
			button2 = (RadioButton) findViewById(R.id.no);
			if (button1.isChecked()) {
				ynvalue = button1.getText().toString();
			} else if (button2.isChecked()) {
				ynvalue = button1.getText().toString();

			}

			Calendar ctaq = Calendar.getInstance();
			SimpleDateFormat dfaq = new SimpleDateFormat("dd-MMM-yyyy");
			String cdate = dfaq.format(ctaq.getTime());
			System.out.println("cdate");

			String totChol = chol.getText().toString();
			int hdl = Integer.parseInt(hdl1.getText().toString());
			int ldl = Integer.parseInt(ldl1.getText().toString());
			int triglycerides = Integer.parseInt(tri1.getText().toString());
			dbobject.createDatabase();
			dbobject.open();
			// selectedPizzaList.clear();
			// testadapter.SaveLanguages();
			dbobject.insertNewData(totChol, ynvalue, hdl, ldl, triglycerides);
			// pizzaList.addAll(dbobject.getVegPizzaList("nonveg"));
			dbobject.close();
			// if (email.length() == 0 || totChol.length() == 0
			// || hdl.length() == 0 || ldl.length() == 0
			// || triglycerides.length() == 0) {
			// show = new AlertDialog.Builder(this).setTitle("Oops..!")
			// .setMessage("You must fill all fields")
			// .setPositiveButton("OK", null).show();
			// } else if (totChol.length() < 2 || totChol.length() > 3
			// || totChol.equals("0")) {
			// show = new AlertDialog.Builder(this)
			// .setTitle("Oops..!")
			// .setMessage(
			// "Total cholesterol should be 2-3 digits and it should not be zero")
			// .setPositiveButton("OK", null).show();
			// } else if ((hdl.length() < 1 || hdl.length() > 2)
			// || hdl.equals("0")) {
			// show = new AlertDialog.Builder(this)
			// .setTitle("Oops..!")
			// .setMessage(
			// "Total cholesterol should be 2-3 digits and it should not be zero")
			// .setPositiveButton("OK", null).show();
			// } else if (ldl.length() < 1 || ldl.length() > 2 ||
			// ldl.equals("0")) {
			// show = new AlertDialog.Builder(this)
			// .setTitle("Oops..!")
			// .setMessage(
			// "ldl should be 2-3 digits and it should not be zero")
			// .setPositiveButton("OK", null).show();
			// } else if (triglycerides.length() < 2 || triglycerides.length() >
			// 3
			// || triglycerides.equals("0")) {
			// show = new AlertDialog.Builder(this)
			// .setTitle("Oops..!")
			// .setMessage(
			// "Total cholesterol should be 2-3 digits and it should not be zero")
			// .setPositiveButton("OK", null).show();
			// }

			// else {
			// DatabaseHelper db = new DatabaseHelper(this);
			//
			// SQLiteDatabase sq = db.getWritableDatabase();
			// ContentValues cv = new ContentValues();
			// cv.put(DatabaseHelper.USER_ID, email);
			// cv.put(DatabaseHelper.DATE, cdate);
			// cv.put(DatabaseHelper.TOTAL_CHOL, totChol);
			// cv.put(DatabaseHelper.HDL, hdl);
			// cv.put(DatabaseHelper.LDL, ldl);
			// cv.put(DatabaseHelper.TRI_G, triglycerides);
			// cv.put(DatabaseHelper.FASTING, ynvalue);
			// if ((sq.insert(DatabaseHelper.TABLE_NAME, null, cv)) != -1) {
			// Toast.makeText(this, "Cholesterol log Successfully Inserted",
			// 2000).show();
			//
			// // Intent intent = new Intent(this, Home.class);
			// // startActivity(intent);
			// // finish();
			// } else {
			// Toast.makeText(this, "Insert Error", 2000).show();
			// }
			// db.close();
			// return true;
			// }

			// case R.id.assess:
			// Intent i = new Intent(this, Assess.class);
			// startActivity(i);
			// this.finish();
			//
			// return true;
			//
			// case R.id.exit:
			// myIntent = new Intent(this, MainActivity.class);
			// myIntent.putExtra("exit", R.id.exit);
			// this.startActivity(myIntent);
			// this.finish();
			// Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
			// return true;
			// case R.id.home:
			// myIntent = new Intent(this, Home.class);
			// this.startActivity(myIntent);
			// this.finish();
			// return true;
		default:
			return false;
		}
		// TODO Auto-generated method stub
		// return super.onOptionsItemSelected(item);

	}

	/*
	 * @Override public void onCreateContextMenu(ContextMenu menu, View
	 * v,ContextMenuInfo menuInfo) { super.onCreateContextMenu(menu, v,
	 * menuInfo); menu.setHeaderTitle("Options"); menu.add(0, v.getId(), 0,
	 * "Assess"); menu.add(0, v.getId(), 0, "Home"); //menu.add(0, v.getId(), 0,
	 * "Cancel"); menu.add(0, v.getId(), 0, "Exit"); }
	 * 
	 * @Override public boolean onContextItemSelected(MenuItem item) {
	 * if(item.getTitle()=="Assess") { function1(item.getItemId()); } else
	 * if(item.getTitle()=="Exit"){ function2(item.getItemId());
	 * 
	 * } else if(item.getTitle()=="Home") { function3(item.getItemId()); } else
	 * { return false; } return true; } public void function1(int id){ Intent
	 * i=new Intent(this,Assess.class); startActivity(i); //Toast.makeText(this,
	 * "function 1 called", Toast.LENGTH_SHORT).show(); } public void
	 * function2(int id){
	 * 
	 * myIntent=new Intent(this,MainActivity.class);
	 * this.startActivity(myIntent); //Toast.makeText(this, "Exit called",
	 * Toast.LENGTH_SHORT).show(); } public void function3(int id) {
	 * myIntent=new Intent(this,Home.class); this.startActivity(myIntent); }
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.activity_cholestral_insert, menu);
	 * return true; } public void save(View v) { // EditText date = (EditText)
	 * findViewById(R.id.date_value); EditText user_id = (EditText)
	 * findViewById(R.id.userID_value); EditText chol = (EditText)
	 * findViewById(R.id.chol_value); EditText hdl1 = (EditText)
	 * findViewById(R.id.hdl_value); EditText ldl1 = (EditText)
	 * findViewById(R.id.ldl_value); EditText tri1 = (EditText)
	 * findViewById(R.id.tri_value); button1 = (RadioButton)
	 * findViewById(R.id.yes); button2 = (RadioButton) findViewById(R.id.no);
	 * if(button1.isChecked()) { ynvalue=button1.getText().toString(); } else
	 * if(button2.isChecked()) { ynvalue=button1.getText().toString();
	 * 
	 * } Calendar ctaq = Calendar.getInstance(); SimpleDateFormat dfaq = new
	 * SimpleDateFormat("dd-MMM-yyyy"); String cdate =
	 * dfaq.format(ctaq.getTime()); System.out.println("cdate");
	 * if(user_id.getText
	 * ().toString().length()==0||chol.getText().toString().length
	 * ()==0||hdl1.getText
	 * ().toString().length()==0||ldl1.getText().toString().length
	 * ()==0||tri1.getText().toString().length()==0) { show = new
	 * AlertDialog.Builder(this).setTitle("Oops..!")
	 * .setMessage("You must fill all fields") .setPositiveButton("OK",
	 * null).show(); } else { DatabaseHelper db=new DatabaseHelper(this);
	 * 
	 * SQLiteDatabase sq = db.getWritableDatabase(); ContentValues cv = new
	 * ContentValues();
	 * cv.put(DatabaseHelper.USER_ID,user_id.getText().toString() );
	 * cv.put(DatabaseHelper.DATE,cdate);
	 * cv.put(DatabaseHelper.TOTAL_CHOL,chol.getText().toString() );
	 * cv.put(DatabaseHelper.HDL,hdl1.getText().toString() );
	 * cv.put(DatabaseHelper.LDL,ldl1.getText().toString() );
	 * cv.put(DatabaseHelper.TRI_G,tri1.getText().toString() );
	 * cv.put(DatabaseHelper.FASTING,ynvalue );
	 * if((sq.insert(DatabaseHelper.TABLE_NAME, null, cv))!=-1) {
	 * Toast.makeText(this, "Record Successfully Inserted", 2000).show();
	 * 
	 * Intent intent=new Intent(this,Home.class); startActivity(intent);
	 * finish(); } else { Toast.makeText(this, "Insert Error", 2000).show(); }
	 * db.close(); }
	 * 
	 * } /*public void viewreports(View v) { Intent i=new
	 * Intent(this,DisplayReports.class); startActivity(i); }
	 */

}
