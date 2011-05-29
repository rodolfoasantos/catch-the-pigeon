package br.eng.mosaic.pigeon.client.infra;

public interface ConfigIF {
	
	public void login();
	
	public void login(String userOrEmail);

	public void login(String user, String email);

}
