package com.example.mall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBHelper helper;
	private SQLiteDatabase db;
	private static DBManager instance = null;
	private Context context;

	public static DBManager getInstance(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
		return instance;
	}

	public DBManager(Context context) {
		db = helper.getWritableDatabase();
		this.context = context;

	}
}
