package br.eng.mosaic.pigeon.client.gameplay.util;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;

public class GameUtil {
	
	
	public static ArrayList<BadPigeon> genEnemies(int amount, int posX, int posY, TiledTextureRegion texture){
		
		ArrayList<BadPigeon> bads = new ArrayList<BadPigeon>();
		Random r = new Random();
				
		for (int i = 0; i < amount; i++) {
			
			switch (r.nextInt(4)) {
			
			case 0:
				bads.add(new BadPigeon( ((int)(Math.random() * posX)), posY, texture, 9, 11, 1));
				break;
				
			case 1:
				bads.add(new BadPigeon( posX, ((int)(Math.random() * posY)), texture, 9, 11, 1));
				break;
				
			case 2:
				bads.add(new BadPigeon( 0, ((int)(Math.random() * posY)), texture, 9, 11, 1));
				break;
				
			case 3:
				BadPigeon b = new BadPigeon(0, ((int)(Math.random() * posY)), texture, 9, 11, 1);
				b.velocity*=1.2;
				bads.add(b);
				break;
				
				
			default:
				break;
			}			
		}
			
		return bads;
	}

}
