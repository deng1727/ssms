
package com.aspire.dotcard.baseread.vo;

public class RCountVO
{

    private String countId;

    private int readNum;

    private int flowersNum;

    private int clickNum;

    private int favoritesNum;

    private int orderNum;

    private int voteNum;

    public boolean setValue(String[] data)
    {
        if (data.length != 7)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) || null == data[2]
            || "".equals(data[2].trim()) || null == data[3]
            || "".equals(data[3].trim()) || null == data[4]
            || "".equals(data[4].trim()) || null == data[5]
            || "".equals(data[5].trim()) || null == data[6]
            || "".equals(data[6].trim()))
        {
            return false;
        }
        
        this.countId = data[0].trim();
        
        try
        {
            this.readNum = Integer.parseInt(data[1]);
            this.flowersNum = Integer.parseInt(data[2]);
            this.clickNum = Integer.parseInt(data[3]);
            this.favoritesNum = Integer.parseInt(data[4]);
            this.orderNum = Integer.parseInt(data[5]);
            this.voteNum = Integer.parseInt(data[6]);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    public int getClickNum()
    {
        return clickNum;
    }

    public void setClickNum(int clickNum)
    {
        this.clickNum = clickNum;
    }

    public String getCountId()
    {
        return countId;
    }

    public void setCountId(String countId)
    {
        this.countId = countId;
    }

    public int getFavoritesNum()
    {
        return favoritesNum;
    }

    public void setFavoritesNum(int favoritesNum)
    {
        this.favoritesNum = favoritesNum;
    }

    public int getFlowersNum()
    {
        return flowersNum;
    }

    public void setFlowersNum(int flowersNum)
    {
        this.flowersNum = flowersNum;
    }

    public int getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(int orderNum)
    {
        this.orderNum = orderNum;
    }

    public int getReadNum()
    {
        return readNum;
    }

    public void setReadNum(int readNum)
    {
        this.readNum = readNum;
    }

    public int getVoteNum()
    {
        return voteNum;
    }

    public void setVoteNum(int voteNum)
    {
        this.voteNum = voteNum;
    }
}
