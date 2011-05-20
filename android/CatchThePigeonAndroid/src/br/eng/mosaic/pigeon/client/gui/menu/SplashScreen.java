package br.eng.mosaic.pigeon.client.gui.menu;

import br.eng.mosaic.pigeon.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity implements Runnable{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Handler h = new Handler();
		h.postDelayed(this, 2000);
	}
	
	@Override
	public void run() {
		startActivity(new Intent(this,MainActivity.class));
		finish();
		
	}
		

}
