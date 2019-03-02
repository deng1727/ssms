package com.aspire.dotcard.basevideosync.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.dao.NewVideoRefDAO;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;

public class NewVideoRefBO {
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(NewVideoRefBO.class);

	private static NewVideoRefBO bo = new NewVideoRefBO();

	private NewVideoRefBO() {
	}

	public static NewVideoRefBO getInstance() {
		return bo;
	}
	/**
	 * ���ڲ�ѯ����Ƶ����
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public ProgramVO queryNewVideoInfo(String programId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicRefBO.queryNewVideoInfo( ) is start...");
		}
		ProgramVO vo = null;
		try
		{
			// ����NewMusicRefDAO���в�ѯ
			vo = NewVideoRefDAO.getInstance().queryNewVideoInfo(programId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ����Ƶ�б�ʱ�������ݿ��쳣��");
		}
		return vo;
	}
	
	/**
	 * ���ڵ���������Ƶ�б�����
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
			list = NewVideoRefDAO.getInstance().queryListByExport(categoryId);
		}
		catch (DAOException e)
		{
			logger.error("�ڵ�����������Ƶ����ʱ�������ݿ��쳣��", e);
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
				
				VideoReferenceVO vo = (VideoReferenceVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getProgramId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getProgramName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getTagName());
				ws.addCell(label);
				String feetype= vo.getFeetype();
				if("1".equals(feetype)){
					feetype = "���";
				}else if("2".equals(feetype)){
					feetype = "�շ�";
				}else if("3".equals(feetype)){
					feetype = "��֧�ְ���";
				}else{
					feetype = "";
				}
				label = new Label(col++, row, feetype);
				ws.addCell(label);
				label = new Label(col++, row, vo.getBroadcast());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCountriy());
				ws.addCell(label);
				label = new Label(col++, row, vo.getContentType());
				ws.addCell(label);
				label = new Label(col++, row, vo.getLastUpTime());
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
		
		label = new Label(col++, row, "��ĿID");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "һ������");
		ws.addCell(label);
		label = new Label(col++, row, "�ʷ�");
		ws.addCell(label);
		label = new Label(col++, row, "�������");
		ws.addCell(label);
		label = new Label(col++, row, "���Ҽ�����");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "����޸�ʱ��");
		ws.addCell(label);
	}
}
