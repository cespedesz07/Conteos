package com.example.conteos;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments_container);
	}

	
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
		if (id == R.id.acerca_de) {
			return true;
			//TODO Implementar actividad acerca de
		}
		else if ( id == R.id.preferencias ){
			abrirPreferencias( null );
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void abrirPreferencias( View vista ){
		Intent intentPref = new Intent( this, PreferenciasActivity.class );
		startActivity( intentPref );
		
	}
}
