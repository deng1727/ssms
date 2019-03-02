package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ProducerPool {
	
    /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(ProducerPool.class);
    
//	private static String quene = "DC.QUEUE.TRAN";
//	private static String quene2 = "DC.QUEUE.BATCH";
//	private static String quene3 = "DC.QUEUE";
	
	private int maxCount = 20;
	private int currentNum = 0; //�ö���ص�ǰ�Ѵ����Ķ�����Ŀ
	private LinkedList<Producer> pool = new LinkedList<Producer>();//���ڴ�Ŷ���ĳ�
	
	private static ProducerPool instance = null;

	private transient Connection connection;

	private ProducerPool() throws JMSException {

		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String url = module.getItemValue("JMS_ACTIVEMQ_URL");
		
		ConnectionFactory factory = new ActiveMQConnectionFactory(url);
		connection = factory.createConnection();
		//connection.setClientID("myclientid_1");
		connection.start();
		
		
		while (currentNum <= maxCount) {
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("DC.QUEUE");
	        MessageProducer producer = session.createProducer(destination);
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			currentNum++;
//			System.out.println("currentNum="+currentNum);
//			System.out.println("producer="+producer);
			pool.add(new Producer(this,session,destination,producer));
		}

	}

	public static ProducerPool getInstance() throws JMSException {
		if (instance == null) {
			instance = new ProducerPool();
		}
		return instance;
	}

	public Producer getProducer() throws JMSException {
		synchronized (this) {
			LOG.debug("wait();hehe"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
//			while (pool.isEmpty()) {
//				if (currentNum <= maxCount) {
//					Session session = connection.createSession(false,
//							Session.AUTO_ACKNOWLEDGE);
//					Destination destination = session.createQueue("DC.QUEUE");
//			        MessageProducer producer = session.createProducer(destination);
//			        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//					currentNum++;
////					System.out.println("currentNum="+currentNum);
////					System.out.println("producer="+producer);
//					pool.add(new Producer(session,destination,producer));
//				} else {
//					//�����ǰ�����޶�����ã������������Ķ�����Ŀ�Ѵﵽ�����Ƶ����ֵ��
//					//��ֻ�ܵȴ������̷߳��ض��󵽳���
//					try {
//						LOG.debug("wait();hehe,Σ����"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
//						wait();
//					} catch (InterruptedException e) {
//						LOG.error(e);
//					}
//				}
//			}
			//�����ǰ�����п��õĶ��󣬾�ֱ�Ӵӳ���ȡ������
			if(pool.size()>0){
				return pool.removeLast();
			}else{
				LOG.error("wait();hehe,Σ����"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					LOG.error("wait();hehe,Σ����"+pool.isEmpty()+"--"+currentNum+"--"+maxCount,e);
				}
			}
			return null;
			
		}
	}

	public void returnObject(Producer producer) {
		synchronized (this) {
			pool.add(producer);
			notifyAll();
		}
	}
}