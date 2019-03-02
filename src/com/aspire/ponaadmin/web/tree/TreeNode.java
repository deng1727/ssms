package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;

/**
 * 
 * �������ڵ�ӿ�
 * 
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 * 
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * 
 * <p>Company: ׿�����뼼�������ڣ����޹�˾</p>
 * 
 *  @author    ������
 * 
 *  @version   1.0 *
 *
 */

public interface TreeNode
{
    
    /**
     * ȡ��id��ֵ 
     *@return id
     */
    public String getTreeKey();
    
    /**
     * ȡ��isFolder��ֵ 
     *@return isFolder
     */
    public boolean isTreeFolder();

    /**
     * �жϽڵ��Ƿ��Զ���������
     * @return
     */
    public boolean isLoad();

    /**
     * ȡ����չ��־
     * ���Ը��ݴ���չ��־���в���
     * @return
     */
    public String getExtendFlag();

    /**
     * ȡ��name��ֵ 
     *@return name
     */
    public String getTreeName();

    /**
     * ȡ��childs��ֵ 
     *@return childs
     */
    public ArrayList getTreeChilds();
    
    
}
