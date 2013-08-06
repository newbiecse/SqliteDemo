package com.example.sqlitedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Input extends Activity {

	private EditText txtPassword;
	private EditText txtFirstName;
	private EditText txtLastName;
	private EditText txtPhone;
	private EditText txtSalary;
	private EditText txtDepartment;
	
	private Button btnOk;
	private Button btnReset;
	
	private long id;
	private boolean bUpdateMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.input);
		
		// edit text
		txtPassword = (EditText) findViewById(R.id.txtPass);
		txtFirstName = (EditText) findViewById(R.id.txtFirstName);
		txtLastName = (EditText) findViewById(R.id.txtLastName);
		txtPhone = (EditText) findViewById(R.id.txtPhone);
		txtSalary = (EditText) findViewById(R.id.txtSalary);
		txtDepartment = (EditText) findViewById(R.id.txtDepartmentID);
		
		// set color text hint
		EditText[] array = {txtPassword, txtFirstName, txtLastName, txtPhone, txtSalary, txtDepartment};
		for (EditText editText : array) {
			editText.setHintTextColor(Color.parseColor("#dedede"));
		}
		
		// button
		btnOk = (Button) findViewById(R.id.btnOk);
		btnReset = (Button) findViewById(R.id.btnReset);
		
		Intent i = this.getIntent();
		id = i.getLongExtra(DBAdapter.COL_ID, 0);
		
		if (id == 0)
		{
			bUpdateMode = false;
		}
		else
		{
			bUpdateMode = true;
			
			String password = i.getStringExtra(DBAdapter.COL_PASSWORD);
			String firstName = i.getStringExtra(DBAdapter.COL_FIRSTNAME);
			String lastName = i.getStringExtra(DBAdapter.COL_LASTNAME);
			int phone = i.getIntExtra(DBAdapter.COL_PHONE, 0);
			float salary = i.getFloatExtra(DBAdapter.COL_SALARY, 0);
			int departmentID = i.getIntExtra(DBAdapter.COL_DEPARTMENTID, 0);
			
			txtPassword.setText(password);
			txtFirstName.setText(firstName);
			txtLastName.setText(lastName);
			
			// Error here----------------------------
			txtPhone.setText( Integer.toString(phone));
			txtSalary.setText( Float.toString(salary));
			txtDepartment.setText( Integer.toString(departmentID));
			// Error here----------------------------
		}
		
		btnOk.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(DBAdapter.COL_PASSWORD, txtPassword.getText().toString());
				i.putExtra(DBAdapter.COL_FIRSTNAME, txtFirstName.getText().toString());
				i.putExtra(DBAdapter.COL_LASTNAME, txtLastName.getText().toString());
				i.putExtra(DBAdapter.COL_PHONE, Integer.parseInt( txtPhone.getText().toString()) );
				i.putExtra(DBAdapter.COL_SALARY, Float.parseFloat( txtSalary.getText().toString()) );
				i.putExtra(DBAdapter.COL_DEPARTMENTID, Integer.parseInt( txtDepartment.getText().toString()) );
				
				if (bUpdateMode)
					i.putExtra(DBAdapter.COL_ID, id);
				
				setResult(RESULT_OK, i); 
				finish();
			}
		});
		
		btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txtPassword.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtPhone.setText("");
				txtSalary.setText("");
				txtDepartment.setText("");
				
			}
		});
		
	}

}
