package com.aspire.ponaadmin.web.game.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.game.dao.TWGameDAO;
import com.aspire.ponaadmin.web.game.vo.TWGameVO;

/**
 * @author wangminlong
 */
public class TWGameBO
{
	
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(TWGameBO.class);
	
	private static TWGameBO instance = new TWGameBO();
	
	private TWGameBO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static TWGameBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 查询图文游戏列表
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryTWGameList(PageResult page, TWGameVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("TWGameBO.queryTWGameList( ) is start...");
		}
		
		try
		{
			TWGameDAO.getInstance().queryTWGameList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回查询图文游戏列表时发生数据库异常！",e);
		}
	}
	
	/**
	 * 用于导出基地阅读作者数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<TWGameVO> list = null;
		
		try
		{
			if (isAll)
			{
				list = TWGameDAO.getInstance().queryListByExport("", "");
			}
			else
			{
				String gameId = this.getParameter(request, "gameId").trim();
				String gameName = this.getParameter(request, "gameName").trim();
				list = TWGameDAO.getInstance().queryListByExport(gameId,
						gameName);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出基地阅读作者数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List<TWGameVO> list,
			WritableWorkbook wwb)
	{
		
		String sheetName = "Sheet";
		WritableSheet ws = null; // 创建sheet
		Label label = null;
		int row = 0;
		int col = 0;
		int sheet = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				if (i % 65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				TWGameVO vo = (TWGameVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getGameId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getGameName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCPName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getGameDesc());
				ws.addCell(label);
				label = new Label(col++, row, String.valueOf(vo.getOldPrice()));
				ws.addCell(label);
				label = new Label(col++, row, String.valueOf(vo.getFee()));
				ws.addCell(label);
				label = new Label(col++, row, vo.getSortId());
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
	
	private void setTitle(WritableSheet ws) throws RowsExceededException,
			WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "游戏ID");
		ws.addCell(label);
		label = new Label(col++, row, "游戏名称");
		ws.addCell(label);
		label = new Label(col++, row, "供应商");
		ws.addCell(label);
		label = new Label(col++, row, "游戏简介");
		ws.addCell(label);
		label = new Label(col++, row, "原价资费（分）");
		ws.addCell(label);
		label = new Label(col++, row, "现价资费（分）");
		ws.addCell(label);
		label = new Label(col++, row, "排序序号");
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
	 * 文件批量导入基地阅读作者
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importTWGameSortId(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("TWGameBO.importAuthorData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];
		
		// 解析EXECL文件，获取终端软件版本列表
		List<TWGameVO> list = this.paraseDataFile(dataFile);
		
		// 调用ReadReferenceDAO进行新增
		TWGameDAO.getInstance().updateTWGameSortId(list, temp);
		
		ret.append("成功导入" + temp[0] + "条记录.");
		
		if (!"".equals(temp[1]))
		{
			ret.append("\n导入不成功id为").append(temp[1]);
		}
		
		return ret.toString();
	}
	
	/**
	 * 解析EXECL文件，获取要添加的商品信息
	 * 
	 * @param dataFile
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public List<TWGameVO> paraseDataFile(FormFile dataFile) throws BOException
	{
		logger.info("TWGameBO.paraseDataFile() is start!");
		List<TWGameVO> list = new ArrayList<TWGameVO>();
		Workbook book = null;
		boolean isTrue;
		
		try
		{
			Set<String> twIdKeySet = TWGameDAO.getInstance().getTWIdKeySet();
			Set<String> sortIdKeySet = TWGameDAO.getInstance().getSortIdKeySet();
			
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
					TWGameVO vo = new TWGameVO();
					
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
						
						switch (j2)
						{
							case 0:// gameid
								if (twIdKeySet.contains(value))
								{
									vo.setGameId(value);
								}
								else
								{
									logger
											.error("解析导入文件时发现，当前图文游戏不存在于图文信息表，于文件中第"
													+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								break;
							case 1://sortid
								try
								{
									value = String.valueOf(Integer.parseInt(value)*-1);
									vo.setSortId(value);
								}
								catch (NumberFormatException e)
								{
									logger.error("解析导入文件时发现，排序信息不是数值型，于文件中第"
											+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								break;
						}
						// 如果有一列出错。就不在处理这一行
						if(!isTrue)
						{
							continue;
						}
					}
					// 如果当前列没有问题 ，加入到将要处理的集合中
					if (isTrue)
					{
						if(sortIdKeySet.contains(vo.getGameId()))
						{
							vo.setDbType("U");
						}
						else
						{
							vo.setDbType("A");
						}
						list.add(vo);
					}
				}
			}
			
			twIdKeySet.clear();
			sortIdKeySet.clear();
		}
		catch (DAOException e1)
		{
			logger.error("得到数据库中图文游戏的全量id时发生错误", e1);
			throw new BOException("解析导入文件出现异常", e1);
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
}
