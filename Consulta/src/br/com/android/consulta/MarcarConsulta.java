package br.com.android.consulta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

// chama layout de marca consulta
public class MarcarConsulta extends Activity {

	private Spinner spnLocal, spnEspecialidade, spnData, spnMedico;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marcar_consulta);
		
	}

}