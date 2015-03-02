package com.example.conteos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class AlmacenamientoConteos extends SQLiteOpenHelper {
	
	//Constantes de la BD
	private static final String NOMBRE_DB = "conteos_vehiculares";
	private static final int VERSION_DB = 1;
	
	//- Consultas SQL
	//Creación de la base de datos
	private static final String SQL_CREACION_TABLA = ""
			+ "CREATE TABLE " + NOMBRE_DB + "("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "estacion TEXT NOT NULL,"
					+ "dia TEXT NOT NULL,"
					+ "hora TEXT NOT NULL,"
					+ "movimiento TEXT NOT NULL,"
					+ "modo_transporte TEXT NOT NULL,"
					+ "hora_global TEXT NOT NULL);";
	
	//Insercion en la base de datos
	private static final String SQL_INSERCION_TABLA = "INSERT INTO conteos_vehiculares( 'estacion', 'dia', 'hora', 'movimiento', 'modo_transporte', 'hora_global' ) VALUES ( ?, ?, ?, ?, ?, ? )";
	
	
	
	public AlmacenamientoConteos( Context context ){
		super(context, NOMBRE_DB, null, VERSION_DB);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( SQL_CREACION_TABLA );
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				
	}
	
	
	public void guardarConteo( String estacion, String dia, String hora, String movimiento, String modoTransporte, String horaGlobal ){
		SQLiteDatabase db = getWritableDatabase();
		SQLiteStatement stat = db.compileStatement( SQL_INSERCION_TABLA );
		
		stat.bindString( 1, estacion );
		stat.bindString( 2, dia );
		stat.bindString( 3, hora );
		stat.bindString( 4, movimiento );
		stat.bindString( 5, modoTransporte );
		stat.bindString( 6, horaGlobal );
		stat.execute();
		stat.clearBindings();
	}

}
