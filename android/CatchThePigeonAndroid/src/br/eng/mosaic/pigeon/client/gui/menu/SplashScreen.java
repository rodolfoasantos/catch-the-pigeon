package br.eng.mosaic.pigeon.client.gui.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.infra.Config;
import br.eng.mosaic.pigeon.client.infra.ConfigIF;

public class SplashScreen extends Activity implements Runnable {
	// create name of animation
	Animation myFadeInAnimation;
	Animation myFadeOutAnimation;
	ImageView myImageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ConfigIF profile = Config.getInstance();	
		profile.login();
		
		setContentView(R.layout.splash);

		// grab the imageview and load the animations

		myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		myFadeOutAnimation = AnimationUtils
				.loadAnimation(this, R.anim.fade_out);
		myImageView = (ImageView) findViewById(R.id.imageView1);

		// fade it in, and fade it out.
		Handler h = new Handler();
		myImageView.startAnimation(myFadeInAnimation);

		h.postDelayed(this, 4000);

	}

	@Override
	public void run() {

		myImageView.startAnimation(myFadeOutAnimation);
		startActivity(new Intent(this, MainActivity.class));
		finish();

	}
}
