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
import com.aspire.dotcard.basecomic.common.Task;
import com.aspire.dotcard.basecomic.common.Validateable;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.vo.VO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public abstract class InsertImportTemplate {
	
	protected static JLogger logger = LoggerFactory.getLogger(InsertImportTemplate.class);
	
	protected String nameRegex;
	protected int fieldLength;
	
	protected String msgInfo;
	
	public void importFile() throws  BOException, IOException{
		final int[] mailInfo={0,0,0,0,0,0};
		final byte[] lock1 = new byte[0];  //给mailInfo[1]-新增行数 配的锁

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
		long start = System.currentTimeMillis();
		try{
			for (int k = 0; k < localFileNames.length; k++) {
				if(localFileNames[k].equals(verfFile)){
					continue;
				}
				File file = new File(localFileNames[k]);
				Reader in = null;
				try {
					in = new FileReader(file);
					//System.out.println(file.getAbsoluteFile());
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
					lineNumeber ++;
					mailInfo[0] = mailInfo[0] + 1;// 总处理行数计数器
					field = line.split(sep);
					if(field.length!=fieldLength){
						mailInfo[4] = mailInfo[4] + 1;// 数据检查不合法行数计数器
						if(mailInfo[4]<=100){
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
			    	final String s = lineNumeber+"";
			    	//System.out.println(s+":"+line);
			    	this.insert(queue,vo,new StatisticsCallback(){
						public void doStatistics(boolean isSuccess) {
							// TODO Auto-generated method stub
							if(isSuccess){//JDBC新增操作成功
								//synchronized(lock1){
									mailInfo[1] = mailInfo[1] + 1;// 新增行数计数器
								//}
							}else{//JDBC新增操作失败
								mailInfo[5] = mailInfo[5]+1;
								dbFailureRow.append(s).append(", ");
							}
						}
			    		
			    	});
			    	
				}
				
			}
		}catch(Exception e){
			logger.error("-->"+localFileNames[0]+"同步失败!",e);
			
		}finally{
			logger.debug("file read over...1");
			queue.destory();
			queue.waitToFinished();
			logger.debug("file read over...2");
			
			
			
			
		}
		//需要清理本业务之前的数据。
		mailInfo[3] = delete();
		setMsgInfo(mailInfo,checkFailureRow,dbFailureRow,verfMsg);
		logger.info(getMsgInfo());
		System.out.println("-insertImportTemplate-timer->"+(System.currentTimeMillis()-start));
		System.out.println("--->"+getMsgInfo());
		
	}

	private void insert(BufferQueue queue, final VO vo,final StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		queue.addTask(new Task(){
			public void task() throws Throwable {
				// TODO Auto-generated method stub
				addData(vo,statisticsCallback);
			}
		});
	}
       

	protected void setMsgInfo(int[] mailInfo,StringBuffer checkFailureRow,StringBuffer dbFailureRow,String verfMsg){
    	StringBuffer msgInfo = new StringBuffer();
    	msgInfo.append("校验文件信息如下："+verfMsg);
    	msgInfo.append("<br><br>系统处理信息如下：");
         msgInfo.append("<br>共处理行数：" + mailInfo[0]);
         msgInfo.append(";<br>成功新增：" + mailInfo[1]);
         //msgInfo.append(";<br>成功修改：" + mailInfo[2]);
         msgInfo.append(";<br>成功删除：" + mailInfo[3]);
         msgInfo.append(";<br>数据检查错误："+ mailInfo[4]);
         if (mailInfo[4]> 0)
         {
             msgInfo.append(";<br>数据检查出错具体行：")
                    .append(checkFailureRow);
         }
         msgInfo.append(";<br>数据处理失败：" + mailInfo[5]);
         if (mailInfo[5] > 0)
         {
             msgInfo.append(";<br>数据处理失败具体行：第")
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
	protected abstract VO createVO(String[] field);
	protected abstract BufferQueue createQueue();
	protected abstract void addData(VO vo, StatisticsCallback statisticsCallback);
	protected abstract int delete();
}
