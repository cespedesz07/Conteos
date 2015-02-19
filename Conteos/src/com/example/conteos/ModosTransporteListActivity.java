package com.example.conteos;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class ModosTransporteListActivity extends ListActivity {
	
	
	public static final String CLAVE_MODOS_TRANSPORTE = "modos_a_contar";
	private HashMap<String, Integer> listaModoIconoTransporte;
	private AdapterListaModosTransporte adapterListaModosTransporte;
	public Set<String> modosTransporteGuardados;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.lista_modos_transporte );			
		
		
		ArregloModosMovimientos arreglos = new ArregloModosMovimientos();
		this.listaModoIconoTransporte = arreglos.getListaModoIconoTransporte();
		/*
		this.modosTransporteGuardados = restaurarItemsSeleccionados();
		this.adapterListaModosTransporte = new AdapterListaModosTransporte( this, this.listaModoIconoTransporte, this.modosTransporteGuardados );
		setListAdapter( this.adapterListaModosTransporte );
		*/		
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		Toast.makeText( this , "Resume List Activity", Toast.LENGTH_SHORT ).show();
		
		this.modosTransporteGuardados = restaurarItemsSeleccionados();
		this.adapterListaModosTransporte = new AdapterListaModosTransporte( this, this.listaModoIconoTransporte, this.modosTransporteGuardados );
		setListAdapter( this.adapterListaModosTransporte );
	}
	
	
	private Set<String> restaurarItemsSeleccionados(){
		SharedPreferences prefActuales = getSharedPreferences( "com.example.conteos_preferences", MODE_PRIVATE );
		Set<String> resultado = new HashSet<String>();
		Set<String> listaModosTransporteActuales = prefActuales.getStringSet( CLAVE_MODOS_TRANSPORTE, resultado );
		Toast.makeText(this, "Restaurando: " + Arrays.toString( listaModosTransporteActuales.toArray() ), Toast.LENGTH_LONG ).show();
		return listaModosTransporteActuales;
	}
	
	
	public void guardarModosTransporteSeleccionados( View view ){
		SharedPreferences prefActuales = getSharedPreferences( "com.example.conteos_preferences", MODE_PRIVATE );
		Editor editor = prefActuales.edit();
		Toast.makeText(this, Arrays.toString( this.adapterListaModosTransporte.getItemsSeleccionados().toArray() ), Toast.LENGTH_LONG).show();
		editor.putStringSet( CLAVE_MODOS_TRANSPORTE, this.adapterListaModosTransporte.getItemsSeleccionados() );
		boolean resp = editor.commit();
		Toast.makeText(this, Boolean.toString(resp), Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
	public void cancelar( View view ){
		finish();
	}
	
	
}
