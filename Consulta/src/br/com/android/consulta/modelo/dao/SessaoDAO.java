package br.com.android.consulta.modelo.dao;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.Usuario;

public class SessaoDAO {

	private final String PREFERENCE_NAME = "LOGIN";
	private final String USUARIO_LOGADO = "USUARIO_LOGADO";
	private final String ID_LOGADO = "ID_USUARIO";
	private final String PERFIL_USUARIO = "PERFIL_USUARIO";
	private SharedPreferences sharedPreferences;

	public SessaoDAO(Activity activity) {
		sharedPreferences = activity.getSharedPreferences(PREFERENCE_NAME, activity.MODE_PRIVATE);
	}

	public Usuario getUsuario() {

		Usuario usuario = new Usuario();
		usuario.setId(sharedPreferences.getInt(ID_LOGADO, 1));
		usuario.setPerfil(sharedPreferences.getString(PERFIL_USUARIO, ""));
		usuario.setLogin(sharedPreferences.getString(USUARIO_LOGADO, ""));

/*		
 		Log.i("user id", usuario.getId() + "");
		Log.i("user login", usuario.getLogin());
		Log.i("user perfil", usuario.getPerfil());
*/
		return usuario;
	}

	public void setUsuario(String usuarioNome, UsuarioDAO dao) {
		SharedPreferences.Editor editor = sharedPreferences.edit();

		Usuario user = dao.retornaIdAndPerfilUsuario(usuarioNome);

		editor.putInt(ID_LOGADO, user.getId());
		editor.putString(USUARIO_LOGADO, user.getLogin());
		editor.putString(PERFIL_USUARIO, user.getPerfil());
		editor.commit();

/*		Log.i("user id", user.getId()+"");
		Log.i("user login", user.getLogin());
		Log.i("user perfil", user.getPerfil()); */
	}

}