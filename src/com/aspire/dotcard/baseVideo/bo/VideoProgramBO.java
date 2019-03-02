package com.aspire.dotcard.baseVideo.bo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.dao.VideoProgramDAO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseread.dao.BaseAuthorDAO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author wangminlong
 */
public class VideoProgramBO
{
	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(VideoProgramBO.class);
	
	private static VideoProgramBO instance = new VideoProgramBO();
	
	private VideoProgramBO()
	{
	}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static VideoProgramBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ѯ��Ƶ��Ŀ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryVideoProgramList(PageResult page, ProgramVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoProgramBO.queryVideoProgramList( ) is start...");
		}
		
		try
		{
			VideoProgramDAO.getInstance().queryVideoProgramList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("������Ƶ��Ŀ�б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ���ڵ�����Ƶ��Ŀ����
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List list = null;
		ProgramVO vo = new ProgramVO();
		try
		{
			if (isAll)
			{
				vo.setProgramId("");
				vo.setProgramName("");
				vo.setNodeId("");
				vo.setVideoId("");
			}
			else
			{
				String programId = this.getParameter(request, "programId")
						.trim();
				String programName = this.getParameter(request, "programName")
						.trim();
				String videoId = this.getParameter(request, "videoId").trim();
				String nodeId = this.getParameter(request, "nodeId").trim();
				
				vo.setProgramId(programId);
				vo.setProgramName(programName);
				vo.setNodeId(nodeId);
				vo.setVideoId(videoId);
			}
			list = VideoProgramDAO.getInstance().queryListByExport(vo);
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
	 * @throws BOException 
	 */
	private void generateQueryDataExcel(List list, WritableWorkbook wwb) throws BOException
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
				
				ProgramVO vo = (ProgramVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getProgramId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getVideoId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getProgramName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getLastUpTime_Y() + "��"
						+ vo.getLastUpTime_M() + "��" + vo.getLastUpTime_D()
						+ "��");
				ws.addCell(label);
				label = new Label(col++, row, vo.getShowTime());
				ws.addCell(label);
			}
		}
		catch (RowsExceededException e)
		{
			logger.error(e);
			throw new BOException("�����ļ������쳣",e); 
		}
		catch (WriteException e)
		{
			logger.error(e);
			throw new BOException("�����ļ������쳣",e); 
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new BOException("�����ļ������쳣",e); 
		}
	}
	
	private void setTitle(WritableSheet ws) throws RowsExceededException,
			WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "��Ŀ���");
		ws.addCell(label);
		label = new Label(col++, row, "���ݱ��");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "����޸�ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀʱ��");
		ws.addCell(label);
	}
	
	/**
	 * �������л�ȡ���������Ϊnull�ͷ��ؿ��ַ���""
	 * 
	 * @param request
	 *            http����
	 * @param key
	 *            �����Ĺؼ���
	 * @return ����ֵ
	 */
	protected String getParameter(HttpServletRequest request, String key)
	{
		String value = request.getParameter(key);
		if (value == null)
		{
			value = "";
		}
		return value;
	}
	
	/**
	 * �ļ�������������Ķ�����
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importAuthorData(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.importAuthorData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];
		
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
		List list = this.paraseDataFile(dataFile);
		
		// ����ReadReferenceDAO��������
		BaseAuthorDAO.getInstance().updateReadAuthor(list, temp);
		
		ret.append("�ɹ�����" + temp[0] + "����¼.");
		
		if (!"".equals(temp[1]))
		{
			ret.append("\n���벻�ɹ�idΪ").append(temp[1]);
		}
		
		return ret.toString();
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
		logger.info("BaseAuthorBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		boolean isTrue;
		
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
				int columns = 5;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BookAuthorVO vo = new BookAuthorVO();
					
					for (int j2 = 0; j2 < columns; j2++)
					{
						String value = sheets[i].getCell(j2, j).getContents()
								.trim();
						
						// ID������Ϊ��
						if ("".equals(value))
						{
							logger.error("���������ļ�ʱ���֣���������Ϊ���������ļ��е�" + j
									+ "�г��֣����в��账��");
							isTrue = false;
							break;
						}
						
						switch (j2)
						{
							case 0:
								vo.setAuthorId(value);
								break;
							case 1:
								vo.setNameLetter(value);
								break;
							case 2:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("���������ļ�ʱ���֣��Ƿ�ԭ������Ϊ��0��1ֵ�����ļ��е�"
											+ j + "�г��֣����в��账��");
									isTrue = false;
								}
								vo.setIsOriginal(value);
								break;
							case 3:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("���������ļ�ʱ���֣��Ƿ����ר��Ϊ��0��1ֵ�����ļ��е�"
											+ j + "�г��֣����в��账��");
									isTrue = false;
								}
								vo.setIsPublish(value);
								break;
							case 4:
								vo.setRecommendManual(value);
								break;
						}
					}
					
					if (isTrue)
					{
						list.add(vo);
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
}
