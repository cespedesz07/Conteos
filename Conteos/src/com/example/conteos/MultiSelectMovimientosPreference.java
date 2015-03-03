package com.example.conteos;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;
import android.widget.Toast;


public class MultiSelectMovimientosPreference extends MultiSelectListPreference implements DialogInterface.OnMultiChoiceClickListener {
	
	
	
	public static final String CLAVE_MOVIMIENTOS = "movimientos_a_contar";
	public static final int MIN_MOVIMIENTOS_SELECCIONADOS = 1;
	public static final int MAX_MOVIMIENTOS_SELECCIONADOS = 3;
	
	public Set<String> valoresPorDefecto;
	private boolean itemsSeleccionados[];
	private int numItemsSeleccionados;
	 
	private boolean excedeLimite;
	
	private AlertDialog dialog;
	private Context context;
	
	
	
	

	
	public MultiSelectMovimientosPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		ArregloModosMovimientos arreglos = new ArregloModosMovimientos();
		this.valoresPorDefecto = arreglos.getMovimentosPorDefecto();
		asignarChecks( this.valoresPorDefecto, getEntries() );
		
		this.context = context;
	}
	
	
	
	
	
	/**
	 * Método que se invoca para configurar los valores iniciales de los items
	 */
	/*
	@Override
	protected void onSetInitialValue( boolean restorePersistedValue, Object defaultValue ){
		if ( restorePersistedValue ){
			SharedPreferences prefActuales = getSharedPreferences();
			Set<String> valoresActuales = prefActuales.getStringSet( CLAVE_MOVIMIENTOS, this.valoresPorDefecto );
			Toast.makeText( this.context, "Recuperados: " + Arrays.toString( valoresActuales.toArray() ), Toast.LENGTH_SHORT ).show();
			asignarChecks( valoresActuales, getEntries() );
			setValues( valoresActuales );
		}
		else{
			Set<String> valoresDefecto = (Set<String>) defaultValue; 
			setValues( valoresDefecto );
		}
	}
	*/
	
	
	
	
	
	/*
	@Override
	protected void onDialogClosed( boolean haAceptado ){
		super.onDialogClosed( haAceptado );
		Toast.makeText(this.context, Boolean.toString(haAceptado), Toast.LENGTH_SHORT).show();
		if ( haAceptado ){			
			CharSequence[] entryValues = getEntryValues();
			Set<String> setItemsSeleccionados = new HashSet<String>();
			for ( int i=0; i<entryValues.length; i++ ){
				if ( this.itemsSeleccionados[i] == true ){
					setItemsSeleccionados.add( String.valueOf(entryValues[i]) );
				}
			}
			
			SharedPreferences prefActuales = getSharedPreferences();
			Editor editor = prefActuales.edit();
			editor.remove( CLAVE_MOVIMIENTOS );
			editor.apply();
			editor.putStringSet( CLAVE_MOVIMIENTOS , setItemsSeleccionados );
			editor.commit();
		}
	}
	*/
	
	
	
	
	
	
	private void asignarChecks( Set<String> valores, CharSequence[] entradas ){
		this.itemsSeleccionados = new boolean[ getEntries().length ];
		for ( String valor : valores ){
			int posicionValor = Arrays.asList(entradas).indexOf( valor ); 		//Posición del valor en el vector de entradas
			if ( posicionValor != -1 ){
				this.itemsSeleccionados[ posicionValor ] = true;
				this.numItemsSeleccionados += 1;
			}
		}
	}
	
	
	
	
	
	
	@Override
	protected void showDialog( Bundle state ){
		AlertDialog.Builder builder = new AlertDialog.Builder( this.context );
		
		builder.setPositiveButton( "Aceptar", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				guardarMovimientosSeleccionados();				
			}
		});
		
		builder.setNegativeButton( "Cancelar", null );
		
		//Se recuperan los items guardados de las preferencias
		SharedPreferences prefActuales = getSharedPreferences();
		Set<String> valoresActuales = prefActuales.getStringSet( CLAVE_MOVIMIENTOS, this.valoresPorDefecto );
		asignarChecks( valoresActuales, getEntries() );
		builder.setMultiChoiceItems( getEntries() , this.itemsSeleccionados, this);		
		
		this.dialog = builder.create();
		this.dialog.show();
	}
	
	
	
	
	
	
	
	private void guardarMovimientosSeleccionados(){
		CharSequence[] entryValues = getEntryValues();
		Set<String> setItemsSeleccionados = new HashSet<String>();
		for ( int i=0; i<entryValues.length; i++ ){
			if ( this.itemsSeleccionados[i] == true ){
				setItemsSeleccionados.add( String.valueOf(entryValues[i]) );
			}
		}
		SharedPreferences prefActuales = getSharedPreferences();
		Editor editor = prefActuales.edit();
		editor.remove( CLAVE_MOVIMIENTOS );
		editor.apply();
		editor.putStringSet( CLAVE_MOVIMIENTOS , setItemsSeleccionados );
		editor.commit();
	}
	
	
	
	
	
	
	@Override
	public void setEntries( CharSequence[] entries ){
		super.setEntries( entries );
	}
	
	
	
	
	
	
	public int obtenerNumItemsSeleccionados(){
		int numItems = 0;
		for ( boolean itemSeleccionado : this.itemsSeleccionados ){
			if ( itemSeleccionado ){
				numItems += 1;
			}
		}
		return numItems;
	}
	
	
	
	
	
	
	//Método onClick del escuchador de eventos cuando se seleccionan los checkboxes
	@Override
	public void onClick(DialogInterface dialog, int posicionItemSeleccionado, boolean valorItemSeleccionado) {
		numItemsSeleccionados = obtenerNumItemsSeleccionados();
		if ( MIN_MOVIMIENTOS_SELECCIONADOS <= numItemsSeleccionados  &&  numItemsSeleccionados <= MAX_MOVIMIENTOS_SELECCIONADOS ){
			this.dialog.getButton( Dialog.BUTTON_POSITIVE ).setEnabled( true );
			itemsSeleccionados[ posicionItemSeleccionado ] = valorItemSeleccionado;
		}
		else{
			if (  numItemsSeleccionados < MIN_MOVIMIENTOS_SELECCIONADOS  ){
				Toast.makeText( this.context, "El No. de movimientos debe ser superior a " + MIN_MOVIMIENTOS_SELECCIONADOS, Toast.LENGTH_LONG ).show();
			}
			else if (  numItemsSeleccionados > MAX_MOVIMIENTOS_SELECCIONADOS  ){
				Toast.makeText( this.context, "El No. de movimientos debe ser inferior a " + MAX_MOVIMIENTOS_SELECCIONADOS, Toast.LENGTH_SHORT ).show();
			}
			this.dialog.getButton( Dialog.BUTTON_POSITIVE ).setEnabled( false );				
		}
	}
	
	
	
	
	

}
