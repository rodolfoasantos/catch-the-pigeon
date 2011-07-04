package br.eng.mosaic.pigeon.communication;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.Transition;
import br.eng.mosaic.pigeon.client.infra.PigeonSharedUser;

public class ThreadScoreServer extends Thread {
	
	private Activity activity;
	private String score;
	
	@Override public void run() {
		sendStatusGame();
	}
	
	private String[] getParams() {
		String user = getUser();
		String message = getMessage();
		return new String[] { user, score, message };	
	}
	
	private String getServerUrl(String uri) {
		return ServerConstants.getContextFromAndroid() + "/" + uri;
	}
	
	private boolean hasValidMessage() {
		String message = getMessage();
		if ( message.trim().equals("")) return false;
		return true;
	}
	
	private String getUser() {
		return "" + PigeonSharedUser.get( activity );
	}
	
	private String getMessage() {
		EditText edt = (EditText) activity.findViewById(R.id.msg);
		if (edt == null) return null;
		if (edt.getText() == null) return null;
		if ((edt.getText().toString() == null)) return null;
		
		return edt.getText().toString();
	}
	
	private void sendStatusGame() {
		if ( !hasValidMessage() ) return;
		
		String[] params = getParams();
		String url = Source.score.apply( params ) + "#CTP";
		url = getServerUrl(url);
		
		ProxyClient client = new ProxyClient();
		client.execute(url, new AsynCallback() {
			@Override public void onSucess(JSONObject json)  { 
				Log.d("pigeon", "sucess:" + json.toString());
			}
			@Override public void onFailure(JSONObject json) {
				Log.d("pigeon", "fail:" + json.toString());
				//new QueueServer().exchange();
			}
		});
	}

	public void configure(Transition transition, String score) {
		this.activity = transition;
		this.score = score;
	}
	
}