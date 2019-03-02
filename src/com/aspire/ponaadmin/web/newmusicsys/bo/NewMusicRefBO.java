/*
 * 文件名：NewMusicRefBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.newmusicsys.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class NewMusicRefBO
{
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(NewMusicRefBO.class);
	
	private static NewMusicRefBO instance = new NewMusicRefBO();
	
	private NewMusicRefBO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static NewMusicRefBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 用于查询当前货架下商品列表
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryNewMusicRefList(PageResult page, NewMusicRefVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewMusicRefList( ) is start...");
		}
		
		try
		{
			// 调用NewMusicRefDAO进行查询
			NewMusicRefDAO.getInstance().queryNewMusicRefList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回新音乐货架列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于移除指定货架下指定的新音乐
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws BOException
	 */
	public void removeNewMusicRefs(String categoryId, String[] musicId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.removeNewMusicRefs( ) is start...");
		}
		
		try
		{
			// 调用NewMusicRefDAO进行查询
			NewMusicRefDAO.getInstance()
					.removeNewMusicRefs(categoryId, musicId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回新音乐货架列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于查看指定货架中是否存在指定音乐
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws BOException
	 */
	public String isHasMusicRefs(String categoryId, String addMusicId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.isHasMusicRefs( ) is start...");
		}
		
		try
		{
			String[] addMusicIds = addMusicId.split(";");
			// 调用NewMusicRefDAO进行查询
			return NewMusicRefDAO.getInstance().isHasMusicRefs(categoryId,
					addMusicIds);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查看指定货架中是否存在指定音乐时发生数据库异常！");
		}
	}
	
	/**
	 * 用于添加指定的新音乐至货架中
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws BOException
	 */
	public void addNewMusicRefs(String categoryId, String addMusicId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.addNewMusicRefs( ) is start...");
		}
		
		try
		{
			String[] addMusicIds = addMusicId.split(";");
			// 调用NewMusicRefDAO进行新增
			NewMusicRefDAO.getInstance().addNewMusicRefs(categoryId,
					addMusicIds);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回新音乐货架列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于查询新音乐列表
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryNewMusicList(PageResult page, NewMusicRefVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewMusicList( ) is start...");
		}
		
		try
		{
			// 调用NewMusicRefDAO进行查询
			NewMusicRefDAO.getInstance().queryNewMusicList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询新音乐列表时发生数据库异常！");
		}
	}
	
	/**
	 * 用于设置新音乐货架下音乐商品排序值
	 * 
	 * @param categoryId
	 * @param setSortId
	 * @throws BOException
	 */
	public void setNewMusicSort(String categoryId, String setSortId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.setNewMusicSort( ) is start...");
		}
		
		try
		{
			String[] sort = setSortId.split(";");
			// 调用NewMusicRefDAO进行查询
			NewMusicRefDAO.getInstance().setNewMusicSort(categoryId, sort);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("设置新音乐货架下音乐商品排序值时发生数据库异常！");
		}
	}
	
	public String importContentADD(FormFile dataFile, String categoryId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.importContentADD( ) is start...");
		}
		StringBuffer ret = new StringBuffer();
		// 解析EXECL文件，获取终端软件版本列表
		List list = new ExcelHandle().paraseDataFile(dataFile, null);
		int errorN = 0;
		
		StringBuffer temp = new StringBuffer();
		int count = 0;
		for (int i = 0; i < list.size(); i++)
		{
			Map m = (Map) list.get(i);
			String sortId = (String) m.get(0);
			String contentId = (String) m.get(1);
			try
			{
				NewMusicRefDAO.getInstance().addReference(categoryId,
						contentId.trim(), (-1 * Integer.parseInt(sortId)) + "");
				count++;
			}
			catch (Exception e)
			{
				errorN++;
				if (errorN % 10 == 0)
				{
					temp.append("<br>").append(contentId);
				}
				else
				{
					temp.append(",").append(contentId);
				}
			}
			
		}
		NewMusicRefDAO.getInstance().approvalCategoryGoods(categoryId);
		ret.append("成功导入" + count + "条记录.");
		if (temp.length() != 0)
		{
			ret.append("导入不成功id为").append(temp);
		}
		return ret.toString();
	}
	
	/**
	 * 文件批量导入新音乐商品上架
	 * 
	 * @param dataFile
	 * @param categoryId
	 * @throws BOException
	 */
	public String importContentALL(FormFile dataFile, String categoryId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.importContentALL( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		int errorN = 0;
		
		try
		{
			// 用于清空原货架下新音乐
			NewMusicRefDAO.getInstance().delNewMusicRef(categoryId);
			
			// // 解析EXECL文件，获取终端软件版本列表
			// List list = BookRefBO.getInstance().paraseDataFile(dataFile);
			
			// 解析EXECL文件，获取终端软件版本列表
			List list = new ExcelHandle().paraseDataFile(dataFile, null);
			
			StringBuffer temp = new StringBuffer();
			int count = 0;
			for (int i = 0; i < list.size(); i++)
			{
				Map m = (Map) list.get(i);
				String sortId = (String) m.get(0);
				String contentId = (String) m.get(1);
				try
				{
					NewMusicRefDAO.getInstance().addReference(categoryId,
							contentId.trim(),
							(-1 * Integer.parseInt(sortId)) + "");
					count++;
				}
				catch (Exception e)
				{
					errorN++;
					if (errorN % 10 == 0)
					{
						temp.append("<br>").append(contentId);
					}
					else
					{
						temp.append(",").append(contentId);
					}
				}
				
			}
			NewMusicRefDAO.getInstance().approvalCategoryGoods(categoryId);
			ret.append("成功导入" + count + "条记录.");
			if (temp.length() != 0)
			{
				ret.append("导入不成功id为").append(temp);
			}
			
			// // 校驗文件中數據是否在新音乐表中存在
			// String temp = NewMusicRefDAO.getInstance().verifyNewMusic(list);
			
			// String[] musicId = new String[list.size()];
			//
			// for (int i = 0; i < list.size(); i++)
			// {
			// musicId[i] = ( String ) list.get(i);
			// }
			//
			// // 调用BookRefDAO进行新增
			// NewMusicRefDAO.getInstance().addNewMusicRefs(categoryId,
			// musicId);
			//
			// ret.append("成功导入" + list.size() + "条记录.");
			//
			// if (!"".equals(temp))
			// {
			// ret.append("导入不成功id为").append(temp);
			// }
			
			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("批量导入列表时发生数据库异常");
		}
	}
	
	/**
	 * 用于查询新音乐详情
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public NewMusicRefVO queryNewMusicInfo(String musicId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewMusicInfo( ) is start...");
		}
		NewMusicRefVO vo = null;
		try
		{
			// 调用NewMusicRefDAO进行查询
			vo = NewMusicRefDAO.getInstance().queryNewMusicInfo(musicId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询新音乐列表时发生数据库异常！");
		}
		return vo;
	}
	
	/**
	 * 用于查询新音乐详情
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public List queryNewMusicKeyResource(String musicId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("NewMusicRefBO.queryNewMusicKeyResource( ) is start...");
		}
		List list = null;
		try
		{
			// 调用NewMusicRefDAO进行查询
			list = NewMusicRefDAO.getInstance().queryNewMusicKeyResource(
					musicId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询新音乐扩展字段时发生数据库异常！");
		}
		return list;
	}
	
	/**
	 * 
	 * @desc 保存
	 * @author dongke Aug 9, 2011
	 * @param keyBaseList
	 * @throws BOException
	 */
	public void saveKeyResource(List keyBaseList) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			try
			{
				if (vo != null && vo.getValue() != null
						&& !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(
							vo.getKeyid(), vo.getValue(), vo.getTid());
				}
			}
			catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
	
	/**
	 * 用于导出基地音乐列表数据
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
			list = NewMusicRefDAO.getInstance().queryListByExport(categoryId);
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
				
				NewMusicRefVO vo = (NewMusicRefVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getMusicId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getMusicName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSinger());
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
		
		label = new Label(col++, row, "歌曲ID");
		ws.addCell(label);
		label = new Label(col++, row, "歌曲名称");
		ws.addCell(label);
		label = new Label(col++, row, "歌手名称");
		ws.addCell(label);
	}
}
