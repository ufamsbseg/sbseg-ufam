package br.com.sbseg.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.sbseg.R;
import br.com.sbseg.beans.Author;
import br.com.sbseg.beans.Paper;


public class ListViewAdapterSecondScreen extends BaseAdapter{
	
	private ArrayList<Paper> paperList = new ArrayList<Paper>();
	private Context context;
	private LayoutInflater inflater;
	private View rowView = null;

	public int getCount() { //Pega o tamanho da lista
		return paperList.size();
	}
	
	public ListViewAdapterSecondScreen(Context context) { //Salva o context da activity que chamou esse adapter
		this.context = context;
	}

	public Object getItem(int position) { //Pega um item da lista que foi jogada nesse Adapter
		return paperList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) { //Trata a exibição dos itens no ListView
			
		inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.activity_tech_session_item, viewGroup, false);
		
		TextView paperInfo = (TextView)rowView.findViewById(R.id.view_text);
		paperInfo.setText(paperList.get(position).getName());
		
		//Esse 'for' é utilizado para imprmir a lista de autores de um paper. Essa lista é impressa por textview alocado dinamicamente.
		for(Author author : paperList.get(position).getAuthors()){
			LinearLayout layoutTechSession = (LinearLayout)rowView.findViewById(R.id.activity_tech_session_item); //pega o arquivo xml do item da lista
			TextView text = new TextView(context); //cria um texto view
			text.setLayoutParams(new ViewGroup.LayoutParams( //daqui até as próximas duas linhas, é feito o layout do text view
			        ViewGroup.LayoutParams.MATCH_PARENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT));
			text.setText(author.getName() + "  " + "(" + author.getAffiliation() + "," + author.getCountry() + ")"); //preenche o text view
			layoutTechSession.addView(text); //insere o text view
/*			TextView divider = new TextView(context); //cria um texto view
			divider.setLayoutParams(new ViewGroup.LayoutParams( //daqui até as próximas duas linhas, é feito o layout do text view
			        ViewGroup.LayoutParams.MATCH_PARENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT));
			divider.setHint("-----------------------------------------------------------"); //preenche o text view
			layoutTechSession.addView(divider); */
		}
		
		
		return rowView;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
		
	public void setData(ArrayList<Paper> paperList) { //Seta o tipo de dado que será trabalhado pelo adapter.
		this.paperList.addAll(paperList);
		notifyDataSetChanged();
	}

}
