package br.eng.mosaic.pigeon.client.infra.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
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

        View view = null;
		try {
			view = getBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
        setContentView( view );
    }
    
    public View getBrowser() throws ClientProtocolException, IOException {
    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
    	HttpClient client = new DefaultHttpClient();
		String url = "http://m.facebook.com/dialog/oauth/?scope=email,user_about_me,publish_stream&client_id=150586265008036&redirect_uri=http://mosaic.eng.br:8080/server/oauth/facebook/callback.do";
		
		HttpUriRequest request = new HttpGet( url );
		HttpResponse response = client.execute( request );
		BufferedReader reader = getReader( response.getEntity().getContent() );
		String content = extract(reader);
		
    	WebView mWebView = new WebView(this);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient( null ); // TODO pendente
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.loadUrl( "http://m.facebook.com/dialog/oauth/?scope=email,user_about_me,publish_stream&client_id=150586265008036&redirect_uri=http://mosaic.eng.br:8080/server/oauth/facebook/callback.do" );
//        String xx = "https://graph.facebook.com/oauth/authorize?client_id=150586265008036&redirect_uri=http://locahost:8080/pigeon/oauth/facebook/callback.do?&scope=email,user_about_me,publish_stream";
//        mWebView.loadUrl( xx );
        
        mWebView.loadData(content, "text/html", "UTF-8");
        
        layout.addView(mWebView);
        
        return layout;
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
