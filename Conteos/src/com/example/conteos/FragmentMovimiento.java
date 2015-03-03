package com.example.conteos;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;




public class FragmentMovimiento extends Fragment implements OnClickListener {
	
	
	
	
	private static final int NUM_BOTONES_EN_FILA = 2;
	private String nombreMovimiento;
	private ArrayList<String> modosTransporteActuales;
	private int anchoLayout;
	private ArregloModosMovimientos arregloModosMovimiento;
	private AlmacenamientoConteos almacenamientoConteos;
	
	
	
	
	public FragmentMovimiento( String nombreMovimiento, Set<String> modosTransporteActuales, int anchoLayout, 
			AlmacenamientoConteos almacenamientoConteos, Context context ){
		super();
		this.nombreMovimiento = nombreMovimiento;
		this.modosTransporteActuales = new ArrayList<String>( modosTransporteActuales );	
		this.anchoLayout = anchoLayout;
		this.arregloModosMovimiento = new ArregloModosMovimientos();
		this.almacenamientoConteos = almacenamientoConteos;
	}
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate( R.layout.fragment_movimiento, container, false );
		
		TextView nombreMovimientoTextView = (TextView) view.findViewById( R.id.nombreMovimiento );
		nombreMovimientoTextView.setText(  String.valueOf( this.nombreMovimiento )  );
		
		TableLayout tablaBotonesModosTransporte = (TableLayout) view.findViewById( R.id.tablaBotonesModosTransporte );		
		int numFilas = (this.modosTransporteActuales.size() / NUM_BOTONES_EN_FILA) + (this.modosTransporteActuales.size() % NUM_BOTONES_EN_FILA);
		for ( int i=0; i<numFilas; i++ ){
			TableRow fila = new TableRow( getActivity() );
			fila.setGravity( Gravity.CENTER );
			fila.setLayoutParams(  new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT )  );
			
			for ( int j=0; j<NUM_BOTONES_EN_FILA; j++ ){
				int numBoton = j + i * NUM_BOTONES_EN_FILA;
				if ( numBoton < this.modosTransporteActuales.size() ){
					Button botonModoTransporte = new Button( getActivity() );
					String nombreModoTransporte = this.modosTransporteActuales.get( numBoton );
					int iconoModoTransporte = this.arregloModosMovimiento.getIconoModoTransporte(nombreModoTransporte);					
					botonModoTransporte.setOnClickListener( this );
					botonModoTransporte.setText( nombreModoTransporte );
					botonModoTransporte.setTextSize( 15 );
					
					Drawable drawableIcono = getResources().getDrawable( iconoModoTransporte ); 
					drawableIcono.setBounds( 0, 0, (int)(drawableIcono.getIntrinsicWidth()*0.5), (int)(drawableIcono.getIntrinsicHeight()*0.5) );
					ScaleDrawable drawableEscalado = new ScaleDrawable( drawableIcono, 0, 0, 0 );
					botonModoTransporte.setCompoundDrawables( null, null, null, drawableEscalado.getDrawable() );
					
					botonModoTransporte.setLayoutParams(  new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT )  );
					
					fila.addView( botonModoTransporte );
				}
			}			
			tablaBotonesModosTransporte.addView( fila );
		}		
		
		view.setLayoutParams( new LinearLayout.LayoutParams( this.anchoLayout , LinearLayout.LayoutParams.MATCH_PARENT) );
	
		return view;
	}

	
	
	
	@Override
	public void onClick(View view) {
		Button botonPresionado = (Button) view;
		SharedPreferences prefActuales = this.getActivity().getSharedPreferences( "com.example.conteos_preferences" , Context.MODE_PRIVATE);
		
		String estacion = prefActuales.getString( "estacion", PreferenciasActivity.VALOR_POR_DEFECTO_ESTACION );		
		String diaConteo = prefActuales.getString( "dia_conteo" , PreferenciasActivity.VALOR_POR_DEFECTO_DIA_CONTEO );		
		String hora = "00:00"; 
		String movimiento = this.nombreMovimiento;
		String modoTransporte = botonPresionado.getText().toString();
		
		Calendar calendar = Calendar.getInstance();
		String horaGlobal = calendar.getTime().toString();
		
		this.almacenamientoConteos.guardarConteo(estacion, diaConteo, hora, movimiento, modoTransporte, horaGlobal );
		
		String texto = String.format( "%s \t%s \t%s \t%s \t%s", estacion, diaConteo, hora, movimiento, modoTransporte );
		//Toast.makeText(getActivity(), "Almacenado: " + texto, Toast.LENGTH_SHORT).show();
		TextView textViewScroll = (TextView) this.getActivity().findViewById( R.id.textViewScroll );
		textViewScroll.setText( textViewScroll.getText() + "\n" + texto );
		
		ScrollView scrollView = (ScrollView) textViewScroll.getParent().getParent();	//Se captura el scroll asociado al  textView donde se muestra el impreso del registro
		scrollView.fullScroll( ScrollView.FOCUS_DOWN );									//Se configura el scroll para que siempre apunte el cursor al final
	}
}
