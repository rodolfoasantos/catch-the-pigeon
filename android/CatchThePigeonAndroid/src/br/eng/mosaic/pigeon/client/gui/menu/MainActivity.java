package br.eng.mosaic.pigeon.client.gui.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.SelectPerson;
import br.eng.mosaic.pigeon.client.gameplay.Stage;
import br.eng.mosaic.pigeon.client.infra.SendMessage;
import br.eng.mosaic.pigeon.client.infra.facebook.LoginFacebook;
import br.eng.mosaic.pigeon.communication.ConnectionVerification;

public class MainActivity extends Activity {
	static final private int GET_CODE = 0;
	private Boolean sair;
	ConnectionVerification flagConnection;
	protected Drawable getDrawable(int id) {
    	return this.getResources().getDrawable( id );
    }
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		flagConnection = new ConnectionVerification();
		flagConnection.setFlagConnection(false);
		
		findViewById(R.id.start_game).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        startGame();
                    }
                });

       
		//showTopFive();
		
        
        /*findViewById(R.id.high_score).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showHighScore();
            }
        });
        */
		findViewById(R.id.facebook_button_main).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loginFacebook();
			}
		});
		
		findViewById(R.id.help_button).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callHelp();
			}
		});
		
		findViewById(R.id.twitter_button_main).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				enviaMensagem();
			}
		});
		
		
	}
	
	private void callHelp() {
		Intent i = new Intent(this, Help.class);
		startActivity(i);
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
		if (haveInternet(getBaseContext()))
    	{
			flagConnection.setFlagConnection(true);
			Intent i = new Intent(this, LoginFacebook.class);
			startActivity(i);
    	}else
    	{
    		flagConnection.setFlagConnection(false);
    		Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
    	}
	}
	
	public void enviaMensagem() {
		startActivity(new Intent(this, SendMessage.class));
	}
	
	public static boolean haveInternet(Context ctx) {
 	   NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
 	   if (info == null || !info.isConnected()) {
 	       return false;
 	   }
 	   if (info.isRoaming()) {
 	       return false;
 	   }
 	  
 	   return true;
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
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("Você realmente deseja sair ?")
	               .setCancelable(false)
	               .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {                        
	                       sair = true ;
	                       finish();
	                   }
	               })
	               .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
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
