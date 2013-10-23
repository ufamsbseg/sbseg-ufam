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
import br.com.sbseg.adapters.ListViewAdapterKeynotes;
import br.com.sbseg.beans.Keynote;
import br.com.sbseg.beans.Palestrante;
import br.com.sbseg.beans.Session;
import br.com.sbseg.parsers.keynotesParse;


public class KeynotesMainActivity extends Activity {
	
	private ListView listviewKeynotes;
	private ListViewAdapterKeynotes adapter;
	private Intent intent;
//	private ArrayList<Keynote> keynotes = new ArrayList<Keynote>();
	private ArrayList<String> listStringTextView;
	private ArrayList<Keynote> ArrayKeynotes;
	public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       context = this;
       
//       keynotes = new ArrayList<Keynotes_Speakers>();
       //addKeynote(ArrayKeynotes, keynotes);
       adapter = new ListViewAdapterKeynotes(this);
       
      
      listviewKeynotes = (ListView) findViewById(R.id.list_view);
      
      listviewKeynotes.setAdapter(adapter);
     // listviewKeynotes.setOnFocusChangeListener(l);
     //  XmlParseTask();
       keyNotesParseTask();  
       
    }
   
    public void keyNotesParseTask() {
    	new AsyncTask<Void, Void, Void>(){

    		
    		@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			
    		@Override
			protected Void doInBackground(Void... arg) {
    			
				
				try {
					ArrayKeynotes = keynotesParse.parse(context);
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
				listStringTextView = addKeynote(ArrayKeynotes);
				adapter.setData(listStringTextView);
				
				listviewKeynotes.setOnItemClickListener(new OnItemClickListener(){
					
					public void onItemClick(AdapterView<?>parent, View view, int position, long id){
						//ListView Clicked item index
					//	int itemPosition = position;
						
						//ListView Clicked item value
						String stringDoSpeaker = (String)adapter.getItem(position);
						String[] dados = stringDoSpeaker.trim().split("%");
						
						Keynote keynote = ArrayKeynotes.get(Integer.parseInt(dados[0]));
						Session session = keynote.getListSession().get(Integer.parseInt(dados[1]));
						Palestrante speaker = session.getListSpeaker().get(Integer.parseInt(dados[2]));
						
						//Show Alert
						//Toast.makeText(getApplicationContext(), "Position: " + itemPosition + "Item: " + speaker.getName(), Toast.LENGTH_LONG).show();
						
						intent = new Intent(KeynotesMainActivity.this,TelaKeynoteMainActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("Speaker", speaker);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
			
			
    		
    	}.execute();	
    }
    
    public ArrayList<String> addKeynote(ArrayList<Keynote> listXML){
    	ArrayList<String> listaStringTextView = new ArrayList<String>();
    	String DiaData;
    	String Horario;
    	String dadosSepeaker;
    	for (Keynote keynote: listXML){
    		DiaData = "*" + keynote.getWeekday() + "," + keynote.getData();
    		listaStringTextView.add(DiaData);
    		for(Session session: keynote.getListSession()){
    			Horario =  "*" + session.getStart() + "-" + session.getEnd();
    			listaStringTextView.add(Horario);
    			for(Palestrante palestrante: session.getListSpeaker()){
    				dadosSepeaker = keynote.getId()+ "%" + session.getId() + "%" + palestrante.getId()+ "%" + palestrante.getName() + "," + palestrante.getFiliation() + "," + palestrante.getTalk().getTitle();
    				listaStringTextView.add(dadosSepeaker);
    				
    				dadosSepeaker ="";
    			}
    			Horario = "";
    		}
    		DiaData = "";
    	}
    	return listaStringTextView;
    }
    
    
    public int getStatusBarHeight() {
    	   int result = 0;
    	   int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    	   if (resourceId > 0) {
    	      result = getResources().getDimensionPixelSize(resourceId);
    	   }
    	   return result;
    }
}
