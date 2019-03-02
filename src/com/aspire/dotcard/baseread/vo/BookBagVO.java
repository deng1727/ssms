package com.aspire.dotcard.baseread.vo;

public class BookBagVO
{
    /**
     * ������
     */
    private String bookBagId;

    /**
     * �����
     */
    private String bookBagName;

    /**
     * ר����ʶ
     */
    private String cateId;

    /**
     * ������
     */
    private String bookBagDesc;
    
    /**
     * ���ͼƬ
     */
    private String bookBagPic;

    /**
     * �ʷ� ��
     */
    private int fee;
    
    /**
     * ����ʱ��
     */
    private String onLineTime;
    
    /**
     * ��������
     */
    private int packetType;

    /**
     * ��������
     */
    private int changeType;

    public boolean setValue(String[] data)
    {
		if (data.length != 8)
		{
			return false;
		}
		if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
				|| "".equals(data[1].trim()) || null == data[2]
				|| "".equals(data[2].trim()) || null == data[5]
				|| "".equals(data[5].trim()) || null == data[7]
				|| "".equals(data[7].trim()))
		{
			return false;
		}

		this.bookBagId = data[0].trim();
		this.bookBagName = data[1].trim();
		this.cateId = data[2].trim();
		this.bookBagDesc = data[3].trim();
		this.bookBagPic = data[4].trim();

		int changeType;
		try
		{
			this.fee = Integer.parseInt(data[5]);
			changeType = Integer.parseInt(data[7]);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		if (changeType != 1 && changeType != 2 && changeType != 3)
		{
			return false;
		}

		if (this.fee > 0)
		{
			this.packetType = 1;
		}
		else
		{
			this.packetType = 2;
		}

		this.onLineTime = data[6].trim();
		this.changeType = changeType;
		return true;
	}

    public String getBookBagDesc()
    {
        return bookBagDesc;
    }

    public void setBookBagDesc(String bookBagDesc)
    {
        this.bookBagDesc = bookBagDesc;
    }

    public String getBookBagId()
    {
        return bookBagId;
    }

    public void setBookBagId(String bookBagId)
    {
        this.bookBagId = bookBagId;
    }

    public String getBookBagName()
    {
        return bookBagName;
    }

    public void setBookBagName(String bookBagName)
    {
        this.bookBagName = bookBagName;
    }

    public String getBookBagPic()
    {
        return bookBagPic;
    }

    public void setBookBagPic(String bookBagPic)
    {
        this.bookBagPic = bookBagPic;
    }

    public int getFee()
    {
        return fee;
    }

    public void setFee(int fee)
    {
        this.fee = fee;
    }

    public String getOnLineTime()
    {
        return onLineTime;
    }

    public void setOnLineTime(String onLineTime)
    {
        this.onLineTime = onLineTime;
    }

    public int getChangeType()
    {
        return changeType;
    }

    public void setChangeType(int changeType)
    {
        this.changeType = changeType;
    }

	public int getPacketType()
	{
		return packetType;
	}

	public void setPacketType(int packetType)
	{
		this.packetType = packetType;
	}

	public String getCateId()
	{
		return cateId;
	}

	public void setCateId(String cateId)
	{
		this.cateId = cateId;
	}
}
