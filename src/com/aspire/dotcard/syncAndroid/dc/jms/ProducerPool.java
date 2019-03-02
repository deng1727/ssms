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
     * 日志引用
     */
    protected static JLogger LOG = LoggerFactory.getLogger(ProducerPool.class);
    
//	private static String quene = "DC.QUEUE.TRAN";
//	private static String quene2 = "DC.QUEUE.BATCH";
//	private static String quene3 = "DC.QUEUE";
	
	private int maxCount = 20;
	private int currentNum = 0; //该对象池当前已创建的对象数目
	private LinkedList<Producer> pool = new LinkedList<Producer>();//用于存放对象的池
	
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
//					//如果当前池中无对象可用，而且所创建的对象数目已达到所限制的最大值，
//					//就只能等待其它线程返回对象到池中
//					try {
//						LOG.debug("wait();hehe,危险了"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
//						wait();
//					} catch (InterruptedException e) {
//						LOG.error(e);
//					}
//				}
//			}
			//如果当前池中有可用的对象，就直接从池中取出对象
			if(pool.size()>0){
				return pool.removeLast();
			}else{
				LOG.error("wait();hehe,危险了"+pool.isEmpty()+"--"+currentNum+"--"+maxCount);
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					LOG.error("wait();hehe,危险了"+pool.isEmpty()+"--"+currentNum+"--"+maxCount,e);
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