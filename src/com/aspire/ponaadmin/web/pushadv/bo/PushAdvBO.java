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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PushAdvBO.class);

    private static PushAdvBO instance = new PushAdvBO();

    private PushAdvBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static PushAdvBO getInstance()
    {
        return instance;
    }
    
    /**
     * 用于查询应用推送列表
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
            // 调用PushAdvDAO进行查询
        	PushAdvDAO.getInstance().queryPushAdvList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询查询应用推送列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询应用列表
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
            // 调用PushAdvDAO进行查询
        	PushAdvDAO.getInstance().queryContentNoInPushList(page, contentId,contentName,channelsId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询查询应用列表时发生数据库异常！");
        }
    }
    
    /**
     * 根据应用id查看是否已存在
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
            // 调用PushAdvDAO进行查询
        	//查询该应用是否已存在推送列表里
        	boolean isHas = PushAdvDAO.getInstance().isHasPushByContentId(contentId );
        	if(isHas)
        		throw new BOException("该推送ID已存在推送！",1001);
        	//查询内容表里是否存在该应用
        	isHas =PushAdvDAO.getInstance().isHasByContentId(contentId,type);
        	if(!isHas)
        		throw new BOException("推送ID不存在！",1002);
        	
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询应用推送列表时发生数据库异常！");
        }
    }
    
    /**
     * 获得全部品牌
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
            // 调用PushAdvDAO
        	List<String> lists = PushAdvDAO.getInstance().getAllBrand();
        	for(String brand:lists){
        		map.put(brand, PushAdvDAO.getInstance().getDevice(brand));
        	}
        	return map;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("获得全部品牌异常！");
        }
    
    }
    
    /**
     * 用于保存新增推送
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
            // 调用PushAdvDAO
        	PushAdvDAO.getInstance().save(pushadv,style);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("保存新增推送时发生数据库异常！");
        }
    }
    
    /**
     * 用于删除指定应用推送广告
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
            // 调用PushAdvDAO
        	PushAdvDAO.getInstance().delByContentId(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("用于删除指定应用推送广告时发生数据库异常！");
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
				logger.debug("查询必备版本号出错",e);
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
            // 调用PushAdvDAO
        	List<String> lists = PushAdvDAO.getInstance().getAllBrand();
        	for(String brand:lists){
        		map.put(brand, PushAdvDAO.getInstance().getHandleDevice(brand , devices));
        	}
        	return map;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("获得全部品牌异常！");
        }
    
    }
    
    
}
