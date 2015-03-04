package com.example.conteos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class AdapterListaModosTransporte extends BaseAdapter {
	
	
	public static final int MIN_MODOS_TRANSPORTE_SELECCIONADOS = 1;	
	
	private final Activity actividad;
	private HashMap<String, Integer> listaStringsIconos;
	private Set<String> itemsPreferencias;
	private Set<String> itemsSeleccionados;
	private View vista;
	
	
	public AdapterListaModosTransporte( Activity actividad, HashMap<String, Integer> listaStringsIconos ){
		super();
		this.actividad = actividad;		
		this.listaStringsIconos = listaStringsIconos;
		this.itemsPreferencias = restaurarItemsSeleccionados();
		Toast.makeText( this.actividad, Arrays.toString( this.itemsPreferencias.toArray() ), Toast.LENGTH_LONG).show();
		this.itemsSeleccionados = new HashSet<String>();
	}
	
	private Set<String> restaurarItemsSeleccionados(){
		SharedPreferences prefActuales = this.actividad.getSharedPreferences( "com.example.conteos_preferences", this.actividad.MODE_PRIVATE );
		Set<String> listaModosTransporteActuales = prefActuales.getStringSet( ModosTransporteListActivity.CLAVE_MODOS_TRANSPORTE, new HashSet<String>() );
		return listaModosTransporteActuales;
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
					if (  itemsPreferencias.contains( checkModoTransporte.getText() )  ){
						itemsPreferencias.remove(  String.valueOf( checkModoTransporte.getText() )  );
					}
					else if (  itemsSeleccionados.contains( checkModoTransporte.getText() )  ) {
						itemsSeleccionados.remove(  String.valueOf( checkModoTransporte.getText() )  );
					}
				}
				Toast.makeText( actividad, "Seleccionados: " + Arrays.toString( itemsSeleccionados.toArray() ), Toast.LENGTH_LONG).show();
				Toast.makeText( actividad, "Van a Preferencias: " + Arrays.toString( itemsPreferencias.toArray() ), Toast.LENGTH_LONG).show();
				
				Button btnAceptar = (Button)actividad.findViewById( R.id.btnAceptar );
				if ( itemsSeleccionados.isEmpty()  &&  itemsPreferencias.isEmpty() ){					
					btnAceptar.setEnabled( false );
					Toast.makeText(actividad, "El No. de movimientos seleccionados debe ser superior a " + MIN_MODOS_TRANSPORTE_SELECCIONADOS , Toast.LENGTH_SHORT).show();
				}
				else{
					btnAceptar.setEnabled( true );
				}
			}			
		} );
		
		if (  this.itemsPreferencias.contains( nombreMedioTransporte )  ){
			checkModoTransporte.setChecked( true );
		}
		
		return vista;
	}
		
		
	public Set<String> getItemsSeleccionados(){
		return this.itemsSeleccionados;
	}
	
	
	public Set<String> getItemsPreferencias(){
		return this.itemsPreferencias;
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
