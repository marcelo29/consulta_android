package br.com.android.consulta.modelo.bean;

public enum Perfil {

	// defini as categorias q serao utilidas
	User("U-User"), Adm("A-Adm");

	// atributo para receber o valor passado no construtor
	public String categoriaPerfil;

	// um construtor para passar as categorias
	private Perfil(String perfil) {
		this.categoriaPerfil = perfil;
	}
}