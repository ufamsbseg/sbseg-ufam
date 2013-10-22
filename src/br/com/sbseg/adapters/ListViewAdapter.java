package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sbseg.R;

public class ListViewAdapter extends BaseAdapter {
	
	private ArrayList<String> programming = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	private ViewGroup viewGroup= null;
	private View rowView = null;

	public int getCount() {
		return programming.size();
	}
	
	public ListViewAdapter(Context context) {
		this.context = context;
	}
	
	public Object getItem(int position) {
		return programming.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
			
		inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.item_list_view_calendar, viewGroup, false);
		TextView textInfor = (TextView) rowView.findViewById(R.id.text_infor);
		
		char c = programming.get(position).charAt(0);
		String event = programming.get(position);
		
		rowView.setClickable(true);
		
		if (c == '*'){
			programming.get(position).replace("*", "");
			String parts[] = programming.get(position).split("#");
			String tag = parts[0];
			event = parts[1];
			rowView.setTag(tag);
			
			View divider = (View) rowView.findViewById(R.id.divider);
			divider.setVisibility(View.VISIBLE);
			
			textInfor.setBackgroundColor(Color.TRANSPARENT);
			rowView.setClickable(false);
		}
		
		textInfor.setText(event);

		return rowView;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
	
	
	public void setData(ArrayList<String> events) {
		this.programming = events;
		notifyDataSetChanged();
	}


}
