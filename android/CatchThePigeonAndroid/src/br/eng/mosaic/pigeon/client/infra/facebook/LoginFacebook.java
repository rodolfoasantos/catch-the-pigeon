package br.eng.mosaic.pigeon.client.infra.facebook;

import android.app.Activity;
import android.os.Bundle;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.communication.ServerConstants;

import com.facebook.android.FbDialog;

public class LoginFacebook extends Activity {
	
    public static final String APP_ID = "223093477704451";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
			showDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
        setContentView( R.layout.main );
    }
    
    public void showDialog() throws Exception {
    	String url = "http://m.facebook.com/dialog/oauth/" +
    			"?scope=email,user_about_me,publish_stream" +
    			"&client_id=114292618654468" +
    			"&redirect_uri=" + ServerConstants.getContextFromFacebook() + "/oauth/facebook/callback.do";
    	new FbDialog(this, url, null).show();
    }
    
}