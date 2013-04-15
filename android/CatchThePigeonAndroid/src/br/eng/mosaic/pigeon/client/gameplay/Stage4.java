package br.eng.mosaic.pigeon.client.gameplay;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import android.app.Dialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;

public class Stage4 extends Stage implements View.OnClickListener {

	public String select;
	public Dialog dialog;
	//private Boolean sair;
	
	@Override
	protected void createCharacters() {
		
		Intent intent = getIntent();
		select = (String) intent.getSerializableExtra("select");
		
		badPigeons.add(new BadPigeon(Stage.playerX + 600, Stage.playerY - 100, Stage.mInvertedEnemyTextureRegion, 1));
		badPigeons.add(new BadPigeon(Stage.playerX + 500, Stage.playerY + 450, Stage.mInvertedEnemyTextureRegion, 1));

		this.setLevel("4");
		
		pigeon.setPosition(playerX/2, playerY);
		scene.getLastChild().attachChild(pigeon);
		
		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}
	}

	@Override
	protected void nextStage() {
		
		super.profile.setScore(1);
		
		String[] person_level = {select,"1",Integer.toString(profile.getScore())};
		Intent i = new Intent(this,Transition.class);
		i.putExtra("level", person_level);
		startActivity(i);		
	}

	/*@Override
	protected void gameOver() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showDialog(GAME_OVER);
			}
		});
		
		profile.setScore(-profile.getScore());
		scoreText.setText("Score: " + profile.getScore());
	}*/

	@Override
	protected void setBackgroundParameter() {
		setBackgroundBack("gfx/mosaic_pigeon_ima_stage01_layer01.png");
		setBackgroundFront("gfx/mosaic_pigeon_ima_stage01_layer02.png");
		setBackgroundFront2("gfx/mosaic_pigeon_ima_stage01_layer03.png");
		setBackgroundFront3("gfx/mosaic_pigeon_ima_stage01_layer04.png");		
	}
	
	public void createBackgroundTest(String back, String mid, String front, String front2, String front3){
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front, 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,back, 0, 188);
		this.mParallaxLayerFront2 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front2, 0, 710);
		this.mParallaxLayerFront3 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front3, 0, 900);
		this.mEngine.getTextureManager().loadTextures(this.mTexture,this.mAutoParallaxBackgroundTexture);
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
	    	dialog = new Dialog(Stage4.this, android.R.style.Theme_Translucent);
	    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	dialog.setCancelable(true);
	    	dialog.setContentView(R.layout.dialog);
	    	
	    	Button btnYes = (Button) dialog.findViewById(R.id.btnsearch);
	    	Button btnNo = (Button) dialog.findViewById(R.id.btncancel);
	    	
	    	btnYes.setOnClickListener(this);
	    	btnNo.setOnClickListener(this);
	    	dialog.show();
	    	
//	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	        builder.setMessage("Você realmente deseja sair ?")
//	               .setCancelable(false)
//	               .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//	                   public void onClick(DialogInterface dialog, int id) {                        
//	                      // sair = true ;
//	                       startActivity(new Intent(getBaseContext(), SelectPerson.class));
//	                   }
//	               })
//	               .setNegativeButton("Não", new DialogInterface.OnClickListener() {
//	                   public void onClick(DialogInterface dialog, int id) {
//	                        dialog.cancel();
//	                   }
//	               });
//	        AlertDialog alert = builder.create();
//	        alert.setTitle("Estágio 1");
//	        alert.setIcon(R.drawable.mosaic_pigeon_ima_icone);
//    		alert.show();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnsearch:
			startActivity(new Intent(getBaseContext(), SelectPerson.class));
			break;
		case R.id.btncancel:
			dialog.dismiss();
			break;
		}
	}

}