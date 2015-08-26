package com.example.mall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBHelper dBHelper;
	private SQLiteDatabase db;
	private static DBManager instance = null;
	private Context context;
	
	private TFavorite tFavorite;

	public static DBManager getInstance(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
		return instance;
	}

	public DBManager(Context context) {
		dBHelper = DBHelper.getInstance(context);
		db = dBHelper.getWritableDatabase();
		this.context = context;

	}
	
	public TFavorite TFavorite() {
		if (tFavorite == null) {
			tFavorite = new TFavorite(db);
		}
		return tFavorite;
	}
}
