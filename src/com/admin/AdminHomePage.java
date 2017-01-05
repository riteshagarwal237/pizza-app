package com.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pizza.R;
import com.pizza.activities.PizzaDisplayActivity;
import com.pizza.database.AccessingDBClass;

public class AdminHomePage extends Activity implements OnClickListener {
	Button adminAddpizzaButton, adminAddToppingButton, adminAddLocationButton,
			viewOrdersButton, signup_registerButton;
	LinearLayout adminLayout, registrationLayout;

	EditText registration_emailText, registration_pwdText,
			registration_confirmpwdText, addressEditText;
	AccessingDBClass dbobject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_homepage);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		adminLayout = (LinearLayout) findViewById(R.id.adminLayout);
		addressEditText = (EditText) findViewById(R.id.signup_addressEditText);
		registrationLayout = (LinearLayout) findViewById(R.id.registrationLayout);
		adminAddpizzaButton = (Button) findViewById(R.id.adminAddpizzaButton);
		adminAddToppingButton = (Button) findViewById(R.id.adminAddToppingButton);
		adminAddLocationButton = (Button) findViewById(R.id.adminAddLocationButton);
		signup_registerButton = (Button) findViewById(R.id.signup_registerButton);
		viewOrdersButton = (Button) findViewById(R.id.ViewOrders);
		adminAddpizzaButton.setOnClickListener(this);
		adminAddToppingButton.setOnClickListener(this);
		adminAddLocationButton.setOnClickListener(this);
		viewOrdersButton.setOnClickListener(this);
		signup_registerButton.setOnClickListener(this);
		dbobject = new AccessingDBClass(AdminHomePage.this);
		if (this.getIntent().getExtras().getBoolean("fromAdmin")) {

			adminLayout.setVisibility(View.VISIBLE);
			registrationLayout.setVisibility(View.GONE);

		} else {
			adminLayout.setVisibility(View.GONE);
			registrationLayout.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.adminAddpizzaButton:
			Intent addpizzaIntent = new Intent(AdminHomePage.this,
					AddPizzaActivity.class);
			startActivity(addpizzaIntent);
			break;
		case R.id.adminAddToppingButton:
			Intent addtoppingIntent = new Intent(AdminHomePage.this,
					AddToppingsActivity.class);
			startActivity(addtoppingIntent);
			break;
		case R.id.adminAddLocationButton:
			Intent addlocationIntent = new Intent(AdminHomePage.this,
					AddLocation.class);
			startActivity(addlocationIntent);
			break;
		case R.id.ViewOrders:
			Intent intent = new Intent(AdminHomePage.this,
					PizzaOrdersActivity.class);
			startActivity(intent);
			break;
		case R.id.signup_registerButton:

			String emailId = ((EditText) findViewById(R.id.signup__emailEditText))
					.getText().toString();
			String password = ((EditText) findViewById(R.id.signup_passwordeditText))
					.getText().toString();
			String confirm_password = ((EditText) findViewById(R.id.signup_confirmpasswordeditText))
					.getText().toString();

			if (emailId.length() == 0 || emailId.length() == 0
					|| password.length() == 0) {

				// Toast.makeText(this, "All Fields Required.",
				// Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(false)
						.setMessage("Please fill all fields")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// All of the fun happens inside the
										// CustomListener now.
										// I had to move it to enable data
										// validation.
									}
								});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
			if (password.equalsIgnoreCase(confirm_password)) {
				dbobject.createDatabase();
				dbobject.open();
				if (dbobject.insertNewUser(emailId, password, addressEditText
						.getText().toString())) {
					Toast.makeText(AdminHomePage.this,
							"User Registartion Successfull", 2000).show();
					Intent pizzaIntent = new Intent(AdminHomePage.this,
							PizzaDisplayActivity.class);
					startActivity(pizzaIntent);
					this.finish();
				} else {
					Toast.makeText(AdminHomePage.this, "User Already exists",
							2000).show();
				}
				dbobject.close();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(false)
						.setMessage("Password Mismatch..!!")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}

			break;
		default:
			break;
		}
	}
}
