package com.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class AddLocation extends Activity implements OnClickListener {

	EditText cityEditText, locationEditText, addressEditText;
	Button addLocationButton;
	AccessingDBClass dbclassObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert_location);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		cityEditText = (EditText) findViewById(R.id.cityEditText);
		locationEditText = (EditText) findViewById(R.id.locationEditText);
		addressEditText = (EditText) findViewById(R.id.addressEditText);
		addLocationButton = (Button) findViewById(R.id.addLocationButton);
		dbclassObject = new AccessingDBClass(AddLocation.this);
		addLocationButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		dbclassObject.createDatabase();
		dbclassObject.open();

		if (dbclassObject.insertLocationData(cityEditText.getText().toString(),
				locationEditText.getText().toString(), addressEditText.getText()
						.toString())) {

			Toast.makeText(AddLocation.this, "Location Added", 2000).show();
			// dbclassObject.insertLocationData(cityEditText.getText().toString(),
			// cityEditText.getText().toString(), cityEditText.getText()
			// .toString());
			//
		} else {
			Toast.makeText(AddLocation.this, "Please try again Later", 2000).show();
		}
		dbclassObject.close();
	}

}
