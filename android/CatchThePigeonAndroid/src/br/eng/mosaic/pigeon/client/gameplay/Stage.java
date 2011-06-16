package br.eng.mosaic.pigeon.client.gameplay;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.Ave;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.anim.BirdExplosion;
import br.eng.mosaic.pigeon.client.gameplay.cast.anim.FeatherEvent;
import br.eng.mosaic.pigeon.client.gameplay.util.AudioFactory;
import br.eng.mosaic.pigeon.client.gameplay.util.GameUtil;
import br.eng.mosaic.pigeon.client.gui.menu.MainActivity;
import br.eng.mosaic.pigeon.client.infra.Config;
import br.eng.mosaic.pigeon.client.infra.ConfigIF;

public abstract class Stage extends BaseGameActivity implements IOnMenuItemClickListener {

	public ConfigIF profile = Config.getInstance();
	
	private ChangeableText scoreText;
	private ChangeableText levelText;
	
	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	
	protected MenuScene mMenuScene;

	public String backgroundBack;
	public String backgroundFront;
	public String backgroundFront2;
	public String backgroundFront3;
	public String backgroundMid;
	
	private Camera mCamera;
	
	private HUD hud;
	private Vector<Sprite> hearts;

	private boolean nextStage = false;

	public Texture mTexture;
	
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TiledTextureRegion mEnemyTextureRegion;
	public static TiledTextureRegion mInvertedEnemyTextureRegion;
	public static TiledTextureRegion mCharacters;
	public static TiledTextureRegion mExplosionPlayerTexture;	
	public static TiledTextureRegion mFetherTexture;	
	
	protected Texture mAutoParallaxBackgroundTexture;

	public TextureRegion mParallaxLayerBack;
	public TextureRegion mParallaxLayerFront;
	public TextureRegion mParallaxLayerFront2;
	public TextureRegion mParallaxLayerFront3;
	public TextureRegion mCharacterLife;
	public TextureRegion mHeart;

	private Texture mFontTexture;
	private Font mFont;

	public static Sound mExplosionSound;
	public static Music mMainMusic;
	public static Music mPigeonDieSound;

	protected Vector<BadPigeon> badPigeons = new Vector<BadPigeon>();
	protected Scene scene;
	protected static Pigeon pigeon;

	private CameraScene mPauseScene;

	public static final int DIALOG_CHOOSE_MESSAGE = 0;
	public static final int GAME_OVER = 1;

	public static String message;

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		this.scene = new Scene(1);
					
		this.mTexture = new Texture(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		
		Stage.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/mosaic_pigeon_img_layer_pigeons.png", 0, 0, 3, 4);
		Stage.mEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/mosaic_pigeon_img_layer_pigeons.png", 96, 0, 8, 4);
		/*96 means that the texture will be positioned beside of first that have 96px of width*/
		Stage.mCharacters = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/mosaic_pigeon_img_layer_pigeons.png", 96, 0, 8, 4);
		Stage.mInvertedEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/mosaic_pigeon_img_layer_pigeons.png", 96, 0, 8, 4);
		Stage.mExplosionPlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/explosion.png", 352, 0, 4, 1);
		Stage.mFetherTexture = TextureRegionFactory.createTiledFromAsset(mTexture, this, "gfx/mosaic_pigeon_ima_spite_feather.png", 0, 0, 3, 3);
				
		// -------- Text -------
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 36, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		// ---------------------

		setBackgroundParameter();

		createCharacters();
		mCharacterLife = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/" + (pigeon.getKindOfPigeon() == Pigeon.FIGEON ? "mosaic_pigeon_ima_figeon.png" : (pigeon.getKindOfPigeon() == Pigeon.SIGEON ? "mosaic_pigeon_ima_sigeon.png" : "mosaic_pigeon_ima_figean.png")), 352, 32);
		mHeart = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/mosaic_pigeon_ima_life.png", 352, 84);
		
		createBackgroundTest(backgroundBack, backgroundMid, backgroundFront,backgroundFront2, backgroundFront3);
				
		mExplosionSound = AudioFactory.createSound(mEngine, this, "mfx/pigeon_snd_punch.ogg");
		mMainMusic = AudioFactory.createMusic(mEngine, this, "mfx/sound_execution.ogg");
		mPigeonDieSound = AudioFactory.createMusic(mEngine, this, "mfx/mosaic_pigeon_snd_sigeon.ogg");	
	}

	@Override
	public Scene onLoadScene() {
		
		this.mMenuScene = this.createMenuScene();		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		// --------------- Criando a Cena e inserindo o background
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-15.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-20.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront2.getHeight(), this.mParallaxLayerFront2)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-25.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront3.getHeight(), this.mParallaxLayerFront3)));
		scene.setBackground(autoParallaxBackground);
		// ---------------------------------------------------------------------

		message = "";

		// --------------- Criando texto de score ---------------
		this.scoreText = new ChangeableText(490, 10, this.mFont, "Score: " + profile.getScore(), "Highcore: XXXXX".length());
		scene.getLastChild().attachChild(scoreText);
		
		// -------------- Criando Retangulo para colis√£o --------------------
		final int rectangleX = (CAMERA_WIDTH) + 1;
		final int rectangleY = (CAMERA_HEIGHT);
		final Rectangle colisionLine = new Rectangle(rectangleX, 0, rectangleX + 1, rectangleY);
		scene.getLastChild().attachChild(colisionLine);
		// -------------------------------------------------------------------

		scene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final ITouchArea pTouchArea, final float pTouchAreaLocalX,
					final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					final RunnableHandler runnableHandler = new RunnableHandler();
					Stage.this.mEngine.getScene().registerUpdateHandler(
							runnableHandler);
					runnableHandler.postRunnable(new Runnable() {
						@Override
						public void run() {
							Ave face = (Ave) pTouchArea;
							birdDied(face);							
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
			public void reset() {}

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if (colisionLine.collidesWith(pigeon)) {
					if (!nextStage) {
						nextStage = true;
						nextStage();
					}
				}

				if (colissionWithPigeon()) {
					if (pigeon.isAlive()) {
						if (pigeon.sufferDamage()) {
							// the bird died
							pigeon.setAlive(false);
							pigeon.setPosition(1000, -1000);
							Pigeon.posX = 1000;
							birdDied(pigeon);
							Stage.mPigeonDieSound.play();
							Stage.mPigeonDieSound.setLooping(false);
							
							gameOver();
						}
						FeatherEvent feather = new FeatherEvent(pigeon.getX(), pigeon.getY(), mFetherTexture, scene, pigeon);
						scene.getLastChild().attachChild(feather);
						//lifeText.setText("♥: " + pigeon.getLife());
					}
				}

				if(badPigeons.size() < 1){
					for (BadPigeon bad : GameUtil.genEnemies(2, CAMERA_WIDTH, CAMERA_HEIGHT, Stage.mEnemyTextureRegion, Stage.mInvertedEnemyTextureRegion)) {
						badPigeons.add(bad);
						scene.getLastChild().attachChild(bad);
						scene.registerTouchArea(bad);
					}
				}
			}
		});

		//--------- Add HUD in the Screen --------------
		int posXLife = 0;
		int posYLife = 5;
		
		hud = new HUD();
		
		Sprite health = new Sprite(posXLife, posYLife, mCharacterLife.getWidth(), mCharacterLife.getHeight(), mCharacterLife);		
		hud.getLastChild().attachChild(health);
				
		posXLife += 50;
		posYLife += (mCharacterLife.getHeight()>>1) - (mHeart.getHeight()>>1);
		
		hearts = new Vector<Sprite>(pigeon.getLife());
		
		for(int i = 0; i < pigeon.getLife(); i++) {
			hearts.add(new Sprite(posXLife, posYLife, mHeart.getWidth(), mHeart.getHeight(), mHeart));		
			hud.getLastChild().attachChild(hearts.elementAt(i));		
			posXLife += mHeart.getWidth() - 3;
		}
				
		mCamera.setHUD(hud);
		//----------------------------------------------
		
		return scene;
	}
	
	/** 
	 * @param level
	 * @Description Teste
	 */
	public void setLevel(String varlevel) {
		this.levelText = new ChangeableText(490, 40, this.mFont, "Level: " + profile.getScore(), "Highcore: XXXXX".length());
		scene.getLastChild().attachChild(levelText);
		Log.i("jamilson", "Valor do Level"+Integer.parseInt(varlevel));
		levelText.setText("Level: "+varlevel);
	}
	
	/**
	 * Called when a bird die
	 * @param bird Bird that went to hell
	 */
	private void birdDied(Ave bird) {	
		this.profile.setScore(1);
		scoreText.setText("Score: " + profile.getScore());
		BirdExplosion explosion = new BirdExplosion(bird.getX(), bird.getY(), mExplosionPlayerTexture, scene);
		scene.getLastChild().attachChild(explosion);
		scene.unregisterTouchArea(bird);
		scene.getLastChild().detachChild(bird);
		badPigeons.remove(bird);
		bird.setAlive(false);
		Stage.mExplosionSound.play();
	}

	/**
	 * Tests the collision between the badpigeon and the piegon
	 * @return <code> true </code> if there was collision
	 */
	protected boolean colissionWithPigeon() {
		for (BadPigeon bp : badPigeons) {
			if ((bp.isAlive()) && (bp.collidesWith(Stage.pigeon))) {
				
				//Destroying hearts :)
				hud.getLastChild().detachChild(hearts.elementAt(pigeon.getLife() - 1));
				
				if (bp.sufferDamage()) {
					// the bird died
					birdDied(bp);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void onLoadComplete() {		
		Stage.mMainMusic.play();		
	}
	public void onPause() {		
		super.onPause();
		Stage.mMainMusic.stop();
		setResult(RESULT_CANCELED);
		finish(); //Close the screen
	}
	
	public void onStop() {
		super.onStop();
		Stage.mMainMusic.stop();
	} 
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(this.scene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			} else {
				/* Attach the menu. */
				this.scene.setChildScene(this.mMenuScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_RESET:
				/* Restart the animation. */
				this.scene.reset();
				//this.scene.back();
				/* Remove the menu and reset it. */
				this.scene.clearChildScene();
				this.mMenuScene.reset();
				return true;
			case MENU_QUIT:
				/* End Activity. */
				this.finish();
				return true;
			default:
				return false;
		}
	}
	
	
	public void setBackgroundBack(String backgroundBack) {
		this.backgroundBack = backgroundBack;
	}

	public void setBackgroundFront(String backgroundFront) {
		this.backgroundFront = backgroundFront;
	}
	
	public void setBackgroundFront2(String backgroundFront2) {
		this.backgroundFront2 = backgroundFront2;
	}
	public void setBackgroundFront3(String backgroundFront3) {
		this.backgroundFront3 = backgroundFront3;
	}
	
	public void createBackground(String back, String mid, String front, String front2, String front3){
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);		
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front, 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,back, 0, 188);
		this.mParallaxLayerFront2 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front2, 0, 690);
		this.mParallaxLayerFront3 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front3, 0, 750);
		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mAutoParallaxBackgroundTexture);
	}
	
	protected Dialog onCreateDialog(final int pID) {
		switch (pID) {
		case DIALOG_CHOOSE_MESSAGE:
			final EditText ipEditText = new EditText(this);
			ipEditText.setText(message);
			return new AlertDialog.Builder(this)
				.setIcon(R.drawable.facebook_icon)
				.setTitle("Your Message").setCancelable(false)
				.setView(ipEditText)
				.setPositiveButton("Send", new OnClickListener() {
					@Override
					public void onClick(final DialogInterface pDialog,
							final int pWhich) {
						message = ipEditText.getText().toString();
					}
				}).setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(final DialogInterface pDialog, final int pWhich) {
						Stage.this.onResume();
					}
				}).create();
		case GAME_OVER:
			return new AlertDialog.Builder(this)
			.setIcon(R.drawable.facebook_icon)
			.setTitle("Game Over").setCancelable(false)
			.setMessage("You Died!")
			.setPositiveButton("Try Again", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(getBaseContext(), Stage1.class);
					i.putExtra("select", (String)getIntent().getSerializableExtra("select"));
					startActivity(i);
				}
			}).setNegativeButton("Menu", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(getBaseContext(), MainActivity.class);
					startActivity(i);
				}
				
			}).create();
		default:
			return super.onCreateDialog(pID);
		}
	}
	
	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(this.mCamera);

		final IMenuItem resetMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_RESET, this.mFont, "RESET"), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(resetMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, this.mFont, "QUIT"), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		menuScene.addMenuItem(quitMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		menuScene.setOnMenuItemClickListener(this);
		
		return menuScene;
	}
		
	protected abstract void setBackgroundParameter();
	
	protected void gameOver() {
			Log.d("aa", "entrei");
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showDialog(GAME_OVER);
				}
			});
	}
	
	protected abstract void createCharacters();

	protected abstract void nextStage();
	
	protected abstract void createBackgroundTest(String back, String mid, String front, String front2, String front3);
	
}
