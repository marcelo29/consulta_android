package br.com.android.consulta;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import br.com.android.consulta.modelo.dao.EspecialidadeDAO;
import br.com.android.consulta.modelo.dao.LocalAtendimentoDAO;
import br.com.android.consulta.modelo.dao.MedicoDAO;
import br.com.android.consulta.modelo.dao.SessaoDAO;

public class MarcarConsulta extends Activity {

	private Spinner spnLocal, spnEspecialidade, spnData, spnMedico;
	private ArrayList<AgendaMedico> lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marcar_consulta);

		populaSpinners();
		aplicaFiltro();
		Button btnMostraNoMapa = (Button) findViewById(R.id.btnMostraNoMapa);
		btnMostraNoMapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("geo:0,0?z=14&q=" + lista.get(0).getLocalAtendimento().getEndereco()));
				startActivity(intent);
			}
		});
	}

	private void aplicaFiltro() {
		Button btnAplicaFiltro = (Button) findViewById(R.id.btnAplicaFiltro);
		btnAplicaFiltro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Usuario usuario = new SessaoDAO(MarcarConsulta.this).getUsuario();

				int idEspecialidade = spnEspecialidade.getSelectedItemPosition();
				int idLocal = spnLocal.getSelectedItemPosition();
				int idMedico = spnMedico.getSelectedItemPosition();
				String stringData = spnData.getSelectedItem().toString();
				lista = new AgendaMedicoDAO(MarcarConsulta.this).retornaAgendaMedico(idEspecialidade, idMedico, idLocal,
						stringData);

				ListView lvMarcarConsultas = (ListView) findViewById(R.id.lvMarcarConsultas);
				lvMarcarConsultas.setAdapter(new MarcarConsultaAdapter(MarcarConsulta.this, lista, usuario,
						R.layout.activity_marcar_consulta));
			}
		});
	}

	private void populaSpinners() {
		spnLocal = (Spinner) findViewById(R.id.spnLocal);
		ArrayList<LocalAtendimento> localDao = new LocalAtendimentoDAO(this).listar();
		ArrayList<String> locais = new ArrayList<String>();
		locais.add("");
		for (int i = 0; i < localDao.size(); i++) {
			locais.add(localDao.get(i).getNome());
		}
		ArrayAdapter<String> adpLocais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais);
		spnLocal.setAdapter(adpLocais);

		spnEspecialidade = (Spinner) findViewById(R.id.spnEspecialidade);
		ArrayList<Especialidade> especialidadeDao = new EspecialidadeDAO(this).listar();
		ArrayList<String> especialidades = new ArrayList<String>();
		especialidades.add("");
		for (int i = 0; i < especialidadeDao.size(); i++) {
			especialidades.add(especialidadeDao.get(i).getNome());
		}
		ArrayAdapter<String> adpEspecialidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				especialidades);
		spnEspecialidade.setAdapter(adpEspecialidades);

		spnMedico = (Spinner) findViewById(R.id.spnMedico);
		ArrayList<Medico> medicoDao = new MedicoDAO(this).listar(0);
		ArrayList<String> medicos = new ArrayList<String>();
		medicos.add("");
		for (int i = 0; i < medicoDao.size(); i++) {
			medicos.add(medicoDao.get(i).getNome());
		}
		ArrayAdapter<String> adpMedicos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medicos);
		spnMedico.setAdapter(adpMedicos);

		spnData = (Spinner) findViewById(R.id.spnData);
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

}