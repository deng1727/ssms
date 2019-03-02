package com.aspire.ponaadmin.web.category.intervenor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BlackinputBo {
	 protected static final JLogger logger = LoggerFactory.getLogger(IntervenorBO.class);

	    private static BlackinputBo instance = new BlackinputBo();

	    private BlackinputBo()
	    {

	    }

	    /**
	     * ��ȡʵ��
	     * 
	     * @return ʵ��
	     */
	    public static BlackinputBo getInstance()
	    {

	        return instance;
	    }
	    private List paraseDataFile(FormFile dataFile) throws BOException
	    {
	        logger.info("BlackinputBo.paraseDataFile() is start!");

	        List list = new ArrayList();

	        Workbook book = null;

	        try
	        {
	            book = Workbook.getWorkbook(dataFile.getInputStream());

	            Sheet[] sheets = book.getSheets();

	            // ֻ�õ�һ��sheet
	            int rows = sheets[0].getRows();
	            int cols = sheets[0].getColumns();
	            String s=null;
	            if (logger.isDebugEnabled())
	            {
	                logger.debug("dataFile.rows==" + rows);
	            }

	            // �ӵ�һ�п�ʼѭ������Ϊ��һ�������� 
	            for (int j = 1; j < rows; j++)
	            {
	            	for (int i = 0; i < cols; i++) {
	            		switch(i){
	            		case 0:s="Ӧ��id:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 1:s=s+",Ӧ������:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 2 :s=s+",��˾����:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 3:s=s+",������Ϸͬ��:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		}
					}
	                if (list.contains(s))
	                {
	                    // ɾ��ԭ�����ڵ�����
	                    list.remove(s);
	                }

	                // ������������id
	                list.add(s);
	            }

	        }
	        catch (Exception e)
	        {
	            logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
	            e.printStackTrace();
	            throw new BOException("���������ļ������쳣", e);
	        }
	        finally
	        {
	            book.close();
	        }

	        return list;
	    }
	    public void importFile( FormFile dataFile) throws Exception
	    {

	        if (logger.isDebugEnabled())
	        {
	            logger.debug("BlackinputBo.importFile() is start...");
	        }

	        // ���������ļ�����
	        List tempList = paraseDataFile(dataFile);

	        if (logger.isDebugEnabled())
	        {
	            logger.debug("����֮��ȥ��������Ϊ" + tempList.size());
	        }
	        String s1=null;
	        String s2=null;
	        String s3=null;
	        String s4=null;
	        String sqlCode="intervenor.BlackinputDao.importFile().insert";
	        for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
	        	String str = (String) iterator.next();//Ӧ��id:300009954226,Ӧ������:����,��˾����:�Ĵ�����ͨ�����������޹�˾,������Ϸͬ��:��
	        	String[] st=str.split("Ӧ��id:");
	        	s1=str.split("Ӧ��id:")[1].split(",Ӧ������:")[0];
	        	s2=str.split("Ӧ��id:")[1].split(",Ӧ������:")[1].split(",��˾����:")[0];
	        	s3=str.split("Ӧ��id:")[1].split(",Ӧ������:")[1].split(",��˾����:")[1].split(",������Ϸͬ��:")[0];
	        	s4=str.split("Ӧ��id:")[1].split(",Ӧ������:")[1].split(",��˾����:")[1].split(",������Ϸͬ��:")[1];
	        	Object[]paras={s1,s2,s3,s4};
	        	DB.getInstance().executeBySQLCode(sqlCode, paras);
	        }
	           
	       
	    }
}
