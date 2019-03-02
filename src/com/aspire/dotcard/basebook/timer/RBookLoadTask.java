package com.aspire.dotcard.basebook.timer;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.BaseBookFtpProcess;
import com.aspire.dotcard.basebook.biz.RBookBO;
import com.aspire.dotcard.basebook.config.BaseBookConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class RBookLoadTask extends TimerTask {
	protected static JLogger logger = LoggerFactory.getLogger(RBookLoadTask.class);
	public void run() {
		logger.debug("import book base begin!");
		//Ftp����
		BaseBookFtpProcess ftp = new BaseBookFtpProcess();
		
		StringBuffer msgInfo = new StringBuffer();
		String encoding = BaseBookConfig.get("fileEncoding");
		String sep = BaseBookConfig.get("BBookListSep");
		if(null==encoding){
			encoding = "UTF-8";
		}
		if(null==sep){
			sep = "|";
		}
		
		//��ȡҪ����Ķ���
    	if (sep.startsWith("0x")) 
		{
			// 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
			String s = sep.substring(2,sep.length());
			int i1 = Integer.parseInt(s,16);
			char c = (char)i1;
			sep = String.valueOf(c);
		}		
		
		/**
		 * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
		 */			
		int[] rs = new int[6];		
		synBookType(ftp, msgInfo, encoding, sep, rs);
		
		msgInfo.append("<br>");
		//������Ϣͬ��
		rs = new int[6];
		synBookAuthor(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		//ͼ����Ϣ
		rs = new int[6];
		synBookInfo(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		//������Ϣ
		rs = new int[4];
		synBookMonth(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//�Ƽ�ͼ����Ϣ
		rs = new int[6];
		synBookRecommend(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//ר����Ϣ
		rs = new int[6];
		synBookArea(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		
		//ר��������Ϣ
		rs = new int[6];
		synBookAreaReference(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		
		//������Ϣ
		//������  0,�������������1��������2�������3���ʻ���4��������5,У�鲻�ϸ�6�����ݴ���ʧ��
		rs = new int[7];
		synBookTotalRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		//������
		rs = new int[7];
		synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		//������
		rs = new int[7];
		synBookWeekRank(ftp, msgInfo, encoding, sep, rs);		
		
		//���¸���������Ʒ����
		RBookBO.getInstance().updateCateTotal();
		
		//�����ʼ�
		logger.info("send mail begin!");
		String[] mailTo = BaseBookConfig.get("BaseBookSynMailto").split(",");
		String synBaseBookSubject = BaseBookConfig.get("synBaseBookSubject");
		Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
		logger.info("send mail end!");
		logger.debug("import book base end!");
	}
	
	/**
	 * ͼ����������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookTotalRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book totalrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("����ͼ����������Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookTotalRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�ͼ����������Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookTotalRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ����³��������а�"+rs[1]);
				msgInfo.append(";<br>�ɹ����µ�������а�"+rs[2]);
				msgInfo.append(";<br>�ɹ������ʻ������а�"+rs[3]);
				msgInfo.append(";<br>�ɹ��������������а�"+rs[4]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book totalrank info begin!");
	}
	
	/**
	 * ͬ��ͼ����������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookMonthRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book monthrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("����ͼ����������Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookMonthRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�ͼ����������Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookMonthRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ����³��������а�"+rs[1]);
				msgInfo.append(";<br>�ɹ����µ�������а�"+rs[2]);
				msgInfo.append(";<br>�ɹ������ʻ������а�"+rs[3]);
				msgInfo.append(";<br>�ɹ��������������а�"+rs[4]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book monthrank info end!");
	}
	
	/**
	 * ͬ��ͼ����������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookWeekRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book weekrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("����ͼ����������Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookWeekRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�ͼ����������Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookWeekRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ����³��������а�"+rs[1]);
				msgInfo.append(";<br>�ɹ����µ�������а�"+rs[2]);
				msgInfo.append(";<br>�ɹ������ʻ������а�"+rs[3]);
				msgInfo.append(";<br>�ɹ��������������а�"+rs[4]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("����ͼ����������Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book weekrank info end!");
	}	
	
	/**
	 * ͬ��ר��������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookAreaReference(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book area reference info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("����ר��������Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAreaReferenceRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�ר��������Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookReference(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("����ר��������Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("����ר��������Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book area reference info end!");
	}	
	
	/**
	 * ͬ��ר����Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookArea(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book area info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("����ר����Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAreaRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�ר����Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookArea(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("����ר����Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("����ר����Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book aren info end!");
	}	
	
	/**
	 * ͬ��ͼ���Ƽ���Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookRecommend(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book recommend info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("��������Ƽ�ͼ����Ϣ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookRecommendRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ������Ƽ�ͼ����Ϣ�����ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookRecommend(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("��������Ƽ�ͼ����Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("��������Ƽ�ͼ����Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book recommend info end!");
	}	
	
	/**
	 * ͬ��ͼ�������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookMonth(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book month info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("�������ͼ��������ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookMonthRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�����ͼ����������ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookMonth(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[2]);
                if(rs[2] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[3]);
                if(rs[3] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("�������ͼ��������ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("�������ͼ��������ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book month info end!");
	}	
	
	/**
	 * ͬ��ͼ����Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookInfo(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		try{
			msgInfo.append("�������ͼ����ϢԪ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�����ͼ�������ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBook(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("�������ͼ����Ϣ���ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("�������ͼ����Ϣ���ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book info end!");
	}
	
	/**
	 * ͬ��������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookAuthor(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book author info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		try{
			msgInfo.append("�������ͼ������Ԫ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAuthorRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�����ͼ�����������ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookAuthor(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("�������ͼ���������ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("�������ͼ���������ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book author info end!");
	}
	
	/**
	 * ͬ��ͼ�������Ϣ
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookType(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book type begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//ͼ�������Ϣ
		try{
			msgInfo.append("�������ͼ�����Ԫ���ݽ����");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookTypeRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp ���ص�����

			if(lfs.length==0){
				msgInfo.append("û���ҵ�����ͼ����������ļ�");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookType(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("������������"+rs[0]);
				msgInfo.append(";<br>�ɹ�������"+rs[1]);
				msgInfo.append(";<br>�ɹ��޸ģ�"+rs[2]);
				msgInfo.append(";<br>�ɹ����ߣ�"+rs[3]);
				msgInfo.append(";<br>���ݼ�鲻�Ϸ���"+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>���ݴ���ʧ�ܣ�"+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("�С�");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("�������ͼ��������ݲ���ʧ��"+e);
			logger.error(e);
			msgInfo.append("�������ͼ��������ݲ���ʧ��"+e);
			msgInfo.append("<br>");			
		}
		
		logger.info("import base book type end!");
	}

}
