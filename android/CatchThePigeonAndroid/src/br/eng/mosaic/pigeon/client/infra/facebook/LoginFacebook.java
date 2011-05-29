package br.eng.mosaic.pigeon.client.infra.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import br.eng.mosaic.pigeon.client.R;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FbDialog;
import com.facebook.android.Util;

public class LoginFacebook extends Activity {
	
	// Your Facebook Application ID must be set before running this example
    // See http://www.facebook.com/developers/createapp.php
    public static final String APP_ID = "223093477704451";
    //"175729095772478" 

    private Facebook mFacebook;
    private AsyncFacebookRunner mAsyncRunner;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (APP_ID == null) {
            Util.showAlert(this, "Warning", "Facebook Applicaton ID must be " +
                    "specified before running this example: see Example.java");
        }

        try {
			showDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
        setContentView( R.layout.main );
    }
    
    
    public void showDialog() throws Exception {
    	String url = "http://m.facebook.com/dialog/oauth/?scope=email,user_about_me,publish_stream&client_id=150586265008036&redirect_uri=http://mosaic.eng.br:8080/server/oauth/facebook/callback.do";
//    	String url = "http://m.facebook.com"; // TODO refatorar para n‹o abrir novo browser : bug
    	new FbDialog(this, url, null).show();
    }
    
    private static BufferedReader getReader( InputStream is ) throws IOException {
		InputStreamReader reader = new InputStreamReader( is ); 
		return new BufferedReader( reader );
	}
	
	private static String extract(BufferedReader reader) throws IOException {
		StringBuilder content = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			content.append(line);
		reader.close();
		return content.toString();
	}

}

//LinearLayout layout = new LinearLayout(this);
//layout.setOrientation(LinearLayout.VERTICAL);
//
//HttpClient client = new DefaultHttpClient();
//HttpUriRequest request = new HttpGet( url );
//HttpResponse response = client.execute( request );
//BufferedReader reader = getReader( response.getEntity().getContent() );
//String content = extract(reader);