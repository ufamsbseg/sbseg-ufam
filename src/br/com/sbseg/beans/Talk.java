package br.com.sbseg.beans;


import java.io.Serializable;


public class Talk implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String resume;

	public Talk(){}
	

	public void setTitle(String title){
		this.title = title;
	}

	public void setResume(String resume){
		this.resume = resume;
	}

	public String getTitle(){
		return this.title;
	}

	public String getResume(){
		return this.resume;
	}

}
