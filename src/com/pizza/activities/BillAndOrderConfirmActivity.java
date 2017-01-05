package com.pizza.activities;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.LoginActivity;
import com.pizza.R;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.pizza.database.AccessingDBClass;

public class BillAndOrderConfirmActivity extends Activity implements
		OnClickListener {

	RadioButton pickUpRadioButton, homedeliveryRadioButton;
	LinearLayout pickupLayout, homedeliveryLayout;
	Spinner citySpinner, locationSpinner;
	TextView locationAddressTextView1, locationAdressTextView2;
	AccessingDBClass dbobject;
	// ArrayAdapter<String> a;
	ArrayList<String> cityList, locationArrayList;

	EditText addressEditText, locationEditText;
	Button buyItBtn, logoutButton, UserLoginButton;
	String orderType;
	String orderAddress;
	LinearLayout loginLayout, logoutLayout;
	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox
	// environments.
	// private static final String CONFIG_CLIENT_ID =
	// "credential from developer.paypal.com";

	private static final String CONFIG_CLIENT_ID = "ASQoUhDEb8-hv5RoQMULwd2bOLxFJ5Sz7jPZSQLx-3gKJJ6OX3xiD89METjg";
	// when testing in sandbox, this is likely the -facilitator email address.
	// private static final String CONFIG_RECEIVER_EMAIL =
	// "matching paypal email address";
	private static final String CONFIG_RECEIVER_EMAIL = "divya.vatnala-facilitator@gmail.com";
	int totalPrice;
	String pizzaDescription;
	String name;
	String locationAddress = "";
	SharedPreferences msharedPreferences;
	TextView userLoginAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_order);
		userLoginAddress = (TextView) findViewById(R.id.userLoginAddress);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		msharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(BillAndOrderConfirmActivity.this);
		pickUpRadioButton = (RadioButton) findViewById(R.id.pickupRadioButton);
		homedeliveryRadioButton = (RadioButton) findViewById(R.id.deliveryRadioButton);
		addressEditText = (EditText) findViewById(R.id.DeliveryStreetNOEditText);
		locationEditText = (EditText) findViewById(R.id.DeliveryLocationEditText);
		citySpinner = (Spinner) findViewById(R.id.citySpinner);
		locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
		locationAddressTextView1 = (TextView) findViewById(R.id.addressTextView1);
		locationAdressTextView2 = (TextView) findViewById(R.id.addressTextView2);
		homedeliveryLayout = (LinearLayout) findViewById(R.id.homeDeliveryLayout);
		UserLoginButton = (Button) findViewById(R.id.UserLoginButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);

		dbobject = new AccessingDBClass(BillAndOrderConfirmActivity.this);
		loginLayout = (LinearLayout) findViewById(R.id.loginLayout);

		logoutLayout = (LinearLayout) findViewById(R.id.logoutLayout);
		logoutLayout.setVisibility(View.GONE);
		pickupLayout = (LinearLayout) findViewById(R.id.pickupLayout);
		buyItBtn = (Button) findViewById(R.id.buyItBtn);
		cityList = new ArrayList<String>();
		totalPrice = this.getIntent().getExtras().getInt("TotalAmount");
		// pizzaDescription = this.getIntent().getExtras()
		// .getString("PizzaDescription");
		locationArrayList = new ArrayList<String>();
		locationArrayList.clear();
		cityList.clear();
		dbobject.createDatabase();
		dbobject.open();
		cityList.addAll(dbobject.getcityList());
		Log.e("City List Size :", "" + cityList.size());
		dbobject.close();

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				BillAndOrderConfirmActivity.this,
				android.R.layout.simple_spinner_item, cityList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// spinner2.setAdapter(dataAdapter);
		citySpinner.setAdapter(dataAdapter);
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				locationArrayList.clear();
				dbobject.createDatabase();
				dbobject.open();
				locationArrayList.addAll(dbobject.getLocationAreaList(cityList
						.get(arg2)));
				dbobject.close();

				ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(
						BillAndOrderConfirmActivity.this,
						android.R.layout.simple_spinner_item, locationArrayList);
				dataAdapter1
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// spinner2.setAdapter(dataAdapter);
				locationSpinner.setAdapter(dataAdapter1);
				locationSpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								dbobject.createDatabase();
								dbobject.open();

								Log.e("On Item selected Listener ",
										"On Item selected Listener");
								name = cityList.get(arg2) + ","
										+ locationArrayList.get(position);
								if (orderType.equalsIgnoreCase("Pickup")) {
									locationAddress = dbobject
											.getLocationAdressList(cityList
													.get(arg2),
													locationArrayList
															.get(position));

									locationAddressTextView1
											.setText(locationAddress.split("_")[0]);
									locationAdressTextView2
											.setText("Phone NO :"
													+ locationAddress
															.split("_")[1]);
									orderAddress = name + ","
											+ locationAddress.split("_")[0]
											+ ","
											+ locationAddress.split("_")[1];
								} else if ((orderType
										.equalsIgnoreCase("HomeDelivery"))) {

									orderAddress = name
											+ addressEditText.getText()
													.toString()
											+ ","
											+ locationEditText.getText()
													.toString();
									Log.e("OnItem selcted Listener HomeDelivery",
											"" + orderAddress);
								}
								dbobject.close();
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {

							}
						});

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		Intent intent = new Intent(this, PayPalService.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
				CONFIG_RECEIVER_EMAIL);

		startService(intent);

		pickUpRadioButton.setOnClickListener(this);
		homedeliveryRadioButton.setOnClickListener(this);
		UserLoginButton.setOnClickListener(this);
		buyItBtn.setOnClickListener(this);
		logoutButton.setOnClickListener(this);

		if (msharedPreferences.getString("userAddress", "").length() == 0) {

			loginLayout.setVisibility(View.VISIBLE);
			logoutLayout.setVisibility(View.GONE);

		} else {
			userLoginAddress.setVisibility(View.VISIBLE);
			userLoginAddress.setText(msharedPreferences.getString(
					"userAddress", ""));
			loginLayout.setVisibility(View.GONE);
			logoutLayout.setVisibility(View.VISIBLE);
		}

	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) BillAndOrderConfirmActivity.this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.d("Network",
								"NETWORKnAME: " + info[i].getTypeName());
						return true;
					}

		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("OnActivity Result", "OnActivity Result");

		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i("paymentExample", confirm.toJSONObject()
								.toString(4));

						// TODO: send 'confirm' to your server for verification.
						// see
						// https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
						// for more details.
						Log.i("Payment Done ", "Insert the order in database");

						dbobject.createDatabase();
						dbobject.open();
						dbobject.insertNewPizzaOrder(orderType, orderAddress,
								totalPrice);
						dbobject.close();

						Toast.makeText(BillAndOrderConfirmActivity.this,
								"Payment successfull", 2000).show();
						buyItBtn.setText("You order has been placed. Thank you..!!");
						buyItBtn.setClickable(false);
					} catch (JSONException e) {
						Log.e("paymentExample",
								"an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("paymentExample", "The user canceled.");
			} else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
				Log.i("paymentExample",
						"An invalid payment was submitted. Please see the docs.");
			}
		} else if (requestCode == 1) {
			logoutLayout.setVisibility(View.VISIBLE);
			logoutButton.setVisibility(View.VISIBLE);
			loginLayout.setVisibility(View.GONE);

			Log.d("++++++++++ from LoginActivity in On ActivityResult",
					"userAddress Shared Preferences"
							+ msharedPreferences.getString("userAddress", ""));
			userLoginAddress.setVisibility(View.VISIBLE);
			userLoginAddress.setText(msharedPreferences.getString(
					"userAddress", ""));
			orderAddress = msharedPreferences.getString("userAddress", "");
		}
	}

	@Override
	public void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.UserLoginButton:

			Intent login_intent = new Intent(BillAndOrderConfirmActivity.this,
					LoginActivity.class);
			startActivityForResult(login_intent, 1);
			break;
		case R.id.logoutButton:

			Editor editor = msharedPreferences.edit();
			editor.remove("userAddress");
			editor.commit();
			Log.d("++++++++++ from LoginActivity in On ActivityResult",
					"userAddress Shared Preferences"
							+ msharedPreferences.getString("userAddress", ""));

			break;

		case R.id.pickupRadioButton:

			Log.e("Pick up onclick", "Pick up onclick");
			orderAddress = "";
			pickupLayout.setVisibility(View.VISIBLE);
			homedeliveryLayout.setVisibility(View.GONE);
			citySpinner.setVisibility(View.VISIBLE);
			locationSpinner.setVisibility(View.VISIBLE);

			// a = new ArrayAdapter<String>(ConfirmOrderActivity.this,
			// android.R.layout.sim, cityList);
			orderType = "Pickup";

			break;

		case R.id.deliveryRadioButton:
			Log.e("home delivery  onclick", "home delivery onclick");
			orderAddress = "";
			pickupLayout.setVisibility(View.GONE);
			homedeliveryLayout.setVisibility(View.VISIBLE);
			orderType = "HomeDelivery";

			citySpinner.setVisibility(View.VISIBLE);
			locationSpinner.setVisibility(View.VISIBLE);
			addressEditText.getText().clear();
			locationEditText.getText().clear();

			break;

		case R.id.buyItBtn:
			if (orderType != null) {
				if (orderType.equalsIgnoreCase("HomeDelivery")) {

					try {
						orderAddress = name + ","
								+ addressEditText.getText().toString() + ","
								+ locationEditText.getText().toString();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					if (orderAddress != null) {
						try {
							orderAddress = name + ","
									+ locationAddress.split("_")[0] + ","
									+ locationAddress.split("_")[1];
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Log.e("Order Address ", "" + orderAddress);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isConnectingToInternet()) {

					PayPalPayment thingToBuy = new PayPalPayment(
							new BigDecimal("1.00"), "USD", "Pizza Order");

					Intent intent = new Intent(
							BillAndOrderConfirmActivity.this,
							PaymentActivity.class);

					intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
							CONFIG_ENVIRONMENT);
					intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
							CONFIG_CLIENT_ID);
					intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
							CONFIG_RECEIVER_EMAIL);

					// It's important to repeat the clientId here so that
					// the
					// SDK
					// has it if
					// Android restarts your
					// app midway through the payment UI flow.
					intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
							CONFIG_CLIENT_ID);
					intent.putExtra(PaymentActivity.EXTRA_PAYER_ID,
							CONFIG_RECEIVER_EMAIL);
					intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

					startActivityForResult(intent, 0);
				} else {

					Toast.makeText(
							BillAndOrderConfirmActivity.this,
							"Couldnot connect to network.Please check your internet connection.",
							3000).show();
				}
			} else {
				Toast.makeText(BillAndOrderConfirmActivity.this,
						"Please select the order type above.", 3000).show();
			}
			break;

		default:
			break;
		}

	}

}
