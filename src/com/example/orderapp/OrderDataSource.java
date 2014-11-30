package com.example.orderapp;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import com.example.orderapp.Order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class OrderDataSource {
	private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_ORDER_NAME,MySQLiteHelper.COLUMN_ORDER_AMOUNT };

	  public OrderDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Order createOrder(Order o) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ORDER_NAME, o.getName());
	    values.put(MySQLiteHelper.COLUMN_ORDER_AMOUNT, o.getAmount());
	    long insertId = database.insert(MySQLiteHelper.DATABASE_TABLE, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.DATABASE_TABLE,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Order newOrder = cursorToOrder(cursor);
	    cursor.close();
	    return newOrder;
	  }

	  public List<Order> getAllOrders() {
	    List<Order> orders = new ArrayList<Order>();

	    Cursor cursor = database.query(MySQLiteHelper.DATABASE_TABLE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Order order = cursorToOrder(cursor);
	      orders.add(order);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return orders;
	  }

	  private Order cursorToOrder(Cursor cursor) {
	    Order order = new Order();
	    order.setId(cursor.getLong(0));
	    order.setName(cursor.getString(1));
	    order.setAmount(cursor.getLong(2));
	    return order;
	  }

}
