package com.bpprojekat2014;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.bpprojekat2014.RegisterActivity;

public class MainActivity extends Activity {

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
