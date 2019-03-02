package com.aspire.ponaadmin.web.tree;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

/**
 * �ڵ���XML�����ļ�
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

public class DataTreeGenerate
{

	/**
	 * Automatically generated method: DataTreeGenerate
	 */
	private DataTreeGenerate () {

	}

    /**
     * �ؼ��֣�����
     */
    public static final String KEY_TYPE = "type";

    /**
     * �ؼ��֣�url
     */
    public static final String KEY_PORTALURL = "portalurl";

    /**
     * �ؼ��֣���
     */
    public static final String KEY_CLASS = "class";

    /**
     * ����XML�ĵ�
     * @param out �����
     * @param generate ������
     * @param type ����
     */
    public static void generateXML(PrintWriter out, TreeNodesGenerate generate, String type)
    {
        //XMLͷ�ļ�
        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        out.println("<tree>");

        Collection childs = generate.getTreeNodeCollection();
        if ((childs != null) && (childs.size() > 0))
        {
            Iterator oiTree = childs.iterator();
            while (oiTree.hasNext())
            {
                TreeNode childModel = ( TreeNode) oiTree.next();
                DataTreeGenerate.generateXMLDownContent(out, generate, type, childModel, " ");
            }
        }
        //XML��β��ʶ
        out.println("</tree>");
    }

    /**
     * ���µݹ����XML�ĵ�
     * @param out �����
     * @param generate ������
     * @param type ����
     * @param nodeModel �ڵ�ģ��
     * @param place �ո�
     */
    private static void generateXMLDownContent(PrintWriter out, TreeNodesGenerate generate, 
    		       String type, TreeNode nodeModel, String place)
    {

        //����XML�ڵ�
        StringBuffer buffer = new StringBuffer();
        //��ʽ����
        String noteStr = place + place;
        buffer.append(noteStr);
        buffer.append("<tree ");
        buffer.append("key=\""+nodeModel.getTreeKey()+"\" ");
        buffer.append("folder=\""+(nodeModel.isTreeFolder() ? "1":"0")+"\" ");
        buffer.append("text=\""+nodeModel.getTreeName()+"\" ");
        buffer.append("action=\"javascript:treeControl()\" ");
        buffer.append("ext=\""+nodeModel.getExtendFlag()+"\" ");
        //if(nodeModel.isLoad()) {
        //������ļ���,���Զ�����
        if(nodeModel.isTreeFolder()) {
            buffer.append("src=\"" + TreeResource.getTreeCode(type, DataTreeGenerate.KEY_PORTALURL)+
                          "?type="+TreeResource.getTreeCode(type, DataTreeGenerate.KEY_TYPE)+
                          "&amp;key="+nodeModel.getTreeKey()+
                          formatExtendURLString(generate, nodeModel)+"\" ");
        }
        //}
        buffer.append(" />");
        out.println(buffer.toString());
    }

    /**
     * ȡ���ڵ����չurl
     * ���ֻ�����ļ��нڵ�
     * @param generate
     * @param nodeModel
     * @return
     */
    private static String formatExtendURLString(TreeNodesGenerate generate,
                                                TreeNode nodeModel) {
        String extUrl = generate.getTreeUrlParaExtendString(nodeModel);
        if(extUrl != null && !(extUrl.trim().equals(""))) {
            extUrl = "&"+extUrl;
            extUrl = extUrl.replaceAll("&", "&amp;");
            return extUrl;
        }else {
            return "";
        }
    }
}
