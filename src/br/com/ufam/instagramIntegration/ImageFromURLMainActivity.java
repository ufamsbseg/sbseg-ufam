package br.com.ufam.instagramIntegration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import br.com.sbseg.R;
import br.com.ufam.beans.Image;

public class ImageFromURLMainActivity extends Activity {

	//public ArrayList<Bitmap> DownloadedImagesFromURL = new ArrayList<Bitmap>();
	public ImageAdapterGridView adapterGridImage;
	public ImageAdapterGridViewHashMapVersion adapterGridImageHashMapVersion; 
    public Context context;
    private GridView gridView;
    private ProgressDialog mProgressDialog;
    private ArrayList<Image> ArrayImagesObject = new ArrayList<Image>();
    private HashMap<String, Image> hashMapImagesObject = new HashMap<String, Image>(); //added for test
    private Bundle bundle;
    
    
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_from_urlmain); 
		
		context = this;
		mProgressDialog = new ProgressDialog(context);
		bundle = getIntent().getExtras();
		hashMapImagesObject = (HashMap<String, Image>)bundle.getSerializable("Images");
//		ArrayImagesObject = (ArrayList<Image>)bundle.getSerializable("Images");
		ArrayList<String> arrayURLs = objectImageToStringArray(hashMapImagesObject);
		String[] urlsArray = arrayURLs.toArray(new String[arrayURLs.size()]); //Trasformando o arraylist em vetor de string
		
		
        // Create an object for subclass of AsyncTask
        GetXMLTask task = new GetXMLTask();
        
        // Execute the task
        task.execute(urlsArray);
        
        gridView = (GridView) findViewById(R.id.grid_view);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), FullImageMainActivity.class);
                Bundle bundle = new Bundle();
                Image image = (Image)adapterGridImageHashMapVersion.getItem(position); //problema aqui. só é possível recuperar itens por chave, não por posição
    			bundle.putString("images", image.getHighUrl());
    			i.putExtras(bundle);
                startActivity(i);
            }
        });
	        
	}
	
	public ArrayList<String> objectImageToStringArray(HashMap<String, Image> hashMapImagesObject){
	
	ArrayList<String> arrayUrl = new ArrayList<String>(); 
	Iterator<String> i = hashMapImagesObject.keySet().iterator(); // É como se fosse o índice do for
	
	try{
	
		while(i.hasNext()){
			String key = i.next();
			arrayUrl.add(hashMapImagesObject.get(key).getLowUrl());
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return arrayUrl;
}

	
//	public ArrayList<String> objectImageToStringArray(ArrayList<Image> ImageArray){
//		
//		ArrayList<String> arrayUrl = new ArrayList<String>(); 
//		
//		try{
//		
//			for(Image image : ImageArray){
//				arrayUrl.add(image.getLowUrl());
//				
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return arrayUrl;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_from_urlmain, menu);
		return true;
		
	}
		 
		    private class GetXMLTask extends AsyncTask<String, Void, String[]> { //O download da imagem é feito aqui
		        @Override
		        protected String[] doInBackground(String... urls) {
		            Bitmap map = null;
		            DownloadImage download= new DownloadImage();
		            for (int i = 0; i < urls.length; i++) {
		                map = download.downloadImage(urls[i]);
		               // map = scaleDownBitmap(map, 20, context);
		                hashMapImagesObject.get(urls[i]).setImage(map);
		               // ArrayImagesObject.get(i).setImage(map); //Adiciona a imagem baixada (e já convertida em um bitmap) nesse Array de bitmap
		            }
		            return urls; //Esse vetor de array vai conter as chaves no hashmap
		        }
		      
		        
		        @Override
				protected void onPreExecute() {
					showDialog("Aguarde, carregando fotos...");
					super.onPreExecute();
				}
		      
		        // Sets the Bitmap returned by doInBackground
		        @Override
		        protected void onPostExecute(String[] arrayOfkeys) {
		        	dismissDialog();
		        	//Cria o adapter
		        	adapterGridImageHashMapVersion = new ImageAdapterGridViewHashMapVersion(context,hashMapImagesObject, arrayOfkeys);
//		        	adapterGridImage = new ImageAdapterGridView(context,ArrayImagesObject);
		        	//Define o Adapter
		            gridView.setAdapter(adapterGridImageHashMapVersion);
		        }
		 
		 
		        public void showDialog(String message){
		    		mProgressDialog.setMessage(message);
		    		mProgressDialog.show();
		    	}
		        
		        public void dismissDialog(){
		    		if (mProgressDialog.isShowing()) {
		    			mProgressDialog.dismiss();
		    		}
		    	}
		        
				@SuppressWarnings("unused")
				public Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) { //added

		        	final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		        	int h= (int) (newHeight*densityMultiplier);
		        	int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		        	photo=Bitmap.createScaledBitmap(photo, w, h, true);

		        	return photo;
		        	}
		    }
}
