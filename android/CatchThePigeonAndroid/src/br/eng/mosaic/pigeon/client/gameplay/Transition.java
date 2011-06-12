package br.eng.mosaic.pigeon.client.gameplay;


import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Transition extends Activity{
	ImageButton next,  back, audio;
	int cont;
	
	public static String  level;
	public static int lev;
	//public static Intent i;
	/*public static Context c;*/
	//public static Integer cont;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.char_select);
		setContentView(R.layout.transition);
			
			
			
			Intent intent = getIntent();
			level = (String) intent.getSerializableExtra("level");
			lev = Integer.parseInt(level);
			
			
			cont=0;
			next  = (ImageButton) findViewById(R.id.next_level);
			back = (ImageButton) findViewById(R.id.back_button_transition);
			audio = (ImageButton) findViewById(R.id.audio_button_transition);
			
			//start  = (Button) findViewById(R.id.button_go);
			Stage.mMainMusic.stop();
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (lev==2)
					{
						Log.i("jamilson", "Dentro de Level 2 "+level);
						Intent i = new Intent(getBaseContext(), Stage2.class);
						startActivity(i);
					}
					if (lev==3)
					{
						Intent i = new Intent(getBaseContext(), Stage3.class);
						startActivity(i);
					}
					BadPigeon.velocity *= 1.5;
				}
			});
			
			back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), SelectPerson.class));
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
		// Fecha a tela
		finish();
	}
	

}