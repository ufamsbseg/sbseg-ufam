package br.com.ufam.beans;


import java.io.Serializable;

public class Author implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //added por causa do "implements Serializable"
	private String name;
	private String affiliation;
	private String country;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAffiliation(String affiliation){
		this.affiliation = affiliation;
	}
	public void setCountry(String country){
		this.country = country;
	}
	public String getName(){
		return this.name;
	}
	public String getAffiliation(){
		return this.affiliation;
	}
	public String getCountry(){
		return this.country;
	}	
}
