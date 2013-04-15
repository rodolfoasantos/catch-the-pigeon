package br.eng.mosaic.pigeon.communication;

public class ConnectionVerification {
	private static Boolean flagConnection;

	public Boolean getFlagConnection() {
		return flagConnection;
	}

	public void setFlagConnection(Boolean flagConnection) {
		ConnectionVerification.flagConnection = flagConnection;
	}
}
