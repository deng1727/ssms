/*
 * 文件名：BaseFileFactory.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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

        // 类型导入对象
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CATEGORY))
        {
            export = new CategoryExportFile();
        }
        // 内容导入对象
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CONTENT))
        {
            export = new ContentExportFile();
        }
        // 商品导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_REFERENCE))
        {
            export = new ReferenceExportFile();
        }
        // 推荐导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND))
        {
            export = new RecommendExportFile();
        }
        // 推荐关联关系导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK))
        {
            export = new RecommendLinkExportFile();
        }
        return export;
    }
}
