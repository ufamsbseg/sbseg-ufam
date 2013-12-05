package br.com.ufam.instagramIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import br.com.sbseg.R;
import br.com.ufam.beans.Image;
import br.com.ufam.instagramIntegration.InstaActivity.ResponseListener;

public class InstaImpl {

	private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
	private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
	public static final String APIURL = "https://api.instagram.com/v1";
	public static String CALLBACKURL = "http://sbseg2013.icomp.ufam.edu.br/index.php/pt-br/";
	//private static final String TAG = "Instagram Demo";
	
	private SessionStore mSessionStore;
	
	private String mAuthURLString;
	private String mTokenURLString;
	private String mAccessTokenString;
	public String mClient_id;
	public String mClient_secret;
	private String mToken;
	private ArrayList <Image> arrayImage = new ArrayList <Image>(); //Objeto das imagens
	private String userIdInstagram; //ID do USER não da APLICAÇÃO!
	
	private AuthAuthenticationListener mAuthAuthenticationListener;
	
	private ProgressDialog mProgressDialog;
	private Context mContext;
	
	public InstaImpl(Context context)
	{
		mContext = context;
		mSessionStore = new SessionStore(context);
		mClient_id = context.getResources().getString(R.string.instagram_id); // Recommended: Put your Instagram ID in string class
		mClient_secret = context.getResources().getString(R.string.instagram_secret); // Recommended: Put your Instagram Secret in string class
		CALLBACKURL = context.getResources().getString(R.string.callbackurl);
		
		mAuthURLString = AUTHURL + "?client_id=" + mClient_id + "&redirect_uri=" + CALLBACKURL + "&response_type=code&display=touch&scope=likes+comments+relationships";
		mTokenURLString = TOKENURL + "?client_id=" + mClient_id + "&client_secret=" + mClient_secret + "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";
		
			InstaAuthDialogListener instaAuthDialogListener = new InstaAuthDialogListener();
		mAccessTokenString = mSessionStore.getInstaAccessToken();
		InstaLoginDialog instaLoginDialog = new InstaLoginDialog(context, mAuthURLString, instaAuthDialogListener);
		instaLoginDialog.show();
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);
		
	}
	
	public class InstaAuthDialogListener implements br.com.ufam.instagramIntegration.InstaLoginDialog.AuthDialogListener
	{

		@Override
		public void onComplete(String token) {
			getAccessToken(token);
		}

		@Override
		public void onError(String error) {
			
		}
		
	}
	
	private void getAccessToken(String token)
	{
		this.mToken = token;
		new GetInstagramTokenAsyncTask().execute();
	}
	//Utilizada para recuperar as imagens.
	public class GetInstagramImagesAsyncTask extends AsyncTask<Void, Void, Void>{ //added

		@Override
		protected Void doInBackground(Void... arg0) {
			
			Log.e("Status","AsyncTask Iniciada...");
			
			String urlString = APIURL + "/users/"+ userIdInstagram +"/media/recent/?access_token=" + mAccessTokenString;

			try{
			
				URL url = new URL(urlString);
			
				InputStream inputStream = url.openConnection().getInputStream();
			
				String response = InstaImpl.streamToString(inputStream);
		
				JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
			
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				Log.e("=P","Segunda AsyncTaks");
				for(int x = 0; x < jsonArray.length(); x++){
					Image image = new Image(null,null,null);

					//Imagens em baixa resolução
					JSONObject mainImageJsonObjectLow = jsonArray.getJSONObject(x).getJSONObject("images").getJSONObject("thumbnail");//Use for loop to traverse through the JsonArray.
					String imageUrlStringLow = mainImageJsonObjectLow.getString("url");
					image.setLowUrl(imageUrlStringLow);
					//Imagens em alta resolução	
					JSONObject mainImageJsonObjectHigh = jsonArray.getJSONObject(x).getJSONObject("images").getJSONObject("standard_resolution");//Use for loop to traverse through the JsonArray.
					String imageUrlStringHigh = mainImageJsonObjectHigh.getString("url");
					image.setHighUrl(imageUrlStringHigh);
					
					arrayImage.add(image);
					//Log.e("arrayURL: ",arrayURL.get(x));
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
				Log.e("Erro no loading da URL","");
			}
			
			return null;
		}
		
		protected void onPostExecute(Void result) {
			
			Intent i= new Intent(mContext,ImageFromURLMainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("Images", arrayImage);
			i.putExtras(bundle);
			mContext.startActivity(i);
			
		}
	}
	
	public class GetInstagramTokenAsyncTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			try 
			{
				URL url = new URL(mTokenURLString);
				HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
				httpsURLConnection.setRequestMethod("POST");
				httpsURLConnection.setDoInput(true);
				httpsURLConnection.setDoOutput(true);
				
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
				outputStreamWriter.write("client_id="+mClient_id+
						"&client_secret="+ mClient_secret +
						"&grant_type=authorization_code" +
						"&redirect_uri="+CALLBACKURL+
						"&code=" + mToken);
				
				outputStreamWriter.flush();
				
				//Response would be a JSON response sent by instagram
				String response = streamToString(httpsURLConnection.getInputStream());
				//Log.e("USER Response", response);
				JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
				
/*				JSONArray jsonArray = jsonObject.getJSONArray("data"); //added
				for(int x = 0; x < jsonArray.length(); x++){ //added
					JSONObject mainImageJsonObject = jsonArray.getJSONObject(x).getJSONObject("images").getJSONObject("low_resolution");//Use for loop to traverse through the JsonArray.
					String imageUrlString = mainImageJsonObject.getString("url");
				}*/
				
				//Your access token that you can use to make future request
				mAccessTokenString = jsonObject.getString("access_token");
				//Log.e(TAG, mAccessTokenString);
				
				//mSessionStore.saveInstaAccessToke(mAccessTokenString); //salvar o access token aqui //added
				Log.e("Primeira AsyncTask","Token Salvo2");
				//User details like, name, id, tagline, profile pic etc.
				JSONObject userJsonObject = jsonObject.getJSONObject("user");
				//Log.e("USER DETAIL", userJsonObject.toString());
				
				//User ID
				String id = userJsonObject.getString("id");
				userIdInstagram = id;
				Log.e("mClient_id", id);
				
				//Username
				String username = userJsonObject.getString("username");
				//Log.e(TAG, username);
				
				//User full name
				String name = userJsonObject.getString("full_name");
				//Log.e(TAG, name);
				mSessionStore.saveInstagramSession(mAccessTokenString, id, username, name);
				//showResponseDialog(name, mAccessTokenString);
			}
			catch (Exception e) 
			{
				mAuthAuthenticationListener.onFail("Failed to get access token");
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissDialog();
			Log.e("Status","Iniciando a nova AsyncTask...3");
			//mAuthAuthenticationListener.onSuccess();
			//chamar GetInstagramImagesAsyncTask aqui
			Log.e("Status","Iniciando a nova AsyncTask...");
			new GetInstagramImagesAsyncTask().execute();
			Log.e("Status","Iniciando a nova AsyncTask...2");
		}

		@Override
		protected void onPreExecute() {
			showDialog("Getting Access Token..");
			super.onPreExecute();
		}
		
	}
	
	public void setAuthAuthenticationListener(AuthAuthenticationListener authAuthenticationListener) {
		this.mAuthAuthenticationListener = authAuthenticationListener;
	}
	
	public interface AuthAuthenticationListener
	{
		public abstract void onSuccess();
		public abstract void onFail(String error);
	}
	
	public static String streamToString(InputStream is) throws IOException {
		String string = "";

		if (is != null) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}

				reader.close();
			} finally {
				is.close();
			}

			string = stringBuilder.toString();
		}

		return string;
	}
	
	public void showDialog(String message)
	{
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}
	
	public void dismissDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	public void showResponseDialog(String name, String accessToken) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseListener.ACTION_RESPONSE);
		broadcastIntent.putExtra(ResponseListener.EXTRA_NAME, name);
		broadcastIntent.putExtra(ResponseListener.EXTRA_ACCESS_TOKEN, accessToken);
		mContext.sendBroadcast(broadcastIntent);
	}
	
}
