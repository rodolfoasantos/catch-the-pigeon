package br.eng.mosaic.pigeon.common.dto;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * DTO to Score of User 
 * 
 * @author Raoni Kulesza
 * 
 */
public class Score {

	public static String[] columns = new String[] { Carros._ID, Carros.NOME, Carros.PLACA, Carros.ANO };

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "br.eng.mosaic.pigeon.client.infra.provider.score";

	public long id;
	public String userId;
	public String date;
	public int points;

	public Score() {
	}

	public Score(String userId, String date, int points) {
		super();
		this.userId = userId;
		this.date = date;
		this.points = points;
	}

	public Score(long id, String userId, String date, int points) {
		super();
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.points = points;
	}

	/**
	 * Inner class to represent Content Provider columns 
	 * 
	 * Implements BaseColumns according to Android guides (already define _id e _count)
	 */
	public static final class Carros implements BaseColumns {
	
		private Carros() {
		}
	
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/scores");
	
		/** 
		 * Mime Type to all scores
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.scores";
	
		/** 
		 * Mime Type to unique score
		 */
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.scores";
	
		/**
		 *  Default ordenation to input in "order by" commands
		 */
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String NOME = "nome";
		public static final String ANO = "ano";
		public static final String PLACA = "placa";
	
		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
		// Adiciona o id na URI default do /carros

		/**
		 * Make an URI to specific Score and this id
		 */
		public static Uri getUriId(long id) {
			Uri uriScore = ContentUris.withAppendedId(Carros.CONTENT_URI, id);
			return uriScore;
		}
	}

	@Override
	public String toString() {
		return "UserId: " + userId + ", Date: " + date + ", Points: " + points;
	}
}
