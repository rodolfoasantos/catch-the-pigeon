package br.eng.mosaic.pigeon.client.gameplay.cast.anim;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import br.eng.mosaic.pigeon.client.gameplay.cast.Ave;

public class BirdExplosion extends Ave {
	
	/** Velocidade da Ave */	
	private float velocity = 0.0f;
	
	private int smokeFrame = 0;
	
	public BirdExplosion(final float pX, final float pY, final TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion, 50);
		this.animate(new long[]{200, 200, 200}, 6, 8, 1);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) { 
		this.mPhysicsHandler.setVelocityX(this.velocity);		
		if(++smokeFrame > 10) {
			this.setVisible(false);
			this.setPosition(-50, -50);
			Log.d("exp", "tirar exp da tela");
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public float getVelocity() {	
		return velocity;
	}
}