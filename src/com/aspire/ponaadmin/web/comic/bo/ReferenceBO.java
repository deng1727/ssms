package com.aspire.ponaadmin.web.comic.bo;

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
import com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.comic.dao.ReferenceDAO;
import com.aspire.ponaadmin.web.comic.vo.ComicChapterVO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.repository.web.ExcelRowConvert;

public class ReferenceBO
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReferenceBO.class);

    private static ReferenceBO instance = new ReferenceBO();

    private ReferenceBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ReferenceBO getInstance()
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
    public void queryReferenceList(PageResult page, ReferenceVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryReferenceList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�������ݿ��쳣��");
        }
    }

    public void removeReference(String categoryId, String[] id)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance()
                          .removeReference(categoryId, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ػ����б�ʱ�������ݿ��쳣��");
        }
    }

    public String isHasReference(String categoryId, String contentId)
                    throws BOException
    {
        try
        {
            String[] contentIdArray = contentId.split(";");
            return ReferenceDAO.getInstance()
                          .isHasReference(categoryId, contentIdArray);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�鿴ָ���������Ƿ����ָ������IDʱ�������ݿ��쳣��");
        }
    }
    

    public void addReference(String categoryId, String contentId)
                    throws BOException
    {
       try
        {
            String[] contentIdArray = contentId.split(";");
            ReferenceDAO.getInstance()
                          .addReference(categoryId, contentIdArray);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ػ����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯ�������б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryList(PageResult page, ReferenceVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯ�½��б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryChapterList(PageResult page, ComicChapterVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryChapterList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�����½��б�ʱ�������ݿ��쳣��");
        }
    }
    /**
     * �������������ֻ�����������Ʒ����ֵ
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setSort(String categoryId, String setSortId)
                    throws BOException
    {
        try
        {
            String[] sort = setSortId.split(";");
            // ����NewMusicRefDAO���в�ѯ
            ReferenceDAO.getInstance().setSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������ֻ�����������Ʒ����ֵʱ�������ݿ��쳣��");
        }
    }
    
    public String importContentADD(FormFile dataFile, String categoryId) throws BOException
    {
        StringBuffer ret = new StringBuffer();
        int errorN = 0;
        // ����EXECL�ļ�����ȡ�ն�����汾�б�
        List list = new ExcelHandle().paraseDataFile(dataFile,null);
        StringBuffer temp = new StringBuffer();
        int count = 0;
        for(int i =0;i<list.size();i++){
        	Map m = (Map)list.get(i);
        	String sortId = (String)m.get(0);
        	String contentId = (String)m.get(1);
        	try {
				ReferenceDAO.getInstance().addReference(categoryId, contentId.trim(), (-1*Integer.parseInt(sortId))+"");
				count++;
			} catch (Exception e) {
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
        TransactionDB tdb = null;
        try {
           tdb = TransactionDB.getInstance();
           ReferenceDAO.getInstance().setCategoryGoodsApproval(tdb, categoryId);
           tdb.commit();
        }
        catch (DAOException e)
        {
          logger.error("���ָ����������������ʱ�����쳣:", e);
            // ִ�лع�
            tdb.rollback();
            throw new BOException("���ָ����������������ʱ�����쳣:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
        ret.append("�ɹ�����" + count + "����¼.");
        if (temp.length()!=0)
      {
          ret.append("���벻�ɹ�idΪ").append(temp);
      }
        return ret.toString();
        }
    
    
    public String importContentALL(FormFile dataFile, String categoryId) throws BOException
    {
        StringBuffer ret = new StringBuffer();
        int errorN = 0;
        try
        {
            // �������ԭ������������
            ReferenceDAO.getInstance().delReference(categoryId);

//            // ����EXECL�ļ�����ȡ�ն�����汾�б�
//            List list = BookRefBO.getInstance().paraseDataFile(dataFile);
         // ����EXECL�ļ�����ȡ�ն�����汾�б�
            List list = new ExcelHandle().paraseDataFile(dataFile,null);
            
            StringBuffer temp = new StringBuffer();
            int count = 0;
            for(int i =0;i<list.size();i++){
            	Map m = (Map)list.get(i);
            	String sortId = (String)m.get(0);
            	String contentId = (String)m.get(1);
            	try {
    				ReferenceDAO.getInstance().addReference(categoryId, contentId.trim(), (-1*Integer.parseInt(sortId))+"");
    				count++;
    			} catch (Exception e) {
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
            ret.append("�ɹ�����" + count + "����¼.");
          if (temp.length()!=0)
          {
              ret.append("���벻�ɹ�idΪ").append(temp);
          }
            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯ����������
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public NewMusicRefVO queryNewMusicInfo(String musicId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicRefBO.queryNewMusicInfo( ) is start...");
        }
        NewMusicRefVO vo = null;
        try
        {
            // ����NewMusicRefDAO���в�ѯ
        	vo =  NewMusicRefDAO.getInstance().queryNewMusicInfo(musicId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�������б�ʱ�������ݿ��쳣��");
        }
        return vo;
    }
    /**
     * ���ڲ�ѯ����������
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public List queryNewMusicKeyResource(String musicId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicRefBO.queryNewMusicKeyResource( ) is start...");
        }
        List list = null;
        try
        {
            // ����NewMusicRefDAO���в�ѯ
        	list =  NewMusicRefDAO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��������չ�ֶ�ʱ�������ݿ��쳣��");
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
	 * ���ڵ���������Ʒ����
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportComicReferenceData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<ReferenceVO> list = null;
		
		try
		{
			if (isAll)
			{
		    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");

		        ReferenceVO vo = new ReferenceVO();
		        vo.setCategoryId(categoryId);
		        vo.setContentId("");
		        vo.setContentName("");
			
				list = ReferenceDAO.getInstance().queryComicReferenceListByExport(vo);
			}
			else
			{
		        
		        // �������л�ȡ��������
		    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
		    	String contentId = request.getParameter("contentId")==null?"":request.getParameter("contentId");
		    	String contentName = request.getParameter("contentName")==null?"":request.getParameter("contentName");


		        ReferenceVO vo = new ReferenceVO();
		        vo.setCategoryId(categoryId);
		        vo.setContentId(contentId);
		        vo.setContentName(contentName);
		        
		        
				list = ReferenceDAO.getInstance().queryComicReferenceListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("�ڵ���������Ʒ��Ϣ����ʱ�������ݿ��쳣��", e);
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
	private void generateQueryDataExcel(List<ReferenceVO> list,
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
				
				ReferenceVO vo = (ReferenceVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getContentId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getContentName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getFlowTime());
				ws.addCell(label);
			    label = new Label(col++, row, vo.getType());
				ws.addCell(label);	
				if("1".equals(vo.getPortal())){
				label = new Label(col++, row, "�ͻ���");
				ws.addCell(label);
				}else if("2".equals(vo.getPortal())){
					label = new Label(col++, row, "WAP�Ż�");
					ws.addCell(label);
			   }else if("3".equals(vo.getPortal())){
					label = new Label(col++, row, "����");
					ws.addCell(label);
				}else{
					label = new Label(col++, row, "δ֪");
					ws.addCell(label);
				}
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
		
		label = new Label(col++, row, "����id");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "���ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "����");
		ws.addCell(label);
		label = new Label(col++, row, "�Ż�");
		ws.addCell(label);
		label = new Label(col++, row, "�������");
		ws.addCell(label);
	}
	

	/**
	 * �༭������Ʒ����
	 * 
	 * @param categoryId �������ܱ���
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		ReferenceDAO.getInstance().approvalCategoryGoods(categoryId);
	}

}
