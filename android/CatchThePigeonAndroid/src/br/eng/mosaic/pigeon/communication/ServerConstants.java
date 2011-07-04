package br.eng.mosaic.pigeon.communication;

public class ServerConstants {

	public static String getContextFromAndroid() {
		return getContext("10.0.0.4"); 
	}
	
	public static String getContextFromFacebook() {
		// informe aqui o ip/dns do server (sua maquina ou cloud)
		return getContext("10.0.0.4");
	}
	
	private static String getContext(String ip) {
		return "http://" + ip + ":8080/pigeon";
	}
	
	public static String getServerContext(){
		return "http://www.mosaic.eng.br/server/topfive.do";
	}
	
}