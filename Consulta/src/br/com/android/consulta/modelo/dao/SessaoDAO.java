package br.com.android.consulta.modelo.dao;

import android.app.Activity;
import android.content.SharedPreferences;
import br.com.android.consulta.modelo.bean.Usuario;

public class SessaoDAO {

	private final String PREFERENCE_NAME = "LOGIN", USUARIO_LOGADO = "USUARIO_LOGADO", ID_LOGADO = "ID_USUARIO",
			PERFIL_USUARIO = "PERFIL_USUARIO", EMAIL_USUARIO = "EMAIL_USUARIO";
	private SharedPreferences sharedPreferences;

	public SessaoDAO(Activity activity) {
		sharedPreferences = activity.getSharedPreferences(PREFERENCE_NAME, activity.MODE_PRIVATE);
	}

	public Usuario getUsuario() {

		Usuario usuario = new Usuario();
		usuario.setId(sharedPreferences.getInt(ID_LOGADO, 0));
		usuario.setPerfil(sharedPreferences.getString(PERFIL_USUARIO, ""));
		usuario.setLogin(sharedPreferences.getString(USUARIO_LOGADO, ""));
		usuario.setEmail(sharedPreferences.getString(EMAIL_USUARIO, ""));
		
		return usuario;
	}

	public void setUsuario(String usuarioNome, UsuarioDAO dao) {
		SharedPreferences.Editor editor = sharedPreferences.edit();

		Usuario user = dao.retornaIdAndPerfilUsuario(usuarioNome);

		editor.putInt(ID_LOGADO, user.getId());
		editor.putString(USUARIO_LOGADO, user.getLogin());
		editor.putString(PERFIL_USUARIO, user.getPerfil());
		editor.putString(EMAIL_USUARIO, user.getEmail());
		
		editor.commit();

	}

}