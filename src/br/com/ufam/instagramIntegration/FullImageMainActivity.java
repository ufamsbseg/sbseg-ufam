package br.com.ufam.instagramIntegration;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
			
			DownloadImage download = new DownloadImage();
	        Bitmap image = download.downloadImage(urlHigh);
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
}
