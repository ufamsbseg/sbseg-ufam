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
import br.com.ufam.R;
import br.com.ufam.adapters.ListViewAdapterKeynotes;
import br.com.ufam.beans.Keynote;
import br.com.ufam.beans.Palestrante;
import br.com.ufam.beans.Session;
import br.com.ufam.parsers.keynotesParse;


public class KeynotesMainActivity extends Activity {
	
	private ListView listviewKeynotes;
	private ListViewAdapterKeynotes adapter;
	private Intent intent;
//	private ArrayList<Keynote> keynotes = new ArrayList<Keynote>();
	private ArrayList<String> listStringTextView;
	private ArrayList<Keynote> ArrayKeynotes;
	public Context context;
	private String tag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentViewPager = getIntent();
        String tagAuxString = intentViewPager.getStringExtra("tag");
        String[] tagAuxSplit = tagAuxString.split("#");
        tag = tagAuxSplit[0].replace("*","").trim();
        
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
		       
		       try{
					int positionItem = findPosition(listStringTextView);
					listviewKeynotes.setSelection(positionItem);
					}
					catch(IndexOutOfBoundsException e){
						e.printStackTrace();
					}
				
				
				
			}
			
			
    		
    	}.execute();	
    }
    
    public ArrayList<String> addKeynote(ArrayList<Keynote> listXML){
    	ArrayList<String> listaStringTextView = new ArrayList<String>();
    	int idImage;
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
    				idImage = getResources().getIdentifier(palestrante.getPhoto() , "drawable", getPackageName());
    				dadosSepeaker = keynote.getId()+ "%" + session.getId() + "%" + palestrante.getId()+ "%" + palestrante.getName() + "," + 
    				           palestrante.getFiliation()+ "," + palestrante.getTalk().getTitle() + "%"+ idImage + "%"+palestrante.getTag();
    				listaStringTextView.add(dadosSepeaker);
    				
    				dadosSepeaker ="";
    			}
    			Horario = "";
    		}
    		DiaData = "";
    	}
    	return listaStringTextView;
    }
    
    
    public int findPosition(ArrayList<String> listString){
    	String aux;
    	String []stringTag;
    	for(int i=0;i<listString.size();i++){
    		aux = listString.get(i);
    		stringTag = aux.split("%");
    		if(stringTag.length > 1){
    			if(tag.equalsIgnoreCase(stringTag[5].trim()) == true){
    				return i;
    			}
    		}
    	}
    	return -1;
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
