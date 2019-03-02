package com.aspire.ponaadmin.web.datasync.implement.game;
/**
 * 用于记录基地游戏主键的VO类。icpCode和icpServid决定基地游戏的主键。
 * @author zhangwei
 *
 */
public class BaseGameKeyVO
{
	//游戏的保存在数据库中id值。该值只是用于数据库更新时使用。
	private String id;
	//系统中游戏同步时状态。0表示从同步前的最初状态，1表示本次同步新增的值，2表示本次同步更新的值.
	//5 表示处理时出现错误。6 表示检查不通过的状态。
	private int status;
	//游戏cp代码
	private String icpCode;
	//游戏的业务代码
	private String icpServid;
	//基地游戏二级分类
	private String appcateName;
	//游戏的特价资费
	private int expPrice ;
	//单机精品，试玩
	private String chargeTime;
	//网游，单机
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
			if(this.icpCode==null||this.icpServid==null)//基地游戏这两这值不允许为空
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
