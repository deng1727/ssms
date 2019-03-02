package com.aspire.dotcard.basevideosync.exportfile;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.LiveExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.PrdPkgApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.PrdPkgExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProductApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProductExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.TopListExportFile;

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

        //视频节目文件导入对象 program
        if(BaseVideoConfig.FILE_TYPE_PROGRAM.equals(type)){
        	export = new ProgramExportFile();
        }
        //视频节目文件按小时增量导入对象
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR.equals(type)){
        	export = new ProgramByHourExportFile();
        }
        //视频业务产品文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_PRD_PKG.equals(type)){
        	export = new PrdPkgExportFile();
        }
        //视频产品包促销计费数据文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_PKG_SALES.equals(type)){
        	export = new PkgSalesExportFile();
        }
        //视频计费数据文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_PRODUCT.equals(type)){
        	export = new ProductExportFile();
        }
        //视频榜单发布文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_TOPLIST.equals(type)){
        	export = new TopListExportFile();
        }
        //视频热点主题列表文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT.equals(type)){
        	export = new HotcontentExportFile();
        }
        //视频节目详情文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_XML.equals(type)){
        	export = new ProgramExportXMLFile();
        }
        //视频节目直播XML文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_LIVE_XML.equals(type)){
        	export = new LiveExportXmlFile();
        }
        //视频热点主题XML文件导入对象
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT_XML.equals(type)){
        	export = new HotcontentExportXmlFile();
        }
        //产品促销计费Api请求数据
        else if(BaseVideoConfig.FILE_TYPE_PRD_API.equals(type)){
        	export = new PkgSalesApiExportFile();
        }
        //业务产品api请求
        else if(BaseVideoConfig.FILE_TYPE_PKG_SALES_API.equals(type)){
        	export = new PrdPkgApiExportFile();
        }
        //热点主题列表api请求数据
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT_API.equals(type)){
        	export = new HotcontentApiExportFile();
        }
        //计费信息数据Api请求
        else if(BaseVideoConfig.FILE_TYPE_PRODUCT_API.equals(type)){
        	export = new ProductApiExportFile();
        } 
      //普通节目数据Api请求
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_API.equals(type)){
        	export = new ProgramApiExportFile();
        } 
        return export;
    }
}
