package br.eng.mosaic.pigeon.client.infra;

import android.app.Activity;

public interface ConfigIF {
	
	public void setContext(Activity context);
	
	public void login();
	
	public void login(String userOrEmail);

	public void login(String user, String email);
	
	public int getScore();
	
	public void setScore(int count);


}
