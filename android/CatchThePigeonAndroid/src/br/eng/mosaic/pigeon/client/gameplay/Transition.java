package br.eng.mosaic.pigeon.client.gameplay;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.infra.PigeonSharedUser;
import br.eng.mosaic.pigeon.communication.AsynCallback;
import br.eng.mosaic.pigeon.communication.ProxyClient;
import br.eng.mosaic.pigeon.communication.ServerConstants;
import br.eng.mosaic.pigeon.communication.Source;

public class Transition extends Activity{
	ImageButton next,  back, audio, person;
	int cont;
	
	public static String[]  level;
	public static int lev;
	
	private void startThreadFromServer() {
		new Thread() {
			@Override public synchronized void start() {
				sendScore();
			}
		}.start();
	}
	
	private String[] getParams() {
		String user = "" + PigeonSharedUser.get( this.getBaseContext() );
		EditText edt = (EditText) findViewById(R.id.msg);
		String message = edt.getText().toString();
		message = (message == null || message.isEmpty()) 
			? "mensagem n‹o especificada ... " + System.currentTimeMillis() : message;
		String score = level[lev];
		return new String[] { user, ""+score, message };	
	}
	
	private String concatRealAddress(String uri) {
		return ServerConstants.getContextFromAndroid() + "/" + uri;
	}
	
	private  void sendScore() {
		String[] params = getParams();
		String url = Source.score.apply( params );
		url = concatRealAddress(url);
		
		ProxyClient client = new ProxyClient();
		client.execute(url, new AsynCallback() {
			@Override public void onSucess(JSONObject json)  { 
				Log.d("pigeon", "sucess:" + json.toString());
			}
			@Override public void onFailure(JSONObject json) {
				Log.d("pigeon", "fail:" + json.toString());
				//new QueueServer().exchange();
			}
		});
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transition);
	
		Intent intent = getIntent();
		level = (String[]) intent.getSerializableExtra("level");
		lev = Integer.parseInt(level[1]);
		
		cont=0;
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
		
		startThreadFromServer();
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