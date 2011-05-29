package br.eng.mosaic.pigeon.client.infra.persistence;

import java.util.ArrayList;
import java.util.List;

import br.eng.mosaic.pigeon.common.domain.Score;
import br.eng.mosaic.pigeon.common.domain.Score.Scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * <pre>
 * Encapsulate a SQLite repository to Scores 
 * 
 * To visualize the database in shell:
 * 
 * &gt;&gt; sqlite3 /data/data/br.eng.mosaic.pigeon.client.infra.persistence/databases/ScoreDB
 * 
 * &gt;&gt; More info: http://www.sqlite.org/sqlite.html
 * 
 * &gt;&gt; .exit to exit
 * 
 * </pre>
 * 
 * @author Raoni Kulesza
 * 
 */
public class ScoreRepository {
	private static final String CATEGORY = "EASY";

	private static final String DB_NAME = "SCOREDB";

	public static final String TABLE_NAME = "score";

	protected SQLiteDatabase db;

	public ScoreRepository(Context ctx) {
		db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
	}

	protected ScoreRepository() {
	}

	public long save(Score score) {
		long id = score.id;

		if (id != 0) {
			update(score);
		} else {
			id = insert(score);
		}

		return id;
	}

	public long insert(Score score) {
		ContentValues values = new ContentValues();
		values.put(Scores.USERID, score.userId);
		values.put(Scores.DATE, score.date);
		values.put(Scores.POINTS, score.points);

		long id = insert(values);
		return id;
	}

	public long insert(ContentValues values) {
		long id = db.insert(TABLE_NAME, "", values);
		return id;
	}

	public int update(Score carro) {
		ContentValues values = new ContentValues();
		values.put(Scores.USERID, carro.userId);
		values.put(Scores.DATE, carro.date);
		values.put(Scores.POINTS, carro.points);

		String _id = String.valueOf(carro.id);

		String where = Scores._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = update(values, where, whereArgs);

		return count;
	}

	public int update(ContentValues values, String where, String[] whereArgs) {
		int count = db.update(TABLE_NAME, values, where, whereArgs);
		Log.i(CATEGORY, "Update [" + count + "] lines");
		return count;
	}

	public int delete(long id) {
		String where = Scores._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = delete(where, whereArgs);

		return count;
	}

	public int delete(String where, String[] whereArgs) {
		int count = db.delete(TABLE_NAME, where, whereArgs);
		Log.i(CATEGORY, "Deleted [" + count + "] lines");
		return count;
	}

	public Score searchById(long id) {
		// SELECT * from SCORE where _id=?
		Cursor c = db.query(true, TABLE_NAME, Score.columns, Scores._ID + "="
				+ id, null, null, null, null, null);

		if (c.getCount() > 0) {

			c.moveToFirst();

			Score score = new Score();

			score.id = c.getLong(0);
			score.userId = c.getString(1);
			score.date = c.getString(2);
			score.points = c.getInt(3);

			return score;
		}

		return null;
	}

	public Cursor getCursor() {
		try {
			// select * from score
			return db.query(TABLE_NAME, Score.columns, null, null, null, null,
					null, null);
		} catch (SQLException e) {
			Log.e(CATEGORY, "Error to search SCORE: " + e.toString());
			return null;
		}
	}
	
	public List<Score> list() {
		Cursor c = db.query(true, TABLE_NAME, Score.columns, null, null, null, null, Scores.POINTS + " DESC", null);


		List<Score> scores = new ArrayList<Score>();

		if (c.moveToFirst()) {

			int idxId = c.getColumnIndex(Scores._ID);
			int idxNome = c.getColumnIndex(Scores.USERID);
			int idxPlaca = c.getColumnIndex(Scores.DATE);
			int idxAno = c.getColumnIndex(Scores.POINTS);

			do {
				Score score = new Score();
				scores.add(score);

				score.id = c.getLong(idxId);
				score.userId = c.getString(idxNome);
				score.date = c.getString(idxPlaca);
				score.points = c.getInt(idxAno);

			} while (c.moveToNext());
		}

		return scores;
	}
	
	public List<Score> listTopFive() {
		// SELECT * from SCORE ORDER BY points LIMIT 5
		Cursor c = db.query(true, TABLE_NAME, Score.columns, null, null, null, null, Scores.POINTS + " DESC", "5");
		List<Score> scores = new ArrayList<Score>();
		if (c.moveToFirst()) {

			int idxId = c.getColumnIndex(Scores._ID);
			int idxNome = c.getColumnIndex(Scores.USERID);
			int idxPlaca = c.getColumnIndex(Scores.DATE);
			int idxAno = c.getColumnIndex(Scores.POINTS);

			do {
				Score score = new Score();
				scores.add(score);

				score.id = c.getLong(idxId);
				score.userId = c.getString(idxNome);
				score.date = c.getString(idxPlaca);
				score.points = c.getInt(idxAno);

			} while (c.moveToNext());
		}

		return scores;
	}

	public Score searchByName(String name) {
		Score score = null;

		try {
			// Idem a: SELECT _id,userID,date,points from SCORE where userId = ?
			Cursor c = db.query(TABLE_NAME, Score.columns, Scores.USERID + "='"
					+ name + "'", null, null, null, null);

			if (c.moveToNext()) {

				score = new Score();

				score.id = c.getLong(0);
				score.userId = c.getString(1);
				score.date = c.getString(2);
				score.points = c.getInt(3);
			}
		} catch (SQLException e) {
			Log.e(CATEGORY, "Error to search score by name: " + e.toString());
			return null;
		}

		return score;
	}

	/**
	 *  Search using SQLiteQueryBuilder
	 *  To use by Content Provider
	 */
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		Cursor c = queryBuilder.query(this.db, projection, selection,
				selectionArgs, groupBy, having, orderBy);
		return c;
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}
}
