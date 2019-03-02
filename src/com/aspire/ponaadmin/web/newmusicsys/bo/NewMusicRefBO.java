/*
 * �ļ�����NewMusicRefBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(NewMusicRefBO.class);
	
	private static NewMusicRefBO instance = new NewMusicRefBO();
	
	private NewMusicRefBO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static NewMusicRefBO getInstance()
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
	public void queryNewMusicRefList(PageResult page, NewMusicRefVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewMusicRefList( ) is start...");
		}
		
		try
		{
			// ����NewMusicRefDAO���в�ѯ
			NewMusicRefDAO.getInstance().queryNewMusicRefList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������ֻ����б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * �����Ƴ�ָ��������ָ����������
	 * 
	 * @param categoryId
	 *            ����id
	 * @param musicId
	 *            ������id��
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
			// ����NewMusicRefDAO���в�ѯ
			NewMusicRefDAO.getInstance()
					.removeNewMusicRefs(categoryId, musicId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������ֻ����б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ���ڲ鿴ָ���������Ƿ����ָ������
	 * 
	 * @param categoryId
	 *            ����id
	 * @param musicId
	 *            ������id��
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
			// ����NewMusicRefDAO���в�ѯ
			return NewMusicRefDAO.getInstance().isHasMusicRefs(categoryId,
					addMusicIds);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("�鿴ָ���������Ƿ����ָ������ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * �������ָ������������������
	 * 
	 * @param categoryId
	 *            ����id
	 * @param musicId
	 *            ������id��
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
			// ����NewMusicRefDAO��������
			NewMusicRefDAO.getInstance().addNewMusicRefs(categoryId,
					addMusicIds);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������ֻ����б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ���ڲ�ѯ�������б�
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
			// ����NewMusicRefDAO���в�ѯ
			NewMusicRefDAO.getInstance().queryNewMusicList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ�������б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * �������������ֻ�����������Ʒ����ֵ
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
			// ����NewMusicRefDAO���в�ѯ
			NewMusicRefDAO.getInstance().setNewMusicSort(categoryId, sort);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������ֻ�����������Ʒ����ֵʱ�������ݿ��쳣��");
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
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
		ret.append("�ɹ�����" + count + "����¼.");
		if (temp.length() != 0)
		{
			ret.append("���벻�ɹ�idΪ").append(temp);
		}
		return ret.toString();
	}
	
	/**
	 * �ļ�����������������Ʒ�ϼ�
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
			// �������ԭ������������
			NewMusicRefDAO.getInstance().delNewMusicRef(categoryId);
			
			// // ����EXECL�ļ�����ȡ�ն�����汾�б�
			// List list = BookRefBO.getInstance().paraseDataFile(dataFile);
			
			// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
			ret.append("�ɹ�����" + count + "����¼.");
			if (temp.length() != 0)
			{
				ret.append("���벻�ɹ�idΪ").append(temp);
			}
			
			// // У��ļ��Д����Ƿ��������ֱ��д���
			// String temp = NewMusicRefDAO.getInstance().verifyNewMusic(list);
			
			// String[] musicId = new String[list.size()];
			//
			// for (int i = 0; i < list.size(); i++)
			// {
			// musicId[i] = ( String ) list.get(i);
			// }
			//
			// // ����BookRefDAO��������
			// NewMusicRefDAO.getInstance().addNewMusicRefs(categoryId,
			// musicId);
			//
			// ret.append("�ɹ�����" + list.size() + "����¼.");
			//
			// if (!"".equals(temp))
			// {
			// ret.append("���벻�ɹ�idΪ").append(temp);
			// }
			
			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������б�ʱ�������ݿ��쳣");
		}
	}
	
	/**
	 * ���ڲ�ѯ����������
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
			// ����NewMusicRefDAO���в�ѯ
			vo = NewMusicRefDAO.getInstance().queryNewMusicInfo(musicId);
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
			// ����NewMusicRefDAO���в�ѯ
			list = NewMusicRefDAO.getInstance().queryNewMusicKeyResource(
					musicId);
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
	 * @desc ����
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
				throw new BOException("������չֵʧ��", e);
			}
		}
	}
	
	/**
	 * ���ڵ������������б�����
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request, WritableWorkbook wwb)
			throws BOException
	{
		// �������л�ȡ��������
        String categoryId = request.getParameter("categoryId").trim();
        List list = null;
        
		try
		{
			list = NewMusicRefDAO.getInstance().queryListByExport(categoryId);
		}
		catch (DAOException e)
		{
			logger.error("�ڵ��������Ķ���������ʱ�������ݿ��쳣��", e);
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
	private void generateQueryDataExcel(List list, WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = null; // ����sheet
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
		
		label = new Label(col++, row, "����ID");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
	}
}
