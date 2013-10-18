package br.com.sbseg.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import br.com.sbseg.R;
import br.com.sbseg.adapters.ViewPagerAdapter;
import br.com.sbseg.parsers.ParseProgramming;

public class ProgrammingActivity extends Activity {
	
	
	public Context context;
	private ViewPager vpDays;
	private ViewPagerAdapter vpaDays;

    @SuppressLint("NewApi") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programming_layout);
        
        ActionBar ab = getActionBar();
        ab.setTitle("SBSeg");
        ab.setSubtitle("Calend�rio");
        
        context = this;
        
        vpDays = (ViewPager) findViewById(R.id.view_pager);
        
        final PagerTabStrip strip = PagerTabStrip.class.cast(vpDays.findViewById(R.id.pagerTabStrip));
		strip.setDrawFullUnderline(false);
		strip.setTabIndicatorColor(Color.parseColor("#5F9EA0"));
		strip.setBackgroundColor(Color.parseColor("#F8F8FF"));
		strip.setTextColor(Color.parseColor("#2F4F4F"));
		strip.setNonPrimaryAlpha(0.3f);
		strip.setTextSpacing(25);
		strip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		strip.setPadding(0, 20, 0, 20);
		
        
        XmlParseTask();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.mainmenu, menu);
      return true;
    } 
    
    public void XmlParseTask() {
    	new AsyncTask<Void, Void, Void>(){
    		
    		private ArrayList<ArrayList<String>> days = new ArrayList<ArrayList<String>>();
    		private ArrayList<String> progMonday;
    		private ArrayList<String> progTuesday;
    		private ArrayList<String> progWednesday;
    		private ArrayList<String> progFriday;

			@Override
			protected Void doInBackground(Void... arg) {
				
				try {
					progMonday = ParseProgramming.parse(context, "11");
					progTuesday = ParseProgramming.parse(context, "12");
					progWednesday = ParseProgramming.parse(context, "13");
					progFriday = ParseProgramming.parse(context, "14");
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
				
				days.add(progMonday);
				days.add(progTuesday);
				days.add(progWednesday);
				days.add(progFriday);
//				
				vpaDays = new ViewPagerAdapter(context);
				vpaDays.setData(days);
				vpDays.setAdapter(vpaDays);
				
				
				
			}
    		
    	}.execute();
    }
    
//    public void printLog(ArrayList<String> programming){
//    	
//    	for (String string : programming) {
//    		Log.e ("Infor:", ""+string);
//		}
//    	
//    }
    
}
