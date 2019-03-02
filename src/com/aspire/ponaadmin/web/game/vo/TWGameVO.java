package com.aspire.ponaadmin.web.game.vo;

public class TWGameVO
{
	private String gameId;
	private String gameName;
	private String CPName;
	private String gameDesc;
	private String gameDescByPage;
	private int oldPrice;
	private int fee;
	private String sortId;
	
	private String dbType;
	
	public String getDbType()
	{
		return dbType;
	}
	
	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}
	
	public String getGameId()
	{
		return gameId;
	}
	
	public void setGameId(String gameId)
	{
		this.gameId = gameId;
	}
	
	public String getGameName()
	{
		return gameName;
	}
	
	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}
	
	public String getCPName()
	{
		return CPName;
	}
	
	public void setCPName(String name)
	{
		CPName = name;
	}
	
	public String getGameDesc()
	{
		return gameDesc;
	}
	
	public void setGameDesc(String gameDesc)
	{
		this.gameDesc = gameDesc;
		
		if (gameDesc.length() > 10)
		{	
			this.gameDescByPage = gameDesc.substring(0, 10) + "...";
		}
		else
		{
			this.gameDescByPage = gameDesc;
		}
	}
	
	public int getOldPrice()
	{
		return oldPrice;
	}
	
	public void setOldPrice(int oldPrice)
	{
		this.oldPrice = oldPrice;
	}
	
	public int getFee()
	{
		return fee;
	}
	
	public void setFee(int fee)
	{
		this.fee = fee;
	}
	
	public String getSortId()
	{
		return sortId;
	}
	
	public void setSortId(String sortId)
	{
		this.sortId = sortId;
	}
	
	public String getGameDescByPage()
	{
		return gameDescByPage;
	}
}
