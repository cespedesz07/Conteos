package com.example.conteos;


import java.util.Arrays;
import java.util.HashMap;
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.lista_modos_transporte );			
		
		
		ArregloModosMovimientos arreglos = new ArregloModosMovimientos();
		this.listaModoIconoTransporte = arreglos.getListaModoIconoTransporte();
		
		this.adapterListaModosTransporte = new AdapterListaModosTransporte( this, this.listaModoIconoTransporte );
		setListAdapter( this.adapterListaModosTransporte );		
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	
	//OJO, SI EN LAS PREFERENCIAS YA EXISTE EL SET DE MODOS DE TRANSPORTE
	//ES NECESARIO ELIMINAR PRIMERO ESE SET (1)
	//APLICAR LOS CAMBIOS (2) Y LUEGO ALMACENAR EL NUEVO SET (3)
	//de lo contrario no se guarda el nuevo SET con los cambios
	public void guardarModosTransporteSeleccionados( View view ){
		Set<String> modosTransporteSeleccionados = this.adapterListaModosTransporte.getItemsSeleccionados();
		Set<String> modosTransportePref = this.adapterListaModosTransporte.getItemsPreferencias();
		for ( String modoTransporteSeleccionado : modosTransporteSeleccionados ){
			modosTransportePref.add( modoTransporteSeleccionado );
		}
		
		SharedPreferences prefActuales = getSharedPreferences( "com.example.conteos_preferences", MODE_PRIVATE );
		Editor editor = prefActuales.edit();
		editor.remove( CLAVE_MODOS_TRANSPORTE );	//(1)
		editor.apply();								//(2)
		editor.putStringSet( CLAVE_MODOS_TRANSPORTE, modosTransportePref );	//(3)
		editor.commit();
		Toast.makeText( getApplicationContext(), "Items Saved: \n" +  Arrays.toString( modosTransportePref.toArray() ), Toast.LENGTH_LONG ).show();		
		Toast.makeText( getApplicationContext(), "Items Restored from pref: \n" + Arrays.toString( prefActuales.getStringSet( CLAVE_MODOS_TRANSPORTE, null ).toArray() ), Toast.LENGTH_LONG  ).show();
		finish();
	}
	
	
	public void cancelar( View view ){
		//Se realiza una comparacion de los items que estan actualmente guardados en las preferencias
		//versus los items seleccionados en el listVIew:
		//AQUELLOS ITEMS SELECCIONADOS EN EL LISTVIEW QUE NO ESTAN EN LAS PREFERENCIAS, SE ELIMINAN DE LOS ITEMS SELECCIONADOS		
		finish();
	}
	
	
}
