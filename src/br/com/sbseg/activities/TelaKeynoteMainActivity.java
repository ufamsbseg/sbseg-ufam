package br.com.sbseg.activities;


import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.beans.Speaker;

public class TelaKeynoteMainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_keynote_main);
		
		Bundle bundle = getIntent().getExtras();
		Speaker speaker = (Speaker)bundle.getSerializable("Speaker");
		String photo = speaker.getPhoto();
		
		TextView nomeSpeaker = (TextView)findViewById(R.id.IDNome);
		TextView filiacaoSpeaker = (TextView)findViewById(R.id.IDFiliacao);
		TextView tituloSpeaker = (TextView)findViewById(R.id.IDTitulo);
		TextView resumoSpeaker = (TextView)findViewById(R.id.IDResumo);
		TextView biografiaSpeaker = (TextView)findViewById(R.id.IDBiografia);
		ImageView foto = (ImageView)findViewById(R.id.imagem);
		
		HashMap<String,Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("antonio",R.drawable.antonio);
    	hashMap.put("davidott",R.drawable.davidott);
    	hashMap.put("georgecox",R.drawable.georgecox);
    	hashMap.put("matt",R.drawable.matt);
    	hashMap.put("fabianmonrose",R.drawable.fabianmonrose);
    	
		
		
		if(photo==null){
			foto.setImageResource(R.drawable.ic_launcher);
		}
		else{
			
			foto.setImageResource(hashMap.get(photo));
		}
		
		nomeSpeaker.setText(speaker.getName());
		filiacaoSpeaker.setText(speaker.getFiliation());
		
		tituloSpeaker.setTextSize(12);
		tituloSpeaker.setText("Titulo: " + speaker.getTalk().getTitle());
		
		resumoSpeaker.setTextSize(12);
		resumoSpeaker.setText("Resumo: " + speaker.getTalk().getResume());
		
		biografiaSpeaker.setTextSize(12);
		biografiaSpeaker.setText("Biografia: " +speaker.getBiography());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_keynote_main, menu);
		return true;
	}

}
