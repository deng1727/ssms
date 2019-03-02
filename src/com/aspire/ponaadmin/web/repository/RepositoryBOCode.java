package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源管理的业务处理返回码定义类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class RepositoryBOCode
{

    /**
     * 成功
     */
    public static final int SUCC = 0 ;

    /**
     * 系统内部错误
     */
    public static final int FAIL = 1 ;

    /**
     * 参数非法
     */
    public static final int INVALID_PARA = 2 ;

    /**
     * 父分类下已经有同名的分类存在
     */
    public static final int CATEGORY_NAME_EXISTED = 1001;

    /**
     * 分类下存在资源或子分类，不能删除
     */
    public static final int CATEGORY_CONTENT_EXISTED = 1002;

    /**
     * 资源被引用，不能删除
     */
    public static final int CONTENT_REFERENCE = 1003;

    
    /**
     * 添加了父货架不包含的门户店
     */
    public static final int CATEGORY_RELATION_PARERR = 1005;
    
    /**
     * 删除了子货架包含的门户店
     */
    public static final int CATEGORY_RELATION_SUBERR = 1006;
    
    /**
     * 货架预览图上传失败
     */
    public static final int CATEGORY_CATE_PIC_UPLOAD=1010;
    
    /**
     * 货架关联机型关系时失败
     */
    public static final int CATEGORY_DEVICE=1020;
    
    /**
     * 删除货架关联机型关系时失败
     */
    public static final int DEL_CATEGORY_DEVICE=1021;
    
    /**
     * 变更货架地域适配信息时小于子货架地域信息集合而失败
     */
    public static final int UPDATE_CATEGORY_CITY=1022;
    
    /**
     * 新增货架地域适配信息时大于父货架地域信息集合而失败
     */
    public static final int ADD_CATEGORY_CITY=1023;
    
    /**
     * 新增货架地域适配信息时大于父货架地域信息集合而失败
     */
    public static final int ADD_CATEGORY_PIC=1024;
    
    /**
     * 新增货架地域适配信息时大于父货架地域信息集合而失败
     */
    public static final int UPDATE_CATEGORY_PIC=1025;
    
    /**
     * 此货架名称在同父货架下已存在
     */
    public static final int ADD_UPDATE_CATEGORY_NAME=1026;
    
}
