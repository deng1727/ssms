package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SessionPool {
	private int maxCount = 20;
	private int currentNum = 0; //该对象池当前已创建的对象数目
	private LinkedList<Session> pool = new LinkedList<Session>();//用于存放对象的池
	private static SessionPool instance = null;

	private transient Connection connection;

	protected SessionPool() throws JMSException {

		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = factory.createConnection();
		connection.start();

	}

	public static SessionPool getInstance() throws JMSException {
		if (instance == null) {
			instance = new SessionPool();
		}
		return instance;
	}

	public Session getSession() throws JMSException {
		synchronized (this) {
			if (pool.isEmpty()) {
				if (currentNum <= maxCount) {
					Session session = connection.createSession(true,
							Session.AUTO_ACKNOWLEDGE);
					currentNum++;
					System.out.println("currentNum="+currentNum);
					System.out.println("session="+session);
					pool.add(session);
				} else {
					//如果当前池中无对象可用，而且所创建的对象数目已达到所限制的最大值，
					//就只能等待其它线程返回对象到池中
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//如果当前池中有可用的对象，就直接从池中取出对象
			currentNum--;
			return pool.removeLast();
		}
	}

	public void returnObject(Session session) {
		synchronized (this) {
			currentNum++;
			pool.add(session);
			notifyAll();
		}
	}
}