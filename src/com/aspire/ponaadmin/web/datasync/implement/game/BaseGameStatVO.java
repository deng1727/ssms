/*
 * �ļ�����BaseGameStatVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��Ϸid
     */
    private String gameId;
    
    /**
     * ��������
     */
    private String testNumber;
    
    /**
     * �û�����
     */
    private String userValue;
    
    /**
     * ��������
     */
    private String dayOrderTimes;

    /**
     * ��������
     */
    private String weekOrderTimes;
    
    /**
     * ��������
     */
    private String monthOrderTimes;
    
    /**
     * ǰ7������
     */
    private String sevenDay;
    
    /**
     * ǰ30������
     */
    private String monthDay;
    
    /**
     * ������
     */
    private String count;
    
    /**
     * ����������
     */
    private String downloadChange;
    
    /**
     * ���û���Ծ��
     */
    private String dayActivityUser;
    
    /**
     * �Ƽ�����
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
