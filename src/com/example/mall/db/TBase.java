package com.example.mall.db;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class TBase<T> {
	protected SQLiteDatabase db;
	protected String tableName;
	protected String uniqueIDName;

	public TBase(SQLiteDatabase db, String tableName, String uniqueIDName) {
		this.db = db;
		this.tableName = tableName;
		this.uniqueIDName = uniqueIDName;
	}
	
	public abstract boolean add(T obj);
	public abstract boolean update(T obj);
	public abstract T get(String id);
	public abstract List<T> getAll();

	/**
	 * 根据id获取 
	 * @param tableName 表
	 * @param columnName
	 * @param id 
	 * @return Cursor
	 */
	public Cursor baseGet(String tableName, String columnName, String id) {
		return db.query(tableName, null, columnName + "=?", new String[] { id }, null, null, null);
	}
	
	public Cursor baseGetAll(String tableName) {
		return db.query(tableName, null, null, null, null, null, null);
	}
	
	/**
	 * 根据id删除
	 * @param tableName
	 * @param columnName
	 * @param id
	 * @return
	 */
	public boolean baseDelete(String tableName, String columnName, String id){
		int result = db.delete(tableName, columnName + "=?", new String[]{ id });
		if(result > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 插入表数据
	 * @param tableName
	 * @param cv
	 * @return
	 */
	public boolean baseAdd(String tableName, ContentValues cv){
		long result = db.insert(tableName, null, cv);
		if(result > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 更新表数据
	 * @param tableName
	 * @param cv
	 * @param columnName
	 * @param id
	 * @return
	 */
	public boolean baseUpdate(String tableName, ContentValues cv, String columnName, String id){
		int result = db.update(tableName, cv, columnName + "=?", new String[]{ id } );
		if(result > 0){
			return true;
		}
		return false;
	}
	
}