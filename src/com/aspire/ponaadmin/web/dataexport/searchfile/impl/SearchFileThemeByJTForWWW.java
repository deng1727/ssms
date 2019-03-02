/*
 * �ļ�����SearchFileGameByJTForWWW.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ʵ�־��嶩���߷���
 */
package com.aspire.ponaadmin.web.dataexport.searchfile.impl;

import com.aspire.common.exception.BOException;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileBase;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileSubject;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>
 * Title: �������ɸ�www�ļ�����Ϸ������ʵ����
 * </p>
 * <p>
 * Description: ʵ�־��嶩���߷���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class SearchFileThemeByJTForWWW extends SearchFileBase
{
    /**
     * ע�ᶩ�ĵ���������
     * 
     * @param subject
     * @throws BOException
     */
    public SearchFileThemeByJTForWWW(SearchFileSubject subject)
                    throws BOException
    {
        // ע�ᶩ������������
        this.subject = subject;
        this.subject.registerObserver(this);

        // ��Ϸ����
        this.type = RepositoryConstants.TYPE_APPTHEME;

        // ȫ��Ӧ�� jt�ļ������ǲ��G�ģ�����jt�ļ������ǲ�ȫ����Ϣ
        this.servattr = SearchFileConstants.SERVATTR_G;

        // �����ļ����͡�www
        this.relation = SearchFileConstants.RELATION_W;

        // �����ļ���
        this.fileName = "appTheme.jt.txt";

        // ׼������
        if (!this.prepareData())
        {
            throw new BOException("��ʼ��ʧ��");
        }
    }

}
