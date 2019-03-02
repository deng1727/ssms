package com.aspire.ponaadmin.web.tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
//import com.aspire.ponaadmin.web.website.WebSiteMgr;

/**
 * ��̬�������������
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

public class DataTreeG extends HttpServlet
{

    /**
     * ��־��¼����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataTreeG.class);

    /**
     * XMLͷ�ļ�
     */
    static final public String CONTENT_TYPE = "text/xml; charset=GBK";

    /**
     * ��������
     */
    static final public String PARA_TYPE = "type";

    /**
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException 
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                    IOException
    {

        response.setContentType(CONTENT_TYPE);
        String type = request.getParameter(DataTreeG.PARA_TYPE);

        String className = TreeResource.getTreeCode(type, DataTreeGenerate.KEY_CLASS);
        TreeNodesGenerate generate = null;
        try
        {
            generate = (TreeNodesGenerate) Class.forName(className).newInstance();
            generate.setParameters(this.getParamenters(request));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e);
        }

        PrintWriter out = response.getWriter();
        DataTreeGenerate.generateXML(out, generate, type);
    }

    /**
     * ��װ�����еĲ���
     * @param request
     * @return
     */
    private HashMap getParamenters(HttpServletRequest request) {
        HashMap map = new  HashMap();
        Object obj = null;
        for(Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            obj = enumeration.nextElement();
            map.put(obj, request.getParameter((String) obj));
        }
        return map;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {

//        try
//        {
//            InitorForTest.init();
//
//            WebSiteMgr mgr = WebSiteMgr.getInstance();
//            mgr.init();
//
//            String className=TreeResourceMgr.getTreeCode("site",DataTreeGenerate.KEY_CLASS);
//            TreeNodesGenerate generate=null;
//            try
//            {
//                generate=(TreeNodesGenerate)Class.forName(className).newInstance();
//                HashMap map=new HashMap();
//                map.put("type","site");
//                map.put("key","100");
//                generate.setParameters(map);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//                logger.error(e);
//            }
//
//            PrintWriter write = new PrintWriter(System.out);
//            write.println("begin test \n");
//            DataTreeGenerate.generateXML(write,generate,"site");
//            write.flush();
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
    }

}
