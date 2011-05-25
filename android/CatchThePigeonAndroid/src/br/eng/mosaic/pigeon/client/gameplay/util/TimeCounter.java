package br.eng.mosaic.pigeon.client.gameplay.util;

import android.os.CountDownTimer;

public class TimeCounter extends CountDownTimer {
	
	private Long time;
	private boolean itsOver;
	
	public TimeCounter(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		itsOver = false;
	}
	
	public boolean isItsOver() {
		return itsOver;
	}
	
	public String getActualTime(){
		return "" + time;
	}

	@Override
	public void onFinish() {
		itsOver = true;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		time = (millisUntilFinished/1000);	
	}

}
