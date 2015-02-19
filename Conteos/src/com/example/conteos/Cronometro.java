package com.example.conteos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;






public class Cronometro{
	
	int seg=00;
	int min=00;
	int hor=00;
	TimerTask timerTask; 
	Calendar mCalendar = Calendar.getInstance(); 
	SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss"); 
	String cronometroTiempo; //String que muestra el valor del cronometro con su correspondiente ormato
	private TextView textViewCron; //TextView asociado al cronometro
	private TextView textViewCronFin; //TextView asociado al mensaje y al tiempo de finalización del cronometro
	SeekBarPreference countingTime;
	int progresoCronometro=0; //Progreso asociado a cada 30 minutos del cronometro
	int segundosTiempoConteo = 360; //Segundos requeridos para que haya un progreso cada media hora
	int contadorProgreso=0; //Contador de los segundos del cronometro para que haya un progreso cada media hora
	
	boolean completed = false;


	
	/**
	 * Este metodo coge un String con el formato HH:mm:ss y lo lleva a un objeto de tipo Calendar donde maneja
	 * el mismo formato 
	 * @param horaInicioCronometro - Formato HH:mm:ss 
	 * @param estado
	 * @return String con el formato de la hora de inicio 
	 * @throws java.text.ParseException
	 */
	public String formatoCronometro(String horaInicioCronometro) throws java.text.ParseException
	{
		
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
	
	
	/**
	 * Metodo que ejecuta un TimerTask cada segundo que hace que el cronometro se ejecute
	 * @param horaInicioCronometro
	 * @param view
	 * @param horaFinal
	 */
	public void inicio(String horaInicioCronometro, View view, final int horaFinal)
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
							contadorProgreso += 1;
							Log.i("ContadorProgresoCron",""+contadorProgreso);
							Log.i("SegundosConteo", ""+segundosTiempoConteo);
							
							if (contadorProgreso == segundosTiempoConteo) //Verificación de el progreso
							{
								contadorProgreso=0;
								progresoCronometro += 1;
								//segundosTiempoConteo = 180;
								
								if (progresoCronometro == 1)//horaFinal)
								{
									timerTask.cancel();	 //Verificar si puedo enviar con un Intent unos valores, pero en el MainActivity
									completed =true;					//donde recibo los valores?
									//startActivity(new Intent("com.example.conteos.SecondActivity"));
								}						//Mirar si cambia el cronometro con un Thread													
							}					
						}
					});
				}
			};
			
			timer.schedule(timerTask, 0, 1000);	
			
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * La tarea que se esta ejecutando se cancela
	 */
	public void pausa()
	{	
		timerTask.cancel();
	}
	
	/**
	 * Este metodo finaliza la tarea que se esta ejecutando y lleva el cronometro a la hora de inicio de conteo
	 * @param horaInicio - Parametro en el que indica la hora de inicio seleccionada
	 * @param horaFinal - Parametro que contiene el valor del cronometro en el momento en el que se finalizo el conteo
	 */
	public void fin(String horaInicio,String horaFinal)
	{
		timerTask.cancel();
		//timerTask = null;
		//seg = 0;
		textViewCron.setText(horaInicio);
		textViewCron.setText(horaInicio+"    Hora final de conteo: "+horaFinal);
		
	}
	
	
	public boolean obtenerEstado()
	{
		return completed;
	}
	
		
	public String getCronometroTiempo()
	{

		return cronometroTiempo;
	}
	
	
}
