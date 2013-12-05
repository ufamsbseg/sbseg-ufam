package br.com.ufam.beans;


import java.io.Serializable;

public class Chair implements Serializable{

	private static final long serialVersionUID = 1L; //added por causa do "implements Serializable"
	protected String name;
	protected String affiliation;
	
	public void setName(String name){
		this.name = name;
	}
	public void setAffiliation(String affiliation){
		this.affiliation = affiliation;
	}
	
	public String getName(){
		return name;
	}
	public String getAffiliation(){
		return affiliation;
	}	
}
