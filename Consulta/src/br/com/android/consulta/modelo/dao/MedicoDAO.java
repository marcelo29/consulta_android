package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Medico;

public class MedicoDAO extends SQLiteOpenHelper {
	// nome do banco
	private static final String DATABASE = "db_consulta";
	// versao
	private static final int VERSAO = 1;
	// tabela
	private static final String TABELA = "medico";

	public MedicoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	// cria o banco
	@Override
	public void onCreate(SQLiteDatabase db) {
		// string p criar a tabela no banco de dados
		String ddl = "create table " + TABELA
				+ "(_id integer primary key autoincrement, crm integer, nome text, unique(crm),"
				+ "id_especialidade integer, foreign key(id_especialidade) references especialidade(_id))";

		// cria a tabela no banco
		db.execSQL(ddl);
		Log.i(DATABASE, TABELA);
	}

	// atualiza o banco
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// string p criar a tabela no banco de dados
		String ddl = "create table " + TABELA
				+ "(_id integer primary key autoincrement, crm integer, nome text, unique(crm),"
				+ "id_especialidade integer, foreign key(id_especialidade) references especialidade(_id))";

		ddl = "drop table if exists " + TABELA;

		// cria a tabela no banco
		db.execSQL(ddl);

		onCreate(db);
	}

	public void cadastrar(Medico medico) {
		ContentValues values = new ContentValues();

		values.put("crm", medico.getCmd());
		values.put("nome", medico.getNome());
		values.put("id_especialidade", medico.getEspecialidade().getId());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DATABASE, "Cadastro "+TABELA);		
	}

	// lista os medicos
	public ArrayList<Medico> listar(int id) {
		ArrayList<Medico> lista = new ArrayList<Medico>();

		String sql = "Select * from " + TABELA + " order by nome";

		if (id != 0) {
			sql = sql + " where id_especialidade = " + id;
		}

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				Medico medico = new Medico();
				medico.setId(cursor.getInt(0));
				medico.setCmd(cursor.getInt(1));
				medico.setNome(cursor.getString(2));
				medico.getEspecialidade().setId(cursor.getInt(3));
				lista.add(medico);
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}

		return lista;
	}

}