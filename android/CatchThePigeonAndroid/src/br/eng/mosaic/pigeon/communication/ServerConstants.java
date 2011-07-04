package br.eng.mosaic.pigeon.communication;

public class ServerConstants {

	public static String getContextFromAndroid() {
		return getContext("mosaic.eng.br"); 
	}
	
	public static String getContextFromFacebook() {
		// informe aqui o ip/dns do server (sua maquina ou cloud)
		return getContext("mosaic.eng.br");
	}
	
	private static String getContext(String ip) {
		return "http://" + ip + "/server";
	}
	
	public static String getServerContext(){
		return "http://www.mosaic.eng.br/server/topfive.do";
	}
	
}