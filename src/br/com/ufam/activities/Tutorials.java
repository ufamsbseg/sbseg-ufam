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
import br.com.ufam.adapters.ListViewTutorials;
import br.com.ufam.beans.Tutorial;
import br.com.ufam.parsers.TutorialsParse;


public class Tutorials extends Activity {
	
	Intent intent;
	private ListView listViewTutorials;
	private ListViewTutorials adapter;
	public Context context;
	private ArrayList<Tutorial> arrayTutorials;
	private ArrayList<String> strings;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);
        
        context = this;
    
        adapter = new ListViewTutorials(this);
        
        listViewTutorials = (ListView) findViewById(R.id.list_view);
        listViewTutorials.setAdapter(adapter);
        
        parseTutorials();
        
        listViewTutorials.setOnItemClickListener(new OnItemClickListener(){
			
			public void onItemClick(AdapterView<?>parent, View view, int position, long id){
				//ListView Clicked item index
				
				//ListView Clicked item value
			
				String teste = (String)adapter.getItem(position);
				String[] dados = teste.split(":");
				
				Tutorial objectTutorial = new Tutorial();
				for (Tutorial tut : arrayTutorials) {
					if(tut.getId().equalsIgnoreCase(dados[0]))
						objectTutorial = tut;
				}
				
				//Show Alert
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("tutorial", objectTutorial);
				intent = new Intent(Tutorials.this,DetailTutorials.class);
				intent.putExtras(bundle);
		        startActivity(intent);
			}
		});
    }
	
	
	public void parseTutorials(){
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				
				try {
					arrayTutorials = TutorialsParse.parse(context);
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
				
			//	imprimir(arrayTutorials);
				
				strings = filterArray(arrayTutorials);
				
				adapter.setData(strings);
			}
			
		}.execute();
	}

	public ArrayList<String> filterArray(ArrayList<Tutorial> arrayTutorials){
		ArrayList<String> strings = new ArrayList<String>();
		String auxDate = arrayTutorials.get(0).getDay().getDate();
		String auxHorary = arrayTutorials.get(0).getPeriod().getBeginning();
		
		strings.add("*" + arrayTutorials.get(0).getDay().getDayWeek() + ", " + auxDate);
		strings.add("*" + auxHorary + " - " + arrayTutorials.get(0).getPeriod().getEnd());
		
		for (Tutorial tutorial : arrayTutorials) {
			if(!auxDate.equalsIgnoreCase(tutorial.getDay().getDate())){
				
				auxDate = tutorial.getDay().getDate();
				auxHorary = tutorial.getPeriod().getBeginning();
				
				strings.add("*" + tutorial.getDay().getDayWeek() + ", " + auxDate);
				strings.add("*" + auxHorary + ", " + tutorial.getPeriod().getEnd());
				
			}
			
			if(!auxHorary.equalsIgnoreCase(tutorial.getPeriod().getBeginning())){
				
				auxHorary = tutorial.getPeriod().getBeginning();
				
				strings.add("*" + auxHorary + ", " + tutorial.getPeriod().getEnd());
			}
			
			strings.add(tutorial.getId() + ": " + tutorial.getTheme());
		}
		
		return strings;
	}
	
	
/*	public void imprimir(ArrayList<Tutorial> array){
		for (Tutorial tutorial : array) {
			Log.e("ID",""+ tutorial.getId());
			Log.e("Theme", ""+tutorial.getTheme());
			Log.e("Date", ""+tutorial.getDay().getDate());
			Log.e("Day Week", ""+tutorial.getDay().getDayWeek());
			Log.e("Beginning", ""+tutorial.getPeriod().getBeginning());
			Log.e("End", ""+tutorial.getPeriod().getEnd());
			Log.e("Description", ""+tutorial.getDescription());

			Log.e("Speakers", "SPEAKERS: ");
			for (Speaker speaker: tutorial.getSpeakers()) {
				Log.e("Name",""+ speaker.getName());
				Log.e("Affiliation",""+ speaker.getAffiliation());
				Log.e("Biography",""+ speaker.getBiography());
			}
			
		}
	} */
	
    
}