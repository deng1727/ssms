package com.aspire.dotcard.searchfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import au.com.bytecode.opencsv.CSVWriter;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileMD5Utils;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameByJTForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameByJTForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameByJTForWWW;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileGameForWWW;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftByJTForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftByJTForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftByJTForWWW;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSoftForWWW;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileSubjectImpl;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeByJTForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeByJTForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeByJTForWWW;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeForMO;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeForWAP;
import com.aspire.ponaadmin.web.dataexport.searchfile.impl.SearchFileThemeForWWW;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SearchFileGenerateBO {

	private static final JLogger LOG = LoggerFactory
			.getLogger(SearchFileGenerateBO.class);

	private List sucessList = new ArrayList();

	private List failureList = new ArrayList();

	private Date startDate;

	private Map goodsidMappingForMO;

	private Map goodsisMappingForWWW;

	private Map goodsisMappingForWAP;

	/**
	 * 十六进制分隔符
	 */
	private char SEPARATOR = (char) Integer.parseInt("01", 16);;

	private char char01 = (char) Integer.parseInt("01", 16);
	private char char0c = (char) Integer.parseInt("0c", 16);

	/**
	 * 数据库中换行符替代符号
	 */
	private String replace = " ";

	private static SearchFileGenerateBO BO = new SearchFileGenerateBO();

	public static SearchFileGenerateBO getInstance() {

		return BO;
	}

	public void generateAllSearchFile() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("开始生成索引文件");
		}
		startDate = new Date();
		sucessList.clear();
		failureList.clear();
		try {
			prepareAllData();
		} catch (DAOException e) {
			this.sendResultMail(false, e.getMessage());
			return;
		}

		SearchFileSubjectImpl impl = new SearchFileSubjectImpl(
				goodsidMappingForMO, goodsisMappingForWWW, goodsisMappingForWAP);

		try {
			new SearchFileGameByJTForWWW(impl);
			new SearchFileGameForWWW(impl);
			new SearchFileSoftByJTForWWW(impl);
			new SearchFileSoftForWWW(impl);
			new SearchFileThemeByJTForWWW(impl);
			new SearchFileThemeForWWW(impl);

			new SearchFileGameByJTForMO(impl);
			new SearchFileGameForMO(impl);
			new SearchFileSoftByJTForMO(impl);
			new SearchFileSoftForMO(impl);
			new SearchFileThemeByJTForMO(impl);
			new SearchFileThemeForMO(impl);

			new SearchFileGameByJTForWAP(impl);
			new SearchFileGameForWAP(impl);
			new SearchFileSoftByJTForWAP(impl);
			new SearchFileSoftForWAP(impl);
			new SearchFileThemeByJTForWAP(impl);
			new SearchFileThemeForWAP(impl);

			impl.prepareAllData();
		} catch (BOException e1) {
			LOG.error("BOException:", e1);
		} catch (DAOException e) {
			LOG.error("DAOException:", e);
		}

		this.failureList.addAll(impl.getFailureList());
		this.sucessList.addAll(impl.getSucessList());

		// generateSearchFileByOtherType("基地音乐", "nt:gcontent:music",
		// "music_old.csv");

		generateSearchFileByOtherType("基地音乐", "nt:gcontent:music", "music.txt");

		// add by dongke 20110501 for new music to WAP
		generateSearchFileByOtherType("新基地音乐", "NEWMUSIC", "music.txt");

		generateSearchFileByOtherType("基地读书", "nt:gcontent:read", "read.txt");

		generateSearchFileByOtherType("基地阅读", "nt:gcontent:bread", "bread.txt");

		generateSearchFileByOtherType("活动专题", "nt:gcontent:dissertation",
				"subject.txt");

		generateSearchFileByOtherType("视频节目", "video", "video.txt");

		generateSearchFileByOtherType("基地动漫", "comic", "comic.txt");

		generateSearchFileByOtherType("客户端重点应用标签", "tag", "content_tag.txt");

		// generateSearchFileByCYDate("2011创业大赛",
		// "cy_data_2011",
		// "cy_data_2011.txt");
		//
		// generateSearchFileByCYDate("市场PK",
		// "cy_data_market",
		// "cy_data_market.txt");
		//
		// generateSearchFileByCYDate("终极PK",
		// "cy_data_ultimate",
		// "cy_data_ultimate.txt");

		sendResultMail(true, null);

		goodsidMappingForMO = null;
		goodsisMappingForWWW = null;
		goodsisMappingForWAP = null;
	}

	public void prepareAllData() throws DAOException {
		goodsidMappingForMO = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsId();
		goodsisMappingForWWW = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsIdByWWW();
		goodsisMappingForWAP = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsIdByWAP();
	}

	public void prepareDataForO() throws DAOException {
		goodsidMappingForMO = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsId();
		goodsisMappingForWWW = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsIdByWWW();
		goodsisMappingForWAP = SearchFileGenerateDAO.getInstance()
				.getAllIDMappingGoodsIdByWAP();
	}

	/**
     * 
     */
	public boolean generateSearchFile(String typeName, String contentType,
			String fileName) {

		return generateSearchFile(typeName, contentType,
				SearchFileConstants.RELATION_O, null, fileName);
	}

	/**
	 * 生成并上传搜索文件。
	 * 
	 * @param contentType
	 *            获取的内容类型
	 * @param relation
	 *            为某个门户生成搜索数据。W 表是WWW，O表示终端
	 * @param servAttr
	 *            省内版还是集团版。如果为null，则不考虑该属性。
	 * @param fileName
	 *            生成的文件名。
	 * @return 是否生成成功 true 表示成功，false 表示失败。
	 */
	public boolean generateSearchFile(String typeName, String contentType,
			int relation, String servAttr, String fileName) {

		String fileNamePath;
		String portal;
		switch (relation) {
		case SearchFileConstants.RELATION_W:
			fileNamePath = SearchFileConfig.WWWPATH + fileName;
			portal = "WWW门户";
			break;
		case SearchFileConstants.RELATION_O:
			fileNamePath = SearchFileConfig.MOPATH + fileName;
			portal = "终端门户";
			break;
		case SearchFileConstants.RELATION_A:
			fileNamePath = SearchFileConfig.WAPATH + fileName;
			portal = "WAP门户";
			break;
		default:
			return false;
		}
		/*
		 * if(SearchFileConstants.RELATION_W.equals(relation)) {
		 * 
		 * fileNamePath= SearchFileConfig.WWWPATH + fileName; portal="WWW门户";
		 * }else { fileNamePath= SearchFileConfig.MOPATH + fileName;
		 * portal="终端门户"; }
		 */
		// 用于邮件统计。
		StringBuffer message = new StringBuffer();
		/*
		 * message.append(DBPersistencyCFG.getInstance()
		 * .getNodeCFG(contentType) .getTypeDesc());
		 */

		message.append(portal);
		message.append("--");
		message.append(typeName);
		message.append("--");
		if ("L".equals(servAttr)) {
			message.append("省内版 ");
		} else if ("G".equals(servAttr)) {
			message.append("集团版 ");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("开始导出索引文件类型：" + message.toString());
		}

		try {
			ResultSet rs = SearchFileGenerateDAO.getInstance().getDataByType(
					relation, contentType, servAttr);
			// 需要先写备份文件，成功后直接改名。
			String backFileName = fileNamePath
					+ PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");
			writeFile(relation, backFileName, rs, 26);// 目前所有的列数都是固定的
			IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
			boolean result = IOUtil.rename(new File(backFileName), fileName);
			if (result) {
				new BOException("重命名文件失败，sourceFileName=" + backFileName);
			}
			message.append("成功");
			sucessList.add(message.toString());
		} catch (Exception e) {
			LOG.error(e);
			message.append("失败");
			failureList.add(message);
			return false;
		}
		return true;

	}

	/**
	 * 写入文件
	 */
	private void writeFile(int relation, String fileNamePath, ResultSet rs,
			int colunmeSize) throws BOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileNamePath));
			String temp[] = new String[colunmeSize];
			int lineNumber = 0;
			while (rs.next()) {
				String id = rs.getString(1);
				String goodsid = getAppGoodsId(relation, id);// ( String )
				// goodsidMapping.get(id);
				if (goodsid == null) {
					// 该内容的id没有上架。
					continue;
				}
				temp[0] = goodsid;
				for (int i = 1; i < colunmeSize; i++)// 从第一行开始
				{
					if (i == 7) {
						try {
							temp[i] = getClobString(rs.getClob(7));
						} catch (Exception e) {
							temp[i] = "";
						}
					} else {
						temp[i] = rs.getString(i);
					}

				}

				writer.write(objectToString(temp));
				writer.newLine();

				if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}

			}
			writer.close();
			rs.close();
		} catch (IOException e) {
			LOG.error("写入搜索文件异常 fileName=" + fileNamePath, e);
			throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
		} catch (SQLException e) {
			LOG.error("读取数据库异常,待写入的fileName=" + fileNamePath, e);
			throw new BOException("读取数据库异常,待写入的fileName=" + fileNamePath, e);
		} catch (Exception e) {
			LOG.error("fileName=" + fileNamePath, e);
			throw new BOException("fileName=" + fileNamePath, e);
		} 
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	/**
	 * 写入文件
	 */
	private void writeFileCy(int relation, String fileNamePath, ResultSet rs,
			int colunmeSize) throws BOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileNamePath));
			String temp[] = new String[colunmeSize];
			int lineNumber = 0;
			Map hm = new HashMap();
			while (rs.next()) {
				String id = rs.getString(2);
				if (hm.get(id) == null) {

					for (int i = 0; i < colunmeSize; i++)// 从第一行开始
					{

						if (i == 7) {
							try {
								temp[i] = getClobString(rs.getClob(8));
							} catch (NullPointerException e) {
								temp[i] = "";
							}
						} else {
							temp[i] = rs.getString(i + 1);
						}

					}
					writer.write(objectToString(temp));
					writer.newLine();
					hm.put(id, temp);
					if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
					{
						writer.flush();
					}

				} else {
					if (LOG.isDebugEnabled()) {
						LOG.debug("重复的记录：" + id);
					}

				}
			}
			writer.close();
			rs.close();
		} catch (IOException e) {
			throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
		} catch (SQLException e) {
			throw new BOException("读取数据库异常,待写入的fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	/**
	 * 根据门户的类型，随即返回一个goodsid
	 * 
	 * @param relation
	 * @param id
	 * @return
	 */
	public String getAppGoodsId(int relation, String id) {

		switch (relation) {
		case SearchFileConstants.RELATION_W:
			return (String) goodsisMappingForWWW.get(id);
		case SearchFileConstants.RELATION_O:
			return (String) goodsidMappingForMO.get(id);
		case SearchFileConstants.RELATION_A:
			return (String) goodsisMappingForWAP.get(id);
		default:
			return null;
		}
		/*
		 * if(SearchFileConstants.RELATION_W.equals(relation)) { return ( String
		 * ) goodsisMappingByWWW.get(id); }else { return ( String )
		 * goodsidMapping.get(id); }
		 */
	}

	public boolean generateSearchFileByOtherType(String typeName, String type,
			String fileName) {

		// 用于邮件统计。
		StringBuffer message = new StringBuffer();
		message.append(typeName);
		message.append("--");
		if (LOG.isDebugEnabled()) {
			LOG.debug("开始导出索引文件类型：" + message.toString());
		}
		String fileNamePath = "";// SearchFileConfig.MOPATH + fileName;;
		String moFileNamePath = "";
		String wapFileNamePath = "";
		try {
			ResultSet rs = SearchFileGenerateDAO.getInstance()
					.getDataByOthreType(type);
			String backFileName = "";
			boolean result = false;
			// 需要先写备份文件，成功后直接改名。
			if (SearchFileConstants.DISSERTATION.equals(type)) {
				// 专题
				fileNamePath = SearchFileConfig.SUBJECT + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherTypeNormal(backFileName, rs, 21);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			} else if (SearchFileConstants.NEWMUSIC.equals(type)) {

				moFileNamePath = SearchFileConfig.MOPATH + "music_new.txt";
				String mobackFileName = moFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");

				wapFileNamePath = SearchFileConfig.WAPATH + fileName;

				String wapbackFileName = wapFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");

				String wwwbackFileName = null;
				writeFileByOtherTypeForAll(mobackFileName, wapbackFileName,
						wwwbackFileName, 8, rs, 21);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(moFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(mobackFileName),
						"music_new.txt");

				IOUtil.deleteFile(new File(wapFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(wapbackFileName), fileName);
				// fileNamePath = SearchFileConfig.WAPATH + fileName;
				// backFileName = fileNamePath
				// + PublicUtil.getCurDateTime("yyyy_MM_dd");
				// writeFileByOtherType(backFileName, rs, 19);// 目前所有的列数都是固定的
				//
				//
				// IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				// result = IOUtil.rename(new File(backFileName), fileName);
			} else if (SearchFileConstants.READ.equals(type)) {
				// 阅读基地 只生成mo门户，和wap门户数据
				moFileNamePath = SearchFileConfig.MOPATH + fileName;
				String mobackFileName = moFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				wapFileNamePath = SearchFileConfig.WAPATH + fileName;

				String wapbackFileName = wapFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");

				String wwwbackFileName = null;
				writeFileByOtherTypeForAll(mobackFileName, wapbackFileName,
						wwwbackFileName, 8, rs, 21);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(moFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(mobackFileName), fileName);

				IOUtil.deleteFile(new File(wapFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(wapbackFileName), fileName);

			} else if (SearchFileConstants.BREAD.equals(type)) {
				// 阅读基地 只生成mo门户，和wap门户数据
				moFileNamePath = SearchFileConfig.MOPATH + fileName;
				String mobackFileName = moFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				wapFileNamePath = SearchFileConfig.WAPATH + fileName;

				String wapbackFileName = wapFileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");

				String wwwbackFileName = null;
				writeFileByOtherTypeForAll(mobackFileName, wapbackFileName,
						wwwbackFileName, 8, rs, 21);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(moFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(mobackFileName), fileName);

				IOUtil.deleteFile(new File(wapFileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(wapbackFileName), fileName);

			} else if (SearchFileConstants.VIDEO.equals(type)) {
				// 视频基地 mo门户，和wap门户共用数据

				fileNamePath = SearchFileConfig.VIDEOPATH + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherTypeRMDuplicate(backFileName, rs, 19, 1);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			} else if (SearchFileConstants.COMIC.equals(type)) {
				fileNamePath = SearchFileConfig.COMIC_PATH + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherTypeNormal(backFileName, rs, 22);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			} else if ("nt:gcontent:music".equals(type)) {
				fileNamePath = SearchFileConfig.MOPATH + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherType(backFileName, rs, 21);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			} else if ("tag".equals(type)) {
				fileNamePath = SearchFileConfig.TAG + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherTypeNormal(backFileName, rs, 2);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			} else {
				fileNamePath = SearchFileConfig.MOPATH + fileName;
				backFileName = fileNamePath
						+ PublicUtil.getCurDateTime("yyyy_MM_dd");
				writeFileByOtherType(backFileName, rs, 19);// 目前所有的列数都是固定的

				IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
				result = IOUtil.rename(new File(backFileName), fileName);
			}

			// modify by aiyan 2012-10-24
			if (!result) {
				throw new BOException("重命名文件失败，sourceFileName=" + backFileName);
			}
			message.append("成功");

			if (SearchFileConstants.READ.equals(type)
					|| SearchFileConstants.NEWMUSIC.equals(type)
					|| SearchFileConstants.BREAD.equals(type)) {

				message.append("<br>");
				message.append("文件所在路径为:").append(moFileNamePath);
				message.append("<br>");
				message.append("文件MD5码为：").append(
						FileMD5Utils.getFileMD5(new File(moFileNamePath)));
				message.append("<br>");
				message.append("文件所在路径为:").append(wapFileNamePath);
				message.append("<br>");
				message.append("文件MD5码为：").append(
						FileMD5Utils.getFileMD5(new File(wapFileNamePath)));
			} else {
				message.append("<br>");
				message.append("文件所在路径为:").append(fileNamePath);
				message.append("<br>");
				message.append("文件MD5码为：").append(
						FileMD5Utils.getFileMD5(new File(fileNamePath)));
			}
			message.append("<br>");

			sucessList.add(message.toString());
		} catch (BOException e) {
			LOG.error(e);
			message.append("失败");
			failureList.add(message);
			return false;
		}
		return true;

	}

	/**
	 * 写入从t_r_gcontent 表拆分出的类型
	 * 
	 * @param type
	 * @param fileName
	 * @return
	 */
	public boolean generateSearchFileByCYDate(String typeName, String type,
			String fileName) {
		// 用于邮件统计。
		StringBuffer message = new StringBuffer();
		String fileNamePath = "";
		String backFileName = "";
		String portal = "终端门户";
		boolean result = false;

		message.append(portal);
		message.append("--");
		message.append(typeName);
		message.append("--");

		if (LOG.isDebugEnabled()) {
			LOG.debug("开始导出索引文件类型：" + message.toString());
		}

		try {
			ResultSet rs = SearchFileGenerateDAO.getInstance()
					.getDataByOthreType(type);

			// 需要先写备份文件，成功后直接改名。
			fileNamePath = SearchFileConfig.BUSINESS + fileName;
			backFileName = fileNamePath
					+ PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");

			if (SearchFileConstants.CY_DATA_2010.equals(type)
					|| SearchFileConstants.MARKET_PK.equals(type)
					|| SearchFileConstants.ULTIMATE_PK.equals(type)) {
				// 目前所有的列数都是固定的
				writeFileCy(SearchFileConstants.RELATION_O, backFileName, rs,
						26);
			} else {
				// 目前所有的列数都是固定的
				writeFile(SearchFileConstants.RELATION_O, backFileName, rs, 26);
			}

			// 删除旧的数据。
			IOUtil.deleteFile(new File(fileNamePath));

			result = IOUtil.rename(new File(backFileName), fileName);

			if (!result) {
				new BOException("重命名文件失败，sourceFileName=" + backFileName);
			}

			message.append("成功");
			sucessList.add(message.toString());
		} catch (BOException e) {
			LOG.error(e);
			message.append("失败");
			failureList.add(message);
			return false;
		}
		return true;
	}

	private void writeFileByOtherType(String fileNamePath, ResultSet rs,
			int colunmeSize) throws BOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileNamePath));
			String temp[] = new String[colunmeSize];
			int lineNumber = 0;
			while (rs.next()) {
				String id = rs.getString(1);
				temp[0] = id;
				for (int i = 1; i < colunmeSize; i++)// 从第一行开始
				{
					temp[i] = rs.getString(i);
				}
				writer.write(objectToString(temp));
				writer.newLine();
				if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}

			}
			writer.close();
			rs.close();
		} catch (IOException e) {
			throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
		} catch (SQLException e) {
			throw new BOException("读取数据库异常,待写入的fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	/**
	 * 
	 * @desc 同一份数据写多个门户
	 * @author dongke Jun 9, 2011
	 * @param moFileNamePath
	 * @param wapFileNamePath
	 * @param wwwFileNamePath
	 * @param targetnum
	 * @param rs
	 * @param colunmeSize
	 * @throws BOException
	 */
	private void writeFileByOtherTypeForAll(String moFileNamePath,
			String wapFileNamePath, String wwwFileNamePath, int targetnum,
			ResultSet rs, int colunmeSize) throws BOException {

		BufferedWriter moWriter = null;
		BufferedWriter wapWriter = null;
		BufferedWriter wwwWriter = null;
		try {
			if (null != moFileNamePath && !"".equals(moFileNamePath)) {
				moWriter = new BufferedWriter(new FileWriter(moFileNamePath));
			}
			if (null != wapFileNamePath && !"".equals(wapFileNamePath)) {
				wapWriter = new BufferedWriter(new FileWriter(wapFileNamePath));
			}
			if (null != wwwFileNamePath && !"".equals(wwwFileNamePath)) {
				wwwWriter = new BufferedWriter(new FileWriter(wwwFileNamePath));
			}
			String moTemp[] = new String[colunmeSize];
			String wwwTemp[] = new String[colunmeSize];
			String wapTemp[] = new String[colunmeSize];
			int lineNumber = 0;
			while (rs.next()) {
				String id = rs.getString(1);
				moTemp[0] = id;
				wwwTemp[0] = id;
				wapTemp[0] = id;
				for (int i = 1; i < colunmeSize; i++)// 从第一行开始
				{
					moTemp[i] = rs.getString(i);
					wwwTemp[i] = rs.getString(i);
					wapTemp[i] = rs.getString(i);

				}
				if (null != moFileNamePath && !"".equals(moFileNamePath)) {
					moTemp[targetnum] = "O";
					moWriter.write(objectToString(moTemp));
					moWriter.newLine();
				}
				if (null != wapFileNamePath && !"".equals(wapFileNamePath)) {
					wapTemp[targetnum] = "A";
					wapWriter.write(objectToString(wapTemp));
					wapWriter.newLine();
				}
				if (null != wwwFileNamePath && !"".equals(wwwFileNamePath)) {
					wwwTemp[targetnum] = "W";
					wwwWriter.write(objectToString(wwwTemp));
					wwwWriter.newLine();
				}
				if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					if (null != moFileNamePath && !"".equals(moFileNamePath)) {
						moWriter.flush();
					}
					if (null != wapFileNamePath && !"".equals(wapFileNamePath)) {
						wapWriter.flush();
					}
					if (null != wwwFileNamePath && !"".equals(wwwFileNamePath)) {
						wwwWriter.flush();
					}
				}

			}
			if (null != moFileNamePath && !"".equals(moFileNamePath)) {
				moWriter.close();
			}
			if (null != wapFileNamePath && !"".equals(wapFileNamePath)) {
				wapWriter.close();
			}
			if (null != wwwFileNamePath && !"".equals(wwwFileNamePath)) {
				wwwWriter.close();
			}
			// moWriter.close();
			rs.close();
		} catch (IOException e) {
			throw new BOException("写入搜索文件异常 fileName=" + moFileNamePath, e);
		} catch (SQLException e) {
			throw new BOException("读取数据库异常,待写入的fileName=" + moFileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + moFileNamePath, e);
		} finally {
			try {
				if (moWriter != null) {
					moWriter.close();
				}
				if (wapWriter != null) {
					wapWriter.close();
				}
				if (wwwWriter != null) {
					wwwWriter.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	private void writeFileByOtherTypeNormal(String fileNamePath, ResultSet rs,
			int colunmeSize) throws BOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileNamePath));
			String temp[] = new String[colunmeSize];
			int lineNumber = 0;
			while (rs.next()) {
				// String id = rs.getString(1);
				// temp[0] = id;
				for (int i = 1; i <= colunmeSize; i++)// 从第一行开始
				{
					temp[i - 1] = rs.getString(i);
				}
				writer.write(objectToString(temp));
				writer.newLine();
				if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}

			}
			writer.close();
			rs.close();
		} catch (IOException e) {
			throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
		} catch (SQLException e) {
			throw new BOException("读取数据库异常,待写入的fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	/*
	 * 去重 keycloum 唯一主键所在结果集的列下标
	 */
	private void writeFileByOtherTypeRMDuplicate(String fileNamePath,
			ResultSet rs, int colunmeSize, int keycloum) throws BOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileNamePath));
			String temp[] = new String[colunmeSize];
			int lineNumber = 0;
			Map hm = new HashMap();
			while (rs.next()) {
				String key = rs.getString(keycloum);
				if (hm.get(key) == null) {
					// String id = rs.getString(1);
					// temp[0] = id;
					for (int i = 1; i <= colunmeSize; i++)// 从第一行开始
					{
						temp[i - 1] = rs.getString(i);
					}
					writer.write(objectToString(temp));
					writer.newLine();
					hm.put(key, temp);
					if (lineNumber % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
					{
						writer.flush();
					}

				} else {
					if (LOG.isDebugEnabled()) {
						LOG.debug("重复的记录：" + key);
					}

				}

			}
			writer.close();
			rs.close();
		} catch (IOException e) {
			throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
		} catch (SQLException e) {
			throw new BOException("读取数据库异常,待写入的fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e1) {
			}
		}

	}

	/*
	 * private boolean uploadRemoteFile(String fileNamePath,String
	 * remoteFileName) throws BOException { FTPClient ftp=null; String
	 * backFileName=remoteFileName+PublicUtil.getCurDateTime("yyyy_mm_dd"); try
	 * { ftp= PublicUtil.getFTPClient(SearchFileConfig.IP,
	 * SearchFileConfig.PORT, SearchFileConfig.USER, SearchFileConfig.PWD,
	 * SearchFileConfig.FTPDIR); ftp.put(fileNamePath,
	 * backFileName);//先上传到备份文件，完成后再进行改名。 ftp.delete(remoteFileName);
	 * ftp.rename(backFileName, remoteFileName); } catch (Exception e) { throw
	 * new BOException("上传ftp文件出错",e); }finally { if(ftp!=null) { try {
	 * ftp.quit(); } catch (Exception e) { } } } return true; }
	 */
	/**
	 * @param type
	 *            带导出的数据类型
	 * @param sourceFilePath
	 *            待拷贝的源文件名
	 * @param disFileName
	 *            目标文件名。
	 */
	/*
	 * private boolean copySameDateSource(String type,String
	 * sourceFileName,String disFileName) { StringBuffer
	 * message=getMessage(type, disFileName); if(LOG.isDebugEnabled()) {
	 * LOG.debug("开始复制数据文件="+sourceFileName+",并导出索引文件类型："+message.toString()); }
	 * String sourceFilePath=SearchFileConfig.LOCALDIR+sourceFileName; String
	 * disFileNamePath=SearchFileConfig.LOCALDIR+disFileName; try {
	 * IOUtil.copy(sourceFilePath, disFileNamePath);
	 * uploadRemoteFile(disFileNamePath,disFileName); message.append("成功"); }
	 * catch (Exception e) { LOG.error(e); message.append("失败"); return false; }
	 * return true; }
	 */
	/**
	 * 发送结果邮件。
	 */
	private void sendResultMail(boolean result, String reason) {

		String mailTitle;
		// 发送邮件表示本次处理结束
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = sucessList.size();
		int totalFailureCount = failureList.size();
		mailTitle = "搜索引擎数据文件生成结果";
		if (result) {

			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<h4>处理结果：</h4>");
			sb.append("<p>其中成功导出<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>个文件。");
			sb.append("失败导出<b>");
			sb.append(totalFailureCount);
			sb.append("</b>个文件。");
			if (totalSuccessCount != 0) {
				sb.append("<p>具体信息为为：<p>");

				for (int i = 0; i < sucessList.size(); i++) {
					sb.append(sucessList.get(i));
					sb.append("<br>");
				}
				sb.append("<br>");
			}

			for (int i = 0; i < failureList.size(); i++) {
				sb.append(failureList.get(i));
				sb.append("<br>");
			}

		} else {
			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>失败原因：<br>");
			sb.append(reason);
		}

		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), SearchFileConfig.mailTo);
	}

	private String getClobString(Clob clob) throws SQLException {

		if (clob == null) {
			return "";
		}
		long len = clob.length();
		return clob.getSubString(0, (int) len);
	}

	/**
	 * 因需求要求写入txt中。这里做如下改动
	 * 
	 * @return
	 */
	private String objectToString(String[] temp) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < temp.length; i++) {
			sb.append(transformString(temp[i]));

			// 加入分隔符
			if (i == temp.length - 1) {
			} else {
				sb.append(this.SEPARATOR);
			}
		}

		return sb.toString();
	}

	/**
	 * 用于替换要处理的特殊字符
	 * 
	 * @param temp
	 * @return
	 */
	private String replaceString(String temp) {
		temp = temp.replaceAll("\r", "");
		temp = temp.replaceAll("\f", "");
		temp = temp.replaceAll(String.valueOf(char01), "");
		temp = temp.replaceAll(String.valueOf(char0c), "");
		temp = temp.replaceAll("\n", replace);

		return temp;
	}

	/**
	 * 把数据库中存在的换行符转换为空格
	 * 
	 * @param temp
	 * @return
	 */
	private String transformString(String temp) {
		if (null != temp) {
			temp = replaceString(temp);
		} else {
			temp = "";
		}

		return temp;
	}

}
