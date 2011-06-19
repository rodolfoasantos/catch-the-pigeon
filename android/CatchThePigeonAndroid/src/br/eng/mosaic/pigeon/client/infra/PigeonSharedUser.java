package br.eng.mosaic.pigeon.client.infra;

import android.content.Context;
import android.content.SharedPreferences;
import br.eng.mosaic.pigeon.common.domain.User;

public class PigeonSharedUser {
	
	public static void save(User user, Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit(); 
		editor.putLong("user.id", user.id);
		
		user.socialNetworks.get(0);
		editor.putLong("user..from.facebook", user.id); 
		editor.commit();	
	}
	
	public static long get(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
		return prefs.getLong("user", 0);
	}
	
}