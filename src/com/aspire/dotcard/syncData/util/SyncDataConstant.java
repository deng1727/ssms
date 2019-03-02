package com.aspire.dotcard.syncData.util;


public interface SyncDataConstant
{
    //发布状态的内容
    final String CONTENT_TYPE_RELEASE = "0006";
    
    //过期状态的内容
    final String CONTENT_TYPE_OVERDUE = "0008";
    
    //内容类型
    final String CONTENT_TYPE = "content";
    
    //业务类型
    final String SERVICE_TYPE = "service";
    
    //商品编码
    final String GOODS_CODE = "商品编码:";
    
    //货架编码
    final String CATEGORY_CODE = "货架编码:";
    
    //货架名称
    final String CATEGORY_NAME = "货架名称:";
    
    //内容编码
    final String CONTENT_CODE = "内容编码:";
    
    //内容名称
    final String CONTENT_NAME = "内容名称:";
    
    //企业代码
    final String ICPCODE = "企业代码:";
    
    //业务代码
    final String ICPSERVID = "业务代码:";
    
    //业务名称
    final String SERVICE_NAME = "业务名称:";
    
    //换行符号
    final String CHANGE_LINE = "<br>";
    /**
     * 同步更新
     */
    final int SYNC_UPDATE=1;
    /**
     * 同步上线
     */
    final int SYNC_ADD=2;
    /**
     * 同步下线
     */
    final int SYNC_DEL=3;
    
    /**
     * 下线应用类型是mm应用
     */
    final String DEL_CONTENT_TYPE_MM = "1";
    
    /**
     * 下线应用类型是创业大赛应用
     */
    final String DEL_CONTENT_TYPE_CY = "2";
    
    /**
     * 下线应用类型是游戏类型
     */
    final String DEL_CONTENT_TYPE_GAME = "3";
    
    /**
     * 内容同步时内容适配关系不同步
     */
    final String SYN_RESOURCE_TYPE_NO = "0";
    
    /**
     * 内容同步时内容适配关系增量同步
     */    
    final String SYN_RESOURCE_TYPE_ADD = "1";
    
    /**
     * 内容同步时内容适配关系全量同步
     */
    final String SYN_RESOURCE_TYPE_ALL = "2";
    
}
