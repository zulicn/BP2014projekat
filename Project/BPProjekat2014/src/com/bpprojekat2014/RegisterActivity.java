package com.bpprojekat2014;


import com.bpprojekat2014.classes.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	User user;
	static EditText username;
	static EditText fullname;
	static EditText password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_acc);
	}

	public void showLoginView(View v) {
	    Intent intent;
	    intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
	public void Registering(View view) {
		if(TryRegister(view) == true)
		{
			Toast message = Toast.makeText(this, "Uspješno ste registrovani, molimo Vas za prijavu na sistem!", Toast.LENGTH_LONG);
			message.show();
			
		    Intent intent;
		    intent = new Intent(this, MainActivity.class);
		    startActivity(intent);
		}
		else
		{
			// Ona mala porukica na dnu ekrana javlja
			Toast message = Toast.makeText(this, "Registracija nije uspjela", Toast.LENGTH_LONG);
			message.show();
		}
	}
	
	// TBD aBd
	private boolean TryRegister(View view){
		// Dohvatanje unesenih korisnickih podataka 
		username = (EditText) view.findViewById(R.id.fullNameRegister);
		fullname = (EditText) view.findViewById(R.id.userNameRegister);
		password = (EditText) view.findViewById(R.id.passwordRegister);
		
		/* TBD aBd
		if(registrovanje uspjelo){
			return true;
		}
		*/
		
		return false;
	}
	
	
	
	
	
	
	// -------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
