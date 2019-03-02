package com.aspire.ponaadmin.web.blacklist.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.blacklist.biz.BlackListBo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * 导入黑名单
 * 
 * @author x_zhailiqing
 * 
 */
public class BlackImportAction extends BaseAction
{
    private static final JLogger LOG = LoggerFactory.getLogger(BlackImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("importBlackList() is start");
        }
        String forward = Constants.FORWARD_COMMON_SUCCESS;

        DataImportForm iForm = ( DataImportForm ) form;
        FormFile dataFile = iForm.getDataFile();
        List err = new ArrayList();
        
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"txt"}))
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(forward);
        }
        
        try
        {
            int successCount = BlackListBo.getInstance().importBlack(dataFile,
                                                                     err);
            StringBuffer message = new StringBuffer();
            message.append("数据导入成功，共成功导入" + successCount + "条数据<br />");
            LOG.info(message.toString());
            message.append("导入失败内容：");
            for (int i = 0; i < err.size(); i++)
            {
                message.append(( String ) err.get(i) + "<br />");
            }
            this.actionLog(request,
                           "黑名单数据导入",
                           dataFile.getFileName(),
                           true,
                           "共导入了" + successCount + "条数据");
            this.saveMessagesValue(request, message.toString());

        }
        catch (Exception e)
        {
            LOG.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "数据导入失败");
        }
        request.setAttribute(Constants.PARA_GOURL, "black.do");
        return mapping.findForward(forward);
    }

}
