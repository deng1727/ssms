/*
 * �ļ�����SearchFileSubject.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ڶ��巢���߷����ӿ�
 */
package com.aspire.ponaadmin.web.dataexport.searchfile;

import java.sql.ResultSet;

import com.aspire.common.exception.BOException;


/**
 * <p>Title: �����߽ӿ�</p>
 * <p>Description: ���ڶ��巢���߷����ӿ�</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public interface SearchFileSubject
{
    /**
     * ��Ӷ�����
     * @param o
     */
    public void registerObserver(SearchFileObserver o);
    
    /**
     * ���Ѷ�����
     * @throws BOException 
     */
    public void notifyObserver(ResultSet rs) throws BOException;
    
    
    /**
     * �����Ż������ͣ��漴����һ��goodsid
     * 
     * @param relation
     * @param id
     * @return
     */
    public String getAppGoodsId(int relation, String id);
}
