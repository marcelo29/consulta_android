package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Especialidade;

// faz o tratamento da tabela especialidade
public class EspecialidadeDAO extends SQLiteOpenHelper {

	// tabela
	private static final String TABELA = "especialidade";

	public EspecialidadeDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// listar as especialidades
	public ArrayList<Especialidade> listar() {
		ArrayList<Especialidade> lista = new ArrayList<Especialidade>();

		String sql = "select * from " + TABELA + " order by _id";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				Especialidade especialidade = new Especialidade();
				especialidade.setId(cursor.getInt(0));
				especialidade.setNome(cursor.getString(1));
				lista.add(especialidade);
			}
			return lista;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

	// lista os medicos
	public Especialidade retornaEspecialidadeMedico(int idMedico) {
		String sql = "select e._id, e.nome from especialidade e, medico m where m._id = " + idMedico
				+ " order by e._id";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			cursor.moveToFirst();
			Especialidade especialidade = new Especialidade();
			especialidade.setId(cursor.getInt(0));
			especialidade.setNome(cursor.getString(1));
			Log.i(TABELA, especialidade.getNome());
			return especialidade;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}
	}
}