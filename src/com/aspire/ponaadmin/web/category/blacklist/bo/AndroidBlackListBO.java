package com.aspire.ponaadmin.web.category.blacklist.bo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.aspire.ponaadmin.web.category.blacklist.dao.AndroidBlackListDAO;
import com.aspire.ponaadmin.web.category.blacklist.vo.AndroidBlackListVO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * <p>
 * 榜单管理BO
 * </p>
 * <p>
 */
public class AndroidBlackListBO {

	 /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(AndroidBlackListBO.class);

    private static AndroidBlackListBO instance = new AndroidBlackListBO();
    
    private AndroidBlackListBO(){
    	
    }
    
    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static AndroidBlackListBO getInstance()
    {
        return instance;
    }
    
    /**
     * 用于查询榜单元数据列表
     * 
     * @param page
     * @param tagName
     * @throws BOException
     */
    public void queryTagList(PageResult page, String tagName, String tagID)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TagBlackListBO.queryTagList( ) is start...");
        }

        try
        {
            // 调用TagManagerDAO进行查询
        	AndroidBlackListDAO.getInstance().queryTagList(page, tagName,tagID);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询榜单元数据列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于批量导入榜单元数据列表
     * 
     * @param dataFile
     * @throws BOException
     */
	public String importTagData(FormFile dataFile) throws BOException {
		
		if (logger.isDebugEnabled())
		{
			logger.debug("TagBlackListBO.importTagData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		int errorN = 0;
		// 解析EXECL文件，获取终端软件版本列表
		List list = new ExcelHandle().paraseDataFile(dataFile, null);
		
		try
		{
			
			Map<String,String> blackListMap = AndroidBlackListDAO.getInstance().getTagIDMap();
			
			StringBuffer temp = new StringBuffer();
			int count = 0;
			int delCount =0;
			for (int i = 0; i < list.size(); i++)
			{
				try
				{
					Map m = (Map) list.get(i);
					String contentId = (String) m.get(0);
					String name = (String) m.get(1);
					String opType = (String) m.get(2);
					
					if(null == contentId || "".equals(contentId)
							|| null == name || "".equals(name)
							|| null == opType || "".equals(opType)
							||!("del".equals(opType.trim())||"add".equals(opType.trim())))
						throw new BOException("数据不合法");
					
					
					
					if("add".equals(opType.trim()))
					{
						if(blackListMap.containsKey(contentId))
						{
							AndroidBlackListDAO.getInstance().updateTag(contentId);
						}
						else
						{
							AndroidBlackListDAO.getInstance().addTag(contentId);
							blackListMap.put(contentId, name);
						}
						count++;
					}
					else
					{
						AndroidBlackListDAO.getInstance().delTag(contentId);
						blackListMap.remove(contentId);
						delCount++;
					}
					
					
				}
				catch (Exception e)
				{
					logger.error(e);
					errorN++;
					if (errorN % 10 == 0)
					{
						temp.append("<br>").append(i+1);
					}
					else
					{
						temp.append(",").append(i+1);
					}
					e.printStackTrace();
				}
			}
			
			ret.append("成功导入" + count + "条记录.<br>");
			ret.append("成功删除" + delCount + "条榜单记录.<br>");
			if (temp.length() != 0)
			{
				ret.append("导入不成功" + errorN +"条记录.<br>");
				ret.append("导入不成功行数为:").append(temp);
			}
			if (logger.isDebugEnabled())
			{
				logger.debug("导入黑名单榜单元数据列表完成，结果为："+ret.toString());
			}
			return ret.toString();
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new BOException("批量新增黑名单榜单时发生数据库异常");
		}
	}
	
	public void exportTagData(WritableWorkbook wwb) throws BOException{
		
		List list = null;
		try
		{
			list = AndroidBlackListDAO.getInstance().queryAllTagListByExport();
		}
		catch (DAOException e)
		{
			logger.error("于导出黑名单榜单元数据时发生数据库异常：", e);
		}
		
		generateQueryDataExcel(list, wwb);
	}
	
    
	
	/**
	 * 生成榜单元数据的excel
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
				
				AndroidBlackListVO vo = (AndroidBlackListVO) list.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getContentID());
				ws.addCell(label);
				label = new Label(col++, row, vo.getName());
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
	
	
	
	/**
	 * 设置榜单元数据excel的第一列标题
	 * 
	 * @param ws
	 */
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "内容ID");
		ws.addCell(label);
		label = new Label(col++, row, "内容名称");
		ws.addCell(label);
	}


}
