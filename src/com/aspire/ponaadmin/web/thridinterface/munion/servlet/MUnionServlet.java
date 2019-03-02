package com.aspire.ponaadmin.web.thridinterface.munion.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;










import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.BaseServlet;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class MUnionServlet extends BaseServlet{

	/**
	 * M�˶��⿪�Ż��ܸ��½ӿ�
	 */
	private static final long serialVersionUID = -3632138444916669773L;
	private static final String messageCode = "{\"Result\":\"100\",\"Msg\":\"Post����...\"}";
	private static final String parasErrorCode = "{\"Result\":\"200\",\"Msg\":\"�����������...\"}";
	private static final String successCode = "{\"Result\":\"000\",\"Msg\":\"���ܴ����ɹ�!\"}";
	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MUnionServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		flushJsonStr(messageCode, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject paras = null ;
		try {
			paras =  getJsonFromRequest(request, response);
		} catch (JSONException e) {
			flushJsonStr(parasErrorCode, response);
			LOG.debug(e);
			return ;
		}
		String newcategoryid = "";
		try {
			newcategoryid = doPerform(request,response,paras);
		} catch (JSONException e) {
			flushJsonStr(parasErrorCode, response);
			return;
		}
		JSONObject retJson= JSONObject.fromObject(successCode);
		retJson.put("categoryID", newcategoryid);
		flushJsonStr(retJson.toString(), response);
		
	}
	
	public String doPerform (HttpServletRequest request, HttpServletResponse response,JSONObject paras) throws JSONException {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("doPerform()");
        }
		/**
		 * ������ʶ����approvalFlag==yesʱ����ʾ�ò�����Ҫ����������
		 */
		String approvalFlag = "";
		String categorypic = ConfigFactory.getSystemConfig()
				.getModuleConfig("ssms").getItemValue("categorypic");  
		String categoryID = "";   //����id
		String pCategoryID = "";  //������   ����,��Ϊ��,�����ڸ�Ŀ¼�����
		String name = "";        //������   ����
		String desc = "";          //��������  ����Ϊ��
		String deviceCategory = "";   
		String[] platForms = null;   //��Ϊ��
		String[] citys = null;  //��Ϊ��
		String sortId =  "";   //1  
		String startDate = "";    //����
		String endDate =  "";        //2099-01-01
		String ctype = "";//0
		String multiurl =  "";
		String othernet = "";   //1
		String state = "";
		String[] relation = null; //W,O,P,A
		String newCategoryID= "";
		try {
			approvalFlag = paras.getString("approvalFlag");
			categoryID = paras.getString("categoryID");
			pCategoryID = paras.getString("pCategoryID");
			name = paras.getString("name");
			desc = paras.getString("desc");
			deviceCategory = paras.getString("deviceCategory");
			if (!StringUtils.isEmpty(paras.getString("platForms"))) {
				platForms = paras.getString("platForms").split(",");
			}
			
			if (!StringUtils.isEmpty(paras.getString("citys"))) {
				citys = paras.getString("citys").split(",");
			}
			sortId = paras.getString("sortID");
			startDate = paras.getString("startDate");
			endDate = paras.getString("endDate");
			ctype = paras.getString("ctype");
			multiurl = paras.getString("multiurl");
			othernet = paras.getString("othernet");
			state = paras.getString("state");
			relation = paras.getString("relation").split(",");
		} catch (JSONException e) {
			throw e ; 
		}
        //String cgyPath = this.getParameter(request, "cgyPath");     
        String city = formatListToSearchString(citys);     
        String platForm = formatListToSearchString(platForms); 
        // �Ż��Ƿ���ʾ״ֵ̬        1
        
        if (state.equals(""))
        {
            state = "1";
        }

        int intstate = Integer.valueOf(state).intValue();

        // ��ȡ�����ŵ�����
        StringBuffer sb = new StringBuffer();
        for (int i = 0; (relation != null) && (i < relation.length); i++)
        {
            sb.append(relation[i]);
            if (i < relation.length - 1)
            {
                sb.append(",");
            }
        }
        //String forward = null;

        String actionType = "";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        try
        {
            if ("".equals(categoryID))// ����
            {
                actionType = "���ӻ���";
                
                // �ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
                CategoryCityBO.getInstance().isHasPCityIds(pCategoryID, citys);
                
                actionTarget = name;
                Category category = new Category();
                category.setName(name);
                category.setDesc(desc);
                category.setRelation(sb.toString());
                
                if("0".equals(sortId))
        		{
                	category.setSortID(SEQCategoryUtil.getInstance()
        					.getSEQByCategoryType(1));
        		}
        		else
        		{
        			category.setSortID(Integer.valueOf(sortId).intValue());
        		}
                
                
                category.setCtype(0);
                category.setChangeDate(new Date());
                category.setDelFlag(0);
               // category.setDelFlag(0);
                category.setState(intstate);//add by dongke 20110428
                category.setDeviceCategory(Integer.parseInt(deviceCategory));
                category.setCityId(city);
                category.setPlatForm(platForm);

                category.setStartDate(startDate);
                category.setEndDate(endDate);
                
                category.setMultiurl(multiurl);// add by aiyan 2011-11-11
                category.setOthernet(othernet);// add by wml 2011-12-12
                if("yes".equals(approvalFlag)){
                	category.setClassifyStatus("0");
                	category.setGoodsStatus("0");
                }else{
                	category.setClassifyStatus("1");
                }
                 newCategoryID = CategoryTools.createCategory(pCategoryID,
                                                                    category);
                
                List keyBaseList =CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());

                //���⴦��һ���ĵ�ͼƬ
                for(int t = 0 ; t < keyBaseList.size();t ++){
                	ResourceVO vo =  (ResourceVO)keyBaseList.get(t);
                	if(vo.getKeyname().equals(categorypic)){
                		keyBaseList.remove(t);
                		break;
                	}
                }
                ////��չ����--ͼƬ
                String catepicURL = null;
                

                // �������Ϊ���ͻ���,��ӻ��ܶ�Ӧ������Ϣ
                if ("1".equals(deviceCategory))
                {
                    String[] devices = request.getParameterValues("devices");
                    CategoryDeviceBO.getInstance()
                                    .saveDeviceToCategory(newCategoryID,
                                                          devices);
                }

                this.saveMessages(request, "RESOURCE_CATE_RESULT_001");
//                cgyPath = cgyPath + ">>" + name;
//                request.setAttribute(Constants.PARA_GOURL,
//                                     "../../web/resourcemgr/categoryInfo.do?categoryID="
//                                                     + newCategoryID
//                                                     + "&cgyPath=" + cgyPath);
/*                request.setAttribute(Constants.PARA_REFRESH_TREE_KEY,
                                     pCategoryID);*/
                
                if ("11".equals(ctype))
                {
                    category = ( Category ) Repository.getInstance()
                                                      .getNode(newCategoryID,
                                                               RepositoryConstants.TYPE_CATEGORY);
                    category.setPicURL("");
                    category.setCtype(11);
                    if("yes".equals(approvalFlag)){
                    	category.setClassifyStatus("0");
                    }else{
                    	category.setClassifyStatus("1");
                    }
                    category.save();
                }
                
                //forward = Constants.FORWARD_COMMON_SUCCESS;
                //���������������Ҫ֪ͨ����������
                if(!"yes".equals(approvalFlag)){
                	 //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
    				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    				String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
                    if(SSMSDAO.getInstance().isAndroidCategory(newCategoryID, rootCategoryId)
                    ||SSMSDAO.getInstance().isOperateCategory(newCategoryID, operateCategoryId)){//modify by aiyan 2013-05-18	 ){

                    	try {
    						SSMSDAO.getInstance().addMessages(MSGType.CatogoryModifyReq, newCategoryID+":0");
    					} catch (DAOException e) {
    						// TODO Auto-generated catch block
    						LOG.error("�������ܼ�¼��Ϣ�ǳ����»���ID:"+newCategoryID,e);					
    					}
                    }
                }
               
            }
            else
            // ���޸�
            {
                actionType = "�޸Ļ���";
                actionTarget = name;
                Category category = ( Category ) Repository.getInstance()
                                                           .getNode(categoryID,
                                                                    RepositoryConstants.TYPE_CATEGORY);
                // Ϊ�ա�Ϊȫ��Ӧ��,�����Ϊ��
                if(null != citys)
                {
                    // �鿴��ǰ����ĵ�����Ϣ�Ƿ������ӻ��ܵĵ�����Ϣ���� 
                    CategoryCityBO.getInstance().isHasCity(category.getCategoryID(), citys);
                }
                

                // �������Ϊ���ͻ���,��ӻ��ܶ�Ӧ������Ϣ
                if ("0".equals(deviceCategory))
                {
                    CategoryDeviceBO.getInstance()
                                    .delDeviceToCategory(categoryID);
                }
                else if ("1".equals(deviceCategory))
                {
                    String[] devices = request.getParameterValues("devices");
                    // ���û��������ѡ����͡�����ԭֵ����
                    if (devices != null)
                    {
                        CategoryDeviceBO.getInstance()
                                        .delDeviceToCategory(categoryID);
                        CategoryDeviceBO.getInstance()
                                        .saveDeviceToCategory(categoryID,
                                                              devices);
                    }
                }

                
                category.setClassifyStatus("0");
                category.setName(name);
                category.setDesc(desc);
                category.setState(intstate);
                category.setDeviceCategory(Integer.parseInt(deviceCategory));
                category.setCityId(city);
                category.setPlatForm(platForm);
                category.setSortID(Integer.valueOf(sortId).intValue());
                category.setRelation(sb.toString());
                category.setChangeDate(new Date());// add by wml 2013-07-25
                category.setStartDate(startDate);
                category.setEndDate(endDate);
                
                category.setMultiurl(multiurl);// add by aiyan 2011-11-11
                category.setOthernet(othernet);// add by wml 2011-12-12
                
                List keyBaseList = CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());

//              ���⴦��һ���ĵ�ͼƬ
                for(int t = 0 ; t < keyBaseList.size();t ++){
                	ResourceVO vo =  (ResourceVO)keyBaseList.get(t);
                	if(vo.getKeyname().equals(categorypic)){
                		keyBaseList.remove(t);
                		break;
                	}
                }
                

                
                if("yes".equals(approvalFlag)){
                	category.setClassifyStatus("0");
                }else{
                	category.setClassifyStatus("1");
                }
                CategoryBO.getInstance().modCategory(pCategoryID, category);
                this.saveMessages(request, "RESOURCE_CATE_RESULT_003");
//                request.setAttribute(Constants.PARA_GOURL,
//                                     "../../web/resourcemgr/categoryInfo.do?categoryID="
//                                                     + categoryID + "&cgyPath="
//                                                     + cgyPath);
                request.setAttribute(Constants.PARA_REFRESH_TREE_KEY,
                                     pCategoryID);
               
                //forward = Constants.FORWARD_COMMON_SUCCESS;
                if(!"yes".equals(approvalFlag)){
                	//����ӵ���Ϣ����,��������޸Ļ��ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
    				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    				String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
                    if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
                       ||SSMSDAO.getInstance().isOperateCategory(categoryID, operateCategoryId)){//modify by aiyan 2013-05-18	 )){
//                    	Catogoryid	����	String	����ID
//                    	Action	����	String	0���½�
//                    	1������������Ϣ�����������չ�ֶΣ�
//                    	9��ɾ��
//                    	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�

                    	try {
    						SSMSDAO.getInstance().addMessages(MSGType.CatogoryModifyReq, category.getId()+":1");
    					} catch (DAOException e) {
    						// TODO Auto-generated catch block
    						LOG.error("�޸Ļ��ܼ�¼��Ϣ�ǳ������޸ĵĻ���ID:"+category.getId(),e);					
    					}
                    }
                }
                
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            // ����Ƿ��������Ѿ����ڣ�Ҫ��ʾ��ͬ�Ĵ�����Ϣ
            if (e.getErrorCode() == RepositoryBOCode.CATEGORY_NAME_EXISTED)
            {
                actionDesc = "���������ظ�";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_001");
            }
            // add by tungke 20090911
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_RELATION_PARERR)
            {
                actionDesc = "�޷���Ӹ����ܲ������Ĺ����ŵ�,��������Ӹ������ϸù����ŵ�";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_003");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_RELATION_SUBERR)
            {
                actionDesc = "�޷�ɾ���ӻ��ܰ����Ĺ����ŵ�,������ɾ���ӻ����ϸù����ŵ�";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_004");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD)
            {
                actionDesc = "ͼƬ�ϴ�ʧ��";
                this.saveMessages(request, "RESOURCE_CATE_BO_UPLOAD_1010");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_DEVICE)
            {
                actionDesc = "��ƻ��ܹ�������ʧ��";
                this.saveMessages(request, "RESOURCE_CATEGORY_DEVICE_1020");
            }
            else if (e.getErrorCode() == RepositoryBOCode.DEL_CATEGORY_DEVICE)
            {
                actionDesc = "ɾ�����ܹ�������ʧ��";
                this.saveMessages(request, "RESOURCE_DEL_CATEGORY_DEVICE_1021");
            }
            else if (e.getErrorCode() == RepositoryBOCode.UPDATE_CATEGORY_CITY)
            {
                actionDesc = "�������ܵ���������ϢʱС���ӻ��ܵ�����Ϣ���϶�ʧ��";
                this.saveMessages(request, "RESOURCE_UPDATE_CATEGORY_CITY_1022");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                actionDesc = "���������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��";
                this.saveMessages(request, "RESOURCE_ADD_CATEGORY_CITY_1023");
            }
            else
            {
                this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
            }
            actionResult = false;
            //forward = Constants.FORWARD_COMMON_FAILURE;
        }
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return newCategoryID;
	}
	
	/**
     * ���ڰ�������Ϣ��ϳ����ݿ���Ϊ������Ӧ�õĸ�ʽ
     * @param valueList
     * @return ����������
     */
    private String formatListToSearchString(String[] valueList)
    {
        StringBuffer sb = new StringBuffer();
        
        // ���Ϊ�ա�Ϊȫ��Ӧ��
        if(null == valueList)
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
        sb.delete(sb.length()-1, sb.length());
        
        return sb.toString();
    }
	
	
}
