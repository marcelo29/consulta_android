package br.com.android.consulta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.android.consulta.modelo.bean.Usuario;
import br.com.android.consulta.modelo.dao.DBHelper;
import br.com.android.consulta.modelo.dao.UsuarioDAO;

// gerencia o login da aplicacoa
public class Login extends Activity {

	protected static final String PREFERENCE_NAME = "LOGIN";
	// componentes da tela
	private EditText edtUsuario, edtSenha;
	private Button btnLogar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Cria todas as tabelas
		DBHelper helper = new DBHelper(this);
		helper.onCreate(helper.getWritableDatabase());

		// relaciona xml com codigo java
		edtUsuario = (EditText) findViewById(R.id.edtUsuario);
		edtSenha = (EditText) findViewById(R.id.edtSenha);

		btnLogar = (Button) findViewById(R.id.btnLogar);

		ChecarLogin();
	}

	private void ChecarLogin() {
		// evento chamando ao clicar do botao
		btnLogar.setOnClickListener(new OnClickListener() {

			@Override
			// acao executado ao clicar do botao
			public void onClick(View v) {

				// recupera usuario e senha digitados
				Usuario usuario = new Usuario(edtUsuario.getText().toString(), edtSenha.getText().toString(), null,
						null);

				// chama o dao
				UsuarioDAO dao = new UsuarioDAO(Login.this);

				// valida os campos
				boolean validacao = validacao(usuario.getLogin(), usuario.getSenha());

				// monta um dialogo
				AlertDialog.Builder dialogo = new AlertDialog.Builder(Login.this);
				// add botao ao dialogo
				dialogo.setNeutralButton("Ok", null);
				try {
					// verifica se bate usuario e senha
					if (validacao) {
						if (dao.Logar(usuario.getLogin(), usuario.getSenha())) {
							registraPerfil(usuario.getLogin(), dao);

							// carrega novo layotu
							Intent intent = new Intent(Login.this, ConsultasMarcadas.class);
							startActivity(intent);
						} else {
							// avisa q usuario e senha estao errados
							dialogo.setMessage(R.string.msg_erro_invalido_login);
							dialogo.show(); // exibe dialogo
						}
					} else {
						// avisa q usuario e senha estao errados
						dialogo.setMessage(R.string.msg_erro_validacao_login);
						dialogo.show(); // exibe dialogo
					}
				} catch (SQLiteException e) {
					// reportar erro
					e.printStackTrace();
				} finally {
					// garante a finalizacao da conexao com o banco
					dao.close();
				}
			}
		});
	}

	// validas os campos
	private boolean validacao(String usuario, String senha) {
		boolean validacao = true;

		if (usuario == null || usuario.equals("")) {
			validacao = false;
		}

		if (senha == null || senha.equals("")) {
			validacao = false;
		}
		return validacao;
	}

	// registra o perfil e usuario logado
	private void registraPerfil(String usuario, UsuarioDAO dao) {
		String perfilUsuario = dao.retornaPerfilUsuario(usuario);

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString("USUARIO_LOGADO", usuario);
		editor.putString("PERFIL_USUARIO", perfilUsuario);

		editor.commit();
	}

}