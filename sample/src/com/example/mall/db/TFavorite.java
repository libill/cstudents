package com.example.mall.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mall.model.Favorite;

public class TFavorite extends TBase<Favorite> {
	private String tableName = SQL.T_FAVORITE;

	private static String uniqueIDName = "id";

	public TFavorite(SQLiteDatabase db) {
		super(db, SQL.T_FAVORITE, uniqueIDName);
	}

	@Override
	public boolean add(Favorite obj) {
		boolean result = false;
		ContentValues cv = getContentValues(obj);
		Cursor cursor = baseGet(tableName, uniqueIDName, obj.getId());
		// 未存在添加，已存在更新
		if (cursor.getCount() == 0) {
			result = baseAdd(tableName, cv);
		} else {
			result = update(obj);
		}
		return result;
	}

	@Override
	public boolean update(Favorite obj) {
		boolean result = false;
		ContentValues cv = getContentValues(obj);
		result = baseUpdate(tableName, cv, uniqueIDName, obj.getId());
		return result;
	}

	@Override
	public Favorite get(String id) {
		Favorite favorite = null;
		Cursor cursor = baseGet(tableName, uniqueIDName, id);
		if (cursor.moveToFirst()) {
			favorite = getFavorite(cursor);
		}
		cursor.close();
		return favorite;
	}
	
	/**
	 * 判断该路径是否已经存在
	 * @param obj
	 * @return
	 */
	public boolean addByUrl(Favorite obj) {
		boolean result = false;
		ContentValues cv = getContentValues(obj);
		Cursor cursor = db.query(tableName, null, "url" + "=?", new String[] { obj.getUrl() }, null, null, null);;
		// 未存在添加，已存在更新
		if (cursor.getCount() == 0) {
			result = baseAdd(tableName, cv);
		} else {
			//TODO: 存在，使用id无法更新这里
			result = update(obj);
		}
		return result;
	}
	
	/**
	 * 判断链接是否已存在
	 * @param url
	 * @return
	 */
	public boolean isExitByUrl(String url) {
		boolean result = false;
		Cursor cursor = db.query(tableName, null, "url" + "=?", new String[] { url }, null, null, null);;
		if (cursor.getCount() == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	@Override
	public List<Favorite> getAll() {
		List<Favorite> list = new ArrayList<Favorite>();
		Cursor cursor = baseGetAll(tableName);
		while (cursor.moveToNext()) {
			Favorite favorite = getFavorite(cursor);
			list.add(favorite);
		}
		cursor.close();
		return list;
	}

	private ContentValues getContentValues(Favorite obj) {
		ContentValues cv = new ContentValues();
		cv.put("id", obj.getId());
		cv.put("title", obj.getTitle());
		cv.put("url", obj.getUrl());
		cv.put("createDate", obj.getCreateDate());
		return cv;
	}

	private Favorite getFavorite(Cursor cursor) {
		Favorite favorite = new Favorite();

		String id = cursor.getString(cursor.getColumnIndex("id"));
		String title = cursor.getString(cursor.getColumnIndex("title"));
		String url = cursor.getString(cursor.getColumnIndex("url"));
		String createDate = cursor.getString(cursor.getColumnIndex("createDate"));

		favorite.setId(id);
		favorite.setTitle(title);
		favorite.setUrl(url);
		favorite.setCreateDate(createDate);

		return favorite;
	}
}
