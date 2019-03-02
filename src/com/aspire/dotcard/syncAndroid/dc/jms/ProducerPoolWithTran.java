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

public class ProducerPoolWithTran {
	
    /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(ProducerPoolWithTran.class);
    
//	private static String quene = "DC.TOPIC.TRAN";
//	private static String quene2 = "DC.TOPIC.BATCH";
//	private static String quene3 = "DC.TOPIC";
	
	private int maxCount = 20;
	private int currentNum = 0; //�ö���ص�ǰ�Ѵ����Ķ�����Ŀ
	private LinkedList<ProducerTran> pool = new LinkedList<ProducerTran>();//���ڴ�Ŷ���ĳ�
	
	private static ProducerPoolWithTran instance = null;

	private transient Connection connection;

	private ProducerPoolWithTran() throws JMSException {
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String url = module.getItemValue("JMS_ACTIVEMQ_URL");
		ConnectionFactory factory = new ActiveMQConnectionFactory(url);
		connection = factory.createConnection();
		//connection.setClientID("myclientid_2");
		connection.start();
		
		while (currentNum <= maxCount) {
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("DC.QUEUE.TRAN");
	        MessageProducer producer = session.createProducer(destination);
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			currentNum++;
//			System.out.println("currentNum="+currentNum);
//			System.out.println("producer="+producer);
			pool.add(new ProducerTran(this,session,destination,producer));
		}

	}

	public static ProducerPoolWithTran getInstance() throws JMSException {
		if (instance == null) {
			instance = new ProducerPoolWithTran();
		}
		return instance;
	}

	public ProducerTran getProducer() throws JMSException {
		synchronized (this) {
//			LOG.debug("Tran:wait();hehe"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
//			while (pool.isEmpty()) {
//				if (currentNum <= maxCount) {
//					Session session = connection.createSession(true,
//							Session.AUTO_ACKNOWLEDGE);
//					Destination destination = session.createQueue("DC.QUEUE.TRAN");
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
//						LOG.debug("Tran:wait();hehe,Σ����"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
//						wait();
//					} catch (InterruptedException e) {
//						LOG.error(e);
//					}
//				}
//			}
//			//�����ǰ�����п��õĶ��󣬾�ֱ�Ӵӳ���ȡ������
//			return pool.removeLast();
			
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

	public void returnObject(ProducerTran producer) {
		synchronized (this) {
			pool.add(producer);
			notifyAll();
		}
	}
}