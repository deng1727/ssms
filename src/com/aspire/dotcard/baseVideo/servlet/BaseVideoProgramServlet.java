package com.aspire.dotcard.baseVideo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.util.HttpUtil;

/**
 * ��Ŀ�¼�֪ͨ(��Ʒ���ص���) �ӿ����� ����ʹ��HTTP GET����������XML��ʽ����Ӧ���ݡ� ��Ƶ���ص���MM���ؽ�Ŀ�¼�֪ͨ��
 * http://localhost:7001/ponaadmin/video/deleteProgram?programid=1111
 */
public class BaseVideoProgramServlet extends HttpServlet
{

    private static final long serialVersionUID = -5226172620353411362L;

    private static final JLogger LOG = LoggerFactory.getLogger(BaseVideoProgramServlet.class);

    private String[] urlIPConfig;

    private String[] urlPortalConfig;

    private String[] phoneList;

    
    private String wapProcedureName;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String requestIP = request.getRemoteAddr();
        String programId = request.getParameter("programid");
        String message = "";
        
        boolean isAssignationIP = false;
        boolean delProgram = false;

        // �жϽ���IP�Ƿ�Ϊָ�������е�IP
        for (int i = 0; i < urlIPConfig.length; i++)
        {
            String tempIP = urlIPConfig[i];
            if (requestIP.indexOf(tempIP) != -1)
            {
                isAssignationIP = true;
                break;
            }
        }

        // �������ָ��IP,�ӿڲ�����,����ʧ��
        if (!isAssignationIP)
        {
            message = "�����IP��" + requestIP + " ��ָ������IP. ";
            if(LOG.isDebugEnabled()){
                LOG.debug(message);
            }
            // ������Ӧxml
            sendMessage(message, response);
            return;
        }

        // ����]�н�ĿprogramId,�ӿڲ�����,����ʧ��
        if (StringUtils.isBlank(programId))
        {
            message = "��ǰ����û�д�����������IPΪ��" + requestIP;
            if(LOG.isDebugEnabled()){
                LOG.debug(message);
            }
            // ������Ӧxml
            sendMessage(message, response);
            return;
        }

        // ɾ���������progranmID������
        delProgram = delProgramById(programId);
        
        //���ɾ���ɹ�
        if (delProgram)
        {
            // ֪ͨ�����Ż�
            for (int i = 0; i < urlPortalConfig.length; i++)
            {
                String tempPortalURL = urlPortalConfig[i];
                if (StringUtils.isNotBlank(tempPortalURL))
                {
                    tempPortalURL = tempPortalURL + "?programid=" + programId;
                   
                    sendPortalIP(tempPortalURL);
                }
            }

            
            if (StringUtils.isNotBlank(wapProcedureName))
            {
                callFunction(wapProcedureName, programId);
            }
            
            message = "";
            
        }
        else
        {// ���ɾ��ʧ�ܣ�����֪ͨ�����Ա
            message = "IP:" + requestIP + " ��������ɾ����Ŀ:" + programId + "ִ��ʧ��!";
            if (LOG.isDebugEnabled())
            {
                LOG.debug(message);
            }
            sendMsg(message);
        }
        
        // ������Ӧxml
        sendMessage(message, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.doGet(request, response);
    }

    /**
     * �����û�������Ӧ
     * 
     * @param delProgram
     *            ɾ���Ƿ�ɹ�
     */
    private void sendMessage(String message, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/xml; charset=UTF-8");
        // response.setHeader("Cache-Control", "no-cache");
        // response.setHeader("Pragma", "no-cache");
        // response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();

        StringBuilder xml = new StringBuilder(200);
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<return>");

        // ������Ҫ���͵�xml
        xml.append("<messagecode>");
        if (StringUtils.isBlank(message))
        {
            xml.append("000000");
        }
        else
        {
            xml.append("999999");
        }
        xml.append("</messagecode>");
        xml.append("<messageinfo>");
        if (StringUtils.isBlank(message))
        {
            xml.append("�����ɹ�");
        }
        else
        {
            xml.append(message);
        }
        xml.append("</messageinfo>");
        xml.append("</return>");
        
//        if (LOG.isDebugEnabled())
//        {
//            LOG.debug("�����û�������Ӧ��" + xml.toString());
//        }
        
        out.print(xml.toString());

        
        xml.delete(0, xml.length()-1);
        xml = null;
        out.close();
        
    }

    /**
     * ����ɾ��ָ����Ŀ���顣���а���ֱ����Ŀ������Ʒ��Ϣ����Ŀ���顣
     * 
     * @param programId
     */
    private boolean delProgramById(String programId)
    {
        try
        {
            BaseVideoFileDAO.getInstance().delProgramById(programId);
            return true;
        }
        catch (BOException e)
        {
            LOG.error("ִ��ɾ��ָ����Ŀ����ʱ�������ݿ��쳣������" + e);
            return false;
        }
    }

    /**
     * ֪ͨ�Ż�����
     * 
     * @param url
     *            �Ż�url
     * @return
     */
    private void sendPortalIP(String url)
    {
        int code = 0;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("֪ͨ�Ż���ʼ" );
        }
        
        try
        {
            code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
          
        }
        catch (Exception e)
        {
            LOG.error("֪ͨ�Ż���������ʧ��");
        }

        if (code == 200)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("֪ͨ�Ż���url=" + url + " �ɹ�. ");
            }
        }
        else
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("֪ͨ�Ż���url=" + url + " ʧ��. ");
            }
        }
    }

    /**
     * ��ɾ��ָ����Ŀʧ��ʱ������������֪�������Ա
     * 
     * @param content
     */
    private void sendMsg(String content)
    {
        DataSyncDAO dao = DataSyncDAO.getInstance();
        for (int i = 0; i < phoneList.length; i++)
        {
            try
            {
                dao.sendMsg(phoneList[i], content);// ���Ͷ���
            }
            catch (DAOException e)
            {
                LOG.error("ɾ��ָ����Ŀ�У����ֻ�" + phoneList[i] + "���Ͷ���ʧ�ܣ�" + e);
            }
        }
    }

    
    /**
     * wapִ�к���
     * 
     * @param functionName��programId
     */
    private int callFunction(String functionName, String programId)
    {
        Connection conn = null;
        CallableStatement cs = null;
        String errorMessage;
        int status = -1;
        // ִ�д洢���̡�
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼִ�к�����" + functionName);
        }

        try
        {
            conn = DB.getInstance().getConnection();
            if (StringUtils.isNotBlank(programId))
            {
                cs = conn.prepareCall("{?="+functionName+"}");
                cs.setString(2, programId);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                status = cs.getInt(1); //��ȡ�������ؽ��
                
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("���ú�����" + functionName+" �ɹ���ִ��״̬��" + status);
                }
            }
        }
        catch (Exception e)
        {
            status = -1;
            errorMessage = "���ú���" + functionName + "ʧ��,ִ��״̬��" + status + ",��������ϵ����Ա������";
            LOG.error(errorMessage, e);
            
        }
        finally
        {
            if (cs != null)
            {
                try
                {
                    cs.close();
                }
                catch (Exception ex)
                {
                    LOG.error("�ر�CallableStatement=ʧ��", ex);
                }
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    LOG.error("�ر�conn=ʧ��", e);
                }
            }
        }
        return status;
    }

    /**
     * ��ʼ��,����������Ϣ
     */
    public void init(ServletConfig config) throws ServletException
    {
        // ������ʵ�Ip
        urlIPConfig = BaseVideoConfig.ipListConfig.split("\\|");
        // ֪ͨ�Ż���ַ
        urlPortalConfig = BaseVideoConfig.urlPortalConfig.split("\\|");
        // ����֪ͨ����
        phoneList = BaseVideoConfig.phoneList.split("\\|");

        //ִ��wap�洢����
        wapProcedureName = BaseVideoConfig.wapProcedureName;
        
    }

}
