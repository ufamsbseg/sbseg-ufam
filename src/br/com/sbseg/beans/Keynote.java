package br.com.sbseg.beans;


import java.io.Serializable;
import java.util.ArrayList;

public class Keynote implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String data;
	private String weekday;
	private ArrayList<Session> listSession;
	
	public Keynote(){}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getWeekday() {
		return weekday;
	}
	
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	public ArrayList<Session> getListSession() {
		return listSession;
	}
	
	public void setSession(Session session){
		this.listSession.add(session);
	}
	
	public void setListSession(ArrayList<Session> listSession) {
		this.listSession = listSession;
	}
}
