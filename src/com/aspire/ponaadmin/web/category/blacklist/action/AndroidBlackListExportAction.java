package com.aspire.ponaadmin.web.category.blacklist.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;

/**
 * <p>
 * 黑名单榜单批量导出Action
 * </p>
 * <p>
 */
public class AndroidBlackListExportAction extends BaseAction{

	/**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(AndroidBlackListExportAction.class);

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
    	if (log.isDebugEnabled())
		{
			log.debug("excel黑名单榜单元数据全量导出开始.......");
		}
		
		String actionType = "全量导出黑名单榜单元数据";
		boolean actionResult = true;
		String actionDesc = "全量导出黑名单榜单元数据成功";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_tag_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			AndroidBlackListBO.getInstance().exportTagData(wwb);
			//TagManagerBO.getInstance().exportALLTagData(wwb);
		}
		catch (Exception e)
		{
			log.error(e);
			
            // 写操作日志
			actionResult = false;
			actionDesc = "全量导出黑名单榜单元数据出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
		}
		finally
		{
			try
			{
				if (wwb != null)
				{
					wwb.write();
					wwb.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		response.setHeader("Content-disposition", "attachment;filename="
				+ excelName);
		response.setContentType("application/msexcel");
		try
		{
			FileInputStream fileInputStream = new FileInputStream(excelName);
			OutputStream out = response.getOutputStream();
			int i = 0;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			File file = new File(excelName);
			file.delete();
		}
		catch (FileNotFoundException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return null;
        
    }
}
