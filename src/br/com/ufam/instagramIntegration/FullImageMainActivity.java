package br.com.ufam.instagramIntegration;


import java.util.ArrayList;

import br.com.sbseg.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class FullImageMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_main);
		
		// get intent data
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        ArrayList<Bitmap> images = (ArrayList <Bitmap>) bundle.getSerializable("images");
        
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapterGridView imageAdapter = new ImageAdapterGridView(this,images);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageBitmap(imageAdapter.arrayListImages.get(position));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image_main, menu);
		return true;
	}

}
