package br.eng.mosaic.pigeon.client.gameplay;

import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectPerson extends Activity{
	ImageButton figeon, sigeon, figean, back, audio;
	int cont;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.char_select);
		
		cont=0;
		figeon = (ImageButton) findViewById(R.id.selectFigeon);
		sigeon = (ImageButton) findViewById(R.id.selectSigeon);
		figean = (ImageButton) findViewById(R.id.selectFigean);
		back = (ImageButton) findViewById(R.id.back_button);
		audio = (ImageButton) findViewById(R.id.audio_button);
		//mosaic_pigeon_icon_mute_icon
		
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
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), MainActivity.class));
				//Stage.mMainMusic.play();
			}
		});
		
		audio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cont==0)
				{
					v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_mute);
					cont++;
				}else
				{
					v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_icon);
					cont=0;
				}
			//v.setBackgroundDrawable(R.drawable.mosaic_pigeon_icon_mute_icon);
			//mosaic_pigeon_icon_mute_icon
				
			}
		});
		
	}
	public void selectFigeon(){
		
		Intent i = new Intent(this,Stage1.class);
		i.putExtra("select", "figeon");
		startActivity(i);
		
		//Intent i = new Intent(this, Stage1.class);
		//startActivity(i);
	}
	public void selectSigeon(){
		Intent i = new Intent(this,Stage1.class);
		i.putExtra("select", "sigeon");
		startActivity(i);
		
		/*Intent i = new Intent(this, Stage1.class);
		startActivity(i);*/
	}
	public void selectFigean(){
		Intent i = new Intent(this,Stage1.class);
		i.putExtra("select", "figean");
		startActivity(i);
		
		/*Intent i = new Intent(this, Stage1.class);
		startActivity(i);*/
	}
	/**
	 * @author jamilson
	 * @Description Implementation for button  back of Activity
	 * @param Indentification of onclick for mouse
	 * @return value boolean
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        	//finish();
	    	startActivity(new Intent(getBaseContext(), MainActivity.class));
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		// Fecha a tela
		finish();
	}
}