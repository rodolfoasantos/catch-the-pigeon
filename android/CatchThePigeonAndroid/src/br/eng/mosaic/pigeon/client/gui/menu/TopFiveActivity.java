package br.eng.mosaic.pigeon.client.gui.menu;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepository;
import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepositoryScript;
import br.eng.mosaic.pigeon.common.domain.Score;
import br.eng.mosaic.pigeon.communication.AsynCallback;
import br.eng.mosaic.pigeon.communication.ProxyClient;
import br.eng.mosaic.pigeon.communication.QueueServer;
import br.eng.mosaic.pigeon.communication.Source;

public class TopFiveActivity extends ListActivity {
	
	public static ScoreRepository repository;

	private List<Score> scores;
	
	@Override public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repository = new ScoreRepositoryScript(this);
		updateFromServer();
		
	}
	
	private void updateFromServer() {
		String realAddress = "http://10.0.0.4:8080/" + Source.topfive.url;
		ProxyClient client = new ProxyClient();
		client.execute(realAddress, new AsynCallback() {			
			@Override public void onSucess(JSONObject json)  { 
				Log.d("rafa", "sucess" + json.toString());
				
				try {
					JSONArray array = getPlayersJSON( json.toString() );
					
				} catch(Exception e) {
					
				}
				
			}
			@Override public void onFailure(JSONObject json) {
				//new QueueServer().exchange();
			}
		});
	}
	
	// preciso reestruturar o array no server, sintaxe!
	private JSONObject getPlayerJSON( JSONArray array, int index ) throws JSONException {
		JSONObject obj = array.getJSONObject(index);
		String json = obj.getString("people");
		return new JSONObject(json);
	}

	private JSONArray getPlayersJSON(String content) throws JSONException {
		JSONObject obj = new JSONObject(content);
		String json = obj.getString("topfive");
		return new JSONArray(json);
	}

	@Override protected void onDestroy() {
		super.onDestroy();

		repository.close();
	}
	
	

}
