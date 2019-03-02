package com.aspire.ponaadmin.web.tree;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

/**
 * 节点树XML构造文件
 *
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 *
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *
 * <p>Company: 卓望数码技术（深圳）有限公司</p>
 *
 *  @author    胡春雨
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
     * 关键字：类型
     */
    public static final String KEY_TYPE = "type";

    /**
     * 关键字：url
     */
    public static final String KEY_PORTALURL = "portalurl";

    /**
     * 关键字：类
     */
    public static final String KEY_CLASS = "class";

    /**
     * 产生XML文档
     * @param out 输出流
     * @param generate 构造器
     * @param type 类型
     */
    public static void generateXML(PrintWriter out, TreeNodesGenerate generate, String type)
    {
        //XML头文件
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
        //XML结尾标识
        out.println("</tree>");
    }

    /**
     * 向下递归产生XML文档
     * @param out 输出流
     * @param generate 构造器
     * @param type 类型
     * @param nodeModel 节点模型
     * @param place 空格
     */
    private static void generateXMLDownContent(PrintWriter out, TreeNodesGenerate generate, 
    		       String type, TreeNode nodeModel, String place)
    {

        //构建XML节点
        StringBuffer buffer = new StringBuffer();
        //格式控制
        String noteStr = place + place;
        buffer.append(noteStr);
        buffer.append("<tree ");
        buffer.append("key=\""+nodeModel.getTreeKey()+"\" ");
        buffer.append("folder=\""+(nodeModel.isTreeFolder() ? "1":"0")+"\" ");
        buffer.append("text=\""+nodeModel.getTreeName()+"\" ");
        buffer.append("action=\"javascript:treeControl()\" ");
        buffer.append("ext=\""+nodeModel.getExtendFlag()+"\" ");
        //if(nodeModel.isLoad()) {
        //如果是文件夹,择自动加载
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
     * 取出节点的扩展url
     * 这个只用在文件夹节点
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
