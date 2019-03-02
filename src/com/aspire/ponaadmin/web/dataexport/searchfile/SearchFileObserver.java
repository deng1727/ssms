/*
 * �ļ�����SearchFileObserver.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ڶ��嶩���߷����ӿ�
 */
package com.aspire.ponaadmin.web.dataexport.searchfile;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.exception.BOException;


/**
 * <p>Title: �����߽ӿ�</p>
 * <p>Description: ���ڶ��嶩���߷����ӿ�</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public interface SearchFileObserver
{
    /**
     * �ɷ����ߴ���������
     * @param rs
     * @throws BOException 
     * @throws SQLException 
     */
    public void addDate(ResultSet rs) throws BOException, SQLException;
    
    /**
     * ���ص�ǰ�����Ƿ��Ƕ�������������
     * @param marker ���ݱ�ʶ
     * @return
     */
    public boolean isAppMarker(String marker);
    
    /**
     * �����ļ�
     */
    public void createFile();
    
    /**
     * �������ڷ����ʼ�ʱ����Ϣ
     * @param isTrue �����ļ��Ƿ�ɹ�
     * @return
     */
    public String getMailContent(boolean isTrue);
}
