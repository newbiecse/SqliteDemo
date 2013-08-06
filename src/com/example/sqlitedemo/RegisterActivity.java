package com.example.sqlitedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	
	
    private EditText txtTo;
	private EditText txtSubject;
	private EditText txtBody;
	private Button btnSend;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        /**
         * go back login activity
         */
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        
        
        /**
         * send email
         */
        btnSend = (Button) findViewById(R.id.btnSend);
        txtTo = (EditText) findViewById(R.id.txtTo);
        txtSubject = (EditText) findViewById(R.id.txtSubject);
        txtBody = (EditText) findViewById(R.id.txtBody);
        
        btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String to = txtTo.getText().toString();
				String subject = txtSubject.getText().toString();
				String body = txtBody.getText().toString();
				
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] {to});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, body);
				
				email.setType("body/rfc822");
				
				startActivity(Intent.createChooser(email, "Choose an Email client"));
				
			}
		});
    }
}