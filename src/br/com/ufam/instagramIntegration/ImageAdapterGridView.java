package br.com.ufam.instagramIntegration;

import java.util.ArrayList;

import br.com.sbseg.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterGridView extends BaseAdapter {
	
	private LayoutInflater mInflater;
    public ArrayList<Bitmap> arrayListImages;
 
    public ImageAdapterGridView(Context context, ArrayList<Bitmap> arrayImages)
    {
        //Itens que preencherão o listview
        this.arrayListImages = arrayImages;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListImages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayListImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//Pega o item de acordo com a posção.
       // Bitmap image = arrayListImages.get(position);
        //infla o layout para podermos preencher os dados
        View view = mInflater.inflate(R.layout.item_gridview, null);
 
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
     
        
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        imageView.setImageBitmap(arrayListImages.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
        return imageView;
 
     //   return view;
		
	}
	
}
