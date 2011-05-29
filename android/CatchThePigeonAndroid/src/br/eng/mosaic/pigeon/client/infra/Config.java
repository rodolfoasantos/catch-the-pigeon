package br.eng.mosaic.pigeon.client.infra;

import android.app.Activity;
import android.content.SharedPreferences;

public class Config implements ConfigIF{
	
	/**
	 * Catch the Pigeon Game User Preferences implemented with SharedPreferences
	 * 
	 */

	private static final String PREFS_NAME = "CatchThePigeonProfile";
	
	private static ConfigIF singleton = null;
	
	private Activity context = null;
	
	private SharedPreferences settings;

	private Config() {
	}

	public static ConfigIF getInstance() {
		if (singleton == null) {
			singleton = new Config();
		}
		return singleton;
	}
	
	public void setContext(Activity context) {
		this.context = context;
		this.settings = context.getSharedPreferences(PREFS_NAME, 0);
	}

	public void login() {
		
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("user", "srlm");
		editor.putInt("score", 0);

		// Commit the edits!
		editor.commit();

	}

	public void login(String user, String email, String social) {

	}

	@Override
	public void login(String userOrEmail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String user, String email) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getScore() {
		return settings.getInt("score", 0);
		
		
	}

	@Override
	public void setScore(int count) {
		SharedPreferences.Editor editor = settings.edit();
		int score = settings.getInt("score", 0) + count;
		editor.putInt("score", score);
		// Commit the edits!
		editor.commit();
		
	}

}
