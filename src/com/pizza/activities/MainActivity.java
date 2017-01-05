package com.pizza.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.LoginActivity;
import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class MainActivity extends Activity {
	AccessingDBClass mDbHelper;
	ImageView imageView1;
	Button GoButton;
	TextView WelcomeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WelcomeTextView = (TextView) findViewById(R.id.titleTextview);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		GoButton = (Button) findViewById(R.id.GoButton);
		Typeface font = Typeface
				.createFromAsset(getAssets(), "Lobster_1.3.otf");
		WelcomeTextView.setTypeface(font);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);

			}
		});
		generateNotification(MainActivity.this,
				" Hurry up..!!Great offers, check out the pizza menu");

		mDbHelper = new AccessingDBClass(MainActivity.this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.getVegPizzaList("veg").size() > 0
				&& mDbHelper.getToppingsList().size() > 0) {

		} else {
			mDbHelper.SavePizzaMenu();
		}
		if (mDbHelper.getcityList().size() <= 0) {
			mDbHelper.saveLocationArea("Hyderabad", "Begumpet", "Opp SBH Bank",
					"400000");
			mDbHelper.saveLocationArea("Secunderabad", "Paradise", "S.P Road",
					"4008567");
			mDbHelper.saveLocationArea("Hyderabad", "Banjara Hills",
					"UBI Colony", "9010238990");
			mDbHelper.saveLocationArea("VishakaPatanam", "Nanal Nagar",
					"Cross Road", "766436212");
		} else {

		}
		mDbHelper.close();

		GoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent1 = new Intent(MainActivity.this,
						PizzaDisplayActivity.class);
				startActivity(intent1);
				// finish();

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Confirm")
					.setMessage("Do you want to exit from the application?")
					.setPositiveButton("yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// sendMessage(22);
									// // Stop the activity
									// editor = app_preferences.edit();
									// editor.putString("id", "");
									// editor.commit();
									MainActivity.this.finish();
								}

							}).setNegativeButton("No", null).show();

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void generateNotification(Context context, String message) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Pizzaa Den", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent intent;

		intent = new Intent(context, MainActivity.class);
		// intent.putExtra("pushNoti", "pushNoti");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, "Pizza", message,
				pendingIntent);

		notification.defaults = Notification.DEFAULT_SOUND;
		notificationManager.notify(0, notification);
	}

}
