package br.eng.mosaic.pigeon.communication;

public class ServerConstants {

	public static String getContextFromAndroid() {
		return getContext("10.0.2.2"); 
	}
	
	public static String getContextFromFacebook() {
		// informe aqui o ip/dns do server (sua maquina ou cloud)
		return getContext("10.0.0.4");
	}
	
	private static String getContext(String ip) {
		return "http://" + ip + "/pigeon";
	}
	
}