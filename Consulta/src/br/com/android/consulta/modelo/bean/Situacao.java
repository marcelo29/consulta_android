package br.com.android.consulta.modelo.bean;

public enum Situacao {

	Disponivel("D"), Marcada("M"), Cancelada("C");
	
	public String situacao;
	
	private Situacao(String situacao) {
		this.situacao = situacao;
	}
	
}