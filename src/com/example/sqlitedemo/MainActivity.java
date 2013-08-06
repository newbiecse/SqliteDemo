package com.example.sqlitedemo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends ListActivity {

	
	private DBAdapter mDBHelper;
	private static final int REQUEST_ADD = 1;
	private static final int REQUEST_EDIT = 2;
	private static final int REQUEST_SEARCH = 3;
	private Cursor mC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDBHelper = new DBAdapter(this);
		mDBHelper = mDBHelper.open();
		
		refreshList();
		registerForContextMenu(getListView());
		
		
	}

	
	//OPTION MENU FIRST
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) 
		{
		case R.id.miAdd:
			
			Intent i = new Intent();
			i.setClass(this, Input.class);
			i.putExtra(DBAdapter.COL_ID, 0);
			startActivityForResult(i, REQUEST_ADD);
			
			return true;
		case R.id.miClear:
			
			mDBHelper.DeleteAllEmployee();
			refreshList();
			
			return true;
		case R.id.miSearch:
			
			Intent iSearch = new Intent();
			iSearch.setClass(this, Search.class);
			startActivity(iSearch);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}	
	}

	//CONTEXT MENU	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();	
		inflater.inflate(R.menu.contextmenu, menu);
	}
 
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		long id = info.id;
		
		switch (item.getItemId()) {
		case R.id.miEdit:
			
			Cursor c = mC;
			c.moveToPosition(position);
			
			Intent i = new Intent();
			i.setClass(this, Input.class);
			
			i.putExtra(DBAdapter.COL_ID, id);
			i.putExtra(DBAdapter.COL_PASSWORD, c.getString(c.getColumnIndexOrThrow(DBAdapter.COL_PASSWORD)));
			i.putExtra(DBAdapter.COL_FIRSTNAME, c.getString(c.getColumnIndexOrThrow(DBAdapter.COL_FIRSTNAME)));
			i.putExtra(DBAdapter.COL_LASTNAME, c.getString(c.getColumnIndexOrThrow(DBAdapter.COL_LASTNAME)));
			i.putExtra(DBAdapter.COL_PHONE, c.getInt(c.getColumnIndexOrThrow(DBAdapter.COL_PHONE)));
			i.putExtra(DBAdapter.COL_SALARY, c.getFloat(c.getColumnIndexOrThrow(DBAdapter.COL_SALARY)));
			i.putExtra(DBAdapter.COL_DEPARTMENTID, c.getInt(c.getColumnIndexOrThrow(DBAdapter.COL_DEPARTMENTID)));
			
			startActivityForResult(i, REQUEST_EDIT);
			return true;
			
		case R.id.miDelete:
			
			mDBHelper.DeleteEmployee(id);
			refreshList();
			return true;
			
		default:
			return super.onContextItemSelected(item);
		}		
	}

	private void refreshList() {
		mC = mDBHelper.GetAllEmployee();
		startManagingCursor(mC);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, mC,
				new String[] {DBAdapter.COL_PASSWORD, DBAdapter.COL_FIRSTNAME, DBAdapter.COL_LASTNAME, DBAdapter.COL_PHONE, DBAdapter.COL_SALARY, DBAdapter.COL_DEPARTMENTID},
				new int[] {R.id.txtText1, R.id.txtText2, R.id.txtText3, R.id.txtText4, R.id.txtText5, R.id.txtText6});

		setListAdapter(adapter);
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) 
		{
		case REQUEST_ADD:
			
			String password = data.getStringExtra(DBAdapter.COL_PASSWORD);
			String firstName = data.getStringExtra(DBAdapter.COL_FIRSTNAME);
			String lastName = data.getStringExtra(DBAdapter.COL_LASTNAME);
			int phone = data.getIntExtra(DBAdapter.COL_PHONE, 0);
			float salary = data.getFloatExtra(DBAdapter.COL_SALARY, 0);
			int departmentID = data.getIntExtra(DBAdapter.COL_DEPARTMENTID, 0);
			
			mDBHelper.CreateEmployee(password, firstName, lastName, phone, salary, departmentID);
			refreshList();
			
			break;
		case REQUEST_EDIT:
			String editPassword = data.getStringExtra(DBAdapter.COL_PASSWORD);
			String editFirstName = data.getStringExtra(DBAdapter.COL_FIRSTNAME);
			String editLastName = data.getStringExtra(DBAdapter.COL_LASTNAME);
			//---------------
			int editPhone = data.getIntExtra(DBAdapter.COL_PHONE, 0);
			float editSalary = data.getFloatExtra(DBAdapter.COL_SALARY, 0);
			int editDepartmentID = data.getIntExtra(DBAdapter.COL_DEPARTMENTID, 0);
			//---------------
			
			long updateId = data.getLongExtra(DBAdapter.COL_ID, 0);
			
			mDBHelper.UpdateEmployee(updateId, editPassword, editFirstName, editLastName, editPhone, editSalary, editDepartmentID);
			
			break;
//		case REQUEST_SEARCH:
//			long searchId = data.getLongExtra(DBAdapter.COL_ID, 0);
//			mDBHelper.GetBookmark(1);
//			
////			Intent i = new Intent()
//			break;
			
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}		
	}
	
}
