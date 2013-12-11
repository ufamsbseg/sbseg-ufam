package br.com.ufam.beans;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Image implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String lowUrl;
	private String highUrl;
	private Bitmap image;
	
	public Image (String lowUrl, String highUrl, Bitmap image){
		this.lowUrl=lowUrl;
		this.highUrl=highUrl;
		this.image=image;
	}
	public String getLowUrl() {
		return lowUrl;
	}
	public void setLowUrl(String lowUrl) {
		this.lowUrl = lowUrl;
	}
	public String getHighUrl() {
		return highUrl;
	}
	public void setHighUrl(String highUrl) {
		this.highUrl = highUrl;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	

}
