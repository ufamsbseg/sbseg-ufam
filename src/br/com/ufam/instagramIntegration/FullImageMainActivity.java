package br.com.ufam.instagramIntegration;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import br.com.sbseg.R;

public class FullImageMainActivity extends Activity {
	
	private String urlHigh;
	private ProgressDialog mProgressDialog;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_main);
		
		// get intent data
		context = this;
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        urlHigh = bundle.getString("images");
        mProgressDialog = new ProgressDialog(context); 
        DownloadImageAsyncTask test = new DownloadImageAsyncTask();
        test.execute();
        
//        Image image = 
//        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
//        imageView.setImageBitmap(image);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image_main, menu);
		return true;
	}

	
	public  class DownloadImageAsyncTask extends AsyncTask<Void, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(Void... url) {
			// TODO Auto-generated method stub
			
	        Bitmap image = downloadImage(urlHigh);
			return image;
		}
		protected void onPostExecute(Bitmap result) {
			dismissDialog();
			Bitmap image =  result;
	        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
	        imageView.setImageBitmap(image);
        }
		
		protected void onPreExecute() {
			showDialog("Abrindo foto...");
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
	
	
	
	public Bitmap downloadImage(String url) {
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
	
}
