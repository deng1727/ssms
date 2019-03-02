/*
 * �ļ�����BaseFileFactory.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.basecolorcomic.exportfile;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.dotcard.basecolorcomic.exportfile.impl.CategoryExportFile;
import com.aspire.dotcard.basecolorcomic.exportfile.impl.ContentExportFile;
import com.aspire.dotcard.basecolorcomic.exportfile.impl.RecommendExportFile;
import com.aspire.dotcard.basecolorcomic.exportfile.impl.RecommendLinkExportFile;
import com.aspire.dotcard.basecolorcomic.exportfile.impl.ReferenceExportFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseFileFactory
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseFileFactory.class);

    private static BaseFileFactory factory = new BaseFileFactory();

    private BaseFileFactory()
    {
    }

    public static BaseFileFactory getInstance()
    {
        return factory;
    }

    /**
     * ��������ִ�ж���
     * 
     * @param type ִ�ж�������
     * @param isSendMail �Ƿ����ʼ�
     * @return
     */
    public BaseFileAbility getBaseFile(String type)
    {
        BaseFileAbility export = null;

        // ���͵������
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CATEGORY))
        {
            export = new CategoryExportFile();
        }
        // ���ݵ������
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CONTENT))
        {
            export = new ContentExportFile();
        }
        // ��Ʒ�������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_REFERENCE))
        {
            export = new ReferenceExportFile();
        }
        // �Ƽ��������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND))
        {
            export = new RecommendExportFile();
        }
        // �Ƽ�������ϵ�������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK))
        {
            export = new RecommendLinkExportFile();
        }
        return export;
    }
}
