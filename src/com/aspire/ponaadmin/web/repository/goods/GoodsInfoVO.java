package com.aspire.ponaadmin.web.repository.goods;


public class GoodsInfoVO extends GoodsVO
{

    private static final long serialVersionUID = 1L;

    /**
     * ��������
     */
    private String categoryName;
    
    /**
     * ҵ������
     */
    private String servName;

    /**
     * ��ȡ��������
     * @param categoryName
     */   
    public String getCategoryName()
    {
    
        return categoryName;
    }

    /**
     * ���û�������
     * @return
     */
    public void setCategoryName(String categoryName)
    {
    
        this.categoryName = categoryName;
    }
    
    /**
     * ��ȡҵ������
     * @param categoryName
     */
    public String getServName()
    {
    
        return servName;
    }

    /**
     * ����ҵ������
     * @return
     */
    public void setServName(String servName)
    {
    
        this.servName = servName;
    }
    
}
