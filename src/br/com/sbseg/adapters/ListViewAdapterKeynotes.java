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

public class ListViewAdapterKeynotes extends BaseAdapter{
	
//	private ArrayList<Keynote> listKeynotes = new ArrayList<Keynote>();
	private ArrayList<String> StringParaColocarNoTexView = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	private TextView textView;
	private TextView textView2;
//	private ViewGroup viewGroup= null;
	private View rowView = null;
	private View rowView2 = null;
	private int idImage;
	
	public int getCount() {
		return StringParaColocarNoTexView.size();
	}
		
	public ListViewAdapterKeynotes(Context context) {
		this.context = context;
	}
	
	public Object getItem(int position) {
		return StringParaColocarNoTexView.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
			
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.item_list_keynotes, viewGroup, false);
		rowView2 = inflater.inflate(R.layout.item_list_keynotes_2, viewGroup, false);
		
		textView = (TextView)rowView.findViewById(R.id.IdTextView);
		textView2 = (TextView)rowView2.findViewById(R.id.textview_keynoteitem2);
		ImageView imgPalestrante = (ImageView)rowView2.findViewById(R.id.iconePalestrante);
		
		String teste = StringParaColocarNoTexView.get(position);
		char c = teste.charAt(0);
		
		if(c =='*'){
			rowView.setClickable(true);
			String newString = teste.replace("*","");
			textView.setText(newString);
			return rowView;
			
		}
		else{
			String[] dados = teste.split("%");
			textView2.setText(dados[3]);
			imgPalestrante.setImageResource(Integer.parseInt(dados[4]));
			
			textView2.setBackgroundColor(Color.TRANSPARENT);
			
			View divider = (View) rowView2.findViewById(R.id.dividerKeynote);
			divider.setVisibility(View.VISIBLE);
			return rowView2;
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
	
	
	public void setData(ArrayList<String> listString) {
		this.StringParaColocarNoTexView.addAll(listString);
		notifyDataSetChanged();
	}

}
