package com.aspire.ponaadmin.web.datasync.implement.game;
/**
 * ���ڼ�¼������Ϸ������VO�ࡣicpCode��icpServid����������Ϸ��������
 * @author zhangwei
 *
 */
public class BaseGameKeyVO
{
	//��Ϸ�ı��������ݿ���idֵ����ֵֻ���������ݿ����ʱʹ�á�
	private String id;
	//ϵͳ����Ϸͬ��ʱ״̬��0��ʾ��ͬ��ǰ�����״̬��1��ʾ����ͬ��������ֵ��2��ʾ����ͬ�����µ�ֵ.
	//5 ��ʾ����ʱ���ִ���6 ��ʾ��鲻ͨ����״̬��
	private int status;
	//��Ϸcp����
	private String icpCode;
	//��Ϸ��ҵ�����
	private String icpServid;
	//������Ϸ��������
	private String appcateName;
	//��Ϸ���ؼ��ʷ�
	private int expPrice ;
	//������Ʒ������
	private String chargeTime;
	//���Σ�����
	private int onlineType;
	
	public boolean equals(Object object)
	{
		if(object==null)
		{
			return false;
		}
		if(this==object)
		{
			return true;
		}
		if(object instanceof BaseGameKeyVO)
		{
			BaseGameKeyVO vo=(BaseGameKeyVO)object;
			if(this.icpCode==null||this.icpServid==null)//������Ϸ������ֵ������Ϊ��
			{
				return false;
			}else if(this.icpCode.equals(vo.getIcpCode())&&this.icpServid.equals(vo.getIcpServid()))
			{
				return true;
			}else
			{
				return false;
			}
			
		}else
		{
			return false;
		}
		
	}
	 public int hashCode()
	 {
	       int result = 17;
	       if(this.icpCode!=null)
	       {
	    	   result = 37 * result + this.icpCode.hashCode();
	       }
	       if(this.icpServid!=null)
	       {
	    	   result = 37 * result + this.icpServid.hashCode();
	       }
	       return result;
	    }
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getIcpCode()
	{
		return icpCode;
	}
	public void setIcpCode(String icpCode)
	{
		this.icpCode = icpCode;
	}
	public String getIcpServid()
	{
		return icpServid;
	}
	public void setIcpServid(String icpServid)
	{
		this.icpServid = icpServid;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	/**
	 * @return Returns the expPrice.
	 */
	public int getExpPrice()
	{
		return expPrice;
	}
	/**
	 * @param expPrice The expPrice to set.
	 */
	public void setExpPrice(int expPrice)
	{
		this.expPrice = expPrice;
	}
	/**
	 * @return Returns the appcateName.
	 */
	public String getAppcateName()
	{
		return appcateName;
	}
	/**
	 * @param appcateName The appcateName to set.
	 */
	public void setAppcateName(String appcateName)
	{
		this.appcateName = appcateName;
	}
	/**
	 * @return Returns the chargeTime.
	 */
	public String getChargeTime()
	{
		return chargeTime;
	}
	/**
	 * @param chargeTime The chargeTime to set.
	 */
	public void setChargeTime(String chargeTime)
	{
		this.chargeTime = chargeTime;
	}
	/**
	 * @return Returns the onlineType.
	 */
	public int getOnlineType()
	{
		return onlineType;
	}
	/**
	 * @param onlineType The onlineType to set.
	 */
	public void setOnlineType(int onlineType)
	{
		this.onlineType = onlineType;
	}

}
