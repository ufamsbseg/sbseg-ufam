package br.com.ufam.instagramIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.sbseg.R;
import br.com.ufam.beans.Image;

public class ImageFromURLMainActivity extends Activity {

	public ImageAdapterGridView adapterGridImage;
	public ImageAdapterGridViewHashMapVersion adapterGridImageHashMapVersion; 
    public Context context;
    private GridView gridView;
    private ProgressDialog mProgressDialog;
    private HashMap<String, Image> hashMapImagesObject = new HashMap<String, Image>(); //added for test
    private Bundle bundle;
    private String[] urlsArray;
    private int width;
	private int height;
    
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_from_urlmain); 

		
		

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		context = this;
		mProgressDialog = new ProgressDialog(context);
		bundle = getIntent().getExtras();
		hashMapImagesObject = (HashMap<String, Image>)bundle.getSerializable("Images");
		ArrayList<String> arrayURLs = objectImageToStringArray(hashMapImagesObject);
		urlsArray = arrayURLs.toArray(new String[arrayURLs.size()]); //Trasformando o arraylist em vetor de string
        
        gridView = (GridView) findViewById(R.id.grid_view);
        
        //gridView.setScaleY(1);
        for (int i = 0; i < urlsArray.length; i++) {
        	Bitmap teste = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
        	hashMapImagesObject.get(urlsArray[i]).setImage(teste);
        }
        adapterGridImageHashMapVersion = new ImageAdapterGridViewHashMapVersion(context,hashMapImagesObject, urlsArray);
        gridView.setAdapter(adapterGridImageHashMapVersion);
        //adapterGridImageHashMapVersion.setGifWebView(gifView);
        for (int i = 0; i < urlsArray.length; i++) {
        	 GetXMLTask task = new GetXMLTask();
        	 task.execute(urlsArray[i]);
        }
        
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_from_urlmain, menu);
		return true;
		
	}
		 
		    private class GetXMLTask extends AsyncTask<String, Void, String[]> { //O download da imagem é feito aqui
		        @Override
		        protected String[] doInBackground(String... urls) {
		        	String teste = urls[0];
		        	//Toast.makeText(context, teste,Toast.LENGTH_LONG).show();
		        	Bitmap bitmap = null;
		            InputStream stream = null;
		            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		            bmOptions.inSampleSize = 1;
		            try {
		                stream = getHttpConnection(teste);
		                bitmap = BitmapFactory.
		                        decodeStream(stream, null, bmOptions);
		                stream.close();
		            } catch (IOException e1) {
		                e1.printStackTrace();
		            }
		            hashMapImagesObject.get(teste).setImage(bitmap);//Adiciona a imagem baixada (e já convertida em um bitmap) no obejto "Image"
//		            }
		            return urls; //Esse vetor de array vai conter as chaves no hashmap
		        }
		      
		        
		        @Override
				protected void onPreExecute() {
					showDialog("Aguarde, carregando fotos...");
					super.onPreExecute();
				}
		    
		        @Override
		        protected void onPostExecute(String[] arrayOfkeys) {
		        	dismissDialog();
		        	adapterGridImageHashMapVersion.setData(hashMapImagesObject, width,height);
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
		       
				
				private InputStream getHttpConnection(String urlString)throws IOException {
			        InputStream stream = null;
			        URL url = new URL(urlString);
			        URLConnection connection = url.openConnection();

			        try {
			            HttpURLConnection httpConnection = (HttpURLConnection) connection;
			            httpConnection.setRequestMethod("GET");
			            httpConnection.connect();
			            
			            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			                stream = httpConnection.getInputStream();
			            }
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			        return stream;
				}
		    }
}
