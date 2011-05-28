package br.eng.mosaic.pigeon.client.gameplay.util;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;

import android.util.Log;
import br.eng.mosaic.pigeon.client.gameplay.Stage;

public class AudioFactory {

	public static Sound createSound(Engine engine, Stage stage, String path) {
		try {
			return SoundFactory.createSoundFromAsset(
					engine.getSoundManager(), stage, path);
		} catch (final Exception e) {
			Log.d("Erro: ", e.toString());
		}
		return null;
	}	
	
	public static Music createMusic(Engine engine, Stage stage, String path) {
		try {
			Music musica = MusicFactory.createMusicFromAsset(engine.getMusicManager(), 
					stage, path);
			musica.setLooping(true);
            return musica;
		} catch (final Exception e) {
			Log.d("Erro: ", e.toString());
		}
		return null;
	}
}
