package com.aspire.ponaadmin.web.game.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
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
import com.aspire.ponaadmin.web.game.dao.TWGameDAO;
import com.aspire.ponaadmin.web.game.vo.TWGameVO;

/**
 * @author wangminlong
 */
public class TWGameBO
{
	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(TWGameBO.class);
	
	private static TWGameBO instance = new TWGameBO();
	
	private TWGameBO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static TWGameBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ѯͼ����Ϸ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryTWGameList(PageResult page, TWGameVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("TWGameBO.queryTWGameList( ) is start...");
		}
		
		try
		{
			TWGameDAO.getInstance().queryTWGameList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���ز�ѯͼ����Ϸ�б�ʱ�������ݿ��쳣��",e);
		}
	}
	
	/**
	 * ���ڵ��������Ķ���������
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<TWGameVO> list = null;
		
		try
		{
			if (isAll)
			{
				list = TWGameDAO.getInstance().queryListByExport("", "");
			}
			else
			{
				String gameId = this.getParameter(request, "gameId").trim();
				String gameName = this.getParameter(request, "gameName").trim();
				list = TWGameDAO.getInstance().queryListByExport(gameId,
						gameName);
			}
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
	private void generateQueryDataExcel(List<TWGameVO> list,
			WritableWorkbook wwb)
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
				
				TWGameVO vo = (TWGameVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getGameId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getGameName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCPName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getGameDesc());
				ws.addCell(label);
				label = new Label(col++, row, String.valueOf(vo.getOldPrice()));
				ws.addCell(label);
				label = new Label(col++, row, String.valueOf(vo.getFee()));
				ws.addCell(label);
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
		
		label = new Label(col++, row, "��ϷID");
		ws.addCell(label);
		label = new Label(col++, row, "��Ϸ����");
		ws.addCell(label);
		label = new Label(col++, row, "��Ӧ��");
		ws.addCell(label);
		label = new Label(col++, row, "��Ϸ���");
		ws.addCell(label);
		label = new Label(col++, row, "ԭ���ʷѣ��֣�");
		ws.addCell(label);
		label = new Label(col++, row, "�ּ��ʷѣ��֣�");
		ws.addCell(label);
		label = new Label(col++, row, "�������");
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
	public String importTWGameSortId(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("TWGameBO.importAuthorData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];
		
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
		List<TWGameVO> list = this.paraseDataFile(dataFile);
		
		// ����ReadReferenceDAO��������
		TWGameDAO.getInstance().updateTWGameSortId(list, temp);
		
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
	public List<TWGameVO> paraseDataFile(FormFile dataFile) throws BOException
	{
		logger.info("TWGameBO.paraseDataFile() is start!");
		List<TWGameVO> list = new ArrayList<TWGameVO>();
		Workbook book = null;
		boolean isTrue;
		
		try
		{
			Set<String> twIdKeySet = TWGameDAO.getInstance().getTWIdKeySet();
			Set<String> sortIdKeySet = TWGameDAO.getInstance().getSortIdKeySet();
			
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
				int columns = 2;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					TWGameVO vo = new TWGameVO();
					
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
							case 0:// gameid
								if (twIdKeySet.contains(value))
								{
									vo.setGameId(value);
								}
								else
								{
									logger
											.error("���������ļ�ʱ���֣���ǰͼ����Ϸ��������ͼ����Ϣ�����ļ��е�"
													+ j + "�г��֣����в��账��");
									isTrue = false;
								}
								break;
							case 1://sortid
								try
								{
									value = String.valueOf(Integer.parseInt(value)*-1);
									vo.setSortId(value);
								}
								catch (NumberFormatException e)
								{
									logger.error("���������ļ�ʱ���֣�������Ϣ������ֵ�ͣ����ļ��е�"
											+ j + "�г��֣����в��账��");
									isTrue = false;
								}
								break;
						}
						// �����һ�г����Ͳ��ڴ�����һ��
						if(!isTrue)
						{
							continue;
						}
					}
					// �����ǰ��û������ �����뵽��Ҫ����ļ�����
					if (isTrue)
					{
						if(sortIdKeySet.contains(vo.getGameId()))
						{
							vo.setDbType("U");
						}
						else
						{
							vo.setDbType("A");
						}
						list.add(vo);
					}
				}
			}
			
			twIdKeySet.clear();
			sortIdKeySet.clear();
		}
		catch (DAOException e1)
		{
			logger.error("�õ����ݿ���ͼ����Ϸ��ȫ��idʱ��������", e1);
			throw new BOException("���������ļ������쳣", e1);
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
