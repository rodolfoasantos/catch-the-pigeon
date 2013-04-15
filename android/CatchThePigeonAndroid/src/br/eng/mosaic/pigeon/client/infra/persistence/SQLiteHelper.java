package br.eng.mosaic.pigeon.client.infra.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper implementation to open, create and update DB
 *   
 * @author Raoni Kulesza
 */
class SQLiteHelper extends SQLiteOpenHelper {

	//private static final String CATEGORY = "EASY";

	private String[] scriptSQLCreate;
	private String scriptSQLDelete;

	/**
	 * Create a SQLiteHelper instance
	 * 
	 * @param context
	 * @param DBname Database name
	 * @param DBVersion DBBase version (check)
	 * @param scriptSQLCreate Create table SQL command
	 * @param scriptSQLDelete Drop table SQL command
	 */
	SQLiteHelper(Context context, String DBname, int DBVersion, String[] scriptSQLCreate, String scriptSQLDelete) {
		super(context, DBname, null, DBVersion);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	/**
	 *  Create a new database
	 *  
	 *  @param SQLiteDatabase - Database reference 
	 */
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Creating SQL DB");
		int qtdeScripts = scriptSQLCreate.length;

		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLCreate[i];
			System.out.println(sql);
			db.execSQL(sql);
		}
	}

	@Override
	/**
	 *  Delete all tables and change database version used 
	 *  
	 *  @param SQLiteDatabase - Database reference
	 *  @param oldVersion - Database old version
	 *  @param newVersion - Database new verison 
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("Version Update: " + oldVersion + " to " + newVersion + ". All registries will be deleted.");
		System.out.println(scriptSQLDelete);
		db.execSQL(scriptSQLDelete);
		onCreate(db);
	}
}