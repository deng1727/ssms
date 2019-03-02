package com.aspire.ponaadmin.web.dataexport.basefile;

import com.aspire.common.exception.BOException;

/**
 * 生成文件接口：定义生成文件对象包含的基本属性
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version
 */
public interface BaseFileObject
{
    /**
     * 生成文件
     */
    public void createFile() throws BOException;
}
