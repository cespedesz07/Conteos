package com.example.conteos;


import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.preference.DialogPreference;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class SeekBarPreference extends DialogPreference {
	
	public static final String HORAS_MINUTOS = "horasMinutos";
	public static final String HORAS_STRING = "horasString";
	public static final String CLAVE_NUM_HORAS_A_CONTAR = "num_horas_a_contar";
	public static final int VALOR_NUM_HORAS_DEFECTO = 2;
	
	private static final int HORA_MIN = 1;
	
	String tiempoConteoString = "";
	
	
	private TextView textViewHoraSeleccionada;
	private SeekBar seekBar;
	private TextView textViewIntervaloHoras;
	private SharedPreferences prefActuales;

	
	
	public SeekBarPreference( Context context, AttributeSet attrs ){
		super(context, attrs);
		Log.i( "Attribute Name", attrs.getAttributeName(0) );
		setDialogTitle( "Seleccione el l�mte de horas a contar" );
		setDialogLayoutResource( R.layout.seek_bar_picker );
		setPositiveButtonText("Aceptar");
		setNegativeButtonText("Cancelar");
	}
	

	
	
	@Override
	protected void onBindDialogView( View vista ){
		this.textViewHoraSeleccionada = (TextView) vista.findViewById( R.id.textViewHoraSeleccionada );
		this.seekBar = (SeekBar) vista.findViewById( R.id.seekBar );
		this.textViewIntervaloHoras = (TextView) vista.findViewById( R.id.textViewIntervaloHoras );
		
		this.prefActuales = getSharedPreferences();
		
		this.seekBar.setMax( 48 );
		this.seekBar.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub				
			}			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub				
			}			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if ( progress < HORA_MIN ){
					seekBar.setProgress( HORA_MIN );
				}
				else{ 
					textViewHoraSeleccionada.setText( String.valueOf(convertirProgresoMinutos(progress, HORAS_STRING)) );
					textViewIntervaloHoras.setText(  
							crearIntervaloHoras( prefActuales, (Integer) convertirProgresoMinutos(progress, HORAS_MINUTOS) )   
							);
				}
			}
		} );		
		
		int numHorasContarActuales = prefActuales.getInt( CLAVE_NUM_HORAS_A_CONTAR, VALOR_NUM_HORAS_DEFECTO );
		
		this.textViewHoraSeleccionada.setText( numHorasContarActuales + " Hrs 00 Min" );
		this.seekBar.setProgress( numHorasContarActuales );
		this.textViewIntervaloHoras.setText(  
				crearIntervaloHoras( prefActuales, (Integer) convertirProgresoMinutos(this.seekBar.getProgress(), HORAS_MINUTOS) )   
				);
		
		super.onBindDialogView( vista );
	}
	
	
	@Override
	protected void onDialogClosed( boolean haAceptado ){
		if ( haAceptado ){
			persistInt( this.seekBar.getProgress() );
		}
	}
	
	
	
	private String crearIntervaloHoras( SharedPreferences prefActuales, int horasEnMin ){
		String intervaloHoras = "";
		String horaActual = prefActuales.getString( TimePickerPreference.CLAVE_HORA_INCIO_CONTEO,
													TimePickerPreference.VALOR_HORA_DEFECTO );
		
		SimpleDateFormat formatoHora = new SimpleDateFormat( "hh:mm" );
		SimpleDateFormat formatoSalida = new SimpleDateFormat( "hh:mm a" );
		try{
			Date horaActualFormat = formatoHora.parse( horaActual );
			
			Calendar cal = Calendar.getInstance();
			cal.setTime( horaActualFormat );
			cal.add( Calendar.MINUTE, horasEnMin );
			Date horaConAumento = cal.getTime();
			
			horaActual = formatoSalida.format( horaActualFormat );
			String horaConAumentoString = formatoSalida.format( horaConAumento );
			intervaloHoras = String.format( "de las %s \n a las %s", horaActual, horaConAumentoString );
			return intervaloHoras;
		}
		catch ( ParseException error ){
			Log.e( "ParseException error" , error.getMessage() );
			return intervaloHoras;
		}		
		
	}
	
	
	public Object convertirProgresoMinutos( int progreso, String tipoRetorno ){
		int horasEnMin = 0;
		String horasString = "";
		
		//Si la barra de progreso est� ubicada en 1, 3, 5, 7, es porque est� ubicado en las horas 1, 2, 3
		//Lo transforma al minuto respectivo: 60, 120, 180    
		if ( progreso % 2 != 0 ){
			horasEnMin = ((progreso / 2) + (progreso % 2));
			horasString = String.valueOf( horasEnMin ) + " Hrs 00 Min";
			tiempoConteoString = String.valueOf( horasEnMin ) + ":00:00:";
			horasEnMin = horasEnMin * 60;
		}
		//Si la barra de progreso est� ubicada en 2, 4, 6, 8, 
		//corresponde a la hora 2hr 30min, 3hr 30min, 4hr 30min
		//Entonces se cambia al minuto respectivo: 90, 150, 210
		else{
			horasEnMin = ( (progreso / 2) );
			horasString = String.valueOf( horasEnMin ) + " Hrs 30 Min";
			tiempoConteoString= String.valueOf( horasEnMin ) + ":30:00";	
			horasEnMin = (horasEnMin * 60) + 30;
		}
		
		if ( tipoRetorno.equals( HORAS_MINUTOS ) ){
			return horasEnMin;
		}
		else if ( tipoRetorno.equals( HORAS_STRING ) ){
			return horasString;
		}
		else{
			return null;
		}
		
		
	}
	
	
	/*
	public void tiempoConteo (int progreso)
	{
		int tiempoConteo=0;
		
		if ( progreso % 2 != 0 ){
			tiempoConteo = ((progreso / 2) + (progreso % 2));
			tiempoConteoString = String.valueOf( tiempoConteo ) + ":00:00:";
		}
		else{
			tiempoConteo = ( (progreso / 2) );
			tiempoConteoString= String.valueOf( tiempoConteo ) + ":30:00";	
		}		
	}*/
	
	public String obtenerFormato()
	{
		return tiempoConteoString;
	}
		
		
	
	

}
