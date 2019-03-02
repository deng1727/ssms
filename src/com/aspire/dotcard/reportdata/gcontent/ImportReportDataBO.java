package com.aspire.dotcard.reportdata.gcontent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConstants;
import com.aspire.ponaadmin.web.util.StringTool;


public class ImportReportDataBO
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ImportReportDataBO.class);

    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");

    
    /**
     * ���췽������singletonģʽ����
     */
    private ImportReportDataBO()
    {
    }

    /**
     * singletonģʽ��ʵ��
     */
    private static ImportReportDataBO bo = new ImportReportDataBO();

    /**
     * ��ȡʵ��
     *
     * @return ʵ��
     */
    public static final ImportReportDataBO getInstance()
    {
        return bo;
    }
    
    /**
     * ���������ݲ��뵽���ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return List
     */
    public boolean importReportData(String fileName, String ftpPath, int type,Integer[] result)
                    throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ImportReportData start....");
            logger.debug("The file name is��" + fileName);
        }
        String fullPathFilename = ftpPath + File.separator + fileName;
        // ��ȡ�ļ�����
        Object[] arrColorContent = readAllFileLine(fullPathFilename);
        int iDataNum = 0;
        if (null != arrColorContent)
        {
            iDataNum = arrColorContent.length;
        }
        
        //��¼���
        if(result[0] == null){
        	
        	result[0] = new Integer(0);
        }
        result[0] =   new Integer(iDataNum + result[0].intValue());
        if (iDataNum == 0)
        {
            logger.debug("û��Ҫ���������");
            return false;
        }
        else
        {
            logger.debug("һ��Ҫ����" + iDataNum + "������");
        }

        // ���д���
        for (int i = 0; i < iDataNum; i++)
        {     
            ImportReportDataVO  vo;
            
            try
            {
            	vo = generateVO(( String ) arrColorContent[i],type);
            	ImportReportDataDAO.getInstance().updateProductValues(vo,result);
            }
            catch(Exception e)
            {
            	//�����ļ���ʽ���󣬼�¼��־������
            	topDatalog.error("�����ļ���ʽ����filename��" + fullPathFilename);
            	logger.error("lineNumer="+(i+1),e);
            	if(result[2] == null){
            		result[2] = new Integer(0);
            	}
            	
            	result[2] = new Integer(result[2].intValue() + 1);
            	continue;//added by zhangwei ���һ����¼����ʧ�ܼ�������
            	//return false;
            }
           
        }
        return true;
    }
    
    /**
     * ��ȡ�ļ��е�ȫ�����ݣ����һ��String
     *
     * @param fileName ,�ļ���ȫ·������
     * @return String
     * @throws IOException
     */
    public static Object[] readAllFileLine(String fileName) throws IOException
    {
        ArrayList list = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null && line.length() > 0)
            {
                list.add(line);
                line = br.readLine();
            }
        }
        catch(IOException ie)
        {
            logger.error(ie);
            throw ie;
        }
        finally{
            br.close();
            fr.close();
        }

        return list.toArray();
    }
    
    /**
     * ��ÿһ�������������ݷֽ⣬����㲥����
     * ���õ����ж���VO��ȥ
     */
    private static ImportReportDataVO generateVO(String recordContent, int type)
                    throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ImportReportDataVO is starting.....");
        }
        ImportReportDataVO vo = new ImportReportDataVO();

        vo.setType(type);

        String[] r = null;
        if (TopDataConstants.CONTENT_TYPE_DAY == type)
        {
            if(StringTool.lengthOfHZ(recordContent) != 255)
            {
                throw new Exception("�ļ����ݸ�ʽ����ȷ!");
            }
            r = new String[14];
            int[] l = {8,12,60,15,15,15,15,15,15,15,15,15,15,15};
            for (int i = 0; i < 14; i++)
            {
                int temp = 0;
                for(int j=i-1;j>-1;j--)
                {
                    temp = temp+r[j].length();
                }
                 
                r[i] = StringTool.formatByLen(recordContent.substring(temp),l[i],"");
            }
        }
        else
        {
            if(StringTool.lengthOfHZ(recordContent) != 195)
            {
                throw new Exception("�ļ����ݸ�ʽ����ȷ!");
            }
            r = new String[10];
            int[] l = {8,12,60,15,15,15,15,15,15,15};
            for (int i = 0; i < 10; i++)
            {
                int temp = 0;
                for(int j=i-1;j>-1;j--)
                {
                    temp = temp+r[j].length();
                }
                 
                r[i] = StringTool.formatByLen(recordContent.substring(temp),l[i],"");
            }
        }

        vo.setContentId(r[1].trim());
        vo.setContentName(r[2].trim());
        vo.setSearchCount(Long.parseLong(r[3].trim()));
        vo.setPvCount(Long.parseLong(r[4].trim()));
        vo.setSubsCount(Long.parseLong(r[5].trim()));
        vo.setRemarkCount(Long.parseLong(r[6].trim()));
        vo.setScoreCount(Long.parseLong(r[7].trim()));
        vo.setCommendCount(Long.parseLong(r[8].trim()));
        vo.setCollectionCount(Long.parseLong(r[9].trim()));
        if (TopDataConstants.CONTENT_TYPE_DAY == type)
        {
            vo.setAvgScoreCount(Long.parseLong(r[10].trim()));
            vo.setAdd7DaysOrderCount(Long.parseLong(r[11].trim()));
            vo.setAdd30DaysOrderCount(Long.parseLong(r[12].trim()));
            vo.setDownLoadCount(Long.parseLong(r[13].trim()));
        }
        
        return vo;
    }
    
}
