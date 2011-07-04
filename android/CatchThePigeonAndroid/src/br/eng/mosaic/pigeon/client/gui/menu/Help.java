package br.eng.mosaic.pigeon.client.gui.menu;

import br.eng.mosaic.pigeon.client.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Help extends Activity { 
    /** Called when the activity is first created. */ 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        this.setContentView(R.layout.help); 
 
        VideoView videoView = (VideoView)this.findViewById(R.id.videoView); 
        MediaController mc = new MediaController(this); 
        videoView.setMediaController(mc); 
        //videoView.setVideoURI(Uri.parse("http://www...mp4")); 
        //videoView.setVideoPath("assets/vfx/help.mp4");
        //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "help.mp4"));
        videoView.setVideoPath("res/raw/help.mp4");
        
        videoView.requestFocus(); 
        videoView.start(); 
    } 
}