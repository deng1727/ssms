package com.aspire.ponaadmin.web.repository;

import javax.sql.RowSet;

import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 操作商品的一些方法。
 * @author zhangwei
 *
 */
public class ReferenceTools
{
	 /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReferenceTools.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private ReferenceTools()
    {

    }
    /**
     * singleton模式的实例
     */
    private static ReferenceTools DAO = new ReferenceTools();

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static final ReferenceTools getInstance()
    {

        return DAO;
    }
    /**
     * 更新商品的的排序值
     * @param id 商品内码id
     * @return
     * @throws BOException 
     */
    public int  updateSortid(String id,int sortid,int variation,String menuStatus) throws BOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateSortid(" + id+","+sortid +","+variation+") is beginning ...." );
        }
        String sqlCode = "ReferenceTools.updateSortid.Update";
        try
        {
        	if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
                   return  DB.getInstance().executeBySQLCode(sqlCode, new Object[]{new Integer(sortid),new Integer(variation),"0",id});
        	}else{
        		return  DB.getInstance().executeBySQLCode(sqlCode, new Object[]{new Integer(sortid),new Integer(variation),"1",id});
        	}
        }
        catch (Exception e)
        {
            throw new BOException("更新商品的排序值出错,id="+id,e);
        }
       
    }
    
    /**
     * 某货架下是否有某商品
     * @param id 商品内码id
     * @return
     * @throws BOException 
     */
    public String  isExistRef(String categoryId,String refNodeId) throws BOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("isExistRef(" + categoryId+","+refNodeId +") is beginning ...." );
        }
        Object[] paras = {categoryId,refNodeId};
        String sqlCode = "ReferenceTools.isExistRef";
        try
        {
            RowSet rs=  DB.getInstance().queryBySQLCode(sqlCode, paras);
            if(rs.next()){
            	return rs.getString(1);
            }
        }
        catch (Exception e)
        {
            throw new BOException("isExistRef出错！",e);
        }
        return null;
       
    }

}
