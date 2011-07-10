package br.eng.mosaic.pigeon.client.gui.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.SelectPerson;
import br.eng.mosaic.pigeon.client.gameplay.SoundManager;
import br.eng.mosaic.pigeon.client.gameplay.Stage;
import br.eng.mosaic.pigeon.client.infra.SendMessage;
import br.eng.mosaic.pigeon.client.infra.facebook.LoginFacebook;
import br.eng.mosaic.pigeon.communication.CommunicationUtil;
import br.eng.mosaic.pigeon.communication.ServerConstants;
import br.eng.mosaic.pigeon.communication.StatusNetwork;

public class MainActivity extends Activity {
	
	private static SoundManager sm;
	private StatusNetwork statusNetwork;
	private Boolean sair;

	protected Drawable getDrawable(int id) {
		return this.getResources().getDrawable( id );
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    sm = SoundManager.getInstance(this);	   
	    sm.addSound(R.raw.mosaic_pigeon_snd_intro); 
	    sm.addSound(R.raw.mosaic_pigeon_snd_game);
	    sm.addSound(R.raw.mosaic_pigeon_snd_transition);
	    sm.addSound(R.raw.mosaic_pigeon_snd_punch_pigeon);
	    sm.addSound(R.raw.mosaic_pigeon_snd_figeon);
	    sm.addSound(R.raw.mosaic_pigeon_snd_figean);
	    sm.addSound(R.raw.mosaic_pigeon_snd_sigeon);

		statusNetwork = new StatusNetwork( this.getApplicationContext() );
		
		topFiveConnection();
		
		findViewById(R.id.start_game).setOnClickListener( new OnClickListener() {
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
		/*Intent i = new Intent(this, Help.class);
		startActivity(i);*/
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=7PGLdk6FHnY")));

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
		if ( statusNetwork.hasNetwork() ) {
			Intent i = new Intent(this, LoginFacebook.class);
			startActivity(i);
		} else
			Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
	}

	public void enviaMensagem() {
		startActivity(new Intent(this, SendMessage.class));
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
			builder.setMessage("Voc� realmente deseja sair ?")
			.setCancelable(false)
			.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {                        
					sair = true ;
					finish();
				}
			})
			.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
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


	public void topFiveConnection(){
		
		
		if ( statusNetwork.hasNetwork() ) {
			updateTopFive();
		} else
			Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
		
	}


	public void updateTopFive() {
		String result = CommunicationUtil.getFileContent(ServerConstants.getServerContext());
		if (result == null){
			Log.e("NGVL", "Falha ao acessar WS");
			return;
		}

		try {
			JSONObject json = new JSONObject(ClearJson(result));

			json = json.getJSONObject("sucess");
			JSONArray jsonArray = json.getJSONArray("topfive");

			JSONObject pessoaJson;

			for (int i = 0; i < jsonArray.length(); i++) {
				pessoaJson = new JSONObject(
						jsonArray.getString(i));

				Log.i("MOSAIC_LOG", "------------------------");       
				Log.i("MOSAIC_LOG", "score="+ pessoaJson.getInt("score"));       
				Log.i("MOSAIC_LOG", "url="+ pessoaJson.getString("url"));

				ImageView imgView = null;
				TextView t = null;


				//Pessoal, não se assustem. Sao 2:16, foi o melhor que pude fazer. Rodolfo and Marco.

				switch (i) {
				case 0 : t = (TextView) findViewById(R.id.main_score_tf1);
				t.setText(String.valueOf(pessoaJson.getInt("score")));
				imgView = new ImageView(getBaseContext());
				imgView = (ImageView)findViewById(R.id.main_img_tf1);
				createImg(pessoaJson.getString("url"), "main_img_tf1", imgView);
				break;
				case 1 : t = (TextView) findViewById(R.id.main_score_tf2);
				t.setText(String.valueOf(pessoaJson.getInt("score")));
				imgView = new ImageView(getBaseContext());
				imgView = (ImageView)findViewById(R.id.main_img_tf2);
				createImg(pessoaJson.getString("url"), "main_img_tf2", imgView);
				break;
				case 2 : t = (TextView) findViewById(R.id.main_score_tf3);
				t.setText(String.valueOf(pessoaJson.getInt("score")));
				imgView = new ImageView(getBaseContext());
				imgView = (ImageView)findViewById(R.id.main_img_tf3);
				createImg(pessoaJson.getString("url"), "main_img_tf3", imgView);
				break;

				case 3 : t = (TextView) findViewById(R.id.main_score_tf4);
				t.setText(String.valueOf(pessoaJson.getInt("score")));
				imgView = new ImageView(getBaseContext());
				imgView = (ImageView)findViewById(R.id.main_img_tf4);
				createImg(pessoaJson.getString("url"), "main_img_tf4", imgView);
				break;

				case 4 : t = (TextView) findViewById(R.id.main_score_tf5);
				t.setText(String.valueOf(pessoaJson.getInt("score")));
				imgView = new ImageView(getBaseContext());
				imgView = (ImageView)findViewById(R.id.main_img_tf5);
				createImg(pessoaJson.getString("url"), "main_img_tf5", imgView);
				break;

				default:
					break;
				}

			}   

		} catch (JSONException e) {     
			Log.e("NGVL", "Erro no parsing do JSON", e);
		}

	}


	private void createImg(String url, String imageName, ImageView imgView){
		//String url = "https://graph.facebook.com/100002069033840/picture";
		Drawable image = CommunicationUtil.ImageOperations(getBaseContext(),url ,"image.jpg");
		//ImageView imgView = new ImageView(getBaseContext());
		//imgView = (ImageView)findViewById(R.id.main_img_tf1);
		imgView.setImageDrawable(image);
	}



	private String ClearJson(String json){

		json = json.replace("<html>", "");
		json = json.replace("<body>", "");
		json = json.replace("<p>", "");
		json = json.replace("</html>", "");
		json = json.replace("</body>", "");
		json = json.replace("</p>", "");
		json = json.replace("&quot;", "\"");

		return json;
	}

}

