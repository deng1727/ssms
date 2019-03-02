
package com.aspire.ponaadmin.web.pivot.bo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.upload.FormFile;


import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.pivot.dao.PivotContentDAO;
import com.aspire.ponaadmin.web.pivot.vo.PivotContentVO;
import com.aspire.ponaadmin.web.pivot.vo.PivotDownloadVO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class PivotContentBO
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PivotContentBO.class);

    private static PivotContentBO instance = new PivotContentBO();

    private PivotContentBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static PivotContentBO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ�ص������б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryPivotContentList(PageResult page, PivotContentVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.queryPivotContentList( ) is start...");
        }

        try
        {
            // ����PivotContentDAO���в�ѯ
            PivotContentDAO.getInstance().queryPivotContentList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�ص������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����Ƴ�ָ���ص�����
     * 
     * @param contentId
     */
    public void removeContentID(String[] contentId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.removeContentID( ) is start...");
        }

        try
        {
            // ����PivotContentDAO
            PivotContentDAO.getInstance().removeContentID(contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�Ƴ�ָ���ص�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����ص������б�
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importPivotContent(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.importPivotContent( ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
            List list = BookRefBO.getInstance().paraseDataFile(dataFile);

            // У��ļ��Д����Ƿ������ݱ��д���
            String temp = PivotContentDAO.getInstance().verifyContentId(list);

            if (!"".equals(temp))
            {
                ret.append("���ݱ��������������ݱ���ֱ�Ϊ��").append(temp).append("���޸ģ�");

                throw new BOException(ret.toString(),
                                      RepositoryBOCode.CATEGORY_DEVICE);
            }

            // �����б����Ƿ����ԭ������
            PivotContentDAO.getInstance().hasContentId(list);

            String[] ids = new String[list.size()];

            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = ( String ) list.get(i);
            }

            // ����PivotDeviceDAO��������
            PivotContentDAO.getInstance().addContentId(ids);

            ret.append("�ɹ�����" + list.size() + "����¼.");

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����ص������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �������ݱ��롣�õ���������.
     * 
     * @param contentId
     * @return
     * @throws BOException
     */
    public String queryContentId(String contentId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.queryContentId( ) is start...");
        }

        try
        {
            // ����PivotContentDAO
            return PivotContentDAO.getInstance().queryContentId(contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�������ݱ��롣�õ���������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����ص�����
     * 
     * @return
     * @throws BOException
     */
    public String downloadData() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.downloadData( ) is start...");
        }

        List list = null;
        String fileName = "";
        
        try
        {
            // ���ô洢����
            PivotContentDAO.getInstance().prepareCallDownload();
            
            // ��ѯ���������ļ�Ӧ�õ�ָ��������
            list = PivotContentDAO.getInstance().queryDownloadData();
            
            // ���淵���������ļ���
            fileName = writeDataToFile(list);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����ص�����ʱ�������ݿ��쳣��");
        }

        return fileName;
    }
    
    /**
     * ���ڰ����ݼ���д������Ҫ�����ص���ʱ�ļ���
     * @param list
     * @return
     */
    private String writeDataToFile(List list) throws BOException
    {
        // ��ʱ·������
        String downloadDir = ServerInfo.getAppRootPath() + File.separator
                         + "download" + File.separator;
        IOUtil.checkAndCreateDir(downloadDir);
        
        // ��ʱ�ļ���
        String fileName = downloadDir + "download" + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss") + ".xls";
        
        writeFile(fileName, list);
        
        return fileName;
    }
    
    /**
     * д���ļ���
     * @param fileName
     * @param list
     */
    private void writeFile(String fileName, List list) throws BOException
    {
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        PivotDownloadVO vo = null;

        // ����sheet�������
        int maxSheetRowSize = 65534;
        int sheetNumber = 0;

        String[] titles = new String[] { "���ݱ���", "��������", "AP����", "AP����", "ƽ̨����",
                        "���ͱ���", "��������", "Ʒ������" };
        try
        {
            File file = new File(fileName);
            
            // �����ļ�
            workbook = Workbook.createWorkbook(file);

            for (int i = 0; i < list.size(); i++)
            {
                // ��ǰsheet��������
                int rowNumber = i % maxSheetRowSize;

                // �������sheet����������������˳�򴴽���������sheet
                if (rowNumber == 0)
                {
                    sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                                 sheetNumber);
                    sheetNumber++;

                    for (int j = 0; j < titles.length; j++)
                    {
                        // ��һ�� ���� �ڶ��� ���� ������ ����
                        sheet.addCell(new Label(j, 0, titles[j]));
                    }
                }

                vo = ( PivotDownloadVO ) list.get(i);
                sheet.addCell(new Label(0, rowNumber + 1, vo.getContentId()));
                sheet.addCell(new Label(1, rowNumber + 1, vo.getContentName()));
                sheet.addCell(new Label(2, rowNumber + 1, vo.getApId()));
                sheet.addCell(new Label(3, rowNumber + 1, vo.getApName()));
                sheet.addCell(new Label(4, rowNumber + 1, vo.getOsName()));
                sheet.addCell(new Label(5, rowNumber + 1, vo.getDeviceId()));
                sheet.addCell(new Label(6, rowNumber + 1, vo.getDeviceName()));
                sheet.addCell(new Label(7, rowNumber + 1, vo.getBrandName()));
            }
            
            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }
        }
        catch (Exception e)
        {
            throw new BOException("����excel�ļ�����", e);
        }
        finally
        {
            try
            {
                workbook.write();
                workbook.close();
            }
            catch (Exception e)
            {
                throw new BOException("����excel��������", e);
            }
        }
    }
    
    public void downloadFile(String fileName, HttpServletResponse response) throws BOException
    {
        File f = new File(fileName);
        
        BufferedInputStream br;
        try
        {
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset(); //�ǳ���Ҫ
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
            OutputStream out = response.getOutputStream();
            while((len = br.read(buf)) >0)
            out.write(buf,0,len);
            br.close();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            throw new BOException("Դexcel�ļ�������", e);
        }
        catch (IOException e)
        {
            throw new BOException("д�ļ�����", e);
        }
    }
}
