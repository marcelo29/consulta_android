package br.com.android.consulta;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.android.consulta.helper.adapter.ConsultasAdapter;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.LocalAtendimento;
import br.com.android.consulta.modelo.bean.Medico;
import br.com.android.consulta.modelo.bean.Usuario;
import br.com.android.consulta.modelo.dao.AgendaMedicoDAO;
import br.com.android.consulta.modelo.dao.EspecialidadeDAO;
import br.com.android.consulta.modelo.dao.LocalAtendimentoDAO;
import br.com.android.consulta.modelo.dao.MedicoDAO;
import br.com.android.consulta.modelo.dao.SessaoDAO;

// chama layout de marca consulta
public class MarcarConsulta extends Activity {

	private Spinner spnLocal, spnEspecialidade, spnData, spnMedico;
	private static final int SITUACAO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marcar_consulta);

		spnLocal = (Spinner) findViewById(R.id.spnLocal);

		spnLocal.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				preencheSpnLocal();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		// constroiTela();
		// acao do botao aplicar filtro
		aplicarFiltro();
	}

	private void aplicarFiltro() {
		Button btnAplicaFiltro = (Button) findViewById(R.id.btnAplicaFiltro);
		btnAplicaFiltro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pega usuario da sessao
				Usuario usuario = new SessaoDAO(MarcarConsulta.this).getUsuario();
				/*
				 * int idLocal = spnLocal.getSelectedItemPosition() + 1; int
				 * idEspecialidade = spnEspecialidade.getSelectedItemPosition()
				 * + 1; int idData = spnData.getSelectedItemPosition() + 1; int
				 * idMedico = spnMedico.getSelectedItemPosition() + 1;
				 */
				ArrayList<AgendaMedico> lista = new AgendaMedicoDAO(MarcarConsulta.this).retornaAgendaMedico(SITUACAO);

				ListView lvMarcarConsulta = (ListView) findViewById(R.id.lvMarcarConsultas);
				lvMarcarConsulta.setAdapter(
						new ConsultasAdapter(MarcarConsulta.this, lista, usuario, R.layout.activity_marcar_consulta));
			}
		});
	}

	private void constroiTela() {
		spnLocal = (Spinner) findViewById(R.id.spnLocal);
		spnEspecialidade = (Spinner) findViewById(R.id.spnEspecialidade);
		spnData = (Spinner) findViewById(R.id.spnData);
		spnMedico = (Spinner) findViewById(R.id.spnMedico);

		ArrayList<LocalAtendimento> locais = new LocalAtendimentoDAO(this).listarLugar();
		ArrayList<String> local = new ArrayList<String>();
		for (int i = 0; i < locais.size(); i++) {
			local.add(locais.get(i).getEndereco());
		}

		ArrayList<Especialidade> especialidades = new EspecialidadeDAO(this).listar();
		ArrayList<String> especialidade = new ArrayList<String>();
		for (int i = 0; i < especialidade.size(); i++) {
			especialidade.add(especialidades.get(i).getNome());
		}

		ArrayList<String> datas = new ArrayList<String>();

		ArrayList<Medico> medicos = new MedicoDAO(this).listar(0);
		ArrayList<String> medico = new ArrayList<String>();
		for (int i = 0; i < medicos.size(); i++) {
			medico.add(medicos.get(i).getNome());
		}

		ArrayAdapter<String> adpLocais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, local);
		ArrayAdapter<String> adpEspecialidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				especialidade);
		ArrayAdapter<String> adpDatas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datas);
		ArrayAdapter<String> adpMedicos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medico);

		spnData.setAdapter(adpDatas);
		spnLocal.setAdapter(adpLocais);
		spnEspecialidade.setAdapter(adpEspecialidades);
		spnMedico.setAdapter(adpMedicos);
	}

	private void preencheSpnLocal() {
		ArrayList<LocalAtendimento> locais = new LocalAtendimentoDAO(MarcarConsulta.this).listarLugar();
		ArrayList<String> local = new ArrayList<String>();
		for (int i = 0; i < locais.size(); i++) {
			local.add(locais.get(i).getEndereco());
		}

		ArrayAdapter<String> adpLocais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, local);
		spnLocal.setAdapter(adpLocais);
	}

}