package com.example.conteos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.TextView;
import org.apache.http.ParseException;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View;

//Eddy
public class Cronometro{
	
	int seg=00;
	int min=00;
	int hor=00;
	TimerTask timerTask; 
	Calendar mCalendar = Calendar.getInstance(); 
	SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss"); 
	String cronometroTiempo;
	private TextView textViewCron;
	private TextView textViewCronFin;
	
	
	/**
	 * Este metodo coge un String con el formato hh:mm:ss y lo lleva a un objeto de tipo Calendar donde maneja
	 * el mismo formato 
	 * @param horaInicioCronometro - Formato hh:mm:ss 
	 * @param estado
	 * @return
	 * @throws java.text.ParseException
	 */
	public String formatoCronometro(String horaInicioCronometro) throws java.text.ParseException
	{
		//En este punto el mCalendar tiene el valor actual de la hora del sistmea
		
		String cronometroFormateado="";
		//SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try
		{
			Date horaInicioFormateada = formato.parse(horaInicioCronometro); //Lleva el String que entra al formato deseado
			//Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(horaInicioFormateada); //Establece el objeto Calendar con la hora en el formato deseado
			cronometroFormateado = formato.format(mCalendar.getTime()); //Se obtiene la hora formateada timpo Calendar y se pasa a un String con el mismo formato
			
			 //En este punto el getTime del mCalendar obtiene el vlr formateado de la hora de inicio
			return cronometroFormateado;
						
		}
		catch(ParseException error)
		{
			Log.e("ParseException error", error.getMessage());
			return cronometroFormateado;
		}
				
	}
	
	
	
	public void inicio(String horaInicioCronometro, View view)
	{
		
		final Handler handler = new Handler();
		Timer timer = new Timer();
		this.textViewCron = (TextView) view.findViewById(R.id.textViewCronometro);
		String cronometroFormateado = "";
		
		

		try {
			Date horaInicioFormateada = formato.parse(horaInicioCronometro);
			mCalendar.setTime(horaInicioFormateada);
			cronometroFormateado = formato.format(mCalendar.getTime());
			
			 
			timerTask = new TimerTask()
			{
				int segundo = 0;
				public void run()
				{
					handler.post(new Runnable()
					{
						public void run()
						{
					
							mCalendar.add(Calendar.SECOND, segundo);
							Date cronometroAumento = mCalendar.getTime();
							cronometroTiempo = formato.format(cronometroAumento);
							textViewCron.setText(cronometroTiempo);
							segundo = 1;
														
						}
					});
				}
			};
			
			timer.schedule(timerTask, 0, 1000);	
			
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	public void pausa()
	{
		
		timerTask.cancel();
	}
	
	
	public void fin(String horaInicio,String horaFinal)
	{
		timerTask.cancel();
		//timerTask = null;
		//seg = 0;
		textViewCron.setText(horaInicio);
		textViewCron.setText(horaInicio+"    Hora final de conteo: "+horaFinal);
	}
	
	
	public String getcronometroTiempo()
	{
		return cronometroTiempo;
	}
	
	
}
