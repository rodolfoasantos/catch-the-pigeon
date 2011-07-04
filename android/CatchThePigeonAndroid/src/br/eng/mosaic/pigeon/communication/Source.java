package br.eng.mosaic.pigeon.communication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public enum Source {

	score("param1/oauth/facebook/publish.do?score=param2&message=param3"),
		topfive("topfive"),
			about("param1/me");
	
	public String url;
	private Source(String url) {
		this.url = url;
	}
	
	public String apply(String ... params) {
		if ( params == null || params.length == 0 ) return this.url;
		
		String newUrl = this.url;
		String jsessionid = null;
		try {
			int i = 1;
			for (String param : params) {
				if ( param.contains("jsessionid") ) {
					jsessionid = param.split(";")[1];
					param = param.split(";")[0];
				}
				
				param = URLEncoder.encode(param, "UTF-8");
				newUrl = newUrl.replace("param" + i++, param);
			}
			newUrl = newUrl + ";" + jsessionid;
			return newUrl;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}