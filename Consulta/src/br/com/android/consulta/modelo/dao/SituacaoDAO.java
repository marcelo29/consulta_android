package br.com.android.consulta.modelo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SituacaoDAO extends SQLiteOpenHelper {

	private static final String TABELA = "situacao";

	public SituacaoDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void listarSituacao() {
		String sql = "Select descricao from " + TABELA + " order by descricao";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				Log.i(TABELA, cursor.getString(0));
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}
	}

}