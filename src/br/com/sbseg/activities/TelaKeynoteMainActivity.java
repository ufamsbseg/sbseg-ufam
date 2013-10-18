package br.com.sbseg.activities;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.beans.Palestrante;

public class TelaKeynoteMainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_keynote_main);
		
		Bundle bundle = getIntent().getExtras();
		Palestrante speaker = (Palestrante) bundle.getSerializable("Speaker");
		
		TextView nomeSpeaker = (TextView)findViewById(R.id.IDNome);
		TextView filiacaoSpeaker = (TextView)findViewById(R.id.IDFiliacao);
		TextView tituloSpeaker = (TextView)findViewById(R.id.IDTitulo);
		TextView resumoSpeaker = (TextView)findViewById(R.id.IDResumo);
		TextView biografiaSpeaker = (TextView)findViewById(R.id.IDBiografia);
		
		nomeSpeaker.setText(speaker.getName());
	//	nomeSpeaker.canScrollVertically(1);
		filiacaoSpeaker.setText(speaker.getFiliation());
//		filiacaoSpeaker.canScrollVertically(1);
		tituloSpeaker.setText(speaker.getTalk().getTitle());
//		tituloSpeaker.canScrollVertically(1);
		resumoSpeaker.setText(speaker.getTalk().getResume());
	//	resumoSpeaker.canScrollVertically(1);
		biografiaSpeaker.setText(speaker.getBiography());
	//	biografiaSpeaker.canScrollVertically(1);
		
	}


}
