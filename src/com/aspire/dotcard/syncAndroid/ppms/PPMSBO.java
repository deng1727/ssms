package com.aspire.dotcard.syncAndroid.ppms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class PPMSBO {
	/**
	 * ��־����
	 */
	JLogger LOG = LoggerFactory.getLogger(PPMSBO.class);
	private static PPMSBO bo = new PPMSBO();
	private PPMSBO() {
	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static PPMSBO getInstance() {

		return bo;
	}
	//private TaskRunner dataSynTaskRunner;

	/**
	 * ����APPInfo
	 * 
	 * @param APPInfoVO
	 *            vo
	 * @return
	 * @throws DAOException
	 */
	public void addAPPInfo(APPInfoVO vo) throws Exception {
		APPInfoDAO.getInstance().addAPPInfo(vo);

	}
	
	public int getLeftContentNum30And33(){
		return APPInfoDAO.getInstance().getLeftContentNum30And33();
	}
	public int getLeftContentNum33And33WithAppid(){
		return APPInfoDAO.getInstance().getLeftContentNum33And33WithAppid();
	}
	//����newpas���reference��
	public void doUpdateNewpasReference(TransactionDB transactionDB,ReceiveChangeVO receiveChangeVO) throws DAOException{
		PPMSDAO dao = PPMSDAO.getTransactionInstance(transactionDB);
		List<TDReferenceVO> vos = dao.getNewpasReferenceByAppid(receiveChangeVO.getAppid());
		String goodsid;
		int sortid ;
		for (TDReferenceVO oldVo : vos) {
			goodsid =createNewGoodsId(receiveChangeVO.getEntityid(), oldVo.getCategoryid(), receiveChangeVO.getAppid());
			sortid = oldVo.getSortid();
			TDReferenceVO newReferenceVO = new TDReferenceVO();
			newReferenceVO.setGoodsid(goodsid);
			newReferenceVO.setSortid(sortid);
			newReferenceVO.setCid(receiveChangeVO.getEntityid());
			
		}
		
	}

	public void handleAPPInfo() throws BOException {
		LOG.info("syncAndroid:handleAPPInfo(ÿ5���ӿ�ʼ���t_a_ppms_receive_change״̬Ϊ-1�ļ�¼,����entityid��30��ͷ��)��ʼ...");
		
		long start = System.currentTimeMillis();
		int receiveChangeLimit = 1000;//Ĭ�ϲ�����
		int threadLimit = 2;//Ĭ��Ϊ2;
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	receiveChangeLimit = Integer.parseInt(module.getItemValue("ReceiveChangeLimit")) ;
	    	if(receiveChangeLimit>5000){//̫����ܲ��á��ʳ������޶�һ�¡�
	    		receiveChangeLimit=5000;
	    	}
	    	LOG.info("ReceiveChangeLimit is:"+receiveChangeLimit);
		}catch(Exception e){
			LOG.error("ReceiveChangeLimit is bad...");
		}
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	threadLimit = Integer.parseInt(module.getItemValue("ThreadLimit")) ;
	    	if(threadLimit>10){//̫����ܲ��á��ʳ������޶�һ�¡�
	    		threadLimit=10;
	    	}
	    	LOG.info("ThreadLimit is:"+threadLimit);
		}catch(Exception e){
			LOG.error("threadLimit is bad...");
		}
		//int maxNum = Integer.valueOf(syncDataMaxNum).intValue();
		
		
		List<ReceiveChangeVO> receiveChangeList = null;
		try {
			receiveChangeList = APPInfoDAO.getInstance().getReceiveChangeList(
					Constant.MESSAGE_HANDLE_STATUS_INIT,receiveChangeLimit,Constant.MESSAGE_CONTENT_TYPE_30);
		} catch (DAOException e) {
			//throw new BOException("��ȡreceiveChangeList����", e);
		    LOG.error("��ȡreceiveChangeList����,",e);
		}
		
		//update by fanqh 20131210
		if(receiveChangeList==null ||(receiveChangeList!=null&&receiveChangeList.size()<1)){
		    LOG.error("��ȡreceiveChangeList����Ϊ��!");
            return;
        }
		
		if(LOG.isDebugEnabled()){
		    LOG.debug("��ʼreceiveChangeList���ݴ���,��Ҫ��������ݴ�С��"+receiveChangeList.size());
		}
		
		
        
//		ExecutorService exec = new ThreadPoolExecutor(threadLimit, threadLimit, 0L,
//				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(receiveChangeLimit),
//				new ThreadPoolExecutor.CallerRunsPolicy());
		
		final List<String> listCidAdd = Collections.synchronizedList(new ArrayList<String>());//��¼������Ӧ��
		final List<String> listCidUpdate = Collections.synchronizedList(new ArrayList<String>());//��¼�޸ĵ�Ӧ��
		final List<DelGoodsVO> listDel = Collections.synchronizedList(new ArrayList<DelGoodsVO>());//��¼ɾ����Ӧ��
		TaskRunner dataSynTaskRunner = new TaskRunner(threadLimit,0);
		
		for (int i = 0; i < receiveChangeList.size(); i++) {
			final ReceiveChangeVO vo = receiveChangeList.get(i);
			// ֪ͨ���� 1 �C Ӧ����Ϣ��� 2 �C ������Ϣ��� 3 �C Ӧ����Ϣ��������Ϣ���
			try {
				if ("1".equals(vo.getType())) {
					//   Ӧ����Ϣ���
//					//��֤���CONTENTID�Ƿ���Ч����contentid��Ҫ��ppms_v_service��V_DC_CM_DEVICE_RESOURCE���ڡ�
//					//�����κ�CONTENTID����Ҫ��ҵ����Ϣ����Դ�����ϵ������ͨ����
//					if(!validate(vo.getEntityid())){
//						//��t_a_ppms_receive_change��statusֵΪ-2;
//						try{
//							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2");
//						}catch(Exception e){
//							LOG.error("��Ч�ĵ�����ʵ�壺"+vo);	
//						}
//
//						continue;
//					}
					long starttime = System.currentTimeMillis();
					HandleContent cm = new HandleContent(vo);//modify by aiyan 2012-07-24
	        		//�����첽����
	        		ReflectedTask task = new ReflectedTask(cm, "handleContent", null, null);
	        		//������ӵ���������
	        		dataSynTaskRunner.addTask(task);
					//handleContent(vo,listCidAdd,listCidUpdate,listDel);//������1�������ʱ��Ҫô��Ӧ��������Ҫô���޸ģ��������ߡ�
				
					LOG.debug("exec vo contentid=" + vo.getEntityid() + ";id="
							+ vo.getId() + ";" + i + ";costtime="
							+ (System.currentTimeMillis() - starttime));
//					exec.execute(new Runnable(){
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try{
//								handleContent(vo,listCidAdd,listCidUpdate,listDel);//������1�������ʱ��Ҫô��Ӧ��������Ҫô���޸ģ��������ߡ�
//							}catch(Exception e){
//								LOG.error("handleAPPInfo:" + vo.getEntityid(),e);	
//							}
//						}});
					
				} else if ("2".equals(vo.getType())) {
					//�����ϵ���
//					//�����κ�CONTENTID����Ҫ�����ݱ�t_r_gcontent���д��ڣ�����ͨ����
					if(!PPMSDAO.validateGcontent(vo.getEntityid().split("\\|")[1])){
						//��t_a_ppms_receive_change��statusֵΪ-2;
						try{
							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2" ,"-2");
						}catch(Exception e){
							LOG.error("��Ч�ĵ�����ʵ�壺"+vo);	
						}

						continue;
					}
					handleResource(vo);
					LOG.debug("exec vo contentid="+vo.getEntityid()+";id="+vo.getId()+";"+i);
//					exec.execute(new Runnable(){
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try{
//								handleResource(vo);
//							}catch(Exception e){
//								LOG.error("handleAPPInfo:" + vo.getEntityid(),e);	
//							}
//						}});
					
					
				} else if ("3".equals(vo.getType())) {
					//    Ӧ����Ϣ��������Ϣ���
					//��֤���CONTENTID�Ƿ���Ч����contentid��Ҫ��ppms_v_service��V_DC_CM_DEVICE_RESOURCE���ڡ�
					//�����κ�CONTENTID����Ҫ��ҵ����Ϣ����Դ�����ϵ������ͨ����
					//if(!validate(vo.getEntityid())){//���ﲻ���۸��飬����˵���Բ��öԼ۸��顣add by aiyan 2013-06-07
					if(!PPMSDAO.validateResource(vo.getEntityid())){
						//��t_a_ppms_receive_change��statusֵΪ-2;
						try{
							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2" ,"-2");
						}catch(Exception e){
							LOG.error("��Ч�ĵ�����ʵ�壺"+vo);	
						}

						continue;
					}
					HandleContent cm = new HandleContent(vo);//modify by aiyan 2012-07-24
	        		//�����첽����
	        		ReflectedTask task = new ReflectedTask(cm, "handleContent", null, null);
	        		//������ӵ���������
	        		dataSynTaskRunner.addTask(task);
					//handleContentResouce(vo,listCidAdd,listCidUpdate,listDel);
					LOG.debug("exec vo contentid="+vo.getEntityid()+";id="+vo.getId()+";"+i);
//					exec.execute(new Runnable(){
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try{
//								handleContentResouce(vo,listCidAdd,listCidUpdate,listDel);
//							}catch(Exception e){
//								LOG.error("��Ч�ĵ�����ʵ�壺"+vo);	
//							}
//
//						}});
					
				}
			} catch (Exception e) {
				LOG.error("handleAPPInfo:" + vo.getEntityid(),e);
			}
		}
		
		    //20131128 add by fanqh
		    try
            {
		        if(LOG.isDebugEnabled()){
		            LOG.debug("waitToFinishedǰ,���߳�˯��500���룡");
		        }
                Thread.sleep(500);
            }
            catch (InterruptedException e1)
            {
                //e1.printStackTrace();
                LOG.error("�߳�˯��500���룬����");
            }
		
		   dataSynTaskRunner.waitToFinished();
	       dataSynTaskRunner.stop();
	        
//	        for (ReceiveChangeVO recVo : receiveChangeList) {
//				if ("1".equals(recVo.getOpt())) {
//					try {
//						HandleContent.grounding(recVo);
//					} catch (DAOException e) {
//						LOG.debug(recVo.getAppid() + "�ϼ�ʧ��.....",e);
//					}
//				} else {
//					try {
//						HandleContent.undercarriage(recVo);
//					} catch (DAOException e) {
//						LOG.debug(recVo.getAppid() +" �¼�ʧ��....",e);
//					}
//				}
//			}
	        
	        
	        
	        
	        
	        
	        
//		exec.shutdown();
//		// �ȴ����߳̽������ټ���ִ������Ĵ���
//		try {
//			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			LOG.error("exec.awaitTermination�����жϴ���",e);
//		}
		LOG.debug("all thread complete");
		
		//  ִ������Ӧ�ñ�����̺�,����opt��ִ����Ʒ���¼ܵ�����.............
		
		//new PPMSDAO().putMessages(pids);//����������Դ�ı��CONTENTID
		
		//remove by aiyan 2013-05-18�۸�����������ͬ����ʱ������
		//����V_SERVICE���ݣ�ע�ؼ۸���Ϣmobileprice��
		//updateVService(cids);
		
		
//		//���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
//		contentIdSet.addAll(pids);
//		contentIdSet.addAll(cids);
//		updateLastTime(contentIdSet);
		
		String searchIsOpen = "0";//Ĭ�ϲ�����
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	        searchIsOpen = module.getItemValue("SEARCH_ISOPEN") ;
		}catch(Exception e){
			LOG.error("SEARCH_ISOPEN not found...");
		}
        if("1".equals(searchIsOpen)){
    		//������ϵͳ���ļ�����
        	LOG.info("createSearcheFile start...");
        	LOG.info("createSearcheFile(listCidAdd,listCidUpdate,listDel)listCidAdd.SIZE="+listCidAdd.size()+"listCidUpdate.size="+listCidUpdate.size()+"listDel.size:"+listDel.size());
    	try{
        	createSearcheFile(listCidAdd,listCidUpdate,listDel);//remove by aiyan 2013-05-24��ʱע����Ҫ��ȫ�����롣û�б�Ҫ�������ļ���
    	}catch(Exception e){
    		e.printStackTrace();
    		LOG.error("createSearcheFile error!",e);
    	}
    		LOG.info("createSearcheFile end...");
        }
		LOG.info("syncAndroid:handleAPPInfo complete!  task num:"+receiveChangeList.size()+"  all times is:"+(System.currentTimeMillis()-start));

	}

	private boolean validate(String contentid) {
		// TODO Auto-generated method stub
		if(PPMSDAO.validateVService(contentid)&&PPMSDAO.validateResource(contentid)){
			return true;
		}
		return false;
	}
	
//	public void updateLastTime(Set<String> contentIdSet) {
//		// TODO Auto-generated method stub
//		LOG.info("syncAndroid:updateLastTime(���³������������ʱ��)��ʼ...");
//		String[] arr = new String[contentIdSet.size()];
//		contentIdSet.toArray(arr);
//		int fromIndex=0;int toIndex=0;int stepSize=10;
//		do{
//			toIndex = fromIndex+stepSize;
//			if(toIndex>arr.length){
//				toIndex=arr.length;
//			}
//			
//			StringBuilder sb = new StringBuilder();
//			for(int i=fromIndex;i<toIndex;i++){
//				sb.append(",'").append(arr[i]).append("'");
//			}
//			if(sb.length()>0){
//				String cids = sb.substring(1);
//				try {
//					new PPMSDAO().updateLastTime(cids);
//				} catch (DAOException e) {
//					// TODO Auto-generated catch block
//					LOG.error("�����˲���v_content_lastû�и���������Գ����������ʱ����Ӱ�졣",e);
//				}
//			}
//			
//			fromIndex=toIndex;
//		}while(toIndex<arr.length);
//	}

	private void createSearcheFile(List<String> listCidAdd,List<String> listCidUpdate,List<DelGoodsVO> listCidDel) {
		// TODO Auto-generated method stub
		LOG.info("syncAndroid:createSearcheFile(������ϵͳ���ļ�����)��ʼ...");
		try {
	
			
			List<SearcharFileVO> data1 = new PPMSDAO().getSearchFileData(listCidAdd,"A");
			List<SearcharFileVO> data2 = new PPMSDAO().getSearchFileData(listCidUpdate,"U");
			List<SearcharFileVO> data3 = new PPMSDAO().getSearchFileDataDel(listCidDel);
			
			List<SearcharFileVO> data = new ArrayList<SearcharFileVO>();
			data.addAll(data1);
			data.addAll(data2);
			data.addAll(data3);
			
			if(data.size()==0){
				LOG.info("createSearcheFileû�����ݣ��ʲ��������ļ���");
				return;
			}
	        String fileName = SearchFileConfig.LOCALDIR + "temp" +File.separator + "temp_"
	        + PublicUtil.getCurDateTime("yyyyMMddHHmmss") + "_00001.temp";  
	        
	        LOG.info(" SearchFileConfig.LOCALDIR " +  SearchFileConfig.LOCALDIR );
	        LOG.info("��ǰ�����ļ�·��" + fileName);
	        
	        String toFileName = "temp_" + PublicUtil.getCurDateTime("yyyyMMddHHmmss")
	                          + "_00001.txt";
	        
	        
	        File file = new File(fileName);
	        
	        if(!file.getParentFile().exists())
	        {
	            file.getParentFile().mkdirs();
	        }
	        
	        BufferedWriter writer = null;
	        try
	        {
	            writer = new BufferedWriter(new FileWriter(fileName));
	            int i = 0;
	            for (SearcharFileVO vo:data){
	                //writer.write(objectToString(obj));
	            	writer.write(vo.toString());
	    	       

	                // /r/n
	                writer.write(13);
	                writer.write(10); 

	                // ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
	                if (i % 10000 == 0)
	                {
	                    writer.flush();
	                }
	                i++;
	            }
	            writer.close();
	        }
	        catch (IOException e)
	        {
	            LOG.error("д�������ļ��쳣 fileName=" + fileName, e);
	        }
	        catch (Throwable e)
	        {
	        	LOG.error("�����ļ���fileName=" + fileName, e);
	        }
	        finally
	        {
	            try
	            {
	                if (writer != null)
	                {
	                    writer.close();
	                }
	            }
	            catch (Exception e1)
	            {
	            }
	        }
	        
	        
	        boolean result = IOUtil.rename(new File(fileName), toFileName);
	        
	        if (!result)
	        {
	            LOG.error("�������ļ�ʧ�ܣ�sourceFileName=" + fileName);
	        }
	        
	        
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("search file dao����",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.error("search file sql����",e);
		}
	}
	
 
    


//	private void notifySearcheSubSystem() {
//		// TODO Auto-generated method stub
//		LOG.info("֪ͨ������ϵͳ������");
//		String search_url = Config.getInstance().getModuleConfig()
//		.getItemValue("SEARCH_URL_Sync");
//		LOG.info("֪ͨ������ϵͳ������-->url:search_url"+search_url);
//		try {
//			HttpUtil.getResponseCodeFromURL(search_url, "utf-8");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			LOG.error("֪ͨ������ϵͳʧ��",e);
//		}
//		LOG.info("֪ͨ������ϵͳ��������ɣ�");
//		
//	}

//	private void updateResource(List<String> list) {
//		// TODO Auto-generated method stub
//		int fromIndex=0;int toIndex=0;int stepSize=10;
//		LOG.info("syncAndroid:updateVService(����V_SERVICE���ݣ�ע�ؼ۸���Ϣmobileprice)��ʼ...");
//		do{
//			toIndex = fromIndex+stepSize;
//			if(toIndex>list.size()){
//				toIndex=list.size();
//			}
//			List<String> sub = list.subList(fromIndex, toIndex);
//			StringBuilder sb = new StringBuilder();
//			for(String pid:sub){
//				sb.append(",").append(pid);
//			}
//			if(sb.length()>0){
//				String pids = sb.substring(1);
//				try {
//					new PPMSDAO().updateResource(pids);
//				} catch (DAOException e) {
//					// TODO Auto-generated catch block
//					LOG.error("�����˲���t_a_cm_device_resourceû�и���������Լ۸���Ϣ��Ӱ��",e);
//				}
//			}
//			
//			fromIndex=toIndex;
//		}while(toIndex<list.size());
//		
//	}
	
//	public void updateVService(List<String> list) {
//		// TODO Auto-generated method stub
//		int fromIndex=0;int toIndex=0;int stepSize=10;
//		LOG.info("syncAndroid:updateVService(����V_SERVICE���ݣ�ע�ؼ۸���Ϣmobileprice)��ʼ...");
//		do{
//			toIndex = fromIndex+stepSize;
//			if(toIndex>list.size()){
//				toIndex=list.size();
//			}
//			List<String> sub = list.subList(fromIndex, toIndex);
//			StringBuilder sb = new StringBuilder();
//			for(String cid:sub){
//				sb.append(",'").append(cid).append("'");
//			}
//			if(sb.length()>0){
//				String cids = sb.substring(1);
//				try {
//					new PPMSDAO().updateVServiceOld(cids);
//				} catch (DAOException e) {
//					// TODO Auto-generated catch block
//					LOG.error("�����˲���V_SERVICEû�и���������Լ۸���Ϣ��Ӱ��",e);
//				}
//			}
//			
//			fromIndex=toIndex;
//		}while(toIndex<list.size());
//		
//	}

	private void handleContent(ReceiveChangeVO receiveChangeVO,List listCidAdd,List listCidUpdate,List listCidDel)
			throws BOException {
		
		int syncMode = 0;//ͬ��ģʽ��ѡ��һ�����Ӧ��д0�ġ�0:�����������1:Ҫ�����¼���Ϣ��Ҫ����FULLDEVICEID�ֶ�
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	syncMode = Integer.parseInt(module.getItemValue("SYNC_MODE"));
		}catch(Exception e){};
		
		// ��ѯ����t_r_gcontent��
		// 1�����ڣ����޸ģ������������Ҳ�ı䣬�������¼ܲ���
		// 2�������ڣ����뵽��t_r_gcontent���ϼܵ���Ӧ�Ķ��������¡�
		CmContentVO dccVo = null;
		GContentVO vo = null;
		long starttime2 = System.currentTimeMillis();
		try {
			// ����
			dccVo = APPInfoDAO.getInstance().getCmContent(
					receiveChangeVO.getEntityid());
			vo = APPInfoDAO.getInstance().getGContentVO(
					receiveChangeVO.getEntityid());
		} catch (DAOException e) {
			// TODO Auto-generated catch block             
			// e.printStackTrace();
			LOG.error("handleContent���ִ���", e);
			throw new BOException("handleContent("
					+ receiveChangeVO.getEntityid() + ")����", e);
		}
		
		LOG.debug("exec vo handleContent1 contentid="+receiveChangeVO.getEntityid()+";id="+receiveChangeVO.getId()+";"+";costtime="+(System.currentTimeMillis()-starttime2));
		//��������Ҫ���˵�HTCӦ�á���ΪHTCӦ���ǲ���MM�Ķ���������ܡ�  HTCӦ�û��ײ�Ӧ�����ﴦ��HTC�����¼����ϵ�ͬ����ɡ����ײ�Ӧ���ǲ��϶���������ܡ�
		//(add 2014-01-07) ������App���ϼ�
		//�����ߵĴ����������Ʒ���Ż�������ͬ���ˡ����ǰѸ�����Ϣͨ�����������������¼����顣��16:HTCӦ�ã�11:�ײ�Ӧ�ã�21��������App
		if(dccVo!=null&&("16".equals(dccVo.getSubtype())||"11".equals(dccVo.getSubtype())||"21".equals(dccVo.getSubtype()))){
			
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				try {
					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2","-2");
				} catch (DAOException e) {
					LOG.error("subtype="+dccVo.getSubtype()+" (16:HTCӦ�ã�11:�ײ�Ӧ�ã�21��������App��) û����Դ��"+receiveChangeVO.getId());
				}
				
				return;
			}
			
			LOG.debug(dccVo.getContentid()+" is subtype="+dccVo.getSubtype()+" content... vo!=null"+(vo!=null));
			TransactionDB tdb = null;
			try{
				tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				//String transactionID = ContextUtil.getTransactionID();
				if(vo!=null){
					dccVo.setId(vo.getId());
					// �޸�
					dao.updateGContent(dccVo);
					dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid()+ ":1");
					dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
				}else{
					String newId = dao.insertGContent(dccVo);
					dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid()+ ":0");
					dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");
				}

				dao.updateVService(dccVo.getContentid());//�۸���Ϣ��
				dao.updateDeviceResourseByCid(dccVo.getContentid());//�������������ϵ��add by aiyan 2013-04-07
				//���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
				dao.updateLastTime(dccVo.getContentid());
				//dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"2");
				tdb.commit();
				
				if(vo!=null&&syncMode==1){//�޸ĵĻ�����FULLDEVICEŪһ�¡�add by aiyan 2013-06-19
					Map<String,List<String>> map = getFullDevice(vo);
					List<String> fullDeviceIdList = map.get("fullDeviceIdList");
					List<String> fullDeviceNameList = map.get("fullDeviceNameList");
					dao.updateFullDevice(vo.getContentId(),fullDeviceIdList,fullDeviceNameList);
					
				}
			}catch(Exception e){
				if (tdb != null) {
					tdb.rollback();
				}
				LOG.error(e);
			}finally {
				if (tdb != null) {
					tdb.close();
				}
			}
			return;
		}
		
		
		if(dccVo==null&&vo == null){
			LOG.info("dccVo==null&&vo == null,˵���������ͻ��ܶ�û�����Ӧ�ã�����������ݰ�����"+receiveChangeVO.getEntityid());

			//��t_a_ppms_receive_change��statusֵΪ-3;
			try {
				new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-3" ,"-3");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error(e);
			}
			LOG.error("��Ч�ĵ�����ʵ�壺"+vo);
	
			
			return;
		}

		if (dccVo==null&&vo != null) {
			// �¼�ANDROID�����������и�CONTENTID����Ʒ��
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String categoryId = module.getItemValue("ROOT_CATEGORYID");
			String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
			TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				// ���߿�ʼ������������
				dao.downAllReference(categoryId, vo);//������Ʒ��android��������Ҫ���ߣ�
				dao.downAllOperateReference(operateCategoryId, vo);//������Ʒ(ָ������Ӫ������Ҫ����)
				
				dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());//�������������ϵ��add by aiyan 2013-04-07
				
				listCidDel.addAll(dao.getDelGoodsList(categoryId, vo.getId()));
				String transactionID = ContextUtil.getTransactionID();
				// ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
				dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid()+ ":9");
				dao.addMessages(MSGType.RefModifyReq, transactionID);
				//removed by aiyan 2013-06-01 �ն��Ż��Լ�������Ĵ����ְ�������Ӧ�ñ�������������ظ��������ˣ��ʶ�����
				//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//�������������ϵ��add by aiyan 2013-04-07
				dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":9");//ɾ���񵥣�
				// ���޸�PPMS�Ľ�������״̬
				dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
				dao.deleteContentid(receiveChangeVO.getEntityid());
				tdb.commit();
			} catch (DAOException e) {
				if (tdb != null) {
					tdb.rollback();
				}
				// TODO Auto-generated catch block
				//e.printStackTrace();
				LOG.error(e);
			}finally {
				if (tdb != null) {
					tdb.close();
				}
			}
		}
		
			
		if(dccVo!=null&&vo!=null){
			
			
			///��֤û������ģ����ߣ���
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				
				// �¼�ANDROID�����������и�CONTENTID����Ʒ��
				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
				String categoryId = module.getItemValue("ROOT_CATEGORYID");
				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
				TransactionDB tdb = null;
				//Producer producer = null;
				try {
					tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
					// ���߿�ʼ������������
					dao.downAllReference(categoryId, vo);//������Ʒ
					dao.downAllOperateReference(operateCategoryId, vo);//������Ʒ(ָ������Ӫ������Ҫ����)
					
					dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());//�������������ϵ��add by aiyan 2013-04-07
					
					listCidDel.addAll(dao.getDelGoodsList(categoryId, vo.getId()));
					String transactionID = ContextUtil.getTransactionID();
					// ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
					dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid()+ ":9");
					dao.addMessages(MSGType.RefModifyReq, transactionID);
					//removed by aiyan 2013-06-01 �ն��Ż��Լ�������Ĵ����ְ�������Ӧ�ñ�������������ظ��������ˣ��ʶ�����
					//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//�������������ϵ��add by aiyan 2013-04-07
					dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":9");//ɾ���񵥣�
					// ���޸�PPMS�Ľ�������״̬
					dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" , "9");
					tdb.commit();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if (tdb != null) {
						tdb.rollback();
					}
					LOG.error(e);
				}finally {
					if (tdb != null) {
						tdb.close();
					}
				}
				
//				
//				///�����䡣���¡�����Ϣ��
//				
//				TransactionDB tdb = null;
//				try{
//					LOG.debug("(dccVo!=null&&vo!=null)û������Ŷ"+receiveChangeVO.getEntityid());
//					
//					tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
//					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
//					dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());
//					dao.addMessages(MSGType.ContentModifyReq, null,receiveChangeVO.getEntityid() + ":2");//�������������ϵ��add by aiyan 2013-04-25
//					// ���޸�PPMS�Ľ�������״̬
//					dao.updateReceiveChange(receiveChangeVO.getId(),
//							Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
//					tdb.commit();
//				}catch(Exception e){
//					tdb.rollback();
//					LOG.debug("(dccVo!=null&&vo!=null)û������Ŷ,����Ҫ�����䣬�ҷ���Ϣ�������ˣ�"+receiveChangeVO.getEntityid(),e);
//				}finally{
//					if(tdb!=null){
//						tdb.close();
//					}
//				}
				//��������ǰ���߼�����ע���ˡ�REMOVED BY AIYAN 2013-04-25
//				try{
//					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2");
//				}catch(Exception e){
//					LOG.error("��Ч�ĵ�����ʵ��,û�����䣡hehe��"+vo);	
//				}
//				return;
			}else{
				long stime = System.currentTimeMillis();
				
				TransactionDB tdb = null;
				try{
					String transactionID = ContextUtil.getTransactionID();
					tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�Ŀ����ϣ�������������������������������ɡ�
					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
					dccVo.setId(vo.getId());
					
					// �޸�
					dao.updateGContent(dccVo);
					dao.updateVService(vo.getContentId());//�۸���Ϣ��
					dao.updateDeviceResourseByCid(vo.getContentId());//�������������ϵ��add by aiyan 2013-04-07
					//���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
					dao.updateLastTime(vo.getContentId());
					
					// ���������ķ�����Ϣ��Ӧ�����ݱ����--------------------------------------------
					dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId()+ ":1");
					//removed by aiyan 2013-05-27����˵��1����һ����䣩�Ͳ���2��
					//dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId()+ ":2");
					dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//�����񵥣�//add by aiyan 2013-05-21
					
					//�ն��Ż��Ǳ���ʱ��Ҫ��ȫ��ͬ��������;Ͱ�ģʽ����1�ġ������¼���Ϣ��  add by aiyan 2013-06-19
			    	if(syncMode==0){
						//����ط��Ƚ��ر���ΪT_R_GCONTENT��Ӧ�������ߵ�ʱ��û��ɾ�����еļ�¼����
						//����dccVo!=null&&vo!=null�µ����������ANDROID������û����Ʒ�������ֲ�
						boolean isExistRef = dao.isExistRefInAndrodCategory(vo);
						if(!isExistRef){
							//transactionID = ContextUtil.getTransactionID();
							//dao.downCategoryByTactic(vo);//����ط�����ע����Ҳ���Բ�ע������ע��һ�°ɡ�������
							dao.addCategroyByTactic(dccVo);
							//dao.updateDeviceResourseByCid(vo.getContentId());//�������������ϵ��add by aiyan 2013-04-07
							// ���������ķ�����Ϣ�����¼ܣ�-------------------------------------------- 
							//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//�����ϵ��add by aiyan 2013-04-07
							dao.addMessages(MSGType.RefModifyReq, transactionID);
							//dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//�����񵥣��տ�ʼ�����ۣ��ǲ�����ɶͳ�����ݵģ��ʶ�������Ϣ//remove by aiyan 2013-05-21
						}else{
							if (!vo.getAppCateID().equals(dccVo.getAppCateID())) {
								// ��2�������޸ģ������¼ܲ���
								dao.downCategoryByTactic(vo);
								dao.addCategroyByTactic(dccVo);
								// ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
								dao.addMessages(MSGType.RefModifyReq, transactionID);
								
							}
						}
			    	}else if(syncMode==1){
						dao.downCategoryByTactic(vo);
						dao.addCategroyByTactic(dccVo);
						dao.addMessages(MSGType.RefModifyReq, transactionID);
			    	}

					dao.updateReceiveChange(receiveChangeVO.getId(),
							Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
					tdb.commit();
					listCidUpdate.add(vo.getContentId());
					
					if(syncMode==1){
						Map<String,List<String>> map = getFullDevice(vo);
						List<String> fullDeviceIdList = map.get("fullDeviceIdList");
						List<String> fullDeviceNameList = map.get("fullDeviceNameList");
						dao.updateFullDevice(vo.getContentId(),fullDeviceIdList,fullDeviceNameList);
					}
				}catch (Exception e) {
					LOG.error("handleContent�����쳣��DB����ع���"+receiveChangeVO, e);
					if (tdb != null) {
						tdb.rollback();
					}
				}finally {
					if (tdb != null) {
						tdb.close();
					}
				}
				LOG.debug("exec vo handleContent2 contentid="+receiveChangeVO.getEntityid()+";id="+receiveChangeVO.getId()+";"+";costtime="+(System.currentTimeMillis()-stime));
			}
		}
		
		
		if(dccVo!=null&&vo==null){
			//���������û�����䣬������T_R_GCONTENT��
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				LOG.error("dccVo!=null&&vo==null û�����䲻����T_R_GCONTENT"+receiveChangeVO.getId());
				//��t_a_ppms_receive_change��statusֵΪ-2;
				try{
					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2" ,"-2");
				}catch(Exception e){
					LOG.error("��Ч�ĵ�����ʵ�壺"+vo);	
				}
				return;
			}
			
			TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�Ŀ����ϣ�������������������������������ɡ�
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				//��T_R_GCONTENT�����ڣ�����Ҫ�������ݡ�
				String transactionID = ContextUtil.getTransactionID();
				// 1�����뵽��t_r_gcontent��
				String newId = dao.insertGContent(dccVo);// ////////////////////////////2-27�Ų��Ե㡣�������Ǻǡ�������
				
				dao.updateVService(dccVo.getContentid());//�۸���Ϣ��
				dao.updateDeviceResourseByCid(dccVo.getContentid());//�������������ϵ��add by aiyan 2013-04-07
				//���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
				dao.updateLastTime(dccVo.getContentid());
				
				dccVo.setId(newId);
				// 2�ϼܶ�Ӧ�Ķ�������
				dao.addCategroyByTactic(dccVo);
				// ���������ķ�����Ϣ������Ӧ�����ݱ��֪ͨ�ӿ�-�����ߣ�----------------------------------------------
				dao.addMessages(MSGType.ContentModifyReq, transactionID, dccVo.getContentid()+ ":0");
				// ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
				dao.addMessages(MSGType.RefModifyReq, transactionID);
				//removed by aiyan 2013-05-27����˵��1����һ����䣩�Ͳ���2��
				//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//�������������ϵ��add by aiyan 2013-04-07
				//dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//�����񵥣��տ�ʼ�����ۣ��ǲ�����ɶͳ�����ݵģ��ʶ�������Ϣ//remove by aiyan 2013-05-21
				// ���޸�PPMS�Ľ�������״̬
				dao.updateReceiveChange(receiveChangeVO.getId(),
						Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");

				tdb.commit();
				listCidAdd.add(dccVo.getContentid());
				
				
			

			} catch (Exception e) {
				LOG.error("handleContent�����쳣��DB����ع���"+receiveChangeVO, e);
				if (tdb != null) {
					tdb.rollback();
				}
				throw new BOException("handleContent�����쳣��DB����ع���"+receiveChangeVO, e);

			}finally {
				if (tdb != null) {
					tdb.close();
				}
			}
		}
		

	}

	private Map getFullDevice(GContentVO vo) {
		// TODO Auto-generated method stub
		boolean isFree = isFreeContent(vo.getContentId());
		Map map = null;
		if(isFree){
			map = getFullDeviceAll(vo.getContentId());
		}else{
			map = getFullDeviceMatch(vo.getContentId());
		}
		return map;
		
	}

	private Map getFullDeviceMatch(String contentId) {
		// TODO Auto-generated method stub
		return PPMSDAO.getFullDeviceMatch(contentId);
	}

	private Map getFullDeviceAll(String contentId) {
		// TODO Auto-generated method stub
		return PPMSDAO.getFullDeviceAll(contentId);
	}

	private boolean isFreeContent(String contentId) {
		// TODO Auto-generated method stub
		return PPMSDAO.isFreeContent(contentId);
		
	}

	private void handleResource(ReceiveChangeVO receiveChangeVO)
			throws BOException {
		TransactionDB tdb = null;
		String[] arr = receiveChangeVO.getEntityid().split("\\|");//�������ԴID��������PPMS�������ǵ���PID|CONTENTID	
		String contentid = arr[1];
		try {
			tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
			dao.updateDeviceResourseByCid(contentid);
			
			//���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
			dao.updateLastTime(contentid);
			
			//��������2����������ٵġ���������ˣ����Ǿ͸���������˵��Ӧ�ñ����add by aiyan2013-06-01
			dao.addMessages(MSGType.ContentModifyReq, "",
					contentid + ":1");//����һ����Ϣ������Ҫ����ID������������������ʽ�����������addMessages����������IDΪ���ַ�����
			
			// ���޸�PPMS�Ľ�������״̬
			dao.updateReceiveChange(receiveChangeVO.getId(),
					Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
			tdb.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			LOG.error("handleResource���ִ���", e);
			if(tdb!=null){
				tdb.rollback();
			}
			throw new BOException("handleResource("
					+ receiveChangeVO.getEntityid() + ")", e);
		} 
		finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
//	private void handleResource2(ReceiveChangeVO vo)
//	throws BOException {
//		TransactionDB tdb = null;
//		//Producer producer = null;
//		try {
//			tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
//			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
//			
//			String contentId = vo.getEntityid();
//			dao.updateDeviceResourseByCid(contentId);
////			// ���������ķ�����Ϣ������Ӧ�����ݱ��֪ͨ�ӿ�-�����ϵ�����----------------------------------------------
////			dao.addMessages(MSGType.ContentModifyReq, "",
////					vo.getEntityid() + ":2");// ///xxxxx���������2013-2-25  // ����ע����ԭ�����������һ����TYPE=3�����ʽ����handleContent�Ѿ�����������Ϣ�ˡ�
//			// ���޸�PPMS�Ľ�������״̬
//			dao.updateReceiveChange(vo.getId(),
//					Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
//			tdb.commit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			LOG.error("handleResource���ִ���", e);
//			tdb.rollback();
//			throw new BOException("handleResource("
//					+ vo.getEntityid() + ")", e);
//		}finally {
//			if (tdb != null) {
//				tdb.close();
//			}
//		}
//}

	private void handleContentResouce(ReceiveChangeVO receiveChangeVO,List listCidAdd,List listCidUpdate,List listCidDel)
			throws BOException {
			try{
				handleContent(receiveChangeVO,listCidAdd,listCidUpdate,listCidDel);
			}catch(Exception e){
				LOG.error(e);
				throw new BOException("�����ݺ����䶼�������£������ݵĴ������",e);
			}
			//removed by aiyan 2013-06-04 
//			try {
//				handleResource2(receiveChangeVO);
//			} catch (Exception e) {
//				LOG.error(e);
//				throw new BOException("�����ݺ����䶼�������£�������Ĵ������", e);
//			}
	}
	
	private String createNewGoodsId(String cid,String categoryid,String appid){
		String g = "000000000" +categoryid;
		String goodsid = g.substring(g.length()-9, g.length()) +"000000"
				+ PublicUtil.lPad(appid, 12)
				+ PublicUtil.lPad(cid, 12);;
		return goodsid;
	}
}
