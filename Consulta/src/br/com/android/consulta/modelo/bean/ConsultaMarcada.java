package br.com.android.consulta.modelo.bean;

import java.util.Date;

public class ConsultaMarcada {

	private int id;
	private Usuario usuario;
	private AgendaMedico agendaMedico;
	private Date dataMarcacaoConsulta, dataCancelamento;
	private Situacao situacao;

	public ConsultaMarcada() {

	}

	public ConsultaMarcada(int id, int idAgenda, Usuario user, Date dataMarcacao, Date dataCancelamento,
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

	public Date getDataMarcacaoConsulta() {
		return dataMarcacaoConsulta;
	}

	public void setDataMarcacaoConsulta(Date dataMarcacaoConsulta) {
		this.dataMarcacaoConsulta = dataMarcacaoConsulta;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

}