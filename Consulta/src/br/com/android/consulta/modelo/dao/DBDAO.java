package br.com.android.consulta.modelo.dao;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.Datas;

// encapsula toda a criacao do banco
public class DBDAO extends SQLiteOpenHelper {

	// nome do banco
	public static final String DATABASE = "db_consulta";
	// versao
	public static final int VERSAO = 9;
	// para exibicao no log cat
	private static final String TAG = "appConsulta";

	// tabelas do banco
	private static String tbUsuario = "usuario", tbLocal = "local_atendimento", tbEspecialidade = "especialidade",
			tbMedico = "medico", tbSituacao = "situacao", tbAgendaMedico = "agenda_medico",
			tbConsultaMarcada = "consulta_marcada";

	public DBDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	// cria o banco caso nao exista
	@Override
	public void onCreate(SQLiteDatabase db) {
		// string p criar a tabela no banco de dados
		String ddl = "create table if not exists " + tbUsuario + "(_id integer primary key autoincrement, "
				+ "login text, senha text, perfil text, email text)";
		db.execSQL(ddl);

		ddl = "create table if not exists " + tbEspecialidade + "(_id integer primary key autoincrement, nome text)";
		db.execSQL(ddl);

		ddl = "create table if not exists " + tbMedico
				+ "(_id integer primary key autoincrement, crm text, nome text, id_especialidade integer, "
				+ "foreign key(id_especialidade) references especialidade(_id))";
		db.execSQL(ddl);

		ddl = "create table if not exists " + tbLocal
				+ "(_id integer primary key autoincrement, nome text, endereco text)";
		db.execSQL(ddl);

		ddl = "create table if not exists " + tbAgendaMedico
				+ "(_id integer primary key autoincrement, id_medico integer, situacao text, id_local integer, "
				+ "data text, hora text, foreign key(id_medico) references medico(_id), "
				+ "foreign key(id_local) references local_atendimento(_id))";
		db.execSQL(ddl);

		ddl = "create table if not exists " + tbConsultaMarcada
				+ "(_id integer primary key autoincrement, id_usuario integer, id_agenda_medico integer, situacao text,"
				+ "data_consulta text, data_cancelamento text, foreign key(id_usuario) references usuario(_id), "
				+ "foreign key(id_agenda_medico) references agenda_medico(_id))";
		db.execSQL(ddl);

		Log.i(TAG, "*********** Create rolou");
		insereUsuario(db, "adm", "123", "adm", "masasp29@gmail.com");
		insereUsuario(db, "user", "123", "user", "marcelo_.aguiar@hotmail.com.br");

		insereEspecialidade(db, "Clinica Medica");
		insereEspecialidade(db, "Neurologista");
		insereEspecialidade(db, "Oftalmologista");
		insereEspecialidade(db, "Cardiologista");

		insereMedico(db, "Manoel Assis", "98654", 1);
		insereMedico(db, "Sabrina Martins", "982354", 2);
		insereMedico(db, "Pedro Paulo", "122354", 3);
		insereMedico(db, "Francisco Almeida", "562345", 1);
		insereMedico(db, "Francisca Silva", "562388", 2);
		insereMedico(db, "Mendes Silva", "555388", 3);
		insereMedico(db, "Cruz Vieira", "51267", 4);
		insereMedico(db, "Samuel Davi", "4551267", 1);
		insereMedico(db, "Cristina Lima", "4983221", 2);

		insereLocalAtendimento(db, "Clinica 24 horas", "Av Rui Barbosa, 110, Aldeota, Fortaleza, Ce");
		insereLocalAtendimento(db, "Posto de Saude Almeida", "Rua 1, 110, Jose Walter, Fortaleza, Ce");
		insereLocalAtendimento(db, "Hospital Santana",
				"Av Desembargador Gonzaga 560, Cidades dos Funcionarios, Fortaleza, Ce");

		// idMedico idLocal situacao data
		insereAgendaMedico(db, 1, 2, "D", "03/10/2015", "08:20:00");
		insereAgendaMedico(db, 2, 1, "D", "18/10/2015", "11:00:00");
		insereAgendaMedico(db, 2, 2, "D", "19/10/2015", "18:10:00");
		insereAgendaMedico(db, 2, 3, "D", "20/10/2015", "18:15:20");
		insereAgendaMedico(db, 3, 1, "D", "21/10/2015", "09:40:00");
		insereAgendaMedico(db, 3, 3, "D", "23/10/2015", "08:10:00");
		insereAgendaMedico(db, 4, 1, "D", "24/10/2015", "12:20:56");
		insereAgendaMedico(db, 4, 2, "D", "25/10/2015", "13:00:56");
		insereAgendaMedico(db, 4, 3, "D", "26/10/2015", "10:20:56");
		insereAgendaMedico(db, 5, 1, "D", "27/10/2015", "11:45:00");
		insereAgendaMedico(db, 5, 2, "D", "28/10/2015", "15:50:00");
		insereAgendaMedico(db, 5, 3, "D", "29/10/2015", "01:45:00");
		insereAgendaMedico(db, 6, 1, "D", "30/10/2015", "12:45:00");
		insereAgendaMedico(db, 6, 2, "D", "01/09/2015", "13:15:00");
		insereAgendaMedico(db, 6, 3, "D", "02/09/2015", "14:45:00");
		insereAgendaMedico(db, 7, 1, "D", "03/09/2015", "06:35:00");
		insereAgendaMedico(db, 7, 2, "D", "04/09/2015", "12:00:00");
		insereAgendaMedico(db, 7, 3, "D", "05/09/2015", "14:40:00");
		insereAgendaMedico(db, 8, 1, "D", "06/09/2015", "14:55:00");
		insereAgendaMedico(db, 8, 2, "D", "07/09/2015", "14:45:00");
		insereAgendaMedico(db, 9, 2, "D", "10/09/2015", "19:05:00");
		insereAgendaMedico(db, 9, 3, "D", "11/09/2015", "21:00:00");

	}

	// cadastra usuario
	private void insereUsuario(SQLiteDatabase db, String login, String senha, String perfil, String email) {
		ContentValues values = new ContentValues();

		values.put("login", login);
		values.put("senha", senha);
		values.put("perfil", perfil);
		values.put("email", email);

		db.insert(tbUsuario, null, values);
	}

	// cadastra lugares
	private void insereLocalAtendimento(SQLiteDatabase db, String nome, String endereco) {
		ContentValues values = new ContentValues();

		values.put("nome", nome);
		values.put("endereco", endereco);

		db.insert(tbLocal, null, values);
	}

	// cadastra especialidades
	private void insereEspecialidade(SQLiteDatabase db, String nome) {
		ContentValues values = new ContentValues();

		values.put("nome", nome);

		db.insert(tbEspecialidade, null, values);
	}

	// cadastra medicos
	private void insereMedico(SQLiteDatabase db, String nome, String crm, int idEspecialidade) {
		ContentValues values = new ContentValues();

		values.put("crm", crm);
		values.put("nome", nome);
		values.put("id_especialidade", idEspecialidade);

		db.insert(tbMedico, null, values);
	}

	// cadastra agenda
	private void insereAgendaMedico(SQLiteDatabase db, int idMedico, int idLocal, String situacao, String data, String hora) {
		ContentValues values = new ContentValues();

		values.put("id_medico", idMedico);
		values.put("id_local", idLocal);
		values.put("situacao", situacao);
		values.put("data", data);
		values.put("hora", hora);

		db.insert(tbAgendaMedico, null, values);
	}

	// cadastra consultas marcadas
	@SuppressWarnings("unused")
	private void insereConsultaMarcada(SQLiteDatabase db, int idUsuario, int idAgendaMedico, String situacao,
			String dataConsulta) {
		ContentValues values = new ContentValues();

		values.put("id_usuario", idUsuario);
		values.put("id_agenda_medico", idAgendaMedico);
		values.put("situacao", situacao);
		values.put("data_consulta", dataConsulta);

		db.insert(tbConsultaMarcada, null, values);
	}

	// atualiza o banco caso mude de versao
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		drop(db);

		onCreate(db);
	}

	// verifica se a base ja existe
	public boolean doesDatabaseExist(ContextWrapper context, String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		return dbFile.exists();
	}

	// deleta o banco
	public void drop(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + tbUsuario);
		db.execSQL("drop table if exists " + tbLocal);
		db.execSQL("drop table if exists " + tbConsultaMarcada);
		db.execSQL("drop table if exists " + tbAgendaMedico);
		db.execSQL("drop table if exists " + tbSituacao);
		db.execSQL("drop table if exists " + tbMedico);
		db.execSQL("drop table if exists " + tbEspecialidade);

		Log.i(TAG, "*********** Drop rolou");
	}
	
	// retorna o id da tabela
	public int retornaIdTabela(String nome, String tabela) {
		String sql = "select _id from " + tabela;

		if (nome.isEmpty())
			return 0;
		else
			sql = sql + " where nome = '" + nome + "'";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			cursor.moveToFirst();
			return cursor.getInt(0);
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return 0;
		} finally {
			cursor.close();
		}
	}

}