package br.ufpe.cin.mosaic.pigeon.game;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Intent;
import br.ufpe.cin.mosaic.pigeon.game.personagens.BadPigeon;
import br.ufpe.cin.mosaic.pigeon.game.personagens.Pigeon;

public class Stage1 extends Stage {
	
	@Override
	protected void createCharacters() {
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int playerX = (CAMERA_WIDTH - Stage1.mPlayerTextureRegion.getTileWidth()) / 4;
		final int playerY = (CAMERA_HEIGHT - Stage1.mPlayerTextureRegion.getTileHeight()) / 2;
		
		this.pigeon = new Pigeon(playerX + 100, playerY, Stage1.mPlayerTextureRegion, 3);
		
		badPigeons.add(new BadPigeon(1, playerY, Stage1.mEnemyTextureRegion1, 1));
		badPigeons.add(new BadPigeon(100, playerY + 100, Stage1.mEnemyTextureRegion1, 1));
		badPigeons.add(new BadPigeon(-100, playerY - 100, Stage1.mEnemyTextureRegion1, 1));
		badPigeons.add(new BadPigeon(playerX + 600, playerY - 100, Stage1.mInvertedEnemyTextureRegion, 9, 11, 1));
		badPigeons.add(new BadPigeon(playerX + 500, playerY + 450, Stage1.mInvertedEnemyTextureRegion, 9, 11, 1));		
		
		scene.getLastChild().attachChild(pigeon);

		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}
	}

	@Override
	protected void nextStage() {
		Intent i = new Intent(getBaseContext(), Stage2.class);
		startActivity(i);
		
		BadPigeon.velocity *= 1.3; 
	}	
}
