package br.eng.mosaic.pigeon.client.gui.menu;

import java.util.List;

import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.common.domain.Score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter to customize "smile_row.xml" layout. Images maybe will view utilizing
 * ImageView
 * 
 * @author Raoni Kulesza
 * 
 */
public class ScoreListAdapter extends BaseAdapter {
	private Context context;
	private List<Score> list;

	public ScoreListAdapter(Context context, List<Score> list) {
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Score c = list.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.score_table, null);

		TextView userId = (TextView) view.findViewById(R.id.userId);
		userId.setText(c.userId);

		TextView date = (TextView) view.findViewById(R.id.date);
		date.setText(c.date);

		TextView points = (TextView) view.findViewById(R.id.points);
		points.setText(String.valueOf(c.points));
		
		return view;
		
	}
}