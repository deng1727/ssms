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
		final byte[] lock1 = new byte[0];  //��mailInfo[1]-�������� �����

		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"dmbase");
		String[] fNameRegex = module.getItemValue(nameRegex)
				.split(",");
		BaseComicFtpProcessor bp = new BaseComicFtpProcessor();
		String localFileNames[] = bp.process(fNameRegex);// ftp ���ص�����
		if (localFileNames.length == 0){
			msgInfo = "û���ҵ��ļ�<br>";
            throw new BOException("û���ҵ�ͬ���ӿڵ�Ԫ���ݵ��ļ�("+nameRegex+")",
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
					logger.error("�������ˣ��ļ�����"+file.getAbsoluteFile());
					logger.error("�ļ�û���ҵ�����",e1);
					continue;
				}
				BufferedReader br = new BufferedReader(in);
				String line = "";
				String sep = handleSep("0x1F");
				String[] field;
				int lineNumeber = 0;
				
				while ((line = br.readLine()) != null) {
					lineNumeber ++;
					mailInfo[0] = mailInfo[0] + 1;// �ܴ�������������
					field = line.split(sep);
					if(field.length!=fieldLength){
						mailInfo[4] = mailInfo[4] + 1;// ���ݼ�鲻�Ϸ�����������
						if(mailInfo[4]<=100){
							checkFailureRow.append("<br>").append("  ��").append(lineNumeber).append("�������ֶβ���");
						}
						continue;
					}
			    	final VO vo = createVO(field);
			    	if(vo instanceof Validateable){
			    		Validateable val = (Validateable)vo;
			    		val.validate();
			    		if(val.getFieldErrorMsg()!=null){
			    			mailInfo[4] = mailInfo[4] + 1;// ���ݼ�鲻�Ϸ�����������
			    			if(mailInfo[4]<=100){
			    				checkFailureRow.append("<br>").append("  ��").append(lineNumeber).append("�У�").append(val.getFieldErrorMsg());
			    			}
			    			continue;
			    			
			    		}
			    	}
			    	final String s = lineNumeber+"";
			    	//System.out.println(s+":"+line);
			    	this.insert(queue,vo,new StatisticsCallback(){
						public void doStatistics(boolean isSuccess) {
							// TODO Auto-generated method stub
							if(isSuccess){//JDBC���������ɹ�
								//synchronized(lock1){
									mailInfo[1] = mailInfo[1] + 1;// ��������������
								//}
							}else{//JDBC��������ʧ��
								mailInfo[5] = mailInfo[5]+1;
								dbFailureRow.append(s).append(", ");
							}
						}
			    		
			    	});
			    	
				}
				
			}
		}catch(Exception e){
			logger.error("-->"+localFileNames[0]+"ͬ��ʧ��!",e);
			
		}finally{
			logger.debug("file read over...1");
			queue.destory();
			queue.waitToFinished();
			logger.debug("file read over...2");
			
			
			
			
		}
		//��Ҫ����ҵ��֮ǰ�����ݡ�
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
    	msgInfo.append("У���ļ���Ϣ���£�"+verfMsg);
    	msgInfo.append("<br><br>ϵͳ������Ϣ���£�");
         msgInfo.append("<br>������������" + mailInfo[0]);
         msgInfo.append(";<br>�ɹ�������" + mailInfo[1]);
         //msgInfo.append(";<br>�ɹ��޸ģ�" + mailInfo[2]);
         msgInfo.append(";<br>�ɹ�ɾ����" + mailInfo[3]);
         msgInfo.append(";<br>���ݼ�����"+ mailInfo[4]);
         if (mailInfo[4]> 0)
         {
             msgInfo.append(";<br>���ݼ���������У�")
                    .append(checkFailureRow);
         }
         msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + mailInfo[5]);
         if (mailInfo[5] > 0)
         {
             msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                    .append(dbFailureRow.substring(0, dbFailureRow.length()-2))
                    .append("��<br>");
         }
         msgInfo.append("<br>");
    	 this.msgInfo = msgInfo.toString();
    }
    
    public String getMsgInfo(){
    	return msgInfo;
    }

    protected String handleSep(String sep){
    	
		if(sep!=null&&sep.startsWith("0x")){
			// 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
