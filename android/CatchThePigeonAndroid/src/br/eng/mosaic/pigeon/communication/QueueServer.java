package br.eng.mosaic.pigeon.communication;

import java.util.Queue;

public class QueueServer {
	
	private Queue<StatusUser> queue;
	
	public void exchange() {
		int attempt = 1;
		while( attempt == 1 ) {
			
			for (StatusUser status : this.queue) {
				
			}
			
			attempt = 0;
		}
	}

}

class StatusUser {
	public int level;
	public int score;
	public String msg;
}