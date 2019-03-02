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
     * @return
     */
    public BaseFileAbility getBaseFile(String type)
    {
        BaseFileAbility export = null;

        //��Ƶ��Ŀ�ļ�������� program
        if(BaseVideoConfig.FILE_TYPE_PROGRAM.equals(type)){
        	export = new ProgramExportFile();
        }
        //��Ƶ��Ŀ�ļ���Сʱ�����������
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR.equals(type)){
        	export = new ProgramByHourExportFile();
        }
        //��Ƶҵ���Ʒ�ļ��������
        else if(BaseVideoConfig.FILE_TYPE_PRD_PKG.equals(type)){
        	export = new PrdPkgExportFile();
        }
        //��Ƶ��Ʒ�������Ʒ������ļ��������
        else if(BaseVideoConfig.FILE_TYPE_PKG_SALES.equals(type)){
        	export = new PkgSalesExportFile();
        }
        //��Ƶ�Ʒ������ļ��������
        else if(BaseVideoConfig.FILE_TYPE_PRODUCT.equals(type)){
        	export = new ProductExportFile();
        }
        //��Ƶ�񵥷����ļ��������
        else if(BaseVideoConfig.FILE_TYPE_TOPLIST.equals(type)){
        	export = new TopListExportFile();
        }
        //��Ƶ�ȵ������б��ļ��������
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT.equals(type)){
        	export = new HotcontentExportFile();
        }
        //��Ƶ��Ŀ�����ļ��������
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_XML.equals(type)){
        	export = new ProgramExportXMLFile();
        }
        //��Ƶ��Ŀֱ��XML�ļ��������
        else if(BaseVideoConfig.FILE_TYPE_LIVE_XML.equals(type)){
        	export = new LiveExportXmlFile();
        }
        //��Ƶ�ȵ�����XML�ļ��������
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT_XML.equals(type)){
        	export = new HotcontentExportXmlFile();
        }
        //��Ʒ�����Ʒ�Api��������
        else if(BaseVideoConfig.FILE_TYPE_PRD_API.equals(type)){
        	export = new PkgSalesApiExportFile();
        }
        //ҵ���Ʒapi����
        else if(BaseVideoConfig.FILE_TYPE_PKG_SALES_API.equals(type)){
        	export = new PrdPkgApiExportFile();
        }
        //�ȵ������б�api��������
        else if(BaseVideoConfig.FILE_TYPE_HOTCONTENT_API.equals(type)){
        	export = new HotcontentApiExportFile();
        }
        //�Ʒ���Ϣ����Api����
        else if(BaseVideoConfig.FILE_TYPE_PRODUCT_API.equals(type)){
        	export = new ProductApiExportFile();
        } 
      //��ͨ��Ŀ����Api����
        else if(BaseVideoConfig.FILE_TYPE_PROGRAM_API.equals(type)){
        	export = new ProgramApiExportFile();
        } 
        return export;
    }
}
