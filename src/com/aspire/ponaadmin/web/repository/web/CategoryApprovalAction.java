package com.aspire.ponaadmin.web.repository.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryOperationBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * ���ܷ�������ύ����
 * @author shiyangwang
 *
 */
public class CategoryApprovalAction extends BaseAction{
	
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		
		String operation = this.getParameter(request, "operation");
		//����״̬   0�༭ �� 1���� ��  2�����  ��  3��˲�ͨ��
		String status = this.getParameter(request, "status");
		
		String forward = "commonSuccess";
        String categoryId = request.getParameter("categoryId");
        String backURL=this.getParameter(request, "backURL");
        
        
        Map<String,Object> map = new HashMap<String,Object>();
       
        //����MM����
        map.put("operation", "10");
       
        map.put("operationobj", "����");
        
        try {
        	if("1".equals(operation)){//���ܷ��������
        		CategoryOperationBO.getInstance().operateInfoforCategory(request, categoryId, status, operation, map);
        	}else if("2".equals(operation)){//������Ʒ������
        		CategoryOperationBO.getInstance().operateInfoforGoods(request, categoryId, status, operation, map);
        	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("�����쳣");
		}
        this.saveMessages(request, "RESOURCE_CATE_RESULT_004");
        
        log.debug("CategoryApprovalAction.parameters:" + categoryId);
        
       
        if("1".equals(operation)&&"2".equals(status)){
        	backURL =  "../../web/resourcemgr/categoryInfo.do?categoryID="
                + categoryId ;
        }else if(!"2".equals(status)){
        	backURL="../../web/resourcemgr/categoryApprovalList.do?operation="+operation+"&approvalStatus=2";
        }
        request.setAttribute(Constants.PARA_GOURL,
                             backURL);
        log.debug("CategoryApprovalAction.gourl=="
                  + request.getContextPath()
                  + backURL);

        return mapping.findForward(forward);
	}

}
