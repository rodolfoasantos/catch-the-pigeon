package br.eng.mosaic.pigeon.client.infra.persistence;

import android.content.Context;

/**
 * <pre>
 * Auxiliary class to delete and create a new Database with predefined lines
 * 
 * </pre>
 * 
 * @author Raoni Kulesza
 * 
 */
public class ScoreRepositoryScript extends ScoreRepository {

	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS SCORE";

	// Create table with sequential "_id"
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"create table score ( _id integer primary key autoincrement, userId text not null,date text not null,points text not null);",
			"insert into score(userId,date,points) values('Kellyton Brito','19/05/2011',200);",
			"insert into score(userId,date,points) values('Rafael Roballo','18/05/2011',200);",
			"insert into score(userId,date,points) values('Rafael Rocha','20/05/2011',300);",
			"insert into score(userId,date,points) values('Rodolfo Arruda','19/05/2011',300);",
			"insert into score(userId,date,points) values('Raoni Kulesza','20/05/2011',300);",
			"insert into score(userId,date,points) values('Silvio Meira','21/05/2011',900);",
			"insert into score(userId,date,points) values('Vinicius Garcia','20/05/2011',800);" };

	private static final String DB_NAME = "SCOREDB";

	private static final int VERSAO_BANCO = 1;

	public static final String TABLE_NAME = "score";

	private SQLiteHelper dbHelper;

	public ScoreRepositoryScript(Context ctx) {
		dbHelper = new SQLiteHelper(ctx, ScoreRepositoryScript.DB_NAME, ScoreRepositoryScript.VERSAO_BANCO,
				ScoreRepositoryScript.SCRIPT_DATABASE_CREATE, ScoreRepositoryScript.SCRIPT_DATABASE_DELETE);

		db = dbHelper.getWritableDatabase();
		System.out.println("DB CREATE");
	}

	@Override
	public void close() {
		super.close();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
