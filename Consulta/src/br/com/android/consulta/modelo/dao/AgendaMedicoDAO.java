package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.modelo.bean.AgendaMedico;
import br.com.android.consulta.modelo.bean.Especialidade;
import br.com.android.consulta.modelo.bean.LocalAtendimento;
import br.com.android.consulta.modelo.bean.Medico;

// faz o tratamento da tabela agendaMedico
public class AgendaMedicoDAO extends SQLiteOpenHelper {

	public AgendaMedicoDAO(Context context) {
		super(context, DBDAO.DATABASE, null, DBDAO.VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// seleciona a agenda passando o idmedico, idespecialidade, idlocal,
	// idsituacao
	public ArrayList<AgendaMedico> retornaAgendaMedico(int idEspecialidade, int idMedico, int idLocal, int idSituacao) {
		ArrayList<AgendaMedico> lista = new ArrayList<AgendaMedico>();

		String sql = "select m.nome, e.nome, l.endereco, a.data "
				+ "from medico m, especialidade e, local_atendimento l, situacao s, agenda_medico a where "
				+ "a.id_medico = " + idMedico + " and a.id_especialidade = " + idEspecialidade + " and a.id_local = "
				+ idLocal + " and a.id_situacao = " + idSituacao;

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				// cursor.moveToFirst();

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

				lista.add(agendaMedico);
			}
			return lista;
		} catch (Exception e) {
			Log.e("", e.getMessage());
			return null;
		} finally {
			cursor.close();
		}

	}

	// seleciona agenda segundo a situacao
	public ArrayList<AgendaMedico> retornaAgendaMedico(int idSituacao) {
		ArrayList<AgendaMedico> lista = new ArrayList<AgendaMedico>();

		String sql = "select m.nome, e.nome, l.endereco, a.data "
				+ "from medico m, especialidade e, local_atendimento l, situacao s, agenda_medico a "
				+ "where m.id_especialidade = e._id and m._id = a.id_medico and l._id = a.id_local and s._id = a.id_situacao ";

		if (idSituacao != 0) {
			sql = sql + "and s._id = " + idSituacao;
		}

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				// cursor.moveToFirst();

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

				lista.add(agendaMedico);
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