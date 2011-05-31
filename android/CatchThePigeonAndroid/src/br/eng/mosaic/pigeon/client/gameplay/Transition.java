package br.eng.mosaic.pigeon.client.gameplay;

import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Transition extends Activity{
	Button start;
	public static String  level;
	/*public static Intent i;
	public static Context c;*/
	//public static Integer cont;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.char_select);
		//setContentView(R.layout.transition);
			
			
			//String ss = (cont++).toString();
			
			//Log.i("jamilson", "SSS"+ss);
			Intent intent = getIntent();
			level = (String) intent.getSerializableExtra("level");
			Log.i("jamilson", level);
			start  = (Button) findViewById(R.id.start_game);
			//start  = (Button) findViewById(R.id.button_go);
			Stage.mMainMusic.stop();
			start.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("jamilson", "Dentro do onClick "+level);
					//Stage.profile.setScore(1);
					//c = getBaseContext();
					
					Intent i = new Intent(getBaseContext(), Stage2.class);
					startActivity(i);
					
					if (level=="2")
					{
						Log.i("jamilson", "Level 2 "+level);
						
						/*i = new Intent(c, Stage2.class);
						startActivity(i);*/
						
					}
					if (level=="3")
					{
						Log.i("jamilson", "Level 3 "+level);
						//Stage.profile.setScore(1);
					//	Intent i = new Intent(getBaseContext(), Stage3.class);
						//startActivity(i);
					}
					

					BadPigeon.velocity *= 1.5;
				}
			});
	}

}
