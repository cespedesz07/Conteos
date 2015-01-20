package com.example.conteos;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Binder;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;


public class MultiSelectListPreference extends ListPreference implements OnMultiChoiceClickListener {
	
	
	public static final String CLAVE_MEDIOS_TRANSPORTE = "medios_transporte";
	private boolean itemsSeleccionados[];

	
	public MultiSelectListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	@Override
	public void setEntries( CharSequence[] entries ){
		super.setEntries( entries );
	}
	
	/**
	 * Este método se invoca al momento de crear la lista de multiple seleccion
	 * es el responsable de abrir el Dialog al hacer click en 
	 */
	protected void onPrepareDialogBuilder( Builder builder ){
		CharSequence[] entries = getEntries();
		
		itemsSeleccionados = new boolean[ entries.length ];
		
		builder.setMultiChoiceItems(entries, itemsSeleccionados, this );
	}
	
	
	protected void onDialogClosed( boolean haAceptado ){
		if ( haAceptado ){
			SharedPreferences prefActuales = getSharedPreferences();
			Editor editor = prefActuales.edit();			
			
			List lista = Arrays.asList( getEntries() );
			Set<String> setEntradas = new HashSet<String>( lista );
			
			//editor.putString( CLAVE_MEDIOS_TRANSPORTE , setEntradas );
			
			
		}		
	}
	
	@Override
	protected Object onGetDefaultValue( TypedArray a, int index ){
		CharSequence[] arreglo = a.getTextArray( 0 );
		Log.i( "array", Arrays.toString(arreglo) );
		return arreglo;
	}
	
	
	public void onClick(DialogInterface dialog, int posicionItemSeleccionado, boolean valorItemSeleccionado) {
		itemsSeleccionados[ posicionItemSeleccionado ] = valorItemSeleccionado;
	}
	
	
	
	
	

}
