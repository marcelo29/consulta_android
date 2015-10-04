package br.com.android.consulta.modelo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Usuario;

// encapsula o tratamento com o banco de dados
public class UsuarioDAO extends SQLiteOpenHelper {

	// tabela
	private static final String TABELA = "usuario";

	// construtor recebendo o contexto
	public UsuarioDAO(Context context) {
		// recebe o contexto, o banco e a versao
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override // cria o banco
	public void onCreate(SQLiteDatabase db) {
		/*
		 * string p criar a tabela no banco de dados String ddl =
		 * "create table " + TABELA + "(_id integer primary key autoincrement,"
		 * + "login text, senha text, perfil text, email text)";
		 * 
		 * // cria a tabela no banco db.execSQL(ddl);
		 */
	}

	// atualiza o banco
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// string p criar a tabela no banco de dados
		String ddl = "drop table if not exists " + TABELA;

		db.execSQL(ddl);

		onCreate(db);
	}

	// cadastra usuario
	public void cadastrar(Usuario usuario) {
		ContentValues values = new ContentValues();

		values.put("login", usuario.getLogin());
		values.put("senha", usuario.getSenha());
		values.put("perfil", usuario.getPerfil());
		values.put("email", usuario.getEmail());

		getWritableDatabase().insert(TABELA, null, values);
		Log.i(DBDAO.DATABASE, "Cadastro " + TABELA);
	}

	// verifica se o bate usuario e senha
	public boolean Logar(String usuario, String senha) {
		// pega permissao de leitura do banco e passa usuario e senha como
		// paramentros
		Cursor cursor = getReadableDatabase().query(TABELA, null, "login = ? and senha = ?",
				new String[] { usuario, senha }, null, null, null);

		// verifica se retornou vazio
		try {
			return cursor.moveToFirst();
		} catch (Exception e) {
			Log.e(TABELA, e.getMessage());
		} finally {
			cursor.close();
		}

		return false;
	}

	// retorna perfil do usuario
	public Usuario retornaIdAndPerfilUsuario(String usuario) {
		Cursor cursor = getReadableDatabase().query(TABELA, null, "login = ?", new String[] { usuario }, null, null,
				null);

		// move para o primeiro registro do cursor
		try {
			cursor.moveToFirst();
			Usuario user = new Usuario();
			user.setId(cursor.getInt(0));
			user.setLogin(cursor.getString(1));
			user.setEmail(cursor.getString(2));
			user.setPerfil(cursor.getString(3));
			return user;
		} catch (Exception e) {
			Log.e(TABELA, e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

}