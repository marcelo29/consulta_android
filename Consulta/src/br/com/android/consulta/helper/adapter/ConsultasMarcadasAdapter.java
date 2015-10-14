package br.com.android.consulta.helper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import br.com.android.consulta.Datas;
import br.com.android.consulta.R;
import br.com.android.consulta.modelo.bean.ConsultaMarcada;
import br.com.android.consulta.modelo.bean.Situacao;
import br.com.android.consulta.modelo.bean.Usuario;

public class ConsultasMarcadasAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ConsultaMarcada> lista, listaDesmarcar;
	private Usuario usuario;

	public ConsultasMarcadasAdapter(Context context, ArrayList<ConsultaMarcada> lista, Usuario usuario,
			ArrayList<ConsultaMarcada> listaDesmarcar) {
		this.context = context;
		this.lista = lista;
		this.usuario = usuario;
		this.listaDesmarcar = listaDesmarcar;
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

		final ConsultaMarcada consultaMarcada = lista.get(position);
		View layout;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// infla o layout recebido para o layout q chamou
			layout = inflater.inflate(R.layout.componentes_consultas_marcadas, null);
		} else {
			layout = convertView;
		}

		// relaciona xml com codigo java
		final CheckBox chkConsulta = (CheckBox) layout.findViewById(R.id.chkConsulta);

		TextView txtMedico = (TextView) layout.findViewById(R.id.txtMedico);
		txtMedico.setText(consultaMarcada.getAgendaMedico().getMedico().getNome());

		TextView txtLocal = (TextView) layout.findViewById(R.id.txtLocal);
		txtLocal.setText(consultaMarcada.getAgendaMedico().getLocalAtendimento().getEndereco() + "	Clinica: "
				+ consultaMarcada.getAgendaMedico().getLocalAtendimento().getNome());

		TextView txtEspecialidade = (TextView) layout.findViewById(R.id.txtEspecialidade);
		txtEspecialidade.setText(consultaMarcada.getAgendaMedico().getMedico().getEspecialidade().getNome());

		TextView txtData = (TextView) layout.findViewById(R.id.txtData);
		txtData.setText(consultaMarcada.getAgendaMedico().getDataHora());

		TextView txtUsuario = (TextView) layout.findViewById(R.id.txtUsuario);
		txtUsuario.setText(consultaMarcada.getUsuario().getLogin());

		// se o usuario da sessao for diferente do q marcou a consulta
		// desabilita o checkbox
		if (usuario.getId() != consultaMarcada.getUsuario().getId())
			chkConsulta.setEnabled(false);
		else
			chkConsulta.setEnabled(true);

		// ao alterar o checkbox ele faz
		chkConsulta.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// add a lista se marcada
					consultaMarcada.setSituacao(Situacao.Cancelada);
					consultaMarcada.getAgendaMedico().setCheckbox(true);
					listaDesmarcar.add(consultaMarcada);
				} else {
					consultaMarcada.setSituacao(Situacao.Marcada);
					consultaMarcada.getAgendaMedico().setCheckbox(false);
					// remove da lista se desmarcada
					if (listaDesmarcar.contains(consultaMarcada)) {
						listaDesmarcar.remove(consultaMarcada);
					}
				}
			}
		});

		// mantem o estado do checkbox
		chkConsulta.setChecked(consultaMarcada.getAgendaMedico().getCheckbox());

		return layout;
	}

}
