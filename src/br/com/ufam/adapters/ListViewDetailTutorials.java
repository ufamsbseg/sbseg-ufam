package br.com.ufam.adapters;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ufam.R;

public class ListViewDetailTutorials extends BaseAdapter {

	private ArrayList<String> strings = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	//private ViewGroup viewGroup= null;
	private View rowView = null;
	
	public int getCount() {
		return strings.size();
	}
	
	public ListViewDetailTutorials(Context context) {
		this.context = context;
	}
	
	public Object getItem(int position) {
		return strings.get(position);
	}
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public View getView(int position, View view, ViewGroup viewGroup) {

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.list_view_detail_tutorials, viewGroup, false);
		
		TextView text = (TextView) rowView.findViewById(R.id.textView1);
		
		text.setText(strings.get(position));
		text.setBackgroundColor(color.transparent);
		
		rowView.setClickable(true);
		
		return rowView;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
	
	public void setData(ArrayList<String> strings) {
		this.strings.addAll(strings);
		notifyDataSetChanged();
	}



}
