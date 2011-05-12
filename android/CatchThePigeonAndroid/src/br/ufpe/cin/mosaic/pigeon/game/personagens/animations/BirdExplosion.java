package br.ufpe.cin.mosaic.pigeon.game.personagens.animations;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import br.ufpe.cin.mosaic.pigeon.game.personagens.Ave;

public class BirdExplosion extends Ave {
	
	/** Velocidade da Ave */	
	private float velocity = 0.0f;
	
	/** Posicao da explosão na tela no eixo x*/
	private float posX = 0;
	/** Posicao da explosão na tela no eixo y*/
	private float posY = 0;
	
	public BirdExplosion(final float pX, final float pY, final TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		this.posX = pX;
		this.posY = pY;
		this.animate(new long[]{200, 200, 200}, 6, 8, 1);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) { 
		this.mPhysicsHandler.setVelocityX(this.velocity);
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public float getVelocity() {	
		return velocity;
	}
}