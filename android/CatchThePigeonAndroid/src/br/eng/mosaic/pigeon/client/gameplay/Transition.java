package br.eng.mosaic.pigeon.client.gameplay;


import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;

public class Transition extends Activity{
	ImageButton next,  back, audio, person;
	int cont;
	
	public static String[]  level;
	public static int lev;
	
	private void sendScoreAndPublish() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://10.0.2.2:8888/oauth/facebook/publish.do");
			HttpResponse response = httpClient.execute(httpPost);
			InputStream is = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transition);
	
		Intent intent = getIntent();
		level = (String[]) intent.getSerializableExtra("level");
		lev = Integer.parseInt(level[1]);
		
		next  = (ImageButton) findViewById(R.id.next_level);
		back = (ImageButton) findViewById(R.id.back_button_transition);
		audio = (ImageButton) findViewById(R.id.audio_button_transition);
		person = (ImageButton) findViewById(R.id.level_person);
		
		try {
			if (level[0].equals("figean"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_figean_noselection);
			else if (level[0].equals("sigeon"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_sigeon_noselection);
			else if (level[0].equals("figeon"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_figeon_noselection);
		} catch (NullPointerException np) {
			Log.e("Null", "person button is null. See the names of the IDs in transition.xml");
		}	
						
		//Stage.mMainMusic.stop();
		try {
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (lev == 2) {
						Intent i = new Intent(getBaseContext(),Stage2.class);
						i.putExtra("select", level[0]);
						startActivity(i);
					}
					if (lev == 3) {
						Intent i = new Intent(getBaseContext(),Stage3.class);
						i.putExtra("select", level[0]);
						startActivity(i);					
					}
					BadPigeon.velocity *= 1.5;
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "next button is null. See the names of the IDs in transition.xml");
		}
		
		try {
			back.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getBaseContext(), SelectPerson.class));
					//Stage.mMainMusic.play();
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "back button is null. See the names of the IDs in transition.xml");
		}
		
		try {
			audio.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (cont==0) {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_mute);					
						cont++;
					} else {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_icon);					
						cont=0;
					}
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "audio button is null. See the names of the IDs in transition.xml");
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
	    	startActivity(new Intent(getBaseContext(), SelectPerson.class));
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish(); //Close the screen
	}
	

}