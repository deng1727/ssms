/*
 * 文件名：SearchFileGameByJTForWWW.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  实现具体订阅者方法
 */
package com.aspire.ponaadmin.web.dataexport.searchfile.impl;

import com.aspire.common.exception.BOException;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileBase;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileSubject;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>
 * Title: 用于生成给www的集团游戏订阅者实现类
 * </p>
 * <p>
 * Description: 实现具体订阅者方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class SearchFileThemeByJTForWWW extends SearchFileBase
{
    /**
     * 注册订阅到发布者上
     * 
     * @param subject
     * @throws BOException
     */
    public SearchFileThemeByJTForWWW(SearchFileSubject subject)
                    throws BOException
    {
        // 注册订阅者至发布者
        this.subject = subject;
        this.subject.registerObserver(this);

        // 游戏类型
        this.type = RepositoryConstants.TYPE_APPTHEME;

        // 全网应用 jt文件名的是查為G的，不加jt文件名的是查全部信息
        this.servattr = SearchFileConstants.SERVATTR_G;

        // 生成文件类型。www
        this.relation = SearchFileConstants.RELATION_W;

        // 生成文件名
        this.fileName = "appTheme.jt.txt";

        // 准备工作
        if (!this.prepareData())
        {
            throw new BOException("初始化失敗");
        }
    }

}
