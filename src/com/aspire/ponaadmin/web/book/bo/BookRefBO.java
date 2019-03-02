/*
 * 文件名：BookRefBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.bo;

import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.dao.BookRefDAO;
import com.aspire.ponaadmin.web.book.vo.BookRefVO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookRefBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BookRefBO.class);

    private static BookRefBO instance = new BookRefBO();

    private BookRefBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BookRefBO getInstance()
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
    public void queryBookRefList(PageResult page, BookRefVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.queryBookRefList( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            BookRefDAO.getInstance().queryBookRefList(page, vo);
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
    public void removeBookRefs(String categoryId, String[] bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.removeBookRefs( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            BookRefDAO.getInstance()
                          .removeBookRefs(categoryId, bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于设置图书货架下图书商品排序值
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setBookSort(String categoryId, String setSortId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.setBookSort( ) is start...");
        }

        try
        {
            String[] sort = setSortId.split(";");
            // 调用BookRefDAO进行查询
            BookRefDAO.getInstance().setBookSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置图书货架下图书商品排序值时发生数据库异常！");
        }
    }
    
    /**
     * 用于查看指定货架中是否存在指定图书
     * 
     * @param categoryId 货架id
     * @param addBookId 新图书id列
     * @throws BOException
     */
    public String isHasBookRefs(String categoryId, String addBookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.isHasBookRefs( ) is start...");
        }

        try
        {
            String[] addBookIds = addBookId.split(";");
            // 调用BookRefDAO进行查询
            return BookRefDAO.getInstance()
                          .isHasBookRefs(categoryId, addBookIds);
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
    public void addBookRefs(String categoryId, String addBookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.addBookRefs( ) is start...");
        }

        try
        {
            String[] addBookIds = addBookId.split(";");
            // 调用BookRefDAO进行新增
            BookRefDAO.getInstance()
                          .addBookRefs(categoryId, addBookIds);
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
    public void queryBookList(PageResult page, BookRefVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.queryBookList( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            BookRefDAO.getInstance().queryBookList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询图书列表时发生数据库异常！");
        }
    }
    
    /**
     * 文件批量导入图书商品上架
     * 
     * @param dataFile
     * @param categoryId
     * @throws BOException
     */
    public String importContentExigence(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.importContentExigence( ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // 用于清空原货架下图书
            BookRefDAO.getInstance().delBookRef(categoryId);
            
            // 解析EXECL文件，获取终端软件版本列表
            List list = this.paraseDataFile(dataFile);

            // 校驗文件中數據是否在图书表中存在
            String temp = BookRefDAO.getInstance()
                                            .verifyBook(list);

            String[] ids = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = (String)list.get(i);
            }
            
            // 调用BookRefDAO进行新增
            BookRefDAO.getInstance()
                          .addBookRefs(categoryId, ids);

            ret.append("成功导入" + list.size() + "条记录.");

            if (!"".equals(temp))
            {
                ret.append("导入不成功id为").append(temp);
            }

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
        logger.info("BookRefBO.paraseDataFile() is start!");
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
    public BookRefVO queryBookInfo(String bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.queryBookInfo( ) is start...");
        }
        BookRefVO vo = null;
        try
        {
            // 调用BookRefDAO进行查询
        	vo =  BookRefDAO.getInstance().queryBookInfo(bookId);
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
    public List queryBookKeyResource(String bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookRefBO.queryBookKeyResource( ) is start...");
        }
        List list = null;
        try
        {
            // 调用BookRefDAO进行查询
        	list =  BookRefDAO.getInstance().queryBookKeyResource(bookId);
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
}
