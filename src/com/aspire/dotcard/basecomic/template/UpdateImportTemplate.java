package com.aspire.dotcard.basecomic.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BaseComicFtpProcessor;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.FileUtil;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.common.Validateable;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.vo.VO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public abstract class UpdateImportTemplate {
	
	protected static JLogger logger = LoggerFactory.getLogger(UpdateImportTemplate.class);
	
	protected String nameRegex;
	protected String tableName;
	protected String key;
	protected int fieldLength;
	
	protected String msgInfo;
	
	public void importFile() throws  BOException, IOException{
		final int[] mailInfo={0,0,0,0,0,0};
		final byte[] lock2 = new byte[0];  //给mailInfo[2]-修改行数 配的锁
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"dmbase");
		String[] fNameRegex = module.getItemValue(nameRegex)
				.split(",");
		BaseComicFtpProcessor bp = new BaseComicFtpProcessor();
		String localFileNames[] = bp.process(fNameRegex);// ftp 下载到本地
		if (localFileNames.length == 0){
			msgInfo = "没有找到文件<br>";
            throw new BOException("没有找到同步接口的元数据的文件("+nameRegex+")",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
		StringBuffer checkFailureRow =  new StringBuffer();
		final StringBuffer dbFailureRow = new StringBuffer();
		BufferQueue queue = createQueue();

		String verfFile = FileUtil.getVerfFile(localFileNames);
		String verfMsg = null;
		if(verfFile!=null){
			verfMsg = FileUtil.getVerfMsg(verfFile);
		}
		try{
			//目的：决定接下来的一条即将导入的记录是该"insert or update"。
			final Set existsAllKey = getExistsAllKey(tableName,key);
			
			for (int k = 0; k < localFileNames.length; k++) {
				if(localFileNames[k].equals(verfFile)){
					continue;
				}
				File file = new File(localFileNames[k]);
				Reader in = null;
				try {
					in = new FileReader(file);
					logger.debug("hehe....update"+file.getAbsoluteFile());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					logger.error("被忽略了，文件名："+file.getAbsoluteFile());
					logger.error("文件没有找到错误：",e1);
					continue;
				}
				BufferedReader br = new BufferedReader(in);
				String line = "";
				String sep = handleSep("0x1F");
				String[] field;
				int lineNumeber = 0;
				
				while ((line = br.readLine()) != null) {
					logger.debug("hehe....line"+line);
					lineNumeber ++;
					mailInfo[0] = mailInfo[0] + 1;// 总处理行数计数器
					field = line.split(sep);
					if(field.length!=fieldLength){
						mailInfo[4] = mailInfo[4] + 1;// 数据检查不合法行数计数器
						if(mailInfo[4]<100){
							checkFailureRow.append("<br>").append("  第").append(lineNumeber).append("行数据字段不够");
						}
						continue;
					}
			    	final VO vo = createVO(field);
			    	if(vo instanceof Validateable){
			    		Validateable val = (Validateable)vo;
			    		val.validate();
			    		if(val.getFieldErrorMsg()!=null){
			    			mailInfo[4] = mailInfo[4] + 1;// 数据检查不合法行数计数器
			    			if(mailInfo[4]<=100){
			    				checkFailureRow.append("<br>").append("  第").append(lineNumeber).append("行，").append(val.getFieldErrorMsg());
			    			}
			    			continue;
			    			
			    		}
			    	}
			    	final int s = lineNumeber;
			    	if(existsAllKey.contains(vo.getKey())){
				    	this.update(queue, vo,new StatisticsCallback(){
	                        //当此处多线程执行的JDBC操作失败，则要记录失败数量。
							public void doStatistics(boolean isSuccess) {
								// TODO Auto-generated method stub
								if(isSuccess){//JDBC修改操作成功
									synchronized(lock2){
										mailInfo[2] = mailInfo[2]+ 1;// 修改行数计数器
									}
								}else{//JDBC修改操作失败
									mailInfo[5] = mailInfo[5]+1;
									dbFailureRow.append(s).append(", ");
								}
							}
				    	});
			    	}else{
			    		mailInfo[5] = mailInfo[5]+1;
						dbFailureRow.append(s).append(", ");
			    	}
			    	
			    	
				}
			}
		}catch(Exception e){
			logger.error("-->"+localFileNames[0]+"同步失败!",e);
			
		}finally{
			logger.debug("file read over...1");
			queue.destory();
			queue.waitToFinished();
			logger.debug("file read over...2");
			setMsgInfo(mailInfo,checkFailureRow,dbFailureRow,verfMsg);
			logger.info(getMsgInfo());
			System.out.println("--->"+getMsgInfo());
		}
		
		
	}
	protected void setMsgInfo(int[] mailInfo,StringBuffer checkFailureRow,StringBuffer dbFailureRow,String verfMsg){
    	StringBuffer msgInfo = new StringBuffer();
    	msgInfo.append("校验文件信息如下："+verfMsg);
    	msgInfo.append("<br><br>系统处理信息如下：");
         msgInfo.append("<br>共处理行数：" + mailInfo[0]);
         //msgInfo.append(";<br>成功新增：" + mailInfo[1]);
         msgInfo.append(";<br>成功修改：" + mailInfo[2]);
         //msgInfo.append(";<br>成功删除：" + mailInfo[3]);
         msgInfo.append(";<br>数据检查错误："+ mailInfo[4]);
         if (mailInfo[4]> 0)
         {
             msgInfo.append(";<br>数据检查出错具体行：")
                    .append(checkFailureRow);
         }
         msgInfo.append(";<br>数据处理失败（由于该内容标识不存在）：" + mailInfo[5]);
         if (mailInfo[5] > 0)
         {
             msgInfo.append(";<br>内容标识不存在具体行：第")
                    .append(dbFailureRow.substring(0, dbFailureRow.length()-2))
                    .append("行<br>");
         }
         msgInfo.append("<br>");
    	 this.msgInfo = msgInfo.toString();
    }
    
    public String getMsgInfo(){
    	return msgInfo;
    }

    protected String handleSep(String sep){
		if(sep!=null&&sep.startsWith("0x")){
			// 0x开头的，表示是16进制的，需要转换
			String s = sep.substring(2, sep.length());
			int i = Integer.parseInt(s, 16);
			char c = (char) i;
			sep = String.valueOf(c);
		}
		return sep;
    }
    
	//得到某一个实体的全量ID,目的：当新导入的记录在这个全量数据中就修改记录，如果没有就新增记录。
    protected Set getExistsAllKey(String tableName,String key) throws BOException{
        // 已存在的全量基id清单
        Set existsAllKey = new HashSet();
        try {
        	existsAllKey = BaseComicDAO.getInstance().getExistsAllKey(tableName,key);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取全量关键字出错了:",e);
			throw new BOException("获取全量关键字出错了:",DataSyncConstants.FAILURE_DATA_ERROR);
		}
		return existsAllKey;

	}
    
	protected abstract VO createVO(String[] field);
	protected abstract BufferQueue createQueue();
	protected abstract void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback);
}
