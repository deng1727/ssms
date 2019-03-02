package com.aspire.dotcard.baseread.biz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.vo.VerfDataVO;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.dotcard.baseread.dao.BookAuthorDao;
import com.aspire.dotcard.baseread.dao.BookBagAreaDAO;
import com.aspire.dotcard.baseread.dao.BookBagContentDao;
import com.aspire.dotcard.baseread.dao.BookBagDao;
import com.aspire.dotcard.baseread.dao.BookCategoryDao;
import com.aspire.dotcard.baseread.dao.BookRecommendDao;
import com.aspire.dotcard.baseread.dao.BookReferenceDao;
import com.aspire.dotcard.baseread.dao.RBookDao;
import com.aspire.dotcard.baseread.dao.RCountDao;
import com.aspire.dotcard.baseread.dao.RUpdateDao;
import com.aspire.dotcard.baseread.dao.ReadLikeDAO;
import com.aspire.dotcard.baseread.dao.TypeDao;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.dotcard.baseread.vo.BookBagAreaVO;
import com.aspire.dotcard.baseread.vo.BookBagContentVO;
import com.aspire.dotcard.baseread.vo.BookBagVO;
import com.aspire.dotcard.baseread.vo.BookUpdateVO;
import com.aspire.dotcard.baseread.vo.MoDirectoryVO;
import com.aspire.dotcard.baseread.vo.RBookVO;
import com.aspire.dotcard.baseread.vo.RCategoryVO;
import com.aspire.dotcard.baseread.vo.RCountVO;
import com.aspire.dotcard.baseread.vo.RTypeVO;
import com.aspire.dotcard.baseread.vo.ReadLikeAuthorVO;
import com.aspire.dotcard.baseread.vo.ReadLikeHisReadVO;
import com.aspire.dotcard.baseread.vo.ReadLikePercentageVO;
import com.aspire.dotcard.baseread.vo.RecommendVO;
import com.aspire.dotcard.baseread.vo.ReferenceVO;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ����ͼ�鴦��ҵ����
 * 
 * @author x_zhailiqing
 * 
 */
public class RReadBO
{
	protected static JLogger logger = LoggerFactory.getLogger(RReadBO.class);
	
	private static RReadBO instance = new RReadBO();
	
	private TaskRunner taskRunner;
	
	private int baseBookSynTaskNum;
	
	private int baseBookMaxReceivedNum;
	
	private int baseBookThresholdNum;
	
	private RReadBO()
	{
		try
		{
			baseBookSynTaskNum = Integer.parseInt(BaseReadConfig
					.get("BaseBookSynTaskNum"));
			baseBookMaxReceivedNum = Integer.parseInt(BaseReadConfig
					.get("BaseBookMaxReceivedNum"));
			baseBookThresholdNum = Integer.parseInt(BaseReadConfig
					.get("baseBookThresholdNum"));
		}
		catch (Exception e)
		{
			logger.error("��ǰ��ʼ��ʧ���ˣ���鿴�����", e);
			baseBookSynTaskNum = 50;
			baseBookMaxReceivedNum = 1000;
			baseBookThresholdNum = 1000;
		}
	}
	
	public synchronized static RReadBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ����ͼ��ר�����봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookArea(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		// ��ѯ���еĻ���
		Map allAreaCate = BookCategoryDao.getInstance().queryAllCate();
		
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				RCategoryVO cate = new RCategoryVO();
				if (!cate.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				try
				{
					// ����
					if (cate.getChangeType() == 1)
					{
						if (null == allAreaCate.get(cate.getCateId()))
						{
							addBookArea(cate);
							rs[1]++;
							allAreaCate.put(cate.getCateId(), cate);
						}
						else
						{
							// �޸�
							updateBookArea(cate);
							rs[2]++;
						}
					}
					else if (cate.getChangeType() == 2)
					{
						if (null == allAreaCate.get(cate.getCateId()))
						{
							addBookArea(cate);
							rs[1]++;
							allAreaCate.put(cate.getCateId(), cate);
						}
						else
						{
							// �޸�
							updateBookArea(cate);
							rs[2]++;
						}
					}
					else if (cate.getChangeType() == 3)
					{
						// ����
						delBookArea(cate);
						rs[3]++;
						allAreaCate.remove(cate.getCateId());
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_catalog"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * ����ͼ��ר��
	 * 
	 * @param cate
	 */
	private void addBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("insert book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().add(cate);
		
	}
	
	/**
	 * ����ͼ��ר��
	 * 
	 * @param cate
	 */
	private void updateBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("update book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().update(cate);
	}
	
	/**
	 * ɾ��ͼ��ר��
	 * 
	 * @param cate
	 */
	private void delBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("delete book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().delete(cate);
	}
	
	/**
	 * ����ͼ�����е��봦�� �ܰ���
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookTotalRank(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error,
			String type) throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		// ��ɾ��֮ǰ�İ�����
		Map allBook = RBookDao.getInstance().queryAllBooks();
		Map cateIDMap = new HashMap();
		Map rankMap = new HashMap();
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				
				String[] dataArray = line.split("[" + sep + "]");
				
				ReferenceVO ref = new ReferenceVO();
				
				if (!ref.setValueByRank(dataArray, type))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				if (null == allBook.get(ref.getBookId()))
				{
					logger.error("ͼ�鲻���ڣ�" + ref.getBookId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ͼ�鲻����");
					err.add(line);
					rs[4]++;
					continue;
				}
				
				try
				{
					logger.debug("insert new rank:" + ref.getBookId() + ","
							+ ref.getCategoryId());
					if (!cateIDMap.containsKey(ref.getCategoryId()))
					{
						cateIDMap.put(ref.getCategoryId(), "");
						BookReferenceDao.getInstance().delRank(ref);
					}
					
					if (!rankMap.containsKey(ref.getCategoryId() + "|"
							+ ref.getBookId()))
					{
						rankMap
								.put(ref.getCategoryId() + "|"
										+ ref.getBookId(), "");
						addRank(ref);
						rs[1]++;
					}
					else
					{
						updateRank(ref);
						rs[2]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_rank" + type
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void addRank(ReferenceVO ref)
	{
		logger.debug("add rank:" + ref.getBookId());
		Object[] args = new Object[] { ref };
		Class[] argsClass = new Class[] { ReferenceVO.class };
		ReflectedTask task = new ReflectedTask(BookReferenceDao.getInstance(),
				"addRank", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	private void updateRank(ReferenceVO ref)
	{
		logger.debug("update rank:" + ref.getBookId());
		Object[] args = new Object[] { ref };
		Class[] argsClass = new Class[] { ReferenceVO.class };
		ReflectedTask task = new ReflectedTask(BookReferenceDao.getInstance(),
				"updateRank", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����ͼ����µ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookMonth(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		Map cateMap = BookCategoryDao.getInstance().queryAllCate();
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				BookBagVO bookBag = new BookBagVO();
				if (!bookBag.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (!cateMap.containsKey(bookBag.getCateId()))
				{
					logger.error("��רID�����ڣ�" + bookBag.getCateId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("����רID������");
					err.add(line);
					rs[5]++;
					continue;
				}
				try
				{
					logger
							.debug("insert new bookBag:"
									+ bookBag.getBookBagId());
					// ����
					if (bookBag.getChangeType() == 1)
					{
						if (!BookBagDao.getInstance().isExist(bookBag))
						{
							BookBagDao.getInstance().addBookBag(bookBag);
							rs[1]++;
						}
						else
						{
							BookBagDao.getInstance().updateBookBag(bookBag);
							rs[2]++;
						}
					}
					else if (bookBag.getChangeType() == 2)
					{
						if (!BookBagDao.getInstance().isExist(bookBag))
						{
							BookBagDao.getInstance().addBookBag(bookBag);
							rs[1]++;
						}
						else
						{
							BookBagDao.getInstance().updateBookBag(bookBag);
							rs[2]++;
						}
					}
					else if (bookBag.getChangeType() == 3)
					{
						// ����
						BookBagDao.getInstance().deleteBookBag(bookBag);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * ����������ݵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookBagContent(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		Map allBook = RBookDao.getInstance().queryAllBooks();
		Map allBookBag = BookBagDao.getInstance().queryAllBookBags();
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				BookBagContentVO bookBagContent = new BookBagContentVO();
				if (!bookBagContent.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allBook.get(bookBagContent.getBookId()))
				{
					logger.error("ͼ�鲻���ڣ�" + bookBagContent.getBookId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ͼ�鲻����");
					err.add(line);
					rs[5]++;
					continue;
				}
				if (null == allBookBag.get(bookBagContent.getBookBagId()))
				{
					logger.error("��������ڣ�" + bookBagContent.getBookId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����������");
					err.add(line);
					rs[5]++;
					continue;
				}
				try
				{
					logger.debug("insert new bookBagContent:"
							+ bookBagContent.getBookBagId());
					// ����
					if (bookBagContent.getChangeType() == 1)
					{
						if (!BookBagContentDao.getInstance().isExist(
								bookBagContent))
						{
							BookBagContentDao.getInstance().addBookBagContent(
									bookBagContent);
							rs[1]++;
						}
						else
						{
							BookBagContentDao.getInstance()
									.updateBookBagContent(bookBagContent);
							rs[2]++;
						}
					}
					else if (bookBagContent.getChangeType() == 2)
					{
						if (!BookBagContentDao.getInstance().isExist(
								bookBagContent))
						{
							BookBagContentDao.getInstance().addBookBagContent(
									bookBagContent);
							rs[1]++;
						}
						else
						{
							BookBagContentDao.getInstance()
									.updateBookBagContent(bookBagContent);
							rs[2]++;
						}
					}
					else if (bookBagContent.getChangeType() == 3)
					{
						// ����
						BookBagContentDao.getInstance().updateBookBagContent(
								bookBagContent);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_bookContent"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * �����ն�Ŀ¼���봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseMoDirectory(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		Map cateMap = BookCategoryDao.getInstance().queryAllCate();
		Map moDMap = new HashMap();
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				MoDirectoryVO moDirectory = new MoDirectoryVO();
				if (!moDirectory.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (!cateMap.containsKey(moDirectory.getCateId()))
				{
					logger.error("��רID�����ڣ�" + moDirectory.getCateId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("����רID������");
					err.add(line);
					rs[4]++;
					continue;
				}
				try
				{
					logger.debug("insert new moDirectory:"
							+ moDirectory.getMoDirectoryId());
					// ����
					if (!moDMap.containsKey(moDirectory.getMoDirectoryId()))
					{
						BookBagContentDao.getInstance().addMoDirectory(
								moDirectory);
						rs[1]++;
						moDMap.put(moDirectory.getMoDirectoryId(), moDirectory);
					}
					else
					{
						BookBagContentDao.getInstance().updateMoDirectory(
								moDirectory);
						rs[2]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_moDirectory"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * ����ͼ����Ʒ���봦�� ����ר�����ݵ���
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookReference(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		Map allBook = RBookDao.getInstance().queryAllBooks();
		Map allArea = BookCategoryDao.getInstance().queryAllCate();
		Map allCate = BookCategoryDao.getInstance().queryAllId();
		Map<String,String> allReference = BookReferenceDao.getInstance().queryAllReferences();
		Map<String, String> provinceMap = BookBagAreaDAO.getInstance()
				.queryAreaMap("province");
		Map<String, String> cityMap = BookBagAreaDAO.getInstance()
				.queryAreaMap("city");
		List err = new ArrayList();
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String errorArea = "";
				String[] dataArray = line.split("[" + sep + "]");
				ReferenceVO ref = new ReferenceVO();
				if (!ref.setValue(dataArray, provinceMap, cityMap, errorArea))
				{
					// ������Ϣ��ȫ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					/*if (!"".equals(errorArea))
					{
						errorRowNumber.append("<br>").append("  ��").append(
								lineNum).append("�����ݵ�����Ϣ����").append(errorArea);
					}
					// ���ݸ�ʽ����
					else
					{
						errorRowNumber.append("<br>").append("  ��").append(
								lineNum).append("�����ݸ�ʽ����");
					}*/
					
					rs[4]++;
					err.add(line);
					continue;
				}
				// ֻ�ǲ��ֵ�����Ϣ����
				else if (!"".equals(errorArea))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݵ�����Ϣ����BOOKID:"+ref.getBookId()+errorArea);
					/*errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݵ�����Ϣ����BOOKID:").append(ref.getBookId())
							.append(errorArea);*/
				}
				/*
				 * if (!ref.setValue(dataArray)) { // ���ݸ�ʽ���� logger.error("�ļ�" +
				 * lfs[i] + "��" + lineNum + "�����ݸ�ʽ����"); errorRowNumber.append("<br>")
				 * .append(" ��") .append(lineNum) .append("�����ݸ�ʽ����"); rs[4]++;
				 * err.add(line); continue; }
				 */
				if (null == allBook.get(ref.getBookId()))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�� ͼ�鲻����");
					/*errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ͼ�鲻����");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allArea.get(ref.getCategoryId()))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�� ר��������");
					/*errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ר��������");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allCate.get(ref.getCategoryId()))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�� ����ID������");
					/*errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("����ID������");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				else
				{
					// ���û���id
					ref.setcId(Integer.parseInt(String.valueOf(allCate.get(ref
							.getCategoryId()))));
				}
				try
				{
					// ����
					if (ref.getChangeType() == 1 || ref.getChangeType() == 2)
					{
						if(null == allReference.get(ref.getCategoryId()+","+ref.getBookId())){
							addBookReference(ref);
							allReference.put(ref.getCategoryId()+","+ref.getBookId(), "0");
							rs[1]++;
						}else{
							// �޸�
							updateBookReference(ref);
							allReference.put(ref.getCategoryId()+","+ref.getBookId(),"0");
							rs[2]++;
						}
					}
					else if (ref.getChangeType() == 3)
					{
						// ����
						delBookReference(ref);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		if(allReference.size() > 0){
			//��ʣ���ɾ��
            Iterator<String> ite = allReference.keySet().iterator();
			
			while (ite.hasNext())
			{
				String key = ite.next();
				if (!"0".equals(allReference.get(key)))
				{
					String[] value = key.split(",");
					ReferenceVO ref = new ReferenceVO();
					ref.setCategoryId(value[0]);
					ref.setBookId(value[1]);
					ref.setcId(Integer.parseInt(String.valueOf(allCate.get(ref
							.getCategoryId()))));
					delBookReference(ref);
					rs[3]++;
				}
			}
		}
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_catalogcontent"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * ����ר��������Ϣ
	 * 
	 * @param ref
	 */
	private void addBookReference(ReferenceVO ref) throws Exception
	{
		logger.debug("insert book reference:" + ref.getBookId());
		Object[] args = new Object[] { ref };
		Class[] argsClass = new Class[] { ReferenceVO.class };
		if (!BookReferenceDao.getInstance().isExist(ref))
		{
			ReflectedTask task = new ReflectedTask(BookReferenceDao
					.getInstance(), "add", args, argsClass);
			this.taskRunner.addTask(task);
		}
		else
		{
			ReflectedTask task = new ReflectedTask(BookReferenceDao
					.getInstance(), "update", args, argsClass);
			this.taskRunner.addTask(task);
		}
	}
	
	private void updateBookReference(ReferenceVO ref) throws Exception
	{
		logger.debug("update book referenc:" + ref.getBookId());
		addBookReference(ref);
	}
	
	private void delBookReference(ReferenceVO ref) throws Exception
	{
		logger.debug("insert book:" + ref.getBookId());
		Object[] args = new Object[] { ref };
		Class[] argsClass = new Class[] { ReferenceVO.class };
		ReflectedTask task = new ReflectedTask(BookReferenceDao.getInstance(),
				"delete", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����ͼ����Ϣ���봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBook(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		Map all = RBookDao.getInstance().queryAllBooks();
		Map typeMap = TypeDao.getInstance().queryAllTypes();
		Map authorMap = BookAuthorDao.getInstance().queryAllAuthor();
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		List err = new ArrayList();
		int num = 0;
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				RBookVO book = new RBookVO();
				if (!book.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					logger.error("��������Ϊ:" + line);
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				// �ж�ͼ�������Ƿ����
				if (null == authorMap.get(book.getAuthorId()))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�� ͼ�����߱�ʶ������");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ͼ�����߱�ʶ������");
					rs[4]++;
					err.add(line);
					continue;
				}
				// �ж�ͼ�������Ƿ����
				if (null == typeMap.get(book.getTypeId()))
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�� ͼ�����Ͳ�����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("��ͼ�����Ͳ�����");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				try
				{
					if (null == all.get(book.getBookId()))
					{
						addBook(book);
						rs[1]++;
						all.put(book.getBookId(), "");
						num++;
					}
					else
					{
						// �޸�
						updateBook(book);
						rs[2]++;
						all.put(book.getBookId(), "");
						num++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		// ɾ��ȫ���ļ��в����ڵĶ�������
		if (num > baseBookThresholdNum)
		{
			delBook(all, rs);
		}
		else
		{
			logger.error("��ǰȫ����������٣��ٹ���ָ����ֵ����������Ͳ�ɾ�ˣ��������ֹ������¡�");
		}
		
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void delBook(Map all, int[] rs) throws Exception
	{
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ���ʼ");
		
		Set<String> keySet = all.keySet();
		Iterator<String> keyIt = keySet.iterator();
		
		while (keyIt.hasNext())
		{
			String keyId = keyIt.next();
			String keyValue = (String) all.get(keyId);
			
			if (!"".equals(keyValue))
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("��ʼɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����ID��ID=" + keyId);
				}
				
				// ����ط�Ҫ��һ��ɾ�����߼�������
				try
				{
					// ����
					delBook(keyId);
					rs[3]++;
				}
				catch (DAOException e)
				{
					logger.error("ɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����IDʱ��������ID=" + keyId, e);
				}
			}
		}
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ�����");
	}
	
	/**
	 * ����ͼ����Ϣ
	 * 
	 * @param book
	 */
	private void addBook(RBookVO book) throws Exception
	{
		logger.debug("insert book:" + book.getBookName());
		// RTypeVO type = new RTypeVO();
		// type.setTypeId(book.getTypeId());
		// BookAuthorVO author = new BookAuthorVO();
		// author.setAuthorId(book.getAuthorId());
		// type = TypeDao.getInstance().getRTypeVO(type);
		// author = BookAuthorDao.getInstance().getAuthor(author);
		// if (null == type || null == author)
		// {
		// throw new BOException("ͼ��" + book.getBookName() + "���߲�����");
		// }
		// ������������
		// book.setAuthorName(author.getAuthorName());
		// ���ô���id
		// book.setTypeId(type.getParentId());
		Object[] args = new Object[] { book };
		Class[] argsClass = new Class[] { RBookVO.class };
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "add",
				args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����
	 * 
	 * @param book
	 */
	private void updateBook(RBookVO book) throws Exception
	{
		logger.debug("update book:" + book.getBookName());
		// RTypeVO type = new RTypeVO();
		// type.setTypeId(book.getTypeId());
		// BookAuthorVO author = new BookAuthorVO();
		// author.setAuthorId(book.getAuthorId());
		// type = TypeDao.getInstance().getRTypeVO(type);
		// author = BookAuthorDao.getInstance().getAuthor(author);
		// if (null == type || null == author)
		// {
		// throw new BOException("ͼ��" + book.getBookName() + "���߲�����");
		// }
		// ������������
		// book.setAuthorName(author.getAuthorName());
		// ���ô���id
		// book.setTypeId(type.getParentId());
		Object[] args = new Object[] { book };
		Class[] argsClass = new Class[] { RBookVO.class };
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(),
				"update", args, argsClass);
		this.taskRunner.addTask(task);
		
	}
	
	/**
	 * ɾ������־λɾ�� ����delflag=1
	 * 
	 * @param book
	 */
	private void delBook(String bookId) throws Exception
	{
		logger.debug("delete bookId:" + bookId);
		Object[] args = new Object[] { bookId };
		Class[] argsClass = new Class[] { String.class };
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(),
				"delete", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����ͼ�����ϲ��������ʷ�Ķ��Ƽ��ӿڵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseLikeHisRead(String encoding, String sep, int[] rs,
                                      String[] lfs,
                                      StringBuffer errorRowNumber,
                                      StringBuffer error) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        List<String> err = new ArrayList<String>();
        Map<String, String> allBook = RBookDao.getInstance().queryAllBooks();
        Map<String, String> allKey = ReadLikeDAO.getInstance().getKeyMap(1);
        for (int i = 0; i < lfs.length; i++)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),
                                                          encoding));
            int lineNum = 0;
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                if (lineNum == 1)
                {
                    line = PublicUtil.delStringWithBOM(line);
                }
                logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // ��ȡҪ����Ķ���
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikeHisReadVO likeHisRead = new ReadLikeHisReadVO();
                if (!likeHisRead.setValue(dataArray, allBook))
                {
                    // ���ݸ�ʽ����
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("�����ݸ�ʽ����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likeHisRead.getMsisdn();
                	
                	// �鿴��ǰ�����Ƿ���ڿ���
                	if(allKey.containsKey(key))
                	{
                		// ������ڣ�ɾ����ǰ��������Ӧ��¼
                		ReadLikeDAO.getInstance().deleteDataByKey(1, key);
                		rs[3]++;
                	}
                	
                	// ��������������
                	ReadLikeDAO.getInstance().insertDataByHis(likeHisRead);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // ���ɵ�������ļ�
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeHisRead"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * ����ͼ�����ϲ�����������Ƽ��ӿڵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseLikeAuthor(String encoding, String sep, int[] rs,
                                      String[] lfs,
                                      StringBuffer errorRowNumber,
                                      StringBuffer error) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        List<String> err = new ArrayList<String>();
        Map<String, String> allBook = RBookDao.getInstance().queryAllBooks();
        Map<String, String> authorMap = BookAuthorDao.getInstance().queryAllAuthor();
        Map<String, String> allKey = ReadLikeDAO.getInstance().getKeyMap(2);
        for (int i = 0; i < lfs.length; i++)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),
                                                          encoding));
            int lineNum = 0;
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                if (lineNum == 1)
                {
                    line = PublicUtil.delStringWithBOM(line);
                }
                logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // ��ȡҪ����Ķ���
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikeAuthorVO likeAuthor = new ReadLikeAuthorVO();
                if (!likeAuthor.setValue(dataArray, allBook, authorMap))
                {
                    // ���ݸ�ʽ����
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("�����ݸ�ʽ����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likeAuthor.getMsisdn();
                	
                	// �鿴��ǰ�����Ƿ���ڿ���
                	if(allKey.containsKey(key))
                	{
                		// ������ڣ�ɾ����ǰ��������Ӧ��¼
                		ReadLikeDAO.getInstance().deleteDataByKey(2, key);
                		rs[3]++;
                	}
                	
                	// ��������������
                	ReadLikeDAO.getInstance().insertDataByAuthor(likeAuthor);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // ���ɵ�������ļ�
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeAuthorRead"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * ����ͼ�����ϲ������ͼ�鼶�Ķ������Ƽ��ӿ�
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseLikeReadPercentage(String encoding, String sep, int[] rs,
                                      String[] lfs,
                                      StringBuffer errorRowNumber,
                                      StringBuffer error) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        List<String> err = new ArrayList<String>();
        Map<String, String> allBook = RBookDao.getInstance().queryAllBooks();
        Map<String, String> allKey = ReadLikeDAO.getInstance().getKeyMap(3);
        for (int i = 0; i < lfs.length; i++)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),
                                                          encoding));
            int lineNum = 0;
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                if (lineNum == 1)
                {
                    line = PublicUtil.delStringWithBOM(line);
                }
                logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // ��ȡҪ����Ķ���
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikePercentageVO likePercentage = new ReadLikePercentageVO();
                if (!likePercentage.setValue(dataArray, allBook))
                {
                    // ���ݸ�ʽ����
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("�����ݸ�ʽ����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likePercentage.getSourceBookId();
                	
                	// �鿴��ǰ�����Ƿ���ڿ���
                	if(allKey.containsKey(key))
                	{
                		// ������ڣ�ɾ����ǰ��������Ӧ��¼
                		ReadLikeDAO.getInstance().deleteDataByKey(3, key);
                		rs[3]++;
                	}
                	
                	// ��������������
                	ReadLikeDAO.getInstance().insertDataByPercentage(likePercentage);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // ���ɵ�������ļ�
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeReadPer"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * ����ͼ�����ϲ������ͼ�鼶���������Ƽ��ӿڵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseLikeOrderPercentage(String encoding, String sep, int[] rs,
                                      String[] lfs,
                                      StringBuffer errorRowNumber,
                                      StringBuffer error) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        List<String> err = new ArrayList<String>();
        Map<String, String> allBook = RBookDao.getInstance().queryAllBooks();
        Map<String, String> allKey = ReadLikeDAO.getInstance().getKeyMap(4);
        for (int i = 0; i < lfs.length; i++)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),
                                                          encoding));
            int lineNum = 0;
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                if (lineNum == 1)
                {
                    line = PublicUtil.delStringWithBOM(line);
                }
                logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // ��ȡҪ����Ķ���
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikePercentageVO likePercentage = new ReadLikePercentageVO();
                if (!likePercentage.setValue(dataArray, allBook))
                {
                    // ���ݸ�ʽ����
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("�����ݸ�ʽ����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likePercentage.getSourceBookId();
                	
                	// �鿴��ǰ�����Ƿ���ڿ���
                	if(allKey.containsKey(key))
                	{
                		// ������ڣ�ɾ����ǰ��������Ӧ��¼
                		ReadLikeDAO.getInstance().deleteDataByKey(4, key);
                		rs[3]++;
                	}
                	
                	// ��������������
                	ReadLikeDAO.getInstance().insertDataByOrderPercentage(likePercentage);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // ���ɵ�������ļ�
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeOrderPer"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * ����ͼ���Ƽ����봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookRecommend(String encoding, String sep, int[] rs,
                                      String[] lfs,
                                      StringBuffer errorRowNumber,
                                      StringBuffer error) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        List err = new ArrayList();
        Map allBook = RBookDao.getInstance().queryAllBooks();
        Map allRec = BookRecommendDao.getInstance().queryAllRecommend();
        for (int i = 0; i < lfs.length; i++)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),
                                                          encoding));
            int lineNum = 0;
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                if (lineNum == 1)
                {
                    line = PublicUtil.delStringWithBOM(line);
                }
                logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // ��ȡҪ����Ķ���
                String[] dataArray = line.split("[" + sep + "]");
                RecommendVO recommend = new RecommendVO();
                if (!recommend.setValue(dataArray))
                {
                    // ���ݸ�ʽ����
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("�����ݸ�ʽ����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }
                if (null == allBook.get(recommend.getBookId()))
                {
                    logger.error("�Ƽ�ͼ�鲻����" + recommend.getBookId());
                    errorRowNumber.append("<br>")
                                  .append("  ��")
                                  .append(lineNum)
                                  .append("���Ƽ�ͼ�鲻����");
                    rs[4]++;
                    err.add(line);
                    continue;
                }
                if (null != recommend.getTypeId()
                    && !"".equals(recommend.getTypeId()))
                {
                    RTypeVO type = new RTypeVO();
                    type.setTypeId(recommend.getTypeId());

                    if (!TypeDao.getInstance().isExist(type))
                    {
                        logger.error("ͼ����಻����" + recommend.getBookId());
                        errorRowNumber.append("<br>")
                                      .append("  ��")
                                      .append(lineNum)
                                      .append("��ͼ����಻����");
                        rs[4]++;
                        err.add(line);
                        continue;
                    }
                }

                try
                {
					if (allRec.containsKey(recommend.getRecommendId() + "|"
							+ recommend.getTypeId() + "|"
							+ recommend.getBookId()))
					{
						updateRecommend(recommend);
						rs[2]++;
						allRec.put(recommend.getRecommendId() + "|"
								+ recommend.getTypeId() + "|"
								+ recommend.getBookId(), "0");
					}
					else
					{
						addRecommend(recommend);
						rs[1]++;
						allRec.put(recommend.getRecommendId() + "|"
								+ recommend.getTypeId() + "|"
								+ recommend.getBookId(), "0");
					}
				}
                catch (Exception e)
                {
                    logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        logger.info("��ʼɾ����ǰ�����ļ��в����ڵ�ԭ���ݿ����ݣ�");
        
        // ɾ������
        Iterator key = allRec.keySet().iterator();
        while(key.hasNext())
        {
			String tempKey = (String) key.next();
			logger.info("��ʼɾ����ǰ�����ļ��в����ڵ�ԭ���ݿ����ݣ�");
			if ("-1".equals(allRec.get(tempKey)))
			{
				logger.info("��ʼɾ����ǰ�����ļ��в����ڵ�ԭ���ݿ����ݣ���û���޸ĵĴ�ɾ������Ϊ��" + tempKey);
				String[] rec = tempKey.split("\\|", -1);
				if (rec.length != 3)
				{
					logger.error("��ǰ����ɾ��ʧ�ܣ�����Ϊ��" + tempKey);
					rs[5]++;
					continue;
				}
				RecommendVO recommend = new RecommendVO();
				recommend.setRecommendId(rec[0]);
				recommend.setTypeId(rec[1]);
				recommend.setBookId(rec[2]);
				delRecommend(recommend);
				rs[3]++;
			}
		}
        
        // ���ɵ�������ļ�
        if (err.size() > 0)
        {
            String errFile = "err_bookrecommend"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * �����Ƽ�ͼ��
	 * 
	 * @param recommend
	 */
	private void addRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().add(recommend);
	}
	
	/**
	 * �����Ƽ���Ϣ
	 * 
	 * @param recommend
	 */
	private void updateRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().update(recommend);
	}
	
	/**
	 * ɾ���Ƽ���Ϣ
	 * 
	 * @param recommend
	 */
	private void delRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().delete(recommend);
	}
	
	/**
	 * ����ͼ�����ߵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookAuthor(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		int num = 0;
		Map all = BookAuthorDao.getInstance().queryAllAuthor();
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]", -1);
				BookAuthorVO author = new BookAuthorVO();
				if (!author.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					logger.error("��������Ϊ:" + line);
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				try
				{
					if (null == all.get(author.getAuthorId()))
					{
						addAuthor(author);
						rs[1]++;
						all.put(author.getAuthorId(), "");
						num++;
					}
					else
					{
						// �޸�
						updateAuthor(author);
						rs[2]++;
						all.put(author.getAuthorId(), "");
						num++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		// ɾ��ȫ�������ﲻ���ڵĶ�������
		if (num > baseBookThresholdNum)
		{
			delAuthor(all, rs);
		}
		else
		{
			logger.error("��ǰȫ����������٣��ٹ���ָ����ֵ����������Ͳ�ɾ�ˣ��������ֹ������¡�");
		}
		
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_author"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void delAuthor(Map all, int[] rs) throws Exception
	{
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ���ʼ");
		
		Set<String> keySet = all.keySet();
		Iterator<String> keyIt = keySet.iterator();
		
		while (keyIt.hasNext())
		{
			String keyId = keyIt.next();
			String keyValue = (String) all.get(keyId);
			
			if (!"".equals(keyValue))
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("��ʼɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����ID��ID=" + keyId);
				}
				
				// ����ط�Ҫ��һ��ɾ�����߼�������
				try
				{
					// ����
					deleteAuthor(keyId);
					rs[3]++;
				}
				catch (DAOException e)
				{
					logger.error("ɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����IDʱ��������ID=" + keyId, e);
				}
			}
		}
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ�����");
	}
	
	/**
	 * ��������
	 * 
	 * @param author
	 */
	private void addAuthor(BookAuthorVO author) throws Exception
	{
		logger.debug("insert new book author:" + author.getAuthorName());
		
		Object[] args = new Object[] { author };
		Class[] argsClass = new Class[] { BookAuthorVO.class };
		ReflectedTask task = new ReflectedTask(BookAuthorDao.getInstance(),
				"add", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����������Ϣ
	 * 
	 * @param author
	 */
	private void updateAuthor(BookAuthorVO author) throws Exception
	{
		logger.debug("insert new book author:" + author.getAuthorName());
		
		Object[] args = new Object[] { author };
		Class[] argsClass = new Class[] { BookAuthorVO.class };
		ReflectedTask task = new ReflectedTask(BookAuthorDao.getInstance(),
				"update", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ɾ������
	 * 
	 * @param author
	 */
	private void deleteAuthor(String authorId) throws Exception
	{
		logger.debug("delete new book authorId:" + authorId);
		Object[] args = new Object[] { authorId };
		Class[] argsClass = new Class[] { String.class };
		ReflectedTask task = new ReflectedTask(BookAuthorDao.getInstance(),
				"delete", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ����ͼ�����ߵ��봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookUpdate(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String[] dataArray = line.split("[" + sep + "]");
				BookUpdateVO update = new BookUpdateVO();
				if (!update.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				try
				{
					logger.debug("insert new bookUpdate:"
							+ update.getContentId());
					if (!RUpdateDao.getInstance().isExist(update))
					{
						addUpdateBook(update);
						// RUpdateDao.getInstance().addUpdate(update);
						rs[1]++;
					}
					else
					{
						updateUpBook(update);
						// RUpdateDao.getInstance().update(update);
						rs[2]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_update"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void addUpdateBook(BookUpdateVO update)
	{
		Object[] args = new Object[] { update };
		Class[] argsClass = new Class[] { BookUpdateVO.class };
		ReflectedTask task = new ReflectedTask(RUpdateDao.getInstance(),
				"addUpdate", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	private void updateUpBook(BookUpdateVO update)
	{
		Object[] args = new Object[] { update };
		Class[] argsClass = new Class[] { BookUpdateVO.class };
		ReflectedTask task = new ReflectedTask(RUpdateDao.getInstance(),
				"update", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * �������ͼ�����ݷ��ർ��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void dealBaseBookType(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				String[] dataArray = line.split("[" + sep + "]");
				RTypeVO type = new RTypeVO();
				if (!type.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				try
				{
					logger.debug("insert new booktype:" + type.getTypeId()
							+ "," + type.getTypeName());
					if (!TypeDao.getInstance().isExist(type))
					{
						TypeDao.getInstance().addType(type);
						rs[1]++;
					}
					else
					{
						TypeDao.getInstance().update(type);
						rs[2]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					err.add(line);
					rs[5]++;
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_category"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * �������ͼ��ͳ�����ݵ���
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void dealBaseBookCount(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		Set set = RCountDao.getInstance().queryAllCount();
		taskRunner = new TaskRunner(baseBookSynTaskNum, baseBookMaxReceivedNum);
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				String[] dataArray = line.split("[" + sep + "]");
				RCountVO count = new RCountVO();
				if (!count.setValue(dataArray))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݸ�ʽ����");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				try
				{
					logger.debug("insert new bookCount:" + count.getCountId());
					if (!set.contains(count.getCountId()))
					{
						// RCountDao.getInstance().addCount(count);
						addBookCount(count);
						set.add(count.getCountId());
						rs[1]++;
					}
					else
					{
						// RCountDao.getInstance().updateCount(count);
						updateBookCount(count);
						rs[2]++;
					}
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					err.add(line);
					rs[5]++;
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		taskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
		taskRunner.end();// ����������
		set.clear();
		
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_count"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void addBookCount(RCountVO count)
	{
		Object[] args = new Object[] { count };
		Class[] argsClass = new Class[] { RCountVO.class };
		ReflectedTask task = new ReflectedTask(RCountDao.getInstance(),
				"addCount", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	private void updateBookCount(RCountVO count)
	{
		Object[] args = new Object[] { count };
		Class[] argsClass = new Class[] { RCountVO.class };
		ReflectedTask task = new ReflectedTask(RCountDao.getInstance(),
				"updateCount", args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * ���ɵ��������ļ�
	 * 
	 * @param errFile
	 * @param err
	 */
	private void makeErrFile(String errFile, List err)
	{
		BufferedWriter out = null;
		try
		{
			String path = BaseReadConfig.get("errorFileDir");
			IOUtil.checkAndCreateDir(path);
			File f = new File(path + File.separator + errFile);
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f)));
			if (null == err)
			{
				return;
			}
			int size = err.size();
			for (int i = 0; i < size; i++)
			{
				out.write((String) err.get(i));
				out.write("\r\n");
			}
			out.flush();
		}
		catch (Exception e)
		{
			logger.error("��������ļ�ʧ��", e);
		}
		finally
		{
			if (null != out)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					;
				}
			}
		}
	}
	
	/**
	 * ���»�������Ʒ����
	 */
	public void updateCateTotal()
	{
		logger.debug("update book category total books begin!");
		try
		{
			BookCategoryDao.getInstance().updateCateTotal();
		}
		catch (Exception e)
		{
			logger.error("���»�������Ʒ��������", e);
		}
		logger.debug("update book category total books end!");
	}
	
	/**
	 * ����ͼ���������Ʒ����
	 */
	public void updateBookTypeTotal()
	{
		logger.debug("update book type total books begin!");
		try
		{
			// /update t_rb_type tp set tp.totalbooks = (select count(*) from
			// t_rb_book b where b.typeid=tp.typeid and b.delflag=0) where
			// tp.parentid='0'
		}
		catch (Exception e)
		{
			logger.error("���·�������Ʒ��������", e);
		}
		logger.debug("update book type total books end!");
	}
	
	/**
	 * �������ͼ��У���ļ�
	 * 
	 * @param encoding
	 *            ����
	 * @param sep
	 *            �ָ���
	 * @param lfs
	 *            �ļ���
	 * @return ���ؽ��
	 * @throws Exception
	 */
	public String dealBaseReadVerf(String encoding, String sep, String[] lfs)
	{
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		StringBuffer mailText = new StringBuffer();
		
		try
		{
			// ������ڣ�����
			for (int i = 0; i < lfs.length; i++)
			{
				String tempFileName = String.valueOf(lfs[i]);
				
				if (logger.isDebugEnabled())
				{
					logger.debug("��ʼ����У���ļ���" + tempFileName + " У��ָ���Ϊ��" + sep);
				}
				
				// ���ļ�
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(tempFileName), encoding));
				
				while ((lineText = reader.readLine()) != null)
				{
					// ��¼�ļ���������
					lineNumeber++;
					
					if (logger.isDebugEnabled())
					{
						logger.debug("��ʼ����У���ļ���" + lineNumeber + "�����ݡ�");
					}
					
					if (lineNumeber == 1)
					{
						// ɾ����һ��bom�ַ�
						lineText = PublicUtil.delStringWithBOM(lineText);
					}
					
					// У���ļ������һ��
					if (BaseVideoConfig.FILE_END.equals(lineText.trim()))
					{
						break;
					}
					
					// ���ļ�����
					mailText.append(readVerfData(lineText, lineNumeber, sep));
				}
			}
		}
		catch (Exception e)
		{
			mailText.append("����У���ļ�ʱ�������� ������<br>");
			logger.error(e);
			return mailText.toString();
			// throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				logger.error(e);
			}
		}
		return mailText.toString();
	}
	
	/**
	 * ����У���ļ���Ϣ��ͳһ����ʽ����ΪУ���ļ���ʽһ����
	 * 
	 * @param lineText
	 *            ��ǰ����Ϣ
	 * @param lineNumeber
	 *            �ڼ��� ���ڼ�¼������Ϣ
	 * @return У���ļ����ݶ���
	 */
	public String readVerfData(String lineText, int lineNumeber,
			String dataSpacers)
	{
		VerfDataVO vo = new VerfDataVO();
		String[] tempData = lineText.split(dataSpacers);
		
		if (tempData.length == 5)
		{
			vo.setFileName(tempData[0]);
			vo.setFileSiz(tempData[1]);
			vo.setFileDataNum(tempData[2]);
			vo.setLineNum(lineNumeber);
		}
		else
		{
			// �����ʼ�������Ϣ...............
			return writeErrorToVerfMail(lineNumeber, lineText,
					"У���ļ��ṹ���ԣ����ڻ�С��5������");
		}
		
		return vo.toMailText();
	}
	
	/**
	 * д��У�鴦�����Ĵ�����Ϣ���ʼ�
	 * 
	 * @param lineNum
	 *            �ڼ���
	 * @param dataText
	 *            ʲô����
	 * @param reasonText
	 *            ����ԭ��
	 */
	public String writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		return new StringBuffer("У���ļ���").append(" ��").append(lineNum).append(
				"��: ").append(dataText).append("�� �������д�ԭ��Ϊ��")
				.append(reasonText).append("<br>").toString();
	}
	
	/**
	 * ��һ��������ר����Ϣ��ɾ���ֻ����ж���Ļ���
	 */
	public boolean cleanOldSimulationDataTree()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����ר�������б�ȥ�����л��ܱ�,��ʼ");
		}
		
		List delList = null;
		try
		{
			// ��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܣ���ɾ��
			delList = BookCategoryDao.getInstance()
					.cleanOldSimulationDataTree();
		}
		catch (DAOException e)
		{
			logger.debug("����ר�������б�ȥ�����л��ܱ�,ʧ��");
		}
		
		for (Iterator iter = delList.iterator(); iter.hasNext();)
		{
			RCategoryVO element = (RCategoryVO) iter.next();
			
			try
			{
				// ɾ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ���
				BookCategoryDao.getInstance().deleteCateGory(element);
			}
			catch (Exception e)
			{
				logger.debug("ȥ�����л��ܱ�,ʧ��");
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("����ר�������б�ȥ�����л��ܱ�,����");
		}
		
		return true;
	}
	
	/**
	 * �ڶ���������ר����Ϣ����������»����д��ڵĻ���
	 */
	public void diySimulationDataTree()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����ר����Ϣ����������»����д��ڵĻ���,��ʼ");
		}
		try
		{
			BookCategoryDao.getInstance().updateDataToTree();
		}
		catch (Exception e)
		{
			logger.debug("����ר����Ϣ����������»����д��ڵĻ���,ʧ��");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("����ר����Ϣ����������»����д��ڵĻ���,����");
		}
	}
	
	/**
	 * ��һ����ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���
	 */
	public boolean cleanOldSimulationDataRank()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���,��ʼ");
		}
		
		try
		{
			BookCategoryDao.getInstance().cleanOldSimulationDataRank();
		}
		catch (BOException e)
		{
			logger.debug("ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���,ʧ��");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���,����");
		}
		
		return true;
	}
	
	/**
	 * �ڶ������������а���Ϣ����������»����д��ڵĻ���
	 */
	public void diySimulationDataRank()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�������а���Ϣ����������»����д��ڵĻ���,��ʼ");
		}
		
		try
		{
			// �������а���Ϣ����������»����д��ڵĻ���
			BookCategoryDao.getInstance().insertDataToRank();
		}
		catch (BOException e)
		{
			logger.debug("�������а���Ϣ����������»����д��ڵĻ���,ʧ��");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�������а���Ϣ����������»����д��ڵĻ���,����");
		}
	}
	
	/**
	 * ���������������а���Ϣ��������Ʒ����
	 */
	public void addDataByRankToReference()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�������а���Ϣ��������Ʒ����,��ʼ");
		}
		
		try
		{
			// �������а���Ϣ��������Ʒ����
			BookCategoryDao.getInstance().insertDataByRankToReference();
		}
		catch (BOException e)
		{
			logger.debug("�������а���Ϣ��������Ʒ����,ʧ��");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�������а���Ϣ��������Ʒ����,����");
		}
	}
	
	/**
	 * ����ͼ����µ�����Ϣ���봦��
	 * 
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookMonthCity(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)
			throws Exception
	{
		String line = null;
		BufferedReader br = null;
		List<String> err = new ArrayList<String>();
		Map<String, String> allBookBag = BookBagDao.getInstance()
				.queryAllBookBags();
		Map<String, String> provinceMap = BookBagAreaDAO.getInstance()
				.queryAreaMap("province");
		Map<String, String> cityMap = BookBagAreaDAO.getInstance()
				.queryAreaMap("city");
		for (int i = 0; i < lfs.length; i++)
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					lfs[i]), encoding));
			int lineNum = 0;
			while ((line = br.readLine()) != null)
			{
				lineNum++;
				if (lineNum == 1)
				{
					line = PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�" + lfs[i] + "��" + lineNum + "�����ݡ�");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// ��ȡҪ����Ķ���
				String errorArea = "";
				String[] dataArray = line.split("[" + sep + "]");
				BookBagAreaVO bookBagArea = new BookBagAreaVO();
				if (!bookBagArea.setValue(dataArray, provinceMap, cityMap,
						errorArea))
				{
					// ���ݸ�ʽ����
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݸ�ʽ����");
					if (!"".equals(errorArea))
					{
						errorRowNumber.append("<br>").append("  ��").append(
								lineNum).append("�����ݵ�����Ϣ����").append(errorArea);
					}
					// ���ݸ�ʽ����
					else
					{
						errorRowNumber.append("<br>").append("  ��").append(
								lineNum).append("�����ݸ�ʽ����");
					}
					rs[2]++;
					err.add(line);
					continue;
				}
				// ֻ�ǲ��ֵ�����Ϣ����
				else if (!"".equals(errorArea))
				{
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ݵ�����Ϣ����BOOKBAGID:").append(
									bookBagArea.getBookBagId()).append(
									errorArea);
				}
				// У�����id
				if (!allBookBag.containsKey(bookBagArea.getBookBagId()))
				{
					logger.error("���ID�����ڣ�" + bookBagArea.getBookBagId());
					errorRowNumber.append("<br>").append("  ��").append(lineNum)
							.append("�����ID������");
					err.add(line);
					rs[2]++;
					continue;
				}
				else
				{
					// У���������
					if (!bookBagArea.getBookBagName().equals(
							allBookBag.get(bookBagArea.getBookBagId())))
					{
						logger.error("�������������ID��ƥ�䣡"
								+ bookBagArea.getBookBagId());
						errorRowNumber.append("<br>").append("  ��").append(
								lineNum).append("�������������ID��ƥ��");
						err.add(line);
						rs[2]++;
						continue;
					}
				}
				
				try
				{
					logger.info("insert new bookBagCity:"
							+ bookBagArea.getBookBagId());
					BookBagAreaDAO.getInstance().addBookBagArea(bookBagArea);
					rs[1]++;
				}
				catch (Exception e)
				{
					logger.error("�ļ�" + lfs[i] + "��" + lineNum + "�����ݴ���ʧ��");
					logger.error(e);
					rs[3]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// ���ɵ�������ļ�
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * �����ı����������Ϣ������״ֵ̬
	 * 
	 * @param status
	 *            start����ʼ״̬��end������״̬��
	 * @return �������
	 */
	public boolean updateBookBagArea(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�����ı����������Ϣ������״ֵ̬,��ʼ" + status);
		}
		
		try
		{
			BookBagAreaDAO.getInstance().updateBookBagAreaByStart(status);
		}
		catch (DAOException e)
		{
			logger.error(e);
			return false;
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�����ı����������Ϣ������״ֵ̬,����");
		}
		
		return true;
	}
	
	/**
	 * ����ͬ��ʱ���һ��ɾ��ʧЧ����
	 * 
	 * @param type
	 * @return
	 */
	public boolean delBookBagAreaByStart(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����ͬ��ʱ���һ��ɾ��ʧЧ����,��ʼ" + type);
		}
		
		try
		{
			BookBagAreaDAO.getInstance().delBookBagAreaByStart(type);
		}
		catch (DAOException e)
		{
			logger.error(e);
			return false;
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("����ͬ��ʱ���һ��ɾ��ʧЧ����,����");
		}
		
		return true;
	}
	
	/**
	 * �鿴��ǰ���в���������������ֵ
	 * 
	 * @return ��ǰ����������������ֵ
	 */
	public int queryCountBookBagArea()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����ͬ��ʱ���һ��ɾ��ʧЧ����,��ʼ");
		}
		
		try
		{
			return BookBagAreaDAO.getInstance().queryCountBookBagArea();
		}
		catch (DAOException e)
		{
			logger.error(e);
			return 0;
		}
	}
}
