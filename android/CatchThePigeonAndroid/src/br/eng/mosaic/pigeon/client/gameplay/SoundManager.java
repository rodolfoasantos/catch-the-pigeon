package br.eng.mosaic.pigeon.client.gameplay;

import java.util.ArrayList;
import java.util.Stack;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {  
	  // Total de sons no pool  
	  private static final int MAXSTREAMS = 10;  
	  // Instância única   
	  private static SoundManager instance;  
	    
	  // Pool de sons  
	  private SoundPool mSoundPool;  
	  // AudioManager para controlar o volume do som  
	  private AudioManager mAudioManager;  
	  // Lista com os ids dos sons adicionados  
	  private ArrayList<Integer> mSoundPoolMap;  
	  // Pilha que armazena as transações   
	  // de execução dos sons  
	  private Stack<Integer> mSongsTransactions;  
	    
	  private Context mContext;  
	  
	  // Construtor privado pra implementar o   
	  // Singleton Design Pattern  
	  private SoundManager(Context ct) {  
	    mContext = ct;  
	    mSoundPoolMap = new ArrayList<Integer>();  
	    mSongsTransactions = new Stack<Integer>();  
	      
	    // Criando o pool de sons  
	    mSoundPool = new SoundPool(MAXSTREAMS, AudioManager.STREAM_MUSIC, 0);  
	      
	    // AudioManager é um serviço de sistema  
	    mAudioManager = (AudioManager)   
	        mContext.getSystemService(Context.AUDIO_SERVICE);
	  }  
	  
	  public void setSound(boolean som) {
		  if(!som) {
			  mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER, 0);
		  } else {
			  mAudioManager.setSpeakerphoneOn(true);
		  }
	  }
	  
	  // Método estático para obter a instância única  
	  public static SoundManager getInstance(Context ct) {  
	    if (instance == null){  
	      instance = new SoundManager(ct);  
	    }  
	    return instance;  
	  }  
	  
	  // Adiciona um som ao pool
	  /* Carrega e obtém o id do som no pool O segundo parâmetro o id do recurso 
       * E o terceiro não serve pra nada, Mas na documentação diz pra colocar 1 */ 
	  public void addSound(int soundId) {  
	    mSoundPoolMap.add(  	       
	      mSoundPool.load(mContext, soundId, 1));  
	  }  
	  
	  // Manda tocar um determinado som
	  /* O AudioManager é usado aqui pra obter o valor atual do volume do aparelho para 
	     * não tocar o som nem baixo nem alto demais. 
	     * A divisão que é feita aqui é pq o método requer um valor entre 0.0 e 1.0. */  
	  public void playSound(int index){
	    
	    float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  
	    streamVolume /= mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
	      
	    /* playId, armazena o id da requisição do som  
	     * a ser tocado. Ele é usado para parar um  
	     * determinado som a qualquer momento. */
	    int playId = mSoundPool.play(  
		      mSoundPoolMap.get(index), // ID do som  
		      streamVolume, // volume da esquerda  
		      streamVolume, // volume da direita  
		      1, // prioridade
		      0, // -1 toca repetidamente,   
		         // n = número de repetições)  
		      1  // pitch. 0.5f metade da velocidade  
		         // 1 = normal e 2 = dobro da velocidade  
		      );  	      
	    // adiciona o id da transação na pilha  
	    mSongsTransactions.push(playId);  
	  }
	  
	  public void stopSounds() {  
	    // Percorre todos os ids da pilha e manda  
	    // parar todos os sons  
	    while (mSongsTransactions.size() > 0)  
	      mSoundPool.stop(mSongsTransactions.pop());  
	  }  
	  
	  //Metodo igual ao anterior mas com o 
	  //parametro q faz q o som faça um loop infitino (-1)
	  public void playLoopedSound(int index) { 			
	     float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
	     mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f); 
	  }
	 	  
	  public void stopLoopSound(int index){
		  mSoundPool.stop(mSoundPoolMap.get(index));
	  }
	  
	  // Libera os recursos alocados  
	  public void cleanup() {  
	    mSoundPool.release();  
	    mSoundPool = null;  
	    mSoundPoolMap.clear();  
	    mSongsTransactions.clear();  
	    mAudioManager.unloadSoundEffects();		
	    instance = null;
	  }	
}
