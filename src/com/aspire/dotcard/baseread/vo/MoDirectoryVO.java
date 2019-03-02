
package com.aspire.dotcard.baseread.vo;

public class MoDirectoryVO
{
    /**
     * 终端目录标识
     */
    private String moDirectoryId;

    /**
     * 专区标识
     */
    private String cateId;


    public String getMoDirectoryId()
	{
		return moDirectoryId;
	}

	public void setMoDirectoryId(String moDirectoryId)
	{
		this.moDirectoryId = moDirectoryId;
	}

	public String getCateId()
	{
		return cateId;
	}

	public void setCateId(String cateId)
	{
		this.cateId = cateId;
	}

	public boolean setValue(String[] data)
    {
        if (data.length != 2)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) )
        {
            return false;
        }

        this.moDirectoryId = data[0].trim();
        this.cateId = data[1].trim();

        return true;
    }

}
