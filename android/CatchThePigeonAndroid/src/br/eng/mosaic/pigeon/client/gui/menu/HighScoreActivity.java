package br.eng.mosaic.pigeon.client.gui.menu;

import java.util.List;

import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepository;
import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepositoryScript;
import br.eng.mosaic.pigeon.common.domain.Score;
import android.app.ListActivity;
import android.os.Bundle;

public class HighScoreActivity extends ListActivity {
	
	public static ScoreRepository repository;

	private List<Score> scores;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repository = new ScoreRepositoryScript(this);
		updateList();
	}
	
	protected void updateList() {
		scores = repository.list();

		setListAdapter(new ScoreListAdapter(this, scores));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		repository.close();
	}
	
	

}
