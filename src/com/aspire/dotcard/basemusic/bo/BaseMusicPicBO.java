package com.aspire.dotcard.basemusic.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.aspire.dotcard.basemusic.dao.BaseMusicPicDAO;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author wangminlong
 */
public class BaseMusicPicBO
{
	
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BaseMusicPicBO.class);
	
	private static BaseMusicPicBO instance = new BaseMusicPicBO();
	
	private BaseMusicPicBO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BaseMusicPicBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 查询基地音乐信息列表
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryMusicList(PageResult page, BaseMusicVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseMusicPicBO.queryMusicList( ) is start...");
		}
		
		try
		{
			BaseMusicPicDAO.getInstance().queryMusicList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回查询基地音乐信息列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于导出基地音乐数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportMusicPicData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<BaseMusicVO> list = null;
		
		try
		{
			if (isAll)
			{
				BaseMusicVO vo = new BaseMusicVO();
				vo.setMusicId("");
				vo.setMusicName("");
				vo.setSingerName("");
				list = BaseMusicPicDAO.getInstance().queryMusicListByExport(vo);
			}
			else
			{
				String musicId = request.getParameter("musicId").trim();
				String musicName = request.getParameter("musicName").trim();
				String singer = request.getParameter("singer").trim();
				
				BaseMusicVO vo = new BaseMusicVO();
				vo.setMusicId(musicId);
				vo.setMusicName(musicName);
				vo.setSingerName(singer);
				
				list = BaseMusicPicDAO.getInstance().queryMusicListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出基地音乐数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List<BaseMusicVO> list,
			WritableWorkbook wwb)
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
				if (i % 65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				BaseMusicVO vo = (BaseMusicVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getMusicId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getMusicName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSingerName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getMusicPic());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCreateTime());
				ws.addCell(label);
				label = new Label(col++, row, vo.getValidity());
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
		
		label = new Label(col++, row, "音乐ID");
		ws.addCell(label);
		label = new Label(col++, row, "音乐名称");
		ws.addCell(label);
		label = new Label(col++, row, "歌手名称");
		ws.addCell(label);
		label = new Label(col++, row, "音乐图片");
		ws.addCell(label);
		label = new Label(col++, row, "音乐导入时间");
		ws.addCell(label);
		label = new Label(col++, row, "音乐有效期");
		ws.addCell(label);
	}
	
	/**
	 * 文件批量导入基地音乐歌手
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importMusicPicData(FormFile dataFile) throws BOException
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
			List<BaseMusicVO> list = this.paraseDataFile(dataFile);
			
			BaseMusicPicDAO.getInstance().updateBaseMusicPic(list, temp);
			
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
	public List<BaseMusicVO> paraseDataFile(FormFile dataFile)
			throws BOException
	{
		logger.info("BaseSingerBO.paraseDataFile() is start!");
		List<BaseMusicVO> list = new ArrayList<BaseMusicVO>();
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
					BaseMusicVO vo = new BaseMusicVO();
					
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
								vo.setMusicId(value);
								break;
							case 1:
								vo.setMusicPic(value);
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
