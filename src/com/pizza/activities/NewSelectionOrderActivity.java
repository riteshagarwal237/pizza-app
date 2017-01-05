package com.pizza.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bean.SelectedPizzaItem;
import com.pizza.R;

;
public class NewSelectionOrderActivity extends Activity implements
		OnClickListener {

	// TableLayout tableLayout;
	LinearLayout mLinear;
	LinearLayout quantityLayout;
	public static ArrayList<SelectedPizzaItem> pizzaList = new ArrayList<SelectedPizzaItem>();

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		PizzaDisplayActivity.selectedPizzaList.clear();
	}

	// ArrayList<int> priceList = new ArrayList<int>();
	int amount = 0;
	int quantity = 1;
	Button selectedOrderButton;
	int position;
	String pizzaDetailWithToppings;

	// ImageView decremntTV;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_selected_order_price);
		selectedOrderButton = (Button) findViewById(R.id.selectedOrderButton);
		selectedOrderButton.setOnClickListener(this);
		mLinear = (LinearLayout) findViewById(R.id.BillingListLayout);
		pizzaList.clear();
		pizzaList.addAll(PizzaDisplayActivity.selectedPizzaList);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mLinear.removeAllViews();
		for (position = 0; position < pizzaList.size(); position++) {
			amount = 0;
			View view = LayoutInflater.from(NewSelectionOrderActivity.this)
					.inflate(R.layout.bill_amount, null);
			LinearLayout toppingsListLayout = (LinearLayout) view
					.findViewById(R.id.toppingsListLayout);

			TextView tname = (TextView) view.findViewById(R.id.textViewName);
			tname.setTextColor(getResources().getColor(R.color.orange));

			TextView tamount = (TextView) view
					.findViewById(R.id.textViewAmount);
			tamount.setTextColor(R.color.orange);
			pizzaDetailWithToppings = pizzaList.get(position).getPizzaName();
			tname.setText(pizzaList.get(position).getPizzaName());
			tamount.setText(""
					+ pizzaList.get(position).getPizzaselectedPrice());
			amount = pizzaList.get(position).getPizzaselectedPrice();
			mLinear.addView(view);
			for (int i = 0; i < pizzaList.get(position).getToppingsList()
					.size(); i++) {
				View view1 = LayoutInflater
						.from(NewSelectionOrderActivity.this).inflate(
								R.layout.bill_amount, null);

				TextView tname1 = (TextView) view1
						.findViewById(R.id.textViewName);
				TextView tamount1 = (TextView) view1
						.findViewById(R.id.textViewAmount);
				tname1.setText(pizzaList.get(position).getToppingsList().get(i)
						.getToppingsName());
				tamount1.setText(""
						+ pizzaList.get(position).getToppingsList().get(i)
								.getToppingsRate());
				toppingsListLayout.addView(view1);
				pizzaDetailWithToppings = pizzaDetailWithToppings
						+ " + "
						+ pizzaList.get(position).getToppingsList().get(i)
								.getToppingsName();
				amount = amount
						+ pizzaList.get(position).getToppingsList().get(i)
								.getToppingsRate();

			}
			pizzaList.get(position).setPizzaName(pizzaDetailWithToppings);
			pizzaList.get(position).setPizzaselectedPrice(amount);
			View view2 = LayoutInflater.from(NewSelectionOrderActivity.this)
					.inflate(R.layout.quantity_layout, null);
			quantityLayout = (LinearLayout) view2
					.findViewById(R.id.quantityLayout);
			quantityLayout.setVisibility(View.INVISIBLE);
			final TextView SizeEditText = (TextView) view2
					.findViewById(R.id.SizeEditText);
			final TextView pizzaTotalAmountTextView = (TextView) view2
					.findViewById(R.id.pizzaTotalAmountTextView);

			// final ImageView decremntTV = (ImageView) view2
			// .findViewById(R.id.decrementImageView);
			// final ImageView incremntIV = (ImageView) view2
			// .findViewById(R.id.incrementImageView);
			// quantity = 1;
			// // decremntTV.setId(position);
			// decremntTV.setId(position);
			// // priceList.set(position, amount);
			// decremntTV.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			//
			// if (arg0.getId() == decremntTV.getId()) {
			// Log.e("Onclick Decrement",
			// ""
			// + Integer.parseInt(SizeEditText
			// .getText().toString()));
			// // quantity = quantity - 1;
			//
			// if (quantity > 1) {
			// quantity = Integer.parseInt(SizeEditText.getText()
			// .toString()) - 1;
			// }
			// Log.e("+++++++quantity Decrement :", "" + quantity);
			// Log.e("Onclick  Decrement :", "" + amount);
			// SizeEditText.setText("" + quantity);
			// pizzaTotalAmountTextView
			// .setText("" + amount * quantity);
			// // pizzaList.get(position).setPizzaselectedPrice(
			// // amount * quantity);
			// }
			// }
			// });
			// incremntIV.setId(position);
			// incremntIV.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// if (v.getId() == incremntIV.getId()) {
			// Log.e("Onclick Increment",
			// ""
			// + Integer.parseInt(SizeEditText
			// .getText().toString()));
			// Log.e("Onclick Increment amount :", "" + amount);
			// if (quantity < 10) {
			// quantity = Integer.parseInt(SizeEditText.getText()
			// .toString()) + 1;
			// }
			//
			// Log.e("Onclick Increment",
			// "pizzaList " + pizzaList.size());
			// Log.e("+++++++quantity incremntIV :", "" + quantity);
			// SizeEditText.setText("" + quantity);
			// pizzaTotalAmountTextView.setText(""
			// + pizzaList.get(v.getId())
			// .getPizzaselectedPrice() * quantity);
			// // pizzaList.get(position).setPizzaselectedPrice(
			// // amount * quantity);
			// // pizzaList.set(position, object)
			// }
			// }
			// });

			pizzaTotalAmountTextView.setText("" + amount * quantity);
			// Log.e("+++ Pizza Item Total Amount :", "" + amount * quantity);
			// pizzaList.get(position).setPizzaselectedPrice(amount * quantity);
			mLinear.addView(view2);
		}

	}

	@Override
	public void onClick(View arg0) {

		Intent pricefinalIntent = new Intent(NewSelectionOrderActivity.this,
				TotalAmountActivity.class);
		startActivity(pricefinalIntent);

	}

}
