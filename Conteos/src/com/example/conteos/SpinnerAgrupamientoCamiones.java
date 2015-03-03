package com.example.conteos;

import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerAgrupamientoCamiones extends Spinner implements OnMultiChoiceClickListener {

	
	private ArrayAdapter<String> simpleAdapter;
	private String[] items;
	private boolean[] itemsSeleccionados;
	
	
	public SpinnerAgrupamientoCamiones(Context context, AttributeSet attr) {
		super(context, attr);
		this.simpleAdapter = new ArrayAdapter<String>( context, android.R.layout.simple_spinner_item );
		setAdapter( this.simpleAdapter );
	}
	
	
	@Override  
	public boolean performClick() {  
		AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );  
		builder.setMultiChoiceItems(items, itemsSeleccionados, this );
		builder.show();  
		return true;  
	}  
	
	
	public void setItems( String[] items ){
		this.items = items;  
		this.itemsSeleccionados = new boolean[ this.items.length ];  
		this.simpleAdapter.clear();  
		this.simpleAdapter.add( items[0]);  
		Arrays.fill( this.itemsSeleccionados, false ); 
	}
	

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

}
