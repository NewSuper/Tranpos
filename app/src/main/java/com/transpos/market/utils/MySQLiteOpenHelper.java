package com.transpos.market.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLiteOpenHelper {

	private static final String TAG = "MySQLiteOpenHelper";
	private static MySQLiteOpenHelper _instance = null;

	/**
	 * 单例
	 * @return
	 */
	public static MySQLiteOpenHelper getInstance(){
		if(_instance == null){
			synchronized(MySQLiteOpenHelper.class) {
				if(_instance == null){
					_instance = new MySQLiteOpenHelper(null);
				}
			}
		}
		return _instance;
	}


	private MySQLiteOpenHelper(Context context) {

	}

	public <T> List<T> query2Entity(Class<T> t, SupportSQLiteDatabase db,String sql){
		return query2Entity(t, db,sql, null);
	}


	public <T> List<T> query2Entity(Class<T> t,SupportSQLiteDatabase db, String sql, String[] selectionArgs){

		List<Map<String, String>> result = query(db,sql, selectionArgs);
		List<T> tr = JsonTools.deserialize4Array(JsonTools.serialize(result), t);
		if(tr == null){
			tr = new ArrayList<T>();
		}
		return tr;
	}

	public List<Map<String, String>> query(SupportSQLiteDatabase db,String sql){
		return query(db,sql, new String[]{});
	}

	public List<Map<String, String>> query(SupportSQLiteDatabase db,String sql, String[] selectionArgs){
		List<Map<String, String>> result = new ArrayList<>();
		Cursor cursor = null;
		try{
			cursor = db.query(sql, selectionArgs);
			result = new ArrayList<>(cursor.getCount());
			if(cursor.moveToFirst()){
				do{
					Map<String, String> map = new HashMap<>(cursor.getColumnCount());
					for (String columnName : cursor.getColumnNames()) {
						map.put(columnName, cursor.getString(cursor.getColumnIndex(columnName)));
					}
					result.add(map);
				}while (cursor.moveToNext());
			}
		}catch (Exception ex){
			LogUtil.e(this, "查询发生异常", ex);
		}finally {
			if(cursor != null){
				cursor.close();
			}
		}
		return result;
	}

	/**
	 * 执行单条sql
	 *
	 * @param sql
	 */
	public void execSQL(SQLiteDatabase db,String sql) {
		db.execSQL(sql);
	}

	/**
	 * 事务性的批量执行
	 * @param lists
	 * @return
	 */
	public synchronized void execTransaction(SQLiteDatabase db,List<String> lists) throws Exception {
		try{
			db.beginTransaction();
			for (String sql : lists) {
				//替换为空的值
				sql = sql.replaceAll("'null'", "NULL");
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
		}catch (Exception ex){
			LogUtil.e(TAG, "execTransaction:事务性批量执行发生异常", ex);
			throw new Exception(ex.getMessage());
		}finally {
			db.endTransaction();
		}
	}

	/**
	 * 根据参数删除
	 *
	 * @param sql
	 */
	public void delete(SQLiteDatabase db,String sql, Object[] data) {
		db.execSQL(sql, data);

	}

	/**
	 * 根据参数更新
	 *
	 * @param sql
	 */
	public void update(SQLiteDatabase db,String sql, Object[] data) {
		db.execSQL(sql, data);
	}

}
