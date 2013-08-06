package com.example.sqlitedemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private DBAdapter mDBHelper;
	private EditText txtID;
	private EditText txtPassword;
	private Button btnLogin; 
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
			mDBHelper = new DBAdapter(this);
			mDBHelper = mDBHelper.open();
	        
	        
	        // setting default screen to login.xml
	        setContentView(R.layout.login);
	 
	        TextView registerScreen = (TextView) findViewById(R.id.link_to_sendAdmins);
	 
	        // Listening to register new account link
	        registerScreen.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View v) {
	                // Switching to Register screen
	                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
	                startActivity(i);
	            }
	        });
	        
	        
	        /**
	         * event login
	         */
	        txtID = (EditText) findViewById(R.id.log_ID);
	        txtPassword = (EditText) findViewById(R.id.log_Password);
	        // set text color of editText
	        txtID.setTextColor(Color.parseColor("#ffffff"));
	        txtPassword.setTextColor(Color.parseColor("#ffffff"));
	        btnLogin = (Button) findViewById(R.id.btnLogin);
	        
	        
	        btnLogin.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					if(txtID.getText().toString().matches(""))
					{
						Toast.makeText(getBaseContext(), "id input not empty", Toast.LENGTH_SHORT).show();
						txtID.requestFocus();
						return;
					}
					if(txtPassword.getText().toString().matches(""))
					{
						Toast.makeText(getBaseContext(), "password input not empty", Toast.LENGTH_SHORT).show();
						txtPassword.requestFocus();
						return;
					}
					
					long id = Long.parseLong(txtID.getText().toString());
					String password = txtPassword.getText().toString();
					
					Cursor mCursor = mDBHelper.login(id, password);
					
					if(mCursor == null || mCursor.getCount() == 0)
					{
						Toast.makeText(getBaseContext(), "Login fail", Toast.LENGTH_LONG).show();
					}
					else
					{
			                // Switching to admin screen
//			                Intent i = new Intent(getApplicationContext(), MainActivity.class);
						Intent i = new Intent(getApplicationContext(), MainActivity.class);
			            startActivity(i);	
					}
				}
			});
	 }
}
