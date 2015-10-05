package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.ConsultaMarcada;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.LocalAtendimento;
import br.com.android.consulta.modelo.bean.Medico;
import br.com.android.consulta.modelo.bean.Usuario;

public class ConsultaMarcadaDAO extends SQLiteOpenHelper {

	public ConsultaMarcadaDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// retorna consultas marcadas pelo usuario logado
	public ArrayList<ConsultaMarcada> retornaConsultasMarcadas(int idUsuario) {
		ArrayList<ConsultaMarcada> lista = new ArrayList<ConsultaMarcada>();

		String sql = "select m.nome as medico, e.nome as especialidade, l.endereco as endereco, "
				+ "a.data as data, u.login as usuario from medico m, especialidade e, local_atendimento l, "
				+ "agenda_medico a, consulta_marcada c, usuario u "
				+ "where m.id_especialidade = e._id and m._id = a.id_medico and l._id = a.id_local and a.situacao = 'M' "
				+ "and c.id_agenda_medico = a._id and u._id = c.id_usuario ";

		// se o usuario nao for adm
		if (idUsuario > 1) {
			sql = sql + "and c.id_usuario = " + idUsuario;
		}

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				AgendaMedico agendaMedico = new AgendaMedico();
				Medico medico = new Medico();
				medico.setNome(cursor.getString(0));
				// Log.i(DBDAO.DATABASE, medico.getNome());

				Especialidade especialidade = new Especialidade();
				especialidade.setNome(cursor.getString(1));
				// Log.i(DBDAO.DATABASE, especialidade.getNome());

				medico.setEspecialidade(especialidade);
				LocalAtendimento local = new LocalAtendimento();
				local.setEndereco(cursor.getString(2));
				// Log.i(DBDAO.DATABASE, local.getEndereco());

				agendaMedico.setLocalAtendimento(local);
				agendaMedico.setMedico(medico);
				agendaMedico.setData(cursor.getString(3));
				// Log.i(DBDAO.DATABASE, agendaMedico.getData());

				Usuario usuario = new Usuario();
				usuario.setLogin(cursor.getString(4));
				
				ConsultaMarcada consultaMarcada = new ConsultaMarcada();
				consultaMarcada.setAgendaMedico(agendaMedico);
				consultaMarcada.setUsuario(usuario);
				
				lista.add(consultaMarcada);
			}
			return lista;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

}