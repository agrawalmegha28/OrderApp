package com.example.orderapp;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class CreateOrderActivity extends Activity {
	private OrderDataSource datasource;
	private EditText etOrderName, etAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_order);
		etOrderName = (EditText)findViewById(R.id.etOrderName);
		etAmount = (EditText)findViewById(R.id.etAmount);
		datasource = new OrderDataSource(this);
	    datasource.open();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_order, menu);
		return true;
	}
	public void onSave(View view){
		Order order = new Order();
		order.setAmount(Long.parseLong(etAmount.getText().toString()));
		order.setName(etOrderName.getText().toString());
		datasource.createOrder(order);
		Toast.makeText(this, "Order created successfully", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
