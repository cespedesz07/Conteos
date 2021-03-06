package com.example.conteos;


import java.util.HashMap;
import java.util.HashSet;
import android.content.Context;


public class ArregloModosMovimientos {

	
	private final HashMap<String, Integer> listaModoIconoTransporte;
	private final HashSet<String> modosTransportePorDefecto;
	private final HashSet<String> movimientosPorDefecto;
	
	
	public ArregloModosMovimientos(){
		
		//Se inicializa el arreglo de Movimientos seleccionados POR DEFECTO
		this.movimientosPorDefecto = new HashSet<String>();
		this.movimientosPorDefecto.add( "2" );
		/*
		this.movimientosPorDefecto.add( appContext.getResources().getStringArray( R.array.movimientos )[2] );
		this.movimientosPorDefecto.add( appContext.getResources().getStringArray( R.array.movimientos )[4] );
		this.movimientosPorDefecto.add( appContext.getResources().getStringArray( R.array.movimientos )[5] );
		*/
		
		//Se inicializa el arreglo de Modos de Transporte seleccionados POR DEFECTO
		this.modosTransportePorDefecto = new HashSet<String>();
		this.modosTransportePorDefecto.add( "Moto" );
		this.modosTransportePorDefecto.add( "Peaton" );
		
		//Se inicializa el arreglo de TODOS LOS Modos e Iconos de Transporte, esencial para:
		//- La clase ModosTransporteListActivity (Para mostrar los iconos en el ListView)
		//- La Clase FragmentMovimiento (Para mostrar los botones con los �conos de cada modo de transporte)
		this.listaModoIconoTransporte = new HashMap<String, Integer>();
		this.listaModoIconoTransporte.put( "Auto-Camioneta-Colectivo", R.drawable.auto_camioneta_colectivo );
		this.listaModoIconoTransporte.put( "Moto", R.drawable.moto );
		this.listaModoIconoTransporte.put( "Bicicleta", R.drawable.bicicleta );
		this.listaModoIconoTransporte.put( "Bus/Buseta", R.drawable.bus_buseta );
		this.listaModoIconoTransporte.put( "Camion 2 Ejes", R.drawable.camion2 );
		this.listaModoIconoTransporte.put( "Camion 3 Ejes", R.drawable.camion3 );
		this.listaModoIconoTransporte.put( "Peaton", R.drawable.peaton );
				
	}
	
	
	public int getIconoModoTransporte( String nombreModoTransporte ){
		int icono = (int) this.listaModoIconoTransporte.get( nombreModoTransporte );
		return icono;
	}
	
	
	public HashMap<String, Integer> getListaModoIconoTransporte(){
		return this.listaModoIconoTransporte;
	}
	
	
	public HashSet<String> getModosTransportePorDefecto(){
		return this.modosTransportePorDefecto;
	}
	
	
	public HashSet<String> getMovimentosPorDefecto(){
		return this.movimientosPorDefecto;
	}
	
	
	
	
}
