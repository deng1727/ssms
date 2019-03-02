package com.aspire.dotcard.syncAndroid.dc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.common.ThreadPoolExecutorUtil;


public class MessageSendTack extends TimerTask
{
    /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(MessageSendTack.class);
    
    @Override
    public void run()
    {
//        if (LOG.isDebugEnabled())
//        {
//            LOG.debug("MessageSend׼����ѯ�����������������ķ���...5������Ϣ������ġ�����");
//        }
        
        LOG.info("MessageSend׼����ѯ�����������������ķ���...5������Ϣ������ġ�����with Throwable");
        
        int limit = 20;
        int noTranLimit = 10000;//һ�δ�����������Ϣ1������
        try
        {
        	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
			"syncAndroid");
            String dc_limit = module.getItemValue("dc_limit") ;
            limit = Integer.parseInt(dc_limit);
            
            String dc_noTranLimit = module.getItemValue("dc_noTranLimit") ;
            noTranLimit = Integer.parseInt(dc_noTranLimit);
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        
        LOG.info("MessageSend׼����ѯ�����������������ķ��Ϳ�ʼ��������������������"+limit+"----һ�����������������"+noTranLimit);
        try{
        //����statusΪ-1������
        List<MessageVO> list = new ArrayList<MessageVO>();
        List<MessageVO> noTranList = MessageSendBO.getInstance().getNoTranMessage(noTranLimit); // һ����״̬Ϊ-1������
        LOG.info("noTranList size:"+noTranList.size());
        List<MessageVO> tranList = MessageSendBO.getInstance().getTranMessage();//��ѯ״̬Ϊ-1������
        LOG.info("tranList size:"+tranList.size());
        list.addAll(noTranList);
        list.addAll(tranList);//�鵽����2������ȫ������list��
        LOG.info("list size:"+list.size());
		Map<String,List<MessageVO>> data = new LinkedHashMap<String,List<MessageVO>>();    //��Ȼ��list�����ڲ�ֻ��һ��VO
		for(int i=0;i<list.size();i++){
			putData(data,list.get(i));    //�ѻ�ȡ�������ݰ���key=transcationid,value=messagevo ����ʽ��װ���� 
		}  
		int num=0;
		List<String> limitList = new ArrayList<String>();
		List<MessageVO> messageList = new ArrayList<MessageVO>();		
		for(Iterator<String> it = data.keySet().iterator();it.hasNext();){
			String key = (String)it.next();
			if(key.equals("null")){
				List<MessageVO> l = (List<MessageVO>)data.get(key);
				for(int i=0;i<l.size();i++){
					MessageVO vo = l.get(i);
    				limitList.add(buildData(vo));
    				messageList.add(vo);
					// modify by aiyan 2013-05-08 ��û���������Ϣ��������ȥ�������ǵ������͡�
        			if(num<limit-1){
        				num++;
        				
        				continue;
        				
        			}else{
        				try{
	        				String transactionID = ContextUtil.getTransactionID();
	        				sendMessage(transactionID,limitList);
		        			for(MessageVO v:messageList){
		        				updateStatus(v);
		        			}
		        			num = 0;
		        			limitList.clear();
		        			messageList.clear();
		        			
        				}catch(Exception e){
    	        			LOG.error("����û���������Ϣ�����ˣ�",e);
    	        		}
        			}
				}
			}else{
				final List<MessageVO> l = (List<MessageVO>) data.get(key);
				ThreadPoolExecutorUtil.execute(new Runnable() {
					public void run() {
						try {
							if (l.size() > 0) {
								String transactionId = l.get(0)
										.getTransactionId();
								// String transactionID =
								// ContextUtil.getTransactionID2();
								List<String> listMapVO = new ArrayList<String>();
								for (int i = 0; i < l.size(); i++) {
									MessageVO vo = l.get(i);
									listMapVO.add(buildData(vo));
								}
								sendMessage(transactionId, listMapVO);
								updateStatus(transactionId);

							}

						} catch (Exception e) {
							LOG.error("��������Ϣ��ʱ�����", e);
						}
					}
				});

			}
		}
		// modify by aiyan 2013-05-08 ��û���������Ϣ��������ȥ�������ǵ������͡���Ϊ�������Ǹ�ѭ����
		//limitList���ܻ���ʣ��ģ�����Ҫ�����һ�¡�
		if(limitList.size()>0){
			try{
				String transactionID = ContextUtil.getTransactionID();
				sendMessage(transactionID,limitList);
    			for(MessageVO v:messageList){
    				updateStatus(v);
    			}
    			num = 0;
    			limitList.clear();
    			messageList.clear();
    			
			}catch(Exception e){
    			LOG.error("����û���������Ϣ�����ˣ�",e);
    		}
		}
		
		LOG.info("MessageSendTack�����ˣ�5���Ӻ��������");
		
		
        }catch(Throwable e){
        	LOG.error("Throwable:MessageSendTack:this error may let timer object stop!!",e);
        }
        
    }

	private void sendMessage(String key,List<String> value) throws BOException{
		// TODO Auto-generated method stub
		MessageSendBO.getInstance().insertMessages(key,value);
	}

	private void updateStatus(MessageVO vo) {
		// TODO Auto-generated method stub
		MessageSendDAO.getInstance().updateStatus(0, vo.getId());
		
	}
	
	private void updateStatus(String transactionId) {
		// TODO Auto-generated method stub
		MessageSendDAO.getInstance().updateStatusWithTran(0, transactionId);
		
	}
	
	private String buildData(MessageVO vo) {
		// TODO Auto-generated method stub
		String data="";
		if(vo.getType().equals("ContentModifyReq")){
			String[] arr = vo.getMessage().split(":");
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("type=\"").append(vo.getType()).append("\",");
			sb.append("Contentid=\"").append(arr[0]).append("\",");
			sb.append("Action=\"").append(arr[1]).append("\",");
			sb.append("Transactionid=\"").append(vo.getTransactionId());
			sb.append("\"}");
			data = sb.toString();
			
//			data.put("type", vo.getType());
//			data.put("Contentid", arr[0]);
//			data.put("Action", arr[1]);
//			data.put("Transactionid", vo.getTransactionId());
		}else if(vo.getType().equals("RefModifyReq")){
//			Goodsid	����	String	reference���е�goodsid
//			Categoryid	��ѡ	String	����categoryid���½�ʱ������
//			Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//			Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//			Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//			Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//			Action	����	String	0���½�
//			9��ɾ��
//			Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�

			String[] arr = vo.getMessage().split(":");
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("type=\"").append(vo.getType()).append("\",");
			sb.append("Goodsid=\"").append(arr[0]).append("\",");
			if("0".equals(arr[6])){
				sb.append("Categoryid=\"").append(arr[1]).append("\",");
				sb.append("Id=\"").append(arr[2]).append("\",");
				sb.append("Refnodeid=\"").append(arr[3]).append("\",");
				sb.append("Sortid=\"").append(arr[4]).append("\",");
				sb.append("Loaddate=\"").append(arr[5]).append("\",");
			}
			sb.append("Action=\"").append(arr[6]).append("\",");
			sb.append("Transactionid=\"").append(vo.getTransactionId());
			sb.append("\"}");
			data = sb.toString();
			
//			data.put("type", vo.getType());
//			data.put("Goodsid", arr[0]);
//			data.put("Categoryid", arr[1]);
//			data.put("Id", arr[2]);
//			data.put("Refnodeid", arr[3]);
//			data.put("Sortid", arr[4]);
//			data.put("Loaddate", arr[5]);
//			data.put("Action", arr[6]);
//			data.put("Transactionid", vo.getTransactionId());
		}else if(vo.getType().equals("CatogoryModifyReq")){
			String[] arr = vo.getMessage().split(":");
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("type=\"").append(vo.getType()).append("\",");
			sb.append("Catogoryid=\"").append(arr[0]).append("\",");
			sb.append("Action=\"").append(arr[1]).append("\",");
			sb.append("Transactionid=\"").append(vo.getTransactionId());
			sb.append("\"}");
			data = sb.toString();
//			data.put("type", vo.getType());
//			data.put("Catogoryid", arr[0]);
//			data.put("Action", arr[1]);
//			data.put("Transactionid", vo.getTransactionId());
		}else if(vo.getType().equals("CountUpdateReq")){
			String[] arr = vo.getMessage().split(":");
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("type=\"").append(vo.getType()).append("\",");
			sb.append("Contentid=\"").append(arr[0]).append("\",");
			sb.append("Action=\"").append(arr[1]).append("\",");
			sb.append("Transactionid=\"").append(vo.getTransactionId());
			sb.append("\"}");
			data = sb.toString();
//			data.put("type", vo.getType());
//			data.put("Contentid", arr[0]);
//			data.put("Action", arr[1]);
//			data.put("Transactionid", vo.getTransactionId());
		}else if(vo.getType().equals("BatchRefModifyReq")){
			StringBuilder sb = new StringBuilder();
			String message = vo.getMessage();
			sb.append("{");
			sb.append("type=\"").append(vo.getType()).append("\",");
			sb.append("Categoryid=\"").append(message.split(":")[0]).append("\",");
			if (message.split(":").length >1) {
				sb.append("Action=\"").append(message.split(":")[1]).append("\",");
			}
			sb.append("Transactionid=\"").append(vo.getTransactionId());
			sb.append("\"}");
			data = sb.toString();
		}
		return data;
	}

    private void putData(Map data,MessageVO vo) {
    	String key =vo.getTransactionId()+"";
		if (data.containsKey(key)) {
			List l = (List) data.get(key);
			l.add(vo);
		} else {
			List l = new ArrayList();
			l.add(vo);
			data.put(key, l);
			
		}

	}

//	public  static void  main(String[] argv){
//		MessageSendTack t = new MessageSendTack();
//		MessageVO vo = new MessageVO();
//		vo.setId(1);
//		vo.setLupdate(new Date());
//		vo.setMessage("aamessages1...");
//		vo.setStatus(-1);
//		vo.setTransactionId(null);
//		vo.setType("hhtype");
//		
//		MessageVO vo1 = new MessageVO();
//		vo1.setId(1);
//		vo1.setLupdate(new Date());
//		vo1.setMessage("aamessages2...");
//		vo1.setStatus(-1);
//		vo1.setTransactionId("123");
//		vo1.setType("hhtype");
//		
//		MessageVO vo2 = new MessageVO();
//		vo2.setId(1);
//		vo2.setLupdate(new Date());
//		vo2.setMessage("aamessages3...");
//		vo2.setStatus(-1);
//		vo2.setTransactionId("123");
//		vo2.setType("hhtype");
//		
//		MessageVO vo3 = new MessageVO();
//		vo3.setId(1);
//		vo3.setLupdate(new Date());
//		vo3.setMessage("aamessages4...");
//		vo3.setStatus(-1);
//		vo3.setTransactionId(null);
//		vo3.setType("hhtype");
//		Map m = new HashMap();
//		t.putData(m,vo);
//		t.putData(m,vo1);
//		t.putData(m,vo2);
//		t.putData(m,vo3);
//		for(Iterator it = m.keySet().iterator();it.hasNext();){
//			String a = it.next()+"";
//			System.out.println(a);
//			System.out.println(m.get(a));
//			
//		}
//	}
 public static void main(String[] args) {
	String m = "100001023";
	System.out.println(m.split(":").length);;
}
    
    
}
