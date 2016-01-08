package org.morse.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.morse.constant.Abbreviation;
import org.morse.constant.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	private final static String TAG = "DBManager";
	private static DBManager dbManager = null;
	private DBOpenHelper helper = null;
	private SQLiteDatabase db = null;
	private DBManager(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
		fill_DB(Constant.class);
		fill_DB(Abbreviation.class);
	}
	
	public static DBManager getInstance(Context context){
		if(dbManager == null){
			dbManager = new DBManager(context);
		}
		return dbManager;
	}
	/**
	 * 插入
	 * @param key
	 * @param value
	 * @return 插入行号
	 */
	public long insert(String key, String value){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.TABLE_MORSE_KEY, key);
		contentValues.put(DBConstant.TABLE_MORSE_VALUE, value);
		long result = db.insert(DBConstant.TABLE_MORSE, null, contentValues);
		return result;
	}
	/**
	 * 根据key删除
	 * @param key
	 * @return 删除的行数
	 */
	public int deleteByKey(String key){
		int result = db.delete(DBConstant.TABLE_MORSE, 
				DBConstant.TABLE_MORSE_KEY + "=?",
				new String[]{key});
		return result;
	}
	/**
	 * 根据value删除
	 * @param value
	 * @return 删除的行数
	 */
	public int deleteByValue(String value){
		int result = db.delete(DBConstant.TABLE_MORSE, 
				DBConstant.TABLE_MORSE_VALUE + "=?",
				new String[]{value});
		return result;
	}
	/**
	 * 更新key
	 * @param key
	 * @return 更新的行数
	 */
	public int updateKey(String key, String value){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.TABLE_MORSE_KEY, key);
		int result = db.update(DBConstant.TABLE_MORSE, 
				contentValues, DBConstant.TABLE_MORSE_VALUE + "=?",
				new String[]{value});
		return result;
	}
	/**
	 * 更新value
	 * @param key
	 * @return 更新的行数
	 */
	public int updateValue(String key, String value){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.TABLE_MORSE_VALUE, value);
		int result = db.update(DBConstant.TABLE_MORSE,
				contentValues,
				DBConstant.TABLE_MORSE_KEY + "=?",
				new String[]{key});
		return result;
	}	
	/**
	 * 根据value查询
	 * @param value
	 * @return 查询的行数
	 */
	public int queryKey(String value){
		Cursor cursor = db.query(DBConstant.TABLE_MORSE,
				new String[]{DBConstant.TABLE_MORSE_KEY},
				DBConstant.TABLE_MORSE_VALUE + "=?",
				new String[]{value},
				null,
				null,
				null);
		return cursor.getColumnCount();
	}
	/**
	 * 根据key查询
	 * @param key
	 * @return 查询的行数
	 */
	public int queryValue(String key){
		Cursor cursor = db.query(DBConstant.TABLE_MORSE,
				new String[]{DBConstant.TABLE_MORSE_KEY},
				DBConstant.TABLE_MORSE_KEY + "=?",
				new String[]{key},
				null,
				null,
				null);
		return cursor.getColumnCount();
	}
	public Cursor queryAll(){
		Cursor c = db.query(DBConstant.TABLE_MORSE,
				new String[]{DBConstant.TABLE_MORSE_KEY, DBConstant.TABLE_MORSE_VALUE},
				null,
				null,
				null,
				null,
				null);
		while(c.moveToNext()){
			Log.v(TAG, "key = " + c.getString(c.getColumnIndex("key")) + 
					", value = " + c.getString(c.getColumnIndex("value")));
		}
		return c;
	}
	public void fill_DB(Class clas){
		Field[] fields = clas.getDeclaredFields();
		for(Field field : fields){
			String key = field.getName();
			String str = new String(key);
			if(key.length() > 1){
				String[] keys = key.split("_");
				str = "";
				for(String temp :keys){
					temp = temp.substring(0, 1) + temp.substring(1).toLowerCase();
					str += temp;
				}
//				Log.v(TAG, "str = " + str);
			}
			Method method;
			Object object;
			try {
				method = clas.getMethod("get" + str, new Class[]{});
				object = method.invoke(clas, new Object[]{});
//				Log.v(TAG, "value = " + String.valueOf(object));
				insert(key, String.valueOf(object));
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		queryAll();
	}
}
