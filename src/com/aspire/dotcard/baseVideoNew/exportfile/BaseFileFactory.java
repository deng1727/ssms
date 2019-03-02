/*
 * �ļ�����BaseFileFactory.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.CostProductRelaExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.CollectExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.CollectNodeExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.LiveExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.NodeExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProductExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoDetailExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoExportFile;

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

        // ��Ƶ�ļ� video
        if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_ADD))
        {
            export = new VideoExportFile();
        }
        // ��Ŀ�������ݵ������ program
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_DETAIL))
        {
            export = new ProgramExportFile();
        }
        // ��Ŀ�ļ����ݵ������ node
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_NODE))
        {
            export = new NodeExportFile();
        }
        // ֱ����Ŀ�����ݵ������ live
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_LIVE))
        {
            export = new LiveExportFile();
        }
        // ��Ʒ���ݵ������
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_PRODUCT))
        {
            export = new ProductExportFile();
        }
        // ��Ƶ��Ŀͳ��
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEODETAIL))
        {
            export = new VideoDetailExportFile();
        }
        // ��Ƶ���ݼ��ڵ�
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COLLECT_NODE))
        {
            export = new CollectNodeExportFile();
        }
        // ��Ƶ���ݼ�
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COLLECT))
        {
            export = new CollectExportFile();
        }
     // ��Ƶ�ļ����ݰ�Сʱ�����������
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_ADD_HOUR))
        {
            export = new VideoByHourExportFile();
        }
        //��Ŀ�����ļ����ݰ�Сʱ�����������
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_PROGRAM_ADD_HOUR))
        {
            export = new ProgramByHourExportFile();
        }
        //�ȵ������Ƽ�����
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_RECOMMEND_ADD_HOUR))
        {
            export = new RecommendByHourExportFile();
        }
        //�ȵ������Ƽ�����
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COST))
        {
            export = new CostProductRelaExportFile();
        }
        return export;
    }
}
