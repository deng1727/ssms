/*
 * �ļ�����BookRefBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BookRefBO.class);

    private static BookRefBO instance = new BookRefBO();

    private BookRefBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BookRefBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯ��ǰ��������Ʒ�б�
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
            // ����BookRefDAO���в�ѯ
            BookRefDAO.getInstance().queryBookRefList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����Ƴ�ָ��������ָ����ͼ��
     * 
     * @param categoryId ����id
     * @param bookId ͼ��id��
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
            // ����BookRefDAO���в�ѯ
            BookRefDAO.getInstance()
                          .removeBookRefs(categoryId, bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ��������ͼ�������ͼ����Ʒ����ֵ
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
            // ����BookRefDAO���в�ѯ
            BookRefDAO.getInstance().setBookSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ�������ͼ����Ʒ����ֵʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ鿴ָ���������Ƿ����ָ��ͼ��
     * 
     * @param categoryId ����id
     * @param addBookId ��ͼ��id��
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
            // ����BookRefDAO���в�ѯ
            return BookRefDAO.getInstance()
                          .isHasBookRefs(categoryId, addBookIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�鿴ָ���������Ƿ����ָ��ͼ��ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �������ָ����ͼ����������
     * 
     * @param categoryId ����id
     * @param addBookId ͼ��id��
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
            // ����BookRefDAO��������
            BookRefDAO.getInstance()
                          .addBookRefs(categoryId, addBookIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯͼ���б�
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
            // ����BookRefDAO���в�ѯ
            BookRefDAO.getInstance().queryBookList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯͼ���б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �ļ���������ͼ����Ʒ�ϼ�
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
            // �������ԭ������ͼ��
            BookRefDAO.getInstance().delBookRef(categoryId);
            
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
            List list = this.paraseDataFile(dataFile);

            // У��ļ��Д����Ƿ���ͼ����д���
            String temp = BookRefDAO.getInstance()
                                            .verifyBook(list);

            String[] ids = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = (String)list.get(i);
            }
            
            // ����BookRefDAO��������
            BookRefDAO.getInstance()
                          .addBookRefs(categoryId, ids);

            ret.append("�ɹ�����" + list.size() + "����¼.");

            if (!"".equals(temp))
            {
                ret.append("���벻�ɹ�idΪ").append(temp);
            }

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����������ߵ������б�ʱ�������ݿ��쳣��");
        }
    }
    

    /**
     * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
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
                    // ����Օr
                    if (!"".equals(value))
                    {
                        // ��������д��ڴ�����
                        if (list.contains(value))
                        {
                            // ɾ��ԭ�����ڵ�����
                            list.remove(value);
                        }
                        
                        list.add(value);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
            throw new BOException("���������ļ������쳣", e);
        }
        finally
        {
            book.close();
        }
        return list;
    }
    /**
     * ���ڲ�ѯͼ������
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
            // ����BookRefDAO���в�ѯ
        	vo =  BookRefDAO.getInstance().queryBookInfo(bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯͼ������ʱ�������ݿ��쳣��");
        }
        return vo;
    }
    /**
     * ���ڲ�ѯͼ����չ�ֶ�����
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
            // ����BookRefDAO���в�ѯ
        	list =  BookRefDAO.getInstance().queryBookKeyResource(bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯͼ����չ�ֶ�ʱ�������ݿ��쳣��");
        }
        return list;
    }
    
    
    /**
     * 
     *@desc ����
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
				throw new BOException("������չֵʧ��", e);
			}
		}
	}
}
