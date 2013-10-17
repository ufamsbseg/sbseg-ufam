package br.com.sbseg.activities;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import br.com.sbseg.R;
import br.com.sbseg.adapters.GridViewAdapter;

public class SbsegMainActivity extends Activity{
	
	
	private GridView gridView;
	private GridViewAdapter gridAdapter;
	private ArrayList<String> arrayString = new ArrayList<String>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setSubtitle("XII Simpósio Brasileiro em Segurança da Informação e de Sistemas Computacionais");
		
		gridView = (GridView) findViewById(R.id.gridView1);
		
		arrayString.add("Programação");
		arrayString.add("Instagram");
		arrayString.add("Facebook");
		arrayString.add("Twitter");
		
		gridAdapter = new GridViewAdapter(this, R.layout.row_grid);
		gridAdapter.setData(arrayString);
		
		gridView.setAdapter(gridAdapter);
	}

}
