package com.aspire.dotcard.baseVideo.bo;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.aspire.dotcard.baseVideo.dao.BlackDAO;
import com.aspire.dotcard.baseVideo.dao.CollectionDAO;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.CollectionResultVO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoDeleteBlackDAO;
import com.aspire.dotcard.basemusic.dao.BaseMusicPicDAO;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * ��Ƶ���ݼ�ҵ����
 */
public class CollectionBO
{
    /**
     * ��־����
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(CollectionBO.class);

    private static CollectionBO instance = new CollectionBO();

    public CollectionBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CollectionBO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��Ƶ���ݼ��б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCollectionList(PageResult page, CollectionVO vo) throws BOException
    {
        try
        {
        	CollectionDAO.getInstance().queryCollectionList(page, vo);
        }
        catch (DAOException e)
        {
        	e.printStackTrace();
            LOG.error("��Ƶ���ݼ��������ݿ��쳣!", e);
            throw new BOException("��Ƶ���ݼ��������ݿ��쳣!");
        }
    }

    @SuppressWarnings("unchecked")
    public List getImportCollectionParaseDataList(FormFile dataFile) throws BOException
    {
        List list = new ArrayList();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("��Ƶ�����ڵ㵼��,Excel�����쳣!", e);
            throw new BOException("��Ƶ�����ڵ㵼��,Excel�����쳣!");
        }
        return list;
    }


    /**
     * ��Ӻ�����
     * 
     * @param contentIdArray
     *            ����ID
     * @return ��ӽ����Ϣ
     * @throws BOException
     */
    public String addImportCollection(List arrlist) throws BOException
    {
        String result = "";
//        if (arrlist == null || (arrlist != null && arrlist.get("grogramid") == 0))
      if (arrlist.size() == 0 )

        {
            LOG.warn("��ƵListΪ��!");
            result = "����������Ƶ�����ڵ�0����¼";
            return result;
        }
        try
        {
        	result = CollectionDAO.getInstance().addImCollection(arrlist);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            LOG.error("��Ƶ�����ڵ����Ӳ����������ݿ��쳣!", e);
            throw new BOException("��Ƶ�����ڵ����Ӳ����������ݿ��쳣!");
        }
        return result;
    }
    /**
     * ��������������excel���뵽���ݿ��������ݿ��Ѿ����ڣ������
     * 
     * @param dataFile
     *            �����ļ�����
     * @return ��������Ϣ
     * @throws BOException
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public String importCollectionADD(List arrList) throws BOException
    {
        String result = null;
        try
        {
            result = addImportCollection(arrList);
        }
        catch (BOException e)
        {
            LOG.error("��Ƶ�����ڵ���������,����쳣!", e);
            throw new BOException("��Ƶ�����ڵ���������,����쳣!");
        }
        return result;
    }

	/**
	 * ���ڵ�����Ƶ���ݼ�����
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportCollectionData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<CollectionResultVO> list = null;
		
		try
		{
			if (isAll)
			{
				CollectionVO vo = new CollectionVO();
				vo.setCollectionId("");
				vo.setNodeName("");
				vo.setParentNodeId("");
			
				list = CollectionDAO.getInstance().queryCollectionListByExport(vo);
			}
			else
			{
		         String collectionId= request.getParameter("collectionId") == null ? "" : request.getParameter("collectionId");
		         String parentNodeId= request.getParameter("parentNodeId") == null ? "" : request.getParameter("parentNodeId");
		         String nodeName= request.getParameter("nodeName") == null ? "" : request.getParameter("nodeName");

		        CollectionVO vo = new CollectionVO();
		        vo.setCollectionId(collectionId);
		        vo.setNodeName(nodeName);
		        vo.setParentNodeId(parentNodeId);
				list = CollectionDAO.getInstance().queryCollectionListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			LOG.error("�ڵ�����Ƶ�����ڵ�����ʱ�������ݿ��쳣��", e);
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
	private void generateQueryDataExcel(List<CollectionResultVO> list,
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
				
				CollectionResultVO vo = (CollectionResultVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getCollectionId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getParentNodeId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeName());
				ws.addCell(label);
				if("1".equals(vo.getIsShow())){
					label = new Label(col++, row, "��");
					ws.addCell(label);		
				}else{
					label = new Label(col++, row, "��");
					ws.addCell(label);	
				}

				
				label = new Label(col++, row, vo.getReName());
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
		
		label = new Label(col++, row, "�ڵ�ID");
		ws.addCell(label);
		label = new Label(col++, row, "���ڵ�ID");
		ws.addCell(label);
		label = new Label(col++, row, "�ڵ�����");
		ws.addCell(label);
		label = new Label(col++, row, "�Ƿ���ʾ");
		ws.addCell(label);
		label = new Label(col++, row, "������");
		ws.addCell(label);
	}

    @SuppressWarnings("unchecked")
    public HashMap<String,String[]> getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        HashMap<String,String[]> map = new HashMap<String,String[]>();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("��Ƶ��Ƶ���ݼ�����,Excel�����쳣!", e);
            throw new BOException("��Ƶ���ݼ�����,Excel�����쳣!");
        }

        String[] arrList = new String[list.size()];
        String[] arrList2 = new String[list.size()];
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("��Ƶ���ݼ�����excel����ListΪ��!");
            map.put("programid", arrList);
            map.put("nodeid", arrList2);
            return map;
        }


        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            arrList2[i] = (String)m.get(1);
            
            m = null;
        }
        list = null;
        map.put("programid", arrList);
        map.put("nodeid", arrList2);

        return map;
    }
	public boolean updateCollections()
	{
		String  sql1 = "collection.CollectionDAO.updateIshowCollections.update";
		String  sql2 = "collection.CollectionDAO.updateNodebynameCollections.update";
		String  sql3 = "collection.CollectionDAO.updateCollectnameCollections.update";

		boolean isTrue = true;
		
		try
		{
			CollectionDAO.getInstance().updateCollections(sql1, null);
			CollectionDAO.getInstance().updateCollections(sql2, null);
			CollectionDAO.getInstance().updateCollections(sql3, null);

		}
		catch (BOException e)
		{
			isTrue = false;
			LOG.debug("ִ��ɾ����Ƶ���ݱ�ʱ�������󣡣���", e);
		}
		return isTrue;
	}
}
