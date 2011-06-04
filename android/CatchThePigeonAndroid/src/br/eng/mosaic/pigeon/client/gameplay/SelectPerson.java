package br.eng.mosaic.pigeon.client.gameplay;

import br.eng.mosaic.pigeon.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectPerson extends Activity{
	ImageButton figeon, sigeon, figean;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.char_select);
		
		figeon = (ImageButton) findViewById(R.id.selectFigeon);
		sigeon = (ImageButton) findViewById(R.id.selectSigeon);
		figean = (ImageButton) findViewById(R.id.selectFigean);
		
		figeon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectFigeon();
				
			}
		});
		sigeon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectSigeon();
				
			}
		});
		figean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectFigean();
			}
		});
		
	}
	public void selectFigeon(){
		Intent i = new Intent(this, Stage1.class);
		startActivity(i);
	}
	public void selectSigeon(){
		Intent i = new Intent(this, Stage1.class);
		startActivity(i);
	}
	public void selectFigean(){
		Intent i = new Intent(this, Stage1.class);
		startActivity(i);
	}

}
