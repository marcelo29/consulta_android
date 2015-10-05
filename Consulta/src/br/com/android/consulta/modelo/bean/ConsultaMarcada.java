package br.com.android.consulta.modelo.bean;

public class ConsultaMarcada {

	private int id;
	private Usuario usuario;
	private AgendaMedico agendaMedico;
	private String dataMarcacaoConsulta, dataCancelamento;
	private Situacao situacao;

	public ConsultaMarcada() {

	}

	public ConsultaMarcada(int id, int idAgenda, Usuario user, String dataMarcacao, String dataCancelamento,
			Situacao situacao) {
		this.id = id;
		this.usuario = user;
		this.dataMarcacaoConsulta = dataMarcacao;
		this.dataCancelamento = dataCancelamento;
		this.situacao = situacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AgendaMedico getAgendaMedico() {
		return agendaMedico;
	}

	public void setAgendaMedico(AgendaMedico agendaMedico) {
		this.agendaMedico = agendaMedico;
	}

	public String getDataMarcacaoConsulta() {
		return dataMarcacaoConsulta;
	}

	public void setDataMarcacaoConsulta(String dataMarcacaoConsulta) {
		this.dataMarcacaoConsulta = dataMarcacaoConsulta;
	}

	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

}