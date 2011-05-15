package br.ufpe.cin.mosaic.pigeon.game;

import java.util.Iterator;
import java.util.Vector;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import br.ufpe.cin.mosaic.pigeon.business.android.facebook.LoginFacebook;
import br.ufpe.cin.mosaic.pigeon.game.personagens.Ave;
import br.ufpe.cin.mosaic.pigeon.game.personagens.BadPigeon;
import br.ufpe.cin.mosaic.pigeon.game.personagens.Pigeon;
import br.ufpe.cin.mosaic.pigeon.game.personagens.animations.BirdExplosion;

public abstract class Stage extends BaseGameActivity {

	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;

	private Camera mCamera;
		
	private boolean nextStage = false;

	private Texture mTexture;
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TiledTextureRegion mEnemyTextureRegion1;
	public static TiledTextureRegion mExplosionPlayerTexture;
	public static TiledTextureRegion mInvertedEnemyTextureRegion;

	private Texture mAutoParallaxBackgroundTexture;

	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;
	private TextureRegion mParallaxLayerFront;
	
	//public static Sound mExplosionSound;

	protected Vector<BadPigeon> badPigeons = new Vector();
	protected Scene scene;
	protected Pigeon pigeon;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsSound(true));		
	}

	@Override
	public void onLoadResources() {
		this.scene = new Scene(1);
		this.mTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bird.png", 0, 0, 3, 4);
		this.mEnemyTextureRegion1 = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bird.png", 0, 0, 3, 4);
		this.mInvertedEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bird.png", 0, 0, 3, 4);
		this.mExplosionPlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bird.png", 0, 0, 3, 4);

		//----- Background ------
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_front.png", 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_back.png", 0, 188);
		this.mParallaxLayerMid = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_mid.png", 0, 669);

		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mAutoParallaxBackgroundTexture);
		//-----------------------
		
		createCharacters();
				
		/*try {
			Stage.mExplosionSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "mfx/explosion.ogg");
		} catch (final Exception e) {
			Log.d("Erro: ", e.toString());
		}*/
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		//--------------- Criando a Cena e inserindo o background ---------------		
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
		scene.setBackground(autoParallaxBackground);
		//---------------------------------------------------------------------

		// -------------- Criando Retangulo para colisão --------------------
		final int rectangleX = (CAMERA_WIDTH) + 1;
		final int rectangleY = (CAMERA_HEIGHT);		
		final Rectangle colisionLine = new Rectangle(rectangleX, 0, rectangleX + 1, rectangleY);
		//colisionRectangle.registerEntityModifier(new LoopEntityModifier(new ParallelEntityModifier(new RotationModifier(6, 0, 360), new SequenceEntityModifier(new ScaleModifier(3, 1, 1.5f), new ScaleModifier(3, 1.5f, 1)))));		
		scene.getLastChild().attachChild(colisionLine);
		//-------------------------------------------------------------------
		
		scene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					final RunnableHandler runnableHandler = new RunnableHandler();
			        Stage.this.mEngine.getScene().registerUpdateHandler(runnableHandler);			        
			        runnableHandler.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                        	scene.unregisterTouchArea((ITouchArea)pTouchArea);
        					scene.getLastChild().detachChild((Ave)pTouchArea);
                        }
                    });
                    return true;
            }

            return false;

			}
		});
		scene.setTouchAreaBindingEnabled(true);
		
		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {				
				if(colisionLine.collidesWith(pigeon)) {
					/*Chama a tela de login do facebook quando o pombo alcanca o final da tela*/
					//Intent i = new Intent(getBaseContext(),LoginFacebook.class);					
					//startActivity(i);
					if(!nextStage) {
						nextStage();
						nextStage = true; //Feito para não criar mais de uma instância de Stage já que onUpdate é chaamdo várias vezes
					}
				}
				
				if(colissionWithPigeon()){
					//Stage.mExplosionSound.play();
					if (pigeon.isAlive()) {	
						if(pigeon.sufferDamage()) {
							//the bird died
							pigeon.setPosition(1000, -1000);
							Pigeon.posX = 1000;
							scene.getLastChild().detachChild(pigeon);
							final BirdExplosion explosion1 = new BirdExplosion(Pigeon.posX, Pigeon.posY, Stage.mExplosionPlayerTexture);
							scene.getLastChild().attachChild(explosion1);
							pigeon.setAlive(false); 
						}
					}
				}
			}
		});
	
		return scene;
	}


	protected boolean colissionWithPigeon() {
		for (BadPigeon bp: badPigeons) {
			if((bp.isAlive()) && (bp.collidesWith(this.pigeon))) {
				if(bp.sufferDamage()) {
					//the bird died
					bp.setAlive(false);
					scene.getLastChild().detachChild(bp);
					final BirdExplosion explosion1 = new BirdExplosion(bp.getX(), bp.getY(), Stage.mExplosionPlayerTexture);
					scene.getLastChild().attachChild(explosion1);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onLoadComplete() {}
	
	protected abstract void createCharacters();
	protected abstract void nextStage();
}