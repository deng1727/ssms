/*
 * �ļ�����CategoryDeviceAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.repository.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class CategoryDeviceAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(CategoryDeviceAction.class) ;
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }
        String forward = "device";
        
        // �������л�ȡ��������
        String deviceId = this.getParameter(request, "deviceId").trim();
        String deviceName = this.getParameter(request, "deviceName").trim();
        String brand = this.getParameter(request, "brand").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�������ͱ���Ϣ
        if(isFirst.equals("1"))//��һ�β���Ҫ��ѯ
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "������������ѯ");
        }else
        {
            DeviceVO device = new DeviceVO();
            device.setDeviceId(deviceId);
            device.setDeviceName(deviceName);
            device.setBrand(brand);
            
            CategoryDeviceBO.getInstance().queryDeviceList(page, device);
        }
        
        
        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("deviceId", deviceId);
        request.setAttribute("deviceName", deviceName);
        request.setAttribute("brand", brand);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }

}
