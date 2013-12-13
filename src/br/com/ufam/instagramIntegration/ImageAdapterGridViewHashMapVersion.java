package br.com.ufam.instagramIntegration;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
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
    private Context mContext;
    private int width;
	private int height;
	private GifWebView gifWebView;
//    public ArrayList<Image> teste;
    String[] arrayOfkeys;
   
    //O Array de strings recebido cotem as urls de baixa resoulução. Foram com essas URLs de baixa resolução que as chaves do hashmap foram montadas
    public ImageAdapterGridViewHashMapVersion(Context context, HashMap<String, Image> hashMapImagesObject, String[] arrayOfkeys)
    {
        //Itens que preencherão o listview
        this.hashMapImagesObject = hashMapImagesObject;
        this.arrayOfkeys = arrayOfkeys;
        this.mContext = context;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(mContext);
    }

    public void setGifWebView(GifWebView gifWebView){
    	this.gifWebView = gifWebView;
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
		//gifWebView = new GifWebView(mContext, "file:///android_asset/loader.gif");
		
        View view = mInflater.inflate(R.layout.item_gridview, null);
        
        //View view2 = mInflater.inflate(R.layout.item_gridview2,null);
          
        
        view.setTag(R.id.imageView1, view.findViewById(R.id.imageView1));
        
       // view2.setTag(R.id.gifImageView, view2.findViewById(R.id.gifImageView));
        
        ImageView imageView = (ImageView)view.getTag(R.id.imageView1);
        //gifWebView = (GifWebView)view2.getTag(R.id.gifImageView);
        
        
        Bitmap imagem = hashMapImagesObject.get(getKeyAtPosition(position)).getImage();
        
        if(width > height){
        	imageView.setLayoutParams(new GridView.LayoutParams(height/3, width/3));
        	//gifWebView.setLayoutParams(new GridView.LayoutParams(height/3, width/3));
        }
        else{
        	imageView.setLayoutParams(new GridView.LayoutParams(width/3, height/3));
        	//gifWebView.setLayoutParams(new GridView.LayoutParams(width/3, height/3));
        }
        
        if(imagem == null)
        	return gifWebView;
        else{
        	imageView.setImageBitmap(imagem);
        	return imageView;
        }
        
        
        
 
     //   return view;
		
	}

	
	public void setData(HashMap<String, Image> hashMapImagesObject, int width, int height){
		this.hashMapImagesObject = hashMapImagesObject;
		this.width = width;
		this.height = height;
	}
}
