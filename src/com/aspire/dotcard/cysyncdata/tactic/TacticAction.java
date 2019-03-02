package com.aspire.dotcard.cysyncdata.tactic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * ����ͬ�����Թ���action
 * @author x_liyouli
 *
 */
public class TacticAction extends BaseAction
{
    /**
     * ��־����
     */
    private static JLogger logger = LoggerFactory.getLogger(TacticAction.class);
    
    /**
    *
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return ActionForward
    * @throws BOException
    * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
    */
   public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
       throws BOException
   {
	   if(logger.isDebugEnabled())
	   {
		   logger.debug("TacticAction.doPerform()");
	   }
       
       String action = request.getParameter("action");
       
       ActionForward forward ;
       if("add".equalsIgnoreCase(action))
       {
    	   forward = add(mapping,form,request,response);
       }
       else if("modify".equalsIgnoreCase(action))
       {
    	   forward = modify(mapping,form,request,response);
       }
       else if("delete".equalsIgnoreCase(action))
       {
    	   forward = delete(mapping,form,request,response);
       }
       else if("showTactic".equalsIgnoreCase(action))
       {
    	   forward = queryTactic(mapping,form,request,response);
       }
       else
       {
    	   //�б�ҳ��
    	   forward = queryAll(mapping,form,request,response);
       }
       
       return forward;
   }
   
   /**
    * ��ѯĳ�����ܻ����µ�����ͬ������
    *
    * @param maping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws BOException
    */
   private ActionForward queryAll(ActionMapping maping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("queryAll()");
       }
	   String categoryID = request.getParameter("categoryID");
	   String forward = Constants.FORWARD_COMMON_FAILURE;
	   try
	   {
		   List tacticList = new CYTacticBO().queryByCategoryID(categoryID);
		   Category category = ( Category ) Repository.getInstance()
                                                       .getNode(categoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
		   
		   request.setAttribute("tacticList", tacticList);
		   request.setAttribute("category", category);
		   forward = "tacticList";
	   }
	   catch(Exception e)
	   {
		   logger.error(e);
	   }	   
	   
	   return maping.findForward(forward); 
   }
   
   /**
    * ��ѯ��������ͬ������
    *
    * @param maping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws BOException
    */
   private ActionForward queryTactic(ActionMapping maping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("queryTactic()");
       }
	   String forward = Constants.FORWARD_COMMON_FAILURE;
	   int id = Integer.parseInt(request.getParameter("id"));
	   
	   try
	   {
		   CYTacticVO vo = new CYTacticBO().queryByID(id);
		   Category category = ( Category ) Repository.getInstance()
                                                       .getNode(vo.getCategoryID(),
                                                                RepositoryConstants.TYPE_CATEGORY);
		   request.setAttribute("tacticVO", vo);
		   request.setAttribute("category", category);
		   forward = "modify";
	   }
	   catch(Exception e)
	   {
		   logger.error(e);
	   }
	   
	   return maping.findForward(forward); 
   }
   
   /**
    * �������ͬ������
    *
    * @param maping
    * @param form
    * @param request
    * @param response
    * @return
 * @throws BOException 
    * @throws BOException
    */
   private ActionForward add(ActionMapping maping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws BOException
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("add()");
       }
	   String forward = Constants.FORWARD_COMMON_FAILURE;
	   
	   CYTacticVO vo = new CYTacticVO();
	   String categoryID = request.getParameter("categoryID");
	   vo.setCategoryID(categoryID);
	   vo.setContentType(request.getParameter("contentType"));
	   vo.setUmFlag(request.getParameter("umFlag"));
	   vo.setAppCateName(request.getParameter("appCateName"));
	   vo.setContentTag(request.getParameter("contentTag"));
	   int relation = Integer.parseInt(request.getParameter("tagRelation"));
	   vo.setTagRelation(relation);
	   boolean operResult = false;
	   String str = "�������ͬ������";
	   try
	   {
		   new CYTacticBO().addTactic(vo);
		   forward = Constants.FORWARD_COMMON_SUCCESS;		   
		   this.saveMessagesValue(request, "�������ͬ�����Գɹ�!");
		   operResult = true;
		   str = str + "�ɹ���";
	   }
	   catch(Exception e)
	   {
		   logger.error(e);
		   this.saveMessagesValue(request, "�������ͬ������ʧ��!");
		   str = str + "ʧ�ܣ�";
	   }
	   finally
	   {
		   Category category = ( Category ) Repository.getInstance()
                                                       .getNode(vo.getCategoryID(),
                                                                RepositoryConstants.TYPE_CATEGORY);
	        //��¼������־
	        this.actionLog(request, str, categoryID+" "+category.getNamePath(), operResult, "");
	   }
	   request.setAttribute("goURL", "tactic.do?subSystem=ssms&action=list&categoryID=" + categoryID);
	   return maping.findForward(forward); 
   }
   
   /**
    * �޸�����ͬ������
    *
    * @param maping
    * @param form
    * @param request
    * @param response
    * @return
 * @throws BOException 
    * @throws BOException
    */
   private ActionForward modify(ActionMapping maping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws BOException
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("modify()");
       }
	   String forward = Constants.FORWARD_COMMON_FAILURE;
	   
	   CYTacticVO vo = new CYTacticVO();
	   int id = Integer.parseInt(request.getParameter("id"));
	   vo.setId(id);
	   
	   String categoryID = request.getParameter("categoryID");
	   vo.setCategoryID(categoryID);
	   vo.setContentType(request.getParameter("contentType"));
	   vo.setUmFlag(request.getParameter("umFlag"));
       vo.setAppCateName(request.getParameter("appCateName"));
	   vo.setContentTag(request.getParameter("contentTag"));
	   int relation = Integer.parseInt(request.getParameter("tagRelation"));
	   vo.setTagRelation(relation);
	   boolean operResult = false;
	   String str = "�޸�����ͬ������";
	   try
	   {
		   new CYTacticBO().modifyTactic(vo);
		   forward = Constants.FORWARD_COMMON_SUCCESS;		   
		   this.saveMessagesValue(request, "�޸�����ͬ�����Գɹ�!");
		   operResult = true;
		   str = str + "�ɹ���";
	   }
	   catch(Exception e)
	   {
		   logger.error(e);
		   this.saveMessagesValue(request, "�޸�����ͬ������ʧ��!");
		   str = str + "ʧ�ܣ�";
	   }
	   finally
	   {
		   Category category = ( Category ) Repository.getInstance()
                                                       .getNode(vo.getCategoryID(),
                                                                RepositoryConstants.TYPE_CATEGORY);
	        //��¼������־
	        this.actionLog(request, str, categoryID+" "+category.getNamePath(), operResult, "");
	   }
	   request.setAttribute("goURL", "tactic.do?subSystem=ssms&action=list&categoryID=" + categoryID);
	   return maping.findForward(forward); 
   }
   
   /**
    * ɾ������ͬ������
    *
    * @param maping
    * @param form
    * @param request
    * @param response
    * @return
 * @throws BOException 
    * @throws BOException
    */
   private ActionForward delete(ActionMapping maping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws BOException
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("delete()");
       }
	   String forward = Constants.FORWARD_COMMON_FAILURE;
	   int id = Integer.parseInt(request.getParameter("id"));
	   String categoryID = request.getParameter("categoryID");
	   boolean operResult = false;
	   String str = "ɾ������ͬ������";
	   try
	   {
		   new CYTacticBO().delTactic(id);
		   forward = Constants.FORWARD_COMMON_SUCCESS;		   
		   this.saveMessagesValue(request, "ɾ������ͬ�����Գɹ�!");
		   str = str + "�ɹ���";
		   operResult = true;
	   }
	   catch(Exception e)
	   {
		   logger.error(e);
		   this.saveMessagesValue(request, "ɾ������ͬ������ʧ��!");
		   str = str + "ʧ�ܣ�";
	   }
	   finally
	   {
		   Category category = ( Category ) Repository.getInstance()
			.getNode(categoryID, RepositoryConstants.TYPE_CATEGORY);
	        //��¼������־
	        this.actionLog(request, str, categoryID+" "+category.getNamePath(), operResult, "");
	   }
	   request.setAttribute("goURL", "tactic.do?subSystem=ssms&action=list&categoryID=" + categoryID);
	   return maping.findForward(forward); 
   }
}
