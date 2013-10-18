package br.com.sbseg.activities;


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
import br.com.sbseg.adapters.ListViewAdapterSessions;
import br.com.sbseg.beans.TechnicalSession;
import br.com.sbseg.parsers.SymposiaParse;


public class SessoesTecnicasActivity extends Activity {
	
	Intent intent;
	private ListView techSessions;
	private ListViewAdapterSessions adapter;
	public Context context;
	private ArrayList<TechnicalSession> techSessionList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sessions);
        
        context = this;
    
        adapter = new ListViewAdapterSessions(this);
        
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
			}
			
		}.execute();
	}
}