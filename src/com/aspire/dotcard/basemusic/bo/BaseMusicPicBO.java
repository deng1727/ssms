package com.aspire.dotcard.basemusic.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.aspire.dotcard.basemusic.dao.BaseMusicPicDAO;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author wangminlong
 */
public class BaseMusicPicBO
{
	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BaseMusicPicBO.class);
	
	private static BaseMusicPicBO instance = new BaseMusicPicBO();
	
	private BaseMusicPicBO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BaseMusicPicBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ѯ����������Ϣ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryMusicList(PageResult page, BaseMusicVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseMusicPicBO.queryMusicList( ) is start...");
		}
		
		try
		{
			BaseMusicPicDAO.getInstance().queryMusicList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���ز�ѯ����������Ϣ�б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ���ڵ���������������
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportMusicPicData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<BaseMusicVO> list = null;
		
		try
		{
			if (isAll)
			{
				BaseMusicVO vo = new BaseMusicVO();
				vo.setMusicId("");
				vo.setMusicName("");
				vo.setSingerName("");
				list = BaseMusicPicDAO.getInstance().queryMusicListByExport(vo);
			}
			else
			{
				String musicId = request.getParameter("musicId").trim();
				String musicName = request.getParameter("musicName").trim();
				String singer = request.getParameter("singer").trim();
				
				BaseMusicVO vo = new BaseMusicVO();
				vo.setMusicId(musicId);
				vo.setMusicName(musicName);
				vo.setSingerName(singer);
				
				list = BaseMusicPicDAO.getInstance().queryMusicListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("�ڵ���������������ʱ�������ݿ��쳣��", e);
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
	private void generateQueryDataExcel(List<BaseMusicVO> list,
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
				
				BaseMusicVO vo = (BaseMusicVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getMusicId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getMusicName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSingerName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getMusicPic());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCreateTime());
				ws.addCell(label);
				label = new Label(col++, row, vo.getValidity());
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
		label = new Label(col++, row, "����ͼƬ");
		ws.addCell(label);
		label = new Label(col++, row, "���ֵ���ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "������Ч��");
		ws.addCell(label);
	}
	
	/**
	 * �ļ���������������ָ���
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importMusicPicData(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseSingerBO.importSingerData( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];
		
		try
		{
			// ����EXECL�ļ�����ȡ�ն�����汾�б�
			List<BaseMusicVO> list = this.paraseDataFile(dataFile);
			
			BaseMusicPicDAO.getInstance().updateBaseMusicPic(list, temp);
			
			ret.append("�ɹ�����" + temp[0] + "����¼.");
			
			if (!"".equals(temp[1]))
			{
				ret.append("\n���벻�ɹ�idΪ").append(temp[1]);
			}
			
			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("����������ָ���ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����EXECL�ļ�����ȡҪ��ӵĻ������ָ���
	 * 
	 * @param dataFile
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public List<BaseMusicVO> paraseDataFile(FormFile dataFile)
			throws BOException
	{
		logger.info("BaseSingerBO.paraseDataFile() is start!");
		List<BaseMusicVO> list = new ArrayList<BaseMusicVO>();
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
				int columns = 2;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BaseMusicVO vo = new BaseMusicVO();
					
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
								vo.setMusicId(value);
								break;
							case 1:
								vo.setMusicPic(value);
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
