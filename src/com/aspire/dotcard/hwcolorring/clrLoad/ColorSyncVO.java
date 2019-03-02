package com.aspire.dotcard.hwcolorring.clrLoad;

public class ColorSyncVO
{
	/**
	 * 最后更新时间
	 */
	private String lupDate;
	/**
	 * 同步标志位，0表示同步未处理，1，表示增加，2表示更新。
	 */
	private int    flag;
	/**
	 * 是否转换成功
	 */
	private boolean isConvert;
	public boolean isConvert()
	{
		return isConvert;
	}
	public void setConvert(boolean isConvert)
	{
		this.isConvert = isConvert;
	}
	public ColorSyncVO()
	{
		
	}
	public ColorSyncVO(String lupDate,int flag)
	{
		this.lupDate=lupDate;
		this.flag=flag;
	}
	public String getLupDate()
	{
		return lupDate;
	}
	public void setLupDate(String lupDate)
	{
		this.lupDate = lupDate;
	}
	public int getFlag()
	{
		return flag;
	}
	public void setFlag(int flag)
	{
		this.flag = flag;
	}

}
