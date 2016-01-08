package org.morse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "DBOpenHelper";	
	private static final String DATABASE_NAME = "morse.db";
//	private static final String TABLE_MORSE = "morse";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TABLE_MORSE = "CREATE TABLE IF NOT EXISTS "
	+ DBConstant.TABLE_MORSE
	+ "(_ID INTEGER PRIMARY KEY AUTOINCREMENT,key NVARCHAR(100), value NVARCHAR(100))";
	
	public DBOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_MORSE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
