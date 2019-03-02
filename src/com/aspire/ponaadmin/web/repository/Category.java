package com.aspire.ponaadmin.web.repository ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>��Դ���еķ���ڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class Category extends EntityNode
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(Category.class) ;

    /**
     * ���췽��
     */
    public Category()
    {
        this.type = "nt:category";
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public Category(String nodeID)
    {
        super(nodeID);
        this.type = "nt:category";
    }

    /**
     * ���÷�������·��
     * @param namePath����������·��
     */
    public void setNamePath(String namePath)
    {
        Property pro = new Property("namePath", namePath);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��������·��
     * @return����������·��
     */
    public String getNamePath()
    {
        Property prop = this.getProperty("namePath");
        if(prop.getValue() != null)
        {
            return (String) prop.getValue() ;
        }
        String namePath = null;
        if(this.getId().equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            namePath = this.getName();
        }
        else
        {
            try
            {
                Category category = (Category) Repository.getInstance().getNode(this.parentID
                		    , RepositoryConstants.TYPE_CATEGORY);
                namePath = category.getNamePath() + ">>" + this.getName();
            }
            catch (Exception e)
            {
                LOG.error(e);
            }
        }
        this.setNamePath(namePath);
        return namePath;
    }

    /**
     * ���÷�������
     * @param value����������
     */
    public void setName(String value)
    {
        Property pro = new Property("name", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��������
     * @return����������
     */
    public String getName()
    {
        return (String) this.getProperty("name").getValue();
    }
    
    /**
     * ���÷�������
     * @param value����������
     */
    public void setDesc(String value)
    {
        Property pro = new Property("desc", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��������
     * @return����������
     */
    public String getDesc()
    {
        return (String) this.getProperty("desc").getValue();
    }

    /**
     * ��������id
     * @param value������id
     */
    public void setSortID(int value)
    {
        Property pro = new Property("sortID", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ����id
     * @return������id
     */
    public int getSortID()
    {
        return ((Integer) this.getProperty("sortID").getValue()).intValue();
    }

    /**
     * ���÷����������ͣ����Ƿ�ϵͳ����
     * @param value��������������
     */
    public void setCtype(int value)
    {
        Property pro = new Property("ctype", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ������������
     * @return��������������
     */
    public int getCtype()
    {
        return ((Integer) this.getProperty("ctype").getValue()).intValue();
    }
    
    /**
     * ���û��ͻ�����Ϣ
     * @param value
     */
    public void setDeviceCategory(int value)
    {
        Property pro = new Property("deviceCategory", new Integer(value));
        this.setProperty(pro);
    }
    
    /**
     * ���ػ��ͻ�����Ϣ
     * @return
     */
    public int getDeviceCategory()
    {
        return ((Integer) this.getProperty("deviceCategory").getValue()).intValue();
    }
    
    /**
     * ���û���ƽ̨��Ϣ
     * @param value
     */
    public void setPlatForm(String platForm)
    {
        Property pro = new Property("platForm", platForm);
        this.setProperty(pro);
    }
    
    /**
     * ���ػ���ƽ̨��Ϣ
     * @return
     */
    public String getPlatForm()
    {
        return (String)this.getProperty("platForm").getValue();
    }
    
    /**
     * ���û����޶�������Ϣ
     * @param value
     */
    public void setCityId(String cityId)
    {
        Property pro = new Property("cityId", cityId);
        this.setProperty(pro);
    }
    
    /**
     * ���ػ����޶�������Ϣ
     * @return
     */
    public String getCityId()
    {
        return (String)this.getProperty("cityId").getValue();
    }
    
    /**
     * �Ƿ����� 0��   1��
     * @return
     */
    public int getIsLock(){
    	return   ((Integer)this.getProperty("isLock").getValue()).intValue();
    }
    public void setIsLock(int value){
    	Property pro = new Property("isLock", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * ����ʱ��
     * @return
     */
    public String getLockTime()
    {
    	return  (String) this.getProperty("lockTime").getValue();
    }
    /**
     * ����ʱ��
     * @param valuelockTime
     */
    public void setLockTime(String value)
    {
    	Property pro = new Property("lockTime", value);
        this.setProperty(pro);
    }
    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Category[");
        sb.append(this.getProperty("name"));
        sb.append(',');
        sb.append(this.getProperty("desc"));
        sb.append(',');
        sb.append(this.getProperty("sortID"));
        sb.append(',');
        sb.append(this.getProperty("ctype"));
        sb.append(',');
        sb.append(this.getProperty("categoryID"));
        sb.append(',');
        sb.append(this.getProperty("delFlag"));
        sb.append(',');
        sb.append(this.getProperty("changeDate"));
        sb.append(',');
        sb.append(this.getProperty("state"));
        sb.append(',');
        sb.append(this.getProperty("parentCategoryID"));
        sb.append(',');
        sb.append(this.getProperty("relation"));
        sb.append(',');
        sb.append(this.getProperty("picURl"));
        sb.append(',');
        sb.append(this.getProperty("statistic"));
        sb.append(',');
        sb.append(this.getProperty("platForm"));
        sb.append(',');
        sb.append(this.getProperty("cityId"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * ���û��ܱ���
     * @param value�����ܱ���
     */
    public void setCategoryID(String value)
    {
        Property pro = new Property("categoryID", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ���ܱ���
     * @return�����ܱ���
     */
    public String getCategoryID()
    {
        return (String) this.getProperty("categoryID").getValue();
    }
    
    /**
     * ����ɾ����־λ
     * @param value��ɾ����־λ
     */
    public void setDelFlag(int value)
    {
        Property pro = new Property("delFlag", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡɾ����־λ
     * @return��ɾ����־λ
     */
    public int getDelFlag()
    {
        return ((Integer) this.getProperty("delFlag").getValue()).intValue();
    }
    
    /**
     * ���ñ��ʱ��
     * @param value�����ʱ��
     */
    public void setChangeDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = sdf.format(date);
        Property pro = new Property("changeDate", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ���ʱ��
     * @return�����ʱ��
     */
    public String getChangeDate()
    {
        return (String) this.getProperty("changeDate").getValue();
    }
    
    /**
     * ���û���״̬�� 1,���������Ż�չʾ��0 �����Ż�չʾ        -----1��������9������
     * @param value������״̬
     */
    public void setState(int value)
    {
        Property pro = new Property("state", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ����״̬
     * @return������״̬
     */
    public int getState()
    {
        return ((Integer) this.getProperty("state").getValue()).intValue();
    }
    
    /**
     * ���ø����ܱ���
     * @param value�������ܱ���
     */
    public void setParentCategoryID(String value)
    {
        Property pro = new Property("parentCategoryID", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�����ܱ���
     * @return�������ܱ���
     */
    public String getParentCategoryID()
    {
        return (String) this.getProperty("parentCategoryID").getValue();
    }

    /**
     * ���ù����ŵ�
     * @param value�������ŵ�
     */
    public void setRelation(String value)
    {
        Property pro = new Property("relation", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�����ŵ�
     * @return�������ŵ�
     */
    public String getRelation()
    {
        return (String) this.getProperty("relation").getValue();
    }
    
    /**
     * ��ȡ�����ŵ�չʾ
     * @return�������ŵ�չʾ���
     */
    public String getDisplayRelation()
    {
        return getRelation().replaceAll("W", "WWW�ŵ�")
                            .replaceAll("O", "�ն��ŵ�")
                            .replaceAll("P", "PC�ŵ�")
                            .replaceAll("A", "WAP�ŵ�");
    }
    /**
     * ���û���Ԥ��ͼ
     * @param picURL
     */
    public void setPicURL(String picURL)
    {
    	Property pro = new Property("picURL", picURL);
        this.setProperty(pro);
    }
    /**
     * ��ȡ����Ԥ��ͼ��ַ
     * @return
     */
    public String getPicURL()
    {
    	return (String) this.getProperty("picURL").getValue();
    }
    /**
     * ���û���ͳ����Ϣ
     * @param statistic
     */
    public void setStatistic(long statistic)
    {
    	Property pro = new Property("statistic", new Long(statistic));
        this.setProperty(pro);
    }
    /**
     * ��ȡ����ͳ����Ϣ
     * @return
     */
    public long getStatistic()
    {
    	return ((Long) this.getProperty("statistic").getValue()).longValue();
    }
    
   
    
    
    /**
     * 
     *@desc 
     *@author dongke
     *May 23, 2011
     * @param keyName
     * @param value
     * @throws BOException 
     */
    public void setKeyValue(String keyName,String value) throws BOException{
    	
		if (this.getCategoryID() != null && keyName  != null && value!= null )
		{
			KeyResourceVO oldKeyResource = this.getKeyResByName(keyName);
			int result = 0;
			
			if(oldKeyResource == null){
				//insert into
				try
				{
					result = CategoryDAO.getInstance().insertCategoryKeyRes(this.getCategoryID(),keyName,value);
				} catch (DAOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BOException("�������ͼƬʧ��!",RepositoryBOCode.ADD_CATEGORY_PIC);
				}
			}else{
				//update to
				try
				{
					result = CategoryDAO.getInstance().updateCategoryKeyRes(this.getCategoryID(),oldKeyResource.getKeyId(),value);
				} catch (DAOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BOException("���»���ͼƬʧ��!",RepositoryBOCode.UPDATE_CATEGORY_PIC);
				}
			}		
		}
    }
    
    
    
    /**
     * ���û��ܿ�ʼ��Чʱ��
     * @param startDate
     */
    public void setStartDate(String startDate)
    {
    	Property pro = new Property("startDate", startDate);
        this.setProperty(pro);
    }
    /**
     * ��ȡ���ܿ�ʼ/��Чʱ��
     * @return
     */
    public String getStartDate()
    {
    	return (String) this.getProperty("startDate").getValue();
    }
    
    /**
     * ���û��ܽ���/ʧЧʱ��
     * @param endDate
     */
    public void setEndDate(String endDate)
    {
    	Property pro = new Property("endDate", endDate);
        this.setProperty(pro);
    }
    /**
     * ��ȡ���ܿ�ʼ��Чʱ��
     * @return
     */
    public String getEndDate()
    {
    	return (String) this.getProperty("endDate").getValue();
    }
    
    /**
     * 
     *@desc 
     *@author dongke
     *May 21, 2011
     * @param keyName
     * @return
     */
  public KeyResourceVO getKeyResByName(String keyName)
	{
	  KeyResourceVO kr = null;
		if (this.getCategoryID() != null)
		{
			try
			{
				 kr = CategoryDAO.getInstance().getCategoryKeyResByKey(
						this.getCategoryID(), keyName);
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return kr;
	}
  /**
   * 
   *@desc 
   *@author dongke
   *May 21, 2011
   * @param id
   * @return
   */
//    public List getAllKeyResourceByCid()
//	{
//		if (this.getCategoryID() != null)
//		{
//			try
//			{
//				return CategoryDAO.getInstance().getCategoryKeyResourceByCid(
//						this.getCategoryID());
//			} catch (DAOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			}
//		}
//		else
//		{
//			return null;
//		}
//	}
    /**
     * 
     *@desc 
     *@author dongke
     *May 21, 2011
     * @param id
     * @return
     */
//      public List getKeyBase()
//  	{
//  		
//  			try
//  			{
//  				return CategoryDAO.getInstance().getCategoryKeyBase();
//  			} catch (DAOException e)
//  			{
//  				// TODO Auto-generated catch block
//  				e.printStackTrace();
//  				return null;
//  			}
//  		
//  	}
  
  
  
		  /**
     * ����URL(Ŀǰ��Ϊ�ն��Ż�����URL)
     * @param value���ն��Ż�����URL
     */
    public void setMultiurl(String multiurl)
    {
        Property pro = new Property("multiurl", multiurl);
        this.setProperty(pro);
    }

    /**
     * ����URL(Ŀǰ��Ϊ�ն��Ż�����URL)
     * @return���ն��Ż�����URL
     */
    public String getMultiurl()
    {
        return ( String ) this.getProperty("multiurl").getValue();
    }

    /**
     * �Ƿ�֧������
     * @param othernet
     */
    public void setOthernet(String othernet)
    {
        Property pro = new Property("othernet", othernet);
        this.setProperty(pro);
    }

    /**
     * �Ƿ�֧������
     * @return
     */
    public String getOthernet()
    {
        return ( String ) this.getProperty("othernet").getValue();
    }
    /**
     * �Ƿ���Ҫ����
     * flag="yes"��ʾ��Ҫ����
     * �����������û���ӻ���ʱ����ʶ
     * @param othernet
     */
    public void setVerifyFlag(String flag)
    {
        Property pro = new Property("verifyFlag", flag);
        this.setProperty(pro);
    }

    /**
     * �Ƿ���Ҫ����
     *  flag="yes"��ʾ��Ҫ����
     *  �����������û���ӻ���ʱ����ʶ
     * @return
     */
    public String getVerifyFlag()
    {
        return ( String ) this.getProperty("verifyFlag").getValue();
    }
    /**
     * ���ܷ�������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param othernet
     */
    public void setClassifyStatus(String classifyStatus)
    {
        Property pro = new Property("classifyStatus", classifyStatus);
        this.setProperty(pro);
    }

    /**
     * ���ܷ�������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @return
     */
    public String getClassifyStatus()
    {
        return ( String ) this.getProperty("classifyStatus").getValue();
    }
    
    /**
     * ������Ʒ����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param goodsStatus
     */
    public void setGoodsStatus(String goodsStatus)
    {
        Property pro = new Property("goodsStatus", goodsStatus);
        this.setProperty(pro);
    }

    /**
     * ������Ʒ����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @return
     */
    public String getGoodsStatus()
    {
        return ( String ) this.getProperty("goodsStatus").getValue();
    }
    /**
     * ���ݱ�ɾ��ǰ������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param goodsStatus
     */
    public void setDelproStatus(String delproStatus)
    {
        Property pro = new Property("delproStatus", delproStatus);
        this.setProperty(pro);
    }

    /**
     * ���ݱ�ɾ��ǰ������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @return
     */
    public String getDelproStatus()
    {
        return ( String ) this.getProperty("delproStatus").getValue();
    }
}
