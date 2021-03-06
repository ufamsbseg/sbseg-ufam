package br.com.ufam.instagramIntegration;

import br.com.sbseg.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class InstaActivity extends Activity /*implements OnClickListener*/ {
	
//	private Button mButton;
	private InstaImpl mInstaImpl;
	private Context mContext;
	private ResponseListener mResponseListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstaImpl = new InstaImpl(this);
		mInstaImpl.setAuthAuthenticationListener(new AuthListener());
//		mContext = this;
//		setContentView(R.layout.activity_insta);
//		mButton = (Button)findViewById(R.id.button1);
//		mButton.setOnClickListener(this);
//		mResponseListener = new ResponseListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_insta, menu);
		return true;
	}

//	@Override
//	public void onClick(View arg0) {
//		mInstaImpl = new InstaImpl(this);
//		mInstaImpl.setAuthAuthenticationListener(new AuthListener());
//	}
	
	public class AuthListener implements br.com.ufam.instagramIntegration.InstaImpl.AuthAuthenticationListener
	{
		@Override
		public void onSuccess() {
			Log.e("Insta","Token Salvo4");
			Toast.makeText(InstaActivity.this, "Instagram Authorization Successful", Toast.LENGTH_SHORT).show();
			Log.e("Insta","Token Salvo3");
		}

		@Override
		public void onFail(String error) {
			Toast.makeText(InstaActivity.this, "Authorization Failed", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
        filter.addAction("com.varundroid.instademo.responselistener");
        filter.addCategory("com.varundroid.instademo");
        registerReceiver(mResponseListener, filter);
        finish();
	}
	
//	@Override
//	protected void onPause() {
//		unregisterReceiver(mResponseListener);
//		super.onPause();
//	}
	
	public class ResponseListener extends BroadcastReceiver {
		
		public static final String ACTION_RESPONSE = "com.varundroid.instademo.responselistener";
		public static final String EXTRA_NAME = "90293d69-2eae-4ccd-b36c-a8d0c4c1bec6";
		public static final String EXTRA_ACCESS_TOKEN = "bed6838a-65b0-44c9-ab91-ea404aa9eefc";

		@Override
		public void onReceive(Context context, Intent intent) {
			mInstaImpl.dismissDialog();
			Bundle extras = intent.getExtras();
			String name = extras.getString(EXTRA_NAME);
			String accessToken = extras.getString(EXTRA_ACCESS_TOKEN);
			final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			alertDialog.setTitle("Your Details");
			alertDialog.setMessage("Name - " + name + ", Access Token - " + accessToken);
			alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			alertDialog.show();
		}
	}
}
