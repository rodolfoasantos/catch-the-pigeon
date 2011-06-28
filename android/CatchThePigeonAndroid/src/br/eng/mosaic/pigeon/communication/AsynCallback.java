package br.eng.mosaic.pigeon.communication;

import org.json.JSONObject;

public interface AsynCallback {
	void onSucess(JSONObject json);
	void onFailure(JSONObject json);
}