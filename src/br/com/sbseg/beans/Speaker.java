package br.com.sbseg.beans;

import java.io.Serializable;

import android.annotation.SuppressLint;


@SuppressLint("ParcelCreator")
public class Speaker implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String affiliation;
	private String biography;
	
	public Speaker(String name, String affiliation, String biography){
		this.name = name;
		this.affiliation = affiliation;
		this.biography = biography;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getAffiliation(){
		return this.affiliation;
	}
	
	public void setAffiliation(String affiliation){
		this.affiliation = affiliation;
	}
	
	public String getBiography(){
		return this.biography;
	}
	
	public void setBiography(String biography){
		this.biography = biography;
	}

}
