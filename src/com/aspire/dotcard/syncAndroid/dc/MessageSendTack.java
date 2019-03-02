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
     * 日志引用
     */
    protected static JLogger LOG = LoggerFactory.getLogger(MessageSendTack.class);
    
    @Override
    public void run()
    {
//        if (LOG.isDebugEnabled())
//        {
//            LOG.debug("MessageSend准备查询操作数据向数据中心发送...5分钟消息会回来的。。。");
//        }
        
        LOG.info("MessageSend准备查询操作数据向数据中心发送...5分钟消息会回来的。。。with Throwable");
        
        int limit = 20;
        int noTranLimit = 10000;//一次处理无事务消息1万条。
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
        
        LOG.info("MessageSend准备查询操作数据向数据中心发送开始。。。无事务打包条数："+limit+"----一次无事务最多条数："+noTranLimit);
        try{
        //发送status为-1的数据
        List<MessageVO> list = new ArrayList<MessageVO>();
        List<MessageVO> noTranList = MessageSendBO.getInstance().getNoTranMessage(noTranLimit); // 一万条状态为-1的数据
        LOG.info("noTranList size:"+noTranList.size());
        List<MessageVO> tranList = MessageSendBO.getInstance().getTranMessage();//查询状态为-1的数据
        LOG.info("tranList size:"+tranList.size());
        list.addAll(noTranList);
        list.addAll(tranList);//查到的这2组数据全部放入list中
        LOG.info("list size:"+list.size());
		Map<String,List<MessageVO>> data = new LinkedHashMap<String,List<MessageVO>>();    //虽然是list但是内部只有一个VO
		for(int i=0;i<list.size();i++){
			putData(data,list.get(i));    //把获取到的数据按照key=transcationid,value=messagevo 的形式封装起来 
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
					// modify by aiyan 2013-05-08 把没有事务的消息批量发过去，而不是单条发送。
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
    	        			LOG.error("发送没有事务的消息出错了！",e);
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
							LOG.error("发事务消息的时候出错！", e);
						}
					}
				});

			}
		}
		// modify by aiyan 2013-05-08 把没有事务的消息批量发过去，而不是单条发送。因为在上面那个循环中
		//limitList可能还有剩余的，这里要最后处理一下。
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
    			LOG.error("发送没有事务的消息出错了！",e);
    		}
		}
		
		LOG.info("MessageSendTack跑完了，5分钟后见！！！");
		
		
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
//			Goodsid	必须	String	reference表中的goodsid
//			Categoryid	可选	String	货架categoryid，新建时必须有
//			Id	可选	String	货架categoryid对应的Id，新建时必须有
//			Refnodeid	可选	String	应用ID，新建时必须有
//			Sortid	可选	String	排序字段，新建时必须有
//			Loaddate	可选	String	更新时间，新建时必须有
//			Action	必须	String	0：新建
//			9：删除
//			Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

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
