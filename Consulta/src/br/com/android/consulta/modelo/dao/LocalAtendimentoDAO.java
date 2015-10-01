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

	// nome do banco
	private static final String DATABASE = "db_consulta";
	// versao
	private static final int VERSAO = 1;
	// tabela
	private static final String TABELA = "local_atendimento";

	public LocalAtendimentoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// string p criar a tabela no banco de dados
		String ddl = "create table " + TABELA + "(_id integer primary key autoincrement, nome text, endereco text)";

		// cria a tabela no banco
		db.execSQL(ddl);
		Log.i(DATABASE, TABELA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String ddl = "create table if not exists " + TABELA
				+ "(_id integer primary key autoincrement, nome text, endereco text)";

		ddl = "drop table if exists " + TABELA;

		// executa o drop caso a tabela exista
		db.execSQL(ddl);

		onCreate(db);
	}

	public void cadastrar(LocalAtendimento local) {
		ContentValues values = new ContentValues();

		values.put("nome", local.getNome());
		values.put("endereco", local.getEndereco());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DATABASE, "Cadastro "+TABELA);
	}

	public ArrayList<String> listarLugarEndereco() {
		ArrayList<String> lista = new ArrayList<String>();

		String sql = "Select endereco from " + TABELA + " order by endereco";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				lista.add(cursor.getString(0));
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}

		return lista;
	}

	public ArrayList<LocalAtendimento> listarLugar() {
		ArrayList<LocalAtendimento> lista = new ArrayList<LocalAtendimento>();

		String sql = "Select nome,endereco from " + TABELA + " order by endereco";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				LocalAtendimento lugar = new LocalAtendimento();
				lugar.setNome(cursor.getString(0));
				lugar.setEndereco(cursor.getString(1));
				lista.add(lugar);
			}
		} catch (Exception e) {
			Log.e("", e.getMessage());
		} finally {
			cursor.close();
		}

		return lista;
	}

}