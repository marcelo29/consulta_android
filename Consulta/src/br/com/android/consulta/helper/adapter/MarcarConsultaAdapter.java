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
import br.com.android.consulta.R;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Situacao;

public class MarcarConsultaAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<AgendaMedico> lista;
	// private Usuario usuario;
	private ArrayList<AgendaMedico> listaMarcados;

	public MarcarConsultaAdapter(Context context,
			ArrayList<AgendaMedico> lista, /* Usuario usuario, */
			ArrayList<AgendaMedico> listaMarcados) {
		this.context = context;
		this.lista = lista;
		// this.usuario = usuario;
		this.listaMarcados = listaMarcados;
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
		// pega o item da lista pela posicao
		final AgendaMedico agendaMedico = lista.get(position);
		final View layout;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// infla o layout recebido para o layout q chamou
			layout = inflater.inflate(R.layout.componentes_marcar_consulta, null);
		} else {
			layout = convertView;
		}

		// relaciona xml com codigo java
		TextView txtMedico = (TextView) layout.findViewById(R.id.txtMedico);
		txtMedico.setText(agendaMedico.getMedico().getNome());

		TextView txtLocal = (TextView) layout.findViewById(R.id.txtLocal);
		txtLocal.setText(agendaMedico.getLocalAtendimento().getEndereco() + " Clinica:"
				+ agendaMedico.getLocalAtendimento().getNome());

		TextView txtEspecialidade = (TextView) layout.findViewById(R.id.txtEspecialidade);
		txtEspecialidade.setText(agendaMedico.getMedico().getEspecialidade().getNome());

		TextView txtData = (TextView) layout.findViewById(R.id.txtData);
		txtData.setText(agendaMedico.getData());

		final CheckBox chkConsulta = (CheckBox) layout.findViewById(R.id.chkConsulta);

		// ao alterar o checkbox ele faz
		chkConsulta.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// se marcado set situacao para marcada e checkbox para true e
				// add na lista
				if (isChecked) {
					agendaMedico.setSituacao(Situacao.Marcada);
					agendaMedico.setCheckbox(true);
					listaMarcados.add(agendaMedico);
				} else {
					// senao set p disponivel e checkbox para false e remove da
					// lista
					agendaMedico.setSituacao(Situacao.Disponivel);
					agendaMedico.setCheckbox(false);
					if (listaMarcados.contains(agendaMedico)) {
						listaMarcados.remove(agendaMedico);
					}
				}
			}
		});

		// mantem o estado do checkbox
		chkConsulta.setChecked(agendaMedico.getCheckbox());

		return layout;
	}

}