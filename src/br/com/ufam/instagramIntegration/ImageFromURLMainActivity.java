package br.com.ufam.instagramIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import br.com.sbseg.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageFromURLMainActivity extends Activity {

	ImageView imageView;
	ArrayList<Bitmap> DownloadedImagesFromURL = new ArrayList<Bitmap>();
	ImageAdapterGridView adapterGridImage;
    Context context;
    GridView gridView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		Bundle bundle = getIntent().getExtras();
		ArrayList<String> urls = bundle.getStringArrayList("URLs");
		String[] urlsArray = urls.toArray(new String[urls.size()]); //Trasformando o arraylist em vetor de string
		setContentView(R.layout.activity_image_from_urlmain); 
		
	        // Create an object for subclass of AsyncTask
	        GetXMLTask task = new GetXMLTask();
	        // Execute the task
	       // task.execute(new String[] { URL });
	        task.execute(urlsArray);
	        
	      //  listViewImages = (ListView)findViewById(R.id.listView1);
	        
	        gridView = (GridView) findViewById(R.id.grid_view);
	        
	        gridView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View v,
	                    int position, long id) {
	 
	                // Sending image id to FullScreenActivity
	                Intent i = new Intent(getApplicationContext(), FullImageMainActivity.class);
	                // passing array index
	                i.putExtra("id", position);
	                Bundle bundle = new Bundle();
	    			bundle.putSerializable("images", DownloadedImagesFromURL);
	    			i.putExtras(bundle);
	                startActivity(i);
	            }
	        });
	        
	        // Instance of ImageAdapter Class
	       // gridView.setAdapter(new ImageAdapter(this));
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_from_urlmain, menu);
		return true;
		
	}
		 
		    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
		        @Override
		        protected Bitmap doInBackground(String... urls) {
		            Bitmap map = null;
		            for (String url : urls) {
		                map = downloadImage(url);
		                DownloadedImagesFromURL.add(map); //Adiciona a imagem baixada (e já convertida em um bitmap) nesse Array de bitmap
		            }
		            return null;
//		            return map;
		        }
		      
		      
		        // Sets the Bitmap returned by doInBackground
		        @Override
		        protected void onPostExecute(Bitmap result) {
		        	//Cria o adapter
		        	adapterGridImage = new ImageAdapterGridView(context,DownloadedImagesFromURL);
		     
		            //Define o Adapter
		            gridView.setAdapter(adapterGridImage);
		            //Cor quando a lista é selecionada para ralagem.
		            //listViewImages.setCacheColorHint(Color.TRANSPARENT);
		            
		        }
		 
		        // Creates Bitmap from InputStream and returns it
		        private Bitmap downloadImage(String url) {
		            Bitmap bitmap = null;
		            InputStream stream = null;
		            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		            bmOptions.inSampleSize = 1;
		 
		            try {
		                stream = getHttpConnection(url);
		                bitmap = BitmapFactory.
		                        decodeStream(stream, null, bmOptions);
		                stream.close();
		            } catch (IOException e1) {
		                e1.printStackTrace();
		            }
		            return bitmap;
		        }
		 
		        // Makes HttpURLConnection and returns InputStream
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
