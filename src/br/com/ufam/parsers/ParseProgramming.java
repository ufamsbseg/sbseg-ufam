package br.com.ufam.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;



public class ParseProgramming {
	
	private static ArrayList<String> arrayProgramming;
	private static InputStream is;
	private static String diaD[];
	private static String tag;
	
	 public static ArrayList<String> parse(Context context, final String d) throws IOException, SAXException {
	       
	        final RootElement root = new RootElement("calendario");
	        
	        arrayProgramming = new ArrayList<String>();
	        is = context.getResources().getAssets().open("programming.xml");
	        
	        //inicia tag 'root'
	        root.setStartElementListener(new StartElementListener() {				
				public void start(Attributes attrb) {}
			});
	        
	        Element dias = root.getChild("dias");
	        
	        //inicia tag 'dias'
	        dias.setStartElementListener(new StartElementListener() {
				public void start(Attributes attr) {}
			});
	        
	        	Element dia = dias.getChild("dia");
	        	
	        	//inicia tag 'dia'
	        	dia.setStartElementListener(new StartElementListener() {
					public void start(Attributes attr) {}
				});
		        
		        dia.getChild("data").setEndTextElementListener(new EndTextElementListener() {
					public void end(String data) {
						diaD = data.split("/");
						//arrayProgramming.add(data);
					}
				});
		        
		        
			        Element evento = dia.getChild("eventos").getChild("evento");
			        
			        //inicia tag 'eventos'
			        evento.setStartElementListener(new StartElementListener() {
						public void start(Attributes attr) {
							if (diaD[0].equals(d)){
								String duracao = attr.getValue("duracao");
								arrayProgramming.add(duracao);
							}
						}
					});
			        
			        	Element nome = evento.getChild("nomes").getChild("nome");
			        	
			        	//inicia nome
			        	nome.setStartElementListener(new StartElementListener() {
							public void start(Attributes attr) {
								if (diaD[0].equals(d)){
									tag = attr.getValue("tag");
								}
							}
						});
			        	
			        	nome.setEndTextElementListener(new EndTextElementListener() {
							public void end(String nome) {
								if (diaD[0].equals(d)){
									arrayProgramming.add("*"+tag+"#"+nome);
								}
							}
						});
			        	
			        	//finaliza nome
			        	nome.setEndElementListener(new EndElementListener() {
							public void end() {}
						});
			        
			        //finaliza tag 'eventos'
			        evento.setEndElementListener(new EndElementListener() {
						public void end() {}
					});
		        
		        
		        //finaliza tag 'dia'
		        dia.setEndElementListener(new EndElementListener() {
					public void end() {}
				});
	        
		    //finaliza tag 'dias'
	        dias.setEndElementListener(new EndElementListener() {
				
				public void end() {
				}
			});
	        
	        //finaliza tag 'root'
	        root.setEndElementListener(new EndElementListener() {
				public void end() {}
			});
	        
            Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());
            
	        return  arrayProgramming;
	    }

}
