package com.example.conteos;

import android.preference.PreferenceActivity;
import android.content.Intent;
import android.os.Bundle;

public class PreferenciasActivity extends PreferenceActivity {
	
	
	public static final int REFRESH_CODE = 1;
	
	//Constantes que corresponden a los nombres de las preferencias
	//que no incluye los movimientos ni los modos, es decir las preferencias
	//primitivas de Android
	public static final String CLAVE_DIA_CONTEO = "dia_conteo";
	public static final String VALOR_POR_DEFECTO_DIA_CONTEO = "Martes";
	
	public static final String CLAVE_ESTACION = "estacion";
	public static final String VALOR_POR_DEFECTO_ESTACION = "56";
	
	//Esta preferencia ayuda a identificar si se ha ejecutado la app por primera vez
	public static final String CLAVE_PRIMERA_VEZ = "primera_vez";
	
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		addPreferencesFromResource( R.xml.preferencias );
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Intent refreshGUIIntent = new Intent( this, MainActivity.class );
		startActivityForResult( refreshGUIIntent, REFRESH_CODE );
	}
	
	

}
