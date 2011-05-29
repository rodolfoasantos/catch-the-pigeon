package br.eng.mosaic.pigeon.test.socialnetwork;

import static br.eng.mosaic.pigeon.test.helper.MimeType.text_json;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;
import br.eng.mosaic.pigeon.test.helper.MimeType;
import br.eng.mosaic.pigeon.test.helper.ServerFakeLocal;

public class CommunicationTest extends TestCase {
	
	private ServerFakeLocal fakeServer;
	
	private synchronized void startServer(MimeType mime, String content) {
		prepareLocalServer(mime, content);
	}
	
	private void prepareLocalServer(MimeType mime, String content) {
		try {
			this.fakeServer = new ServerFakeLocal(mime, content);
		} catch (IOException e) {
			String message = "falhou ao levantar server : checa isso > \n" + e.getMessage(); 
			throw new RuntimeException( message );
		} 
	}
	
	private synchronized void stopServer() {
		this.fakeServer.stop();
	}
	
	@Test
	public void testSendMessage() {
		String json = "{'name', 'abcdef'}";
		startServer(text_json, json);
		
		stopServer();
	}

}
