package br.eng.mosaic.pigeon.client.gameplay;

import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectPerson extends Activity {
	
	private ImageButton figeon, sigeon, figean, back, audio;
	private int cont;
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.char_select);
		
		figeon = (ImageButton) findViewById(R.id.selectFigeon);
		sigeon = (ImageButton) findViewById(R.id.selectSigeon);
		figean = (ImageButton) findViewById(R.id.selectFigean);
		back = (ImageButton) findViewById(R.id.back_button);
		audio = (ImageButton) findViewById(R.id.audio_button);
		
		figeon.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SelectPerson.this, Stage1.class);
				i.putExtra("select", "figeon");
				startActivity(i);				
			}
		});
		
		sigeon.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {			
				Intent i = new Intent(SelectPerson.this, Stage1.class);
				i.putExtra("select", "sigeon");
				startActivity(i);
			}
		});
		
		figean.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SelectPerson.this, Stage1.class);
				i.putExtra("select", "figean");
				startActivity(i);
			}
		});
		
		try {
			back.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getBaseContext(), MainActivity.class));
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "back_button is null");
		}
		
		try {
			audio.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) { 
					if (cont==0) {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_mute);
						cont++;
					}else {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_icon);
						cont=0;
					} 				
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "audio_button is null");
		}
			
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
	    	startActivity(new Intent(getBaseContext(), MainActivity.class));
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish(); // Close the screen
	}
}