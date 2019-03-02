package com.aspire.dotcard.baseread.action;

import java.util.ArrayList;
import java.util.Hashtable;
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
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.dotcard.baseread.dao.BookCategoryDao;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.gcontent.CityVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class BaseReadCategoryAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadCategoryAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
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
        else if ("del".equals(perType))
        {
            return del(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("save".equals(perType))
        {
            return save(mapping, form, request, response);
        }
        else if ("mod".equals(perType))
        {
            return mod(mapping, form, request, response);
        }
        else if ("update".equals(perType))
        {
            return update(mapping, form, request, response);
        }
        else if ("city".equals(perType))
        {
            return city(mapping, form, request, response);
        }
        else if ("categoryQuery".equals(perType))
        {
            return categoryQuery(mapping, form, request, response);
        }
        else if ("approval".equals(perType))
		{
			return approval(mapping, form, request, response);
		}
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
    
    /**
	 * ͼ������ύ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		
		try {
			ReadCategoryBO.getInstance().approvalCategory(request,categoryId);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "�ύ����ͼ����ܳ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_BOOKCATEGORY_RESULT_001");
		request.setAttribute(Constants.PARA_GOURL, "categoryTree.do?perType=query&categoryId=" + categoryId);
		return mapping.findForward(forward);
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String operation = this.getParameter(request, "operation");
        String cityName = null;
        String platFormName = null;
        ReadCategoryVO categoryVO;
        List keyBaseList = null;
        
		String actionType = "��ѯ�Ķ�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ�Ķ�������Ϣ�ɹ�";
		String actionTarget = categoryId;
		String path = "";
		
        try
        {
            // ��ѯ������Ϣ
            categoryVO = ReadCategoryBO.getInstance()
                                       .queryReadCategoryVO(categoryId);
            // ��ѯ������Ϣ
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(categoryVO.getCityId());
            // ��ѯƽ̨��Ϣ
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(categoryVO.getPlatForm());

            keyBaseList = ReadCategoryBO.getInstance()
                                        .queryReadCategoryKeyBaseList(categoryId);
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ�Ķ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ�����Ķ������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("path", path);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("operation", operation);
        request.setAttribute("categoryVO", categoryVO);

        return mapping.findForward(forward);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward del(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String categoryId = this.getParameter(request, "categoryId").trim();
        
        String actionType = "ɾ���Ķ�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "ɾ���Ķ�������Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        try
        {
            // �Ƿ�����ӻ���
            if (0 < ReadCategoryBO.getInstance().hasReadChild(categoryId))
            {
                this.saveMessagesValue(request, "�����������ӻ��ܲ�����ɾ����");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // �Ƿ������Ʒ
            if (0 < ReadCategoryBO.getInstance().hasRead(categoryId))
            {
                this.saveMessagesValue(request, "������������Ʒ������ɾ����");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // ����ɾ��ָ������
            //ReadCategoryBO.getInstance().delReadCategory(categoryId);
            // ɾ����չ�ֶ�ֵ
            //ReadCategoryBO.getInstance().delReadCategoryKeyResource(categoryId);
            ReadCategoryBO.getInstance().delBookCategoryItem(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "ɾ���Ķ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ�����Ķ������Ƿ������ɾ����������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "ɾ�������Ķ����ܳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/new_category_main.jsp");
        request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
        return mapping.findForward(forward);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "add";

        String pCategoryId = this.getParameter(request, "parentCategoryId")
                                 .trim();
        ReadCategoryVO readCategory = new ReadCategoryVO();

        readCategory.setParentId(pCategoryId);
        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(null);

        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("readCategory", readCategory);
        request.setAttribute("pCategoryId", pCategoryId);

        return mapping.findForward(forward);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = Constants.FORWARD_COMMON_FAILURE;

        FileForm fileForm = ( FileForm ) form;

        // У���ļ���׺��
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        Hashtable files = fileForm.getMultipartRequestHandler()
                                  .getFileElements();
        FormFile uploadFile = ( FormFile ) files.get("dataFile");

        ReadCategoryVO readVO = new ReadCategoryVO();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String decrisption = this.getParameter(request, "decrisption").trim();
        String type = this.getParameter(request, "type").trim();
        String catalogType = this.getParameter(request, "catalogType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        String sortId = this.getParameter(request, "sortId").trim();
        String catepicURL = "";
        String cid = ReadCategoryBO.getInstance().getReadVOId();
        String categoryId = ReadCategoryBO.getInstance().getReadVOCategoryId();
        
        String actionType = "�����Ķ�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�����Ķ�������Ϣ�ɹ�";
		String actionTarget = pCategoryId;

        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(cid);

        readVO.setParentId(pCategoryId);
        readVO.setCategoryName(categoryName);
        readVO.setDecrisption(decrisption);
        readVO.setType(type);
        readVO.setCatalogType(catalogType);
        readVO.setPlatForm(platForms);
        readVO.setCityId(citys);
        
        if("0".equals(sortId))
		{
        	readVO.setSortId(SEQCategoryUtil.getInstance()
					.getSEQByCategoryType(2));
		}
		else
		{
			readVO.setSortId(Integer.parseInt(sortId));
		}
        

        try
        {
            // ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
            ReadCategoryBO.getInstance().hasReadNameInParentId(pCategoryId,
                                                               categoryName);

            // �ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
            CategoryCityBO.getInstance()
                          .isHasReadPCityIds(pCategoryId,
                                             request.getParameterValues("citys"));

            // �õ�����id
            readVO.setId(cid);
            readVO.setCategoryId(categoryId);

            // ����ͼƬ
            if (uploadFile.getFileName() != null
                && !"".equals(uploadFile.getFileName()))
            {
                catepicURL = ReadCategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile,
                                                             readVO.getId());

                readVO.setPicUrl(catepicURL);
            }

            // �������������ֻ���
            ReadCategoryBO.getInstance().saveReadCategory(readVO);

            if (keyBaseList != null && keyBaseList.size() > 0)
            {
                this.saveReadCategoryKeyResource(keyBaseList,
                                                 fileForm,
                                                 cid,
                                                 request);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "����ͼ������б����");

            if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                this.saveMessages(request, "���������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME)
            {
                this.saveMessages(request, "�˻���������ͬ���������Ѵ���");
            }

			// д������־
			actionResult = false;
			actionDesc = "�����Ķ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����Ķ����ܳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/new_category_main.jsp");
        request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
        return mapping.findForward(forward);
    }

    /**
     * ���ڰ�������Ϣ��ϳ����ݿ���Ϊ������Ӧ�õĸ�ʽ
     * 
     * @param valueList
     * @return ����������
     */
    private String formatListToSearchString(String[] valueList)
    {
        StringBuffer sb = new StringBuffer();

        // ���Ϊ�ա�Ϊȫ��Ӧ��
        if (null == valueList)
        {
            sb.append("{").append(Constants.ALL_CITY_PLATFORM).append("}");

            return sb.toString();
        }

        // ��������
        for (int i = 0; i < valueList.length; i++)
        {
            String temp = valueList[i];

            sb.append("{").append(temp).append("}").append(",");
        }

        // ɾ�����һ����Ϣ
        sb.delete(sb.length() - 1, sb.length());

        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward mod(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "mod";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String parentId = this.getParameter(request, "parentId").trim();
        ReadCategoryVO readVO;
        String cityName = null;
        String platFormName = null;
        List platFormList = null;
        List cityList = null;
        List keyBaseList = null;
        String path = "";
        try
        {
            // �õ���ǰ������Ϣ
            readVO = ReadCategoryBO.getInstance()
                                   .queryReadCategoryVO(categoryId);

            // �õ���ǰ���ܳ�����Ϣ
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(readVO.getCityId());
            cityList = CategoryCityBO.getInstance()
                                     .queryListByCityId(readVO.getCityId());
            // �õ���ǰ����ƽ̨��Ϣ
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(readVO.getPlatForm());
            platFormList = CategoryCityBO.getInstance()
                                         .queryListByCityId(readVO.getPlatForm());
            keyBaseList = ReadCategoryBO.getInstance()
                                        .queryReadCategoryKeyBaseList(categoryId);
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�Ķ������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("path", path);
        request.setAttribute("pCategoryId", parentId);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("cityList", cityList);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("readVO", readVO);
        return mapping.findForward(forward);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        String forward = Constants.FORWARD_COMMON_FAILURE;
        FileForm fileForm = ( FileForm ) form;

        // У���ļ���׺��
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        Hashtable files = fileForm.getMultipartRequestHandler()
                                  .getFileElements();
        FormFile uploadFile = ( FormFile ) files.get("dataFile");

        ReadCategoryVO readVO = new ReadCategoryVO();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String oldName = this.getParameter(request, "oldName").trim();
        String decrisption = this.getParameter(request, "decrisption").trim();
        String type = this.getParameter(request, "type").trim();
        String catalogType = this.getParameter(request, "catalogType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        String oldPicURL = this.getParameter(request, "oldPicURL").trim();
        String clearPicUrl = this.getParameter(request, "clearPicUrl").trim();
        String sortId = this.getParameter(request, "sortId").trim();

        String catepicURL = "";
        
        String actionType = "�޸��Ķ�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�޸��Ķ�������Ϣ�ɹ�";
		String actionTarget = categoryId;
		

        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(categoryId);

        readVO.setCategoryId(categoryId);
        readVO.setParentId(pCategoryId);
        readVO.setCategoryName(categoryName);
        readVO.setDecrisption(decrisption);
        readVO.setCatalogType(catalogType);
        readVO.setType(type);
        readVO.setPlatForm(platForms);
        readVO.setCityId(citys);
        readVO.setSortId(Integer.parseInt(sortId));

        try
        {
            // ���Ҫ���ͼƬ��Ϣ��
            if ("1".equals(clearPicUrl))
            {
                readVO.setPicUrl("");
            }
            // ����иġ�
            else if (uploadFile != null && !"".equals(uploadFile.getFileName()))
            {
                catepicURL = ReadCategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile,
                                                             readVO.getCategoryId());

                readVO.setPicUrl(catepicURL);
            }
            // ���û�иġ�
            else
            {
                readVO.setPicUrl(oldPicURL);
            }

            // ���û�иı��������
            if (!oldName.equals(categoryName))
            {
                // ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
                ReadCategoryBO.getInstance()
                              .hasReadNameInParentId(pCategoryId, categoryName);
            }

            if (null != request.getParameterValues("citys"))
            {
                // �鿴��ǰ����ĵ�����Ϣ�Ƿ������ӻ��ܵĵ�����Ϣ����
                CategoryCityBO.getInstance()
                              .isHasReadCity(categoryId,
                                             request.getParameterValues("citys"));
            }

            // �жϱ���Ļ��ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
            CategoryCityBO.getInstance()
                          .isHasReadPCityIds(pCategoryId,
                                             request.getParameterValues("citys"));

            // ���ڱ��ͼ�����
            ReadCategoryBO.getInstance().updateReadCategory(readVO);

            if (keyBaseList != null && keyBaseList.size() > 0)
            {
                this.saveReadCategoryKeyResource(keyBaseList,
                                                 fileForm,
                                                 categoryId,
                                                 request);

            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ķ������б����");

            if (e.getErrorCode() == RepositoryBOCode.UPDATE_CATEGORY_CITY)
            {
                this.saveMessages(request, "�������ܵ���������ϢʱС���ӻ��ܵ�����Ϣ���϶�ʧ��");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                this.saveMessages(request, "�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME)
            {
                this.saveMessages(request, "�˻���������ͬ���������Ѵ���");
            }

			// д������־
			actionResult = false;
			actionDesc = "�޸��Ķ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����Ķ����ܳɹ�");
        request.setAttribute(Constants.PARA_GOURL,
                             "../../web/baseRead/categoryTree.do?categoryId="
                                             + categoryId
                                             + "&perType=query&isReload=yes");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        return mapping.findForward(forward);
    }

    /**
     * 
     * @desc ������չ�ֶ�
     * @author dongke Aug 8, 2011
     * @throws BOException
     */
    public void saveReadCategoryKeyResource(List keyBaseList,
                                            FileForm fileForm, String cid,
                                            HttpServletRequest request)
                    throws BOException
    {

        // ��Դ��������ģ��·��
        String resServerPath = ConfigFactory.getSystemConfig()
                                            .getModuleConfig("baseRead")
                                            .getItemValue("resServerPath");
        List delResourcelist = new ArrayList();
        this.saveKeyResource(keyBaseList,
                             delResourcelist,
                             fileForm,
                             cid,
                             request,
                             resServerPath,
                             "category");
        ReadCategoryBO.getInstance().saveKeyResource(keyBaseList);
        KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }

    public ActionForward city(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("city doPerform()");
        }
        String forward = "city";

        // �������л�ȡ��������
        String cityName = this.getParameter(request, "cityName").trim();
        String pvcName = this.getParameter(request, "pvcName").trim();
        String categoryID = this.getParameter(request, "categoryID");
        String pCategoryID = this.getParameter(request, "pCategoryID");
        String isFirst = this.getParameter(request, "isFirst").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�������ͱ���Ϣ
        if (isFirst.equals("1"))// ��һ�β���Ҫ��ѯ
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "������������ѯ");
        }
        else
        {
            CityVO city = new CityVO();
            city.setCityName(cityName);
            city.setPvcName(pvcName);

            CategoryCityBO.getInstance().queryReadCityList(page,
                                                           city,
                                                           categoryID,
                                                           pCategoryID);
        }

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("cityName", cityName);
        request.setAttribute("pvcName", pvcName);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("pCategoryID", pCategoryID);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward categoryQuery(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "categoryQuery";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String categoryName = this.getParameter(request, "categoryName").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();
        PageResult page = new PageResult(request);
        
		String actionType = "��ѯ�Ķ�����������Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ�Ķ�����������Ϣ�ɹ�";
		String actionTarget = "";
		
		if("1".equals(isFirst))
		{
	        request.setAttribute("categoryId", categoryId);
	        request.setAttribute("categoryName", categoryName);
	        request.setAttribute("perType", forward);
	        request.setAttribute("PageResult", page);
	        return mapping.findForward(forward);
		}
		
        try
        {
        	ReadCategoryVO categoryVO = new ReadCategoryVO();
        	categoryVO.setId(categoryId);
        	categoryVO.setCategoryName(categoryName);
        	
            // ��ѯ����������Ϣ
        	ReadCategoryBO.getInstance().queryCategoryDescList(page, categoryVO);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ�Ķ�����������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ�����Ķ����������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }
}
