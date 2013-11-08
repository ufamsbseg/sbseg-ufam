package br.com.ufam.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ufam.R;
import br.com.ufam.beans.Palestrante;

public class TelaKeynoteMainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_keynote_main);
		
		Bundle bundle = getIntent().getExtras();
		Palestrante speaker = (Palestrante)bundle.getSerializable("Speaker");
		String photo = speaker.getPhoto();
		
		TextView nomeSpeaker = (TextView)findViewById(R.id.IDNome);
		TextView filiacaoSpeaker = (TextView)findViewById(R.id.IDFiliacao);
		TextView tituloSpeaker = (TextView)findViewById(R.id.IDTitulo);
		TextView resumoSpeaker = (TextView)findViewById(R.id.IDResumo);
		TextView biografiaSpeaker = (TextView)findViewById(R.id.IDBiografia);
		ImageView foto = (ImageView)findViewById(R.id.imagem);
		
    	int idImagem = getResources().getIdentifier(photo , "drawable", getPackageName());
		
		
		if(photo==null){
			foto.setImageResource(R.drawable.ic_launcher);
		}
		else{
		
			foto.setImageResource(idImagem);
		}
		
		nomeSpeaker.setText(speaker.getName());
		filiacaoSpeaker.setText(speaker.getFiliation());
		
		tituloSpeaker.setTextSize(12);
		tituloSpeaker.setText("Titulo: " + speaker.getTalk().getTitle());
		
		View divider = (View)findViewById(R.id.dividerKeynote1);
		divider.setVisibility(View.VISIBLE);
		
		resumoSpeaker.setTextSize(12);
		resumoSpeaker.setText("Resumo: " + speaker.getTalk().getResume());
		
		View divider1 = (View)findViewById(R.id.dividerKeynote2);
		divider1.setVisibility(View.VISIBLE);
		
		biografiaSpeaker.setTextSize(12);
		biografiaSpeaker.setText("Biografia: " +speaker.getBiography());
		
		
	}

	
}
