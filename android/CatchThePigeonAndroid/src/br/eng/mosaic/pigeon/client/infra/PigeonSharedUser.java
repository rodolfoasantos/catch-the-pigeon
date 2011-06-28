package br.eng.mosaic.pigeon.client.infra;

import android.content.Context;
import android.content.SharedPreferences;
import br.eng.mosaic.pigeon.common.domain.User;

public class PigeonSharedUser {
	
	public static void save(User user, Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit(); 
		editor.putString("user.id", user.id);
		editor.commit();	
	}
	
	public static String get(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
		String param = prefs.getString("user.id", null); 
		return param;
	}
	
}