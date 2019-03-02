/*
 * 文件名：ReadReferenceBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.baseread.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
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
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.dotcard.baseread.dao.ReadReferenceDAO;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.baseread.vo.ReadReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ReadReferenceBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReadReferenceBO.class);

    private static ReadReferenceBO instance = new ReadReferenceBO();

    private ReadReferenceBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReadReferenceBO getInstance()
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
    public void queryReadRefList(PageResult page, ReadReferenceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadRefList( ) is start...");
        }

        try
        {
            // 调用ReadReferenceDAO进行查询
            ReadReferenceDAO.getInstance().queryReadRefList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于移除指定货架下指定的图书
     * 
     * @param categoryId 货架id
     * @param bookId 图书id列
     * @throws BOException
     */
    public void removeReadRefs(String categoryId, String[] bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.removeReadRefs( ) is start...");
        }

        try
        {
            // 调用ReadReferenceDAO进行查询
            ReadReferenceDAO.getInstance()
                          .removeReadRefs(categoryId, bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回阅读货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于设置阅读货架下图书商品排序值
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setReadSort(String categoryId, String setSortId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.setReadSort( ) is start...");
        }

        try
        {
            String[] sort = setSortId.split(";");
            // 调用ReadReferenceDAO进行查询
            ReadReferenceDAO.getInstance().setReadSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置阅读货架下图书商品排序值时发生数据库异常！");
        }
    }
    
    /**
     * 用于查看指定货架中是否存在指定图书
     * 
     * @param categoryId 货架id
     * @param addBookId 新图书id列
     * @throws BOException
     */
    public String isHasReadRefs(String categoryId, String addBookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.isHasReadRefs( ) is start...");
        }

        try
        {
            String[] addBookIds = addBookId.split(";");
            // 调用ReadReferenceDAO进行查询
            return ReadReferenceDAO.getInstance()
                          .isHasReadRefs(categoryId, addBookIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查看指定货架中是否存在指定图书时发生数据库异常！");
        }
    }
    
    /**
     * 用于添加指定的图书至货架中
     * 
     * @param categoryId 货架id
     * @param addBookId 图书id列
     * @throws BOException
     */
    public void addReadRefs(String categoryId, String addBookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.addReadRefs( ) is start...");
        }

        try
        {
            String[] addBookIds = addBookId.split(";");
            ReadCategoryVO categoryVO = ReadCategoryBO.getInstance()
            .queryReadCategoryVO(categoryId);
            // 调用ReadReferenceDAO进行新增
            ReadReferenceDAO.getInstance()
                          .addReadRefs(categoryVO, addBookIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询图书列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryReadList(PageResult page, ReadReferenceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadList( ) is start...");
        }

        try
        {
            // 调用ReadReferenceDAO进行查询
            ReadReferenceDAO.getInstance().queryReadList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询图书列表时发生数据库异常！");
        }
    }
    
    public String importContentADD(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.importContentADD( ) is start...");
        }
        StringBuffer ret = new StringBuffer();
        // 解析EXECL文件，获取终端软件版本列表
        List list = new ExcelHandle().paraseDataFile(dataFile,null);
        try {
        	ReadReferenceDAO.getInstance().verifyBookExists(list);
		} catch (Exception e) {
			logger.error(e);
		}
        
        int errorN = 0;
        ReadCategoryVO categoryVO = ReadCategoryBO.getInstance().queryReadCategoryVO(categoryId);
        StringBuffer temp = new StringBuffer();
        int count = 0;
        for(int i =0;i<list.size();i++){
        	Map m = (Map)list.get(i);
        	String sortId = (String)m.get(0);
        	String contentId = (String)m.get(1);
        	try {
        		ReadReferenceDAO.getInstance().addReference(categoryVO.getId(),categoryVO.getCategoryId(), contentId.trim(), (-1*Integer.parseInt(sortId))+"");
				count++;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				errorN++;
				if(errorN % 10 == 0)
				{
					temp.append("<br>").append(contentId);
				}
				else
				{
					temp.append(",").append(contentId);
				}
			}
        	
        	
        }
        ReadReferenceDAO.getInstance().approvalCategoryGoods(categoryId);
        ret.append("成功导入" + count + "条记录.");
      if (temp.length()!=0)
      {
          ret.append("导入不成功id为").append(temp);
      }
        return ret.toString();
    }
    
    /**
     * 文件批量导入图书商品上架
     * 
     * @param dataFile
     * @param categoryId
     * @throws BOException
     */
    public String importContentALL(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.importContentALL( ) is start...");
        }

        StringBuffer ret = new StringBuffer();
        int errorN = 0;

        try
        {
            // 用于清空原货架下图书
            ReadReferenceDAO.getInstance().delBookRef(categoryId);
            
//            // 解析EXECL文件，获取终端软件版本列表
//            List list = this.paraseDataFile(dataFile);
            
            // 解析EXECL文件，获取终端软件版本列表
            List list = new ExcelHandle().paraseDataFile(dataFile,null);
            ReadReferenceDAO.getInstance().verifyBookExists(list);
            ReadCategoryVO categoryVO = ReadCategoryBO.getInstance().queryReadCategoryVO(categoryId);
            StringBuffer temp = new StringBuffer();
            int count = 0;
            for(int i =0;i<list.size();i++){
            	Map m = (Map)list.get(i);
            	String sortId = (String)m.get(0);
            	String contentId = (String)m.get(1);
            	try {
            		ReadReferenceDAO.getInstance().addReference(categoryVO.getId(),categoryVO.getCategoryId(), contentId.trim(), (-1*Integer.parseInt(sortId))+"");
    				count++;
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				//e.printStackTrace();
    				logger.error(e.getMessage(),e);
    				errorN++;
    				if(errorN % 10 == 0)
    				{
    					temp.append("<br>").append(contentId);
    				}
    				else
    				{
    					temp.append(",").append(contentId);
    				}
    			}
            	
            	
            }
            ReadReferenceDAO.getInstance().approvalCategoryGoods(categoryId);
            ret.append("成功导入" + count + "条记录.");
          if (temp.length()!=0)
          {
              ret.append("导入不成功id为").append(temp);
          }

//            // 校驗文件中數據是否在图书表中存在
//            String temp = ReadReferenceDAO.getInstance()
//                                            .verifyBook(list);
//
//            String[] ids = new String[list.size()];
//            
//            for (int i = 0; i < list.size(); i++)
//            {
//                ids[i] = (String)list.get(i);
//            }
//            
//            // 调用ReadReferenceDAO进行新增
//            ReadReferenceDAO.getInstance()
//                          .addReadRefs(categoryId, ids);
//
//            ret.append("成功导入" + list.size() + "条记录.");
//
//            if (!"".equals(temp))
//            {
//                ret.append("导入不成功id为").append(temp);
//            }

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("导入紧急上线的内容列表时发生数据库异常！");
        }
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
        logger.info("ReadReferenceBO.paraseDataFile() is start!");
        List list = new ArrayList();
        Workbook book = null;

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

                // int columns = sheets[i].getColumns();
                int columns = 0;

                for (int j = 0; j < rows; j++)
                {
                    String value = sheets[i].getCell(columns, j)
                                            .getContents()
                                            .trim();
                    // 不為空時
                    if (!"".equals(value))
                    {
                        // 如果集合中存在此数据
                        if (list.contains(value))
                        {
                            // 删除原来存在的数据
                            list.remove(value);
                        }
                        
                        list.add(value);
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
     * 用于查询图书详情
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public ReadReferenceVO queryReadInfo(String bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadInfo( ) is start...");
        }
        ReadReferenceVO vo = null;
        try
        {
            // 调用ReadReferenceDAO进行查询
        	vo =  ReadReferenceDAO.getInstance().queryReadInfo(bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询图书详情时发生数据库异常！");
        }
        return vo;
    }
    /**
     * 用于查询图书扩展字段详情
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public List queryReadKeyResource(String bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadKeyResource( ) is start...");
        }
        List list = null;
        try
        {
            // 调用ReadReferenceDAO进行查询
        	list =  ReadReferenceDAO.getInstance().queryReadKeyResource(bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询图书扩展字段时发生数据库异常！");
        }
        return list;
    }
    
    
    /**
     * 
     *@desc 保存
     *@author dongke
     *Aug 9, 2011
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
				if (vo != null && vo.getValue() != null && !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(vo.getKeyid(),
							vo.getValue(), vo.getTid());
				}
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
	  /**
	 * 用于导出新图书数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportNewBookReferenceData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<ReadReferenceVO> list = null;
		
		try
		{
			if (isAll)
			{
		        String categoryId = request.getParameter("categoryId") == null ? "" :request.getParameter("categoryId").trim();

				 ReadReferenceVO vo = new ReadReferenceVO();
				 vo.setCId(categoryId);
			        vo.setBookId("");
			        vo.setBookName("");
			        vo.setAuthorName("");
			        vo.setTypeName("");
			        vo.setChargeType("");
			
				list = ReadReferenceDAO.getInstance().queryNewBookReferenceListByExport(vo);
			}
			else
			{

		        String categoryId = request.getParameter("categoryId") == null ? "" :request.getParameter("categoryId").trim();
		        String bookId = request.getParameter("bookId") == null ? "" :request.getParameter("bookId").trim();
		        String bookName = request.getParameter("bookName") == null ? "" :request.getParameter("bookName").trim();
		        String authorName = request.getParameter("authorName") == null ? "" :request.getParameter("authorName").trim();
		        String typeName = request.getParameter("typeName") == null ? "" :request.getParameter("typeName").trim();
		        String chargeType = request.getParameter("chargeType") == null ? "" :request.getParameter("chargeType").trim();


		        ReadReferenceVO vo = new ReadReferenceVO();
		        vo.setCId(categoryId);
		        vo.setBookId(bookId);
		        vo.setBookName(bookName);
		        vo.setAuthorName(authorName);
		        vo.setTypeName(typeName);
		        vo.setChargeType(chargeType);
		        
		        
				list = ReadReferenceDAO.getInstance().queryNewBookReferenceListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出视频商品信息数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List<ReadReferenceVO> list,
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
				
				ReadReferenceVO vo = (ReadReferenceVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getBookId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getBookName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getAuthorName());
				ws.addCell(label);
			    label = new Label(col++, row, vo.getTypeName());
			    ws.addCell(label);		
                label = new Label(col++, row, vo.getRankValue());
				ws.addCell(label);			
				label = new Label(col++, row, vo.getLastUpTime());
				ws.addCell(label);
                label = new Label(col++, row, vo.getSortNumber());
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
		
		label = new Label(col++, row, "图书编号");
		ws.addCell(label);
		label = new Label(col++, row, "图书名称");
		ws.addCell(label);
		label = new Label(col++, row, "图书作者");
		ws.addCell(label);
		label = new Label(col++, row, "图书分类");
		ws.addCell(label);
		label = new Label(col++, row, "评分");
		ws.addCell(label);
		label = new Label(col++, row, "最后更新时间");
		ws.addCell(label);
		label = new Label(col++, row, "排序序号");
		ws.addCell(label);
	}

}
