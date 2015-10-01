package br.com.android.consulta.modelo.bean;

public enum Situacao {

	Disponivel("D-Disponivel"), Marcada("M-Marcada"), Cancelada("C-Cancelada");
	
	private String situacao;
	
	private Situacao(String situacao) {
		this.situacao = situacao;
	}
	
}