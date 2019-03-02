package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;
import com.enterprisedt.net.ftp.FTPException;

public class WapCategoryExportAction extends BaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		response.setContentType("text/plain;charset=GBK");
		OutputStream out = null;
		ExportData res = null;
		try {
			out = response.getOutputStream();
			res = WapCategoryExportBo.export();
			out.write(res.getMsg().getBytes());
		} catch (IOException e1) {
			return null;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

}
