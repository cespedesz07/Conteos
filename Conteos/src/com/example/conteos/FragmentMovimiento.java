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
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;





public class FragmentMovimiento extends Fragment implements OnClickListener {
	
	
	
	private static final int NUM_BOTONES_EN_FILA = 2; //Filas en el TableLayout
	private String nombreMovimiento; 
	private ArrayList<String> movimientosActuales;
	private ArrayList<String> modosTransporteActuales;
	private int anchoLayout; //Llamado desde el MainActivity para establecer el ancho de cada Fragment
	private ArregloModosMovimientos arregloModosMovimiento; //-------Entender esta clase
	private AlmacenamientoConteos almacenamientoConteos;	//------Entender esta clase
	private Context context;
	
	private int ancho;
	private int alto;
	
	
	public FragmentMovimiento( String nombreMovimiento, Set<String> movimientosActuales, Set<String> modosTransporteActuales, int anchoLayout, //El constructor se llama en actualizarGUI
			AlmacenamientoConteos almacenamientoConteos, Context context ){
		super();
		this.nombreMovimiento = nombreMovimiento;
		this.movimientosActuales = new ArrayList<String> (movimientosActuales);
		this.modosTransporteActuales = new ArrayList<String>( modosTransporteActuales );	
		this.anchoLayout = anchoLayout;
		this.arregloModosMovimiento = new ArregloModosMovimientos();
		this.almacenamientoConteos = almacenamientoConteos;
	}
	
	
	
	public void establecerDimension(ArrayList<String> movimientosActuales, ArrayList<String>modosTransporte)
	{
		if (movimientosActuales.size() == 3)
		{
			if (modosTransporte.size() == 6){
				alto = 90;
				ancho = 150;
			}
			else if (modosTransporte.size() == 5){
				alto = 90;
				ancho = 150;
			}
			else if (modosTransporte.size() == 4){
				alto = 130;
				ancho = 150;
			}
			else if (modosTransporte.size() == 3){
				alto = 130;
				ancho = 160;
			}
			else if (modosTransporte.size() == 2){
				alto = 210;
				ancho = 160;
			}
			else if (modosTransporte.size() == 1){
				alto = 210;
				ancho = 210;
			}
			else{
				alto = 80;
				ancho = 150;
			}
				
		}
		else if (movimientosActuales.size() == 2)
		{
			if (modosTransporte.size() == 6){
				alto = 90;
				ancho = 215;
			}
			else if (modosTransporte.size() == 5){
				alto = 90;
				ancho = 215;
			}
			else if (modosTransporte.size() == 4){
				alto = 130;
				ancho = 215;
			}
			else if (modosTransporte.size() == 3){
				alto = 130;
				ancho = 215;
			}
			else if (modosTransporte.size() == 2){
				alto = 210;
				ancho = 215;
			}
			else if (modosTransporte.size() == 1){
				alto = 210;
				ancho = 270;
			}
			else{
				alto = 80;
				ancho = 215;
			}
		}
		else
		{
			
			if (modosTransporte.size() == 6){
				alto = 90;
				ancho = 380;
			}
			else if (modosTransporte.size() == 5){
				alto = 90;
				ancho = 380;
			}
			else if (modosTransporte.size() == 4){
				alto = 130;
				ancho = 380;
			}
			else if (modosTransporte.size() == 3){
				alto = 130;
				ancho = 380;
			}
			else if (modosTransporte.size() == 2){
				alto = 210;
				ancho = 380;
			}
			else if (modosTransporte.size() == 1){
				alto = 210;
				ancho = 590;
			}
			else{
				alto = 80;
				ancho = 380;
			}
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate( R.layout.fragment_movimiento, container, false ); //Infla el Fragment
		//Verificar si el atributo layout_weight modifica en algo el ViewGroup y mirar con las coordenadas de los botones
		TextView nombreMovimientoTextView = (TextView) view.findViewById( R.id.nombreMovimiento );
		nombreMovimientoTextView.setText(  String.valueOf( this.nombreMovimiento )  );
		establecerDimension(this.movimientosActuales, this.modosTransporteActuales);
		
		
		
		TableLayout tablaBotonesModosTransporte = (TableLayout) view.findViewById( R.id.tablaBotonesModosTransporte );		
		int numFilas = (this.modosTransporteActuales.size() / NUM_BOTONES_EN_FILA) + (this.modosTransporteActuales.size() % NUM_BOTONES_EN_FILA);
		for ( int i=0; i<numFilas; i++ ){
			TableRow fila = new TableRow( getActivity() );
			fila.setGravity( Gravity.CENTER ); //Mirar con este parametro si se puede modificar el espaciado de botones
			
			
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

					//botonModoTransporte.setGravity(Gravity.CENTER);
					//botonModoTransporte.setPadding(0, 0, 0, 0);
					botonModoTransporte.setHeight(alto);
					botonModoTransporte.setWidth(ancho);

					//botonModoTransporte.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					//botonModoTransporte.setLayoutParams(  new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT )  ); //No esta funcionando bien, revisar como funciona

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
		
		TextView textViewHora = (TextView) this.getActivity().findViewById( R.id.textViewCronometro );
		String hora = textViewHora.getText().toString();
		
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
