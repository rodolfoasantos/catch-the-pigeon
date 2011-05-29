package br.eng.mosaic.pigeon.client.infra;

import android.app.Activity;
import android.content.SharedPreferences;

public class Config implements ConfigIF{
	
	/**
	 * Catch the Pigeon Game User Preferences implemented with SharedPreferences
	 * 
	 */

	private static final String PREFS_NAME = "CatchThePigeonProfile";
	
	private Activity context = null;

	private static ConfigIF singleton = null;

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
	}

	public void login() {
		
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("user", "srlm");
		editor.putFloat("score", 0.0f);

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

}
