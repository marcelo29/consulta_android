package br.com.android.consulta.modelo.bean;

public class Usuario {

	// atributos do usuario
	private int id;
	private String login, senha, email;
	private Perfil perfil;

	public Usuario() {

	}

	public Usuario(String usuario, String senha, String email, Perfil perfil) {
		this.login = usuario;
		this.senha = senha;
		this.perfil = perfil;
		this.email = email;

	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}