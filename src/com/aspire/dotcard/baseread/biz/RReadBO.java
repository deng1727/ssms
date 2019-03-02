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
 * 基地图书处理业务类
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
			logger.error("当前初始化失败了，请查看配置项！", e);
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
	 * 基地图书专区导入处理
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
		// 查询所有的货架
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				RCategoryVO cate = new RCategoryVO();
				if (!cate.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				try
				{
					// 新增
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
							// 修改
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
							// 修改
							updateBookArea(cate);
							rs[2]++;
						}
					}
					else if (cate.getChangeType() == 3)
					{
						// 下线
						delBookArea(cate);
						rs[3]++;
						allAreaCate.remove(cate.getCateId());
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_catalog"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 新增图书专区
	 * 
	 * @param cate
	 */
	private void addBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("insert book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().add(cate);
		
	}
	
	/**
	 * 更新图书专区
	 * 
	 * @param cate
	 */
	private void updateBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("update book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().update(cate);
	}
	
	/**
	 * 删除图书专区
	 * 
	 * @param cate
	 */
	private void delBookArea(RCategoryVO cate) throws Exception
	{
		logger.debug("delete book Area:" + cate.getCateId());
		BookCategoryDao.getInstance().delete(cate);
	}
	
	/**
	 * 基地图书排行导入处理 总榜处理
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
		// 先删除之前的榜单内容
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				
				String[] dataArray = line.split("[" + sep + "]");
				
				ReferenceVO ref = new ReferenceVO();
				
				if (!ref.setValueByRank(dataArray, type))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				
				if (null == allBook.get(ref.getBookId()))
				{
					logger.error("图书不存在！" + ref.getBookId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行图书不存在");
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		
		// 生成导入错误文件
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
	 * 基地图书包月导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				BookBagVO bookBag = new BookBagVO();
				if (!bookBag.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (!cateMap.containsKey(bookBag.getCateId()))
				{
					logger.error("区专ID不存在！" + bookBag.getCateId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行区专ID不存在");
					err.add(line);
					rs[5]++;
					continue;
				}
				try
				{
					logger
							.debug("insert new bookBag:"
									+ bookBag.getBookBagId());
					// 新增
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
						// 下线
						BookBagDao.getInstance().deleteBookBag(bookBag);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 基地书包内容导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				BookBagContentVO bookBagContent = new BookBagContentVO();
				if (!bookBagContent.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allBook.get(bookBagContent.getBookId()))
				{
					logger.error("图书不存在！" + bookBagContent.getBookId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行图书不存在");
					err.add(line);
					rs[5]++;
					continue;
				}
				if (null == allBookBag.get(bookBagContent.getBookBagId()))
				{
					logger.error("书包不存在！" + bookBagContent.getBookId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行书包不存在");
					err.add(line);
					rs[5]++;
					continue;
				}
				try
				{
					logger.debug("insert new bookBagContent:"
							+ bookBagContent.getBookBagId());
					// 新增
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
						// 下线
						BookBagContentDao.getInstance().updateBookBagContent(
								bookBagContent);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_bookContent"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 基地终端目录导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				MoDirectoryVO moDirectory = new MoDirectoryVO();
				if (!moDirectory.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				if (!cateMap.containsKey(moDirectory.getCateId()))
				{
					logger.error("区专ID不存在！" + moDirectory.getCateId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行区专ID不存在");
					err.add(line);
					rs[4]++;
					continue;
				}
				try
				{
					logger.debug("insert new moDirectory:"
							+ moDirectory.getMoDirectoryId());
					// 新增
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_moDirectory"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 基地图书商品导入处理 用于专区内容导入
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String errorArea = "";
				String[] dataArray = line.split("[" + sep + "]");
				ReferenceVO ref = new ReferenceVO();
				if (!ref.setValue(dataArray, provinceMap, cityMap, errorArea))
				{
					// 地域信息完全错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					/*if (!"".equals(errorArea))
					{
						errorRowNumber.append("<br>").append("  第").append(
								lineNum).append("行数据地域信息错误").append(errorArea);
					}
					// 数据格式错误
					else
					{
						errorRowNumber.append("<br>").append("  第").append(
								lineNum).append("行数据格式错误");
					}*/
					
					rs[4]++;
					err.add(line);
					continue;
				}
				// 只是部分地域信息错误
				else if (!"".equals(errorArea))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据地域信息错误BOOKID:"+ref.getBookId()+errorArea);
					/*errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据地域信息错误BOOKID:").append(ref.getBookId())
							.append(errorArea);*/
				}
				/*
				 * if (!ref.setValue(dataArray)) { // 数据格式错误 logger.error("文件" +
				 * lfs[i] + "第" + lineNum + "行数据格式错误"); errorRowNumber.append("<br>")
				 * .append(" 第") .append(lineNum) .append("行数据格式错误"); rs[4]++;
				 * err.add(line); continue; }
				 */
				if (null == allBook.get(ref.getBookId()))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行 图书不存在");
					/*errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行图书不存在");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allArea.get(ref.getCategoryId()))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行 专区不存在");
					/*errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行专区不存在");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				if (null == allCate.get(ref.getCategoryId()))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行 货架ID不存在");
					/*errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("货架ID不存在");*/
					rs[4]++;
					err.add(line);
					continue;
				}
				else
				{
					// 设置货架id
					ref.setcId(Integer.parseInt(String.valueOf(allCate.get(ref
							.getCategoryId()))));
				}
				try
				{
					// 新增
					if (ref.getChangeType() == 1 || ref.getChangeType() == 2)
					{
						if(null == allReference.get(ref.getCategoryId()+","+ref.getBookId())){
							addBookReference(ref);
							allReference.put(ref.getCategoryId()+","+ref.getBookId(), "0");
							rs[1]++;
						}else{
							// 修改
							updateBookReference(ref);
							allReference.put(ref.getCategoryId()+","+ref.getBookId(),"0");
							rs[2]++;
						}
					}
					else if (ref.getChangeType() == 3)
					{
						// 下线
						delBookReference(ref);
						rs[3]++;
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		if(allReference.size() > 0){
			//把剩余的删除
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
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_catalogcontent"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 新增专区内容信息
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
	 * 基地图书信息导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				RBookVO book = new RBookVO();
				if (!book.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					logger.error("错误内容为:" + line);
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
					rs[4]++;
					err.add(line);
					continue;
				}
				// 判断图书作者是否存在
				if (null == authorMap.get(book.getAuthorId()))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行 图书作者标识不存在");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行图书作者标识不存在");
					rs[4]++;
					err.add(line);
					continue;
				}
				// 判断图书类型是否存在
				if (null == typeMap.get(book.getTypeId()))
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行 图书类型不存在");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行图书类型不存在");
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
						// 修改
						updateBook(book);
						rs[2]++;
						all.put(book.getBookId(), "");
						num++;
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		// 删除全量文件中不存在的多余数据
		if (num > baseBookThresholdNum)
		{
			delBook(all, rs);
		}
		else
		{
			logger.error("当前全量变更数过少，少过了指定阀值。所以这里就不删了，看到的手工处理下。");
		}
		
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void delBook(Map all, int[] rs) throws Exception
	{
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。开始");
		
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
					logger.debug("开始删除在本次全量产品文件不存在的老的数据ID，ID=" + keyId);
				}
				
				// 这个地方要加一个删除的逻辑。。。
				try
				{
					// 下线
					delBook(keyId);
					rs[3]++;
				}
				catch (DAOException e)
				{
					logger.error("删除在本次全量产品文件不存在的老的数据ID时发生错误，ID=" + keyId, e);
				}
			}
		}
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。结束");
	}
	
	/**
	 * 新增图书信息
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
		// throw new BOException("图书" + book.getBookName() + "作者不存在");
		// }
		// 设置作者名称
		// book.setAuthorName(author.getAuthorName());
		// 设置大类id
		// book.setTypeId(type.getParentId());
		Object[] args = new Object[] { book };
		Class[] argsClass = new Class[] { RBookVO.class };
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "add",
				args, argsClass);
		this.taskRunner.addTask(task);
	}
	
	/**
	 * 更新
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
		// throw new BOException("图书" + book.getBookName() + "作者不存在");
		// }
		// 设置作者名称
		// book.setAuthorName(author.getAuthorName());
		// 设置大类id
		// book.setTypeId(type.getParentId());
		Object[] args = new Object[] { book };
		Class[] argsClass = new Class[] { RBookVO.class };
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(),
				"update", args, argsClass);
		this.taskRunner.addTask(task);
		
	}
	
	/**
	 * 删除、标志位删除 设置delflag=1
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
	 * 基地图书猜你喜欢——历史阅读推荐接口导入处理
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
                logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // 获取要处理的对象
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikeHisReadVO likeHisRead = new ReadLikeHisReadVO();
                if (!likeHisRead.setValue(dataArray, allBook))
                {
                    // 数据格式错误
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行数据格式错误");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likeHisRead.getMsisdn();
                	
                	// 查看当前主键是否存在库中
                	if(allKey.containsKey(key))
                	{
                		// 如果存在，删除当前主键所对应记录
                		ReadLikeDAO.getInstance().deleteDataByKey(1, key);
                		rs[3]++;
                	}
                	
                	// 新增解析的数据
                	ReadLikeDAO.getInstance().insertDataByHis(likeHisRead);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // 生成导入错误文件
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeHisRead"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * 基地图书猜你喜欢——名家推荐接口导入处理
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
                logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // 获取要处理的对象
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikeAuthorVO likeAuthor = new ReadLikeAuthorVO();
                if (!likeAuthor.setValue(dataArray, allBook, authorMap))
                {
                    // 数据格式错误
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行数据格式错误");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likeAuthor.getMsisdn();
                	
                	// 查看当前主键是否存在库中
                	if(allKey.containsKey(key))
                	{
                		// 如果存在，删除当前主键所对应记录
                		ReadLikeDAO.getInstance().deleteDataByKey(2, key);
                		rs[3]++;
                	}
                	
                	// 新增解析的数据
                	ReadLikeDAO.getInstance().insertDataByAuthor(likeAuthor);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // 生成导入错误文件
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeAuthorRead"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * 基地图书猜你喜欢——图书级阅读关联推荐接口
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
                logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // 获取要处理的对象
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikePercentageVO likePercentage = new ReadLikePercentageVO();
                if (!likePercentage.setValue(dataArray, allBook))
                {
                    // 数据格式错误
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行数据格式错误");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likePercentage.getSourceBookId();
                	
                	// 查看当前主键是否存在库中
                	if(allKey.containsKey(key))
                	{
                		// 如果存在，删除当前主键所对应记录
                		ReadLikeDAO.getInstance().deleteDataByKey(3, key);
                		rs[3]++;
                	}
                	
                	// 新增解析的数据
                	ReadLikeDAO.getInstance().insertDataByPercentage(likePercentage);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // 生成导入错误文件
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeReadPer"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * 基地图书猜你喜欢——图书级订购关联推荐接口导入处理
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
                logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // 获取要处理的对象
                String[] dataArray = line.split("[" + sep + "]",-1);
                ReadLikePercentageVO likePercentage = new ReadLikePercentageVO();
                if (!likePercentage.setValue(dataArray, allBook))
                {
                    // 数据格式错误
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行数据格式错误");
                    rs[4]++;
                    err.add(line);
                    continue;
                }

                try
                {
                	String key = likePercentage.getSourceBookId();
                	
                	// 查看当前主键是否存在库中
                	if(allKey.containsKey(key))
                	{
                		// 如果存在，删除当前主键所对应记录
                		ReadLikeDAO.getInstance().deleteDataByKey(4, key);
                		rs[3]++;
                	}
                	
                	// 新增解析的数据
                	ReadLikeDAO.getInstance().insertDataByOrderPercentage(likePercentage);
                	rs[1]++;
                	allKey.put(key, key);
				}
                catch (Exception e)
                {
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        // 生成导入错误文件
        if (err.size() > 0)
        {
            String errFile = "err_bookLikeOrderPer"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * 基地图书推荐导入处理
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
                logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");

                if ("".equals(line.trim()))
                {
                    continue;
                }

                // 获取要处理的对象
                String[] dataArray = line.split("[" + sep + "]");
                RecommendVO recommend = new RecommendVO();
                if (!recommend.setValue(dataArray))
                {
                    // 数据格式错误
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行数据格式错误");
                    rs[4]++;
                    err.add(line);
                    continue;
                }
                if (null == allBook.get(recommend.getBookId()))
                {
                    logger.error("推荐图书不存在" + recommend.getBookId());
                    errorRowNumber.append("<br>")
                                  .append("  第")
                                  .append(lineNum)
                                  .append("行推荐图书不存在");
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
                        logger.error("图书分类不存在" + recommend.getBookId());
                        errorRowNumber.append("<br>")
                                      .append("  第")
                                      .append(lineNum)
                                      .append("行图书分类不存在");
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
                    logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
                    logger.error(e);
                    rs[5]++;
                    err.add(line);
                    error.append(lineNum).append(", ");
                }
            }
            rs[0] += lineNum;
        }
        
        logger.info("开始删除当前数据文件中不存在的原数据库数据！");
        
        // 删除操作
        Iterator key = allRec.keySet().iterator();
        while(key.hasNext())
        {
			String tempKey = (String) key.next();
			logger.info("开始删除当前数据文件中不存在的原数据库数据！");
			if ("-1".equals(allRec.get(tempKey)))
			{
				logger.info("开始删除当前数据文件中不存在的原数据库数据！，没被修改的待删除数据为：" + tempKey);
				String[] rec = tempKey.split("\\|", -1);
				if (rec.length != 3)
				{
					logger.error("当前数据删除失败，数据为：" + tempKey);
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
        
        // 生成导入错误文件
        if (err.size() > 0)
        {
            String errFile = "err_bookrecommend"
                             + DateUtil.formatDate(new Date(), "yyyyMMdd")
                             + ".txt";
            makeErrFile(errFile, err);
        }
    }
	
	/**
	 * 新增推荐图书
	 * 
	 * @param recommend
	 */
	private void addRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().add(recommend);
	}
	
	/**
	 * 更新推荐信息
	 * 
	 * @param recommend
	 */
	private void updateRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().update(recommend);
	}
	
	/**
	 * 删除推荐信息
	 * 
	 * @param recommend
	 */
	private void delRecommend(RecommendVO recommend) throws Exception
	{
		logger.debug("insert recommend:" + recommend.getBookId());
		BookRecommendDao.getInstance().delete(recommend);
	}
	
	/**
	 * 基地图书作者导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]", -1);
				BookAuthorVO author = new BookAuthorVO();
				if (!author.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					logger.error("错误内容为:" + line);
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
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
						// 修改
						updateAuthor(author);
						rs[2]++;
						all.put(author.getAuthorId(), "");
						num++;
					}
				}
				catch (Exception e)
				{
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		// 删除全量数据里不存在的多余数据
		if (num > baseBookThresholdNum)
		{
			delAuthor(all, rs);
		}
		else
		{
			logger.error("当前全量变更数过少，少过了指定阀值。所以这里就不删了，看到的手工处理下。");
		}
		
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_author"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	private void delAuthor(Map all, int[] rs) throws Exception
	{
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。开始");
		
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
					logger.debug("开始删除在本次全量产品文件不存在的老的数据ID，ID=" + keyId);
				}
				
				// 这个地方要加一个删除的逻辑。。。
				try
				{
					// 下线
					deleteAuthor(keyId);
					rs[3]++;
				}
				catch (DAOException e)
				{
					logger.error("删除在本次全量产品文件不存在的老的数据ID时发生错误，ID=" + keyId, e);
				}
			}
		}
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。结束");
	}
	
	/**
	 * 新增作者
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
	 * 更新作者信息
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
	 * 删除作者
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
	 * 基地图书作者导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String[] dataArray = line.split("[" + sep + "]");
				BookUpdateVO update = new BookUpdateVO();
				if (!update.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		// 生成导入错误文件
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
	 * 处理基地图书数据分类导入
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
				
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				String[] dataArray = line.split("[" + sep + "]");
				RTypeVO type = new RTypeVO();
				if (!type.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					err.add(line);
					rs[5]++;
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_category"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 处理基地图书统计数据导入
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
				
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				String[] dataArray = line.split("[" + sep + "]");
				RCountVO count = new RCountVO();
				if (!count.setValue(dataArray))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据格式错误");
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					err.add(line);
					rs[5]++;
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		
		taskRunner.waitToFinished();// 等待更新数据库完毕。
		taskRunner.end();// 结束运行器
		set.clear();
		
		// 生成导入错误文件
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
	 * 生成导入错误的文件
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
			logger.error("导入错误文件失败", e);
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
	 * 更新货架下商品总数
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
			logger.error("更新货架下商品总数出错！", e);
		}
		logger.debug("update book category total books end!");
	}
	
	/**
	 * 更新图书分类下商品总数
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
			logger.error("更新分类下商品总数出错！", e);
		}
		logger.debug("update book type total books end!");
	}
	
	/**
	 * 处理基地图书校验文件
	 * 
	 * @param encoding
	 *            编码
	 * @param sep
	 *            分隔符
	 * @param lfs
	 *            文件列
	 * @return 返回结果
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
			// 如果存在，解析
			for (int i = 0; i < lfs.length; i++)
			{
				String tempFileName = String.valueOf(lfs[i]);
				
				if (logger.isDebugEnabled())
				{
					logger.debug("开始处理校验文件：" + tempFileName + " 校验分隔符为：" + sep);
				}
				
				// 读文件
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(tempFileName), encoding));
				
				while ((lineText = reader.readLine()) != null)
				{
					// 记录文件的行数。
					lineNumeber++;
					
					if (logger.isDebugEnabled())
					{
						logger.debug("开始处理校验文件第" + lineNumeber + "行数据。");
					}
					
					if (lineNumeber == 1)
					{
						// 删除第一行bom字符
						lineText = PublicUtil.delStringWithBOM(lineText);
					}
					
					// 校验文件的最后一行
					if (BaseVideoConfig.FILE_END.equals(lineText.trim()))
					{
						break;
					}
					
					// 读文件数据
					mailText.append(readVerfData(lineText, lineNumeber, sep));
				}
			}
		}
		catch (Exception e)
		{
			mailText.append("解析校验文件时发生错误 ！！！<br>");
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
	 * 解析校验文件信息（统一处理方式，因为校验文件格式一样）
	 * 
	 * @param lineText
	 *            当前行信息
	 * @param lineNumeber
	 *            第几行 便于记录错误信息
	 * @return 校验文件数据对象
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
			// 加入邮件错误信息...............
			return writeErrorToVerfMail(lineNumeber, lineText,
					"校验文件结构不对，大于或小于5个属性");
		}
		
		return vo.toMailText();
	}
	
	/**
	 * 写入校验处理发生的错误信息至邮件
	 * 
	 * @param lineNum
	 *            第几行
	 * @param dataText
	 *            什么内容
	 * @param reasonText
	 *            出错原因
	 */
	public String writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		return new StringBuffer("校验文件：").append(" 第").append(lineNum).append(
				"行: ").append(dataText).append("。 此数据有错原因为：")
				.append(reasonText).append("<br>").toString();
	}
	
	/**
	 * 第一步：根据专区信息，删除现货架中多出的货架
	 */
	public boolean cleanOldSimulationDataTree()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据专区和排行表去除旧有货架表,开始");
		}
		
		List delList = null;
		try
		{
			// 查询在专区表中不存在。在货架表中存在的货架，以删除
			delList = BookCategoryDao.getInstance()
					.cleanOldSimulationDataTree();
		}
		catch (DAOException e)
		{
			logger.debug("根据专区和排行表去除旧有货架表,失败");
		}
		
		for (Iterator iter = delList.iterator(); iter.hasNext();)
		{
			RCategoryVO element = (RCategoryVO) iter.next();
			
			try
			{
				// 删除专区表中不存在。在货架表中存在的货架
				BookCategoryDao.getInstance().deleteCateGory(element);
			}
			catch (Exception e)
			{
				logger.debug("去除旧有货架表,失败");
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据专区和排行表去除旧有货架表,结束");
		}
		
		return true;
	}
	
	/**
	 * 第二步：根据专区信息，新增或更新货架中存在的货架
	 */
	public void diySimulationDataTree()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据专区信息，新增或更新货架中存在的货架,开始");
		}
		try
		{
			BookCategoryDao.getInstance().updateDataToTree();
		}
		catch (Exception e)
		{
			logger.debug("根据专区信息，新增或更新货架中存在的货架,失败");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据专区信息，新增或更新货架中存在的货架,结束");
		}
	}
	
	/**
	 * 第一步：删除商品信息，在根据排行榜信息，删除现货架中多出的货架
	 */
	public boolean cleanOldSimulationDataRank()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("删除商品信息，在根据排行榜信息，删除现货架中多出的货架,开始");
		}
		
		try
		{
			BookCategoryDao.getInstance().cleanOldSimulationDataRank();
		}
		catch (BOException e)
		{
			logger.debug("删除商品信息，在根据排行榜信息，删除现货架中多出的货架,失败");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("删除商品信息，在根据排行榜信息，删除现货架中多出的货架,结束");
		}
		
		return true;
	}
	
	/**
	 * 第二步：根据排行榜信息，新增或更新货架中存在的货架
	 */
	public void diySimulationDataRank()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据排行榜信息，新增或更新货架中存在的货架,开始");
		}
		
		try
		{
			// 根据排行榜信息，新增或更新货架中存在的货架
			BookCategoryDao.getInstance().insertDataToRank();
		}
		catch (BOException e)
		{
			logger.debug("根据排行榜信息，新增或更新货架中存在的货架,失败");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据排行榜信息，新增或更新货架中存在的货架,结束");
		}
	}
	
	/**
	 * 第三步：根据排行榜信息，加入商品表中
	 */
	public void addDataByRankToReference()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据排行榜信息，加入商品表中,开始");
		}
		
		try
		{
			// 根据排行榜信息，加入商品表中
			BookCategoryDao.getInstance().insertDataByRankToReference();
		}
		catch (BOException e)
		{
			logger.debug("根据排行榜信息，加入商品表中,失败");
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据排行榜信息，加入商品表中,结束");
		}
	}
	
	/**
	 * 基地图书包月地域信息导入处理
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
				logger.debug("开始处理文件" + lfs[i] + "第" + lineNum + "行数据。");
				
				if ("".equals(line.trim()))
				{
					continue;
				}
				
				// 获取要处理的对象
				String errorArea = "";
				String[] dataArray = line.split("[" + sep + "]");
				BookBagAreaVO bookBagArea = new BookBagAreaVO();
				if (!bookBagArea.setValue(dataArray, provinceMap, cityMap,
						errorArea))
				{
					// 数据格式错误
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据格式错误");
					if (!"".equals(errorArea))
					{
						errorRowNumber.append("<br>").append("  第").append(
								lineNum).append("行数据地域信息错误").append(errorArea);
					}
					// 数据格式错误
					else
					{
						errorRowNumber.append("<br>").append("  第").append(
								lineNum).append("行数据格式错误");
					}
					rs[2]++;
					err.add(line);
					continue;
				}
				// 只是部分地域信息错误
				else if (!"".equals(errorArea))
				{
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行数据地域信息错误BOOKBAGID:").append(
									bookBagArea.getBookBagId()).append(
									errorArea);
				}
				// 校验书包id
				if (!allBookBag.containsKey(bookBagArea.getBookBagId()))
				{
					logger.error("书包ID不存在！" + bookBagArea.getBookBagId());
					errorRowNumber.append("<br>").append("  第").append(lineNum)
							.append("行书包ID不存在");
					err.add(line);
					rs[2]++;
					continue;
				}
				else
				{
					// 校验书包名称
					if (!bookBagArea.getBookBagName().equals(
							allBookBag.get(bookBagArea.getBookBagId())))
					{
						logger.error("书包名称与此书包ID不匹配！"
								+ bookBagArea.getBookBagId());
						errorRowNumber.append("<br>").append("  第").append(
								lineNum).append("书包名称与此书包ID不匹配");
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
					logger.error("文件" + lfs[i] + "第" + lineNum + "行数据处理失败");
					logger.error(e);
					rs[3]++;
					err.add(line);
					error.append(lineNum).append(", ");
				}
			}
			rs[0] += lineNum;
		}
		// 生成导入错误文件
		if (err.size() > 0)
		{
			String errFile = "err_book"
					+ DateUtil.formatDate(new Date(), "yyyyMMdd") + ".txt";
			makeErrFile(errFile, err);
		}
	}
	
	/**
	 * 用来改变书包地域信息的数据状态值
	 * 
	 * @param status
	 *            start：开始状态，end：结束状态。
	 * @return 变更条数
	 */
	public boolean updateBookBagArea(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用来改变书包地域信息的数据状态值,开始" + status);
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
			logger.debug("用来改变书包地域信息的数据状态值,结束");
		}
		
		return true;
	}
	
	/**
	 * 用于同步时最后一步删除失效数据
	 * 
	 * @param type
	 * @return
	 */
	public boolean delBookBagAreaByStart(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于同步时最后一步删除失效数据,开始" + type);
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
			logger.debug("用于同步时最后一步删除失效数据,结束");
		}
		
		return true;
	}
	
	/**
	 * 查看当前库中插入新增数据数量值
	 * 
	 * @return 当前表中新增数据总量值
	 */
	public int queryCountBookBagArea()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于同步时最后一步删除失效数据,开始");
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
