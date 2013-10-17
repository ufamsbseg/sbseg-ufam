package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.sbseg.R;

public class ViewPagerAdapter extends PagerAdapter{
	
	private LayoutInflater inflater;
	private Context context;
	private View view;
	public int countView = 0;
	private ArrayList<ArrayList<String>> days = new ArrayList<ArrayList<String>>();
	private ListViewAdapter lvaProgramming;
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



	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
//		switch (position) {
//		case 0:
			
			view = inflater.inflate(R.layout.layout_screen1_view_pager, null);
			lvCalendar = (ListView) view.findViewById(R.id.list_view1);
			lvaProgramming = new ListViewAdapter(context);
			lvaProgramming.setData(days.get(position));
			
//			break;
//		case 1:
//			
//			view = inflater.inflate(R.layout.layout_screen1_view_pager, null);
//			lvCalendar = (ListView) view.findViewById(R.id.list_view1);
//			lvaProgramming = new ListViewAdapter(context);
//			lvaProgramming.setData(days.get(position));
//			
//			break;
//			 
//		case 2:
//			
//			view = inflater.inflate(R.layout.layout_screen1_view_pager, null);
//			lvCalendar = (ListView) view.findViewById(R.id.list_view1);
//			lvaProgramming = new ListViewAdapter(context);
//			lvaProgramming.setData(days.get(position));
//			
//			break;
//		case 3:
//			
////			view = inflater.inflate(R.layout.layout_screen3_view_pager, null);
////			lvCalendar = (ListView) view.findViewById(R.id.list_view3);
////			lvaProgramming = new ListViewAdapter(context);
////			lvaProgramming.setData(days.get(position));
//			
//			break;
//		default:
//			break;
//		}
		
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
