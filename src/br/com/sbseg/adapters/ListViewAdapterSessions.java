package br.com.sbseg.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.beans.TechnicalSession;

public class ListViewAdapterSessions extends BaseAdapter{
	
	private ArrayList<TechnicalSession> techSessionList = new ArrayList<TechnicalSession>();
	private Context context;
	private LayoutInflater inflater;
	private View rowView = null;
	
	public int getCount() { //Pega o tamanho da lista
		return techSessionList.size();
	}
	
	public ListViewAdapterSessions(Context context) { //Salva o context da activity que chamou esse adapter
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
		
		rowView = inflater.inflate(R.layout.item_list_view_calendar, viewGroup, false);

		String dateVerifierAnterior = (position != 0 ? techSessionList.get(position-1).getDate() : null);
		String dateVerifierAtual = techSessionList.get(position).getDate();
		
		if(position == 0 || !dateVerifierAtual.equalsIgnoreCase(dateVerifierAnterior)){
			
			TextView text = (TextView)rowView.findViewById(R.id.infoDate);
			text.setText(techSessionList.get(position).getDayWeek() + "  " + techSessionList.get(position).getDate() + " " + techSessionList.get(position).getBeginning() + " - " + techSessionList.get(position).getEnd());
		}
		
		TextView infoSession = (TextView) rowView.findViewById(R.id.infoSession);
		
		infoSession.setText(techSessionList.get(position).getId() + ": " + techSessionList.get(position).getName());
		
		return rowView;
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