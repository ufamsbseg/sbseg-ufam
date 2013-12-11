package br.com.ufam.beans;


import java.io.Serializable;

import android.annotation.SuppressLint;


@SuppressLint("ParcelCreator")
public class Day implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String dayWeek;
	
	public Day(String date, String dayWeek){
		this.date = date;
		this.dayWeek = dayWeek;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	public void setDayWeek(String dayWeek){
		this.dayWeek = dayWeek;
	}

	public String getDate(){
		return date;
	}
	public String getDayWeek(){
		return dayWeek;
	}
}
