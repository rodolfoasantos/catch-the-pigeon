package br.eng.mosaic.pigeon.client.gameplay.util;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;

public class GameUtil {
	
	public static ArrayList<BadPigeon> genEnemies(int amount, int cameraWidth, int cameraHeight, TiledTextureRegion texture, TiledTextureRegion invertedTexture) {		
		
		ArrayList<BadPigeon> bads = new ArrayList<BadPigeon>();
		Random r = new Random();
				
		for (int i = 0; i < amount; i++) {			
			int posAnim;
			switch (r.nextInt(4)) {			
				case 0:
					posAnim= ((int)(Math.random() * cameraWidth));
					bads.add(new BadPigeon( posAnim, cameraHeight, (posAnim > Pigeon.posX ? invertedTexture : texture), 1));
					break;
				case 1:
					bads.add(new BadPigeon( cameraWidth, ((int)(Math.random() * cameraHeight)), invertedTexture, 1));
					break;
				case 2:
					posAnim = ((int)(Math.random() * cameraWidth));
					bads.add(new BadPigeon(((int)(Math.random() * cameraWidth)), 0, (posAnim > Pigeon.posX ? invertedTexture : texture), 1));
					break;
				case 3:
					BadPigeon b = new BadPigeon(0, ((int)(Math.random() * cameraHeight)), texture, 1);					
					bads.add(b);
					break;
				default:
					break;
			}			
		}			
		return bads;
	}

}
