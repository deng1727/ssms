/*
 * 文件名：AppBaseFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.dataexport.basefile.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.config.ServerInfo;
import com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title: 应用信息同步生成文件
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class AppBaseFile extends BaseFileAbstract
{

    public AppBaseFile()
    {
        // select g.contentid, g.name, decode(g.averagemark, null, 3,
        // g.averagemark) averagemark, decode(b.type, 'nt:gcontent:appGame',
        // '1', 'nt:gcontent:appSoftWare', '0', 'nt:gcontent:appTheme', '2')
        // type, g.appcateid, decode(g.icpcode, '100246', '0', g.icpcode, '2')
        // icpcode, g.keywords, g.introduction, g.scantimes, g.fulldevicename,
        // v.mobileprice / 10 price, g.wwwpropapicture2, g.wwwpropapicture3,
        // g.picture1 from t_r_base b, t_r_gcontent g, v_service v where
        // g.contentid = v.contentid and g.provider = 'O' and g.SERVATTR = 'G'
        // and g.subtype <> '6' and g.subtype <> '11' and g.subtype <> '12' and
        // (v.paytype = '0' or v.paytype is null) and g.id = b.id and
        // g.wwwpropapicture2 is not null and g.wwwpropapicture3 is not null and
        // g.picture1 is not null
        this.sql = "dataexport.basefile.AppBaseFile.getDBData";
        
        this.fileName = ServerInfo.getAppRootPath() + File.separator
                        + "ftpdata" + File.separator + "MStore"
                        + File.separator + "BSB" + File.separator + "APPNew_"
                        + PublicUtil.getCurDateTime("yyyyMMdd") + ".CSV";
        
        this.fileType = BaseFileConstants.FILE_TYPE_CSV;
        
        this.toFileName = "APPNew_" + PublicUtil.getCurDateTime("yyyyMMdd")
                          + ".CSV";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract#fromObject(java.sql.ResultSet)
     */
    protected Object[] fromObject(ResultSet rs) throws SQLException
    {
        String[] obj = new String[14];
        
        obj[0] = rs.getString("contentid");
        obj[1] = rs.getString("name");
        obj[2] = rs.getString("averagemark");
        obj[3] = rs.getString("type");
        obj[4] = rs.getString("appcateid");
        obj[5] = rs.getString("icpcode");
        obj[6] = rs.getString("keywords");
        obj[7] = rs.getString("introduction");
        obj[8] = rs.getString("scantimes");
        obj[9] = getClobString(rs.getClob("fulldevicename"));
        obj[10] = rs.getString("price");
        obj[11] = rs.getString("wwwpropapicture2");
        obj[12] = rs.getString("wwwpropapicture3");
        obj[13] = rs.getString("picture1");
  
        Object [] robj = new Object[2];
        robj[0] = rs.getString("contentid");//去重的ID
        robj[1] = obj;
        return robj;
    }
}
