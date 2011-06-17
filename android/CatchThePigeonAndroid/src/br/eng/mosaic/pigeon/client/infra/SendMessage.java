package br.eng.mosaic.pigeon.client.infra;

import static br.eng.mosaic.pigeon.test.helper.MimeType.text_json;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.test.helper.MimeType;
import br.eng.mosaic.pigeon.test.helper.ServerFakeLocal;

public class SendMessage extends Activity {
	
	private ServerFakeLocal fakeServer;
	private String pigeon;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transition);
		
		Intent intent = getIntent();
		pigeon = (String) intent.getSerializableExtra("select");
		
		String json = "{'name', '"+pigeon+"'}";
		startServer(text_json, json);
		
		stopServer();
	}

	private synchronized void startServer(MimeType mime, String content) {
		prepareLocalServer(mime, content);
	}
	
	private void prepareLocalServer(MimeType mime, String content) {
		try {
			this.fakeServer = new ServerFakeLocal(mime, content);
		} catch (IOException e) {
			String message = "falhou ao levantar server : checa isso > \n" + e.getMessage(); 
			throw new RuntimeException( message );
		} 
	}
	
	private synchronized void stopServer() {
		this.fakeServer.stop();
	}
    	
	//talvez sirva para conectar ao servidor real
        /*URL url = null;
        try {
            String registrationUrl = String.format("http://localhost:8080/servidor?id=%s&name=%s", "1", URLEncoder.encode("abc","UTF-8"));
            url = new URL(registrationUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("MyApp", "Registration success");
            } else {
                Log.w("MyApp", "Registration failed for: " + registrationUrl);              
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
	//////////////////////////////////////////////////////
}
