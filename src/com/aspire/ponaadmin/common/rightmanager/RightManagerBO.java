package com.aspire.ponaadmin.common.rightmanager ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;

/**
 * <p>Ȩ�޹������BO��</p>
 * <p>Ȩ�޹������BO�࣬�ṩ�ⲿ���õ�ȫ���ӿ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightManagerBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(RightManagerBO.class);

    /**
     * ��������AbstractRightModelģ�͵�map��key��ģ�͵�����
     */
    private HashMap rightModelMap = new HashMap();

    /**
     * singletonģʽ��ʵ��
     */
    private static RightManagerBO instance = new RightManagerBO();

    /**
     * ���췽������singletonģʽ����
     */
    private RightManagerBO ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static RightManagerBO getInstance()
    {
        return instance;
    }

    /**
     * ע��Ȩ��ģ�ͣ�Ӧ���ڳ�ʼ����ʱ����á�
     * @param model AbstractRightModel
     */
    public void registerRightModel(AbstractRightModel model)
    {
        logger.debug("registerRightModel(" + model.getType() + ")") ;
        //������Ҫͬ�������ǿ��ǵ���Щ�����ڳ�ʼ��ʱ����õģ����Բ���ͬ����
        this.rightModelMap.put(new Integer(model.getType()), model);
    }

    /**
     * ��ӽ�ɫ�����ݿ�
     * @param role RoleVO��Ҫ��ӵĽ�ɫ
     * @throws BOException
     */

    public void addRole (RoleVO role)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addRole(" + role + ")") ;
        }
        if (role == null)
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            String newName = role.getName();
            List list = getRoleByName(newName);
            for(int i = 0; i < list.size(); i++)
            {
                RoleVO vo = (RoleVO) list.get(i);
                if(newName.equals(vo.getName()))
                {
                    throw new  BOException("��ɫ�Ѿ�����", RightManagerConstant.ROLE_NAME_EXIST);
                }

            }
            RoleDAO.addRole(role) ;

        }
        catch (DAOException e)
        {
            throw new BOException("addRole error", e) ;
        }
    }

    /**
     * ɾ����ɫ
     * @param roleID String Ҫɾ���Ľ�ɫID
     * @return int ���ؽ���룬��ErrorCode�࣬SUCC�ɹ���FAIL�ڲ�����ROLE_USED��ɫ��ʹ����
     * @throws BOException
     */
    public int delRole (String roleID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delRole(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            //�����ɫ��ʹ���ˣ�����ɾ����
            if(UserRoleDAO.getInstance().getRoleUsers(roleID).size() != 0)
            {
                return RightManagerConstant.ROLE_USED;
            }
            RoleDAO.delRole(roleID) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
            return RightManagerConstant.FAIL;
        }
        return RightManagerConstant.SUCC;
    }

    /**
     * ��ȡϵͳ���н�ɫ�б�
     * @return List ��ɫ�б�
     * @throws BOException
     */
    public List getAllRole ()
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRole()") ;
        }
        try
        {
            return RoleDAO.getAllRole() ;
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRole error", e) ;
        }
    }


    /**
     * ��ȡϵͳ���н�ɫ�б�ʵ�ַ�ҳ
     * @param page PageResult ��ҳ����
     * @param name String ��ɫ����
     * @throws BOException
     */
    public void getAllRole (PageResult page, String name)
        throws BOException
    {
        logger.debug("getAllRole(" + page + ")") ;
        if(page == null)
        {
                throw new BOException("page is null!", RightManagerConstant.INVALID_PARA);
        }
        try
        {
            RoleDAO.getAllRole(page, name) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRole error", e) ;
        }
    }


    /**
     * ������ɫ�ķ�����ʵ�ַ�ҳ
     * @param page PageResult����ҳ��
     * @param name String����������
     * @param desc String����������
     * @param rightType int��Ȩ������
     * @param rightID String��Ȩ��id
     * @throws BOException
     */
    public void searchRole (PageResult page, String name, String desc,
                            int rightType, String rightID)
        throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("searchRole(" + page + "," + name + "," + desc + "," +
                         rightType + "," + rightID + ")") ;
        }
        if(page == null)
        {
                throw new BOException("page is null!", RightManagerConstant.INVALID_PARA);
        }
        try
        {
            RoleDAO.searchRole(page, name,desc,rightType,rightID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("searchRole error", e) ;
        }
    }

    /**
     * ����ID��ȡ��Ӧ�Ľ�ɫ
     * @param roleID String,��ɫID
     * @return RoleVO
     * @throws BOException
     */
    public RoleVO getRoleByID (String roleID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleByID(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RoleDAO.getRoleByID(roleID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleByID error", e) ;
        }
    }

    /**
     * ���ݽ�ɫ���Ʋ���
     * @param name String,��ɫ����
     * @return list
     * @throws BOException
     */
    public List getRoleByName (String name)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleByName(" + name + ")") ;
        }
        if (name == null || name.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RoleDAO.getRoleByName(name) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleByName error", e) ;
        }
    }

    /**
     * �޸Ľ�ɫ��Ϣ
     * @param role RoleVO��Ҫ���µĽ�ɫ��Ϣ
     * @throws BOException
     */
    public void modRoleInfo (RoleVO role)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("modRoleInfo(" + role + ")") ;
        }
        if ((role == null) || (role.getRoleID() == null) || role.getRoleID().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            String newName = role.getName();
            String id = role.getRoleID();
            List list = getRoleByName(newName);
            for(int i = 0; i < list.size(); i++)
            {
                RoleVO vo = (RoleVO) list.get(i);
                if(newName.equals(vo.getName()) && !id.equals(vo.getRoleID()))
                {
                    throw new  BOException("��ɫ�Ѿ�����", RightManagerConstant.ROLE_NAME_EXIST);
                }
            }
            RoleDAO.modRole(role) ;
       }
        catch (DAOException e)
        {
            throw new BOException("modRole error", e) ;
        }

    }

    /**
     * �����û����еĽ�ɫ
     * @param userID String,�û�ID
     * @param roleIDList List,��ɫID�б�
     * @throws BOException
     */
    public void setUserRoles (String userID, List roleIDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setUserRoles(" + userID + roleIDList + ")") ;
        }
        if ((userID == null) || userID.trim().equals("") || (roleIDList == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            UserRoleDAO.getInstance().setUserRoles(userID, roleIDList) ;
        }
        catch (DAOException e)
        {
            throw new BOException("setUserRoles error", e) ;
        }
    }

    /**
     * ��ȡ�û����еĽ�ɫ
     * @param userID String,�û�ID
     * @return List
     * @throws BOException
     */
    public List getUserRoles (String userID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserRoles(" + userID + ")") ;
        }
        if (userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return UserRoleDAO.getInstance().getUserRoles(userID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getUserRoles error", e) ;
        }
    }

    /**
     * ��������ĳ����ɫ��һ���û�
     * @param roleID String,��ɫID
     * @param userIDList List,�û�ID�б�
     * @throws BOException
     */
    public void setRoleUsers (String roleID, List userIDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setRoleUsers(" + roleID + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (userIDList == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            UserRoleDAO.getInstance().setRoleUsers(roleID, userIDList) ;
        }
        catch (DAOException e)
        {
            throw new BOException("setRoleUsers error", e) ;
        }
    }

    /**
     * �޸�����ĳ����ɫ���û�������n����ȥ��n����
     * @param roleID String����ɫID
     * @param newUserIDs String[]�����ӵ�n��
     * @param delUserIDs String[]��ȥ����n��
     * @throws BOException
     */
    public void modRoleUsers (String roleID, String[] newUserIDs,
                              String[] delUserIDs)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("modRoleUsers(" + roleID + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (newUserIDs == null) ||
            (delUserIDs == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        try
        {
            UserRoleDAO.getInstance().modRoleUsers(roleID, newUserIDs,
                                                   delUserIDs) ;
        }
        catch (DAOException e)
        {
            throw new BOException("modRoleUsers error", e) ;
        }
    }

    /**
     * ��ȡ����ĳ����ɫ��һ���û�
     * @param roleID String,��ɫID
     * @return List
     * @throws BOException
     */
    public List getRoleUsers (String roleID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleUsers(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return UserRoleDAO.getInstance().getRoleUsers(roleID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleUsers error", e) ;
        }
    }

    /**
     * ��ȡ��ɫ���е�Ȩ��
     * @param roleID String,��ɫID
     * @param type int��Ȩ������
     * @return List
     * @throws BOException
     */
    public List getRoleRights (String roleID, int type)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleRights(" + roleID + "," + type + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        List rightList = null;
        try
        {
            AbstractRightModel model = (AbstractRightModel) this.rightModelMap.
                get(new Integer(type)) ;
            //���û��֧�ֵ�Ȩ��ģ�ͣ����׳��쳣
            if(model == null)
            {
                throw new BOException("right type [" + type + "] not support!");
            }
            rightList = model.getRoleRight(roleID);
        }
        catch (Exception e)
        {
            throw new BOException("getRoleRights error", e);
        }
        return rightList;
    }

    /**
     * ���ý�ɫ���е�Ȩ��
     * @param roleID String,��ɫID
     * @param type int,Ȩ������1������Ȩ��2��վ��Ŀ¼Ȩ�ޡ�
     * @param IDList List,Ȩ���б����汣��Ȩ��ID�����typeΪ1������rightID�б����typeΪ2������nodeID�б�
     * @throws BOException
     */
    public void setRoleRights (String roleID, int type, List IDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setRoleRights(" + roleID + type + IDList + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (IDList == null) ||
            ((type != 1) && (type != 2)))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        AbstractRightModel model = ( AbstractRightModel ) this.rightModelMap.get(new Integer(type));
        // ���û��֧�ֵ�Ȩ��ģ�ͣ����׳��쳣
        if (model == null)
        {
            throw new BOException("right type [" + type + "] not support!");
        }
        model.setRoleRight(roleID, IDList);
    }

    /**
     * ��ȡһ��http����uri��Ӧҳ�������ڵ�Ȩ��
     * 
     * @param pageURI http����uri
     * @return ҳ���Ӧ��Ȩ��
     * @throws BOException
     */
    public RightVO getRightOfPageURI(String pageURI) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightOfPageURI(" + pageURI + ")") ;
        }

        if (pageURI == null || pageURI.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RightDAO.getInstance().getRightOfPageURI(pageURI);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ȡһ��http����uri��Ӧҳ�������ڵ�Ȩ��ʧ�ܣ�",e);
        }
    }

    /**
     * ��ȡϵͳ����Ȩ���б�������վ��Ŀ¼Ȩ�ޡ�
     * @return list,Ȩ���б��Ҳ���Ϊnull
     * @throws BOException
     */

    public List getAllRight() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRights") ;
        }
        try
        {
            return RightDAO.getInstance().getAllRight();
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRights error", e) ;
        }
    }

    /**
     * ����Ȩ��id��ȡȨ�޶���
     * @param rightID String Ȩ��id
     * @return RightVO Ȩ�޶���
     * @throws BOException
     */
    public RightVO getRightByID(String rightID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightByID("+rightID+")") ;
        }
        if(rightID == null || rightID.trim().equals(""))
        {
            throw new BOException("invalid parameter!",
                                  RightManagerConstant.INVALID_PARA) ;
        }
        try
        {
            return RightDAO.getInstance().getRightVOByID(rightID);
        }
        catch(Exception e)
        {
            throw new BOException("getRightByID failed", e);
        }
    }

    /**
     * ��ȡ�û�������Ȩ��
     * @param userID String �û��ʺ�ID
     * @return HashMap �û���Ȩ��hash��
     * @throws BOException
     */
    public List getUserRights(String userID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserRights("+userID+")") ;
        }

        List roleList = this.getUserRoles(userID);
        List allRightList = new ArrayList();

        //ȡ�����е�Ȩ��ģ��AbstractRightModel
        Object[] models = this.rightModelMap.values().toArray();

        for(int i = 0; i < roleList.size(); i++)
        {
            RoleVO role = (RoleVO) roleList.get(i);
            for(int k = 0; k < models.length; k++)
            {
                int rightType = ((AbstractRightModel) models[k]).getType();
                List rightList = this.getRoleRights(role.getRoleID(), rightType);
                allRightList.addAll(rightList);
            }
        }
        return allRightList;
    }

    /**
     * ����û��Ƿ�ӵ�н���ĳ��������Ȩ��
     * @param userSession UserSessionVO �û���Ϣ
     * @param rightID String Ȩ��id
     * @return int ����ֵ�����������μ�RightManagerConstant��
     * @throws BOException
     * @see com.aspire.ponaadmin.common.rightmanager.RightManagerConstant
     */
    public int checkUserRight(UserSessionVO userSession, String rightID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("checkUserRight(" + rightID + ")") ;
        }
        //�ȼ���Ƿ���Ի�ȡ���û�Ȩ����Ϣ
        if ((userSession == null) || (userSession.getRights() == null))
        {
            //û���û�Ȩ����Ϣ
            return RightManagerConstant.RIGHTCHECK_NO_USERINFO;
        }

        //�ж��Ƿ����Ȩ��
        List rightList = userSession.getRights();
        for(int i = 0; i < rightList.size(); i++)
        {
            RightVO right = (RightVO) rightList.get(i);
            if(right.checkRight(rightID) == RightManagerConstant.RIGHTCHECK_PASSED)
            {
                return RightManagerConstant.RIGHTCHECK_PASSED;
            }
        }

        //�ߵ�����˵��û��Ȩ��
        return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
    }
}
