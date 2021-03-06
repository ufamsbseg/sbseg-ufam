package br.com.ufam.activities;


import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.sbseg.R;
import br.com.ufam.adapters.ListViewAdapterSessions;
import br.com.ufam.beans.TechnicalSession;
import br.com.ufam.parsers.SymposiaParse;


public class SessoesTecnicasActivity extends Activity {
	
	Intent intent;
	private ListView techSessions;
	private ListViewAdapterSessions adapter;
	public Context context;
	private ArrayList<TechnicalSession> techSessionList; 
	public static String tag;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sessions);
        //Recuperando a tag queu foi passada pela tela principal
        //Essa tag é usada para ajustar o foco
        Intent intentViewPager = getIntent();
        String tagAuxString = intentViewPager.getStringExtra("tag");
        String[] tagAuxSplit = tagAuxString.split("#");
        tag = tagAuxSplit[0].replace("*","");
        
        context = this;
    
        adapter = new ListViewAdapterSessions(this,tag);
        
        techSessions = (ListView) findViewById(R.id.list_view);
        techSessions.setAdapter(adapter);
        
        parseTechnicalSession();
        
        
        techSessions.setOnItemClickListener(new OnItemClickListener(){ //Esse método é usado para pegar um item da lista que foi escolhido pelo usuário.
			
			public void onItemClick(AdapterView<?>parent, View view, int position, long id){
				//ListView Clicked item index
				int itemPosition = position;
				
				//ListView Clicked item value
				TechnicalSession objectSession = (TechnicalSession)techSessions.getItemAtPosition(itemPosition);
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("objectSession", objectSession); //Adiciona o item no bundle
				intent = new Intent(SessoesTecnicasActivity.this,TechSessionActivity.class);
				intent.putExtras(bundle); //Adiciona o bundle na intent
		        startActivity(intent); 
			}
		});
    }
	
	
	public void parseTechnicalSession(){
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				
				try {
					techSessionList = SymposiaParse.parse(context); //techSessionList recebe as informações contidas no xml, através do parse
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				
				super.onPostExecute(result);				
				
				adapter.setData(techSessionList); //Seta no adapter o tipo de dado que o mesmo irá trabalhar.
				//added para testar o foco
				int counter = -1; //usado para encontrar a posição da view na listView
				short flag = 0; //Usado para indicar se o grupo foi encontrado
				for(TechnicalSession techS : techSessionList){

					counter++;
					String tagAuxString = techS.getId();
					String[] tagAux = tagAuxString.split("-");
					String tagClass = tagAux[0];
					
					if(tag.equals(tagClass)){
						techSessions.setSelection(counter);
						flag = 1;
					}
					if(flag != 0) break;
					
				}  
				
			}
			
		}.execute();
	
	}
}