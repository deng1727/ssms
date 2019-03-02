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
		final byte[] lock2 = new byte[0];  //��mailInfo[2]-�޸����� �����
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
		try{
			//Ŀ�ģ�������������һ����������ļ�¼�Ǹ�"insert or update"��
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
					logger.debug("hehe....line"+line);
					lineNumeber ++;
					mailInfo[0] = mailInfo[0] + 1;// �ܴ�������������
					field = line.split(sep);
					if(field.length!=fieldLength){
						mailInfo[4] = mailInfo[4] + 1;// ���ݼ�鲻�Ϸ�����������
						if(mailInfo[4]<100){
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
			    	final int s = lineNumeber;
			    	if(existsAllKey.contains(vo.getKey())){
				    	this.update(queue, vo,new StatisticsCallback(){
	                        //���˴����߳�ִ�е�JDBC����ʧ�ܣ���Ҫ��¼ʧ��������
							public void doStatistics(boolean isSuccess) {
								// TODO Auto-generated method stub
								if(isSuccess){//JDBC�޸Ĳ����ɹ�
									synchronized(lock2){
										mailInfo[2] = mailInfo[2]+ 1;// �޸�����������
									}
								}else{//JDBC�޸Ĳ���ʧ��
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
			logger.error("-->"+localFileNames[0]+"ͬ��ʧ��!",e);
			
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
    	msgInfo.append("У���ļ���Ϣ���£�"+verfMsg);
    	msgInfo.append("<br><br>ϵͳ������Ϣ���£�");
         msgInfo.append("<br>������������" + mailInfo[0]);
         //msgInfo.append(";<br>�ɹ�������" + mailInfo[1]);
         msgInfo.append(";<br>�ɹ��޸ģ�" + mailInfo[2]);
         //msgInfo.append(";<br>�ɹ�ɾ����" + mailInfo[3]);
         msgInfo.append(";<br>���ݼ�����"+ mailInfo[4]);
         if (mailInfo[4]> 0)
         {
             msgInfo.append(";<br>���ݼ���������У�")
                    .append(checkFailureRow);
         }
         msgInfo.append(";<br>���ݴ���ʧ�ܣ����ڸ����ݱ�ʶ�����ڣ���" + mailInfo[5]);
         if (mailInfo[5] > 0)
         {
             msgInfo.append(";<br>���ݱ�ʶ�����ھ����У���")
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
    
	//�õ�ĳһ��ʵ���ȫ��ID,Ŀ�ģ����µ���ļ�¼�����ȫ�������о��޸ļ�¼�����û�о�������¼��
    protected Set getExistsAllKey(String tableName,String key) throws BOException{
        // �Ѵ��ڵ�ȫ����id�嵥
        Set existsAllKey = new HashSet();
        try {
        	existsAllKey = BaseComicDAO.getInstance().getExistsAllKey(tableName,key);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("��ȡȫ���ؼ��ֳ�����:",e);
			throw new BOException("��ȡȫ���ؼ��ֳ�����:",DataSyncConstants.FAILURE_DATA_ERROR);
		}
		return existsAllKey;

	}
    
	protected abstract VO createVO(String[] field);
	protected abstract BufferQueue createQueue();
	protected abstract void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback);
}
