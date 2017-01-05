package com.admin;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bean.OrderListItem;
import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class PizzaOrdersActivity extends Activity {
	ListView lv;
	ArrayList<OrderListItem> ordersList = new ArrayList<OrderListItem>();
	AccessingDBClass dbobject;
	OrdersAdapter orderAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		lv = (ListView) findViewById(R.id.listView1);
		ordersList.clear();
		dbobject = new AccessingDBClass(PizzaOrdersActivity.this);
		dbobject.createDatabase();
		dbobject.open();
		ordersList.addAll(dbobject.getPizzOrdersList());
		dbobject.close();
		orderAdapter = new OrdersAdapter();
		lv.setAdapter(orderAdapter);
	}

	class OrdersAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ordersList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			convertView = LayoutInflater.from(PizzaOrdersActivity.this)
					.inflate(R.layout.order_item_view, null);

			TextView ordertypeTV = (TextView) convertView
					.findViewById(R.id.OrderTypeTV);
			TextView orderPrice = (TextView) convertView
					.findViewById(R.id.billAmountTV);
			TextView orderAddress = (TextView) convertView
					.findViewById(R.id.pizzaAdressTV);

			TextView pizzaOrderDescription = (TextView) convertView
					.findViewById(R.id.pizzaOrderDescription);
			ordertypeTV.setText(ordersList.get(position).getOrderType());
			orderPrice.setText("Bill Amount :"
					+ ordersList.get(position).getPrice());
			orderAddress.setText(ordersList.get(position).getAddress());
			pizzaOrderDescription.setText(ordersList.get(position)
					.getOrderDescription());
			return convertView;
		}
	}

}
