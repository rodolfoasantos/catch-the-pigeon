package br.eng.mosaic.pigeon.client.gameplay;

import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectPerson extends Activity {
	
	private ImageButton figeon;
	private ImageButton sigeon;
	private ImageButton figean;
	private ImageButton back;
	private ImageButton audio;
	private int cont;
	
	public static int pigeonSelect;
	public static boolean mute;
	private static SoundManager sm;  
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.char_select);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);  
	    sm = SoundManager.getInstance(this);		
	    mute = false;
		figeon = (ImageButton) findViewById(R.id.selectFigeon);
		sigeon = (ImageButton) findViewById(R.id.selectSigeon);
		figean = (ImageButton) findViewById(R.id.selectFigean);
		back = (ImageButton) findViewById(R.id.back_button);
		audio = (ImageButton) findViewById(R.id.audio_button);
		
		sm.playSound(0);
		
		try {
			figeon.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sm.stopSounds();
					pigeonSelect = 1;
					Intent i = new Intent(getBaseContext(), Stage1.class);
				//	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("select", "figeon");
					startActivity(i);				
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "figeon button is null. See the names of the IDs in char_select.xml");
		}
		
		try {
			sigeon.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sm.stopSounds();
					pigeonSelect = 3;
					Intent i = new Intent(getBaseContext(), Stage1.class);
					i.putExtra("select", "sigeon");
					startActivity(i);
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "sigeon button is null. See the names of the IDs in char_select.xml");
		}
		
		try {
			figean.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sm.stopSounds();
					pigeonSelect = 2;
					Intent i = new Intent(getBaseContext(), Stage1.class);
					i.putExtra("select", "figean");
					startActivity(i);
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "figean button is null. See the names of the IDs in char_select.xml");
		}
		
		try {
			back.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sm.stopSounds();
					startActivity(new Intent(getBaseContext(), MainActivity.class));
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "back button is null. See the names of the IDs in char_select.xml");
		}
		
		try {
			audio.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) { 
					if (cont == 0) {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_mute);
						mute = true;
						sm.stopSounds();
						cont++;
					}else {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_icon);
						mute = false;
						sm.playSound(0);
						cont=0;
					} 				
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "audio button is null. See the names of the IDs in char_select.xml");
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
	    	sm.stopSounds();
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