package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.sbseg.R;

public class ListViewTutorials extends BaseAdapter{
	
	private ArrayList<String> strings = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	//private ViewGroup viewGroup= null;
	TextView text1;
	TextView text2;
	private View rowView = null;
	private View rowView2 = null;
	private int idImage;

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
		rowView2 = inflater.inflate(R.layout.list_view_tutorials_2, viewGroup, false);
		
		text1 = (TextView) rowView.findViewById(R.id.IdTextView);
		text2 = (TextView) rowView2.findViewById(R.id.IdTextView2);
		ImageView imgPalestrante = (ImageView)rowView2.findViewById(R.id.iconeMinicurso);
	
		String stringTeste = strings.get(position);
		char firstChar = stringTeste.charAt(0);
		
		if(firstChar == '*'){
			rowView.setClickable(true);
			String newString = stringTeste.replace("*","");
			text1.setText(newString);
			return rowView;
		}
		else{
			text2.setText(stringTeste);
			imgPalestrante.setImageResource(R.drawable.caderno);
			
			text2.setBackgroundColor(Color.TRANSPARENT);
			
			View divider = (View) rowView2.findViewById(R.id.divider);
			divider.setVisibility(View.VISIBLE);
			return rowView2;
		}
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

