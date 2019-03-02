package com.aspire.dotcard.baseread.bo;

import java.io.File;
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

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.dotcard.baseread.dao.BaseAuthorDAO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

/**
 * @author wangminlong
 */
public class BaseAuthorBO
{

	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BaseAuthorBO.class);

	private static BaseAuthorBO instance = new BaseAuthorBO();

	private BaseAuthorBO()
	{}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BaseAuthorBO getInstance()
	{
		return instance;
	}

	/**
	 * ��ѯ�����Ķ�������Ϣ�б����
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryBaseAuthorList(PageResult page, BookAuthorVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryBaseAuthorList( ) is start...");
		}

		try
		{
			BaseAuthorDAO.getInstance().queryBaseAuthorList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���ػ����Ķ�������Ϣ�б�ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڲ�ѯ�����Ķ�ָ������������Ϣ
	 * 
	 * @param authorId
	 * @return
	 * @throws BOException
	 */
	public BookAuthorVO queryBaseAuthorVO(String authorId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryBaseAuthorVO(" + authorId
					+ ") is start...");
		}

		try
		{
			return BaseAuthorDAO.getInstance().queryBaseAuthorVO(authorId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ�����Ķ�ָ������������Ϣʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڱ�������Ķ�ָ����������
	 * 
	 * @param authorVO
	 * @throws BOException
	 */
	public void updateReadAuthor(BookAuthorVO authorVO) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.updateReadAuthor() is start...");
		}

		try
		{
			BaseAuthorDAO.getInstance().updateBaseAuthorVO(authorVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��������Ķ�ָ����������ʱ�������ݿ��쳣��");
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
		List list = null;

		try
		{
			if (isAll)
			{
				list = BaseAuthorDAO.getInstance().queryListByExport("", "");
			}
			else
			{
				String authorId = this.getParameter(request, "authorId").trim();
				String authorName = this.getParameter(request, "authorName")
						.trim();
				list = BaseAuthorDAO.getInstance().queryListByExport(authorId,
						authorName);
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
				if(i%65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				BookAuthorVO vo = (BookAuthorVO) list.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getAuthorId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getAuthorName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNameLetter());
				ws.addCell(label);
				label = new Label(col++, row, vo.getDescription());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIsOriginal());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIsPublish());
				ws.addCell(label);
				label = new Label(col++, row, vo.getRecommendManual());
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
	
	
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "����ID");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "������������ĸ");
		ws.addCell(label);
		label = new Label(col++, row, "���߼��");
		ws.addCell(label);
		label = new Label(col++, row, "�Ƿ�ԭ������");
		ws.addCell(label);
		label = new Label(col++, row, "�Ƿ��������");
		ws.addCell(label);
		label = new Label(col++, row, "�Ƽ����");
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
				int columns = 6;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BookAuthorVO vo = new BookAuthorVO();

					for (int j2 = 0; j2 < columns; j2++)
					{
						String value = sheets[i].getCell(j2, j)
								.getContents().trim();

						// ID������Ϊ��
						if ("".equals(value) && j2!=5)
						{
							logger.error("���������ļ�ʱ���֣���������Ϊ���������ļ��е�" + j + "�г��֣����в��账��");
							isTrue = false;
							break;
						}
						
						logger.info("��ǰֵΪ��" + value + "��J2ֵΪ��"+ j2);
						
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
							case 5:
								vo.setDescription(value);
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

	/**
	 * ���ڲ�ѯ�����Ķ�ָ����������
	 * 
	 * @param authorId
	 * @throws BOException
	 */
	public List queryAuthorIdKeyResource(String authorId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("BaseAuthorBO.queryAuthorIdKeyResource( ) is start...");
		}
		List list = null;
		try
		{
			list = BaseAuthorDAO.getInstance().queryAuthorKeyResource(authorId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ�����Ķ�ָ����������ʱ�������ݿ��쳣��");
		}
		return list;
	}

	/**
	 * ���ڷ��ػ����Ķ�������չ�ֶ��б�
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryAuthorKeyBaseList(String tid) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryAuthorKeyBaseList( ) is start...");
		}

		try
		{
			return BaseAuthorDAO.getInstance().queryAuthorKeyBaseList(tid);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���ػ����Ķ�������չ�ֶ��б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	 /**
     * �ϴ�ͼ�����ߵ�Ԥ��ͼ��ͼƬ
     * @param request
     * @throws BOException
     */
    public String uploadAuthorPicURL(FormFile uploadFile,String authorId)throws BOException
    {
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "readAuthor"
                         + File.separator;
        String ftpDir = BaseReadConfig.get("bookAuthorPicURl");
        
        IOUtil.checkAndCreateDir(tempDir);

        // ����ļ���������ļ�������·����
        String fileName = uploadFile.getFileName();
        String suffex = fileName.substring(fileName.lastIndexOf('.'));

        // ƴװ��ʱ�ļ���Ϊ temp.png
        String oldFileName = tempDir + "temp" + suffex;
		logger.debug("oldFileName="+oldFileName);
        try
        {
            IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
        }
        catch (Exception e1)
        {
            throw new BOException("д��ͼ������ͼƬ��ʱ�ļ�ʧ�ܡ�tempFilePath=" + oldFileName,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }

       

        FTPClient ftp = null;
        try
        {
            ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
                                          SystemConfig.SOURCESERVERPORT,
                                          SystemConfig.SOURCESERVERUSER,
                                          SystemConfig.SOURCESERVERPASSWORD,
                                          ftpDir);
            PublicUtil.checkAndCreateDir(ftp, authorId);
            ftp.chdir(authorId);
            ftp.put(oldFileName, authorId+suffex);
            
        }
        catch (Exception e)
        {
            throw new BOException("�ϴ�����Դ�����������쳣��",
                                  e,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }
        finally
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e)
                {
                }
            }
        }
        return SystemConfig.URL_PIC_VISIT + BaseReadConfig.get("bookAuthorPicURl")
                +authorId+"/"+authorId+suffex;
    }
   
}
