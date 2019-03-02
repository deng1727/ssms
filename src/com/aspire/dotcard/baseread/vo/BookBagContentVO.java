
package com.aspire.dotcard.baseread.vo;

public class BookBagContentVO
{
    /**
     * 书包id
     */
    private String bookBagId;

    /**
     * 图书id
     */
    private String bookId;

    /**
     * 排序id
     */
    private int sortId;

    /**
     * 操作类型
     */
    private int changeType;

    public String getBookBagId()
    {
        return bookBagId;
    }

    public void setBookBagId(String bookBagId)
    {
        this.bookBagId = bookBagId;
    }

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

    public int getChangeType()
    {
        return changeType;
    }

    public void setChangeType(int changeType)
    {
        this.changeType = changeType;
    }

    public boolean setValue(String[] data)
    {
        if (data.length != 4)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) || null == data[3]
            || "".equals(data[3].trim()))
        {
            return false;
        }

        this.bookBagId = data[0].trim();
        this.bookId = data[1].trim();

        int changeType;
        int sortId = 0;
        try
        {
            if (null != data[2] && !"".equals(data[2].trim()))
            {
                sortId = Integer.parseInt(data[2]);
            }
            changeType = Integer.parseInt(data[3]);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if (changeType != 1 && changeType != 2 && changeType != 3)
        {
            return false;
        }
        this.sortId = sortId;
        this.changeType = changeType;
        return true;
    }

}
