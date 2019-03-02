package com.aspire.ponaadmin.web.taccode.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO;
import com.aspire.ponaadmin.web.taccode.vo.TacVO;

public class TacCodeBO {

	/**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(TacCodeBO.class);

    private static TacCodeBO instance = new TacCodeBO();

    private TacCodeBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static TacCodeBO getInstance()
    {
        return instance;
    }
    
    /**
     * 用于查询TAC码库列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryTacCodeList(PageResult page, TacVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.queryTacCodeList() is start...");
        }

        try
        {
            // 调用TacCodeDAO进行查询
            TacCodeDAO.getInstance().queryTacCodeList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询TAC码库列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于删除指定TAC码
     * 
     * @param id
     * @param tacCode
     */
    public void delByTacCode(String id,String tacCode) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.delByTacCode() is start...");
        }

        try
        {
            // 调用TacCodeDAO
        	TacCodeDAO.getInstance().delByTacCode(id,tacCode);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("移除指定重点机型时发生数据库异常！");
        }
    }
    
    /**
     * 导入TAC码库列表
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importTacCode(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.importTacCode() is start...");
        }

        StringBuffer ret = new StringBuffer();

        List list = new ArrayList();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }
        catch (BOException e)
        {
        	logger.error("TAC码库列表导入,Excel解析异常!", e);
            throw new BOException("TAC码库列表导入,Excel解析异常!");
        }
        
        if(list == null || list.size() < 1)
        	throw new BOException("TAC码库列表导入,Excel文件为空!");
        
        try
        {
            // 获取tacCode列表
            Map tacCodeMap = TacCodeDAO.getInstance().getTacCodeMap();
            int success = 0;//记录成功导入条数
            int fail = 0;//记录失败导入条数
            StringBuffer failRows = new StringBuffer();//记录失败的行
            ArrayList<Object[]> insertList = new ArrayList<Object[]>();//保存新增数据
            ArrayList<Object[]> updateList = new ArrayList<Object[]>();//保存更新数据
            for(int i =0;i < list.size();i++){
            	Map map = (Map)list.get(i);
            	if(null == map || 5 != map.size()){//不需有五列
            		fail++;
            		failRows.append(i+",");
            		continue;
            	}
            	//Excel里5列记录都不能为空
            	if(null == map.get(0)||"".equals(map.get(0))||null == map.get(1)||"".equals(map.get(1))
            			||null == map.get(2)||"".equals(map.get(2))||null == map.get(3)||"".equals(map.get(3))
            			||null == map.get(4)||"".equals(map.get(4))){
            		fail++;
            		failRows.append(i+",");
            		continue;
            	}
            	//存在就更新
            	if(tacCodeMap.containsKey(map.get(2))){
            		//添加的顺序是表格第一列为渠道ID,第二列为渠道名,第三列为TAC码,第四列为手机品牌,第五列为手机型号
            		updateList.add(new Object[]{map.get(1).toString().trim(),map.get(2),map.get(0),map.get(3),map.get(4)});
            	}else{
            		insertList.add(new Object[]{map.get(1).toString().trim(),map.get(2),map.get(0),map.get(3),map.get(4)});
            	}
            	if(insertList.size() >= 5000){//5000条执行一次数据库操作
            		success += TacCodeDAO.getInstance().patchImportData("1",insertList);
            		insertList.clear();
            		insertList = new ArrayList<Object[]>();
            	}
            	if(updateList.size() >= 5000){//5000条执行一次数据库操作
            		success += TacCodeDAO.getInstance().patchImportData("2",updateList);
            		updateList.clear();
            		updateList = new ArrayList<Object[]>();
            	}
            }

            //最后把未执行的数据在导入数据中
            if(null != insertList && insertList.size() > 0){
            	success += TacCodeDAO.getInstance().patchImportData("1",insertList);
            	insertList.clear();
            	insertList = new ArrayList<Object[]>();
            }
            if(null != updateList && updateList.size() > 0){
            	success += TacCodeDAO.getInstance().patchImportData("2",updateList);
        		updateList.clear();
        		updateList = new ArrayList<Object[]>();
            }
            
            ret.append("总共导入："+list.size()+"条记录,");
            ret.append("成功导入" + success + "条记录,");
            if(fail > 0){
            	ret.append("失败导入" + fail + "条记录,");
            	ret.append("导入失败行："+failRows.toString());
            }
            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("导入TAC码库列表时发生数据库异常！");
        }
    }
}
