package br.eng.mosaic.pigeon.client.gameplay.cast.anim;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import br.eng.mosaic.pigeon.client.gameplay.cast.Ave;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;

public class FeatherEvent extends Ave{

	/** Velocidade da Ave */	
	private float velocity = 0.0f;
	private Scene scene;
	private int smokeFrame = 0;
	
	public FeatherEvent(final float pX, final float pY, final TiledTextureRegion pTextureRegion, Scene scene, Pigeon pigeon) {
		super(pX, pY, pTextureRegion, 50);
		int kind = pigeon.getKindOfPigeon();
		int initialFrame, finalFrame;
		switch(kind) {
			case Pigeon.SIGEON:
				initialFrame = 6;
				finalFrame = 8;
				break;
			case Pigeon.FIGEAN:
				initialFrame = 3;
				finalFrame = 5;
				break;
			case Pigeon.FIGEON:
			default:
				initialFrame = 0;
				finalFrame = 2;
		}
		this.animate(new long[]{200, 200, 200}, initialFrame, finalFrame, 1);
		this.scene = scene;
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) { 
		this.mPhysicsHandler.setVelocityX(this.velocity);		
		if(++smokeFrame > 10) {
			this.setVisible(false);
			this.scene.detachChild(this);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public float getVelocity() {	
		return velocity;
	}
}
