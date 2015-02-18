package com.example.conteos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.util.Log;


public class Formato {
	
	SimpleDateFormat formato = new SimpleDateFormat ("HH:mm:ss");
	Calendar mCalendar = Calendar.getInstance();
	
	
	
	public String formatoCronometro(String horaInicioCronometro) throws java.text.ParseException
	{
		String cronometroFormateado="";
		try
		{
			Date horaInicioFormateada = formato.parse(horaInicioCronometro);
			mCalendar.setTime(horaInicioFormateada);
			cronometroFormateado = formato.format(mCalendar.getTime());
			return cronometroFormateado;
			
		}
		catch (ParseException error)
		{
			Log.e("ParseExceptionError", error.getMessage());
			return cronometroFormateado;
		}
	}

}
