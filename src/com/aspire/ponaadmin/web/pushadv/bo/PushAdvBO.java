package com.aspire.ponaadmin.web.pushadv.bo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO;
import com.aspire.ponaadmin.web.pushadv.vo.InfosVO;
import com.aspire.ponaadmin.web.pushadv.vo.PushAdvVO;
import com.aspire.ponaadmin.web.util.IOUtil;

public class PushAdvBO {

	/**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PushAdvBO.class);

    private static PushAdvBO instance = new PushAdvBO();

    private PushAdvBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static PushAdvBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯӦ�������б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryPushAdvList(PageResult page,PushAdvVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.queryPushAdvList() is start...");
        }

        try
        {
            // ����PushAdvDAO���в�ѯ
        	PushAdvDAO.getInstance().queryPushAdvList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��ѯӦ�������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯӦ���б�
     * 
     * @param page
     * @param contentId
     * @param contentName
     * @throws BOException
     */
    public void queryContentNoInPushList(PageResult page,String contentId,String contentName,String channelsId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.queryContentList() is start...");
        }

        try
        {
            // ����PushAdvDAO���в�ѯ
        	PushAdvDAO.getInstance().queryContentNoInPushList(page, contentId,contentName,channelsId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��ѯӦ���б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����Ӧ��id�鿴�Ƿ��Ѵ���
     * 
     * @param contentId
     * @throws BOException
     */
    public void isHasByContentId(String contentId, String type)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.isHasPushByContentId() is start...");
        }

        try
        {
            // ����PushAdvDAO���в�ѯ
        	//��ѯ��Ӧ���Ƿ��Ѵ��������б���
        	boolean isHas = PushAdvDAO.getInstance().isHasPushByContentId(contentId );
        	if(isHas)
        		throw new BOException("������ID�Ѵ������ͣ�",1001);
        	//��ѯ���ݱ����Ƿ���ڸ�Ӧ��
        	isHas =PushAdvDAO.getInstance().isHasByContentId(contentId,type);
        	if(!isHas)
        		throw new BOException("����ID�����ڣ�",1002);
        	
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯӦ�������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ȫ��Ʒ��
     * 
     * @throws DAOException
     */
    public HashMap<String, List<String>> getAll() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.getAllBrand() is start...");
        }
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        try
        {
            // ����PushAdvDAO
        	List<String> lists = PushAdvDAO.getInstance().getAllBrand();
        	for(String brand:lists){
        		map.put(brand, PushAdvDAO.getInstance().getDevice(brand));
        	}
        	return map;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ȫ��Ʒ���쳣��");
        }
    
    }
    
    /**
     * ���ڱ�����������
     * 
     * @param id
     * @param tacCode
     */
    public void save(PushAdvVO pushadv ,String style) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.save() is start...");
        }

        try
        {
            // ����PushAdvDAO
        	PushAdvDAO.getInstance().save(pushadv,style);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������������ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����ɾ��ָ��Ӧ�����͹��
     * 
     * @param id
     * @param contentId
     */
    public void delByContentId(String id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.delByContentId() is start...");
        }

        try
        {
            // ����PushAdvDAO
        	PushAdvDAO.getInstance().delByContentId(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ɾ��ָ��Ӧ�����͹��ʱ�������ݿ��쳣��");
        }
    }
    public String getBrand(String[] redevices){
    	if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.getBrand() is start...");
        }
    	HashSet<String> hset = new HashSet<String>();
    	String brands = "";
    	
    	try {
    		for (String redevice : redevices) {
        		 hset.add(PushAdvDAO.getInstance().getBrand(redevice));
    		}
    		
    		for (String brand : hset) {
				brands += brand +"|";
			}

		} catch (Exception e) {
			logger.error(e);
		}
    	return brands;
    }
    public List<String> getVersions(){

    	if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.getBrand() is start...");
        }
    	List<String> versions = new ArrayList<String>();
    		
    		try {
				versions = PushAdvDAO.getInstance().getVersion();
			} catch (DAOException e) {
				logger.debug("��ѯ�ر��汾�ų���",e);
			}
    	return versions;
    }
    public PushAdvVO getVOById (String id){
    	return PushAdvDAO.getInstance().getVoBycontentid(id);
    }
   
    public int sequences() throws Exception{
    	return PushAdvDAO.getInstance().sequences();
    }
    
    public HashMap<String, List<String>> getNoSelected(String devices) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("PushAdvBO.getNoSelected() is start...");
        }
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        
        try
        {
            // ����PushAdvDAO
        	List<String> lists = PushAdvDAO.getInstance().getAllBrand();
        	for(String brand:lists){
        		map.put(brand, PushAdvDAO.getInstance().getHandleDevice(brand , devices));
        	}
        	return map;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ȫ��Ʒ���쳣��");
        }
    
    }
    
    
}
