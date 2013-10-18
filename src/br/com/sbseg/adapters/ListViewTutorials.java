package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sbseg.R;

public class ListViewTutorials extends BaseAdapter{
	
	private ArrayList<String> strings = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	//private ViewGroup viewGroup= null;
	private View rowView = null;

	public int getCount() {
		return strings.size();
	}
	
	public ListViewTutorials(Context context) {
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
		
		rowView = inflater.inflate(R.layout.list_view_tutorials, viewGroup, false);
		
		TextView text = (TextView) rowView.findViewById(R.id.textView1);
	
		String stringTeste = strings.get(position);
		char firstChar = stringTeste.charAt(0);
		
		if(firstChar == ' ')
			rowView.setClickable(true);
		
		text.setText(stringTeste);
		
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

