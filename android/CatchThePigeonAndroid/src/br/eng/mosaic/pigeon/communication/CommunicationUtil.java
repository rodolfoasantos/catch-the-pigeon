package br.eng.mosaic.pigeon.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CommunicationUtil {


	public static String getFileContent(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = toString(instream);

				instream.close();
				return result;
			}
		} catch (Exception e) {
			Log.e("NGVL", "Falha ao acessar service", e);
		}
		return null;
	}


	private static String toString(InputStream is) 
	throws IOException{

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = 
			new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0){
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}

	public static Drawable ImageOperations(Context ctx, String url, String saveFilename) {
		try {
			InputStream is = (InputStream) fetch(url);
			Drawable d = Drawable.createFromStream(is, "src/assets/gfx");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
}
