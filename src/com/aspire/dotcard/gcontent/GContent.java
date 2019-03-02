package com.aspire.dotcard.gcontent;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.EntityNode;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的内容节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author 张敏
 */
public class GContent extends EntityNode
{

    /**
     * 日志记录对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(GContent.class);

    /**
     * 资源类型：业务内容，内容类型
     */
    public static final String TYPE_CONTENT = "nt:gcontent";

    /**
     * 构造方法
     */
    public GContent()
    {
        this.type = TYPE_CONTENT;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GContent(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_CONTENT;
    }

    /**
     * 获取内容的名称
     * @return Returns the name.
     */
    public String getName()
    {
        return getNoNullString((String) this.getProperty("name").getValue());
    }


    /**
     * 设置内容的所有适配机型
     * @param name The name to set.
     */
    public void setFulldeviceName(String fulldeviceName)
    {
        Property pro = new Property("fulldeviceName", fulldeviceName);
        this.setProperty(pro);
    }
    /**
     * 获取内容所有适配机型
     * @return Returns the name.
     */
    public String getFulldeviceName()
    {
        return getNoNullString((String) this.getProperty("fulldeviceName").getValue());
    }

    /**
     * 设置内容的所有适配机型ID
     * @param name The ID to set.
     */
    public void setFulldeviceID(String fulldeviceID)
    {
        Property pro = new Property("fulldeviceID", fulldeviceID);
        this.setProperty(pro);
    }
    /**
     * 获取内容所有适配机型ID
     * @return Returns the ID.
     */
    public String getFulldeviceID()
    {
        return getNoNullString((String) this.getProperty("fulldeviceID").getValue());
    }
    
    
    /**
     * 设置内容的名称
     * @param name The name to set.
     */
    public void setName(String name)
    {
        Property pro = new Property("name", name);
        this.setProperty(pro);
    }
    /**
     * 获取业务内容分类
     * @return Returns the cateName.
     */
    public String getCateName()
    {
        return getNoNullString((String) this.getProperty("cateName").getValue());
    }


    /**
     * 设置业务内容分类
     * @param cateName The cateName to set.
     */
    public void setCateName(String cateName)
    {
        Property pro = new Property("cateName", cateName);
        this.setProperty(pro);
    }
    
    /**
     * 获取服务提供商
     * @return Returns the spName.
     */
    public String getSpName()
    {
        return getNoNullString((String) this.getProperty("spName").getValue());
    }


    /**
     * 设置服务提供商
     * @param spName The spName to set.
     */
    public void setSpName(String spName)
    {
        Property pro = new Property("spName", spName);
        this.setProperty(pro);
    }

    /**
     * 获取sp的企业代码（外码）
     * @return Returns the icpCode.
     */
    public String getIcpCode()
    {
        return (String) this.getProperty("icpCode").getValue();
    }


    /**
     * 设置sp的企业代码（外码）
     * @param icpCode The icpCode to set.
     */
    public void setIcpCode(String icpCode)
    {
        Property pro = new Property("icpCode", icpCode);
        this.setProperty(pro);
    }


    /**
     * 获取服务代码
     * @return Returns the icpServId.
     */
    public String getIcpServId()
    {
        return (String) this.getProperty("icpServId").getValue();
    }


    /**
     * 设置服务代码
     * @param icpServId The icpServId to set.
     */
    public void setIcpServId(String icpServId)
    {
        Property pro = new Property("icpServId", icpServId);
        this.setProperty(pro);
    }
    
    /**
     * 获取内容标识
     * @return Returns the contentTag.
     */
    public String getContentTag()
    {
        return (String) this.getProperty("contentTag").getValue();
    }


    /**
     * 设置内容标识
     * @param contentTag The contentTag to set.
     */
    public void setContentTag(String contentTag)
    {
        Property pro = new Property("contentTag", contentTag);
        this.setProperty(pro);
    }
    
    /**
     * 获取详情介绍
     * @return Returns the introduction.
     */
    public String getIntroduction()
    {
        return (String) this.getProperty("introduction").getValue();
    }


    /**
     * 设置详情介绍
     * @param The introduction to set.
     */
    public void setIntroduction(String introduction)
    {
        Property pro = new Property("introduction", introduction);
        this.setProperty(pro);
    }

    /**
     * 获取内容编码
     * @return Returns the contentID.
     */
    public String getContentID()
    {
        return (String) this.getProperty("contentID").getValue();
    }


    /**
     * 设置内容编码
     * @param contentID The contentID to set.
     */
    public void setContentID(String contentID)
    {
        Property pro = new Property("contentID", contentID);
        this.setProperty(pro);
    }
    
    
    /**
     * 获取内容编码
     * @return Returns the contentID.
     */
    public String getAppId()
    {
        return (String) this.getProperty("appId").getValue();
    }


    /**
     * 设置内容编码
     * @param contentID The contentID to set.
     */
    public void setAppId(String appId)
    {
        Property pro = new Property("appId", appId);
        this.setProperty(pro);
    }
    /**
     * 获取企业代码（内码）
     * @return Returns the companyID.
     */
    public String getCompanyID()
    {
        return (String) this.getProperty("companyID").getValue();
    }

    /**
     * 设置企业代码（内码）
     * @param companyID The companyID to set.
     */
    public void setCompanyID(String companyID)
    {
        Property pro = new Property("companyID", companyID);
        this.setProperty(pro);
    }

    /**
     * 获取业务代码（内码）
     * @return Returns the productID.
     */
    public String getProductID()
    {
        return (String) this.getProperty("productID").getValue();
    }

    /**
     * 设置业务代码（内码）
     * @param productID The productID to set.
     */
    public void setProductID(String productID)
    {
        Property pro = new Property("productID", productID);
        this.setProperty(pro);
    }

    /**
     * 设置内容关键字
     * @param imageName The imageName to set.
     */
    public void setKeywords(String keywords)
    {
        Property pro = new Property("keywords", keywords);
        this.setProperty(pro);
        
        setKeywordsList();
    }

    /**
     * 获取内容关键字
     * @return Returns the imageName.
     */
    public String getKeywords()
    {
        return (String) this.getProperty("keywords").getValue();
    }
    /**
     * 获取内容关键字显示字符,去掉{}
     * @return Returns the imageName.
     */
    public String getKeywordsDesc()
    {
        String s = (String) this.getProperty("keywords").getValue();
        if(s==null || s.equals(""))
        {
            return "";
        }
        
        char[] c =s.toCharArray();
        char[] d = new char[c.length];
        int j=0;
        for(int i=0;i<c.length;i++)
        {
            if(c[i]!='{' && c[i]!='}')
            {
                d[j++]=c[i];
            }
        }
        return new String(d,0,j);
    }
    public String getKeywordsFormat()
    {
        String s = getKeywordsDesc();
        s = s.replaceAll(";", "\r");
        return s;
        
    }
    /**
     * 获取内容标签列表,主要是插件使用
     * @return
     */
    public List getKeywordsList()
    {
        return (List) this.getProperty("keywordsList").getValue();
    }

    /**
     * 根据标签,设置标签列表
     *
     */
    public void setKeywordsList()
    {
        String s = getKeywordsDesc();
        String[] ss = s.split(";");
        List list = new ArrayList();
        for(int i=0;i<ss.length;i++)
            list.add(ss[i]);

        setKeywordsList(list);
    }
    
    /**
     * 根据List,设置标签列表
     * @param list
     */
    public void setKeywordsList(List list)
    {
        Property pro = new Property("keywordsList", list);
        this.setProperty(pro);
    }

    /**
     * 获取导入日期
     * @return Returns the createDate.
     */
    public String getCreateDate()
    {
        return (String) this.getProperty("createDate").getValue();
    }


    /**
     * 设置导入日期
     * @param createDate The createDate to set.
     */
    public void setCreateDate(String createDate)
    {
        Property pro = new Property("createDate", createDate);
        this.setProperty(pro);
    }
    
    /**
     * 设置内容上线时间
     * @param marketDate
     */
    public void setMarketDate(String marketDate)
    {
        Property pro = new Property("marketDate", marketDate);
        this.setProperty(pro);
    }
    
    /**
     * 获取内容上线时间
     * @return
     */
    public String getMarketDate()
    {       
        return (String) this.getProperty("marketDate").getValue();
    }
    
    /**
     * 更新时间
     * @param LupdDate
     */
    public void setLupdDate(String LupdDate)
    {
        Property pro = new Property("lupdDate", LupdDate);
        this.setProperty(pro);
    }
    
    /**
     * 获取更新时间
     * @return
     */
    public String getLupdDate()
    {
        return (String) this.getProperty("lupdDate").getValue(); 
    }
    
    
    /**
     * 电子流增量更新时间
     * @param LupdDate
     */
    public void setPlupdDate(String PlupdDate)
    {
        Property pro = new Property("plupdDate", PlupdDate);
        this.setProperty(pro);
    }
    /**
     * 获取电子流增量更新时间
     * @return
     */
    public String getPlupdDate()
    {
        return (String) this.getProperty("plupdDate").getValue(); 
    }
    
    /**
     * 异网状态
     * @param OtherNet
     */
    public void setOtherNet(String otherNet)
    {
        Property pro = new Property("otherNet", otherNet);
        this.setProperty(pro);
    }
    /**
     * 获取异网状态
     * @return
     */
    public String getOtherNet()
    {
        return (String) this.getProperty("otherNet").getValue(); 
    }
    
    /**
     * 设置内容累计定购次数，值从报表导入
     */
    public void setOrderTimes(int orderTimes)
    {
        Property pro = new Property("orderTimes", new Integer(orderTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计定购次数
     * @return
     */
    public int getOrderTimes()
    {
        return (( Integer ) this.getProperty("orderTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容定购次数，值从报表导入
     */
    public void setWeekOrderTimes(int weekOrderTimes)
    {
        Property pro = new Property("weekOrderTimes", new Integer(weekOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容定购次数
     * @return
     */
    public int getWeekOrderTimes()
    {
        return (( Integer ) this.getProperty("weekOrderTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容定购次数，值从报表导入
     */
    public void setMonthOrderTimes(int monthOrderTimes)
    {
        Property pro = new Property("monthOrderTimes", new Integer(monthOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容定购次数
     * @return
     */
    public int getMonthOrderTimes()
    {
        return (( Integer ) this.getProperty("monthOrderTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容定购次数，值从报表导入
     */
    public void setDayOrderTimes(int dayOrderTimes)
    {
        Property pro = new Property("dayOrderTimes", new Integer(dayOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容定购次数
     * @return
     */
    public int getDayOrderTimes()
    {
        return (( Integer ) this.getProperty("dayOrderTimes").getValue()).intValue();
    }
    
    /**
     * 设置内容累计搜索次数，值从报表导入
     */
    public void setSearchTimes(int searchTimes)
    {
        Property pro = new Property("searchTimes", new Integer(searchTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计搜索次数
     * @return
     */
    public int getSearchTimes()
    {
        return (( Integer ) this.getProperty("searchTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容搜索次数，值从报表导入
     */
    public void setWeekSearchTimes(int weekSearchTimes)
    {
        Property pro = new Property("weekSearchTimes", new Integer(weekSearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容搜索次数
     * @return
     */
    public int getWeekSearchTimes()
    {
        return (( Integer ) this.getProperty("weekSearchTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容搜索次数，值从报表导入
     */
    public void setMonthSearchTimes(int monthSearchTimes)
    {
        Property pro = new Property("monthSearchTimes", new Integer(monthSearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容搜索次数
     * @return
     */
    public int getMonthSearchTimes()
    {
        return (( Integer ) this.getProperty("monthSearchTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容搜索次数，值从报表导入
     */
    public void setDaySearchTimes(int daySearchTimes)
    {
        Property pro = new Property("daySearchTimes", new Integer(daySearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容搜索次数
     * @return
     */
    public int getDaySearchTimes()
    {
        return (( Integer ) this.getProperty("daySearchTimes").getValue()).intValue();
    }
    
    /**
     * 设置内容累计浏览次数，值从报表导入
     */
    public void setScanTimes(int ScanTimes)
    {
        Property pro = new Property("scanTimes", new Integer(ScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计浏览次数
     * @return
     */
    public int getScanTimes()
    {
        return (( Integer ) this.getProperty("scanTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容浏览次数，值从报表导入
     */
    public void setWeekScanTimes(int weekScanTimes)
    {
        Property pro = new Property("weekScanTimes", new Integer(weekScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容浏览次数
     * @return
     */
    public int getWeekScanTimes()
    {
        return (( Integer ) this.getProperty("weekScanTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容浏览次数，值从报表导入
     */
    public void setMonthScanTimes(int monthScanTimes)
    {
        Property pro = new Property("monthScanTimes", new Integer(monthScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容浏览次数
     * @return
     */
    public int getMonthScanTimes()
    {
        return (( Integer ) this.getProperty("monthScanTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容浏览次数，值从报表导入
     */
    public void setDayScanTimes(int dayScanTimes)
    {
        Property pro = new Property("dayScanTimes", new Integer(dayScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容浏览次数
     * @return
     */
    public int getDayScanTimes()
    {
        return (( Integer ) this.getProperty("dayScanTimes").getValue()).intValue();
    }
    
    /**
     * 设置内容累计评论次数，值从报表导入
     */
    public void setCommentTimes(int commentTimes)
    {
        Property pro = new Property("commentTimes", new Integer(commentTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计评论次数
     * @return
     */
    public int getCommentTimes()
    {
        return (( Integer ) this.getProperty("commentTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容评论次数，值从报表导入
     */
    public void setWeekCommentTimes(int weekCommentTimes)
    {
        Property pro = new Property("weekCommentTimes", new Integer(weekCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容评论次数
     * @return
     */
    public int getWeekCommentTimes()
    {
        return (( Integer ) this.getProperty("weekCommentTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容评论次数，值从报表导入
     */
    public void setMonthCommentTimes(int monthCommentTimes)
    {
        Property pro = new Property("monthCommentTimes", new Integer(monthCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容评论次数
     * @return
     */
    public int getMonthCommentTimes()
    {
        return (( Integer ) this.getProperty("monthCommentTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容评论次数，值从报表导入
     */
    public void setDayCommentTimes(int dayCommentTimes)
    {
        Property pro = new Property("dayCommentTimes", new Integer(dayCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容评论次数
     * @return
     */
    public int getDayCommentTimes()
    {
        return (( Integer ) this.getProperty("dayCommentTimes").getValue()).intValue();
    }

    /**
     * 设置内容累计评分次数，值从报表导入
     */
    public void setMarkTimes(int markTimes)
    {
        Property pro = new Property("markTimes", new Integer(markTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计评分次数
     * @return
     */
    public int getMarkTimes()
    {
        return (( Integer ) this.getProperty("markTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容评分次数，值从报表导入
     */
    public void setWeekMarkTimes(int weekMarkTimes)
    {
        Property pro = new Property("weekMarkTimes", new Integer(weekMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容评分次数
     * @return
     */
    public int getWeekMarkTimes()
    {
        return (( Integer ) this.getProperty("weekMarkTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容评分次数，值从报表导入
     */
    public void setMonthMarkTimes(int monthMarkTimes)
    {
        Property pro = new Property("monthMarkTimes", new Integer(monthMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容评分次数
     * @return
     */
    public int getMonthMarkTimes()
    {
        return (( Integer ) this.getProperty("monthMarkTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容评分次数，值从报表导入
     */
    public void setDayMarkTimes(int dayMarkTimes)
    {
        Property pro = new Property("dayMarkTimes", new Integer(dayMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容评分次数
     * @return
     */
    public int getDayMarkTimes()
    {
        return (( Integer ) this.getProperty("dayMarkTimes").getValue()).intValue();
    }
    
    /**
     * 设置内容累计推荐次数，值从报表导入
     */
    public void setCommendTimes(int commendTimes)
    {
        Property pro = new Property("commendTimes", new Integer(commendTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计推荐次数
     * @return
     */
    public int getCommendTimes()
    {
        return (( Integer ) this.getProperty("commendTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容推荐次数，值从报表导入
     */
    public void setWeekCommendTimes(int weekCommendTimes)
    {
        Property pro = new Property("weekCommendTimes", new Integer(weekCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容推荐次数
     * @return
     */
    public int getWeekCommendTimes()
    {
        return (( Integer ) this.getProperty("weekCommendTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容推荐次数，值从报表导入
     */
    public void setMonthCommendTimes(int monthCommendTimes)
    {
        Property pro = new Property("monthCommendTimes", new Integer(monthCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容推荐次数
     * @return
     */
    public int getMonthCommendTimes()
    {
        return (( Integer ) this.getProperty("monthCommendTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容推荐次数，值从报表导入
     */
    public void setDayCommendTimes(int dayCommendTimes)
    {
        Property pro = new Property("dayCommendTimes", new Integer(dayCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容推荐次数
     * @return
     */
    public int getDayCommendTimes()
    {
        return (( Integer ) this.getProperty("dayCommendTimes").getValue()).intValue();
    }
    
    /**
     * 设置内容累计收藏次数，值从报表导入
     */
    public void setCollectTimes(int collectTimes)
    {
        Property pro = new Property("collectTimes", new Integer(collectTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取内容累计收藏次数
     * @return
     */
    public int getCollectTimes()
    {
        return (( Integer ) this.getProperty("collectTimes").getValue()).intValue();
    }
    
    /**
     * 设置上周内容收藏次数，值从报表导入
     */
    public void setWeekCollectTimes(int weekCollectTimes)
    {
        Property pro = new Property("weekCollectTimes", new Integer(weekCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上周内容收藏次数
     * @return
     */
    public int getWeekCollectTimes()
    {
        return (( Integer ) this.getProperty("weekCollectTimes").getValue()).intValue();
    }
    
    /**
     * 设置上月内容收藏次数，值从报表导入
     */
    public void setMonthCollectTimes(int monthCollectTimes)
    {
        Property pro = new Property("monthCollectTimes", new Integer(monthCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上月内容收藏次数
     * @return
     */
    public int getMonthCollectTimes()
    {
        return (( Integer ) this.getProperty("monthCollectTimes").getValue()).intValue();
    }
    
    /**
     * 设置上日内容收藏次数，值从报表导入
     */
    public void setDayCollectTimes(int dayCollectTimes)
    {
        Property pro = new Property("dayCollectTimes", new Integer(dayCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取上日内容收藏次数
     * @return
     */
    public int getDayCollectTimes()
    {
        return (( Integer ) this.getProperty("dayCollectTimes").getValue()).intValue();
    }
    
    /**
     * 设置评分加权平均值
     */
    public void setAverageMark(int averageMark)
    {
        Property pro = new Property("averageMark", new Integer(averageMark));
        this.setProperty(pro);
    }
    
    /**
     * 获取评分加权平均值
     * @return
     */
    public int getAverageMark()
    {
        return (( Integer ) this.getProperty("averageMark").getValue()).intValue();
    }

    /**
     * 获取内容类型的中文描述字符
     * @return String 类型的中文描述
     */
    public String getTypeDesc()
    {
        return GContentFactory.getTypeDesc(this);
    }
    
    /**
     * 得到不为null的String对象
     * @param name
     * @return
     */
    public static String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }
    
    /**
     * 设置上线范围，本地/全网
     * @param servAttre to set.
     */
    public void setServAttr(String servAttr)
    {
        Property pro = new Property("servAttr", servAttr);
        this.setProperty(pro);
        
        setKeywordsList();
    }

    /**
     * 设置上线范围，本地/全网
     * @return Returns the servAttr.
     */
    public String getServAttr()
    {
        return (String) this.getProperty("servAttr").getValue();
    }
    /**
     * 获取应用子类别 1表示widget应用
     * @return Returns the subType.
     */
    public String getSubType()
    {
        return (String) this.getProperty("subType").getValue();
    }


    /**
     * 设置应用子类别 1表示widget应用
     * @param chargeTime
     */
    public void setSubType(String subType)
    {
        Property pro = new Property("subType", subType);
        this.setProperty(pro);
    }
    
	/**
	 * 模糊适配设备ID zhangweixing add 
	 * @param matchDeviceid
	 */
	public void setMatch_Deviceid(String matchDeviceid) {
		Property pro = new Property("match_Deviceid", matchDeviceid);
		this.setProperty(pro);
	}

	/**
	 * 模糊适配设备ID zhangweixing add 
	 * @return
	 */
	public String getMatch_Deviceid() {
		return getNoNullString((String) this.getProperty("match_Deviceid")
				.getValue());
	}
    
    /**
     * 设置终端下载量，值从报表导入
     */
    public void setMoDayOrderTimes(int moDayOrderTimes)
    {
        Property pro = new Property("moDayOrderTimes", new Integer(moDayOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * 获取终端下载量
     * @return
     */
    public int getMoDayOrderTimes()
    {
        return (( Integer ) this.getProperty("moDayOrderTimes").getValue()).intValue();
    }
    
    /**
	 * 应用介绍（图文） 
	 * @param richAppdesc
	 */
	public void setRichAppdesc(String richAppdesc) {
		Property pro = new Property("richAppdesc", richAppdesc);
		this.setProperty(pro);
	}

	/**
	 * 应用介绍（图文）
	 * @return
	 */
	public String getRichAppdesc() {
		return getNoNullString((String) this.getProperty("richAppdesc")
				.getValue());
	}
	
	 /**
     * 广告图  
     * @return Returns the advertPic.
     */
    public String getAdvertPic()
    {
        return (String) this.getProperty("advertPic").getValue();
    }


    /**
     * 广告图  
     * @param chargeTime
     */
    public void setAdvertPic(String advertPic)
    {
        Property pro = new Property("advertPic", advertPic);
        this.setProperty(pro);
    }
    
    /**
     * wp创业大赛下载URL
     * @return Returns the AuditionUrl.
     */
    public String getAuditionUrl()
    {
        return (String) this.getProperty("auditionUrl").getValue();
    }


    /**
     * wp创业大赛下载URL  
     * @param AuditionUrl
     */
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }

	/**
	 * 代理商
	 * 
	 * @param mapname
	 */
	public void setMapname(String mapname) {
		Property pro = new Property("mapname", mapname);
		this.setProperty(pro);
	}

	/**
	 * 代理商
	 * 
	 * @return
	 */
	public String getMapname() {
		return (String) this.getProperty("mapname").getValue();
	}
	 /**
     * 获取审批状态
     * @return Returns the spName.
     */
    public String getVerifyStatus()
    {
        return getNoNullString((String) this.getProperty("verifyStatus").getValue());
    }


    /**
     * 设置审批状态
     * @param spName The spName to set.
     */
    public void setVerifyStatus(String verifyStatus)
    {
        Property pro = new Property("verifyStatus", verifyStatus);
        this.setProperty(pro);
    }

    /**
	 * 触点合作商渠道分发类型：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）
	 * 
	 * @param channeldisptype
	 */
	public void setChannelDispType(String channeldisptype) {
		Property pro = new Property("channeldisptype", channeldisptype);
		this.setProperty(pro);
	}

	/**
	 * 触点合作商渠道分发类型：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）
	 * 
	 * @return
	 */
	public String getChannelDispType() {
		return (String) this.getProperty("channeldisptype").getValue();
	}
    
}
