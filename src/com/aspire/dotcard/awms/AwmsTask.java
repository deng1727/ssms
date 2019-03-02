package com.aspire.dotcard.awms;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.autosync.dao.AutoSyncDAO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AwmsTask extends TimerTask {
	
	 /**
     * 日志对象
     */
	
    protected static JLogger LOG = LoggerFactory.getLogger(AwmsTask.class);
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date startDate = new Date();
		int[] mailInfo = new int[3];//0:货架修改数，1：货架新增数，2：同步商品的货架数。
		try {
			
			String ssmsRootCategoryid=null;
			String awmsRootCategoryid=null;
	        try
	        {
	        	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"AWMSConfig");
	            ssmsRootCategoryid = module.getItemValue("ssms_root_categoryid") ;
	            awmsRootCategoryid = module.getItemValue("awms_root_categoryid") ;
	        }
	        catch (Exception e)
	        {
	           throw new Exception("AWMSConfig params is wrong !",e);
	        }
	        LOG.info("同步生成T_R_AWMS_CATEGORY和T_R_AWMS_REFERENCE");
			syncAWMSTable();//数据准备工作。
			//String categoryid = "100003012";String awmsCategoryid="600033965";
			LOG.info("将T_R_AWMS_CATEGORY的数据同步到T_R_CATEGORY");
			
			syncCategory(ssmsRootCategoryid,awmsRootCategoryid,mailInfo);
			
			LOG.info("将T_R_AWMS_REFERENCE的数据同步到T_R_REFERENCE");
			List<CategoryMappingVO> listCM = getALLCategoryMappingVO(ssmsRootCategoryid,awmsRootCategoryid);
			for(CategoryMappingVO cm:listCM){
				
				String cid = null;	
				cid = AutoSyncDAO.getInstance().getCategoryCId(cm.getCategoryId());
				AutoSyncDAO.getInstance().addAutoRef(cid);// wml+ 用于先备份商品信息，数据中心那头用。
				
				downAllRefInCategory(cm);
				upAllRefInCategory(cm);
			}
			mailInfo[2]=listCM.size();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("AndroidListTask:this error may let timer object stop!!",e);
		}finally {
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("AWMSConfig");
			StringBuffer sb = new StringBuffer();
			sb.append("<br>");
			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("<br>");
			
            sb.append("<b>");
			sb.append("处理结果概述：");
			sb.append("</b>");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("本次同步总共修改"+mailInfo[0]+"个货架。新增"+mailInfo[1]+"个货架，共同步"+mailInfo[2]+"个货架下得商品。");
			String content = sb.toString();
			LOG.info(content);
			String subject = module.getItemValue("subject");
			String[] mailTo = module.getItemValue("mailto").split(",");
			this.sendMail(content, mailTo, subject);
		}


	}
	
	/**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
	private void sendMail(String mailContent, String[] mailTo, String subject) {
		Mail.sendMail(subject, mailContent, mailTo);
	}

	private void syncAWMSTable() {
		// TODO Auto-generated method stub
		AWMSDAO.getInstance().syncAWMSTable();
	}

	private void downAllRefInCategory(CategoryMappingVO cm) {
		// TODO Auto-generated method stub
		
		
//		if(cm.getAwmsCategoryId().equals("600033966")){
//			LOG.info(cm.getName()+"--"+cm.getAwmsCategoryId());
//		}
		List<RefVO> refList = getAllRef(cm.getCategoryId());
		
		if(refList.size()>0){
			for(RefVO vo:refList){
				AWMSDAO.getInstance().RemoveRefVO(vo);
			}
		}
		
	}

	private List<RefVO> getAllRef(String categoryId) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getAllRef(categoryId);
	}
	
	private List<RefVO> getAllAWMSRef(String categoryId) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getAllAWMSRef(categoryId);
	}

	private void upAllRefInCategory(CategoryMappingVO cm) {
		// TODO Auto-generated method stub
		List<RefVO> refList = getAllAWMSRef(cm.getAwmsCategoryId());
//		if(cm.getAwmsCategoryId().equals("600033966")){
//			LOG.info(cm.getName()+"--"+cm.getAwmsCategoryId());
//		}
		if(refList.size()>0){
			for(RefVO vo:refList){
				GContent gc = getGContent(vo);
				if(gc!=null&&vo.getSsmsCategoryid()!=null&&!vo.getSsmsCategoryid().trim().equals("")){
					AWMSDAO.getInstance().addCategory(gc,vo);	
				}
				
			}
		}
		
	}

	private GContent getGContent(RefVO vo) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getGContent(vo.getGoodsid());
	}

	private List<CategoryMappingVO> getALLCategoryMappingVO(String categoryid,String awmsCategoryid) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getALLCategoryMappingVO(categoryid,awmsCategoryid);
	}


	private void syncCategory(String categoryid, String awmsCategoryid,int[] mailInfo) {
		// TODO Auto-generated method stub
//		List<CategoryVO> remote= getAllCategory(awmsCategoryid,"awms");
//		List<CategoryVO> locale= getAllCategory(categoryid,"ssms");
//		List<CategoryVO> left= new ArrayList<CategoryVO>();
//		for(CategoryVO r:remote){
//			boolean flag = false;
//			for(CategoryVO l:locale){
//				if(r.equals(l)){
//					flag = true;
//					break;
//				}
//			}
//			if(!flag){
//				left.add(r);	
//			}
//			
//		}
		LOG.info("syncCategory update  begin...");
		List<CategoryVO> updateCategoryList = getUpdateCategoryList(awmsCategoryid);
		for(CategoryVO v:updateCategoryList){
			updateCategory(v);
		}
		mailInfo[0]=updateCategoryList.size();
		LOG.info("syncCategory update  end...");
		
		List<CategoryVO> insertCategoryList = getInsertCategoryList(awmsCategoryid);
		LOG.info("syncCategory begin...");
		for(CategoryVO v:insertCategoryList){//插于T_R_BASE,T_R_CATEGORY
			insertCategory(v);
		}
		
		for(CategoryVO v:insertCategoryList){//插于T_R_CATEGORY中的PARENTID的值！
			updateParentCategoryid(v);
		}
		
		for(CategoryVO v:insertCategoryList){//插入T_R_BASE 的PARENTID 和PATH
			updateBase(v);
		}
		mailInfo[1]=insertCategoryList.size();
		LOG.info("syncCategory end...");
		
	}

	private void updateCategory(CategoryVO v) {
		// TODO Auto-generated method stub
		AWMSDAO.getInstance().updateCategory(v);
	}

	private List<CategoryVO> getUpdateCategoryList(String awmsCategoryid) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getUpdateCategoryList(awmsCategoryid);
	}

	private List<CategoryVO> getInsertCategoryList(String awmsCategoryid) {
		// TODO Auto-generated method stub
		return AWMSDAO.getInstance().getInsertCategoryList(awmsCategoryid);
	}

	private void updateBase(CategoryVO v) {
		// TODO Auto-generated method stub
		AWMSDAO.getInstance().updateBase(v);
	}

	private void updateParentCategoryid(CategoryVO v) {
		// TODO Auto-generated method stub
		AWMSDAO.getInstance().updateParentCategoryid(v);
		LOG.info("updateParentCategoryid...");
	}

	private void insertCategory(CategoryVO v) {
		// TODO Auto-generated method stub
		AWMSDAO.getInstance().insertCategory(v);
		//System.out.println("need in.."+v.getName());
		LOG.info("need in.."+v.getName());
		
	}

//	private List<CategoryVO> getAllCategory(String categoryid,String type) {
//		// TODO Auto-generated method stub
//		return AWMSDAO.getInstance().getAllCategory(categoryid,type);
//		
//	}

	
}
