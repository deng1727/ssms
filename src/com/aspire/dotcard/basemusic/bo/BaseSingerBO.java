package com.aspire.dotcard.basemusic.bo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.dao.BaseSingerDAO;
import com.aspire.dotcard.basemusic.vo.BaseSingerVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author wangminlong
 */
public class BaseSingerBO
{

	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BaseSingerBO.class);

	private static BaseSingerBO instance = new BaseSingerBO();

	private BaseSingerBO()
	{}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BaseSingerBO getInstance()
	{
		return instance;
	}

	/**
	 * 查询基地音乐歌手信息列表出错
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryBaseSingerList(PageResult page, BaseSingerVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.queryBaseSingerList( ) is start...");
		}

		try
		{
			BaseSingerDAO.getInstance().queryBaseSingerList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回基地音乐歌手信息列表时发生数据库异常！");
		}
	}

	/**
	 * 用于查询基地音乐歌手详情信息
	 * 
	 * @param singerId
	 * @return
	 * @throws BOException
	 */
	public BaseSingerVO queryBaseSingerVO(String singerId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.queryBaseSingerVO(" + singerId
					+ ") is start...");
		}

		try
		{
			return BaseSingerDAO.getInstance().queryBaseSingerVO(singerId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询基地音乐歌手详情信息时发生数据库异常！");
		}
	}

	/**
	 * 用于变更基地音乐歌手详情
	 * 
	 * @param singerVO
	 * @throws BOException
	 */
	public void updateBaseSinger(BaseSingerVO singerVO) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.updateBaseSinger() is start...");
		}

		try
		{
			BaseSingerDAO.getInstance().updateBaseSingerVO(singerVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("变更基地音乐歌手详情时发生数据库异常！");
		}
	}

	/**
	 * 用于导出基地音乐歌手数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List list = null;

		try
		{
			if (isAll)
			{
				list = BaseSingerDAO.getInstance().queryListByExport("", "");
			}
			else
			{
				String singerId = this.getParameter(request, "singerId").trim();
				String singerName = this.getParameter(request, "singerName")
						.trim();
				list = BaseSingerDAO.getInstance().queryListByExport(singerId,
						singerName);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出基地音乐歌手数据时发生数据库异常：", e);
		}

		generateQueryDataExcel(list, wwb);
	}

	/**
	 * 生成应用更新版本的excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryDataExcel(List list, WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = wwb.createSheet(sheetName, 0); // 创建sheet
		Label label = null;
		int row = 0;
		int col = 0;
		int sheet = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				if(i%65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				BaseSingerVO vo = (BaseSingerVO) list.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getSingerId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSingerName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNameLetter());
				ws.addCell(label);
				label = new Label(col++, row, vo.getDescription());
				ws.addCell(label);
				label = new Label(col++, row, vo.getImgUrl());
				ws.addCell(label);
				label = new Label(col++, row, vo.getType());
				ws.addCell(label);
				label = new Label(col++, row, vo.getUpdate());
				ws.addCell(label);
			}
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "歌手ID");
		ws.addCell(label);
		label = new Label(col++, row, "歌手名称");
		ws.addCell(label);
		label = new Label(col++, row, "歌手名称首字母");
		ws.addCell(label);
		label = new Label(col++, row, "歌手简介");
		ws.addCell(label);
		label = new Label(col++, row, "歌手图片");
		ws.addCell(label);
		label = new Label(col++, row, "歌手属性");
		ws.addCell(label);
		label = new Label(col++, row, "最后修改日期");
		ws.addCell(label);
	}
	
	/**
	 * 从请求中获取参数，如果为null就返回空字符串""
	 * 
	 * @param request
	 *            http请求
	 * @param key
	 *            参数的关键字
	 * @return 参数值
	 */
	protected String getParameter(HttpServletRequest request, String key)
	{
		String value = request.getParameter(key);
		if (value == null)
		{
			value = "";
		}
		return value;
	}

	/**
	 * 文件批量导入基地音乐歌手
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importSingerData(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.importSingerData( ) is start...");
		}

		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];

		try
		{
			// 解析EXECL文件，获取终端软件版本列表
			List list = this.paraseDataFile(dataFile);

			BaseSingerDAO.getInstance().updateBaseSinger(list, temp);

			ret.append("成功导入" + temp[0] + "条记录.");

			if (!"".equals(temp[1]))
			{
				ret.append("\n导入不成功id为").append(temp[1]);
			}

			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("导入基地音乐歌手时发生数据库异常！");
		}
	}

	/**
	 * 解析EXECL文件，获取要添加的基地音乐歌手
	 * 
	 * @param dataFile
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public List paraseDataFile(FormFile dataFile) throws BOException
	{
		logger.info("BaseSingerBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		boolean isTrue;

		try
		{
			book = Workbook.getWorkbook(dataFile.getInputStream());
			Sheet[] sheets = book.getSheets();
			int sheetNum = sheets.length;
			if (logger.isDebugEnabled())
			{
				logger.debug("paraseSoftVersion.sheetNum==" + sheetNum);
			}

			for (int i = 0; i < sheetNum; i++)
			{
				int rows = sheets[i].getRows();
				int columns = 2;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BaseSingerVO vo = new BaseSingerVO();

					for (int j2 = 0; j2 < columns; j2++)
					{
						String value = sheets[i].getCell(j2, j).getContents()
								.trim();

						// ID不可以为空
						if ("".equals(value))
						{
							logger.error("解析导入文件时发现，数据中有为空现象，于文件中第" + j
									+ "行出现，此行不予处理！");
							isTrue = false;
							break;
						}

						// ID不可以为空
						if (j2 == 1
								&& !("11".equals(value) || "12".equals(value)
										|| "13".equals(value)
										|| "21".equals(value)
										|| "22".equals(value)
										|| "23".equals(value)
										|| "31".equals(value)
										|| "32".equals(value) || "33"
										.equals(value)))
						{

							logger.error("解析导入文件时发现，歌手属性不为指定枚举值，于文件中第" + j
									+ "行出现，此行不予处理！");
							isTrue = false;
							break;
						}

						switch (j2)
						{
						case 0:
							vo.setSingerId(value);
							break;
						case 1:
							vo.setType(value);
							break;
						}
					}

					if (isTrue)
					{
						list.add(vo);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
			throw new BOException("解析导入文件出现异常", e);
		}
		finally
		{
			book.close();
		}
		return list;
	}

	/**
	 * 用于查询基地音乐歌手详情
	 * 
	 * @param singerId
	 * @throws BOException
	 */
	public List querySingerIdKeyResource(String singerId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("BaseSingerBO.querySingerIdKeyResource( ) is start...");
		}
		List list = null;
		try
		{
			list = BaseSingerDAO.getInstance().querySingerKeyResource(singerId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询基地音乐歌手详情时发生数据库异常！");
		}
		return list;
	}

	/**
	 * 用于返回基地音乐歌手扩展字段列表
	 * 
	 * @return
	 * @throws BOException
	 */
	public List querySingerKeyBaseList(String tid) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.querySingerKeyBaseList( ) is start...");
		}

		try
		{
			return BaseSingerDAO.getInstance().querySingerKeyBaseList(tid);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回基地音乐歌手扩展字段列表时发生数据库异常！", e);
		}
	}
}
