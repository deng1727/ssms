/*
 * 文件名：BaseFileFactory.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志对象
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
     * 用来返回执行对象
     * 
     * @param type 执行对象类型
     * @param isSendMail 是否发送邮件
     * @return
     */
    public BaseFileAbility getBaseFile(String type)
    {
        BaseFileAbility export = null;

        // 视频文件内容导入对象
        if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO))
        {
            export = new VideoExportFile(true);
        }
        // 视频文件内容增量导入对象
        if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_ADD))
        {
            export = new VideoExportFile(false);
        }
        // 机型文件内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_DEVICE))
        {
            export = new DeviceExportFile();
        }
        // 码率文件内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_CODERATE))
        {
            export = new CodeRateExportFile();
        }
        // 节目详情内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL))
        {
            export = new ProgramExportFile();
        }
        // 栏目文件内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_NODE))
        {
            export = new NodeExportFile();
        }
        // 直播节目单内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_LIVE))
        {
            export = new LiveExportFile();
        }
        // 排行榜内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_RANK))
        {
            export = new RankExportFile();
        }
        // 产品内容导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_PRODUCT))
        {
            export = new ProductExportFile();
        }
        // 视频节目统计
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEODETAIL))
        {
            export = new VideoDetailExportFile();
        }
        // 视频文件内容按小时增量导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_VIDEO_ADD_HOUR))
        {
            export = new VideoByHourExportFile();
        }
        //节目详情文件内容按小时增量导入对象
        else if (type.equals(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR))
        {
            export = new ProgramByHourExportFile();
        }
        //热点内容推荐数据
        else if (type.equals(BaseVideoConfig.FILE_TYPE_RECOMMEND_ADD_HOUR))
        {
            export = new RecommendByHourExportFile();
        }
        
        return export;
    }
}
