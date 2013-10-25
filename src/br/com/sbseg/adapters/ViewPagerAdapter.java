package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.sbseg.R;
import br.com.sbseg.activities.KeynotesMainActivity;
import br.com.sbseg.activities.SessoesTecnicasActivity;
import br.com.sbseg.activities.Tutorials;
import br.com.sbseg.activities.WorkshopActivity;

public class ViewPagerAdapter extends PagerAdapter{
	
	private LayoutInflater inflater;
	private Context context;
	private View view;
	public int countView = 0;
	private ArrayList<ArrayList<String>> days = new ArrayList<ArrayList<String>>();
	private ListViewAdapterProgramming lvaProgramming;
	private ListView lvCalendar; 
	private static final String[] titles ={"Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira"};
	
	
	
	public ViewPagerAdapter(Context context) {
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		
		return days.size();
	}
	
	

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	public Object getItem(int position){
		return lvCalendar.getItemAtPosition(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
			
			view = inflater.inflate(R.layout.layout_screen1_view_pager, null);
			lvCalendar = (ListView) view.findViewById(R.id.list_view1);
			lvaProgramming = new ListViewAdapterProgramming(context);
			lvaProgramming.setData(days.get(position));
			
			lvCalendar.setOnItemClickListener(new OnItemClickListener(){ //Esse método é usado para pegar um item da lista que foi escolhido pelo usuário.
				
				public void onItemClick(AdapterView<?>parent, View view, int position2, long id){
					
					
					ArrayList<String> listaStrings = days.get(position);
					char c = (listaStrings.get(position2)).charAt(1);
					String tag = listaStrings.get(position2); //usada para ajudar no foco das listas de eventos.
//						Log.e("teste",""+listaStrings.get(position2));
//					
					//TechnicalSession objectSession = (TechnicalSession)lvCalendar.getItemAtPosition(itemPosition);
					
					/*else if()
					Keynote objectKeynote = (Keynote)lvCalendar.getItemAtPosition(itemPosition);
					
					else if()
					
					Bundle bundle = new Bundle();
					bundle.putSerializable("objectSession", objectSession); //Adiciona o item no bundle
					
					
					intent = new Intent(ViewPagerAdapter.this,TechSessionActivity.class);
					intent.putExtras(bundle); //Adiciona o bundle na intent
			        startActivity(intent);*/ 
					switch (c){
						case  'W':
							Intent intent0 = new Intent(context, WorkshopActivity.class);
							intent0.putExtra("tag", tag); //added para teste
							context.startActivity(intent0);
							break;
						case  'P':
							Intent intent1 = new Intent(context, KeynotesMainActivity.class);
							context.startActivity(intent1);
							break;
						case  'S':
							Intent intent2 = new Intent(context, SessoesTecnicasActivity.class);
							intent2.putExtra("tag", tag);
						//	Log.e("STRIIIIIING", ""+listaStrings.get(position2));
						//	Log.e("TAAAAAAAG", ""+tag);
							context.startActivity(intent2);
							break;
						case  'M':
							Intent intent3 = new Intent(context, Tutorials.class);
							context.startActivity(intent3);
							break;
					}
				}
			});
		
		
		lvCalendar.setAdapter(lvaProgramming);
		
		((ViewPager) container).addView(view, 0);
		
		return view;
		
	}
	

	public void setData(ArrayList<ArrayList<String>> days){
		this.days = days;
		notifyDataSetChanged();
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);

	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}	

}
