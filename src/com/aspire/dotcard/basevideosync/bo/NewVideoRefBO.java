package com.aspire.dotcard.basevideosync.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.dao.NewVideoRefDAO;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;

public class NewVideoRefBO {
	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory.getLogger(NewVideoRefBO.class);

	private static NewVideoRefBO bo = new NewVideoRefBO();

	private NewVideoRefBO() {
	}

	public static NewVideoRefBO getInstance() {
		return bo;
	}
	/**
	 * 用于查询新视频详情
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public ProgramVO queryNewVideoInfo(String programId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewVideoInfo( ) is start...");
		}
		ProgramVO vo = null;
		try
		{
			// 调用NewMusicRefDAO进行查询
			vo = NewVideoRefDAO.getInstance().queryNewVideoInfo(programId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询新视频列表时发生数据库异常！");
		}
		return vo;
	}
	
	/**
	 * 用于导出基地视频列表数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request, WritableWorkbook wwb)
			throws BOException
	{
		// 从请求中获取货架内码
        String categoryId = request.getParameter("categoryId").trim();
        List list = null;
        
		try
		{
			list = NewVideoRefDAO.getInstance().queryListByExport(categoryId);
		}
		catch (DAOException e)
		{
			logger.error("于导出基地新视频数据时发生数据库异常：", e);
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
				
				VideoReferenceVO vo = (VideoReferenceVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getProgramId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getProgramName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getTagName());
				ws.addCell(label);
				String feetype= vo.getFeetype();
				if("1".equals(feetype)){
					feetype = "免费";
				}else if("2".equals(feetype)){
					feetype = "收费";
				}else if("3".equals(feetype)){
					feetype = "仅支持按次";
				}else{
					feetype = "";
				}
				label = new Label(col++, row, feetype);
				ws.addCell(label);
				label = new Label(col++, row, vo.getBroadcast());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCountriy());
				ws.addCell(label);
				label = new Label(col++, row, vo.getContentType());
				ws.addCell(label);
				label = new Label(col++, row, vo.getLastUpTime());
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
		
		label = new Label(col++, row, "节目ID");
		ws.addCell(label);
		label = new Label(col++, row, "节目名称");
		ws.addCell(label);
		label = new Label(col++, row, "一级分类");
		ws.addCell(label);
		label = new Label(col++, row, "资费");
		ws.addCell(label);
		label = new Label(col++, row, "播出年代");
		ws.addCell(label);
		label = new Label(col++, row, "国家及地区");
		ws.addCell(label);
		label = new Label(col++, row, "内容类型");
		ws.addCell(label);
		label = new Label(col++, row, "最后修改时间");
		ws.addCell(label);
	}
}
