package br.com.android.consulta.helper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import br.com.android.consulta.R;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Perfil;

public class ConsultaAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<AgendaMedico> lista;
	private String perfil_logado, usuario;
	private int idLayout;
	
	public ConsultaAdapter(Context context, ArrayList<AgendaMedico> lista, String perfil_logado,
			String usuario, int idLayout) {
		this.context = context;
		this.lista = lista;
		this.perfil_logado = perfil_logado;
		this.usuario = usuario;
		this.idLayout = idLayout;
	}

	// retorna tamanho da lista
	@Override
	public int getCount() {
		return lista.size();
	}

	// retorna objeto da lista segundo a posicao
	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	// retorna posicao da lista
	@Override
	public long getItemId(int position) {
		return position;
	}

	// adapcao da listview
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// obtem a agenda do medico
		AgendaMedico agendaMedico = lista.get(position);
		View layout;

		// se a convertView for null
		if (convertView == null) {
			// chama componentes_consultas_marcadas p/ dentro do list view
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// define o layout q sera utilizado
			if (idLayout == R.layout.activity_consultas_marcadas) {
				layout = inflater.inflate(R.layout.componentes_consultas_marcadas, null);
			} else {
				layout = inflater.inflate(R.layout.componentes_marcar_consulta, null);
			}
		} else {
			layout = convertView;
		}

		// relaciona xml com codigo java
		CheckBox chkConsulta = (CheckBox) layout.findViewById(R.id.chkConsulta);

		TextView txtMedico = (TextView) layout.findViewById(R.id.txtMedico);
		txtMedico.setText(agendaMedico.getMedico().getNome());

		TextView txtLocal = (TextView) layout.findViewById(R.id.txtLocal);
		txtLocal.setText(agendaMedico.getLocalAtendimento().getEndereco());
		
		TextView txtEspecialidade = (TextView) layout.findViewById(R.id.txtEspecialidade);
		txtEspecialidade.setText(agendaMedico.getMedico().getEspecialidade().getNome());

		TextView txtData = (TextView) layout.findViewById(R.id.txtData);
		txtData.setText("10-20-1023");

		if (idLayout == R.layout.activity_consultas_marcadas) {
			TextView txtUsuario = (TextView) layout.findViewById(R.id.txtUsuario);
			txtUsuario.setText(this.usuario);
		}

		// verifica se o perfil logado tem direito de acesso
		if (perfil_logado.equals(Perfil.Adm.categoriaPerfil)) {
			chkConsulta.setEnabled(false);
		}

		// retorno o layout com os componentes dentro do listView
		return layout;
	}

}