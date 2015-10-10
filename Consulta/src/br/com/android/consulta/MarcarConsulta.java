package br.com.android.consulta;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.android.consulta.helper.adapter.MarcarConsultaAdapter;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.LocalAtendimento;
import br.com.android.consulta.modelo.bean.Medico;
import br.com.android.consulta.modelo.bean.Usuario;
import br.com.android.consulta.modelo.dao.AgendaMedicoDAO;
import br.com.android.consulta.modelo.dao.DBDAO;
import br.com.android.consulta.modelo.dao.EspecialidadeDAO;
import br.com.android.consulta.modelo.dao.LocalAtendimentoDAO;
import br.com.android.consulta.modelo.dao.MedicoDAO;
import br.com.android.consulta.modelo.dao.SessaoDAO;

public class MarcarConsulta extends Activity {

	private Spinner spnLocal, spnEspecialidade, spnData, spnMedico;
	private ListView lvMarcarConsultas;
	private Button btnConfirma, btnAplicaFiltro;
	private ArrayList<AgendaMedico> lista, listaMarcada;
	private Usuario usuario;
	private Boolean validaLista = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marcar_consulta);

		// recebe usuario da sessao corrente
		usuario = new SessaoDAO(this).getUsuario();

		constroiTela();
		aplicaFiltro();
		confirmarConsultas();
	}

	private void confirmarConsultas() {
		// confirmar as consultas marcadas pelo usuario
		btnConfirma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialogo = new AlertDialog.Builder(MarcarConsulta.this);
				dialogo.setNeutralButton("Ok", null);
				if (validaLista) {
					if (!(listaMarcada.size() == 0)) {
						for (int i = 0; i < listaMarcada.size(); i++) {
							new AgendaMedicoDAO(MarcarConsulta.this).MarcaConsulta(
									new DBDAO(MarcarConsulta.this).getWritableDatabase(), listaMarcada.get(i).getId(),
									usuario.getId());

							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("message/rfc822");
							intent.putExtra(Intent.EXTRA_EMAIL, new String[] { usuario.getEmail() });
							intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta marcada com sucesso");
							intent.putExtra(Intent.EXTRA_TEXT, listaMarcada.get(i).corpoTexto("marcada"));
							startActivity(intent);
						}
						// recarrega agendas
						btnAplicaFiltro.callOnClick();
					} else {
						// recebe aviso
						dialogo.setMessage(R.string.aviso_nenhuma_consulta_marcada);
						// mostra aviso
						dialogo.show();
					}
				} else {
					dialogo.setMessage(R.string.aviso_lista_vazia);
					dialogo.show();
				}
			}
		});
	}

	private void aplicaFiltro() {
		btnAplicaFiltro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int idEspecialidade = spnEspecialidade.getSelectedItemPosition();
				int idLocal = spnLocal.getSelectedItemPosition();
				int idMedico = spnMedico.getSelectedItemPosition();
				String stringData = spnData.getSelectedItem().toString();

				lista = new AgendaMedicoDAO(MarcarConsulta.this).retornaAgendaMedico(idEspecialidade, idMedico, idLocal,
						stringData);
				listaMarcada = new ArrayList<AgendaMedico>();
				validaLista = true;

				lvMarcarConsultas = (ListView) findViewById(R.id.lvMarcarConsultas);
				lvMarcarConsultas
						.setAdapter(new MarcarConsultaAdapter(MarcarConsulta.this, lista, /* usuario, */ listaMarcada));
			}
		});
	}

	private void constroiTela() {
		// relaciona os componentes ao codigo java
		btnAplicaFiltro = (Button) findViewById(R.id.btnAplicaFiltro);
		btnConfirma = (Button) findViewById(R.id.btnConfirma);
		spnLocal = (Spinner) findViewById(R.id.spnLocal);
		spnEspecialidade = (Spinner) findViewById(R.id.spnEspecialidade);
		spnMedico = (Spinner) findViewById(R.id.spnMedico);
		spnData = (Spinner) findViewById(R.id.spnData);

		// recupera os locais
		ArrayList<LocalAtendimento> localDao = new LocalAtendimentoDAO(this).listar();
		ArrayList<String> locais = new ArrayList<String>();
		locais.add("");
		for (int i = 0; i < localDao.size(); i++) {
			// armazena os locais
			locais.add(localDao.get(i).getNome());
		}
		// seta os locais
		ArrayAdapter<String> adpLocais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais);
		// e os passa pro spinner msm procedimento para os outros
		spnLocal.setAdapter(adpLocais);

		ArrayList<Especialidade> especialidadeDao = new EspecialidadeDAO(this).listar();
		ArrayList<String> especialidades = new ArrayList<String>();
		especialidades.add("");
		for (int i = 0; i < especialidadeDao.size(); i++) {
			especialidades.add(especialidadeDao.get(i).getNome());
		}
		ArrayAdapter<String> adpEspecialidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				especialidades);
		spnEspecialidade.setAdapter(adpEspecialidades);

		ArrayList<Medico> medicoDao = new MedicoDAO(this).listar(0);
		ArrayList<String> medicos = new ArrayList<String>();
		medicos.add("");
		for (int i = 0; i < medicoDao.size(); i++) {
			medicos.add(medicoDao.get(i).getNome());
		}
		ArrayAdapter<String> adpMedicos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medicos);
		spnMedico.setAdapter(adpMedicos);

		ArrayList<AgendaMedico> agendaMedicoDao = new AgendaMedicoDAO(this).retornaAgendaMedico();
		ArrayList<String> dataMedicoDisponivel = new ArrayList<String>();
		dataMedicoDisponivel.add("");
		for (int i = 0; i < agendaMedicoDao.size(); i++) {
			dataMedicoDisponivel.add(agendaMedicoDao.get(i).getData());
		}
		ArrayAdapter<String> adpDatas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				dataMedicoDisponivel);
		spnData.setAdapter(adpDatas);
	}

	// carrega menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();

		inflater.inflate(R.menu.marcar_consulta, menu);

		return true;
	}

	// ao selecionar item do menu faca
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// caso a opcao mapa seja selecionada
		case R.id.menu_mapa:

			AlertDialog.Builder dialogo = new AlertDialog.Builder(MarcarConsulta.this);
			dialogo.setNeutralButton("Ok", null);
			// ver se a lista esta vazia
			if (listaMarcada.size() != 0) {
				// verifica se possui apenas um endereco selecionado
				if (listaMarcada.size() == 1) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(
							Uri.parse("geo:0,0?z=14&q=" + listaMarcada.get(0).getLocalAtendimento().getEndereco()));
					startActivity(intent);
				} else {
					dialogo.setMessage("Marque apenas um endereco para visualizar no mapa!!.");
					dialogo.show();
				}
			} else {
				dialogo.setMessage(R.string.aviso_nenhuma_consulta_marcada);
				dialogo.show();
			}

			return false;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}