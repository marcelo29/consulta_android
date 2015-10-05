package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.LocalAtendimento;

public class LocalAtendimentoDAO extends SQLiteOpenHelper {

	private static final String TABELA = "local_atendimento";

	public LocalAtendimentoDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void cadastrar(LocalAtendimento local) {
		ContentValues values = new ContentValues();

		values.put("nome", local.getNome());
		values.put("endereco", local.getEndereco());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DBDAO.DATABASE, "Cadastro " + TABELA);
	}

	public ArrayList<LocalAtendimento> listar() {
		ArrayList<LocalAtendimento> lista = new ArrayList<LocalAtendimento>();

		String sql = "Select * from " + TABELA + " order by _id";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				LocalAtendimento lugar = new LocalAtendimento();
				lugar.setId(cursor.getInt(0));
				lugar.setNome(cursor.getString(1));
				lugar.setEndereco(cursor.getString(2));
				lista.add(lugar);
			}
			return lista;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

}