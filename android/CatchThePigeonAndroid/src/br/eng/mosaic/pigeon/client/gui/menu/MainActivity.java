package br.eng.mosaic.pigeon.client.gui.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.SelectPerson;
import br.eng.mosaic.pigeon.client.gameplay.Stage;
import br.eng.mosaic.pigeon.client.infra.facebook.LoginFacebook;

public class MainActivity extends Activity {
	
	public void updateSocialLogged() {
		ImageButton btn = (ImageButton) findViewById(R.id.facebook);
		Drawable image = getDrawable( R.drawable.mosaic_pigeon_img_layer_facebook);
		btn.setBackgroundDrawable(image);
	}
	
	protected Drawable getDrawable(int id) {
    	return this.getResources().getDrawable( id );
    }
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.start_game).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        startGame();
                    }
                });

       /* findViewById(R.id.top_five).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showTopFive();
            }
        });
        */
        /*findViewById(R.id.high_score).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showHighScore();
            }
        });
        */
		findViewById(R.id.facebook).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loginFacebook();
			}
		});
	}
	
	private void startGame() {
		// call the dialog to set a message
		try {

			this.showDialog(Stage.DIALOG_CHOOSE_MESSAGE);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}		
		//Intent i = new Intent(this, Stage1.class);
		//Intent i = new Intent(this, Stage2.class);
		//Intent i = new Intent(this, Stage3.class);
		Intent i = new Intent(this, SelectPerson.class);
		startActivity(i);
	}

	private void showTopFive() {

		Intent i = new Intent(this, TopFiveActivity.class);
		startActivity(i);
	}

	private void showHighScore() {

		Intent i = new Intent(this, HighScoreActivity.class);
		startActivity(i);
	}

	private void loginFacebook() {
		Intent i = new Intent(this, LoginFacebook.class);
		startActivity(i);
	}

}