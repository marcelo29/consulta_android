package br.com.android.consulta.modelo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Perfil;
import br.com.android.consulta.modelo.bean.Usuario;

// encapsula o tratamento com o banco de dados
public class UsuarioDAO extends SQLiteOpenHelper {

	// tabela
	private static final String TABELA = "usuario";

	public UsuarioDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override // cria o banco
	public void onCreate(SQLiteDatabase db) {

	}

	// atualiza o banco
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// string p criar a tabela no banco de dados
		String ddl = "drop table if not exists " + TABELA;

		db.execSQL(ddl);

		onCreate(db);
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
			user.setSenha(cursor.getString(2));
			user.setPerfil(Perfil.valueOf(cursor.getString(3)));
			user.setEmail(cursor.getString(4));

			return user;
		} catch (Exception e) {
			Log.e(TABELA, e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

}