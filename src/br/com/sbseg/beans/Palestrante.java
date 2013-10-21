package br.com.sbseg.beans;

import java.io.Serializable;

public class Palestrante implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String filiation;
	private Talk talk;
	private String biography;
	private String photo;
	
	public Palestrante(){}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getFiliation() {
		return filiation;
	}
	public void setFiliation(String filiation) {
		this.filiation = filiation;
	}
	
	
	public Talk getTalk() {
		return talk;
	}
	public void setTalk(Talk talk) {
		this.talk = talk;
	}
	
	
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	
	}

	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
