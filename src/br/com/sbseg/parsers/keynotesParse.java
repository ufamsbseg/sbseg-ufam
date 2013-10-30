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
import br.com.sbseg.beans.Keynote;
import br.com.sbseg.beans.Palestrante;
import br.com.sbseg.beans.Session;
import br.com.sbseg.beans.Talk;

public class keynotesParse {
	
	private static Keynote keynote;
	private static Session session;
	private static Palestrante ObejectSpeaker;
	private static Talk talk;
	private static ArrayList<Keynote> arrayKeynotes;
	private static ArrayList<Session> arraySessions;
	private static ArrayList<Palestrante> arraySpeakers;
	private static InputStream is;
	
	 public static ArrayList<Keynote> parse(Context context) throws IOException, SAXException {
	       
	        final RootElement root = new RootElement("keynotes");
	        
	        arrayKeynotes = new ArrayList<Keynote>();
	        is = context.getResources().getAssets().open("keynoteSpeakers_sbseg.xml");
	        
	        // use setStartElementListener para ter acesso aos atributos do item atual 
	        root.setStartElementListener(new StartElementListener() {				
				
				public void start(Attributes attrb) {
					//versao = attrb.getValue("versao");
				
			}});
	        
	        //pega referência de um item 
	        Element KN = root.getChild("keynote");
	        
	        //agora vamos pegar todos os valores que estão dentro do KN
	        //sempre inicie com esse método no KN
	        KN.setStartElementListener(new StartElementListener() {
				
				
				public void start(Attributes attr) {
					//aproveite e instancie aqui um novo objeto para armazar as informações desse KN
					keynote = new Keynote();
					String idKeynote = attr.getValue("id");
					keynote.setId(Integer.parseInt(idKeynote.trim()));
				}
			});
	        
	        KN.getChild("date").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String date) {
	                keynote.setData(date);
	            }
	        });
	        KN.getChild("weekday").setEndTextElementListener(new EndTextElementListener(){
	            public void end(String weekday) {
	            	keynote.setWeekday(weekday);
	            }
	        });
	        	
	        	Element elementSessions = KN.getChild("sessions");
		        elementSessions.setStartElementListener(new StartElementListener() {
					
					
					public void start(Attributes attr) {
						//aproveite e instancie aqui um novo objeto para armazar as informações desse KN
						arraySessions = new ArrayList<Session>();
//						arraySpeakers = new ArrayList<Speaker>();
//						session = new Session();
//						idSession = attr.getValue("id");
//						session.setId(Integer.parseInt(idSession.trim()));
					}
				});
		        
		        	Element elementSession = elementSessions.getChild("session");
		        	elementSession.setStartElementListener(new StartElementListener() {
					
					
					public void start(Attributes attr) {
						//aproveite e instancie aqui um novo objeto para armazar as informações desse KN
					//	arraySpeakers = new ArrayList<Speaker>();
						session = new Session();
						String idSession = attr.getValue("id");
						session.setId(Integer.parseInt(idSession.trim()));
						}
					});
			        
			        
			        elementSession.getChild("start").setEndTextElementListener(new EndTextElementListener(){
			            public void end(String start) {
			            	session.setStart(start);
			            }
			        });
			        
			        elementSession.getChild("end").setEndTextElementListener(new EndTextElementListener(){
			            public void end(String end) {
			            	session.setEnd(end);
			            }
			        });
			        	
			        Element speakers =  elementSession.getChild("speakers");
			        speakers.setStartElementListener(new StartElementListener() {
						
						
						public void start(Attributes attr) {
							//aproveite e instancie aqui um novo objeto para armazar as informações desse KN
							arraySpeakers = new ArrayList<Palestrante>();
//							speaker = new Speaker();
//							idSpeaker = attr.getValue("id");
//							speaker.setId(Integer.parseInt(idSpeaker.trim()));
						}
					});
			      
			        	Element speaker =  speakers.getChild("speaker");
			        
				        speaker.setStartElementListener(new StartElementListener() {
							
							
							public void start(Attributes attr) {
								//aproveite e instancie aqui um novo objeto para armazar as informações desse KN
								
								ObejectSpeaker = new Palestrante();
								String idSpeaker = attr.getValue("id");
								ObejectSpeaker.setId(Integer.parseInt(idSpeaker.trim()));
							}
						});
			        
				        speaker.getChild("tag").setEndTextElementListener(new EndTextElementListener(){
				            public void end(String tag) {
				            	ObejectSpeaker.setTag(tag);
				            }
				        });
				        
				        speaker.getChild("photo").setEndTextElementListener(new EndTextElementListener(){
				            public void end(String photo) {
				            	ObejectSpeaker.setPhoto(photo);
				            }
				        });
				        
				        speaker.getChild("name").setEndTextElementListener(new EndTextElementListener(){
				            public void end(String name) {
				            	ObejectSpeaker.setName(name);
				            }
				        });
	
				        speaker.getChild("filiation").setEndTextElementListener(new EndTextElementListener(){
				            public void end(String filiation) {
				            	ObejectSpeaker.setFiliation(filiation);
				            }
				        });
	
				        
				        	Element tlk = speaker.getChild("talk");
				        
					        tlk.setStartElementListener(new StartElementListener() {				
								
								public void start(Attributes attrb) {
								talk = new Talk();
							}});
					        
					        tlk.getChild("title").setEndTextElementListener(new EndTextElementListener(){
					            public void end(String title) {
					            	talk.setTitle(title);
					            }
					        });
					        
					        tlk.getChild("resume").setEndTextElementListener(new EndTextElementListener(){
					            public void end(String resume) {
					            	talk.setResume(resume);
					            }
					        });
					        tlk.setEndElementListener(new EndElementListener() {
								
								
								public void end() {
									ObejectSpeaker.setTalk(talk);
								}
							});
		        
				        speaker.getChild("biography").setEndTextElementListener(new EndTextElementListener(){
				            public void end(String biography) {
				            	ObejectSpeaker.setBiography(biography);
				            }
				        });
				        
				        speaker.setEndElementListener(new EndElementListener() {
							
							
							public void end() {
								arraySpeakers.add(ObejectSpeaker);
							}
						});
				    
			       
			        
			        elementSession.setEndElementListener(new EndElementListener() {
						
						
						public void end() {
							session.setListSpeaker(arraySpeakers);
							arraySessions.add(session);
						}
					});
		        //quando os itens acabarem, chame esse método e adicione a instancia atual de Pessoo no ArrayList
	        KN.setEndElementListener(new EndElementListener() {
				
				
				public void end() {
					keynote.setListSession(arraySessions);
					arrayKeynotes.add(keynote);
				}
			});
	        
            Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());
            
	        return  arrayKeynotes;
	    }

}
