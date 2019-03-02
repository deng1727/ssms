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
	 * 日志引用
	 */
	JLogger LOG = LoggerFactory.getLogger(PPMSBO.class);
	private static PPMSBO bo = new PPMSBO();
	private PPMSBO() {
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static PPMSBO getInstance() {

		return bo;
	}
	//private TaskRunner dataSynTaskRunner;

	/**
	 * 插入APPInfo
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
	//更新newpas里的reference表
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
		LOG.info("syncAndroid:handleAPPInfo(每5分钟开始检查t_a_ppms_receive_change状态为-1的记录,而且entityid是30开头的)开始...");
		
		long start = System.currentTimeMillis();
		int receiveChangeLimit = 1000;//默认不开放
		int threadLimit = 2;//默认为2;
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	receiveChangeLimit = Integer.parseInt(module.getItemValue("ReceiveChangeLimit")) ;
	    	if(receiveChangeLimit>5000){//太大可能不好。故程序中限定一下。
	    		receiveChangeLimit=5000;
	    	}
	    	LOG.info("ReceiveChangeLimit is:"+receiveChangeLimit);
		}catch(Exception e){
			LOG.error("ReceiveChangeLimit is bad...");
		}
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	threadLimit = Integer.parseInt(module.getItemValue("ThreadLimit")) ;
	    	if(threadLimit>10){//太大可能不好。故程序中限定一下。
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
			//throw new BOException("获取receiveChangeList出错", e);
		    LOG.error("获取receiveChangeList出错,",e);
		}
		
		//update by fanqh 20131210
		if(receiveChangeList==null ||(receiveChangeList!=null&&receiveChangeList.size()<1)){
		    LOG.error("获取receiveChangeList数据为空!");
            return;
        }
		
		if(LOG.isDebugEnabled()){
		    LOG.debug("开始receiveChangeList数据处理,需要处理的数据大小："+receiveChangeList.size());
		}
		
		
        
//		ExecutorService exec = new ThreadPoolExecutor(threadLimit, threadLimit, 0L,
//				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(receiveChangeLimit),
//				new ThreadPoolExecutor.CallerRunsPolicy());
		
		final List<String> listCidAdd = Collections.synchronizedList(new ArrayList<String>());//记录新增的应用
		final List<String> listCidUpdate = Collections.synchronizedList(new ArrayList<String>());//记录修改的应用
		final List<DelGoodsVO> listDel = Collections.synchronizedList(new ArrayList<DelGoodsVO>());//记录删除的应用
		TaskRunner dataSynTaskRunner = new TaskRunner(threadLimit,0);
		
		for (int i = 0; i < receiveChangeList.size(); i++) {
			final ReceiveChangeVO vo = receiveChangeList.get(i);
			// 通知类型 1 – 应用信息变更 2 – 适配信息变更 3 – 应用信息和适配信息变更
			try {
				if ("1".equals(vo.getType())) {
					//   应用消息变更
//					//验证这个CONTENTID是否有效：该contentid需要在ppms_v_service和V_DC_CM_DEVICE_RESOURCE存在。
//					//就是任何CONTENTID都需要有业务信息和资源适配关系，否则不通过。
//					if(!validate(vo.getEntityid())){
//						//将t_a_ppms_receive_change的status值为-2;
//						try{
//							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2");
//						}catch(Exception e){
//							LOG.error("无效的电子流实体："+vo);	
//						}
//
//						continue;
//					}
					long starttime = System.currentTimeMillis();
					HandleContent cm = new HandleContent(vo);//modify by aiyan 2012-07-24
	        		//构造异步任务
	        		ReflectedTask task = new ReflectedTask(cm, "handleContent", null, null);
	        		//将任务加到运行器中
	        		dataSynTaskRunner.addTask(task);
					//handleContent(vo,listCidAdd,listCidUpdate,listDel);//当发生1这情况的时候，要么是应用新增，要么是修改，还有下线。
				
					LOG.debug("exec vo contentid=" + vo.getEntityid() + ";id="
							+ vo.getId() + ";" + i + ";costtime="
							+ (System.currentTimeMillis() - starttime));
//					exec.execute(new Runnable(){
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try{
//								handleContent(vo,listCidAdd,listCidUpdate,listDel);//当发生1这情况的时候，要么是应用新增，要么是修改，还有下线。
//							}catch(Exception e){
//								LOG.error("handleAPPInfo:" + vo.getEntityid(),e);	
//							}
//						}});
					
				} else if ("2".equals(vo.getType())) {
					//适配关系变更
//					//就是任何CONTENTID都需要在内容表（t_r_gcontent）中存在，否则不通过。
					if(!PPMSDAO.validateGcontent(vo.getEntityid().split("\\|")[1])){
						//将t_a_ppms_receive_change的status值为-2;
						try{
							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2" ,"-2");
						}catch(Exception e){
							LOG.error("无效的电子流实体："+vo);	
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
					//    应用信息和适配信息变更
					//验证这个CONTENTID是否有效：该contentid需要在ppms_v_service和V_DC_CM_DEVICE_RESOURCE存在。
					//就是任何CONTENTID都需要有业务信息和资源适配关系，否则不通过。
					//if(!validate(vo.getEntityid())){//这里不做价格检查，徐惠浜说可以不用对价格检查。add by aiyan 2013-06-07
					if(!PPMSDAO.validateResource(vo.getEntityid())){
						//将t_a_ppms_receive_change的status值为-2;
						try{
							new PPMSDAO().updateReceiveChangeNoTran(vo.getId(),"-2" ,"-2");
						}catch(Exception e){
							LOG.error("无效的电子流实体："+vo);	
						}

						continue;
					}
					HandleContent cm = new HandleContent(vo);//modify by aiyan 2012-07-24
	        		//构造异步任务
	        		ReflectedTask task = new ReflectedTask(cm, "handleContent", null, null);
	        		//将任务加到运行器中
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
//								LOG.error("无效的电子流实体："+vo);	
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
		            LOG.debug("waitToFinished前,让线程睡眠500毫秒！");
		        }
                Thread.sleep(500);
            }
            catch (InterruptedException e1)
            {
                //e1.printStackTrace();
                LOG.error("线程睡眠500毫秒，报错！");
            }
		
		   dataSynTaskRunner.waitToFinished();
	       dataSynTaskRunner.stop();
	        
//	        for (ReceiveChangeVO recVo : receiveChangeList) {
//				if ("1".equals(recVo.getOpt())) {
//					try {
//						HandleContent.grounding(recVo);
//					} catch (DAOException e) {
//						LOG.debug(recVo.getAppid() + "上架失败.....",e);
//					}
//				} else {
//					try {
//						HandleContent.undercarriage(recVo);
//					} catch (DAOException e) {
//						LOG.debug(recVo.getAppid() +" 下架失败....",e);
//					}
//				}
//			}
	        
	        
	        
	        
	        
	        
	        
//		exec.shutdown();
//		// 等待子线程结束，再继续执行下面的代码
//		try {
//			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			LOG.error("exec.awaitTermination发生中断错误",e);
//		}
		LOG.debug("all thread complete");
		
		//  执行完了应用变更流程后,根据opt来执行商品上下架的流程.............
		
		//new PPMSDAO().putMessages(pids);//发送适配资源改变的CONTENTID
		
		//remove by aiyan 2013-05-18价格变更放在内容同步的时候发生。
		//更换V_SERVICE数据（注重价格信息mobileprice）
		//updateVService(cids);
		
		
//		//更新程序包的最后更新时间（目的是要改变select * from v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
//		contentIdSet.addAll(pids);
//		contentIdSet.addAll(cids);
//		updateLastTime(contentIdSet);
		
		String searchIsOpen = "0";//默认不开放
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	        searchIsOpen = module.getItemValue("SEARCH_ISOPEN") ;
		}catch(Exception e){
			LOG.error("SEARCH_ISOPEN not found...");
		}
        if("1".equals(searchIsOpen)){
    		//搜索子系统的文件生成
        	LOG.info("createSearcheFile start...");
        	LOG.info("createSearcheFile(listCidAdd,listCidUpdate,listDel)listCidAdd.SIZE="+listCidAdd.size()+"listCidUpdate.size="+listCidUpdate.size()+"listDel.size:"+listDel.size());
    	try{
        	createSearcheFile(listCidAdd,listCidUpdate,listDel);//remove by aiyan 2013-05-24临时注销。要做全量导入。没有必要生搜索文件。
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
//		LOG.info("syncAndroid:updateLastTime(更新程序包的最后更新时间)开始...");
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
//					LOG.error("出现了部分v_content_last没有更新情况，对程序包最后更新时间有影响。",e);
//				}
//			}
//			
//			fromIndex=toIndex;
//		}while(toIndex<arr.length);
//	}

	private void createSearcheFile(List<String> listCidAdd,List<String> listCidUpdate,List<DelGoodsVO> listCidDel) {
		// TODO Auto-generated method stub
		LOG.info("syncAndroid:createSearcheFile(搜索子系统的文件生成)开始...");
		try {
	
			
			List<SearcharFileVO> data1 = new PPMSDAO().getSearchFileData(listCidAdd,"A");
			List<SearcharFileVO> data2 = new PPMSDAO().getSearchFileData(listCidUpdate,"U");
			List<SearcharFileVO> data3 = new PPMSDAO().getSearchFileDataDel(listCidDel);
			
			List<SearcharFileVO> data = new ArrayList<SearcharFileVO>();
			data.addAll(data1);
			data.addAll(data2);
			data.addAll(data3);
			
			if(data.size()==0){
				LOG.info("createSearcheFile没有数据，故不用生成文件！");
				return;
			}
	        String fileName = SearchFileConfig.LOCALDIR + "temp" +File.separator + "temp_"
	        + PublicUtil.getCurDateTime("yyyyMMddHHmmss") + "_00001.temp";  
	        
	        LOG.info(" SearchFileConfig.LOCALDIR " +  SearchFileConfig.LOCALDIR );
	        LOG.info("当前生成文件路径" + fileName);
	        
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

	                // 防止占用太多内存，每一万行刷新到文件一下。
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
	            LOG.error("写入搜索文件异常 fileName=" + fileName, e);
	        }
	        catch (Throwable e)
	        {
	        	LOG.error("出错文件名fileName=" + fileName, e);
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
	            LOG.error("重命名文件失败，sourceFileName=" + fileName);
	        }
	        
	        
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("search file dao错误：",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.error("search file sql错误：",e);
		}
	}
	
 
    


//	private void notifySearcheSubSystem() {
//		// TODO Auto-generated method stub
//		LOG.info("通知搜索子系统。。。");
//		String search_url = Config.getInstance().getModuleConfig()
//		.getItemValue("SEARCH_URL_Sync");
//		LOG.info("通知搜索子系统。。。-->url:search_url"+search_url);
//		try {
//			HttpUtil.getResponseCodeFromURL(search_url, "utf-8");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			LOG.error("通知搜索子系统失败",e);
//		}
//		LOG.info("通知搜索子系统。。。完成！");
//		
//	}

//	private void updateResource(List<String> list) {
//		// TODO Auto-generated method stub
//		int fromIndex=0;int toIndex=0;int stepSize=10;
//		LOG.info("syncAndroid:updateVService(更换V_SERVICE数据（注重价格信息mobileprice)开始...");
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
//					LOG.error("出现了部分t_a_cm_device_resource没有更新情况，对价格信息有影响",e);
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
//		LOG.info("syncAndroid:updateVService(更换V_SERVICE数据（注重价格信息mobileprice)开始...");
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
//					LOG.error("出现了部分V_SERVICE没有更新情况，对价格信息有影响",e);
//				}
//			}
//			
//			fromIndex=toIndex;
//		}while(toIndex<list.size());
//		
//	}

	private void handleContent(ReceiveChangeVO receiveChangeVO,List listCidAdd,List listCidUpdate,List listCidDel)
			throws BOException {
		
		int syncMode = 0;//同步模式的选择，一般情况应该写0的。0:是正常情况，1:要有上下架消息，要处理FULLDEVICEID字段
		try{
	    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
	    	syncMode = Integer.parseInt(module.getItemValue("SYNC_MODE"));
		}catch(Exception e){};
		
		// 查询本地t_r_gcontent表，
		// 1、存在：则修改，如果二级分类也改变，则做上下架操作
		// 2、不存在，插入到表t_r_gcontent，上架到对应的二级分类下。
		CmContentVO dccVo = null;
		GContentVO vo = null;
		long starttime2 = System.currentTimeMillis();
		try {
			// 存在
			dccVo = APPInfoDAO.getInstance().getCmContent(
					receiveChangeVO.getEntityid());
			vo = APPInfoDAO.getInstance().getGContentVO(
					receiveChangeVO.getEntityid());
		} catch (DAOException e) {
			// TODO Auto-generated catch block             
			// e.printStackTrace();
			LOG.error("handleContent出现错误", e);
			throw new BOException("handleContent("
					+ receiveChangeVO.getEntityid() + ")出错！", e);
		}
		
		LOG.debug("exec vo handleContent1 contentid="+receiveChangeVO.getEntityid()+";id="+receiveChangeVO.getId()+";"+";costtime="+(System.currentTimeMillis()-starttime2));
		//这里首先要过滤掉HTC应用。因为HTC应用是不上MM的二级分类货架。  HTC应用或套餐应用这里处理（HTC的上下架在老的同步完成。而套餐应用是不上二级分类货架。
		//(add 2014-01-07) 定制类App不上架
		//这两者的处理过程在商品库优化就是相同的了。就是把各个信息通过过来，不考虑上下架事情。）16:HTC应用，11:套餐应用，21：定制类App
		if(dccVo!=null&&("16".equals(dccVo.getSubtype())||"11".equals(dccVo.getSubtype())||"21".equals(dccVo.getSubtype()))){
			
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				try {
					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2","-2");
				} catch (DAOException e) {
					LOG.error("subtype="+dccVo.getSubtype()+" (16:HTC应用，11:套餐应用，21：定制类App：) 没有资源："+receiveChangeVO.getId());
				}
				
				return;
			}
			
			LOG.debug(dccVo.getContentid()+" is subtype="+dccVo.getSubtype()+" content... vo!=null"+(vo!=null));
			TransactionDB tdb = null;
			try{
				tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				//String transactionID = ContextUtil.getTransactionID();
				if(vo!=null){
					dccVo.setId(vo.getId());
					// 修改
					dao.updateGContent(dccVo);
					dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid()+ ":1");
					dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
				}else{
					String newId = dao.insertGContent(dccVo);
					dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid()+ ":0");
					dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");
				}

				dao.updateVService(dccVo.getContentid());//价格信息。
				dao.updateDeviceResourseByCid(dccVo.getContentid());//更新他的适配关系。add by aiyan 2013-04-07
				//更新程序包的最后更新时间（目的是要改变select * from v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
				dao.updateLastTime(dccVo.getContentid());
				//dao.updateReceiveChange(receiveChangeVO.getId(),Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"2");
				tdb.commit();
				
				if(vo!=null&&syncMode==1){//修改的话，把FULLDEVICE弄一下。add by aiyan 2013-06-19
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
			LOG.info("dccVo==null&&vo == null,说明电子流和货架都没有这个应用，有问题的数据啊！！"+receiveChangeVO.getEntityid());

			//将t_a_ppms_receive_change的status值为-3;
			try {
				new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-3" ,"-3");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error(e);
			}
			LOG.error("无效的电子流实体："+vo);
	
			
			return;
		}

		if (dccVo==null&&vo != null) {
			// 下架ANDROID根货架下所有该CONTENTID的商品。
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String categoryId = module.getItemValue("ROOT_CATEGORYID");
			String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
			TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				// 下线开始咯。。。。。
				dao.downAllReference(categoryId, vo);//下线商品（android根货架下要下线）
				dao.downAllOperateReference(operateCategoryId, vo);//下线商品(指定的运营货架下要下线)
				
				dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());//下线他的适配关系。add by aiyan 2013-04-07
				
				listCidDel.addAll(dao.getDelGoodsList(categoryId, vo.getId()));
				String transactionID = ContextUtil.getTransactionID();
				// 与数据中心发送消息（上下架）--------------------------------------------
				dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid()+ ":9");
				dao.addMessages(MSGType.RefModifyReq, transactionID);
				//removed by aiyan 2013-06-01 终端门户以及对适配的处理又把他当做应用变更处理。这样就重复做事情了，故丢掉。
				//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//下线他的适配关系。add by aiyan 2013-04-07
				dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":9");//删除榜单！
				// 回修改PPMS的接受数据状态
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
			
			
			///验证没有适配的，下线！！
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				
				// 下架ANDROID根货架下所有该CONTENTID的商品。
				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
				String categoryId = module.getItemValue("ROOT_CATEGORYID");
				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
				TransactionDB tdb = null;
				//Producer producer = null;
				try {
					tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
					// 下线开始咯。。。。。
					dao.downAllReference(categoryId, vo);//下线商品
					dao.downAllOperateReference(operateCategoryId, vo);//下线商品(指定的运营货架下要下线)
					
					dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());//下线他的适配关系。add by aiyan 2013-04-07
					
					listCidDel.addAll(dao.getDelGoodsList(categoryId, vo.getId()));
					String transactionID = ContextUtil.getTransactionID();
					// 与数据中心发送消息（上下架）--------------------------------------------
					dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid()+ ":9");
					dao.addMessages(MSGType.RefModifyReq, transactionID);
					//removed by aiyan 2013-06-01 终端门户以及对适配的处理又把他当做应用变更处理。这样就重复做事情了，故丢掉。
					//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//下线他的适配关系。add by aiyan 2013-04-07
					dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":9");//删除榜单！
					// 回修改PPMS的接受数据状态
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
//				///下适配。更新。法消息。
//				
//				TransactionDB tdb = null;
//				try{
//					LOG.debug("(dccVo!=null&&vo!=null)没有适配哦"+receiveChangeVO.getEntityid());
//					
//					tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
//					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
//					dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());
//					dao.addMessages(MSGType.ContentModifyReq, null,receiveChangeVO.getEntityid() + ":2");//下线他的适配关系。add by aiyan 2013-04-25
//					// 回修改PPMS的接受数据状态
//					dao.updateReceiveChange(receiveChangeVO.getId(),
//							Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
//					tdb.commit();
//				}catch(Exception e){
//					tdb.rollback();
//					LOG.debug("(dccVo!=null&&vo!=null)没有适配哦,这里要下适配，且发消息。出错了！"+receiveChangeVO.getEntityid(),e);
//				}finally{
//					if(tdb!=null){
//						tdb.close();
//					}
//				}
				//下面是以前的逻辑，被注销了。REMOVED BY AIYAN 2013-04-25
//				try{
//					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2");
//				}catch(Exception e){
//					LOG.error("无效的电子流实体,没有适配！hehe："+vo);	
//				}
//				return;
			}else{
				long stime = System.currentTimeMillis();
				
				TransactionDB tdb = null;
				try{
					String transactionID = ContextUtil.getTransactionID();
					tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。目标是希望下面两个方法都在这个事务里面完成。
					PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
					dccVo.setId(vo.getId());
					
					// 修改
					dao.updateGContent(dccVo);
					dao.updateVService(vo.getContentId());//价格信息。
					dao.updateDeviceResourseByCid(vo.getContentId());//更新他的适配关系。add by aiyan 2013-04-07
					//更新程序包的最后更新时间（目的是要改变select * from v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
					dao.updateLastTime(vo.getContentId());
					
					// 与数据中心发送消息（应用数据变更）--------------------------------------------
					dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId()+ ":1");
					//removed by aiyan 2013-05-27大胖说有1（上一个语句）就不用2了
					//dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId()+ ":2");
					dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//新增榜单！//add by aiyan 2013-05-21
					
					//终端门户那边有时候要搞全量同步，这里就就把模式换成1的。好上下架消息。  add by aiyan 2013-06-19
			    	if(syncMode==0){
						//这个地方比较特别，因为T_R_GCONTENT表应用在下线的时候没有删除表中的记录，故
						//出现dccVo!=null&&vo!=null下的情况，会有ANDROID根货架没有商品。这里弥补
						boolean isExistRef = dao.isExistRefInAndrodCategory(vo);
						if(!isExistRef){
							//transactionID = ContextUtil.getTransactionID();
							//dao.downCategoryByTactic(vo);//这个地方可以注销，也可以不注销，先注销一下吧。。。。
							dao.addCategroyByTactic(dccVo);
							//dao.updateDeviceResourseByCid(vo.getContentId());//下线他的适配关系。add by aiyan 2013-04-07
							// 与数据中心发送消息（上下架）-------------------------------------------- 
							//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//适配关系。add by aiyan 2013-04-07
							dao.addMessages(MSGType.RefModifyReq, transactionID);
							//dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//新增榜单！刚开始到货价，是不会有啥统计数据的，故丢弃榜单消息//remove by aiyan 2013-05-21
						}else{
							if (!vo.getAppCateID().equals(dccVo.getAppCateID())) {
								// 如2级分类修改：做上下架操作
								dao.downCategoryByTactic(vo);
								dao.addCategroyByTactic(dccVo);
								// 与数据中心发送消息（上下架）--------------------------------------------
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
					LOG.error("handleContent出现异常。DB事务回滚。"+receiveChangeVO, e);
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
			//如果电子流没有适配，则不能入T_R_GCONTENT。
			if(!PPMSDAO.validateResource(receiveChangeVO.getEntityid())){
				LOG.error("dccVo!=null&&vo==null 没有适配不能入T_R_GCONTENT"+receiveChangeVO.getId());
				//将t_a_ppms_receive_change的status值为-2;
				try{
					new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2" ,"-2");
				}catch(Exception e){
					LOG.error("无效的电子流实体："+vo);	
				}
				return;
			}
			
			TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。目标是希望下面两个方法都在这个事务里面完成。
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				//在T_R_GCONTENT表不存在，所以要新增内容。
				String transactionID = ContextUtil.getTransactionID();
				// 1、插入到表t_r_gcontent中
				String newId = dao.insertGContent(dccVo);// ////////////////////////////2-27号测试点。。。。呵呵。。。。
				
				dao.updateVService(dccVo.getContentid());//价格信息。
				dao.updateDeviceResourseByCid(dccVo.getContentid());//更新他的适配关系。add by aiyan 2013-04-07
				//更新程序包的最后更新时间（目的是要改变select * from v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
				dao.updateLastTime(dccVo.getContentid());
				
				dccVo.setId(newId);
				// 2上架对应的二级分类
				dao.addCategroyByTactic(dccVo);
				// 与数据中心发送消息（货架应用数据变更通知接口-新上线）----------------------------------------------
				dao.addMessages(MSGType.ContentModifyReq, transactionID, dccVo.getContentid()+ ":0");
				// 与数据中心发送消息（上下架）--------------------------------------------
				dao.addMessages(MSGType.RefModifyReq, transactionID);
				//removed by aiyan 2013-05-27大胖说有1（上一个语句）就不用2了
				//dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() + ":2");//下线他的适配关系。add by aiyan 2013-04-07
				//dao.addMessages(MSGType.CountUpdateReq, transactionID,receiveChangeVO.getEntityid()+":0");//新增榜单！刚开始到货价，是不会有啥统计数据的，故丢弃榜单消息//remove by aiyan 2013-05-21
				// 回修改PPMS的接受数据状态
				dao.updateReceiveChange(receiveChangeVO.getId(),
						Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");

				tdb.commit();
				listCidAdd.add(dccVo.getContentid());
				
				
			

			} catch (Exception e) {
				LOG.error("handleContent出现异常。DB事务回滚。"+receiveChangeVO, e);
				if (tdb != null) {
					tdb.rollback();
				}
				throw new BOException("handleContent出现异常。DB事务回滚。"+receiveChangeVO, e);

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
		String[] arr = receiveChangeVO.getEntityid().split("\\|");//如果是资源ID，则这里PPMS传给我们的是PID|CONTENTID	
		String contentid = arr[1];
		try {
			tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
			dao.updateDeviceResourseByCid(contentid);
			
			//更新程序包的最后更新时间（目的是要改变select * from v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
			dao.updateLastTime(contentid);
			
			//电子流发2这种情况很少的。如果发来了，我们就跟数据中心说该应用变更。add by aiyan2013-06-01
			dao.addMessages(MSGType.ContentModifyReq, "",
					contentid + ":1");//单独一个消息，不需要事物ID，所以这里用无事务方式处理，具体就是addMessages方法中事务ID为空字符串。
			
			// 回修改PPMS的接受数据状态
			dao.updateReceiveChange(receiveChangeVO.getId(),
					Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
			tdb.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			LOG.error("handleResource出现错误", e);
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
//			tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
//			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
//			
//			String contentId = vo.getEntityid();
//			dao.updateDeviceResourseByCid(contentId);
////			// 与数据中心发送消息（货架应用数据变更通知接口-适配关系变更）----------------------------------------------
////			dao.addMessages(MSGType.ContentModifyReq, "",
////					vo.getEntityid() + ":2");// ///xxxxx明天继续！2013-2-25  // 这里注销的原因是这个方法一般是TYPE=3这个方式。在handleContent已经处理适配消息了。
//			// 回修改PPMS的接受数据状态
//			dao.updateReceiveChange(vo.getId(),
//					Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
//			tdb.commit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			LOG.error("handleResource出现错误", e);
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
				throw new BOException("在内容和适配都变得情况下，对内容的处理出错！",e);
			}
			//removed by aiyan 2013-06-04 
//			try {
//				handleResource2(receiveChangeVO);
//			} catch (Exception e) {
//				LOG.error(e);
//				throw new BOException("在内容和适配都变得情况下，对适配的处理出错！", e);
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
