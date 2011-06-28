package br.eng.mosaic.pigeon.client.gameplay;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Intent;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;

public class Stage3 extends Stage {

	public String select;
	
	@Override
	protected void createCharacters() {
		
		Intent intent = getIntent();
		select = (String) intent.getSerializableExtra("select");
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int playerX = (CAMERA_WIDTH - Stage.mPlayerTextureRegion.getTileWidth()) / 4;
		final int playerY = (CAMERA_HEIGHT - Stage.mPlayerTextureRegion.getTileHeight()) / 2;
		
		this.pigeon = new Pigeon(playerX/2, playerY, Stage.mCharacters, 3, (select.equalsIgnoreCase("figeon") ? Pigeon.FIGEON : (select.equalsIgnoreCase("sigeon") ? Pigeon.SIGEON : Pigeon.FIGEAN)));
		
		badPigeons.add(new BadPigeon(playerX + 600, playerY - 100, Stage.mInvertedEnemyTextureRegion, 1));
		badPigeons.add(new BadPigeon(playerX + 500, playerY + 450, Stage.mInvertedEnemyTextureRegion, 1));

		this.setLevel("3");
		
		scene.getLastChild().attachChild(pigeon);
		
		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}
	}

	@Override
	protected void nextStage() {
		
		super.profile.setScore(1);
		
		String[] person_level = {select,"2"};
		Intent i = new Intent(this,Transition.class);
		i.putExtra("level", person_level);
		startActivity(i);		
	}

	@Override
	protected void gameOver() {}

	@Override
	protected void setBackgroundParameter() {
		setBackgroundBack("gfx/mosaic_pigeon_ima_stage03_layer01.png");
		setBackgroundFront("gfx/mosaic_pigeon_ima_stage03_layer02.png");
		setBackgroundFront2("gfx/mosaic_pigeon_ima_stage03_layer03.png");
		setBackgroundFront3("gfx/mosaic_pigeon_ima_stage03_layer04.png");			
	}
	
	public void createBackgroundTest(String back, String mid, String front, String front2, String front3){
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front, 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,back, 0, 188);
		this.mParallaxLayerFront2 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front2, 0, 710);
		this.mParallaxLayerFront3 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front3, 0, 900);
		this.mEngine.getTextureManager().loadTextures(this.mTexture,this.mAutoParallaxBackgroundTexture);
	}
}