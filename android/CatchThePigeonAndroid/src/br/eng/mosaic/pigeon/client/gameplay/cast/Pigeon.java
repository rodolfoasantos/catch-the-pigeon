package br.eng.mosaic.pigeon.client.gameplay.cast;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Pigeon extends Ave {
	
	/** Velocidade da Ave */
	public static float velocity = 30.0f;
	
	/** Posicao do pombo na tela no eixo x*/
	public static float posX = 0;
	/** Posicao do pombo na tela no eixo y*/
	public static float posY = 0;
	
	
	private Pigeon(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int startAnim, int endAnim, int life) {
		super(pX, pY, pTextureRegion, life);
		this.setPosition(pX, pY);		
		this.animate(new long[]{200, 200, 200}, startAnim, endAnim, true);
	}
	
	public Pigeon(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int life) {
		this(pX, pY, pTextureRegion, 3, 5, life);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.mPhysicsHandler.setVelocityX(this.velocity);
		Pigeon.posX = this.getX();
		Pigeon.posY = this.getY();
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public float getVelocity() {	
		return velocity;
	}
	
}