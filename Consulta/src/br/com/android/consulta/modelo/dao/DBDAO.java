package br.com.android.consulta.modelo.dao;

import java.io.File;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// encapsula toda a criacao do banco
public class DBDAO extends SQLiteOpenHelper {
	// nome do banco
	public static final String DATABASE = "db_consulta";
	// versao
	public static final int VERSAO = 3;
	// tabelas do banco
	private static final String tbUsuario = "usuario", tbLocal = "local_atendimento", tbEspecialidade = "especialidade",
			tbMedico = "medico", tbSituacao = "situacao", tbAgendaMedico = "agenda_medico",
			tbConsultaMarcada = "consulta_marcada";

	public DBDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	// cria o banco caso nao exista
	@Override
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

		db.execSQL("insert into " + tbLocal + " values(null, 'Fortaleza', 'Rua Rio Grande Do Norte 1270')");
		db.execSQL("insert into " + tbLocal + " values(null, 'Another', 'Rua Amazonas 612')");

		ddl = "create table if not exists " + tbEspecialidade + "(_id integer primary key autoincrement, nome text)";
		db.execSQL(ddl);
		Log.i(DATABASE, tbEspecialidade);

		db.execSQL("insert into " + tbEspecialidade + " values(null, 'cardiologia')");
		db.execSQL("insert into " + tbEspecialidade + " values(null, 'traumatologia')");
		db.execSQL("insert into " + tbEspecialidade + " values(null, 'neurologia')");
		db.execSQL("insert into " + tbEspecialidade + " values(null, 'geral')");

		ddl = "create table if not exists " + tbMedico
				+ "(_id integer primary key autoincrement, crm integer, nome text, id_especialidade integer, "
				+ "foreign key(id_especialidade) references especialidade(_id))";
		db.execSQL(ddl);
		Log.i(DATABASE, tbMedico);

		db.execSQL("insert into " + tbMedico + " values(null, 1, 'House', 1)"); // cardiologia
		db.execSQL("insert into " + tbMedico + " values(null, 2, 'Marcelo', 2)"); // traumatologia
		db.execSQL("insert into " + tbMedico + " values(null, 3, 'Sum', 3)"); // neurologia
		db.execSQL("insert into " + tbMedico + " values(null, 4, 'Aira', 4)"); // geral

		ddl = "create table if not exists " + tbSituacao
				+ "(_id integer primary key autoincrement, nome text, descricao text)";
		db.execSQL(ddl);
		Log.i(DATABASE, tbSituacao);

		db.execSQL("insert into " + tbSituacao + " values(null, 'd', 'disponivel')");
		db.execSQL("insert into " + tbSituacao + " values(null, 'm', 'marcada')");
		db.execSQL("insert into " + tbSituacao + " values(null, 'c', 'cancelada')");

		ddl = "create table if not exists " + tbAgendaMedico
				+ "(_id integer primary key autoincrement, id_medico integer, id_situacao integer, id_local integer, "
				+ "data text, hora text, foreign key(id_medico) references medico(_id), "
				+ "foreign key(id_situacao) references situacao(_id), foreign key(id_local) references local_atendimento(_id))";
		db.execSQL(ddl);
		Log.i(DATABASE, tbAgendaMedico);

		// marcadas
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 1, 2, 1, '29-09-2015', null)");
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 2, 2, 2, '30-09-2015', null)");
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 3, 2, 2, '01-11-2015', null)");
		
		// disponivel
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 3, 1, 2, '01-10-2015', null)");
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 3, 1, 1, '09-08-2015', null)");
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 4, 1, 2, '03-10-2015', null)");
		db.execSQL("insert into " + tbAgendaMedico + " values(null, 4, 1, 1, '25-09-2015', null)");

		ddl = "create table if not exists " + tbConsultaMarcada
				+ "(_id integer primary key autoincrement, id_usuario integer, id_agenda_medico integer, "
				+ "data_consulta text, data_cancelamento text, foreign key(id_usuario) references usuario(_id), "
				+ "foreign key(id_agenda_medico) references agenda_medico(_id))";
		db.execSQL(ddl);
		Log.i(DATABASE, tbConsultaMarcada);

		db.execSQL("insert into " + tbConsultaMarcada + " values(null, 1, 1, null, null)"); // adm
		db.execSQL("insert into " + tbConsultaMarcada + " values(null, 2, 2, null, null)"); // user
		db.execSQL("insert into " + tbConsultaMarcada + " values(null, 1, 3, null, null)"); // adm
	}

	// atualiza o banco caso mude de versao
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// verifica se a base ja existe
	public boolean doesDatabaseExist(ContextWrapper context, String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		return dbFile.exists();
	}

	public void drop(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + tbLocal);
		db.execSQL("drop table if exists " + tbConsultaMarcada);
		db.execSQL("drop table if exists " + tbAgendaMedico);
		db.execSQL("drop table if exists " + tbSituacao);
		db.execSQL("drop table if exists " + tbMedico);
		db.execSQL("drop table if exists " + tbEspecialidade);
	}
}