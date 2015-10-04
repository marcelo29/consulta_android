package br.com.android.consulta.modelo.bean;

import java.util.ArrayList;

public class AgendaMedico {

	private int id;
	private Medico medico;
	private String data, hora;
	private LocalAtendimento localAtendimento;
	private Situacao situacao;
	private ArrayList<ConsultaMarcada> listaConsultaMarcada;

	public AgendaMedico() {

	}

	public AgendaMedico(int id, Medico medico, String data, String hora, LocalAtendimento localAtendimento,
			Situacao situacao, ArrayList<ConsultaMarcada> listaConsultaMarcada) {
		this.id = id;
		this.medico = medico;
		this.data = data;
		this.hora = hora;
		this.localAtendimento = localAtendimento;
		this.situacao = situacao;
		this.listaConsultaMarcada = listaConsultaMarcada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public String getData() {
		return data;
	}

	public void setData(String date) {
		this.data = date;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public LocalAtendimento getLocalAtendimento() {
		return localAtendimento;
	}

	public void setLocalAtendimento(LocalAtendimento localAtendimento) {
		this.localAtendimento = localAtendimento;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public ArrayList<ConsultaMarcada> getListaConsultaMarcada() {
		return listaConsultaMarcada;
	}

	public void setConsultaMarcada(ArrayList<ConsultaMarcada> listaConsultaMarcada) {
		this.listaConsultaMarcada = listaConsultaMarcada;
	}
}