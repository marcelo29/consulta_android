package br.com.android.consulta.modelo.bean;

public class Medico {

	private int cmd, id;
	private String nome;
	private Especialidade especialidade;

	public Medico() {

	}

	public Medico(int cmd, int id, String nome, Especialidade especialidade) {
		this.id = id;
		this.cmd = cmd;
		this.nome = nome;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
}