package br.com.android.consulta.modelo.bean;

public enum Perfil {
	adm("adm"), user("user");

	private final String tipoPerfil;

	private Perfil(String perfil) {
		tipoPerfil = perfil;
	}

	public String toString() {
		return tipoPerfil;
	}

}