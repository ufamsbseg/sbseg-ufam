package br.com.ufam.instagramIntegration;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import br.com.sbseg.R;
import br.com.ufam.beans.Image;

public class ImageAdapterGridViewHashMapVersion extends BaseAdapter{
	
	private LayoutInflater mInflater;
    public HashMap<String, Image> hashMapImagesObject;
//    public ArrayList<Image> teste;
    String[] arrayOfkeys;
    //O Array de strings recebido cotem as urls de baixa resoulução. Foram com essas URLs de baixa resolução que as chaves do hashmap foram montadas
    public ImageAdapterGridViewHashMapVersion(Context context, HashMap<String, Image> hashMapImagesObject, String[] arrayOfkeys)
    {
        //Itens que preencherão o listview
        this.hashMapImagesObject = hashMapImagesObject;
        this.arrayOfkeys = arrayOfkeys;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

	public int getCount() {
		// TODO Auto-generated method stub
		return hashMapImagesObject.size();
	}
	//Não é possível pegar um item da hashmap por uma posição, só é possível pegá-lo com uma chave.
	//Esse método retorna uma url de baixa resolução (chave do hashmap) que está em uma posição do arrayOfKeys.
	public String getKeyAtPosition(int position){
		return arrayOfkeys[position];
	}
	
	//Esse método não funciona. O get() precisa de uma chave.
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hashMapImagesObject.get(getKeyAtPosition(position)); 
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//Pega o item de acordo com a posção.
       // Bitmap image = arrayListImages.get(position);
        //infla o layout para podermos preencher os dados
        View view = mInflater.inflate(R.layout.item_gridview, null);
 
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
     
        
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        imageView.setImageBitmap(hashMapImagesObject.get(getKeyAtPosition(position)).getImage());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
        return imageView;
 
     //   return view;
		
	}

	
	public void setData(HashMap<String, Image> hashMapImagesObject){
		this.hashMapImagesObject = hashMapImagesObject;
	}
}
