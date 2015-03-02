package com.example.conteos;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;


public class TimePickerPreference extends DialogPreference {

	
	private static final String SEPARADOR_HORA = ":";
	public static final String CLAVE_HORA_INCIO_CONTEO = "hora_inicio_conteo";
	public static final String VALOR_HORA_DEFECTO = new SimpleDateFormat("HH:mm").format( new Date() );
	
	private TimePicker timePicker;
	
	
	public TimePickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogTitle("Seleccione la hora de inicio de conteo: ");
		setDialogLayoutResource( R.layout.time_picker );		
		setPositiveButtonText( "Aceptar" );
		setNegativeButtonText( "Cancelar" );
	}	
	
	/**
	 * Método que asocia la vista time_picker.xml al actual DialogPreference.
	 * Útil al momento de mostrar el time_picker.xml al momento de abirse el dialogo
	 * y tambien útila para capturar la hora seleccionada
	 */
	@Override
	protected void onBindDialogView( View vista ){
		this.timePicker = (TimePicker) vista.findViewById( R.id.timePicker );
		this.timePicker.setIs24HourView(true);
		
		SharedPreferences prefActuales = getSharedPreferences();
		
		//Si no existe la preferencia de hora de inicio de conteo, 
		//el sgte método retorna el valor x defecto
		String[] horaActual = prefActuales.getString( CLAVE_HORA_INCIO_CONTEO, VALOR_HORA_DEFECTO ).split( SEPARADOR_HORA );

		this.timePicker.setCurrentHour(   Integer.valueOf(horaActual[0]) );
		this.timePicker.setCurrentMinute( Integer.valueOf(horaActual[1]) );
		super.onBindDialogView( vista );
	}	
	
	/**
	 * Al dar click en los botones aceptar o cancelar, se ejecuta este método
	 */
	@Override
	protected void onDialogClosed( boolean haAceptado ){
		if ( haAceptado ){
			//Si se ha dado click en Aceptar, se guarda la hora actual en las preferencias
			persistString( String.valueOf(timePicker.getCurrentHour() + SEPARADOR_HORA + timePicker.getCurrentMinute()) );
		}
	}
	

}
