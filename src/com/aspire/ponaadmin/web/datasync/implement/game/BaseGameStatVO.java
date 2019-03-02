/*
 * 文件名：BaseGameStatVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.datasync.implement.game;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BaseGameStatVO
{
    /**
     * 游戏id
     */
    private String gameId;
    
    /**
     * 测试评分
     */
    private String testNumber;
    
    /**
     * 用户好评
     */
    private String userValue;
    
    /**
     * 日下载量
     */
    private String dayOrderTimes;

    /**
     * 周下载量
     */
    private String weekOrderTimes;
    
    /**
     * 月下载量
     */
    private String monthOrderTimes;
    
    /**
     * 前7天下载
     */
    private String sevenDay;
    
    /**
     * 前30天下载
     */
    private String monthDay;
    
    /**
     * 总下载
     */
    private String count;
    
    /**
     * 下载增长率
     */
    private String downloadChange;
    
    /**
     * 日用户活跃率
     */
    private String dayActivityUser;
    
    /**
     * 推荐次数
     */
    private String commendTime;

    public String getCommendTime()
    {
        return commendTime;
    }

    public void setCommendTime(String commendTime)
    {
        this.commendTime = commendTime;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public String getDayActivityUser()
    {
        return dayActivityUser;
    }

    public void setDayActivityUser(String dayActivityUser)
    {
        this.dayActivityUser = dayActivityUser;
    }

    public String getDayOrderTimes()
    {
        return dayOrderTimes;
    }

    public void setDayOrderTimes(String dayOrderTimes)
    {
        this.dayOrderTimes = dayOrderTimes;
    }

    public String getDownloadChange()
    {
        return downloadChange;
    }

    public void setDownloadChange(String downloadChange)
    {
        this.downloadChange = downloadChange;
    }

    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public String getMonthDay()
    {
        return monthDay;
    }

    public void setMonthDay(String monthDay)
    {
        this.monthDay = monthDay;
    }

    public String getMonthOrderTimes()
    {
        return monthOrderTimes;
    }

    public void setMonthOrderTimes(String monthOrderTimes)
    {
        this.monthOrderTimes = monthOrderTimes;
    }

    public String getSevenDay()
    {
        return sevenDay;
    }

    public void setSevenDay(String sevenDay)
    {
        this.sevenDay = sevenDay;
    }

    public String getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(String testNumber)
    {
        this.testNumber = testNumber;
    }

    public String getUserValue()
    {
        return userValue;
    }

    public void setUserValue(String userValue)
    {
        this.userValue = userValue;
    }

    public String getWeekOrderTimes()
    {
        return weekOrderTimes;
    }

    public void setWeekOrderTimes(String weekOrderTimes)
    {
        this.weekOrderTimes = weekOrderTimes;
    }
}
