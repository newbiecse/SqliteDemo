package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	private static final String DB_NAME = "Employee";
	private static final String DB_TABLE = "Employee";
	private static final int DB_VERSION = 1;
	
	private static final String DB_CREATE = 
			"CREATE TABLE Employee (" +
			"_id integer PRIMARY KEY AUTOINCREMENT," +
			"password text," +		
			"firstName text," +
			"lastName text," +
			"phone integer," +
			"salary real," +			
			"departmentID integer);";
	
	
	
	private static final String DB_UPGRADE =
			"DROP TABLE IF EXISTS Employee";
		 
	public static final String COL_ID = "_id";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "firstName";
	public static final String COL_LASTNAME = "lastName";
	public static final String COL_PHONE = "phone";
	public static final String COL_SALARY = "salary";	
	public static final String COL_DEPARTMENTID = "departmentID";	
	
	
	private SQLiteDatabase mDB;
	private DBHelper mDBHelper;
	private Context mCtx;
	
	private static class DBHelper extends SQLiteOpenHelper
	{
		public DBHelper(Context context)
		{
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DB_UPGRADE);
		}		
	}

	public DBAdapter(Context ctx)
	{
		this.mCtx = ctx;
	}
	
	//OPEN METHOD AND CLOSE METHOD
	public DBAdapter open()
	{
		mDBHelper = new DBHelper(mCtx);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		mDBHelper.close();
	}
	
	//INSERT AND DELETE METHOD
	
	
	public long CreateEmployee(String password, String firstName, String lastName, int phone, float salary, int departmentID)
	{
		ContentValues v = new ContentValues();
		v.put(COL_PASSWORD, password);
		v.put(COL_FIRSTNAME, firstName);
		v.put(COL_LASTNAME, lastName);
		v.put(COL_PHONE, phone);
		v.put(COL_SALARY, salary);
		v.put(COL_DEPARTMENTID, departmentID);
		return mDB.insert(DB_TABLE, null, v);
	}
	
	public boolean DeleteEmployee(long id)
	{
		return mDB.delete(DB_TABLE, COL_ID + "=" + id, null) > 0;
	}
	
	public boolean DeleteAllEmployee()
	{
		return mDB.delete(DB_TABLE, null, null) > 0;
	}
	
	//GET ALL Employee METHOD
	
	public Cursor GetAllEmployee()
	{
		return mDB.query(DB_TABLE, 
				new String[] {COL_ID, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, COL_PHONE, COL_SALARY, COL_DEPARTMENTID},
				null, null, null, null, null);
	}	
	//GET A SPECIFIC Employee
	
	public Cursor Search(String searchParam)
	{
		try {
			long id = Long.parseLong(searchParam);
			
			Cursor mCursor = mDB.query(true, DB_TABLE, 
					new String[] {COL_ID, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, COL_PHONE, COL_SALARY, COL_DEPARTMENTID},
					COL_ID + "=" + id, 
					null, null, null, null, null);
			
			if (mCursor != null)
				mCursor.moveToFirst();
			
			return mCursor;
			
		} 
		catch (Exception e) 
		{
			// find by full name
			if(searchParam.matches("(.*)-(.*)"))
			{
				String[] arr = searchParam.split("-");
				String firstName = arr[0];
				String lastName = arr[1];
				
				Cursor mCursor = mDB.query(true, DB_TABLE, 
						new String[] {COL_ID, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, COL_PHONE, COL_SALARY, COL_DEPARTMENTID},
						COL_FIRSTNAME + " LIKE '" + firstName + "%'" + 
						" AND " + 
						COL_LASTNAME + " LIKE '" + lastName + "%'", 
						null, null, null, null, null);
				
				if (mCursor != null)
					mCursor.moveToFirst();
				
				return mCursor;				
			}
			
			// find by first name
			else
			{
				Cursor mCursor = mDB.query(true, DB_TABLE, 
						new String[] {COL_ID, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, COL_PHONE, COL_SALARY, COL_DEPARTMENTID},
						COL_FIRSTNAME + " LIKE '" + searchParam + "%'", 
						null, null, null, null, null);
				
				if (mCursor != null)
					mCursor.moveToFirst();
				
				return mCursor;
			}
			
		}
	}
	
	// LOGIN
	/**
	 * 
	 * @param id
	 * @param password
	 * @return cursor
	 */
	public Cursor login(long id, String password)
	{
		Cursor mCursor = mDB.query(true, DB_TABLE, 
				new String[] {COL_ID, COL_PASSWORD, COL_DEPARTMENTID},
				COL_ID + "=" + id
				+ " AND "
				+ COL_PASSWORD + "=" + password, 
				null, null, null, null, null);
		
		mCursor.moveToFirst();
		return mCursor;
	}
	
	//FINAL UPDATE THE Employee
	public boolean UpdateEmployee(long id, String password, String firstName, String lastName, int phone, float salary, int departmentID)
	{
		ContentValues v = new ContentValues();
		v.put(COL_ID, id);
		v.put(COL_PASSWORD, password);
		v.put(COL_FIRSTNAME, firstName);
		v.put(COL_LASTNAME, lastName);
		v.put(COL_PHONE, phone);
		v.put(COL_SALARY, salary);
		v.put(COL_DEPARTMENTID, departmentID);
		return mDB.update(DB_TABLE, v, COL_ID + "=" + id, null) > 0;
	}
}
