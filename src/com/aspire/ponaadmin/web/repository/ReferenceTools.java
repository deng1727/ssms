package com.aspire.ponaadmin.web.repository;

import javax.sql.RowSet;

import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ������Ʒ��һЩ������
 * @author zhangwei
 *
 */
public class ReferenceTools
{
	 /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReferenceTools.class);

    /**
     * ���췽������singletonģʽ����
     */
    private ReferenceTools()
    {

    }
    /**
     * singletonģʽ��ʵ��
     */
    private static ReferenceTools DAO = new ReferenceTools();

    /**
     * ��ȡʵ��
     *
     * @return ʵ��
     */
    public static final ReferenceTools getInstance()
    {

        return DAO;
    }
    /**
     * ������Ʒ�ĵ�����ֵ
     * @param id ��Ʒ����id
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
            throw new BOException("������Ʒ������ֵ����,id="+id,e);
        }
       
    }
    
    /**
     * ĳ�������Ƿ���ĳ��Ʒ
     * @param id ��Ʒ����id
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
            throw new BOException("isExistRef����",e);
        }
        return null;
       
    }

}
