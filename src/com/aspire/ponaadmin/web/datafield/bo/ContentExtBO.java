package com.aspire.ponaadmin.web.datafield.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.dao.ContentExtDAO;
import com.aspire.ponaadmin.web.datafield.vo.ContentExtQueryVO;
import com.aspire.ponaadmin.web.datafield.vo.ContentExtVO;

/**
 * <p>
 * 应用活动属性管理查询的BO类
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentExtBO {

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ContentExtBO.class);

	/**
	 * singleton模式的实例
	 */
	private static ContentExtBO instance = new ContentExtBO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private ContentExtBO() {

	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static ContentExtBO getInstance() {

		return instance;
	}

	/**
	 * 根据名称、提供商等字段查询应用活动。
	 * 
	 * @param page
	 * @param contentID 内容ID
	 * @param name 内容名称
	 * @param spName 提供商
	 * @param type 类型:1团购，2秒杀
	 * @throws BOException
	 */
	public void queryContentExtList(PageResult page, String contentID,
			String name, String spName, String type, String isrecomm,
			String date) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryContentExtList(contentID=" + contentID
					+ " name=" + name + " spName=" + spName + " type=" + type
					+ " isrecomm=" + isrecomm + " date=" + date + ")");
		}
		if (page == null) {
			throw new BOException("invalid para , page is null");
		}
		try {
			ContentExtDAO.getInstance().queryContentExtList(page, contentID,
					name, spName, type, isrecomm, date);
		} catch (DAOException e) {
			logger.error("queryContentExtList...", e);
			throw new BOException("queryContentExtList", e);
		}
	}

	/**
	 * 根据名称、提供商等字段查询应用。
	 * 
	 * @param page
	 * @param contentID 内容ID
	 * @param name 内容名称
	 * @param spName 提供商
	 * @throws BOException
	 */
	public void queryContentExtQueryList(PageResult page, String contentID,
			String name, String spName) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryContentExtQueryList(contentID=" + contentID
					+ " name=" + name + " spName=" + spName + ")");
		}
		if (page == null) {
			throw new BOException("invalid para , page is null");
		}
		try {
			ContentExtDAO.getInstance().queryContentExtQueryList(page,
					contentID, name, spName);
		} catch (DAOException e) {
			logger.error("queryContentExtQueryList...", e);
			throw new BOException("queryContentExtQueryList", e);
		}
	}

	private List filterList(List list, List formatList, List existList,
			String type) {

		List result = new ArrayList();
		Map map = MapFactory.create(type);
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			String contentID = m.get(map.get("contentID") + "") + "";
			String isrecomm = m.get(map.get("isrecomm") + "") + "";
			String discount = m.get(map.get("discount") + "") + "";
			String dateStart = m.get(map.get("dateStart") + "") + "";
			String dateEnd = m.get(map.get("dateEnd") + "") + "";
			String timeStart = m.get(map.get("timeStart") + "") + "";
			String timeEnd = m.get(map.get("timeEnd") + "") + "";

			boolean isFormat = true;//先假设给的这行数据是规范数据。
			try {
				dateStart = dateStart.replaceAll("-", "");
				dateEnd = dateEnd.replaceAll("-", "");

				//	            System.out.println(isMS+"--"+contentID+"---"+contentID.length());
				//	            
				//	            System.out.println(!Utils.isMatcher(contentID, "[\\d]{12}"));
				//	            System.out.println(!"1".equals(isrecomm)&&!"2".equals(isrecomm));
				//	            System.out.println(!Utils.isMatcher(discount,"[\\d]{1,2}")&&!Utils.isMatcher(discount,"100"));
				//	            
				//	            
				//	            
				//	            System.out.println(dateStart+"--"+dateEnd+"--"+(!Utils.isMatcher(dateStart,"[\\d]{8}")||
				//	            		!Utils.isMatcher(dateEnd,"[\\d]{8}")));
				//	            
				//	            System.out.println(isMS&&(!Utils.isMatcher(timeStart,"[\\d]{2}:[\\d]{2}:[\\d]{2}")||
				//	            		!Utils.isMatcher(timeEnd,"[\\d]{2}:[\\d]{2}:[\\d]{2}")));
				//	            
				//	            
				//	            System.out.println(dateStart.compareTo(dateEnd)>0);

				//检查contentID,不能空，且长度为12
				if (!Utils.isMatcher(contentID, "[\\d]{12}")) {
					formatList.add(list.get(i));
					isFormat = false;
				} else if (!"1".equals(isrecomm) && !"0".equals(isrecomm)) {
					formatList.add(list.get(i));
					isFormat = false;
				} else if (!"3".equals(type)&&!Utils.isMatcher(discount, "[\\d]{1,2}")
						&& !Utils.isMatcher(discount, "100")) {
					formatList.add(list.get(i));
					isFormat = false;
				} else if (!Utils.isMatcher(dateStart, "[\\d]{8}")
						|| !Utils.isMatcher(dateEnd, "[\\d]{8}")) {
					formatList.add(list.get(i));
					isFormat = false;
				} else if ("2".equals(type)
						&& (!Utils.isMatcher(timeStart,
								"([0-1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]") || !Utils
								.isMatcher(timeEnd,
										"([0-1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]"))) {
					formatList.add(list.get(i));
					isFormat = false;
				} else if (dateStart.compareTo(dateEnd) > 0) {
					formatList.add(list.get(i));
					isFormat = false;
				}else if("2".equals(type)&&dateStart.compareTo(dateEnd) == 0&&timeStart.compareTo(timeEnd)>0){//是秒杀，那么通一天的情况下，那么应该开始时间小于等于结束时间。
					formatList.add(list.get(i));
					isFormat = false;
				}

				if (isFormat) {
					if(!"2".equals(type)){//如果不是秒杀，这两个值就取这样的默认值。
						timeStart= "00:00:00";
						timeEnd = "23:59:59";
					}
					
					int only = contentIsOnly(contentID, dateStart, dateEnd,
							timeStart, timeEnd);

//					System.out.println(only + "-" + contentID + "-" + dateStart
//							+ "-" + dateEnd + "-" + timeStart + "-" + timeEnd);
					if (only == 0) {
						result.add(list.get(i));
					} else {
						existList.add(list.get(i));
					}
				}

			} catch (BOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	private List convertList(List list, List notExistList,String type) {
		List result = new ArrayList();
		Map map = MapFactory.create(type);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			String contentID = m.get(map.get("contentID") + "") + "";
			String isrecomm = m.get(map.get("isrecomm") + "") + "";
			
			String discount = "0";//限时免费的折扣率为0；
			if(!"3".equals(type)){//如果是限时免费，折扣为0，其他的就的取EXCEL中的值；
				discount = m.get(map.get("discount") + "") + "";
			}
			
			String dateStart = m.get(map.get("dateStart") + "") + "";
			String dateEnd = m.get(map.get("dateEnd") + "") + "";
			
			
			String timeStart = m.get(map.get("timeStart") + "") + "";
			String timeEnd = m.get(map.get("timeEnd") + "") + "";
			if(!"2".equals(type)){//如果不是秒杀，这两个值就取这样的默认值。
				timeStart= "00:00:00";
				timeEnd = "23:59:59";
			}

			try {
				ContentExtVO vo = queryContentExtID(contentID);

				if (vo != null) {
					dateStart = dateStart.replaceAll("-", "");
					dateEnd = dateEnd.replaceAll("-", "");
					vo.setContentID(contentID);
					vo.setIsrecomm(isrecomm);
					vo.setDiscount(Integer.parseInt(discount));
					vo.setDateStart(dateStart);
					vo.setDateEnd(dateEnd);
					vo.setTimeStart(timeStart);
					vo.setTimeEnd(timeEnd);
					result.add(vo);
				}else{
					notExistList.add(list.get(i));
				}

			} catch (BOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	public Map contentExtImport(FormFile dataFile, Map params)
			throws BOException {
		int count = 0;
		List list = paraseDataFile(dataFile);
		List existList = new ArrayList();
		List notExistList = new ArrayList();
		List formatList = new ArrayList();
		String type = params.get("type") + "";

		list = filterList(list, formatList, existList, type);//1：折扣，2：秒杀，3：限时免费
		list = convertList(list, notExistList,type);

		for (int i = 0; i < list.size(); i++) {
			ContentExtVO vo = (ContentExtVO) list.get(i);
			double expPrice = Double.parseDouble(vo.getMobilePrice() + "")
					* Double.parseDouble(vo.getDiscount() + "") / 100.0;
			contentExtSave(vo.getContentID(), vo.getName(), vo.getSpName(), vo
					.getIcpcode(), type, vo.getMobilePrice() + "", vo
					.getDiscount()
					+ "", vo.getDateStart(), vo.getDateEnd(),
					vo.getTimeStart(), vo.getTimeEnd(), params.get("userid")
							+ "", expPrice, vo.getIsrecomm()

			);
			count++;
		}

		Map ret = new HashMap();
		ret.put("formatList", formatList);
		ret.put("existList", existList);
		ret.put("notExistList", notExistList);
		ret.put("succCount", count + "");
		return ret;

	}

	/**
	 * 解析EXECL文件，获取要添加的商品信息
	 * 
	 * @param in
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	private List paraseDataFile(FormFile dataFile) throws BOException {

		List list = new ArrayList();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(dataFile.getInputStream());
			Sheet[] sheets = book.getSheets();
			int sheetNum = sheets.length;
			for (int i = 0; i < sheetNum; i++) {
				int rows = sheets[i].getRows();
				int columns = sheets[i].getColumns();
				for (int j = 1; j < rows; j++) {
					char col = 'A';
					Map row = new HashMap();
					for (int k = 0; k < columns; k++) {
						String value = sheets[i].getCell(k, j).getContents()
								.trim();
						row.put((col++) + "", value);
					}
					list.add(row);
				}
			}

		} catch (Exception e) {
			logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
			throw new BOException("解析导入文件出现异常", e);
		}

		finally {
			book.close();
		}
		return list;
	}

	public void contentExtSave(String contentID, String name, String spName,
			String icpcode, String type, String mobilePrice, String discount,
			String dateStart, String dateEnd, String timeStart, String timeEnd,
			String userid, double expPrice, String isrecomm) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("contentExtSave(contentID=" + contentID + " name="
					+ name + " spName=" + spName + " icpcode=" + icpcode
					+ " icpcode=" + icpcode + " type=" + type + " mobilePrice="
					+ mobilePrice + " discount=" + discount + " dateStart="
					+ dateStart + " dateEnd=" + dateEnd + " timeStart="
					+ timeStart + " timeEnd=" + timeEnd + " userid=" + userid
					+ " expPrice=" + expPrice + " isrecomm=" + isrecomm);
		}
		try {

			ContentExtDAO.getInstance().contentExtSave(contentID, name, spName,
					icpcode, type, mobilePrice, discount, dateStart, dateEnd,
					timeStart, timeEnd, userid, expPrice, isrecomm);

		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("contentExtSave", e);
		}

	}

	public int contentIsOnly(String contentID, String dateStart, String dateEnd)
			throws BOException {

		int ret = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("contentIsOnly(contentID=" + contentID + " dateStart="
					+ dateStart + " dateEnd=" + dateEnd);
		}
		try {

			ret = ContentExtDAO.getInstance().contentIsOnly(contentID,

			dateStart, dateEnd, "00:00:00", "23:59:59");

		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("contentIsOnly", e);
		}

		return ret;
	}

	public int contentIsOnly(String contentID, String dateStart,
			String dateEnd, String timeStart, String timeEnd)
			throws BOException {

		int ret = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("contentIsOnly(contentID=" + contentID + " dateStart="
					+ dateStart + " dateEnd=" + dateEnd + " timeStart="
					+ timeStart + " timeEnd=" + timeEnd);
		}
		try {

			ret = ContentExtDAO.getInstance().contentIsOnly(contentID,

			dateStart, dateEnd, timeStart, timeEnd);

		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("contentIsOnly", e);
		}

		return ret;
	}

	public int contentIsOnly(String contentID, String dateStart,
			String dateEnd, String timeStart, String timeEnd, String id)
			throws BOException {

		int ret = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("contentIsOnly(contentID=" + contentID + " dateStart="
					+ dateStart + " dateEnd=" + dateEnd + " timeStart="
					+ timeStart + " timeEnd=" + timeEnd + " id=" + id);
		}
		try {

			ret = ContentExtDAO.getInstance().contentIsOnly(contentID,

			dateStart, dateEnd, timeStart, timeEnd, id);

		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("contentIsOnly", e);
		}

		return ret;
	}

	public void idDel(String[] idDel) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("idDel(" + idDel + ")");
		}

		// 开始执行删除操作
		for (int i = 0; (idDel != null) && (i < idDel.length); i++) {
			String id = idDel[i];
			if (logger.isDebugEnabled()) {
				logger.debug("idDel(" + id + ")");
			}
			try {
				ContentExtDAO.getInstance().idDel(id);
			} catch (DAOException e) {
				logger.error("idDel=" + idDel, e);
				throw new BOException("idDel error", e);
			}
		}

	}

	public ContentExtVO queryContentExtID(String id) throws BOException {

		try {
			return ContentExtDAO.getInstance().queryContentExtID(id);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("根据id查询失败！");
		}
	}

	public ContentExtVO getContentExtID(String id) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("getContentExtID(" + id + ")");
		}
		if (id == null) {
			throw new BOException("invalid para id");
		}
		try {
			return ContentExtDAO.getInstance().getContentExtID(id);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("根据id查询失败！");
		}
	}

	public void contentExtUpdate(String id, String type, String discount,
			String dateStart, String dateEnd, String timeStart, String timeEnd,
			String userid, double expPrice, String isrecomm) throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("contentExtUpdate( id= " + id + ")");
		}
		try {

			ContentExtDAO.getInstance().contentExtUpdate(id, type, discount,
					dateStart, dateEnd, timeStart, timeEnd, userid, expPrice,
					isrecomm);

		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("contentExtUpdate", e);
		}
	}
}

class MapFactory{
	public static Map create(String type){
		if("1".equals(type)){//折扣
			return new HashMap() {
					{
						put("contentID", "A");
						put("isrecomm", "B");
						put("discount", "C");
						put("dateStart", "D");
						put("dateEnd", "E");
					}
			};
		}else if("2".equals(type)){//秒杀
			return new HashMap() {
				{
					put("contentID", "A");
					put("isrecomm", "B");
					put("discount", "C");
					put("dateStart", "D");
					put("dateEnd", "E");
					put("timeStart", "F");
					put("timeEnd", "G");
				}
		};
		}else if("3".equals(type)){//限时免费
			return new HashMap() {
				{
					put("contentID", "A");
					put("isrecomm", "B");
					put("dateStart", "C");
					put("dateEnd", "D");
				}
		};
		}else{
			return null;
		}
	}
	
}