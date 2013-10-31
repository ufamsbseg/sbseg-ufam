package br.com.sbseg.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.adapters.ListViewDetailTutorials;
import br.com.sbseg.beans.Speaker;
import br.com.sbseg.beans.Tutorial;

public class DetailTutorialsActivity extends Activity {

	private ListView listViewDetails;
	private ListViewDetailTutorials adapter;
	public Context context;
	private ArrayList<String> strings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_tutorials);
		
		context = this;
		
		adapter = new ListViewDetailTutorials(this);
		
		listViewDetails = (ListView) findViewById(R.id.listView1);
		listViewDetails.setAdapter(adapter);
		
		Bundle bundle = getIntent().getExtras();
		Tutorial tutorial = (Tutorial) bundle.getSerializable("tutorial");
		
		TextView theme = (TextView) findViewById(R.id.textView1);
		theme.setText(tutorial.getId() + ": " + tutorial.getTheme());
		
		strings = filterDetails(tutorial);
		
		adapter.setData(strings);		
	}

	
	private ArrayList<String> filterDetails(Tutorial tutorial){
		ArrayList<String> arrayStrings = new ArrayList<String>();
		
		arrayStrings.add("Descri��o: " + tutorial.getDescription());
		
		for (Speaker speaker : tutorial.getSpeakers()) {
			arrayStrings.add(" ");
			arrayStrings.add("Speaker: " + speaker.getName() + ", " + speaker.getAffiliation());
			arrayStrings.add("Biography: " + speaker.getBiography());
		}
		
		return arrayStrings;
	}
	
}
