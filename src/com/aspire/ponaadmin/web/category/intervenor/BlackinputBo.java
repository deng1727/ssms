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
	     * 获取实例
	     * 
	     * @return 实例
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

	            // 只拿第一个sheet
	            int rows = sheets[0].getRows();
	            int cols = sheets[0].getColumns();
	            String s=null;
	            if (logger.isDebugEnabled())
	            {
	                logger.debug("dataFile.rows==" + rows);
	            }

	            // 从第一行开始循环，因为第一行是列名 
	            for (int j = 1; j < rows; j++)
	            {
	            	for (int i = 0; i < cols; i++) {
	            		switch(i){
	            		case 0:s="应用id:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 1:s=s+",应用名称:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 2 :s=s+",公司名称:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		case 3:s=s+",合作游戏同名:"+sheets[0].getCell(i, j).getContents().trim();break;
	            		}
					}
	                if (list.contains(s))
	                {
	                    // 删除原来存在的数据
	                    list.remove(s);
	                }

	                // 加入内容数据id
	                list.add(s);
	            }

	        }
	        catch (Exception e)
	        {
	            logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
	            e.printStackTrace();
	            throw new BOException("解析导入文件出现异常", e);
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

	        // 解析导入文件数据
	        List tempList = paraseDataFile(dataFile);

	        if (logger.isDebugEnabled())
	        {
	            logger.debug("解析之后去重数据数为" + tempList.size());
	        }
	        String s1=null;
	        String s2=null;
	        String s3=null;
	        String s4=null;
	        String sqlCode="intervenor.BlackinputDao.importFile().insert";
	        for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
	        	String str = (String) iterator.next();//应用id:300009954226,应用名称:诛仙,公司名称:四川瑞银通汽车服务有限公司,合作游戏同名:是
	        	String[] st=str.split("应用id:");
	        	s1=str.split("应用id:")[1].split(",应用名称:")[0];
	        	s2=str.split("应用id:")[1].split(",应用名称:")[1].split(",公司名称:")[0];
	        	s3=str.split("应用id:")[1].split(",应用名称:")[1].split(",公司名称:")[1].split(",合作游戏同名:")[0];
	        	s4=str.split("应用id:")[1].split(",应用名称:")[1].split(",公司名称:")[1].split(",合作游戏同名:")[1];
	        	Object[]paras={s1,s2,s3,s4};
	        	DB.getInstance().executeBySQLCode(sqlCode, paras);
	        }
	           
	       
	    }
}
