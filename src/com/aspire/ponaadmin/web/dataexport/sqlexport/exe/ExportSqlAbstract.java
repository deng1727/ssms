package com.aspire.ponaadmin.web.dataexport.sqlexport.exe;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.SqlKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.bo.DataExportBO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportDAO;
import com.enterprisedt.net.ftp.FTPClient;

public abstract class ExportSqlAbstract implements ExportSqlInterface
{
	/**
	 * ��־����
	 */
	protected static JLogger Log = null;
	
	/**
	 * ����������Ϣ
	 */
	protected DataExportVO vo = null;
	
	/**
	 * ������������
	 */
	protected int rowNum = 0;
	
	public ExportSqlAbstract(DataExportVO vo)
	{
		Log = LoggerFactory.getLogger(this.getClass());
		this.vo = vo;
	}
	
	/**
	 * ���ڷ��ص�ǰ����ִ�е�sql����ܹ��õ��Ľ������
	 * 
	 * @return
	 * @throws DAOException
	 */
	private int getDataSize() throws BOException
	{
		Log.info("ͳһ�����ļ�ģ�飬��ǰִ��sql���Ϊ:" + vo.getExportSql() + ". ������Ϊ��"
				+ vo.getExportLine());
		
		// ��sql����뵼�����������֤
		if (!DataExportDAO.getInstance().hasSqlTrue(vo.getExportSql(),
				vo.getExportLine()))
		{
			Log.error("��sql����뵼�����������֤!");
			throw new BOException("��sql����뵼�����������֤��");
		}
		
		Log.info("ͳһ�����ļ�ģ�飬��ȡ��ǰִ��sql����õĽ������.");
		
		try
		{
			return DataExportDAO.getInstance().getDataSize(vo.getExportSql());
		}
		catch (DAOException e)
		{
			Log.error("ִ������sqlʱ����������֤!", e);
			throw new BOException("��sql����뵼�����������֤��",e);
		}
	}
	
	/**
	 * ���ڴ���Ŀ¼
	 */
	private void createFilePath()
	{
		File file = new File(vo.getFileAllName());
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
	}
	
	/**
	 * �õ�����SQLȫ������
	 * 
	 * @return
	 */
	private List<String[]> queryDBforList()
	{
		Log.info("�õ�����SQLȫ�����ݣ���ǰִ��sql���Ϊ:" + vo.getExportSql() + ". ������Ϊ��"
				+ vo.getExportLine());
		
		try
		{
			return DataExportDAO.getInstance().getDataByExportSql(
					vo.getExportSql(), vo.getExportLine());
		}
		catch (DAOException e)
		{
			Log.error("ִ������sqlʱ����������֤!", e);
			return null;
		}
	}
	
	/**
	 * �õ�����SQL��ҳ����
	 * 
	 * @param start
	 *            ��ʼ��
	 * @param end
	 *            ��β��
	 * @return
	 */
	private List<String[]> queryDBforPagingList(int start, int end)
	{
		Log.info("�õ�����SQL��ҳ���ݣ���ǰִ��sql���Ϊ:" + vo.getExportSql() + ". ������Ϊ��"
				+ vo.getExportLine() + ". ��ʼ����Ϊ��" + start + "��������Ϊ��" + end);
		
		try
		{
			return DataExportDAO.getInstance().getPagingDataByExportSql(
					vo.getExportSql(), vo.getExportLine(), start, end);
		}
		catch (DAOException e)
		{
			Log.error("ִ������sqlʱ����������֤!", e);
			return null;
		}
	}
	
	/**
	 * ��ҳִ�����ݵ���
	 * 
	 * @param dataSize
	 * @param pageContentNum
	 * @throws BOException
	 */
	private void pagingDataExport(int dataSize, int pageContentNum)
			throws BOException
	{
		List<String[]> dataList = null;
		// �õ���ҳ��
		int pageNum = (dataSize + pageContentNum - 1) / pageContentNum;
		int start = 0;
		int end = 0;
		
		Log.info("��ǰ����������ҳ����ÿҳΪ:" + pageContentNum + " ����" + pageNum + "ҳ��ѯ.");
		
		for (int i = 0; i < pageNum; i++)
		{
			start = i * pageContentNum;
			end = (i + 1) * pageContentNum;
			
			Log.info("�˴β�ѯ����������ҳ����Ϊ>��:" + start + "��<=��" + end + "��.");
			
			dataList = queryDBforPagingList(start, end);
			
			try
			{
				writerFile(dataList);
			}
			catch (BOException e)
			{
				Log.error("�����ļ�ʱ�����˴�����ʧ�ܣ�", e);
				//throw new BOException("�����ļ�ʱ�����˴�����ʧ�ܣ�", e);
			}
			if(dataList!=null)
				if(dataList!=null) dataList.clear();
		}
	}
	
	/**
	 * ���������ļ�
	 */
	public String createFile(DataExportGroupVO groupVO)
	{
		Log.info("ͳһ�����ļ�ģ����������ǰ������Ϊ" + vo.getExportName());
		if (!SqlKey.isKey(vo.getExportSql().trim()))
		{
			Log.error("��ǰִ�е�sql��������⣬ֻ��Ϊselect���! ��ǰ���Ϊ:" + vo.getExportSql());
			return "��ǰִ�е�sql��������⣬ֻ��Ϊselect���! ��ǰ���Ϊ:" + vo.getExportSql();
		}
		int pageContentNum = Integer.parseInt(vo.getExportPageNum());
		long starttime = System.currentTimeMillis();
		Date date = new Date();
		int dataSize = 0;
		try
		{
			// �õ�����������        У���ļ���Ҫ������
			dataSize = getDataSize();
		}
		catch (BOException e1)
		{
			Log.error("ִ������sqlʱ����������֤!", e1);
			return "ִ������sqlʱ����������֤!";
		}
		
		// ���Ϊ��
		if (dataSize == 0)
		{
			Log.info("��ѯ���ݿ�ʱ�õ�����Ϊ�ա��˴�����ʧ�ܣ�");
			return "��ѯ���ݿ�ʱ�õ�����Ϊ�ա��˴�����ʧ�ܣ�";
		}
		
		// ����Ŀ¼
		createFilePath();
		
		// д���ļ�
		try
		{
			// �����ļ�
			createFileByName();
			// ȫ����������
			if (pageContentNum == 0)
			{
				List<String[]> dataList = queryDBforList();
				writerFile(dataList);
				if(dataList!=null)
				{
					if(dataList!=null) dataList.clear();
				}
			}
			// ��ҳ��������
			else
			{
				pagingDataExport(dataSize, pageContentNum);
			}
			
			createVeriFile(dataSize);
		}
		catch (BOException e)
		{
			Log.error("�����ļ�ʱ�������󣡴˴�������ʧ�ܸ��ա���������", e);
			return e.getMessage();
		}
		finally
		{
			// �ر��ļ�
			try
			{
				closeFile();
			}
			catch (BOException e)
			{
				Log.error("�رյ�ǰ���������ļ�ʱ�������󣡴˴�������ʧ�ܸ��ա���������", e);
				return e.getMessage();
			}
		}
		Log.info("ͳһ�����ļ�ģ�������ļ�������ɣ���ǰ������Ϊ" + vo.getExportName());
		
		// �Ƿ��ϴ�FTP
		if(!groupVO.getFtpId().equals("0"))
		{
			if (null != groupVO.getDataExportFtpVo()
					&& !"".equals(groupVO.getDataExportFtpVo().getFtpId()))
			{
				try
				{
					copyFileToFTP(groupVO);
				}
				catch (BOException e)
				{
					Log.error("�������ϴ�FTPʱ�����˴��󣡴˴�������ʧ�ܸ��ա���������", e);
					return e.getMessage();
				}
			}
		}
		
		// ���һ��ִ��ʱ�䡢ִ�к�ʱ���
		try
		{
			long endtime = System.currentTimeMillis();
			vo.setLastTime(date);
			vo.setExecTime(String.valueOf(endtime - starttime));
			DataExportBO.getInstance().editDataExport(vo);
		}
		catch (BOException e)
		{
			Log.error("���д�����ʱ�����ִ��ʱ��ʱ�������󣡴˴�����Ҳ��������ʧ�ܸ��ա���������", e);
			return e.getMessage();
		}
		
		Log.info("ͳһ�����ļ�ģ����ɣ���ǰ������Ϊ" + vo.getExportName());
		
		return "success";
	}
	
	/**
	 * ��ǰ��������Ҫ���ϴ��ļ���FTP
	 * @param groupVO
	 * @throws BOException
	 */
	private void copyFileToFTP(DataExportGroupVO groupVO) throws BOException
	{
		Log.info("��ǰ����Ҫ�ϴ�FTP����ǰ������Ϊ" + vo.getExportName());
		
        FTPClient ftp = null;

        try
        {
            // ȡ��Զ��Ŀ¼���ļ��б�
            ftp = groupVO.getDataExportFtpVo().getFTPClient();

            if (null != groupVO.getDataExportFtpVo().getFtpPath() && !"".equals(groupVO.getDataExportFtpVo().getFtpPath()))
            {
                ftp.chdir(groupVO.getDataExportFtpVo().getFtpPath());
            }

            // �ѱ���ȫ·���ļ��ϴ���ָ���ļ���
            ftp.put(vo.getFileAllName(), vo.getToFTPFileName());

        }
        catch (Exception e)
        {
            throw new BOException("��ǰ����Ҫ�ϴ�FTP�������ϴ��ļ�ʱ�����쳣��", e);
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
		Log.info("��ǰ�����ϴ�FTP��ɣ���ǰ������Ϊ" + vo.getExportName());
	}
	
	public abstract void createFileByName() throws BOException;
	
	public abstract void closeFile() throws BOException;
	
	public abstract void createVeriFile(int size) throws BOException;
	
	public abstract String writerFile(List<String[]> dataList)
			throws BOException;
}
