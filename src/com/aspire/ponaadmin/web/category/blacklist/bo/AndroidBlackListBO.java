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
 * �񵥹���BO
 * </p>
 * <p>
 */
public class AndroidBlackListBO {

	 /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(AndroidBlackListBO.class);

    private static AndroidBlackListBO instance = new AndroidBlackListBO();
    
    private AndroidBlackListBO(){
    	
    }
    
    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static AndroidBlackListBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯ��Ԫ�����б�
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
            // ����TagManagerDAO���в�ѯ
        	AndroidBlackListDAO.getInstance().queryTagList(page, tagName,tagID);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ԫ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �������������Ԫ�����б�
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
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
						throw new BOException("���ݲ��Ϸ�");
					
					
					
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
			
			ret.append("�ɹ�����" + count + "����¼.<br>");
			ret.append("�ɹ�ɾ��" + delCount + "���񵥼�¼.<br>");
			if (temp.length() != 0)
			{
				ret.append("���벻�ɹ�" + errorN +"����¼.<br>");
				ret.append("���벻�ɹ�����Ϊ:").append(temp);
			}
			if (logger.isDebugEnabled())
			{
				logger.debug("�����������Ԫ�����б���ɣ����Ϊ��"+ret.toString());
			}
			return ret.toString();
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new BOException("����������������ʱ�������ݿ��쳣");
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
			logger.error("�ڵ�����������Ԫ����ʱ�������ݿ��쳣��", e);
		}
		
		generateQueryDataExcel(list, wwb);
	}
	
    
	
	/**
	 * ���ɰ�Ԫ���ݵ�excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryDataExcel(List list, WritableWorkbook wwb)
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
	 * ���ð�Ԫ����excel�ĵ�һ�б���
	 * 
	 * @param ws
	 */
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "����ID");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
	}


}
