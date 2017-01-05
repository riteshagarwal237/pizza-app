package com.pizza.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bean.PizzaBean;
import com.bean.SelectedPizzaItem;
import com.bean.ToppingsItem;
import com.pizza.R;
import com.pizza.database.AccessingDBClass;

public class PizzaDisplayActivity extends Activity {

	View v1, v2;

	// /https://pizzaonline.dominos.co.in/menu.php
	// http://www.pizzacorner.co.in/classic.asp
	// http://www.pizzahut.co.in/pizza_pan_veg.php
	// http://uniqueandroidtutorials.blogspot.in/2013/02/creating-dynamic-row-in-tablelayout.html
	// http://www.coderzheaven.com/2011/04/17/using-sqlite-in-android-a-really-simple-example/
	public ArrayList<PizzaBean> getPizzaList() {
		pizzaList.clear();

		dbobject.createDatabase();
		dbobject.open();
		// testadapter.SaveLanguages();
		dbobject.getVegPizzaList("veg");
		dbobject.close();
		Log.e("Pizza List :", "" + pizzaList.size());
		return pizzaList;

	}

	ArrayList<String> web1 = new ArrayList<String>();
	public String[] web = { " VEGGIE CRUNCH Pizza", "Cheese Pizza",
			"Olive Pizza", "Peppy Paneer", "Margherita-Single Cheese",
			"GARDEN FRESH", "TOMATINO"

	};

	Myadapter pizzadapter;
	GridView gridview;
	AccessingDBClass dbobject;
	// public static ArrayList<PizzaBean> selectedPizzaList = new
	// ArrayList<PizzaBean>();
	public static ArrayList<SelectedPizzaItem> selectedPizzaList = new ArrayList<SelectedPizzaItem>();
	ArrayList<PizzaBean> pizzaList = new ArrayList<PizzaBean>();
	// Button selctedPizzaButton, addButton;
	LinearLayout vegLayout, nonvegLayout;
	ArrayList<ToppingsItem> toppingsList = new ArrayList<ToppingsItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.e("++++++++++++PizzaDisplay Activity", "PizzaDisplay Activity");

		setContentView(R.layout.pizzsdisplaylayout);
		toppingsList.clear();
		dbobject = new AccessingDBClass(PizzaDisplayActivity.this);
		v1 = (View) findViewById(R.id.line1);
		v2 = (View) findViewById(R.id.line2);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// selctedPizzaButton = (Button) findViewById(R.id.selctedPizzaButton);
		// addButton = (Button) findViewById(R.id.addpizza);
		// addButton.setOnClickListener(onClickListener);
		// selctedPizzaButton.setOnClickListener(onClickListener);
		pizzadapter = new Myadapter();
		gridview = (GridView) findViewById(R.id.grid);
		gridview.setAdapter(pizzadapter);
		vegLayout = (LinearLayout) findViewById(R.id.favourite_shows_layout);
		vegLayout.setOnClickListener(onClickListener);
		nonvegLayout = (LinearLayout) findViewById(R.id.favourite_stars_layout);
		nonvegLayout.setOnClickListener(onClickListener);
		// getPizzaList();
		dbobject.createDatabase();
		dbobject.open();
		selectedPizzaList.clear();
		// dbobject.SaveLanguages();
		pizzaList.addAll(dbobject.getVegPizzaList("veg"));
		toppingsList.addAll(dbobject.getToppingsList());
		dbobject.close();
		pizzadapter.notifyDataSetChanged();
		Log.e("Pizza List :", "" + pizzaList.size());
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				arg0.setSelection(position);

				// selectedPizzaList.add(pizzaList.get(position));

				// selectedPizzaList.add(web[position]);
				// .setBackgroundColor(getResources().getColor(R.))

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		// selectedPizzaList.clear();
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.favourite_shows_layout:
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.INVISIBLE);
				pizzaList.clear();
				dbobject.createDatabase();
				dbobject.open();
				pizzaList.addAll(dbobject.getVegPizzaList("veg"));
				dbobject.close();
				pizzadapter.notifyDataSetChanged();
				break;
			case R.id.favourite_stars_layout:
				v1.setVisibility(View.INVISIBLE);
				v2.setVisibility(View.VISIBLE);
				pizzaList.clear();
				dbobject.createDatabase();
				dbobject.open();
				pizzaList.addAll(dbobject.getVegPizzaList("nonveg"));
				dbobject.close();
				pizzadapter.notifyDataSetChanged();
			}

		}
	};
	String ynvalue = "";
	SelectedPizzaItem pizzaobject;
	int[] imageId = { R.drawable.veggie, R.drawable.cheese_pizza,
			R.drawable.olive, R.drawable.panner_chilli, R.drawable.margarite,
			R.drawable.ic_launcher, R.drawable.tomatino };

	class Myadapter extends BaseAdapter {
		TextView textView;
		ImageView i1;

		@Override
		public int getCount() {
			return pizzaList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = LayoutInflater.from(PizzaDisplayActivity.this)
					.inflate(R.layout.grid_item, null);
			ImageView pizzaimageView = (ImageView) convertView
					.findViewById(R.id.pizzaimageView);
			pizzaimageView.setImageResource(pizzaList.get(position)
					.getImageID());
			// Log.e("imageId[position]", ""
			// + pizzaList.get(position).getImageID());
			// Log.e("imageId[position]", "" + imageId[position]);
			// pizzaimageView.setImageResource(imageId[position]);
			Button customiseButton = (Button) convertView
					.findViewById(R.id.customiseButton);
			textView = (TextView) convertView
					.findViewById(R.id.pizzaNameTextView);
			TextView regularCostTV = (TextView) convertView
					.findViewById(R.id.RegularPriceCost);
			TextView mediumCostTV = (TextView) convertView
					.findViewById(R.id.mediumPriceCost);
			TextView largeCostTV = (TextView) convertView
					.findViewById(R.id.largePriceCost);
			customiseButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					SelectedToppingsList.clear();
					final Dialog d1 = new Dialog(PizzaDisplayActivity.this);
					d1.requestWindowFeature(Window.FEATURE_NO_TITLE);
					d1.setContentView(R.layout.customitem_layout);
					d1.show();
					for (int i = 0; i < toppingsList.size(); i++) {
						toppingsList.get(i).setChecked(false);
					}
					Button goOrderButton = (Button) d1
							.findViewById(R.id.goOrderButton);
					Button addPizzaButton = (Button) d1
							.findViewById(R.id.addanotherpizzaButton);
					RadioGroup radiogroup = (RadioGroup) d1
							.findViewById(R.id.radioGroup1);
					RadioButton button1 = (RadioButton) d1
							.findViewById(R.id.regularButton);
					RadioButton button2 = (RadioButton) d1
							.findViewById(R.id.mediumButton);
					RadioButton button3 = (RadioButton) d1
							.findViewById(R.id.largeButton);
					pizzaobject = new SelectedPizzaItem();
					if (button1.isChecked()) {
						pizzaobject.setPizzaselectedPrice(pizzaList.get(
								position).getRegualrPrice());
					}
					radiogroup
							.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

								@Override
								public void onCheckedChanged(RadioGroup group,
										int checkedId) {

									if (checkedId == R.id.regularButton) {
										ynvalue = "regular";
										pizzaobject
												.setPizzaselectedPrice(pizzaList
														.get(position)
														.getRegualrPrice());
									} else if (checkedId == R.id.mediumButton) {
										ynvalue = "medium";
										pizzaobject
												.setPizzaselectedPrice(pizzaList
														.get(position)
														.getMediumPrice());

									} else if (checkedId == R.id.largeButton) {
										ynvalue = "large";
										pizzaobject
												.setPizzaselectedPrice(pizzaList
														.get(position)
														.getLargePrice());
									}
								}
							});

					pizzaobject.setPizzaName(pizzaList.get(position)
							.getItemName());

					ListView lv = (ListView) d1
							.findViewById(R.id.toppingsListView);
					ToppingsAdapter toppingsAdapter = new ToppingsAdapter();
					lv.setAdapter(toppingsAdapter);
					goOrderButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							if (toppingsList.size() > 0) {

								for (int i = 0; i < toppingsList.size(); i++) {
									if (toppingsList.get(i).isChecked()) {
										SelectedToppingsList.add(toppingsList
												.get(i));
										// selectedPizzaList.add(pizzaobject);
									}
								}
								pizzaobject
										.setToppingsList(SelectedToppingsList);
							} else {
								// selectedPizzaList.add(pizzaobject);
							}
							d1.dismiss();
							selectedPizzaList.add(pizzaobject);
							Intent displayIntent = new Intent(
									PizzaDisplayActivity.this,
									NewSelectionOrderActivity.class);
							// displayIntent.putExtra("SizeType", ynvalue);
							startActivity(displayIntent);
						}
					});
					addPizzaButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							ArrayList<ToppingsItem> myheadToppingsList = new ArrayList<ToppingsItem>();
							myheadToppingsList.clear();
							if (toppingsList.size() > 0) {

								for (int i = 0; i < toppingsList.size(); i++) {
									if (toppingsList.get(i).isChecked()) {
										myheadToppingsList.add(toppingsList
												.get(i));
										// selectedPizzaList.add(pizzaobject);
									}
								}
								pizzaobject.setToppingsList(myheadToppingsList);
							} else {
								// selectedPizzaList.add(pizzaobject);
							}
							selectedPizzaList.add(pizzaobject);

							Log.e("Already selectd Pizza", "Size :"
									+ selectedPizzaList.size());
							for (int i = 0; i < selectedPizzaList.size(); i++) {
								Log.e("selectedPizzaList", "Name :"
										+ selectedPizzaList.get(i)
												.getPizzaName());
								Log.e("selectedPizzaList", "Price :"
										+ selectedPizzaList.get(i)
												.getPizzaselectedPrice());
								Log.e("selectedPizzaList", "Toppings List :"
										+ selectedPizzaList.get(i)
												.getToppingsList());

								for (int k = 0; k < selectedPizzaList.get(i)
										.getToppingsList().size(); k++) {
									Log.e("toppingsList", "Toppings List :"
											+ selectedPizzaList.get(i)
													.getToppingsList().get(k)
													.getToppingsName());
								}
							}

							d1.dismiss();
						}
					});

				}
			});
			i1 = (ImageView) convertView.findViewById(R.id.pizzaimageView);
			textView.setText(pizzaList.get(position).getItemName());
			regularCostTV.setText(""
					+ pizzaList.get(position).getRegualrPrice() + " "
					+ getResources().getString(R.string.Rs));
			mediumCostTV.setText("" + pizzaList.get(position).getMediumPrice()
					+ " " + getResources().getString(R.string.Rs));
			largeCostTV.setText("" + pizzaList.get(position).getLargePrice()
					+ " " + getResources().getString(R.string.Rs));

			return convertView;
		}
	}

	public static ArrayList<ToppingsItem> SelectedToppingsList = new ArrayList<ToppingsItem>();

	class ToppingsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return toppingsList.size();
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
		public View getView(final int position, View convertView, ViewGroup arg2) {

			convertView = LayoutInflater.from(PizzaDisplayActivity.this)
					.inflate(R.layout.toppings_view, null);
			TextView tv = (TextView) convertView
					.findViewById(R.id.toppingsNameTextView);
			tv.setText(toppingsList.get(position).getToppingsName() + "-"
					+ toppingsList.get(position).getToppingsRate());
			CheckBox ch1 = (CheckBox) convertView
					.findViewById(R.id.toppingsCheckbox);
			ch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean ischecked) {
					Log.e("+++++ On checked change Listener",
							"+++++ On checked change Listener");
					if (ischecked) {
						Log.e("++++++++++++++++ checked ", "" + ischecked);

						toppingsList.get(position).setChecked(true);
						// selectedPizzaList.get(0).setToppingsList(SelectedToppingsList)
						// selectedFriendsArrayList.add(temp_arraylist.get(arg0));
						// selectedPizzaList.get(0).setToppingsList(toppingsList.set(index,
						// object))
						// SelectedToppingsList.add(toppingsList.get(position));
					} else {
						toppingsList.get(position).setChecked(false);
						// SelectedToppingsList.remove(toppingsList.get(position));

					}

				}
			});
			return convertView;
		}
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // Handle the back button
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	//
	// new AlertDialog.Builder(this)
	// .setIcon(android.R.drawable.ic_dialog_alert)
	// .setTitle("Confirm")
	// .setMessage("Do you want to exit from the application?")
	// .setPositiveButton("yes",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // sendMessage(22);
	// // // Stop the activity
	// // editor = app_preferences.edit();
	// // editor.putString("id", "");
	// // editor.commit();
	// PizzaDisplayActivity.this.finish();
	// }
	//
	// }).setNegativeButton("No", null).show();
	//
	// return true;
	// } else {
	// return super.onKeyDown(keyCode, event);
	// }
	//
	// }

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		AlertDialog exit_dialog = new AlertDialog.Builder(
				PizzaDisplayActivity.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Confirm")
				.setMessage("Do you want to exit from the application?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// sendMessage(22);
								// Stop the activity
								// editor = app_preferences.edit();
								// editor.putString("id", "");
								// editor.commit();
								// MainActivity.this.finish();
								// listeners.updateChanged("");
								finish();

							}

						}).setNegativeButton("No", null).show();
	}
}
