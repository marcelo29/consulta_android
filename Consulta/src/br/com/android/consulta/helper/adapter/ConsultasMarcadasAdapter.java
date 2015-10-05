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
import br.com.android.consulta.modelo.bean.ConsultaMarcada;
import br.com.android.consulta.modelo.bean.Usuario;

public class ConsultasMarcadasAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ConsultaMarcada> lista;
	private Usuario usuario;
	private int idLayout;

	public ConsultasMarcadasAdapter(Context context, ArrayList<ConsultaMarcada> lista, Usuario usuario, int idLayout) {
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

		ConsultaMarcada consultaMarcada = lista.get(position);
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
		txtMedico.setText(consultaMarcada.getAgendaMedico().getMedico().getNome());

		TextView txtLocal = (TextView) layout.findViewById(R.id.txtLocal);
		txtLocal.setText(consultaMarcada.getAgendaMedico().getLocalAtendimento().getEndereco());

		TextView txtEspecialidade = (TextView) layout.findViewById(R.id.txtEspecialidade);
		txtEspecialidade.setText(consultaMarcada.getAgendaMedico().getMedico().getEspecialidade().getNome());

		TextView txtData = (TextView) layout.findViewById(R.id.txtData);
		txtData.setText(consultaMarcada.getAgendaMedico().getData());

		if (idLayout == R.layout.activity_consultas_marcadas) {
			TextView txtUsuario = (TextView) layout.findViewById(R.id.txtUsuario);
			txtUsuario.setText(consultaMarcada.getUsuario().getLogin());
		}

		// se o perfil for adm desabilidata o checkbox
		if (!usuario.getLogin().equals(consultaMarcada.getUsuario().getLogin())) {
			chkConsulta.setEnabled(false);
		}

		return layout;
	}

}
