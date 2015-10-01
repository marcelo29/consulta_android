package br.com.android.consulta;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.android.consulta.helper.adapter.ConsultaAdapter;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.LocalAtendimento;
import br.com.android.consulta.modelo.bean.Medico;
import br.com.android.consulta.modelo.dao.EspecialidadeDAO;
import br.com.android.consulta.modelo.dao.LocalAtendimentoDAO;
import br.com.android.consulta.modelo.dao.MedicoDAO;

public class ConsultasMarcadas extends Activity {

	private String perfilLogado, usuarioLogado;
	private ArrayList<AgendaMedico> listaAgendaMedico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultas_marcadas);

		ConstroiTela();
		
		Button btnMarcarConsulta = (Button) findViewById(R.id.btnMarcarConsulta);
		btnMarcarConsulta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConsultasMarcadas.this, MarcarConsulta.class);
				startActivity(intent);
			}
		});
	}

	private void ConstroiTela() {

/*		ArrayList<Especialidade> listaEspecialidade = new ArrayList<Especialidade>();
		ArrayList<Medico> listaMedicos = new ArrayList<Medico>();
		ArrayList<LocalAtendimento> listaLocal = new ArrayList<LocalAtendimento>();
*/
		cadastroTeste();

/*		
		listaLocal = new LocalAtendimentoDAO(this).listarLugar();
		LocalAtendimento local = listaLocal.get(0);

		listaEspecialidade = new EspecialidadeDAO(this).listar();
		Especialidade especialidade = listaEspecialidade.get(0);

		listaMedicos = new MedicoDAO(this).listar(especialidade.getId());
		Medico medico = listaMedicos.get(0);
*/

		recebeUsuarioAndPerfil();
		ListView lvConsultasMarcadas = (ListView) findViewById(R.id.lvConsultasMarcadas);
		lvConsultasMarcadas.setAdapter(new ConsultaAdapter(this, listaAgendaMedico, perfilLogado, usuarioLogado,
				R.layout.activity_consultas_marcadas));
	}

	public void cadastroTeste() {
		Especialidade especialidade = new Especialidade();
		especialidade.setNome("Cardiologia ");
		new EspecialidadeDAO(this).cadastrar(especialidade);

		LocalAtendimento local = new LocalAtendimento();
		local.setEndereco("Rua Rio Grande do Norte, 1270");
		new LocalAtendimentoDAO(this).cadastrar(local);

		Medico medico = new Medico();
		medico.setNome("Dr. House ");
		medico.setEspecialidade(especialidade);
		new MedicoDAO(this).cadastrar(medico);

		AgendaMedico agendaMedico = new AgendaMedico();
		agendaMedico.setMedico(medico);
		agendaMedico.setLocalAtendimento(local);

		listaAgendaMedico = new ArrayList<AgendaMedico>();
		listaAgendaMedico.add(agendaMedico);		
	}

	public void recebeUsuarioAndPerfil() {
		final String PERFIL_USUARIO = "PERFIL_USUARIO";
		final String USUARIO_LOGADO = "USUARIO_LOGADO";
		final String PREFERENCE_NAME = "LOGIN";

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		perfilLogado = sharedPreferences.getString(PERFIL_USUARIO, "");
		usuarioLogado = sharedPreferences.getString(USUARIO_LOGADO, "");
	}
}