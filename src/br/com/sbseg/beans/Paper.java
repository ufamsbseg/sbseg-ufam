package br.com.sbseg.beans;

import java.io.Serializable;
import java.util.ArrayList;


public class Paper implements Serializable{

	private static final long serialVersionUID = 1L; //added por causa do "implements Serializable"
	private String name;
	private ArrayList<Author> authors;
	private String beginning;
	private String end;
	
	public void setName(String name){
		this.name = name;
	}
	public void setAuthors(ArrayList<Author> authors){
		this.authors = authors;
	}
	public void setBeginning(String beginning){
		this.beginning = beginning;
	}
	public void setEnd(String end){
		this.end = end;
	}
	
	public String getName(){
		return name;
	}
	public ArrayList<Author> getAuthors(){
		return authors;
	}
	public String getBeginning(){
		return beginning;
	}
	public String getEnd(){
		return end;
	}
}
