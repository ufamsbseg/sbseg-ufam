package br.com.ufam.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import br.com.sbseg.R;
import br.com.ufam.FacebookSbsegMainActivity;
import br.com.ufam.instagramIntegration.InstaActivity;

public class SbsegMainActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sbseg);
	}
	
	public void Calendar(View view){
		Intent intent = new Intent(this,ProgrammingActivity.class);
		startActivity(intent);
	}
	
	public void facebook(View view){
		Intent intent = new Intent(this,FacebookSbsegMainActivity.class);
		startActivity(intent);
	}
	
	public void instagram(View view){
		Intent intent = new Intent(this,InstaActivity.class);
		startActivity(intent);
	}

	
}
