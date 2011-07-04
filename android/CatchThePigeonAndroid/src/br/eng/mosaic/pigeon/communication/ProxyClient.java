package br.eng.mosaic.pigeon.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class ProxyClient {
	
	public enum Method {
		GET, POST;
	}
	
	public void execute(String url, AsynCallback callback) {
		execute(url, Method.GET, callback);
	}
	
	public void execute(String url, Method m, AsynCallback callback) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpRequestBase request = getMethod(m, url);
			HttpResponse response = httpClient.execute(request);
			
			String content = getContent( response.getEntity() );
			JSONObject json = new JSONObject( content );
			callback.onSucess(json);
		} catch (Throwable t) {
			callback.onFailure( getFailure(t) );
		}
	}
	
	private JSONObject getFailure(Throwable t) {
		try {
			String msg = getFailureMessage(t);
			String jsonContent = "{ 'fail' : '" + msg + "' }";
			JSONObject json = new JSONObject(jsonContent);
			return json;
		} catch (JSONException e) { 
			return null;
		}
	}
	
	private String getFailureMessage(Throwable t) {
		return t.getClass() + " : \n"  + t.getMessage();
	}
	
	private String getContent(HttpEntity entity) throws IOException {
		InputStream is = entity.getContent();
		return readFully(is, (int) entity.getContentLength());
	}
	
	private String readFully(InputStream is, int length) throws IOException {
		if (is != null) {
		    Writer writer = new StringWriter();
		    char[] buffer = new char[4096];
		    try {
		    	InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		        Reader reader = new BufferedReader(isr);
		        int n;
		        while ((n = reader.read(buffer)) != -1) {
		        	writer.write(buffer, 0, n);
		        }
		    } finally {
		        is.close();
		    }
		    return writer.toString();
		} else {        
		    return "";
		}
	}

	private HttpRequestBase getMethod(Method m, String url) throws UnsupportedEncodingException {
		switch (m) {
			case GET: return new HttpGet(url);
			case POST: return getHttpPost(url);
		}
		return null;
	}
	
	private HttpPost getHttpPost(String url) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		BasicNameValuePair basic = new BasicNameValuePair("chave", "valor");
		params.add( basic );
		
		UrlEncodedFormEntity encod = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		post.setEntity( encod );
		
		return post;
	}

}

