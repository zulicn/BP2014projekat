package com.bpprojekat2014;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bpprojekat2014.RegisterActivity;
import com.bpprojekat2014.classes.User;

public class MainActivity extends Activity {
	
	User user;
	static EditText username;
	static EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		      
	}
	
	// U xmlu textviewu dodan parametar koji ga postavlja da je odgovarajuci txtview klikabilan
	// Takodje i parametar koji pozove ovu funkciju nakon sto klikne na textview
	public void showRegistrationView(View v) {
	    Intent intent;
	    intent = new Intent(this, RegisterActivity.class);
	    startActivity(intent);
	}
	
	// Ime klase od activitiya koji se prikazuje nakon logina!
	// Ova metoda prvo provjeri da li se može logirati korisnik. Ako može prikaže mu odgovarajuci activity
	public void Signing(View view) {
		if(TrySignIn(view) == true)
		{
		    Intent intent;
		    // TBD aBd samo izbrisati "/*KlasaOdActivitijaNakonLoginaNOTIMPLEMENTET*/RegisterActivity" i staviti novu
		    // klasu koju vec neko napravi .. sad za sad stoji ovo RegisterActivity da ne prikazuje errore
		    intent = new Intent(this, /*KlasaOdActivitijaNakonLoginaNOTIMPLEMENTET*/RegisterActivity.class);
		    startActivity(intent);
		}
		else
		{
			// Ona mala porukica na dnu ekrana javlja
			Toast message = Toast.makeText(this /*Ili getActivity() ili this.getApplicationContext()*/,
					"Pogrešni login podaci!", Toast.LENGTH_LONG);
			message.show();
		}
	}
	
	// TBD aBd
	private boolean TrySignIn(View view){
		// Dohvatanje unesenih korisnickih podataka 
		username = (EditText) view.findViewById(R.id.userNameLogin);
		password = (EditText) view.findViewById(R.id.passwordLogin);
		
		/* TBD aBd
		if(uspjeo login){
			user = new User(username.getText().toString());
			// TBD dodati neki text u gornjem desnom uglu gdje ce pisati ime usera
			// dodati u manifestu jer ce tako uvijek postojati sta god da korisnik radio na aplikaciji
			// Slicno kao onaj zeleni krugic "Sign in"
			
			// Koristiti user.getFullName() da se postavi tekst;
			return true;
		}
		*/
		
		// sad za sad nek vraca false
		return false;
	}
	
	

	
	
	
	
	
	
	// --------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
