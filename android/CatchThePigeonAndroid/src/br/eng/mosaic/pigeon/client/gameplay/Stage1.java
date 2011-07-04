package br.eng.mosaic.pigeon.client.gameplay;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;

public class Stage1 extends Stage {

	public String select;
	private Boolean sair;
		
	@Override
	protected void createCharacters() {
		Intent intent = getIntent();
		select = (String) intent.getSerializableExtra("select");
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		Stage.playerX = (CAMERA_WIDTH - Stage.mPlayerTextureRegion.getTileWidth()) / 4;
		Stage.playerY = (CAMERA_HEIGHT - Stage.mPlayerTextureRegion.getTileHeight()) / 2;

		pigeon = new Pigeon(Stage.playerX/2, Stage.playerY, Stage.mCharacters, 3, (select.equalsIgnoreCase("figeon") ? Pigeon.FIGEON : (select.equalsIgnoreCase("sigeon") ? Pigeon.SIGEON : Pigeon.FIGEAN)));

		badPigeons.add(new BadPigeon(Stage.playerX + 600, Stage.playerY - 100, Stage.mInvertedEnemyTextureRegion, 1));
		badPigeons.add(new BadPigeon(Stage.playerX + 500, Stage.playerY + 450, Stage.mInvertedEnemyTextureRegion, 1));

		this.setLevel("1");
		
		scene.getLastChild().attachChild(pigeon);

		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}
	}

	@Override
	protected void nextStage() {
		
		super.profile.setScore(1);
		
		String[] person_level = {select,"2",Integer.toString(profile.getScore())};
		Intent i = new Intent(this,Transition.class);
		i.putExtra("level", person_level);
		startActivity(i);
		
		//BadPigeon.velocity *= 1.3;
	}

	@Override
	protected void setBackgroundParameter() {
		setBackgroundBack("gfx/mosaic_pigeon_ima_stage01_layer01.png");
		setBackgroundFront("gfx/mosaic_pigeon_ima_stage01_layer02.png");
		setBackgroundFront2("gfx/mosaic_pigeon_ima_stage01_layer03.png");
		setBackgroundFront3("gfx/mosaic_pigeon_ima_stage01_layer04.png");
				
		//setBackgroundMid("gfx/parallax_background_layer_mid.png");		
	}
	
	public void createBackgroundTest(String back, String mid, String front, String front2, String front3){
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024,
				TextureOptions.DEFAULT);			
		
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(
				this.mAutoParallaxBackgroundTexture, this,front, 0, 0);
		
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(
				this.mAutoParallaxBackgroundTexture, this,back, 0, 188);
		
		this.mParallaxLayerFront2 = TextureRegionFactory.createFromAsset(
				this.mAutoParallaxBackgroundTexture, this,front2, 0, 690);
		
		this.mParallaxLayerFront3 = TextureRegionFactory.createFromAsset(
				this.mAutoParallaxBackgroundTexture, this,front3, 0, 750);
	//	this.mParallaxLayerMid = TextureRegionFactory.createFromAsset(
		//		this.mAutoParallaxBackgroundTexture, this,mid, 0, 669);

		this.mEngine.getTextureManager().loadTextures(this.mTexture,
				this.mAutoParallaxBackgroundTexture);
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
	                       startActivity(new Intent(getBaseContext(), SelectPerson.class));
	                       
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

}
