package com.aspire.dotcard.syncAndroid.dc.jms;

import javax.jms.Connection;  
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.MessageProducer;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
import org.apache.activemq.ActiveMQConnectionFactory;  
  
public class SendMessage {  
 private static final String url ="tcp://localhost:61616";  
 private static final String QUEUE_NAME ="choice.queue.aiyan";  
 protected String expectedBody = "<hello>world!_aiyan...1235</hello>";  
 public void sendMessage() throws JMSException{  
  Connection connection =null;
  Session session = null;
  try{  
   ActiveMQConnectionFactory connectionFactory =new ActiveMQConnectionFactory(url);  
   connection = (Connection)connectionFactory.createConnection();  
   connection.start();  
   session = (Session)connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
   Destination destination = session.createQueue(QUEUE_NAME);  
   MessageProducer producer = session.createProducer(destination);  

   TextMessage message = session.createTextMessage(expectedBody);  
   message.setStringProperty("headname", "remoteB");  
   producer.send(message);      
   session.commit();
  }catch(Exception e){  
   e.printStackTrace();  
  }finally{
	  session.close();  
	  connection.close();  
  }
 }  
   
 public static void main(String[] args){  
  SendMessage sndMsg = new SendMessage();  
  try{  
   sndMsg.sendMessage();  
  }catch(Exception ex){  
   System.out.println(ex.toString());  
  }  
 }  
} 