package br.com.sbseg.parsers;

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
import br.com.sbseg.beans.Day;
import br.com.sbseg.beans.Period;
import br.com.sbseg.beans.Speaker;
import br.com.sbseg.beans.Tutorial;

public class TutorialsParse {
	
	private static Tutorial tutorial;
	private static Day day;
	private static Period period;
	private static Speaker speaker;
	private static ArrayList<Speaker> arraySpeakers;
	private static ArrayList<Tutorial> arrayTutorials;
	private static InputStream is;
	private static String id;
	
	 public static ArrayList<Tutorial> parse(Context context) throws IOException, SAXException {
	       
	        final RootElement root = new RootElement("tutorials");
	        
	        arrayTutorials = new ArrayList<Tutorial>();
	        is = context.getResources().getAssets().open("MiniCursos.xml");
	        
	        // use setStartElementListener para ter acesso aos atributos do item atual 
	        root.setStartElementListener(new StartElementListener() {				
				public void start(Attributes attrb) {
				}
			});
	        
	        //pega referência de um item 
	        Element item = root.getChild("tutorial");
	        
	        //agora vamos pegar todos os valores que estão dentro do dia
	        //sempre inicie com esse método no item
	        item.setStartElementListener(new StartElementListener() {
				
				public void start(Attributes attr) {
					//aproveite e instancie aqui um novo objeto para armazar as informações desse item
					id = attr.getValue("id");
					tutorial = new Tutorial();
					tutorial.setId(id);
				}
			});
	        
	      //pega os dados do dia
	        Element itemDay = item.getChild("day");
	        itemDay.setStartElementListener(new StartElementListener() {				
				public void start(Attributes attrb) {
					day = new Day(null, null);
				}
			});
	        
	        itemDay.getChild("date").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String date) {
	            	day.setDate(date);
	            }
	        });
	        
	        itemDay.getChild("dayWeek").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String dayWeek) {
	            	day.setDayWeek(dayWeek);
	            }
	        });
	        
	        itemDay.setEndElementListener(new EndElementListener() {	
				public void end() {
					tutorial.setDay(day);
				}
			});
	        
	      //pega os dados do periodo
	        Element itemPeriod = item.getChild("period");
	        itemPeriod.setStartElementListener(new StartElementListener() {				
				public void start(Attributes attrb) {
					period = new Period(null, null);
				}
			});
	        
	        itemPeriod.getChild("beginning").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String beginning) {
	            	period.setBeginning(beginning);
	            }
	        });
	        
	        itemPeriod.getChild("end").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String end) {
	            	period.setEnd(end);
	            }
	        });
	        
	        itemPeriod.setEndElementListener(new EndElementListener() {	
				public void end() {
					tutorial.setPeriod(period);
				}
			});
	        
	        //pega o tema
	        item.getChild("theme").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String theme) {
	                tutorial.setTheme(theme);
	            }
	        });
	        
	        //pega a descri��o
	        item.getChild("description").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String description) {
	            	tutorial.setDescription(description);
	            }
	        });
	        
	        //pega os dados dos speakers
	        
	        Element speakers = item.getChild("speakers");
	        speakers.setStartElementListener(new StartElementListener() {				
				public void start(Attributes attrb) {
					arraySpeakers = new ArrayList<Speaker>();
				}
			});
	        
	        Element itemSpeaker = speakers.getChild("speaker");
	        itemSpeaker.setStartElementListener(new StartElementListener(){			
				public void start(Attributes attrb) {
					speaker = new Speaker(null, null, null);}
			});
	        
	        itemSpeaker.getChild("name").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String name) {
	            	speaker.setName(name);
	            }
	        });
	        
	        itemSpeaker.getChild("affiliation").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String affiliation) {
	            	speaker.setAffiliation(affiliation);
	            }
	        });
	        
	        /*
	         * ESSA PARTE DEVE SER DESCOMENTADA SE FOR UTILIZAR BIOGRAFIA DO SPEAKER
	         * 
	        itemSpeaker.getChild("biography").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String biography) {
	            	speaker.setBiography(biography);
	            }
	        });
	        */
	        itemSpeaker.setEndElementListener(new EndElementListener() {	
				public void end() {
					arraySpeakers.add(speaker);
				}
			});
	        
	        speakers.setEndElementListener(new EndElementListener() {	
				public void end() {
					tutorial.setSpeakers(arraySpeakers);
				}
			});
	        
	        //quando os itens acabarem, chame esse método e adicione a instancia atual de Pessoo no ArrayList
	        item.setEndElementListener(new EndElementListener() {
				
				public void end() {
					arrayTutorials.add(tutorial);
				}
			});
	        
            Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());
			
	        return  arrayTutorials;
	    }
	 
}

