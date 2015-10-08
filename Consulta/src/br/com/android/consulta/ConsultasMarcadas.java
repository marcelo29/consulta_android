package br.com.android.consulta;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.android.consulta.helper.adapter.ConsultasMarcadasAdapter;
import br.com.android.consulta.modelo.bean.ConsultaMarcada;
import br.com.android.consulta.modelo.bean.Usuario;
import br.com.android.consulta.modelo.dao.AgendaMedicoDAO;
import br.com.android.consulta.modelo.dao.ConsultaMarcadaDAO;
import br.com.android.consulta.modelo.dao.DBDAO;
import br.com.android.consulta.modelo.dao.SessaoDAO;

public class ConsultasMarcadas extends Activity {
	private Usuario usuario;
	private Button btnMarcarConsulta, btnDesmarcarConsulta;
	private ListView lvConsultasMarcadas;
	private ArrayList<ConsultaMarcada> lista, listaDesmarcar;
	// private Boolean validaLista = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultas_marcadas);

		constroiTela();

		marcarConsulta();

		desmarcarConsulta();
	}

	private void desmarcarConsulta() {
		btnDesmarcarConsulta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialogo = new AlertDialog.Builder(ConsultasMarcadas.this);
				dialogo.setNeutralButton("Ok", null);
				// se a lista nao for vazia executa o for
				if (!(listaDesmarcar.size() == 0)) {
					for (int i = 0; i < listaDesmarcar.size(); i++) {
						// desmarcar consulta passando id da agenda e do usuario
						new AgendaMedicoDAO(ConsultasMarcadas.this).DesmarcaConsulta(
								new DBDAO(ConsultasMarcadas.this).getWritableDatabase(),
								listaDesmarcar.get(i).getAgendaMedico().getId(), usuario.getId());

						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("message/rfc822");
						intent.putExtra(Intent.EXTRA_EMAIL, new String[] { usuario.getEmail() });
						intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta desmarcada com sucesso");
						intent.putExtra(Intent.EXTRA_TEXT, listaDesmarcar.get(i).getAgendaMedico().corpoTexto("desmarcada"));
						startActivity(intent);

					}
				} else {
					dialogo.setMessage(R.string.aviso_nenhuma_consulta_marcada);
					dialogo.show();
				}
			}
		});

	}

	private void marcarConsulta() {
		btnMarcarConsulta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConsultasMarcadas.this, MarcarConsulta.class);
				startActivity(intent);
			}
		});
	}

	private void constroiTela() {
		// retorna usuario da sessao
		usuario = new SessaoDAO(this).getUsuario();

		// dps vai ser pela consultasMarcadasDAO
		lista = new ConsultaMarcadaDAO(ConsultasMarcadas.this).retornaConsultasMarcadas(usuario.getId());
		listaDesmarcar = new ArrayList<ConsultaMarcada>();
		// validaLista = true;

		btnMarcarConsulta = (Button) findViewById(R.id.btnMarcarConsulta);
		btnDesmarcarConsulta = (Button) findViewById(R.id.btnDesmarcarConsultas);

		lvConsultasMarcadas = (ListView) findViewById(R.id.lvConsultasMarcadas);
		lvConsultasMarcadas.setAdapter(new ConsultasMarcadasAdapter(this, lista, usuario, listaDesmarcar));
	}

	@Override
	protected void onResume() {
		// atualiza lista apos alteracoes
		super.onResume();
		// atualiza lista
		lista = new ConsultaMarcadaDAO(ConsultasMarcadas.this).retornaConsultasMarcadas(usuario.getId());
		listaDesmarcar = new ArrayList<ConsultaMarcada>();

		lvConsultasMarcadas = (ListView) findViewById(R.id.lvConsultasMarcadas);
		lvConsultasMarcadas.setAdapter(new ConsultasMarcadasAdapter(this, lista, usuario, listaDesmarcar));

	}
}