package br.com.ufam.instagramIntegration;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionStore {
	
	private static final String FILE_NAME = "InstaSessionStore";
	private static final String ACCESS_TOKEN_KEY = "Access_Token";
	private Context mContext;
	
	public SessionStore(Context context)
	{
		this.mContext = context;
	}
	
	public SharedPreferences getSharedPreferences()
	{
		return mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}
	
	public void saveInstaAccessToke(String accessToken) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(ACCESS_TOKEN_KEY, accessToken);
		editor.commit();
		Log.e("Token Salvo","");
	}
	
	public String getInstaAccessToken() {
		return getSharedPreferences().getString(ACCESS_TOKEN_KEY, null);
	}
	
	public String getInstaId(){
		return getSharedPreferences().getString("Api_Id", null);
	}
	
	public void saveInstagramSession(String token, String id, String username, String name)
	{
		Editor editor = getSharedPreferences().edit();
		editor.putString("Api_Id", id);
		editor.putString("Api_name", name);
		editor.putString("Api_access_token", token);
		editor.putString("Api_user_name", username);
		editor.commit();
	}
	
	//If you have other things saved in SharedPreference, clear things like this.
	
	public void resetInstagram()
	{
		Editor editor = getSharedPreferences().edit();
		editor.remove("Api_Id");
		editor.remove("Api_name");
		editor.remove("Api_access_token");
		editor.remove("Api_user_name");
		editor.remove("Api_email");
		editor.commit();
	}
	
	//If you have only Instagram value saved in SharedPreference, you can reset using this function as well.
	
	public void resetInstagream(){
		Editor editor = getSharedPreferences().edit();
		editor.clear();
		editor.commit();
	}

}
