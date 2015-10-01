package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Especialidade;

public class EspecialidadeDAO extends SQLiteOpenHelper {

	// nome do banco
	private static final String DATABASE = "db_consulta";
	// versao
	private static final int VERSAO = 1;
	// tabela
	private static final String TABELA = "especialidade";

	public EspecialidadeDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	public void cadastrar(Especialidade especialidade) {
		ContentValues values = new ContentValues();

		values.put("nome", especialidade.getNome());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DATABASE, "Cadastro "+TABELA);
	}

	// listar as especialidades
	public ArrayList<Especialidade> listar() {
		ArrayList<Especialidade> lista = new ArrayList<Especialidade>();

		String sql = "Select nome from " + TABELA + " order by nome";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				Especialidade especialidade = new Especialidade();
				especialidade.setNome(cursor.getString(0));
				lista.add(especialidade);
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}

		return lista;
	}

}