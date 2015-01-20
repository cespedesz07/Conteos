package com.example.conteos;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class PreferenciasActivity extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		addPreferencesFromResource( R.xml.preferencias );
	}

}
