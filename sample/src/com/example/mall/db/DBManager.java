package com.example.mall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mall.constant.ConstantSettings;
import com.lidroid.xutils.DbUtils;

public class DBManager {
	private static DBManager instance = null;
	private Context mContext;
	private DbUtils dbUtils;
	
	private TFavorite tFavorite;

	public static DBManager init(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
		return instance;
	}

	public DBManager(Context context) {
		dbUtils = DbUtils.create(context, ConstantSettings.dbName, ConstantSettings.dbVersion, new DatabaseUpgradeListener());
		this.mContext = context;
	}

	public static DBManager getDBManager(){
		return instance;
	}


	public class DatabaseUpgradeListener implements DbUtils.DbUpgradeListener {

		@Override
		public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
			// 使用for实现跨版本升级数据库
			for (int i = oldVersion; i < newVersion; i++) {
				switch (i) {

					default:
						break;
				}
			}
		}
	}
}
