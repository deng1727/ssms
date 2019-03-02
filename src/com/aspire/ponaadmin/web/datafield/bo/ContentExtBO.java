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
 * Ӧ�û���Թ����ѯ��BO��
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentExtBO {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ContentExtBO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static ContentExtBO instance = new ContentExtBO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private ContentExtBO() {

	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static ContentExtBO getInstance() {

		return instance;
	}

	/**
	 * �������ơ��ṩ�̵��ֶβ�ѯӦ�û��
	 * 
	 * @param page
	 * @param contentID ����ID
	 * @param name ��������
	 * @param spName �ṩ��
	 * @param type ����:1�Ź���2��ɱ
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
	 * �������ơ��ṩ�̵��ֶβ�ѯӦ�á�
	 * 
	 * @param page
	 * @param contentID ����ID
	 * @param name ��������
	 * @param spName �ṩ��
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

			boolean isFormat = true;//�ȼ���������������ǹ淶���ݡ�
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

				//���contentID,���ܿգ��ҳ���Ϊ12
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
				}else if("2".equals(type)&&dateStart.compareTo(dateEnd) == 0&&timeStart.compareTo(timeEnd)>0){//����ɱ����ôͨһ�������£���ôӦ�ÿ�ʼʱ��С�ڵ��ڽ���ʱ�䡣
					formatList.add(list.get(i));
					isFormat = false;
				}

				if (isFormat) {
					if(!"2".equals(type)){//���������ɱ��������ֵ��ȡ������Ĭ��ֵ��
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
			
			String discount = "0";//��ʱ��ѵ��ۿ���Ϊ0��
			if(!"3".equals(type)){//�������ʱ��ѣ��ۿ�Ϊ0�������ľ͵�ȡEXCEL�е�ֵ��
				discount = m.get(map.get("discount") + "") + "";
			}
			
			String dateStart = m.get(map.get("dateStart") + "") + "";
			String dateEnd = m.get(map.get("dateEnd") + "") + "";
			
			
			String timeStart = m.get(map.get("timeStart") + "") + "";
			String timeEnd = m.get(map.get("timeEnd") + "") + "";
			if(!"2".equals(type)){//���������ɱ��������ֵ��ȡ������Ĭ��ֵ��
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

		list = filterList(list, formatList, existList, type);//1���ۿۣ�2����ɱ��3����ʱ���
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
	 * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
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
			logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
			throw new BOException("���������ļ������쳣", e);
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

		// ��ʼִ��ɾ������
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
			throw new BOException("����id��ѯʧ�ܣ�");
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
			throw new BOException("����id��ѯʧ�ܣ�");
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
		if("1".equals(type)){//�ۿ�
			return new HashMap() {
					{
						put("contentID", "A");
						put("isrecomm", "B");
						put("discount", "C");
						put("dateStart", "D");
						put("dateEnd", "E");
					}
			};
		}else if("2".equals(type)){//��ɱ
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
		}else if("3".equals(type)){//��ʱ���
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