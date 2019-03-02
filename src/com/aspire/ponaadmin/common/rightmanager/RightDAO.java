package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>Ȩ����ص�DAO��</p>
 * <p>Ȩ����ص�DAO�࣬��Ȩ�����������ڲ�����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RightDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(RightDAO.class) ;

    /**
     * ��������pageURIVO��map
     */
    private HashMap pageURIMap = null;

    /**
     * ��������Ȩ��RightVO��map
     */
    private HashMap rightMap = null;

    /**
     * singletonģʽ��ʵ��
     */
    private static RightDAO instance = null ;

    /**
     * ���췽������singletonģʽ����
     */
    private RightDAO () throws DAOException
    {
        this.loadAllPageRUI();
        this.loadAllRight();
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static RightDAO getInstance () throws DAOException
    {
        if(instance == null)
        {
            instance = new RightDAO();
        }
        return instance ;
    }

    /**
     * ��ȡһ��http����uri��Ӧҳ�������ڵ�Ȩ��
     * @param pageURI http����uri
     * @return ҳ���Ӧ��Ȩ��
     */
    final RightVO getRightOfPageURI (String pageURI)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightOfPageURI(" + pageURI + ")") ;
        }
        RightVO right = null;
        PageURIVO page = (PageURIVO) this.pageURIMap.get(pageURI);
        if(page != null)
        {
            right = (RightVO) this.rightMap.get(page.getRightID());

        }
        return right;
    }

    /**
     * �����ݿ����������е�pageURI
     */
    private void loadAllPageRUI() throws DAOException
    {
        logger.debug("loadAllPageRUI()");
        String sqlCode = "rightmanager.RightDAO.loadAllPageRUI().SELECT";
        this.pageURIMap = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next())
            {
                PageURIVO page = this.getPageURIVO(rs);
                this.pageURIMap.put(page.getPageURI(), page);
            }
        }
        catch(SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * �����ݿ����������е�Ȩ��RightVO
     */
    private void loadAllRight() throws DAOException
    {
        logger.debug("loadAllRight()");
        String sqlCode = "rightmanager.RightDAO.loadAllRight().SELECT";
        this.rightMap = new HashMap();
        //�������츴��Ȩ�޵���ʱmap
        HashMap tmpParentMap = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next())
            {
                RightVO right = null;
                int levels = rs.getInt("levels");
                String rightID = rs.getString("RIGHTID");
                if(levels > 0)
                {
                    //�Ǹ�������
                    right = new CompositeRightVO();
                    //����ʱmapȡ�����и���Ȩ�޵�����Ȩ��id
                    List rightOfParent = (List) tmpParentMap.get(rightID);
                    //�������Ѿ�����Ȩ��map��ȡ������Ȩ�ޣ������õ�����Ȩ����
                    List rightListOfParent = new ArrayList();
                    for(int i = 0; i < rightOfParent.size(); i++)
                    {
                        rightListOfParent.add(
                            this.rightMap.get(rightOfParent.get(i))) ;
                    }
                    ((CompositeRightVO) right).setRightList(rightListOfParent);
                }
                else
                {
                    //���Ǹ�������
                    right = new RightVO();
                }

                //������Ȩ���и�Ȩ�ޣ�����ĳ������Ȩ�ޣ�
                //�����Ȩ�޵�id���浽��ʱmap�У��ȵ�������Ȩ��ʱ�Ϳ���ȡ������
                String parentID = rs.getString("PARENTID");
                if(parentID != null && parentID.trim().equals(""))
                {
                    parentID = null;
                }
                if(parentID != null)
                {
                    List rightOfParent = (List) tmpParentMap.get(parentID);
                    if(rightOfParent == null)
                    {
                        rightOfParent = new ArrayList();
                        tmpParentMap.put(parentID, rightOfParent);
                    }
                    rightOfParent.add(rightID);
                }

                right.setRightID(rightID) ;
                right.setName(rs.getString("NAME")) ;
                right.setDesc(rs.getString("DESCS")) ;
                right.setParentID(parentID) ;
                this.rightMap.put(right.getRightID(), right) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * ��ȡϵͳ����Ȩ���б�������վ��Ŀ¼Ȩ�ޡ�
     * @return list,Ȩ���б�
     */
    final List getAllRight ()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRight") ;
        }
        List rightList = new ArrayList();
        rightList.addAll(this.rightMap.values());
        return rightList ;
    }

    /**
     * ͨ��Ȩ��id��ȡȨ����Ϣ��
     * @param rightID String Ȩ��id
     * @return RightVO Ȩ����Ϣ
     */
    final RightVO getRightVOByID(String rightID)
    {
        return (RightVO) this.rightMap.get(rightID);
    }

    /**
     * �Ӽ�¼����ȡPageURIVO����
     * @param rs ResultSet ��¼��
     * @return PageURIVO PageURIVO����
     * @throws SQLException
     */
    private PageURIVO getPageURIVO(ResultSet rs) throws SQLException
    {
        PageURIVO page = new PageURIVO();
        page.setRightID(rs.getString("RIGHTID")) ;
        page.setPageURI(rs.getString("PAGEURI")) ;
        page.setDesc(rs.getString("DESCS")) ;
        return page;
    }
}
