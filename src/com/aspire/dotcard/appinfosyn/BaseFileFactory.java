package com.aspire.dotcard.appinfosyn;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appinfosyn.impl.AppXMLFile;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;


public class BaseFileFactory {

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
     * @return
     */
    public BaseFileAbility getBaseFile(String type)
    {
        BaseFileAbility export = null;

    if(BaseVideoConfig.FILE_TYPE_PROGRAM_XML.equals(type)){
        	export = new AppXMLFile();
        }
      
        return export;
    }
}
