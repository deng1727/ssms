package com.aspire.dotcard.baseVideo.bo;

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
import com.aspire.dotcard.baseVideo.dao.VideoProgramDAO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseread.dao.BaseAuthorDAO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author wangminlong
 */
public class VideoProgramBO
{
	
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(VideoProgramBO.class);
	
	private static VideoProgramBO instance = new VideoProgramBO();
	
	private VideoProgramBO()
	{
	}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static VideoProgramBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 查询视频节目列表
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryVideoProgramList(PageResult page, ProgramVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoProgramBO.queryVideoProgramList( ) is start...");
		}
		
		try
		{
			VideoProgramDAO.getInstance().queryVideoProgramList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回视频节目列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于导出视频节目数据
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
		ProgramVO vo = new ProgramVO();
		try
		{
			if (isAll)
			{
				vo.setProgramId("");
				vo.setProgramName("");
				vo.setNodeId("");
				vo.setVideoId("");
			}
			else
			{
				String programId = this.getParameter(request, "programId")
						.trim();
				String programName = this.getParameter(request, "programName")
						.trim();
				String videoId = this.getParameter(request, "videoId").trim();
				String nodeId = this.getParameter(request, "nodeId").trim();
				
				vo.setProgramId(programId);
				vo.setProgramName(programName);
				vo.setNodeId(nodeId);
				vo.setVideoId(videoId);
			}
			list = VideoProgramDAO.getInstance().queryListByExport(vo);
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
	 * @throws BOException 
	 */
	private void generateQueryDataExcel(List list, WritableWorkbook wwb) throws BOException
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
				
				ProgramVO vo = (ProgramVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getProgramId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getVideoId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getProgramName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getLastUpTime_Y() + "年"
						+ vo.getLastUpTime_M() + "月" + vo.getLastUpTime_D()
						+ "日");
				ws.addCell(label);
				label = new Label(col++, row, vo.getShowTime());
				ws.addCell(label);
			}
		}
		catch (RowsExceededException e)
		{
			logger.error(e);
			throw new BOException("导出文件出现异常",e); 
		}
		catch (WriteException e)
		{
			logger.error(e);
			throw new BOException("导出文件出现异常",e); 
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new BOException("导出文件出现异常",e); 
		}
	}
	
	private void setTitle(WritableSheet ws) throws RowsExceededException,
			WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "节目编号");
		ws.addCell(label);
		label = new Label(col++, row, "内容编号");
		ws.addCell(label);
		label = new Label(col++, row, "节目名称");
		ws.addCell(label);
		label = new Label(col++, row, "栏目编码");
		ws.addCell(label);
		label = new Label(col++, row, "栏目名称");
		ws.addCell(label);
		label = new Label(col++, row, "最后修改时间");
		ws.addCell(label);
		label = new Label(col++, row, "节目时长");
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
	public String importAuthorData(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.importAuthorData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];
		
		// 解析EXECL文件，获取终端软件版本列表
		List list = this.paraseDataFile(dataFile);
		
		// 调用ReadReferenceDAO进行新增
		BaseAuthorDAO.getInstance().updateReadAuthor(list, temp);
		
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
	public List paraseDataFile(FormFile dataFile) throws BOException
	{
		logger.info("BaseAuthorBO.paraseDataFile() is start!");
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
				int columns = 5;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BookAuthorVO vo = new BookAuthorVO();
					
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
							case 0:
								vo.setAuthorId(value);
								break;
							case 1:
								vo.setNameLetter(value);
								break;
							case 2:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("解析导入文件时发现，是否原创大神为非0、1值，于文件中第"
											+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								vo.setIsOriginal(value);
								break;
							case 3:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("解析导入文件时发现，是否出版专家为非0、1值，于文件中第"
											+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								vo.setIsPublish(value);
								break;
							case 4:
								vo.setRecommendManual(value);
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
}
