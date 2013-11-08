package br.com.ufam.beans;

import java.io.Serializable;

import android.annotation.SuppressLint;


@SuppressLint("ParcelCreator")
public class Period implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String beginning;
	private String end; 
	
	public Period(String beginning, String end){
		this.beginning = beginning;
		this.end = end;
	}
	
	public void setBeginning(String beginning){
		this.beginning = beginning;
	}
	public void setEnd(String end){
		this.end = end;
	}

	public String getBeginning(){
		return beginning;
	}
	public String getEnd(){
		return end;
	}

}

