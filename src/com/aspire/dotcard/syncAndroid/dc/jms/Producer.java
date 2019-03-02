package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.ppms.PPMSTimer;

public class Producer {
	private transient Destination destination;
	private transient Session session;
	private transient MessageProducer producer;
	private transient ProducerPool pool;
	
	protected static JLogger LOG = LoggerFactory.getLogger(Producer.class);

	public Producer(ProducerPool pool,Session session,Destination destination,MessageProducer producer) {
		this.pool = pool;
		this.session = session;
		this.destination = destination;
		this.producer = producer;
	}
//	public void sendMessage(Map data) throws JMSException {
//		MapMessage message = session.createMapMessage();
//		Iterator it = data.keySet().iterator();
//		while (it.hasNext()) {
//			String key = (String) it.next();
//			String value = (String) data.get(key);
//			message.setString(key, value);
//
//		}
//		producer.send(destination, message);
//	}
	
	public void sendMessage(String key,List<Map> value) throws JMSException {
		MapMessage message = session.createMapMessage();
		message.setString("messageKey", key);
		message.setObject("messageValue", value);
		producer.send(destination, message);
	}
	
	//将Producer对象还到消息对象池中换回去。
	public void returnObject() {
		pool.returnObject(this);
	}
	

//	public void commit() throws JMSException {
//		session.commit();
//	}

	public void rollback() {
		try {
			session.rollback();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			LOG.error("JMS在rollback出错，这个SESSION有问题！",e);
		}
	}

}
