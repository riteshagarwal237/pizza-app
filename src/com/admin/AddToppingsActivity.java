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

public class AddToppingsActivity extends Activity {

	EditText toppingNameEditText, toppingPriceEditText;
	Button addButton;
	AccessingDBClass dataObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert_toppings);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		toppingNameEditText = (EditText) findViewById(R.id.toppingsNameEditText);
		toppingPriceEditText = (EditText) findViewById(R.id.toppingPriceEditText);
		addButton = (Button) findViewById(R.id.addToppingsButton);
		dataObject = new AccessingDBClass(AddToppingsActivity.this);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (toppingNameEditText.getText().length() != 0
						&& toppingPriceEditText.getText().length() != 0) {
					dataObject.createDatabase();
					dataObject.open();
					if (dataObject.insertNewToppings(toppingNameEditText
							.getText().toString(),
							Integer.parseInt(toppingPriceEditText.getText()
									.toString()))) {
						Toast.makeText(AddToppingsActivity.this,
								"Topping Item Inserted", 2000).show();
					} else {
						Toast.makeText(AddToppingsActivity.this,
								"Topping Item already added", 2000).show();
					}

					dataObject.close();
				} else {
					Toast.makeText(AddToppingsActivity.this,
							"Please fill the fields", 2000).show();
				}

			}
		});
	}
}
