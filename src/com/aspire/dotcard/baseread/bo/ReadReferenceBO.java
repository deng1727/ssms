/*
 * �ļ�����ReadReferenceBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReadReferenceBO.class);

    private static ReadReferenceBO instance = new ReadReferenceBO();

    private ReadReferenceBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ReadReferenceBO getInstance()
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
    public void queryReadRefList(PageResult page, ReadReferenceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadRefList( ) is start...");
        }

        try
        {
            // ����ReadReferenceDAO���в�ѯ
            ReadReferenceDAO.getInstance().queryReadRefList(page, vo);
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
    public void removeReadRefs(String categoryId, String[] bookId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.removeReadRefs( ) is start...");
        }

        try
        {
            // ����ReadReferenceDAO���в�ѯ
            ReadReferenceDAO.getInstance()
                          .removeReadRefs(categoryId, bookId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����Ķ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���������Ķ�������ͼ����Ʒ����ֵ
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
            // ����ReadReferenceDAO���в�ѯ
            ReadReferenceDAO.getInstance().setReadSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����Ķ�������ͼ����Ʒ����ֵʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ鿴ָ���������Ƿ����ָ��ͼ��
     * 
     * @param categoryId ����id
     * @param addBookId ��ͼ��id��
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
            // ����ReadReferenceDAO���в�ѯ
            return ReadReferenceDAO.getInstance()
                          .isHasReadRefs(categoryId, addBookIds);
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
            // ����ReadReferenceDAO��������
            ReadReferenceDAO.getInstance()
                          .addReadRefs(categoryVO, addBookIds);
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
    public void queryReadList(PageResult page, ReadReferenceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.queryReadList( ) is start...");
        }

        try
        {
            // ����ReadReferenceDAO���в�ѯ
            ReadReferenceDAO.getInstance().queryReadList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯͼ���б�ʱ�������ݿ��쳣��");
        }
    }
    
    public String importContentADD(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadReferenceBO.importContentADD( ) is start...");
        }
        StringBuffer ret = new StringBuffer();
        // ����EXECL�ļ�����ȡ�ն�����汾�б�
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
        ret.append("�ɹ�����" + count + "����¼.");
      if (temp.length()!=0)
      {
          ret.append("���벻�ɹ�idΪ").append(temp);
      }
        return ret.toString();
    }
    
    /**
     * �ļ���������ͼ����Ʒ�ϼ�
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
            // �������ԭ������ͼ��
            ReadReferenceDAO.getInstance().delBookRef(categoryId);
            
//            // ����EXECL�ļ�����ȡ�ն�����汾�б�
//            List list = this.paraseDataFile(dataFile);
            
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
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
            ret.append("�ɹ�����" + count + "����¼.");
          if (temp.length()!=0)
          {
              ret.append("���벻�ɹ�idΪ").append(temp);
          }

//            // У��ļ��Д����Ƿ���ͼ����д���
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
//            // ����ReadReferenceDAO��������
//            ReadReferenceDAO.getInstance()
//                          .addReadRefs(categoryId, ids);
//
//            ret.append("�ɹ�����" + list.size() + "����¼.");
//
//            if (!"".equals(temp))
//            {
//                ret.append("���벻�ɹ�idΪ").append(temp);
//            }

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
            // ����ReadReferenceDAO���в�ѯ
        	vo =  ReadReferenceDAO.getInstance().queryReadInfo(bookId);
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
            // ����ReadReferenceDAO���в�ѯ
        	list =  ReadReferenceDAO.getInstance().queryReadKeyResource(bookId);
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
	  /**
	 * ���ڵ�����ͼ������
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
			logger.error("�ڵ�����Ƶ��Ʒ��Ϣ����ʱ�������ݿ��쳣��", e);
		}
		
		generateQueryDataExcel(list, wwb);
	}
	
	/**
	 * ����Ӧ�ø��°汾��excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryDataExcel(List<ReadReferenceVO> list,
			WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = wwb.createSheet(sheetName, 0); // ����sheet
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
		
		label = new Label(col++, row, "ͼ����");
		ws.addCell(label);
		label = new Label(col++, row, "ͼ������");
		ws.addCell(label);
		label = new Label(col++, row, "ͼ������");
		ws.addCell(label);
		label = new Label(col++, row, "ͼ�����");
		ws.addCell(label);
		label = new Label(col++, row, "����");
		ws.addCell(label);
		label = new Label(col++, row, "������ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "�������");
		ws.addCell(label);
	}

}
