
package com.aspire.ponaadmin.web.dataexport.experience;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CreateFileByExperience
{
    private static final JLogger LOG = LoggerFactory.getLogger(CreateFileByExperience.class);

    public boolean export(CommonExperience export)
    {
        try
        {
            LOG.info("开始导出文件。type=" + export.getName());
            List list = export.getDBData();
            /*
             * List exportList = new ArrayList(list.size()); for (int i = 0; i <
             * list.size(); i++) {
             * exportList.add(export.transformExportItems((List) list.get(i))); }
             */
            export.writeToFile(list);
            LOG.info("导出文件成功。type=" + export.getName());
            return true;
        }
        catch (Exception e)
        {
            LOG.error("导出文件出错。type=" + export.getName()+e);
            return false;
        }
    }
}
