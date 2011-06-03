package br.eng.mosaic.pigeon.client.gameplay.cast;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Pigeon extends Ave {
	
	/** Velocidade da Ave */
	public static float velocity = 30.0f;
	
	/** Posicao do pombo na tela no eixo x*/
	public static float posX = 0;
	/** Posicao do pombo na tela no eixo y*/
	public static float posY = 0;
	
	//---------Species of Pigeons----------
	public static final int FIGEON = 0;
	public static final int SIGEON = 1;
	public static final int FIGEAN = 2;
	//--------------------------------------
	
	/** Selected kind of pigeon */
	private int kindOfPigeon = FIGEON;
	
	/**
	 * 
	 * @param pX Position of the pigeon on the Axis X
	 * @param pY Position of the pigeon on the Axis Y
	 * @param pTextureRegion Texture of the pigeon
	 * @param life Amount of life of the pigeon
	 * @param kindOfPigeon Kind of the pigeon (FIGEON, SIGEON or FIGEAN)
	 */
	public Pigeon(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int life, int kindOfPigeon) {
		super(pX, pY, pTextureRegion, life);
		int startAnim;
		int endAnim; 
		
		switch(kindOfPigeon) {
			case FIGEAN:
				startAnim = 0;
				endAnim = 7;
				break;
			case SIGEON:
				startAnim = 8;
				endAnim = 15;
				break;
			case FIGEON:
			default:
				startAnim = 16;
				endAnim = 23;
				
		}
		
		this.setPosition(pX, pY);
		this.animate(new long[]{millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame, millisecondsByFrame}, startAnim, endAnim, true);
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