package br.com.sbseg.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.beans.TechnicalSession;

public class ListViewWorkShopAdapter extends BaseAdapter{
	
	private ArrayList<TechnicalSession> techSessionList = new ArrayList<TechnicalSession>();
	private Context context;
	private LayoutInflater inflater;
	private View rowView = null;
	private View rowView2 = null;
	private View rowView3 = null;
	
	public int getCount() { //Pega o tamanho da lista
		return techSessionList.size();
	}
	
	public ListViewWorkShopAdapter(Context context) { //Salva o context da activity que chamou esse adapter
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
		
		rowView = inflater.inflate(R.layout.item_list_view_workshop, viewGroup, false);
		rowView2 = inflater.inflate(R.layout.item_list_view_workshop2, viewGroup, false);
		rowView3 = inflater.inflate(R.layout.item_list_view_workshop3, viewGroup, false);
		
		String dateVerifierAnterior = (position != 0 ? techSessionList.get(position-1).getDate() : null);
		String dateVerifierAtual = techSessionList.get(position).getDate();
		
		String TimeVerifierAnterior = (position != 0 ? techSessionList.get(position-1).getBeginning() : null); //added
		String TimeVerifierAtual = techSessionList.get(position).getBeginning(); //added
		
		if(position == 0 || !dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior)){
			
			TextView textDate = (TextView)rowView.findViewById(R.id.infoDate4);
			textDate.setBackgroundColor(Color.parseColor("#b2c85c"));
			textDate.setTextColor(Color.parseColor("#FFFFFF"));
			textDate.setText(techSessionList.get(position).getDayWeek() + "  " + techSessionList.get(position).getDate());
			
			TextView textTime = (TextView)rowView.findViewById(R.id.infoTime4);
			textTime.setBackgroundColor(Color.parseColor("#b2c85c"));
			textTime.setTextColor(Color.parseColor("#FFFFFF"));
			textTime.setText(techSessionList.get(position).getBeginning() + " - " + techSessionList.get(position).getEnd());
			
			TextView infoSession = (TextView) rowView.findViewById(R.id.infoSession4);
			
			Log.e("caso 1", ""+techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			Log.e("dados 1", ""+dateVerifierAnterior + " " + dateVerifierAtual + " " + TimeVerifierAnterior + " " + TimeVerifierAtual);
			
			infoSession.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider = (View)rowView.findViewById(R.id.dividerWorkshop);
			divider.setVisibility(View.VISIBLE);
			
			
			return rowView;
		}
		 if(dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior) && !TimeVerifierAtual.equalsIgnoreCase(TimeVerifierAnterior)){
			TextView textTime2 = (TextView)rowView2.findViewById(R.id.infoTime5);
			textTime2.setBackgroundColor(Color.parseColor("#b2c85c"));
			textTime2.setTextColor(Color.parseColor("#FFFFFF"));
			textTime2.setText(techSessionList.get(position).getBeginning() + " - " + techSessionList.get(position).getEnd());
			
			TextView infoSession2 = (TextView) rowView2.findViewById(R.id.infoSession5);
			
			Log.e("caso 2", ""+techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			Log.e("dados 2", ""+dateVerifierAnterior + " " + dateVerifierAtual + " " + TimeVerifierAnterior + " " + TimeVerifierAtual);
			
			infoSession2.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider2 = (View)rowView2.findViewById(R.id.dividerWorkshop2);
			divider2.setVisibility(View.VISIBLE);
			
			return rowView2;
			
		}	
		
		else if(dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior) && TimeVerifierAtual.equalsIgnoreCase(TimeVerifierAnterior)){
			
			TextView infoSession3 = (TextView) rowView3.findViewById(R.id.infoSession6);
			
			Log.e("caso 3", ""+techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			Log.e("dados 3", ""+dateVerifierAnterior + " " + dateVerifierAtual + " " + TimeVerifierAnterior + " " + TimeVerifierAtual);
			
			infoSession3.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
			View divider3 = (View)rowView3.findViewById(R.id.dividerWorkshop3);
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
