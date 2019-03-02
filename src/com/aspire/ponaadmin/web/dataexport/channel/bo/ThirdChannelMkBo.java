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
	 * 全部信息
	 */
	private List all = null;
	
	/**
	 * 免费信息
	 */
	private List free = null;
	
	/**
	 * 付费信息
	 */
	private List pay = null;
	
	/**
	 * 先体验后付费
	 */
	private List tb = null;
	
	/**
	 * 导出WWW最新货架数据信息
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
			logger.error("导出WWW最新数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WWW最新货架 全部数据文件 开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WWW最新货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
				logger.debug("生成WWW最新货架 全部数据文件完成");
			} catch (BOException e) {
				logger.error("导出WWW最新货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WWW最新货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WWW最新货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最新货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最新货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最新货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WWW最新货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WWW最新货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("生成WWW最新货架付费数据文件开始");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最新货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最新货架付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最新货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WWW最新货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW最新货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZX");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WWW最新货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最新货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最新货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最新货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WWW最新货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW最新货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WWW最新货架下数据为空!");
//			sb.append("<br/>");
//		}		
		logger.debug("ThirdChannelMkBo.exportWwwZx end!");
	}
	
	/**
	 * 导出WWW最热货架数据信息
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
			logger.error("导出WWW最热数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");	
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WWW最热货架全部数据文件开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WWW最热货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
				logger.debug("生成WWW最热货架全部数据文件完成");
			} catch (BOException e) {
				logger.error("导出WWW最热货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WWW最热货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WWW最热货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最热货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最热货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最热货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WWW最热货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WWW最热货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("生成WWW最热货架付费数据文件开始");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最热货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最热货架付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最热货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WWW最热货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW最热货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "ZR");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WWW最热货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WWW最热货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW最热货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW最热货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WWW最热货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW最热货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}
//		}else{
//			sb.append("WWW最热货架下数据为空，未导出文件!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * 导出WWW小编推荐数据信息
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
			logger.error("导出WWW小编推荐数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");	
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WWW小编推荐货架数据文件开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WWW小编推荐货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
			} catch (BOException e) {
				logger.error("导出WWW小编推荐货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WWW小编推荐货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
				logger.debug("生成WWW小编推荐货架全部数据文件完成");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WWW小编推荐货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);				
					sb.append("导出WWW小编推荐货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW小编推荐货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW小编推荐货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WWW小编推荐货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WWW小编推荐货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("生成WWW小编推荐货架付费数据文件开始");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WWW小编推荐货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW小编推荐货架付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW小编推荐货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WWW小编推荐货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW小编推荐货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WWW").replaceAll("BBB", "XBTJ");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WWW小编推荐货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WWW小编推荐货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WWW小编推荐货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WWW小编推荐货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WWW小编推荐货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WWW小编推荐货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WWW小编推荐货架下数据为空，未导出文件!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * 导出WAP最新货架数据信息
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
			logger.error("导出WAP最新数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");	
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WAP最新货架全部数据文件开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WAP最新货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
				logger.debug("生成WAP最新货架全部数据文件完成");
			} catch (BOException e) {
				logger.error("导出WAP最新货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WAP最新货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WAP最新货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最新货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP最新货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP最新货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WAP最新货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WAP最新货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				payFileName = localDir+File.separator+payFileName;				
				try {
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最新货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
				} catch (BOException e) {
					logger.error("导出WAP最新货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WAP最新货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP最新货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZX");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WAP最新货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最新货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP最新货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP最新货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WAP最新货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP最新货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAP最新货架下数据为空，未导出文件!");
//			sb.append("<br/>");
//		}		
	}
	
	/**
	 * 导出WAP最热货架数据信息
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
			logger.error("导出WAP最热数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");	
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WAP最热货架全部数据文件开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WAP最热货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
				logger.debug("生成WAP最热货架全部数据文件完成");
			} catch (BOException e) {
				logger.error("导出WAP最热货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WAP最热货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WAP最热货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最热货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP最热货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP最热货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WAP最热货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WAP最热货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("生成WAP最热货架付费数据文件开始");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最热货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP最热货架付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP最热货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WAP最热货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP最热货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "ZR");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WAP最热货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WAP最热货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP最热货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP最热货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WAP最热货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP最热货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAP最热货架下数据为空，未导出文件!");
//			sb.append("<br/>");
//		}
	}	
	
	/**
	 * 导出WAP小编推荐数据信息
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
			logger.error("导出WAP小编推荐数据信息失败",e);
		}
//		if(all.size()!=0){
			sb.append("<br/>");
			String fileName = DataExportTools.parseFileName(ChannelConfig.exportAllFile);
			String allFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");	
			allFileName = localDir+File.separator+allFileName;
			//导出全部数据
			try {
				logger.debug("生成WAP小编推荐货架全部数据文件开始");
				DataExportTools.writeToCSVFile(allFileName,all,ChannelConfig.ExperEncoding);
				sb.append("导出WAP小编推荐货架， 生成全部数据信息文件成功，共"+(all.size()-1)+"条数据 ");
				sb.append("<br/>");
				logger.debug("生成WAP小编推荐货架全部数据文件完成");
			} catch (BOException e) {
				logger.error("导出WAP小编推荐货架， 全部数据信息生成文件失败，",e);
				sb.append("导出WAP小编推荐货架， 生成全部数据信息文件失败");
				sb.append("<br/>");
			}
//			if(free.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportFreeFile);
				String freeFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				freeFileName = localDir+File.separator+freeFileName;
				try {
					logger.debug("生成WAP小编推荐货架免费数据文件开始");
					DataExportTools.writeToCSVFile(freeFileName,free,ChannelConfig.ExperEncoding);
					sb.append("导出WAP小编推荐货架， 生成免费数据信息文件成功，共"+(free.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP小编推荐货架免费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP小编推荐货架， 免费数据信息生成文件失败，",e);
					sb.append("导出WAP小编推荐货架， 生成免费数据信息文件失败");
					sb.append("<br/>");
				}
//			}else{
//				sb.append("导出WAP小编推荐货架， 生成免费信息文件失败，没有免费数据信息！");
//				sb.append("<br/>");
//			}
//			if(pay.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportPayFile);
				String payFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				payFileName = localDir+File.separator+payFileName;				
				try {
					logger.debug("生成WAP小编推荐货架付费数据文件开始");
					DataExportTools.writeToCSVFile(payFileName,pay,ChannelConfig.ExperEncoding);
					sb.append("导出WAP小编推荐货架， 生成付费数据信息文件成功，共"+(pay.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP小编推荐货架付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP小编推荐货架， 付费数据信息生成文件失败，",e);
					sb.append("导出WAP小编推荐货架， 生成付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP小编推荐货架， 生成付费信息文件失败，没有付费数据信息！");
//				sb.append("<br/>");
//			}
//			if(tb.size()>0){
				fileName = DataExportTools.parseFileName(ChannelConfig.exportTbFile);
				String tbFileName = fileName.replaceAll("AAA", "WAP").replaceAll("BBB", "XBTJ");
				tbFileName = localDir+File.separator+tbFileName;				
				try {
					logger.debug("生成WAP小编推荐货架先体验后付费数据文件开始");
					DataExportTools.writeToCSVFile(tbFileName,tb,ChannelConfig.ExperEncoding);
					sb.append("导出WAP小编推荐货架， 生成先体验后付费数据信息文件成功，共"+(tb.size()-1)+"条数据 ");
					sb.append("<br/>");
					logger.debug("生成WAP小编推荐货架先体验后付费数据文件完成");
				} catch (BOException e) {
					logger.error("导出WAP小编推荐货架， 先体验后付费数据信息生成文件失败，",e);
					sb.append("导出WAP小编推荐货架， 生成先体验后付费数据信息文件失败");
					sb.append("<br/>");
				}	
//			}else{
//				sb.append("导出WAP小编推荐货架， 生成先体验后付费信息文件失败，没有先体验后付费数据信息！");
//				sb.append("<br/>");				
//			}			
//		}else{
//			sb.append("WAP小编推荐货架下数据为空，未导出文件!");
//			sb.append("<br/>");
//		}
	}	
}
