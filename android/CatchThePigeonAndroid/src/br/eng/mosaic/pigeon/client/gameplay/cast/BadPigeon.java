package br.eng.mosaic.pigeon.client.gameplay.cast;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class BadPigeon extends Ave {
	
	/** Velocidade da Ave */	
	public static float velocity = 70.0f;

	
	private BadPigeon(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int startAnimation, int endAnimation, int life) {
		super(pX, pY, pTextureRegion, life);
		this.setPosition(pX, pY);
		//this.animate(new long[]{millisecondsByFrame, millisecondsByFrame, millisecondsByFrame}, startAnimation, endAnimation, true);
		this.animate(new long[]{millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame}, startAnimation, endAnimation, true);
	}
	
	public BadPigeon(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int life) {
		//Test if the bird come from left to right or from right to left then setup the correct image
		//this(pX, pY, pTextureRegion, (pX < Pigeon.posX ? 0 : 9), (pX < Pigeon.posX ? 2 : 11), 1);
		this(pX, pY, pTextureRegion, (pX < Pigeon.posX ? 24 : 28), (pX < Pigeon.posX ? 27 : 31), 1);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) { 
		this.mPhysicsHandler.setVelocityX(BadPigeon.velocity * (Pigeon.posX < this.getX() ? - 1: 1));
		//A perseguicao e feita calculando a diferenca de altura entre o pombo mau e o bom e em seguindo diminuindo a distancia entre eles com a porcentagem entre a distancia do pombo mau e o bom		
		this.mPhysicsHandler.setVelocityY((Pigeon.posY - this.getY()) * (this.getX()/Pigeon.posX));
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public float getVelocity() {	
		return velocity;
	}
}