package br.eng.mosaic.pigeon.client.gameplay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;

public class Stage1 extends Stage {

	@Override
	protected void createCharacters() {
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int playerX = (CAMERA_WIDTH - Stage1.mPlayerTextureRegion.getTileWidth()) / 4;
		final int playerY = (CAMERA_HEIGHT - Stage1.mPlayerTextureRegion.getTileHeight()) / 2;

		this.pigeon = new Pigeon(playerX/2, playerY, Stage1.mPlayerTextureRegion, 3);

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

	@Override
	protected void gameOver() {
		
		Log.d("aa", "entrei");
		
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("Game Over");
		alert.setMessage("You Died!");
		
		alert.setButton("Try Again", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(getBaseContext(), Stage1.class);
				startActivity(i);
			}
		});
		
		
		alert.setButton2("Menu", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(i);
			}
		});
		
	}	
}
