package br.com.ufam.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.sbseg.R;

public class GridViewAdapter extends BaseAdapter{
 
	private Context context;
	private ArrayList<String> data;
	int layoutResourceId;

	 public GridViewAdapter(Context context, int layoutResourceId) {
		  this.layoutResourceId = layoutResourceId;
		  this.context = context;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  View row = convertView;
	  
	  TextView text = null;
	  ImageView image = null;
	
	  
	   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	   row = inflater.inflate(layoutResourceId, parent, false);
	
	   text = (TextView) row.findViewById(R.id.text);
	  image = (ImageView) row.findViewById(R.id.image); 
	  
//	  text.setText(data.get(position)); //Null pointer exception
	 // Log.e("teste",""+ data);
//	  image.setImageBitmap(null);
	  return row;
	
	 }
	 
	 public void setData(ArrayList<String> data){
		 this.data = data;
		 notifyDataSetChanged();
	 }

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

}