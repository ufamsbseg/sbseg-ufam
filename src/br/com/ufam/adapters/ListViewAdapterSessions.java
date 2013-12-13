package br.com.ufam.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.ufam.beans.TechnicalSession;

public class ListViewAdapterSessions extends BaseAdapter{
	
	private ArrayList<TechnicalSession> techSessionList = new ArrayList<TechnicalSession>();
	private Context context;
	private LayoutInflater inflater;
	private View rowView = null;
	private View rowView2 = null;
	private View rowView3 = null;
	
	public int getCount() { //Pega o tamanho da lista
		return techSessionList.size();
	}
	
	public ListViewAdapterSessions(Context context, String tag) { //Salva o context da activity que chamou esse adapter
		this.context = context;
		
	}

	public Object getItem(int position) { //Pega um item da lista que foi jogada nesse Adapter
		return techSessionList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) { //Trata a exibição dos itens no ListView
			
		inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.item_list_view_session, viewGroup, false); //data, hora e item
		rowView2 = inflater.inflate(R.layout.item_list_view_session2, viewGroup, false); //hora e item
		rowView3 = inflater.inflate(R.layout.item_list_view_session3, viewGroup, false); //item
		
		String dateVerifierAnterior = (position != 0 ? techSessionList.get(position-1).getDate().trim() : null);
		String dateVerifierAtual = techSessionList.get(position).getDate();
		
		String TimeVerifierAnterior = (position != 0 ? techSessionList.get(position-1).getBeginning().trim() : null);
		String TimeVerifierAtual = techSessionList.get(position).getBeginning();
		
		
		if(position == 0 || !dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior)){
			
			TextView textDate = (TextView)rowView.findViewById(R.id.infoDate);
			textDate.setBackgroundColor(Color.parseColor("#b2c85c"));
			textDate.setTextColor(Color.parseColor("#FFFFFF"));
			textDate.setText(techSessionList.get(position).getDayWeek() + "  " + techSessionList.get(position).getDate());
			
			TextView textTime = (TextView)rowView.findViewById(R.id.infoTime);
			textTime.setBackgroundColor(Color.parseColor("#b2c85c"));
			textTime.setTextColor(Color.parseColor("#FFFFFF"));
			textTime.setText(techSessionList.get(position).getBeginning() + " - " + techSessionList.get(position).getEnd());
			
			TextView infoSession = (TextView) rowView.findViewById(R.id.infoSession);
			
			infoSession.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider = (View)rowView.findViewById(R.id.dividerSessions);
			divider.setVisibility(View.VISIBLE);
			
			
			return rowView;
		}
		 if(dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior) && !TimeVerifierAtual.equalsIgnoreCase(TimeVerifierAnterior)){
			TextView textTime2 = (TextView)rowView2.findViewById(R.id.infoTime2);
			textTime2.setBackgroundColor(Color.parseColor("#b2c85c"));
			textTime2.setTextColor(Color.parseColor("#FFFFFF"));
			textTime2.setText(techSessionList.get(position).getBeginning() + " - " + techSessionList.get(position).getEnd());
			
			TextView infoSession2 = (TextView) rowView2.findViewById(R.id.infoSession2);
			
			infoSession2.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider2 = (View)rowView2.findViewById(R.id.dividerSessions2);
			divider2.setVisibility(View.VISIBLE);
			
			return rowView2;
			
		}	
		
		else if(dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior) && TimeVerifierAtual.equalsIgnoreCase(TimeVerifierAnterior)){
			
			TextView infoSession3 = (TextView) rowView3.findViewById(R.id.infoSession3);
			
			infoSession3.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider3 = (View)rowView3.findViewById(R.id.dividerSessions3);
			divider3.setVisibility(View.VISIBLE);
			
		}
		return rowView3;

	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
	
	
	public void setData(ArrayList<TechnicalSession> techSessionList) { //Seta o tipo de dado que será trabalhado pelo adapter.
		this.techSessionList.addAll(techSessionList);
		notifyDataSetChanged();
	}

}
