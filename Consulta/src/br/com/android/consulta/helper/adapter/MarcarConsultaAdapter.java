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
import br.com.android.consulta.modelo.bean.Usuario;

public class MarcarConsultaAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<AgendaMedico> lista;
	private Usuario usuario;
	private int idLayout;

	public MarcarConsultaAdapter(Context context, ArrayList<AgendaMedico> lista, Usuario usuario, int idLayout) {
		this.context = context;
		this.lista = lista;
		this.usuario = usuario;
		this.idLayout = idLayout;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		AgendaMedico agendaMedico = lista.get(position);
		View layout;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		txtData.setText(agendaMedico.getData());

		if (idLayout == R.layout.activity_consultas_marcadas) {
			TextView txtUsuario = (TextView) layout.findViewById(R.id.txtUsuario);
			txtUsuario.setText(usuario.getLogin());
		}

		return layout;
	}

}
