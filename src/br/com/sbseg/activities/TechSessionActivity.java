package br.com.sbseg.activities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.adapters.ListViewAdapterSecondScreen;
import br.com.sbseg.beans.TechnicalSession;

public class TechSessionActivity extends Activity {
	
	private TextView dataDayTime;
	private TextView idNameLocal;
	private ListViewAdapterSecondScreen adapter;
	public Context context;
	private ListView listPaper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tech_session);
		
		Bundle bundle = getIntent().getExtras();
		TechnicalSession techSession = (TechnicalSession) bundle.get("objectSession");
		//Pegando os TextViews
		dataDayTime = (TextView)findViewById(R.id.day_data_time);
		idNameLocal = (TextView)findViewById(R.id.id_name_local);
//		chairInfo = (TextView)findViewById(R.id.chair_info);
		//Adicionando informações aos TextViews
		dataDayTime.setText(techSession.getDayWeek() + "," + techSession.getDate() + " " + techSession.getBeginning() + "-" + techSession.getEnd());
		idNameLocal.setText(techSession.getId() + ":" + techSession.getName());
//		chairInfo.setText("Chair: " + techSession.getChair().getName() + "(" + techSession.getChair().getAffiliation() + ")");
		
		context = this;
	    
        adapter = new ListViewAdapterSecondScreen(this);
        //Jogando a lista (ListView) no adapter
        listPaper = (ListView) findViewById(R.id.list_view);
        listPaper.setAdapter(adapter);
        adapter.setData(techSession.getPapers()); //Seta no adapter o tipo de dado que o mesmo irá trabalhar.	
	}

}
