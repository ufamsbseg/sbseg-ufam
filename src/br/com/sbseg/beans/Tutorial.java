package br.com.sbseg.beans;

import java.io.Serializable;
import java.util.ArrayList;

import android.annotation.SuppressLint;

@SuppressLint("ParcelCreator")
public class Tutorial implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Day day;
	private Period period;
	private ArrayList<Speaker> speakers;
	private String theme;
	private String id;
	private String description;
	
	public Day getDay(){
		return this.day;
	}
	
	public void setDay(Day day){
		this.day = day;
	}
	
	public Period getPeriod(){
		return this.period;
	}
	
	public void setPeriod(Period period){
		this.period = period;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getTheme(){
		return this.theme;
	}
	
	public void setTheme(String theme){
		this.theme = theme;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public ArrayList<Speaker> getSpeakers(){
		return this.speakers;
	}
	
	public void addSpeaker(Speaker speaker){
		speakers.add(speaker);
	}
	
	public void setSpeakers(ArrayList<Speaker> speakers){
		this.speakers = speakers;
	}
	
}

