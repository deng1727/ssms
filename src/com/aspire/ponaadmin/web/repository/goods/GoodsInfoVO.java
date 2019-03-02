package com.aspire.ponaadmin.web.repository.goods;


public class GoodsInfoVO extends GoodsVO
{

    private static final long serialVersionUID = 1L;

    /**
     * 货架名称
     */
    private String categoryName;
    
    /**
     * 业务名称
     */
    private String servName;

    /**
     * 获取货架名称
     * @param categoryName
     */   
    public String getCategoryName()
    {
    
        return categoryName;
    }

    /**
     * 设置货架名称
     * @return
     */
    public void setCategoryName(String categoryName)
    {
    
        this.categoryName = categoryName;
    }
    
    /**
     * 获取业务名称
     * @param categoryName
     */
    public String getServName()
    {
    
        return servName;
    }

    /**
     * 设置业务名称
     * @return
     */
    public void setServName(String servName)
    {
    
        this.servName = servName;
    }
    
}
