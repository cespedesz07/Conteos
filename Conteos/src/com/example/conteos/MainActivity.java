package com.example.conteos;




import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;



public class MainActivity extends ActionBarActivity {
	
	public boolean estadoActivo = false; //Variable que identifica si el cronometro esta corriendo
	public boolean firstTime = true; //Variable que indica si el botón de inico se ha pulsado pro primervez
	public boolean iniciado = false; //Variable que indiica si el cronometro ha sido iniciado
	public String horaInicioFormato; //Variable que recibe la hora inicial del conteo con el formato debido
	public String horaSinFormato=""; //Variable con la hora de inicio de conteo que se va a formatear
	public String horaFinal=""; //Hora en el que el conteo se detuvo
	public Cronometro mCronometro = new Cronometro();
	public SeekBarPreference tiempoConteo;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments_container);
		
	}
	
	
	
	public void onResume()
	{
		super.onResume();
		//Cronometro cron = new Cronometro();
		
		TextView cronometro = (TextView) findViewById(R.id.textViewCronometro);
		
		SharedPreferences horaInicio = getSharedPreferences("com.example.conteos_preferences", MODE_PRIVATE);
		String hora = horaInicio.getString(TimePickerPreference.CLAVE_HORA_INCIO_CONTEO, TimePickerPreference.VALOR_HORA_DEFECTO);
		horaSinFormato = hora+":00";
		
		
		SharedPreferences horasContar = getSharedPreferences("com.example.conteos_preferences", MODE_PRIVATE);
		int horasConteo = horasContar.getInt(SeekBarPreference.CLAVE_NUM_HORAS_A_CONTAR, SeekBarPreference.VALOR_NUM_HORAS_DEFECTO);
		Log.i("Numero horas a contar", ""+horasConteo);
		
		//String timeCount = (String)tiempoConteo.convertirProgresoMinutos(horasConteo, SeekBarPreference.HORAS_MINUTOS);
		//Log.i("Horas minutos", timeCount.toString());
		
		try {
			horaInicioFormato = mCronometro.formatoCronometro(horaSinFormato);
			cronometro.setText(horaInicioFormato);
			

			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

	
	public void onClickInicio(View v)
	{
		String horaFormatoActual="";
		TextView cronometro = (TextView) findViewById(R.id.textViewCronometro);
		Button btnInicio = (Button) findViewById(R.id.btnInicio);
		iniciado = true;
		
		
		if (estadoActivo == false && firstTime == true)
		{	
			estadoActivo = true;
			firstTime = false;
			mCronometro.inicio(horaSinFormato, cronometro);
			btnInicio.setText("Pausar");
			Toast.makeText(getBaseContext(), "El conteo ha iniciado", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if (estadoActivo == false && firstTime == false)
			{
				estadoActivo = true;
				horaFormatoActual = mCronometro.getcronometroTiempo();
				mCronometro.inicio(horaFormatoActual, cronometro);
				btnInicio.setText("Pausar");
			}
			else														
			{
				if (estadoActivo == true)
				{
					estadoActivo = false;
					mCronometro.pausa();
					btnInicio.setText("Iniciar");
				}
			}
		}
			
	}
	
	
	
	public void onClickFinal(View v)				
	{												
													
		if (iniciado == true && estadoActivo == false)
		{
			
			estadoActivo = false;
			Button btnInicio = (Button) findViewById(R.id.btnInicio);
			horaFinal = mCronometro.getcronometroTiempo();
			mCronometro.fin(horaInicioFormato, horaFinal);
			firstTime = true;
			btnInicio.setText("Iniciar");
			Toast.makeText(getBaseContext(), "El conteo ha finalizado", Toast.LENGTH_SHORT).show();
		
		}
		else if (iniciado == true && estadoActivo == true)
		{
			estadoActivo = false;
			Button btnInicio = (Button) findViewById(R.id.btnInicio);
			horaFinal = mCronometro.getcronometroTiempo();
			mCronometro.fin(horaInicioFormato, horaFinal);
			firstTime = true;
			btnInicio.setText("Iniciar");
			Toast.makeText(getBaseContext(), "El conteo ha finalizado", Toast.LENGTH_SHORT).show();
			
		}
		else
		{
			Toast.makeText(getBaseContext(), "Por favor inicie el conteo", Toast.LENGTH_SHORT).show();
		}
	}

	
	
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
