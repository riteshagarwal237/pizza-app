package com.pizza.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bean.SelectedPizzaItem;
import com.pizza.R;

public class TotalAmountActivity extends Activity {
	LinearLayout finalBillingListLayout;
	Button confirmorderButton;

	ArrayList<SelectedPizzaItem> pizzaList;
	TextView totalAmountTextView;
	int amountTotal = 0;
	// int pizza
	int quantity;

	ArrayList<Integer> priceList = new ArrayList<Integer>();
	Handler myhandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.total_amount_layout);
		finalBillingListLayout = (LinearLayout) findViewById(R.id.finalBillingListLayout);
		confirmorderButton = (Button) findViewById(R.id.confirmorderButton);
		totalAmountTextView = (TextView) findViewById(R.id.totalAmountTextView);
		pizzaList = new ArrayList<SelectedPizzaItem>();
		pizzaList.clear();
		priceList.clear();
		quantity = 1;
		pizzaList.addAll(NewSelectionOrderActivity.pizzaList);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		for (int i = 0; i < pizzaList.size(); i++) {
			// String pizzaNameAndDetails = null;
			View view = LayoutInflater.from(TotalAmountActivity.this).inflate(
					R.layout.bill_amount, null);
			LinearLayout toppingsListLayout = (LinearLayout) view
					.findViewById(R.id.toppingsListLayout);
			TextView tname = (TextView) view.findViewById(R.id.textViewName);
			TextView tamount = (TextView) view
					.findViewById(R.id.textViewAmount);
			amountTotal = amountTotal
					+ pizzaList.get(i).getPizzaselectedPrice();
			tamount.setVisibility(View.INVISIBLE);
			Log.e("Get Selected Price", "" + amountTotal);
			tname.setText(pizzaList.get(i).getPizzaName());
			tamount.setText("" + pizzaList.get(i).getPizzaselectedPrice());
			View amountView = LayoutInflater.from(TotalAmountActivity.this)
					.inflate(R.layout.quantity_layout, null);
			final TextView SizeEditText = (TextView) amountView
					.findViewById(R.id.SizeEditText);
			final TextView pizzaTotalAmountTextView = (TextView) amountView
					.findViewById(R.id.pizzaTotalAmountTextView);

			ImageView decremntTV = (ImageView) amountView
					.findViewById(R.id.decrementImageView);
			ImageView incremntIV = (ImageView) amountView
					.findViewById(R.id.incrementImageView);
			incremntIV.setId(i);
			pizzaTotalAmountTextView.setText(""
					+ pizzaList.get(i).getPizzaselectedPrice());
			incremntIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if (quantity < 50) {

						quantity = Integer.parseInt(SizeEditText.getText()
								.toString()) + 1;
						Log.e("Incrementd", "" + quantity);
						SizeEditText.setText(""
								+ (Integer.parseInt(SizeEditText.getText()
										.toString()) + 1));
						pizzaTotalAmountTextView.setText(String
								.valueOf(pizzaList.get(arg0.getId())
										.getPizzaselectedPrice() * quantity));
						priceList.set(arg0.getId(), pizzaList.get(arg0.getId())
								.getPizzaselectedPrice() * quantity);
						myhandler.post(new Runnable() {

							@Override
							public void run() {
								int total = 0;
								for (int i = 0; i < priceList.size(); i++) {
									total = total + priceList.get(i);
								}
								totalAmountTextView.setText("" + total);
							}
						});

					}
				}
			});

			decremntTV.setId(i);
			decremntTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (quantity > 1) {
						quantity = Integer.parseInt(SizeEditText.getText()
								.toString()) - 1;
						Log.e("Decremented", "" + quantity);
						SizeEditText.setText(""
								+ (Integer.parseInt(SizeEditText.getText()
										.toString()) - 1));
						pizzaTotalAmountTextView.setText(String
								.valueOf(pizzaList.get(v.getId())
										.getPizzaselectedPrice() * quantity));

						priceList.set(v.getId(), pizzaList.get(v.getId())
								.getPizzaselectedPrice() * quantity);
						myhandler.post(new Runnable() {

							@Override
							public void run() {
								int total = 0;
								for (int i = 0; i < priceList.size(); i++) {
									total = total + priceList.get(i);
								}
								totalAmountTextView.setText("" + total);
							}
						});

					}
				}
			});
			toppingsListLayout.addView(amountView);
			finalBillingListLayout.addView(view);
			priceList.add(pizzaList.get(i).getPizzaselectedPrice());
		}
		totalAmountTextView.setText("" + amountTotal);
		confirmorderButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent confirmIntent = new Intent(TotalAmountActivity.this,
						BillAndOrderConfirmActivity.class);

				confirmIntent.putExtra("TotalAmount", amountTotal);
				// confirmIntent.putExtra("PizzaDescription", amountTotal);
				startActivity(confirmIntent);

			}
		});
	}

}
