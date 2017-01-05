package com.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class LoginActivity extends Activity implements OnClickListener {

	private String userName, pwd;
	EditText userText, pwdText;
	Button newuserbutton, loginButton;
	AccessingDBClass dbobject;

	SharedPreferences mPrefrences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		dbobject = new AccessingDBClass(LoginActivity.this);
		userText = (EditText) findViewById(R.id.usertext);
		mPrefrences = PreferenceManager
				.getDefaultSharedPreferences(LoginActivity.this);
		pwdText = (EditText) findViewById(R.id.pwdtext);
		loginButton = (Button) findViewById(R.id.loginbutton);
		loginButton.setOnClickListener(this);
		newuserbutton = (Button) findViewById(R.id.newuserbutton);
		newuserbutton.setOnClickListener(this);
		dbobject.createDatabase();
		dbobject.open();
		if (dbobject.userTableSize() < 0) {
			dbobject.saveAdminDetails();
		}
		dbobject.close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginbutton:

			userName = userText.getText().toString();
			pwd = pwdText.getText().toString();
			System.out.println(userName);
			System.out.println(pwd);

			if (userName.equalsIgnoreCase("") || pwd.equalsIgnoreCase("")) {
				Toast.makeText(this, "All Fields Required.", Toast.LENGTH_SHORT)
						.show();
			}
			dbobject.createDatabase();
			dbobject.open();

			if (userName.equalsIgnoreCase("admin")
					&& pwd.equalsIgnoreCase("admin")) {
				Intent myIntent = new Intent(this, AdminHomePage.class);
				myIntent.putExtra("fromAdmin", true);
				this.startActivity(myIntent);
				finish();
			} else if (dbobject.Login(userName, pwd)) {
				// if(mPrefrences.getString("", defValue))
				// Intent myIntent = new Intent(this, AdminHomePage.class);
				// myIntent.putExtra("fromAdmin", true);
				// this.startActivity(myIntent);
				finish();
			} else {
				Toast.makeText(this, "Login failed.Please check Login Details",
						Toast.LENGTH_SHORT).show();
			}
			dbobject.close();

			break;
		case R.id.newuserbutton:
			Intent myIntent = new Intent(this, AdminHomePage.class);
			myIntent.putExtra("fromAdmin", false);
			this.startActivity(myIntent);
			// finish();

		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			this.finish();
			return true;

		default:
			return super.onKeyDown(keyCode, event);
		}
	}

}
