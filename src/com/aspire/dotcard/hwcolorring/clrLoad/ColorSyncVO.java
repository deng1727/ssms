package com.aspire.dotcard.hwcolorring.clrLoad;

public class ColorSyncVO
{
	/**
	 * ������ʱ��
	 */
	private String lupDate;
	/**
	 * ͬ����־λ��0��ʾͬ��δ����1����ʾ���ӣ�2��ʾ���¡�
	 */
	private int    flag;
	/**
	 * �Ƿ�ת���ɹ�
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
