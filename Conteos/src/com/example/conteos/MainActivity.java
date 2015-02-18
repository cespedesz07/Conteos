package com.example.conteos;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.conteos.SeekBarPreference;


// CAMBIO DE SANTI
public class MainActivity extends ActionBarActivity {
	
	public boolean estadoActivo = false; //Variable que identifica si el cronometro esta corriendo
	public boolean inicioPrimerVez = true; //Variable que indica si el botón de inico se ha pulsado pro primervez
	public boolean iniciado = false; //Variable que indiica si el cronometro ha sido iniciado
	public String horaInicioFormato; //Variable que recibe la hora inicial del conteo con el formato debido
	public String horaSinFormato=""; //Variable con la hora de inicio de conteo que se va a formatear se obtiene de las preferencias
	public String horaFinal=""; //Hora en el que el conteo se detuvo
	public Cronometro mCronometro = new Cronometro();
	public Formato mFormato = new Formato();
	
	String tiempoConteoString=""; //String con el formato HH:mm:ss donde se almacena el tiempo total de conteo
	int progresoHorasConteo=0; //Variable que identifica el progreso del SeekBar de las horas a contar
	int tiempoConteo=0; //variable utilizada para dar formato segun el progreso del SeekBar
	
	public boolean complete = false;
	
	TextView cronometro;
	Button btnInicio;
	Button btnFin;
	
	
	
	
	private class CronometroHilo extends AsyncTask<String, String, String> // ---------- INICIO CLASE CronometroHilo ------------
	{
		//Iniciar acá el TextView del croonometro?
		boolean estadoActivo;
		boolean iniciado1;
		boolean iniciado;
		int progresoHorasConteo;
		int progresoConteoCronometro = 0;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Calendar mCalendar = Calendar.getInstance();
		Thread mThread = new Thread();
		int segundo = 0;
		String cronometroTiempo="";
		TimerTask timerTask;
		Timer timer =  new Timer();
		final Handler handler = new Handler();
		String horaInicio;
		String msgFina = "";
		int contadorProgreso = 0;
		int segundosProgresoSeekBar = 360;
		int progresoCronometro = 0;
		boolean finalizar;
		

		
		public CronometroHilo(boolean estado, boolean iniciadoPrimerVez, boolean iniciado, int progresoHorasConteo, String horaInicio) //Constructor con el tiempo de conteo?
		{
			this.estadoActivo = estado;
			this.iniciado1 = iniciadoPrimerVez;
			this.iniciado = iniciado;
			this.progresoHorasConteo = progresoHorasConteo;
			this.horaInicio = horaInicio;
			finalizar = false;
			//Variable que indique el tiempo de conteo
		}
		
		
		@Override
		protected void onPreExecute()
		{
			btnInicio = (Button) findViewById(R.id.btnInicio);
			btnFin = (Button) findViewById(R.id.btnFin);
			cronometro.setText(horaInicio);
		}
		
		
		protected String doInBackground(String... horaFormato) //Manejo de cronometro (Start Pause Stop), mirar si manejar el texto de los botones y Stop automatico
		{												
														
			
			
			//1 Comprobar que el cronometro no ha cumplido el tiempo de conteo
			
			String horaInicioCronometro = horaFormato[0];
			String cronometroFormateado= ""; 
			
			
			
			try
			{
				Date horaInicioFormateada = formato.parse(horaInicioCronometro);
				mCalendar.setTime(horaInicioFormateada);
				cronometroFormateado = formato.format(mCalendar.getTime());
				
				while (!isCancelled())  //verificar si el while esta funcionando ---- Mirar la doncición del While para terminar cuando se complete el tiempo
				{
					
					btnInicio.setOnClickListener(new OnClickListener()
							{
							
								@Override
								public void onClick(View v) {
								
									if (estadoActivo == false && iniciado1 == true)
									{
										Toast.makeText(getBaseContext(), "Conteo iniciado", Toast.LENGTH_SHORT).show();
										iniciado = true;
										estadoActivo = true;
										iniciado1 = false;
										btnInicio.setText("Pausar");
										
										timerTask = new TimerTask()
										{
											public void run()
											{
												handler.post(new Runnable()
												{
													public void run()
													{
														mCalendar.add(Calendar.SECOND, segundo);
														Date cronometroAumento = mCalendar.getTime();
														cronometroTiempo = formato.format(cronometroAumento);
														segundo = 1;
														contadorProgreso += 1;
														if (contadorProgreso == segundosProgresoSeekBar)
														{
															contadorProgreso = 0;
															progresoCronometro +=1;
														
															if (progresoCronometro == progresoHorasConteo)
															{
																timerTask.cancel();
																// variable que indiq	ue finalizado?
																finalizar = true;
																
															}
														}
														publishProgress(cronometroTiempo);
													}
												});	
											}
										};
										timer.schedule(timerTask, 0, 1000);
										
									}
									else if (estadoActivo == false && iniciado1 == false)
									{
										estadoActivo = true;
										btnInicio.setText("Pausar");
										timerTask = new TimerTask()
										{
											public void run()
											{
												handler.post(new Runnable()
												{
													public void run()
													{
														mCalendar.add(Calendar.SECOND, segundo);
														Date cronometroAumento = mCalendar.getTime();
														cronometroTiempo = formato.format(cronometroAumento);
														segundo = 1;
														if (contadorProgreso == segundosProgresoSeekBar)
														{
															contadorProgreso = 0;
															progresoCronometro +=1;
														
															if (progresoCronometro == progresoHorasConteo)
															{
																timerTask.cancel();
																finalizar = true;
															}
														}
														publishProgress(cronometroTiempo);
													}
												});	
											}
										};
										timer.schedule(timerTask, 0, 1000);
									}
									else if(estadoActivo == true)
									{
										estadoActivo = false;
										btnInicio.setText("Iniciar");
										timerTask.cancel();
									}
								}
							});
					
					Log.i("Testing", "Fuera del Onclick");
				
				
					btnFin.setOnClickListener(new OnClickListener()  //Revisar como finalizar.
					{
						@Override
						public void onClick(View v)
						{
							if (iniciado == true && estadoActivo == false)
							{
								btnInicio.setText("Iniciar");
								iniciado = false;
								estadoActivo = false;
								timerTask.cancel();
								finalizar = true;
							}
							else if (iniciado == true && estadoActivo == true)
							{
								btnInicio.setText("Iniciar");
								iniciado = false;
								estadoActivo = false;
								timerTask.cancel();
								finalizar = true;
							}
							else
							{
								Toast.makeText(getBaseContext(), "Por favor inicie el conteo", Toast.LENGTH_SHORT).show();
							}
						}
					});
					
					if (finalizar == true)
						cancel(true);
					
					
				
				} //Fin del While
				
				return cronometroTiempo;
			}
			catch (ParseException error)
			{
				Log.e("ParseException error", error.getMessage());
				return "Error en el formato";
			}
			

		}
		
		
		@Override
		protected void onProgressUpdate(String... cronometroTiempo) //Mostrar cada avance del cronometro
		{
			cronometro.setText(cronometroTiempo[0]);
		}
		
		
		@Override
		protected void onPostExecute(String cron) //Resetear el cronometro ya que habria finalizado automaticamente, entonces
		{											//Reiniciar el conteo y el texto de los botones
			cronometro.setText(horaInicio+"   Cronometro finalizdo "+cron); //Revisar que se esta imrpoejdo esto antes de empezar
		}
		
		
		@Override
		protected void onCancelled(String cron) //Mirar que se ejecute con el boton de Finalizar antes del tiempo total de conteo
		{
			cronometro.setText("Finalizdo");    
		}
		
		
	} //----------- FIN CLASE CronometroHilo ------------------
	

	
	
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments_container);	
	}

	
	
	public void onResume()
	{
		super.onResume();
		//Cronometro cron = new Cronometro();
		
		cronometro = (TextView) findViewById(R.id.textViewCronometro);
		
		SharedPreferences horaInicio = getSharedPreferences("com.example.conteos_preferences", MODE_PRIVATE);
		String hora = horaInicio.getString(TimePickerPreference.CLAVE_HORA_INCIO_CONTEO, TimePickerPreference.VALOR_HORA_DEFECTO);
		horaSinFormato = hora+":00";
		
		SharedPreferences horasContar = getSharedPreferences("com.example.conteos_preferences", MODE_PRIVATE);
		progresoHorasConteo = horasContar.getInt(SeekBarPreference.CLAVE_NUM_HORAS_A_CONTAR, SeekBarPreference.VALOR_NUM_HORAS_DEFECTO);
		Log.i("Progreso", ""+progresoHorasConteo);
		
		try {	
			
			//horaInicioFormato = mCronometro.formatoCronometro(horaSinFormato);
			horaInicioFormato = mFormato.formatoCronometro(horaSinFormato);
			cronometro.setText(horaInicioFormato);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		//new CronometroHilo(estadoActivo, inicioPrimerVez, iniciado, progresoHorasConteo, horaInicioFormato);
		CronometroHilo cronometro = new CronometroHilo(estadoActivo, inicioPrimerVez, iniciado, progresoHorasConteo, horaInicioFormato);
		cronometro.execute(horaInicioFormato);
	}
	

	/*
	public void onClickInicio(View v)       
	{	
		String horaFormatoActual="";
		cronometro = (TextView) findViewById(R.id.textViewCronometro);
		btnInicio = (Button) findViewById(R.id.btnInicio);
		iniciado = true;
		
		if (estadoActivo == false && inicioPrimerVez == true)
		{	
			estadoActivo = true;
			inicioPrimerVez = false;
			
			//Escribir codigo del metodo Inicio de la clase Cronometro
			//mCronometro.inicio(horaSinFormato, cronometro, horasConteo);  //El TimeTask mientras se ejecuta sigue con las lineas de abajo																		
			btnInicio.setText("Pausar");
			Toast.makeText(getBaseContext(), "El conteo ha iniciado", Toast.LENGTH_SHORT).show();

			

		}
		else
		{
			if (estadoActivo == false && inicioPrimerVez == false)
			{
				estadoActivo = true;
				horaFormatoActual = mCronometro.getCronometroTiempo(); //Obtenerlo directamente de esta clase cuando se copie el codigo de inicio
				//mCronometro.inicio(horaFormatoActual, cronometro, horasConteo);  
				btnInicio.setText("Pausar");
			}
			else														
			{
				if (estadoActivo == true)
				{
					estadoActivo = false;
					//mCronometro.pausa(); Escribir el metodo pausa en esta clase
					btnInicio.setText("Iniciar");
				}
			}
		}

	}*/
	
	
	/*
	public void onClickFinal(View v)				
	{												
													
		if (iniciado == true && estadoActivo == false)
		{
			iniciado = false;
			estadoActivo = false;
			Button btnInicio = (Button) findViewById(R.id.btnInicio);
			horaFinal = mCronometro.getCronometroTiempo(); //Obtener el vlr cuando escriba el codigo en esta misma clase
			//mCronometro.fin(horaInicioFormato, horaFinal); Escribir el código en esta misma clase
			inicioPrimerVez = true;
			btnInicio.setText("Iniciar");
			Toast.makeText(getBaseContext(), "El conteo ha finalizado", Toast.LENGTH_SHORT).show();
			//startActivity(new Intent("com.example.conteos.SecondActivity"));
		}
		else if (iniciado == true && estadoActivo == true)
		{
			iniciado = false;
			estadoActivo = false;
			Button btnInicio = (Button) findViewById(R.id.btnInicio);
			horaFinal = mCronometro.getCronometroTiempo(); //Obtener el vlr cuando escriba el codigo en esta misma clase
			//mCronometro.fin(horaInicioFormato, horaFinal); Escribir el código en esta misma clase
			inicioPrimerVez = true;
			btnInicio.setText("Iniciar");
			Toast.makeText(getBaseContext(), "El conteo ha finalizado", Toast.LENGTH_SHORT).show();
			//startActivity(new Intent("com.example.conteos.SecondActivity"));
		}
		else
		{
			Toast.makeText(getBaseContext(), "Por favor inicie el conteo", Toast.LENGTH_SHORT).show();
		}
	}*/

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.acerca_de) {
			return true;
			//TODO Implementar actividad acerca de
		}
		else if ( id == R.id.preferencias ){
			abrirPreferencias( null );
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public void abrirPreferencias( View vista ){
		Intent intentPref = new Intent( this, PreferenciasActivity.class );
		startActivity( intentPref );		
	}
	
}
