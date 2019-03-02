package com.aspire.dotcard.syncAndroid.dc;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class MessageSendBO {
	protected static JLogger LOG = LoggerFactory
			.getLogger(MessageSendBO.class);

	private MessageSendBO() {

	}

	private static MessageSendBO instance = null;

	public static MessageSendBO getInstance() {
		if (instance == null) {
			instance = new MessageSendBO();
		}
		return instance;
	}

	/**
	 * 查询待发送数据
	 * @param status
	 */
	public List<MessageVO> getMessageSendByStatus(int status) {
		//查询数据
		return MessageSendDAO.getInstance().getMessageSendByStatus(status);

	}

	/**
	 * 根据事务id查询message数据
	 * @param status
	 */
	public List<MessageVO> getMessageSendByTransactionId(String transactionId) {
		//查询数据
		return MessageSendDAO.getInstance().getMessageSendByTransactionId(
				transactionId);

	}

	/**
	 * 根据id修改记录状态和最后修改时间
	 * @param hret
	 * @param id
	 */
	public void updateStatusAndLupdate(int hret, int id) {
		MessageSendDAO.getInstance().updateStatus(hret, id);
	}

	/**
	 * 根据事务id修改记录状态和最后修改时间
	 * @param status
	 * @param transactionId
	 */
	public void updateByTransactionId(int status, String transactionId) {
		MessageSendDAO.getInstance().updateByTransactionId(status,
				transactionId);
	}
	
	
//	create table T_A_MESSAGES2(
//			message varchar2(4000),
//			createdate date default sysdate,
//			lupdate date,
//			TRANSACTIONID varchar2(15) not null,
//			status number(1) not null,
//			ifext number(1) not null,
//			primary key(TRANSACTIONID)
//
//			)
//
//			create table t_a_messages2_ext(
//			message varchar2(4000),
//			TRANSACTIONID varchar2(15) not null,
//			sortid number(5) not null,
//			id number(10) not null,
//			primary key(id)
//			)

	//此方法就是为了把消息放到T_A_MESSAGES2和t_a_messages2_ext表中
	public void insertMessages(String key, List<String> value) throws BOException {
		try{
			List<String> messages = getMessages(key,value);	
			if(messages.size()==1){
				MessageSendDAO.getInstance().handleSingleMessages(key,messages.get(0),"-1","0");//-1：表示未处理 0：表示不需要看扩展表
			}else if(messages.size()>1){
				MessageSendDAO.getInstance().handleMultiMessages(key,messages,"-1","1");//-1：表示未处理 1：表示需要看扩展表
			}else{
				LOG.error("find error insertMessages:"+key);//这个情况是异常情况，需要观察。
			}
		}catch(Exception e){
			throw new BOException("消息组合时出错！"+key+" -- "+value.size(),e);
		}
	}

	private List<String> getMessages(String key, List<String> value) {
		//    	{messageValue=[{Transactionid=null, Action=0, type=CountUpdateReq, Contentid=300000987816}, 
		//        {Transactionid=null, Action=0, type=CountUpdateReq, Contentid=300000996185}, 
		//        {Transactionid=null, Action=0, type=CountUpdateReq, Contentid=300000987816}], 
		//        messageKey=201305141000244}
		StringBuilder buildValue = new StringBuilder();
		StringBuilder vv = new StringBuilder();
		buildValue.append("{");
		buildValue.append("messageValue=[");
		for (String v : value) {
			vv.append(",").append(v);
		}
		if (vv.length() > 0) {
			vv = vv.deleteCharAt(0);
		}
		buildValue.append(vv);
		buildValue.append("],messageKey=").append(key).append("}");

		final String result = buildValue.toString();
		int len = result.length();
		
		
		int messagesSize = 3000;
		try{
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
			"syncAndroid");
	        String messagesSizeStr = module.getItemValue("MESSAGES_SIZE") ;
	        messagesSize = Integer.parseInt(messagesSizeStr);
		}catch(Exception e){
			LOG.error("MESSAGES_SIZE 没有配置！");
		}
		
		if (result.length() > messagesSize) {
			List<String> list = new ArrayList<String>();
			int start = 0, end = 0, step = messagesSize;
			while (start < len) {
				end = start + step;
				if (end > len) {
					end = len;
				}
				String str = result.substring(start, end);
				list.add(str);
				start = end;
			}
			return list;
		} else {
			return new ArrayList() {
				{
					add(result);
				}
			};
		}
	}

	public List<MessageVO> getNoTranMessage(int noTranLimit) {
		// TODO Auto-generated method stub
		return MessageSendDAO.getInstance().getNoTranMessage(noTranLimit);
	}

	public List<MessageVO> getTranMessage() {
		// TODO Auto-generated method stub
		return MessageSendDAO.getInstance().getTranMessage();
	}

}
