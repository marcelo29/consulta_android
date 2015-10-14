package br.com.android.consulta.modelo.bean;

import java.util.ArrayList;
import java.util.Date;

import br.com.android.consulta.Datas;

public class AgendaMedico {

	private int id;
	private Medico medico;
	private String /* data, */ hora;
	private Date data;
	private LocalAtendimento localAtendimento;
	private Situacao situacao;
	private ArrayList<ConsultaMarcada> listaConsultaMarcada;
	private Boolean checkbox;

	public AgendaMedico() {
		this.checkbox = false;
	}

	public AgendaMedico(int id, Medico medico, Date /* String */ data, String hora, LocalAtendimento localAtendimento,
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

	public Date/* String */ getData() {
		return data;
	}

	public void setData(Date/* String */ date) {
		this.data = date;
	}

	public String getHora() {
		return hora;
	}

	public String getDataHora() {
		String dataHora = new Datas().convertDataEmString(data) + " " + hora;
		return dataHora;
	}
	
	public String getStringData() {
		return new Datas().convertDataEmString(data);
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

	public Boolean getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String corpoTexto(String situacao) {
		return "Consulta " + situacao + " com: " + medico.getNome() + " no dia: " + data + " na Clinica: "
				+ localAtendimento.getNome() + " endereco: " + localAtendimento.getEndereco();
	}
}