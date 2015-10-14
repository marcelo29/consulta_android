package br.com.android.consulta.modelo.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.android.consulta.Datas;
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

	// atualiza agenda_medico e consulta_marcada
	public void MarcaConsulta(SQLiteDatabase db, int idAgendaMedico, int idUsuario) {
		String sql = "update agenda_medico set situacao = 'M' where _id = " + idAgendaMedico;
		db.execSQL(sql);
		sql = "insert into consulta_marcada values(null, " + idUsuario + ", " + idAgendaMedico + ", 'M', null, null)";
		db.execSQL(sql);
	}

	// atualiza agenda_medico e consulta_marcada
	public void DesmarcaConsulta(SQLiteDatabase db, int idAgendaMedico, int idUsuario) {
		String sql = "update agenda_medico set situacao = 'C' where _id = " + idAgendaMedico;
		db.execSQL(sql);
		sql = "update consulta_marcada set situacao = 'C', id_usuario = " + idUsuario + " where id_agenda_medico = "
				+ idAgendaMedico;
		db.execSQL(sql);
	}

	// seleciona a agenda passando o idmedico, idespecialidade, idlocal,
	// data
	public ArrayList<AgendaMedico> retornaAgendaMedico(int idEspecialidade, int idMedico, int idLocal,
			String stringData) {
		ArrayList<AgendaMedico> lista = new ArrayList<AgendaMedico>();

		String sql = "select a._id, m.nome, e.nome, l.nome, l.endereco, a.data, a.hora "
				+ "from medico m, especialidade e, local_atendimento l, agenda_medico a where a.situacao = 'D'";

		if (idEspecialidade != 0) {
			sql = sql + " and m.id_especialidade = e._id and e._id = " + idEspecialidade;
		} else {
			sql = sql + " and m.id_especialidade = e._id";
		}

		if (idMedico != 0) {
			sql = sql + " and a.id_medico = m._id and m._id = " + idMedico;
		} else {
			sql = sql + " and a.id_medico = m._id";
		}

		if (idLocal != 0) {
			sql = sql + " and a.id_local = l._id and l._id = " + idLocal;
		} else {
			sql = sql + " and a.id_local = l._id";
		}

		if (!stringData.isEmpty()) {
			sql = sql + " and a.data = '" + stringData + "'";
		}

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				// cursor.moveToFirst();

				AgendaMedico agendaMedico = new AgendaMedico();
				agendaMedico.setId(cursor.getInt(0));

				Medico medico = new Medico();
				medico.setNome(cursor.getString(1));
				// Log.i(DBDAO.DATABASE, medico.getNome());

				Especialidade especialidade = new Especialidade();
				especialidade.setNome(cursor.getString(2));
				// Log.i(DBDAO.DATABASE, especialidade.getNome());

				medico.setEspecialidade(especialidade);
				LocalAtendimento local = new LocalAtendimento();
				local.setNome(cursor.getString(3));
				local.setEndereco(cursor.getString(4));
				// Log.i(DBDAO.DATABASE, local.getEndereco());

				agendaMedico.setLocalAtendimento(local);
				agendaMedico.setMedico(medico);
				Datas data = new Datas();
				agendaMedico.setData(data.convertStringEmData(cursor.getString(5), "dd/MM/yyyy"));
				agendaMedico.setHora(cursor.getString(6));
				// Log.i(DBDAO.DATABASE, agendaMedico.getData());
				// agendaMedico.setSituacao();

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

	// seleciona agendas disponiveis
	public ArrayList<AgendaMedico> retornaAgendaMedico() {
		ArrayList<AgendaMedico> lista = new ArrayList<AgendaMedico>();

		String sql = "select m.nome, e.nome, l.endereco, a.data, a._id, a.hora "
				+ "from medico m, especialidade e, local_atendimento l, agenda_medico a "
				+ "where m.id_especialidade = e._id and m._id = a.id_medico and l._id = a.id_local and a.situacao = 'D'";

		/*
		 * if (idSituacao != 0) { sql = sql + "and s._id = " + idSituacao; }
		 */

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				// cursor.moveToFirst();

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
				AgendaMedico agendaMedico = new AgendaMedico();
				agendaMedico.setId(cursor.getInt(4));
				agendaMedico.setLocalAtendimento(local);
				agendaMedico.setMedico(medico);
				Datas data = new Datas();
				agendaMedico.setData(data.convertStringEmData(cursor.getString(3), "dd/MM/yyyy"));
				agendaMedico.setHora(cursor.getString(5));
				
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