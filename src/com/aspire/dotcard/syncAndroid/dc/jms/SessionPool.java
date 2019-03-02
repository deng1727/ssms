package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SessionPool {
	private int maxCount = 20;
	private int currentNum = 0; //�ö���ص�ǰ�Ѵ����Ķ�����Ŀ
	private LinkedList<Session> pool = new LinkedList<Session>();//���ڴ�Ŷ���ĳ�
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
					//�����ǰ�����޶�����ã������������Ķ�����Ŀ�Ѵﵽ�����Ƶ����ֵ��
					//��ֻ�ܵȴ������̷߳��ض��󵽳���
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//�����ǰ�����п��õĶ��󣬾�ֱ�Ӵӳ���ȡ������
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