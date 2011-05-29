package br.eng.mosaic.pigeon.client.gameplay;

import android.content.Intent;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;

public class Stage3 extends Stage {

	@Override
	protected void createCharacters() {
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int playerX = (CAMERA_WIDTH - Stage2.mPlayerTextureRegion.getTileWidth()) / 4;
		final int playerY = (CAMERA_HEIGHT - Stage2.mPlayerTextureRegion.getTileHeight()) / 2;

		this.pigeon = new Pigeon(playerX/2, playerY, Stage1.mPlayerTextureRegion, 3);

		badPigeons.add(new BadPigeon(0, playerY, Stage2.mEnemyTextureRegion1, 1));	
		badPigeons.add(new BadPigeon(0, playerY - 100, Stage2.mEnemyTextureRegion1, 1));
		badPigeons.add(new BadPigeon(playerX + 600, playerY + 100, Stage2.mInvertedEnemyTextureRegion, 9, 11, 1));	
		scene.getLastChild().attachChild(pigeon);

		scene.getLastChild().attachChild(pigeon);

		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}		
	}
	
	@Override
	protected void nextStage() {
		super.profile.setScore(1);
		Intent i = new Intent(getBaseContext(), Stage3.class);
		startActivity(i);				
	}

	@Override
	protected void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setBackgroundParameter() {
		setBackgroundBack("gfx/mosaic_pigeon_ima_stage03_layer01.png");
		setBackgroundFront("gfx/mosaic_pigeon_ima_stage03_layer02.png");
		setBackgroundFront2("gfx/mosaic_pigeon_ima_stage03_layer03.png");
		setBackgroundFront3("gfx/mosaic_pigeon_ima_stage03_layer04.png");	
	}

}
