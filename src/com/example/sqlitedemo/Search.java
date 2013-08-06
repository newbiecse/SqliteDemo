package com.example.sqlitedemo;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Search extends ListActivity  {
	
	private DBAdapter mDBHelper;
	private Cursor mC;
	
	private EditText txtSearch;
	private Button btnSearch;
//	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.search);
		
		
		
		mDBHelper = new DBAdapter(this);
		mDBHelper = mDBHelper.open();
		
		
//		registerForContextMenu(getListView());
		View header = getLayoutInflater().inflate(R.layout.header_search, null);
//		View header = (View) findViewById(R.layout.header_search);
		ListView lv = getListView();
		lv.addHeaderView(header);
		
		refreshList("");
		
		// control
		txtSearch = (EditText) findViewById(R.id.txtSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
//		listView = (ListView) findViewById(R.id.listView);
				
//		refreshList(0);		
				
		// btnSearch click
		btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String searchParam = txtSearch.getText().toString();
				refreshList(searchParam);
//				finish();
			}
		});
		
	}
	

	private void refreshList(String searchParam) {
		if (searchParam == "")
			mC = mDBHelper.GetAllEmployee();
		else
			mC = mDBHelper.Search(searchParam);
		if(mC != null)
		{
			startManagingCursor(mC);
		
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.search, mC,
					new String[] {DBAdapter.COL_ID, DBAdapter.COL_FIRSTNAME, DBAdapter.COL_LASTNAME},
					new int[] {R.id.txt_COL_ID, R.id.txt_COL_FIRSTNAME, R.id.txt_COL_LASTNAME});
			setListAdapter(adapter);
		}
		
	}

	 
}
