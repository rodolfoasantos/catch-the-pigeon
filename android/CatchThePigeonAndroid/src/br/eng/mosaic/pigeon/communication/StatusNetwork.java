package br.eng.mosaic.pigeon.communication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class StatusNetwork {
	
	private Context context;
	
	public StatusNetwork(Context ctx) {
		this.context = ctx;
	}
	
	public Boolean hasNetwork() {
		return connected();
	}
	
	private NetworkInfo getNetworkInfo() {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (NetworkInfo) manager.getActiveNetworkInfo();
	}
	
	private boolean connected() {
 	   NetworkInfo info = getNetworkInfo();
 	   if (info == null || !info.isConnected()) {
 	       return false;
 	   }
 	   if (info.isRoaming()) {
 	       return false;
 	   }
 	  
 	   return true;
 	}

}