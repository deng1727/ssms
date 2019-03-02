/*
 * �ļ�����SearchFileSubjectImpl.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ����ʵ�ַ����߷���
 */

package com.aspire.ponaadmin.web.dataexport.searchfile.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.dotcard.searchfile.SearchFileGenerateDAO;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileObserver;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileSubject;

/**
 * <p>
 * Title: ������ʵ����
 * </p>
 * <p>
 * Description: ����ʵ�ַ����߷���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class SearchFileSubjectImpl implements SearchFileSubject
{

    private static final JLogger LOG = LoggerFactory.getLogger(SearchFileSubjectImpl.class);

    /**
     * �������б�
     */
    private List observers;

    private Map goodsidMappingForMO;

    private Map goodsisMappingForWWW;

    private Map goodsisMappingForWAP;
    
    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    /**
     * ȫ�������б�
     */
    private ResultSet dataList;

    /**
     * ���췽��
     */
    public SearchFileSubjectImpl(Map goodsidMappingForMO,
                                 Map goodsisMappingForWWW,
                                 Map goodsisMappingForWAP)
    {

        observers = new ArrayList();
        this.goodsidMappingForMO = goodsidMappingForMO;
        this.goodsisMappingForWWW = goodsisMappingForWWW;
        this.goodsisMappingForWAP = goodsisMappingForWAP;
    }

    /**
     * ��Ӷ�����
     * 
     * @param o
     */
    public void registerObserver(SearchFileObserver o)
    {

        observers.add(o);
    }

    /**
     * ���Ѷ�����
     * 
     * @throws BOException
     * 
     * @throws BOException
     */
    public void notifyObserver(ResultSet rs) throws BOException
    {

        try
        {
            // ѭ���õ�������Ϣ
            while (rs.next())
            {
                // ��������
                String type = rs.getString("type");
                // �����ṩ������ ��ʾ����ҵ�񣬺ͻ���ҵ��
                String provider = rs.getString("provider");
                // �û���Դ��LΪ�㶫�û� GΪ�����û���
                String servattr = rs.getString("servattr");
                // ��ϱ�ʶ�� type&provider&servattr
                String marker = getMarker(type, provider, servattr);

                for (int i = 0; i < observers.size(); i++)
                {
                    SearchFileObserver element = ( SearchFileObserver ) observers.get(i);

                    if (element.isAppMarker(marker))
                    {
                        try
                        {
                            element.addDate(rs);
                        }
                        catch (Exception e)
                        {
                        	LOG.error("���������������ˣ�",e);
                            observers.remove(i);
                            failureList.add(element.getMailContent(false));
                        }
                    }
                }
            }
            rs.close();
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣.", e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
            catch (Exception e1)
            {
            }
        }
    }

    /**
     * ������
     * 
     * @throws DAOException
     * @throws BOException
     * @throws
     */
    public void prepareAllData() throws DAOException, BOException
    {

        if (goodsidMappingForMO == null)
        {
            goodsidMappingForMO = SearchFileGenerateDAO.getInstance()
                                                       .getAllIDMappingGoodsId();
        }

        if (goodsisMappingForWWW == null)
        {
            goodsisMappingForWWW = SearchFileGenerateDAO.getInstance()
                                                        .getAllIDMappingGoodsIdByWWW();
        }

        if (goodsisMappingForWAP == null)
        {
            goodsisMappingForWAP = SearchFileGenerateDAO.getInstance()
                                                        .getAllIDMappingGoodsIdByWAP();
        }
        
        int sum = SearchFileGenerateDAO.getInstance().getAllContentDataSum();
        
        LOG.info("��ǰ��������ȫ������������Ϊ:" + sum);
        
        int pageContentNum = SearchFileConfig.pageSize;
        
        // ���Ҫ�����ļ���ȫ����������ʮ�򡣾ͷ����Ĳ�ѯ�õ��������ɡ�
        if(sum > pageContentNum)
        {
        	// �õ���ҳ��
        	int pageNum = (sum + pageContentNum - 1) / pageContentNum;
        	int start = 0;
        	int end = 0;
        	
        	LOG.info("��ǰ����������ҳ����ÿҳΪ:" + pageContentNum + " ����" + pageNum + "ҳ��ѯ.");
        	
        	for(int i=0; i<pageNum ;i++)
        	{
        		start = i * pageContentNum;
        		end = (i + 1) * pageContentNum;
        		
        		LOG.info("�˴β�ѯ����������ҳ����Ϊ>��:" + start + "��<=��" + end + "��.");
        		
        		dataList = SearchFileGenerateDAO.getInstance().getAllContentDataByPageNum(start, end);
        		notifyObserver(dataList);
        	}
        }
        else
        {
        	dataList = SearchFileGenerateDAO.getInstance().getAllContentData();
        	notifyObserver(dataList);
        }

        this.createFiles();

    }

    /**
     * �����ļ�
     */
    public void createFiles()
    {

        for (Iterator iter = observers.iterator(); iter.hasNext();)
        {
            SearchFileObserver element = ( SearchFileObserver ) iter.next();

            element.createFile();
            
            sucessList.add(element.getMailContent(true));
        }
    }

    /**
     * ���ص�ǰ�������ݵı�ǡ�����ƥ�������ļ�
     * 
     * @param type ��������
     * @param provider �����ṩ������
     * @param servattr �û���Դ
     * @return ��ϱ�ʶ��
     */
    private String getMarker(String type, String provider, String servattr)
    {

        return type + "&" + provider + "&" + servattr;
    }

    /**
     * �����Ż������ͣ��漴����һ��goodsid
     * 
     * @param relation
     * @param id
     * @return
     */
    public String getAppGoodsId(int relation, String id)
    {

        switch (relation)
        {
            case SearchFileConstants.RELATION_W:
                return ( String ) goodsisMappingForWWW.get(id);
            case SearchFileConstants.RELATION_O:
                return ( String ) goodsidMappingForMO.get(id);
            case SearchFileConstants.RELATION_A:
                return ( String ) goodsisMappingForWAP.get(id);
            default:
                return null;
        }
    }
    
    public List getFailureList()
    {
    
        return failureList;
    }

    
    public List getSucessList()
    {
    
        return sucessList;
    }
}
