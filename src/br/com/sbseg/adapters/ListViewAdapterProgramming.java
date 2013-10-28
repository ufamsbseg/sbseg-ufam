package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.sbseg.R;




public class ListViewAdapterProgramming extends BaseAdapter {
	
	private ArrayList<String> programming = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	private ViewGroup viewGroup= null;
	private View rowView = null;
	private View rowView2 = null;

	public int getCount() {
		return programming.size();
	}
	
	public ListViewAdapterProgramming(Context context) {
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
		
		rowView = inflater.inflate(R.layout.item_list_view_program, viewGroup, false);
		rowView2 = inflater.inflate(R.layout.item_layout_program_dois,viewGroup,false); 	
		
		TextView textInfor = (TextView) rowView.findViewById(R.id.text_infor);
		TextView textInfor2=(TextView) rowView2.findViewById(R.id.text_infor1);
		ImageView icone1 = (ImageView)rowView.findViewById(R.id.imagemIcone);
		ImageView icone2 = (ImageView)rowView.findViewById(R.id.imagemIcone2);
		

		
		
		char c = programming.get(position).charAt(0);
		String event = programming.get(position);
		Log.e("teste",""+ event);
		rowView.setClickable(true);
		rowView2.setClickable(true);
		if (c == '*'){
			programming.get(position).replace("*", "");
			String parts[] = programming.get(position).split("#");
			String tag = parts[0].replace("*", "");
			event = parts[1];
			rowView.setTag(tag);
			
			View divider = (View) rowView.findViewById(R.id.divider);
			divider.setVisibility(View.VISIBLE);
		
			
			switch(tag.charAt(0)){
			case  'W':
				icone1.setImageResource(R.drawable.documento);
				icone2.setImageResource(R.drawable.petita_right_arrow);
				break;
			case  'P':
				icone1.setImageResource(R.drawable.apresentacao);
				icone2.setImageResource(R.drawable.petita_right_arrow);
				break;
			case  'S':
				icone1.setImageResource(R.drawable.documento);
				icone2.setImageResource(R.drawable.petita_right_arrow);
				break;
			case  'M':
				icone1.setImageResource(R.drawable.caderno);
				icone2.setImageResource(R.drawable.petita_right_arrow);
				break;
			}
			
			rowView.setClickable(false);
			textInfor.setText(event);
			return rowView;
		}
		
		textInfor2.setText(event);
		return rowView2;
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
