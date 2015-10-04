package br.com.android.consulta;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.android.consulta.helper.adapter.ConsultasAdapter;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Usuario;
import br.com.android.consulta.modelo.dao.ConsultaMarcadaDAO;
import br.com.android.consulta.modelo.dao.SessaoDAO;

public class ConsultasMarcadas extends Activity {
	private Usuario usuario;

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

		// retorna usuario da sessao
		usuario = new SessaoDAO(this).getUsuario();

		// dps vai ser pela consultasMarcadasDAO
		ArrayList<AgendaMedico> lista = new ConsultaMarcadaDAO(ConsultasMarcadas.this)
				.retornaConsultasMarcadas(usuario.getId());

		// Log.i(this.toString(), listaConsultasMarcadas.get(0).toString());

		ListView lvConsultasMarcadas = (ListView) findViewById(R.id.lvConsultasMarcadas);
		lvConsultasMarcadas
				.setAdapter(new ConsultasAdapter(this, lista, usuario, R.layout.activity_consultas_marcadas));
	}

}