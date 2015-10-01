package br.com.android.consulta.modelo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

// encapsula toda a criacao do banco
public class DBHelper extends SQLiteOpenHelper {
	// nome do banco
	private static final String DATABASE = "db_consulta";
	// versao
	private static final int VERSAO = 1;
	// tabelas do banco
	private static final String tbUsuario = "usuario", tbLocal = "local_atendimento", tbEspecialidade = "especialidade",
			tbMedico = "medico";

	public DBHelper(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	// cria o banco caso nao exista
	public void onCreate(SQLiteDatabase db) {
		// string p criar a tabela no banco de dados
		db.execSQL("create table if not exists " + tbUsuario + "(_id integer primary key autoincrement, "
				+ "login text, senha text, perfil text, email text)");
		Log.i(DATABASE, tbUsuario);

		db.execSQL("insert into " + tbUsuario + " values(null, 'adm', '123', 'A-Adm', 'adm@adm')");
		db.execSQL("insert into " + tbUsuario + " values(null, 'user', '123', 'U-User', 'user@user')");

		String ddl = "create table if not exists " + tbLocal
				+ "(_id integer primary key autoincrement, nome text, endereco text)";
		db.execSQL(ddl);
		Log.i(DATABASE, tbLocal);

		ddl = "create table if not exists " + tbEspecialidade + "(_id integer primary key autoincrement, nome text)";
		db.execSQL(ddl);
		Log.i(DATABASE, tbEspecialidade);

		ddl = "create table if not exists " + tbMedico
				+ "(_id integer primary key autoincrement, crm integer, nome text, id_especialidade integer, "
				+ "foreign key(id_especialidade) references especialidade(_id))";
		db.execSQL(ddl);
		Log.i(DATABASE, tbMedico);
	}

	// atualiza o banco caso mude de versao
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}