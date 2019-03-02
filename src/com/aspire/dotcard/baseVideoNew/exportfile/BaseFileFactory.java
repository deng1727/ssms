/*
 * 文件名：BaseFileFactory.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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

        // 视频文件 video
        if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_ADD))
        {
            export = new VideoExportFile();
        }
        // 节目详情内容导入对象 program
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_DETAIL))
        {
            export = new ProgramExportFile();
        }
        // 栏目文件内容导入对象 node
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_NODE))
        {
            export = new NodeExportFile();
        }
        // 直播节目单内容导入对象 live
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_LIVE))
        {
            export = new LiveExportFile();
        }
        // 产品内容导入对象
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_PRODUCT))
        {
            export = new ProductExportFile();
        }
        // 视频节目统计
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEODETAIL))
        {
            export = new VideoDetailExportFile();
        }
        // 视频内容集节点
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COLLECT_NODE))
        {
            export = new CollectNodeExportFile();
        }
        // 视频内容集
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COLLECT))
        {
            export = new CollectExportFile();
        }
     // 视频文件内容按小时增量导入对象
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_VIDEO_ADD_HOUR))
        {
            export = new VideoByHourExportFile();
        }
        //节目详情文件内容按小时增量导入对象
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_PROGRAM_ADD_HOUR))
        {
            export = new ProgramByHourExportFile();
        }
        //热点内容推荐数据
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_RECOMMEND_ADD_HOUR))
        {
            export = new RecommendByHourExportFile();
        }
        //热点内容推荐数据
        else if (type.equals(BaseVideoNewConfig.FILE_TYPE_COST))
        {
            export = new CostProductRelaExportFile();
        }
        return export;
    }
}
