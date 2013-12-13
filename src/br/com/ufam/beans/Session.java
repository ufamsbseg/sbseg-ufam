package br.com.ufam.beans;


import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String start;
	private String end;
	private ArrayList<Palestrante> listSpeaker;
	
	public Session(){}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public ArrayList<Palestrante> getListSpeaker() {
		return listSpeaker;
	}
	public void setListSpeaker(ArrayList<Palestrante> listSpeaker) {
		this.listSpeaker = listSpeaker;
	}
	
	public void setSpeaker(Palestrante speaker) {
		this.listSpeaker.add(speaker);
	}
}
