package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.Medico;

public class MedicoDAO extends SQLiteOpenHelper {

	// tabela
	private static final String TABELA = "medico";

	public MedicoDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	// cria o banco
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	// atualiza o banco
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void cadastrar(Medico medico) {
		ContentValues values = new ContentValues();

		values.put("crm", medico.getCmd());
		values.put("nome", medico.getNome());
		values.put("id_especialidade", medico.getEspecialidade().getId());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DBDAO.DATABASE, "Cadastro " + TABELA);
	}

	// lista os medicos
	public ArrayList<Medico> listar(int id) {
		ArrayList<Medico> lista = new ArrayList<Medico>();

		String sql = "select * from " + TABELA;

		if (id != 0) {
			sql = sql + " where id_especialidade = " + id;
		}
		sql = sql + " order by _id";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				Medico medico = new Medico();
				medico.setId(cursor.getInt(0));
				medico.setCmd(cursor.getInt(1));
				medico.setNome(cursor.getString(2));
				if (id != 0) {
					medico.setEspecialidade(retornaEspecialidadeMedico(id));
				}
				lista.add(medico);
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}

		return lista;
	}

	// retorna a especialidade do medico
	public Especialidade retornaEspecialidadeMedico(int id) {
		String sql = "Select * from especialidade where _id = " + id;

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		Especialidade especialidade = new Especialidade();

		try {
			especialidade.setId(cursor.getInt(0));
			especialidade.setNome(cursor.getString(1));
			return especialidade;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}
}