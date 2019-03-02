package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;
import java.util.Map;

/**
 * �������ڵ㼯�ϵĵ����ɽӿ�
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

public interface TreeNodesGenerate
{
    
    /**
     * �ؼ��֣�key
     */    
    static final public String PARA_KEY = "key";
    
    /**
     * ϵͳ�ش�����
     * @param map
     */
    public void setParameters(Map map);

    /**
     * ȡ���ڵ㼯��
     * @return
     */
    public ArrayList getTreeNodeCollection();
    
    /**
     * ȡ����չURL�ַ���
     * ��ʽΪ keyStr=Valuestr&....
     * һ�Ӽ�ֵ֮����"&"����
     *
     * @return
     * @param treeNode 
     */
    public String getTreeUrlParaExtendString(TreeNode treeNode);
}
