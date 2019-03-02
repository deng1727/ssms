/*
 * 文件名：DeviceBaseFile.java
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
 * Title: 机型信息同步文件
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
public class DeviceBaseFile extends BaseFileAbstract
{
    public DeviceBaseFile()
    {
        // select t.device_id,t.device_name,b.brand_name from t_device
        // t,t_device_brand b where t.brand_id=b.brand_id
        this.sql = "dataexport.basefile.DeviceBaseFile.getDBData";
        
        this.fileName = ServerInfo.getAppRootPath() + File.separator
                        + "ftpdata" + File.separator + "MStore"
                        + File.separator + "BSB" + File.separator + "MMJiXing_"
                        + PublicUtil.getCurDateTime("yyyyMMdd") + ".txt";
        
        this.fileType = BaseFileConstants.FILE_TYPE_TXT;
        
        this.compart = "|";
        
        this.toFileName = "MMJiXing_" + PublicUtil.getCurDateTime("yyyyMMdd")
                          + ".txt";
    }

    /**
     * 用于组装数据集合
     */
    protected Object[] fromObject(ResultSet rs) throws SQLException
    {
        Object[] obj = new Object[4];

        obj[0] = String.valueOf(rs.getInt("device_id"));
        obj[1] = this.checkFieldLength(rs.getString("device_name"), 80);
        obj[2] = this.checkFieldLength(rs.getString("brand_name"), 80);
        obj[3] = "0";
        Object [] robj = new Object[2];
        robj[0] = new Integer(rs.getInt("device_id"));
        robj[1] = obj;
        return robj;
    }
}
