package com.example.conteos;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;


public class MultiSelectMovimientosPreference extends MultiSelectListPreference {
	
	
	public static final String CLAVE_MOVIMIENTOS = "movimientos_a_contar";
	public static final int MIN_MOVIMIENTOS_SELECCIONADOS = 1;
	public static final int MAX_MOVIMIENTOS_SELECCIONADOS = 3;
	
	private static final int POSICION_ITEM_POR_DEFECTO = 0; 
	
	public Set<String> valoresPorDefecto;
	private boolean itemsSeleccionados[];
	private int numItemsSeleccionados;
	 
	private boolean excedeLimite;
	

	
	public MultiSelectMovimientosPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.itemsSeleccionados = new boolean[ getEntries().length ];
		
		ArregloModosMovimientos arreglos = new ArregloModosMovimientos();
		this.valoresPorDefecto = arreglos.getMovimentosPorDefecto();
		asignarChecks( this.valoresPorDefecto, getEntries() );
		
	}
	
	/**
	 * Método que se invoca para configurar los valores iniciales de los items
	 */
	@Override
	protected void onSetInitialValue( boolean restorePersistedValue, Object defaultValue ){
		if ( restorePersistedValue ){
			SharedPreferences prefActuales = getSharedPreferences();
			Set<String> valoresActuales = prefActuales.getStringSet( CLAVE_MOVIMIENTOS, this.valoresPorDefecto );
			asignarChecks( valoresActuales, getEntries() );
			setValues( valoresActuales );
		}
		else{
			Set<String> valoresDefecto = (Set<String>) defaultValue; 
			setValues( valoresDefecto );
		}
	}
	
	
	private void asignarChecks( Set<String> valores, CharSequence[] entradas ){
		this.itemsSeleccionados = new boolean[ entradas.length ];
		for ( String valor : valores ){
			int posicionValor = Arrays.asList(entradas).indexOf( valor ); 		//Posición del valor en el vector de entradas
			if ( posicionValor != -1 ){
				this.itemsSeleccionados[ posicionValor ] = true;
				this.numItemsSeleccionados += 1;
			}
		}		
	}
	
	
	
	@SuppressWarnings("deprecation")
	protected void onPrepareDialogBuilder( Builder builder ){
		CharSequence[] entries = getEntries(); 
		
		builder.setMultiChoiceItems(entries, itemsSeleccionados, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int posicionItemSeleccionado, boolean valorItemSeleccionado) {
				numItemsSeleccionados = obtenerNumItemsSeleccionados();
				if ( numItemsSeleccionados <= MAX_MOVIMIENTOS_SELECCIONADOS ){
					itemsSeleccionados[ posicionItemSeleccionado ] = valorItemSeleccionado;
				}
				else{
					excedeLimite = true;
				}
			}
		} );
		AlertDialog alertDialog = builder.create();
		
		
	}
	
	
	
	
	public int obtenerNumItemsSeleccionados(){
		int numItems = 0;
		for ( boolean itemSeleccionado : this.itemsSeleccionados ){
			if ( itemSeleccionado ){
				numItems += 1;
			}
		}
		if ( numItems > MAX_MOVIMIENTOS_SELECCIONADOS ){
			this.excedeLimite = false;
		}
		return numItems;
	}
	
	
	@Override
	public void setEntries( CharSequence[] entries ){
		super.setEntries( entries );
	}
	
	
	protected void onDialogClosed( boolean haAceptado ){
		super.onDialogClosed( haAceptado );
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
	
	
	
	
	

}
