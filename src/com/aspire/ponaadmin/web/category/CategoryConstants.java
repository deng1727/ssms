package com.aspire.ponaadmin.web.category;


public class CategoryConstants {
	/**
	 * T_CATERULE_COND表
	 */
	//获取条件，从产品库中获取自有业务内容。（软件，游戏，主题），不包含基地游戏
	public static final int CONDTYPE_PROD_OWNER = 10;
	//获取条件，从产品库中获取外部接入的内容（比如，图书，音乐，等）
	public static final int CONDTYPE_PROD_OTHERS = 12;
	/**
	 * 只处理基地游戏
	 */
	public static final int CONDTYPE_PROD_BASEGAME = 11;
    
    /**
     * 只处理品牌店套餐业务
     */
    public static final int CONDTYPE_PROD_BRAND = 13;
    
    /**
     * 只处理基本业务
     */
    public static final int CONDTYPE_PROD_BASE = 14;
    
	//获取条件，从精品库中获取
	public static final int CONDTYPE_ELITE = 1;


	/**
	 * 刷新货架下商品 0
	 */
	public static final int RULETYPE_REFRESH = 0;
	/**
	 * 货架下商品重排顺序  1
	 */
	public static final int RULETYPE_REORDER = 1;
	/**
	 * 对图书推荐的类型进行特殊处理
	 */
	public static final int RULETYPE_BOOK_COMMEND = 5;
	/**
	 * 对运营的5个分类（最新，免费，推荐，排行，星级）特殊处理,不仅需要上架到当前
	 * 货架，还要根据内容deviceName上架到子货架,两外从内容获取的应用还要根据百分比分组 随机排序
	 */
	public static final int RULETYPE_OPERATION = 6;
	
	
	/*
	 * 增量刷新货架下商品
	 */
	public static final int RULETYPE_ADD_REFRESH = 7;

	//INTERVALTYPE:执行时间间隔类型 0：天；1：周；2：月
	public static final int INTERVALTYPE_DAY = 0;
	public static final int INTERVALTYPE_WEEK = 1;
	public static final int INTERVALTYPE_MONTH = 2;
	/**
	 * 从产品库取商品后，然后在大随即
	 */
	public static final int RULE_RANDOM_ALL =100;

}
