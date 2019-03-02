/*
 * �ļ�����BaseFileFactory.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.impl.CodeRateExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.DeviceExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.LiveExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.NodeExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProductExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.RankExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoDetailExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoExportFile;

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

        // ��Ƶ�ļ����ݵ������
        if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO))
        {
            export = new VideoExportFile(true);
        }
        // ��Ƶ�ļ����������������
        if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_ADD))
        {
            export = new VideoExportFile(false);
        }
        // �����ļ����ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_DEVICE))
        {
            export = new DeviceExportFile();
        }
        // �����ļ����ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_CODERATE))
        {
            export = new CodeRateExportFile();
        }
        // ��Ŀ�������ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL))
        {
            export = new ProgramExportFile();
        }
        // ��Ŀ�ļ����ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_NODE))
        {
            export = new NodeExportFile();
        }
        // ֱ����Ŀ�����ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_LIVE))
        {
            export = new LiveExportFile();
        }
        // ���а����ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_RANK))
        {
            export = new RankExportFile();
        }
        // ��Ʒ���ݵ������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_PRODUCT))
        {
            export = new ProductExportFile();
        }
        // ��Ƶ��Ŀͳ��
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEODETAIL))
        {
            export = new VideoDetailExportFile();
        }
        // ��Ƶ�ļ����ݰ�Сʱ�����������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_ADD_HOUR))
        {
            export = new VideoByHourExportFile();
        }
        //��Ŀ�����ļ����ݰ�Сʱ�����������
        else if (type.equals(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR))
        {
            export = new ProgramByHourExportFile();
        }
        //�ȵ������Ƽ�����
        else if (type.equals(BaseVideoConfig.FILE_TYPE_RECOMMEND_ADD_HOUR))
        {
            export = new RecommendByHourExportFile();
        }
        
        return export;
    }
}
