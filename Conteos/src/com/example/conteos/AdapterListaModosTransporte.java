package com.example.conteos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterListaModosTransporte extends BaseAdapter {
	
	
	private final Activity actividad;
	private HashMap<String, Integer> listaStringsIconos;
	private Set<String> itemsSeleccionados;
	private View vista;
	
	
	public AdapterListaModosTransporte( Activity actividad, HashMap<String, Integer> listaStringsIconos, 
			Set<String> itemsPreviosSeleccionados ){
		super();
		this.actividad = actividad;
		this.listaStringsIconos = listaStringsIconos;
		this.itemsSeleccionados = itemsPreviosSeleccionados;
		Toast.makeText( this.actividad, Arrays.toString( this.itemsSeleccionados.toArray() ), Toast.LENGTH_LONG ).show();
	}
	
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		LayoutInflater inflater = this.actividad.getLayoutInflater();
		this.vista = inflater.inflate( R.layout.elemento_lista_modos_transporte, null, true );		
		
		HashMap<String, Integer> parStringIcono = (HashMap<String, Integer>) getItem( position );
		String nombreMedioTransporte = new ArrayList<String>( parStringIcono.keySet() ).get( 0 );
		Integer iconoMedioTransporte = parStringIcono.get( nombreMedioTransporte );
		
		ImageView iconoModoTransporte = (ImageView) vista.findViewById( R.id.iconoModoTransporte );
		iconoModoTransporte.setImageResource( iconoMedioTransporte );		
		
		final CheckBox checkModoTransporte = (CheckBox) vista.findViewById( R.id.checkBoxModoTransporte );
		checkModoTransporte.setText( nombreMedioTransporte );
		checkModoTransporte.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View view) {
				if ( checkModoTransporte.isChecked() ){
					itemsSeleccionados.add(  String.valueOf( checkModoTransporte.getText() )  );
				}
				else{
					itemsSeleccionados.remove(  String.valueOf( checkModoTransporte.getText() )  );
				}
			}			
		} );
		
		if (  this.itemsSeleccionados.contains( nombreMedioTransporte )  ){
			checkModoTransporte.setChecked( true );
		}
		
		return vista;
	}
	
	
	public Set<String> getItemsSeleccionados(){
		return this.itemsSeleccionados;
	}
	

	@Override
	public int getCount() {
		return this.listaStringsIconos.size();
	}
	

	@Override
	public Object getItem(int position) {		
		Set<String> setNombresModosTransporte = this.listaStringsIconos.keySet();
		Iterator<String> iterator = setNombresModosTransporte.iterator();
		String claveModoTransporte = "";
		for ( int i=0; i<this.listaStringsIconos.size()  &&  iterator.hasNext(); i++ ){
			claveModoTransporte = iterator.next();
			if ( i == position ){
				break;
			}			
		}		
		int iconoModoTransporte = this.listaStringsIconos.get( claveModoTransporte );
		HashMap<String, Integer> parStringIcono = new HashMap<String, Integer>(1);
		parStringIcono.put( claveModoTransporte, iconoModoTransporte );
		return parStringIcono;
	}
	

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

}
