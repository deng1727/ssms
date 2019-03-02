/*
 * �ļ�����BookRefAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.book.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.book.vo.BookRefVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookRefAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BookRefAction.class);
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryBook".equals(perType))
        {
            return queryBook(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }else if ("showBook".equals(perType))
        {
            return showBook(mapping, form, request, response);
        }else if ("saveBook".equals(perType))
        {
            return saveBook(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }

    /**
     * �õ�ͼ��ָ�������µ���Ʒ�б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "query";
        BookRefVO vo = new BookRefVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();

        vo.setCId(categoryId);
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            BookRefBO.getInstance().queryBookRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯͼ��ָ�������µ���Ʒ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("bookId", bookId);
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        return mapping.findForward(forward);
    }
    
    /**
     * �����Ƴ�ָ��������ָ����ͼ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] musicId = request.getParameterValues("dealRef");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            BookRefBO.getInstance().removeBookRefs(categoryId, musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ƴ�ָ��������ָ����ͼ�����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "ɾ��ͼ��ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * ��������ͼ�������ͼ����Ʒ����ֵ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "setSort";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // ����ͼ�������ͼ����Ʒ����ֵ
            BookRefBO.getInstance().setBookSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��������ͼ�����������ͼ������ֵʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "����ͼ����Ʒ����ֵ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڲ�ѯͼ���б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryBook";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        BookRefVO vo = new BookRefVO();
        PageResult page = new PageResult(request);
        
        // ����ǵ�һ�Ρ�����
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("perType", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("bookName", bookName);
            request.setAttribute("bookId", bookId);
            request.setAttribute("authorName", authorName);
            request.setAttribute("typeName", typeName);
            
            return mapping.findForward(forward);
        }
        
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡͼ��������bookName=" + bookName + ", bookId="
                      + bookId + ", authorName=" + authorName);
        }

        try
        {
            // ��ѯͼ���б������ϼ����»�����
            BookRefBO.getInstance().queryBookList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯͼ���б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("bookName", bookName);
        request.setAttribute("bookId", bookId);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        
        return mapping.findForward(forward);
    }
    
    /**
     * �������ָ����ͼ����������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addBookId = this.getParameter(request, "addBookId").trim();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // �鿴ԭ�����Ƿ�����û���Ҫ������������Ϣ
            String ret = BookRefBO.getInstance().isHasBookRefs(categoryId,
                                                                    addBookId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ����ͼ�飺" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // ����ָ��ͼ����ָ��������
            BookRefBO.getInstance().addBookRefs(categoryId, addBookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�������ָ����ͼ��������ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "���ͼ��ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * �ļ���������ͼ����Ʒ�ϼ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }
        
        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        DataImportForm iForm=(DataImportForm)form;
        
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        FormFile dataFile = iForm.getDataFile();
        try
        {
            // �ļ���������ͼ����Ʒ�ϼ�
            ret = BookRefBO.getInstance().importContentExigence(dataFile, categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����ļ���������ͼ����Ʒ�ϼܳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "�ļ���������ͼ����Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    
    /**
     * ���ڲ�ѯͼ������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showBook";

        // �������л�ȡ��������
     
        String bookId = this.getParameter(request, "bookId").trim();
       
		
        BookRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯͼ������
        	vo =  BookRefBO.getInstance().queryBookInfo(bookId);
        	keyBaseList = BookRefBO.getInstance().queryBookKeyResource(bookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯͼ���������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڱ���ͼ����չ����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward saveBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveBook";

        // �������л�ȡ��������
     
        String bookId = this.getParameter(request, "bookId").trim();
        FileForm fileForm = (FileForm) form;
		
        // У���ļ���׺��
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        BookRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯͼ������
        	vo =  BookRefBO.getInstance().queryBookInfo(bookId);
        	keyBaseList = BookRefBO.getInstance().queryBookKeyResource(bookId);
        	
        	if (keyBaseList != null)
    		{
    			this.saveBookKeyResource(keyBaseList,fileForm,bookId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "����ͼ����չ��Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "����ͼ����չ����ֵ�ɹ�!");

        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc ������չ�ֶ�
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveBookKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//��Դ��������ģ��·��
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basebook").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"book");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
