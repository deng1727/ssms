package com.aspire.ponaadmin.web.dataexport.channel.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig;
import com.aspire.ponaadmin.web.dataexport.channel.dao.ThirdChannelMkDao;

public class ThirdChannelMkBo {
	
	protected static JLogger logger = LoggerFactory.getLogger(ThirdChannelMkBo.class);

	private static ThirdChannelMkBo bo = new ThirdChannelMkBo();
	
	public static ThirdChannelMkBo getInstance(){
		return bo;
	}
	
	/**
	 * ȫ����Ϣ
	 */
	private List all = null;
	
	/**
	 * �����Ϣ
	 */
	private List free = null;
	
	/**
	 * ������Ϣ
	 */
	private List pay = null;
	
	/**
	 * ������󸶷�
	 */
	private List tb = null;
	
	/**
	 * ����WWW���»���������Ϣ
	 * @param sb
	 */
	public void exportWwwZx(StringBuffer sb,String localDir){
		logger.debug("ThirdChannelMkBo.exportWwwZx begin!");
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWWWData(all, free, pay,tb,ChannelConfig.wwwZXCategoryId);
		} catch (DAOException e) {
			logger.error("����WWW����������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WWW���»��� ȫ�������ļ� ��ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WWW���»��ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
				logger.debug("����WWW���»��� ȫ�������ļ����");
			} catch (BOException e) {
				logger.error("����WWW���»��ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WWW���»��ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WWW���»�����������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("����WWW���»��ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���»�����������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���»��ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���»��ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WWW���»��ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("����WWW���»��ܸ��������ļ���ʼ");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WWW���»��ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���»��ܸ��������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���»��ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���»��ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWW���»��ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WWW���»���������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WWW���»��ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���»���������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���»��ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���»��ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWW���»��ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WWW���»���������Ϊ��!");
//			sb.append("<br/>");
//		}		
		logger.debug("ThirdChannelMkBo.exportWwwZx end!");
	}
	
	/**
	 * ����WWW���Ȼ���������Ϣ
	 * @param sb
	 */
	public void exportWwwZr(StringBuffer sb,String localDir){
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWWWData(all, free, pay,tb, ChannelConfig.wwwZRCategoryId);
		} catch (DAOException e) {
			logger.error("����WWW����������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");	
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WWW���Ȼ���ȫ�������ļ���ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WWW���Ȼ��ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
				logger.debug("����WWW���Ȼ���ȫ�������ļ����");
			} catch (BOException e) {
				logger.error("����WWW���Ȼ��ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WWW���Ȼ��ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WWW���Ȼ�����������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("����WWW���Ȼ��ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���Ȼ�����������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���Ȼ��ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���Ȼ��ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WWW���Ȼ��ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("����WWW���Ȼ��ܸ��������ļ���ʼ");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WWW���Ȼ��ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���Ȼ��ܸ��������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���Ȼ��ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���Ȼ��ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWW���Ȼ��ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WWW���Ȼ���������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WWW���Ȼ��ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWW���Ȼ���������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WWW���Ȼ��ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWW���Ȼ��ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWW���Ȼ��ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}
//		}else{
//			sb.append("WWW���Ȼ���������Ϊ�գ�δ�����ļ�!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * ����WWWС���Ƽ�������Ϣ
	 * @param sb
	 */
	public void exportWwwXbtj(StringBuffer sb,String localDir){
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWWWData(all, free, pay,tb, ChannelConfig.wwwXBTJCategoryId);
		} catch (DAOException e) {
			logger.error("����WWWС���Ƽ�������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");	
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WWWС���Ƽ����������ļ���ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WWWС���Ƽ����ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
			} catch (BOException e) {
				logger.error("����WWWС���Ƽ����ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WWWС���Ƽ����ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
				logger.debug("����WWWС���Ƽ�����ȫ�������ļ����");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WWWС���Ƽ�������������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);				
					sb.append("����WWWС���Ƽ����ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWWС���Ƽ�������������ļ����");
				} catch (BOException e) {
					logger.error("����WWWС���Ƽ����ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWWС���Ƽ����ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WWWС���Ƽ����ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("����WWWС���Ƽ����ܸ��������ļ���ʼ");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WWWС���Ƽ����ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWWС���Ƽ����ܸ��������ļ����");
				} catch (BOException e) {
					logger.error("����WWWС���Ƽ����ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWWС���Ƽ����ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWWС���Ƽ����ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WWWС���Ƽ�����������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WWWС���Ƽ����ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WWWС���Ƽ�����������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WWWС���Ƽ����ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WWWС���Ƽ����ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WWWС���Ƽ����ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WWWС���Ƽ�����������Ϊ�գ�δ�����ļ�!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * ����WAP���»���������Ϣ
	 * @param sb
	 */
	public void exportWapZx(StringBuffer sb,String localDir){
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWapData(all, free, pay,tb, ChannelConfig.wapZXCategoryId);
		} catch (DAOException e) {
			logger.error("����WAP����������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");	
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WAP���»���ȫ�������ļ���ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WAP���»��ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
				logger.debug("����WAP���»���ȫ�������ļ����");
			} catch (BOException e) {
				logger.error("����WAP���»��ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WAP���»��ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WAP���»�����������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("����WAP���»��ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAP���»�����������ļ����");
				} catch (BOException e) {
					logger.error("����WAP���»��ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���»��ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WAP���»��ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				payFileName = localDir+File.separator+payFileName;				
				try {
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WAP���»��ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
				} catch (BOException e) {
					logger.error("����WAP���»��ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���»��ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAP���»��ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WAP���»���������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WAP���»��ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAP���»���������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WAP���»��ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���»��ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAP���»��ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAP���»���������Ϊ�գ�δ�����ļ�!");
//			sb.append("<br/>");
//		}		
	}
	
	/**
	 * ����WAP���Ȼ���������Ϣ
	 * @param sb
	 */
	public void exportWapZr(StringBuffer sb,String localDir){
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWapData(all, free, pay,tb, ChannelConfig.wapZRCategoryId);
		} catch (DAOException e) {
			logger.error("����WAP����������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");	
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WAP���Ȼ���ȫ�������ļ���ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WAP���Ȼ��ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
				logger.debug("����WAP���Ȼ���ȫ�������ļ����");
			} catch (BOException e) {
				logger.error("����WAP���Ȼ��ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WAP���Ȼ��ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WAP���Ȼ�����������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("����WAP���Ȼ��ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAP���Ȼ�����������ļ����");
				} catch (BOException e) {
					logger.error("����WAP���Ȼ��ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���Ȼ��ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WAP���Ȼ��ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("����WAP���Ȼ��ܸ��������ļ���ʼ");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WAP���Ȼ��ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAP���Ȼ��ܸ��������ļ����");
				} catch (BOException e) {
					logger.error("����WAP���Ȼ��ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���Ȼ��ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAP���Ȼ��ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WAP���Ȼ���������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WAP���Ȼ��ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAP���Ȼ���������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WAP���Ȼ��ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAP���Ȼ��ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAP���Ȼ��ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAP���Ȼ���������Ϊ�գ�δ�����ļ�!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * ����WAPС���Ƽ�������Ϣ
	 * @param sb
	 */
	public void exportWapXbtj(StringBuffer sb,String localDir){
		all = new ArrayList();
		free = new ArrayList();
		pay = new ArrayList();
		tb = new ArrayList();
		try {
			ThirdChannelMkDao.getInstance().getWapData(all, free, pay, tb,ChannelConfig.wapXBTJCategoryId);
		} catch (DAOException e) {
			logger.error("����WAPС���Ƽ�������Ϣʧ��",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");	
			allFileName = localDir+File.separator+allFileName;
			//����ȫ������
			try {
				logger.debug("����WAPС���Ƽ�����ȫ�������ļ���ʼ");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("����WAPС���Ƽ����ܣ� ����ȫ��������Ϣ�ļ��ɹ�����"+(all.size()-1)+"������ ");
				sb.append("<br/>");
				logger.debug("����WAPС���Ƽ�����ȫ�������ļ����");
			} catch (BOException e) {
				logger.error("����WAPС���Ƽ����ܣ� ȫ��������Ϣ�����ļ�ʧ�ܣ�",e);
				sb.append("����WAPС���Ƽ����ܣ� ����ȫ��������Ϣ�ļ�ʧ��");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("����WAPС���Ƽ�������������ļ���ʼ");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("����WAPС���Ƽ����ܣ� �������������Ϣ�ļ��ɹ�����"+(free.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAPС���Ƽ�������������ļ����");
				} catch (BOException e) {
					logger.error("����WAPС���Ƽ����ܣ� ���������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAPС���Ƽ����ܣ� �������������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("����WAPС���Ƽ����ܣ� ���������Ϣ�ļ�ʧ�ܣ�û�����������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("����WAPС���Ƽ����ܸ��������ļ���ʼ");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("����WAPС���Ƽ����ܣ� ���ɸ���������Ϣ�ļ��ɹ�����"+(pay.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAPС���Ƽ����ܸ��������ļ����");
				} catch (BOException e) {
					logger.error("����WAPС���Ƽ����ܣ� ����������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAPС���Ƽ����ܣ� ���ɸ���������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAPС���Ƽ����ܣ� ���ɸ�����Ϣ�ļ�ʧ�ܣ�û�и���������Ϣ��");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("����WAPС���Ƽ�����������󸶷������ļ���ʼ");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("����WAPС���Ƽ����ܣ� ����������󸶷�������Ϣ�ļ��ɹ�����"+(tb.size()-1)+"������ ");
					sb.append("<br/>");
					logger.debug("����WAPС���Ƽ�����������󸶷������ļ����");
				} catch (BOException e) {
					logger.error("����WAPС���Ƽ����ܣ� ������󸶷�������Ϣ�����ļ�ʧ�ܣ�",e);
					sb.append("����WAPС���Ƽ����ܣ� ����������󸶷�������Ϣ�ļ�ʧ��");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("����WAPС���Ƽ����ܣ� ����������󸶷���Ϣ�ļ�ʧ�ܣ�û��������󸶷�������Ϣ��");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAPС���Ƽ�����������Ϊ�գ�δ�����ļ�!");
//			sb.append("<br/>");
//		}
	}	
}
