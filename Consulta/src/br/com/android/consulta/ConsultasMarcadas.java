package br.com.android.consulta;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultas_marcadas);

		constroiTela();

		marcarConsulta();

		desmarcarConsulta();
	}

	// desmarca a consulta
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
						Date dataConsulta = new Datas()
								.convertStringEmData(listaDesmarcar.get(i).getAgendaMedico().getDataHora());
						// checa se falta menos de 24horas para a consulta
						if (new Datas().cancelamentoDisponivel(dataConsulta, new Date())) {
							new AgendaMedicoDAO(ConsultasMarcadas.this).DesmarcaConsulta(
									new DBDAO(ConsultasMarcadas.this).getWritableDatabase(),
									listaDesmarcar.get(i).getAgendaMedico().getId(), usuario.getId());

							// envia email avisando q foi desmarcada
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("message/rfc822");
							intent.putExtra(Intent.EXTRA_EMAIL, new String[] { usuario.getEmail() });
							intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta desmarcada com sucesso");
							intent.putExtra(Intent.EXTRA_TEXT,
									listaDesmarcar.get(i).getAgendaMedico().corpoTexto("desmarcada"));
							startActivity(intent);

						} else {
							dialogo.setMessage(R.string.aviso_consulta_indisponivel);
							dialogo.show();
						}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();

		inflater.inflate(R.menu.consultas_marcadas, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_mapa:

			AlertDialog.Builder dialogo = new AlertDialog.Builder(ConsultasMarcadas.this);
			dialogo.setNeutralButton("Ok", null);
			// ver se a lista esta vazia
			if (listaDesmarcar.size() != 0) {
				if (listaDesmarcar.size() == 1) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("geo:0,0?z=14&q="
							+ listaDesmarcar.get(0).getAgendaMedico().getLocalAtendimento().getEndereco()));
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