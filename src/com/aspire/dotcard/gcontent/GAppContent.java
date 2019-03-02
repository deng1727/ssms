package com.aspire.dotcard.gcontent;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.Property;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 应用相关的内容基类
 * 
 * @author bihui
 * 
 */
public class GAppContent extends GContent {
	// ORACLE在有多个varchar2(4000)字段时，JDBC只能处理到4000/3个长度，超过时会报错
	// ORA-01461 ：仅可以为插入LONG列的LONG值赋值
	private final int MAX_LENGTH = 1333;

	private final int MAX_COLUMNS = 20;

	/**
	 * 日志记录对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(GAppContent.class);

	/**
	 * 资源类型：应用内容，内容类型
	 */
	public static final String TYPE = "nt:gcontent:app";

	/**
	 * 构造方法
	 */
	public GAppContent() {
		this.type = TYPE;
	}

	/**
	 * 构造方法
	 * 
	 * @param nodeID，资源id
	 */
	public GAppContent(String nodeID) {
		super(nodeID);
		this.type = TYPE;
	}

	/**
	 * 获取当前应用支持的机型
	 * 
	 * @return
	 */
	public String getBrand() {
		return getNoNullString((String) getProperty("brand").getValue());
	}

	/**
	 * 设置当前应用支持的机型
	 * 
	 * @return
	 */
	public void setBrand(String brand) {
		Property property = new Property("brand", brand);
		this.setProperty(property);
	}
	

	/**
	 * 创业大赛标志
	 * 
	 * @return
	 */
	public String getIsmmtoevent() {
		return getNoNullString((String) getProperty("ismmtoevent").getValue());
	}

	/**
	 * 创业大赛标志
	 * 
	 * @return
	 */
	public void setIsmmtoevent(String ismmtoevent) {
		Property property = new Property("ismmtoevent", ismmtoevent);
		this.setProperty(property);
	}
	/**
	 * 是否正版标示
	 * 
	 * @return
	 */
	public String getCopyrightFlag() {
		return getNoNullString((String) getProperty("copyrightFlag").getValue());
	}

	/**
	 * 是否正版标示
	 * 
	 * @return
	 */
	public void setCopyrightFlag(String CopyrightFlag) {
		Property property = new Property("copyrightFlag", CopyrightFlag);
		this.setProperty(property);
	}
	/**
	 * 获取终端名称
	 * 
	 * @return
	 */
	public String getDeviceName() {
		StringBuffer buffer = new StringBuffer();
		String colName;
		int deviceCount = 0;
		for (int i = 0; i < MAX_COLUMNS; i++) {

			deviceCount = i + 1;
			colName = deviceCount < 10 ? "deviceName0" + deviceCount
					: "deviceName" + deviceCount;

			buffer.append(getNoNullString((String) getProperty(colName)
					.getValue()));

		}

		return buffer.toString();
	}

	/**
	 * 设置终端名称
	 * 
	 * @param audioType
	 *            The audioType to set.
	 */
	public void setDeviceName(String deviceName) {
		Object[] colValues = PublicUtil.getConentArray(deviceName, ",",
				MAX_LENGTH);
		String colName;
		int deviceCount = 0;
		for (int i = 0; i < colValues.length; i++) {
			// 最大10个字段
			if (i == MAX_COLUMNS)
				break;

			deviceCount = i + 1;
			colName = deviceCount < 10 ? "deviceName0" + deviceCount
					: "deviceName" + deviceCount;

			setProperty(new Property(colName, (String) colValues[i]));

		}
		if (deviceCount < MAX_COLUMNS) {
			for (deviceCount = deviceCount + 1; deviceCount <= MAX_COLUMNS; deviceCount++) {
				colName = deviceCount < 10 ? "deviceName0" + deviceCount
						: "deviceName" + deviceCount;
				setProperty(new Property(colName, ""));
			}
		}

	}

	/**
	 * 取格式化后的游戏支持的手机型号列表字符串,在显示内容信息和编译信息的页面中使用 content_info.jsp,content_edit.jsp
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getFormatDeviceName() throws BOException {
		String deviceName = this.getDeviceName();
		String formatDeviceName = "";
		String deviceNameItem = "";

		String[] vDeviceName = deviceName.split(",");
		for (int i = 0; i < vDeviceName.length; i++) {
			deviceNameItem = vDeviceName[i];
			deviceNameItem = deviceNameItem.replaceAll("\r", "");
			deviceNameItem = deviceNameItem.replaceAll("\n", "");
			if (deviceNameItem.length() == 0)
				continue;
			deviceNameItem = deviceNameItem.substring(deviceNameItem
					.indexOf("{") + 1, deviceNameItem.lastIndexOf("}"));
			formatDeviceName = formatDeviceName + deviceNameItem + "\r";
		}

		// 去除最后的换行符
		int j = 0;
		j = formatDeviceName.lastIndexOf("\r");
		if (j > 0) {
			formatDeviceName = formatDeviceName.substring(0, j);
		}

		return formatDeviceName;
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName01(String deviceName) {
		Property property = new Property("deviceName01", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName01() throws BOException {
		return getNoNullString((String) getProperty("deviceName01").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName02(String deviceName) {
		Property property = new Property("deviceName02", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName02() throws BOException {
		return getNoNullString((String) getProperty("deviceName02").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName03(String deviceName03) {
		Property property = new Property("deviceName03", deviceName03);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName03() throws BOException {
		return getNoNullString((String) getProperty("deviceName03").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName04(String deviceName04) {
		Property property = new Property("deviceName04", deviceName04);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName04() throws BOException {
		return getNoNullString((String) getProperty("deviceName04").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName05(String deviceName05) {
		Property property = new Property("deviceName05", deviceName05);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName05() throws BOException {
		return getNoNullString((String) getProperty("deviceName05").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName06(String deviceName06) {
		Property property = new Property("deviceName06", deviceName06);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName06() throws BOException {
		return getNoNullString((String) getProperty("deviceName06").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName07(String deviceName07) {
		Property property = new Property("deviceName07", deviceName07);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName07() throws BOException {
		return getNoNullString((String) getProperty("deviceName07").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName08(String deviceName08) {
		Property property = new Property("deviceName08", deviceName08);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName08() throws BOException {
		return getNoNullString((String) getProperty("deviceName08").getValue());
	}
	
	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setAppId(String appId) {
		Property property = new Property("appId", appId);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getAppId()  {
		return getNoNullString((String) getProperty("appId").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName09(String deviceName09) {
		Property property = new Property("deviceName09", deviceName09);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName09() throws BOException {
		return getNoNullString((String) getProperty("deviceName09").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName10(String deviceName10) {
		Property property = new Property("deviceName10", deviceName10);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName10() throws BOException {
		return getNoNullString((String) getProperty("deviceName10").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName11(String deviceName) {
		Property property = new Property("deviceName11", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName11() throws BOException {
		return getNoNullString((String) getProperty("deviceName11").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName12(String deviceName) {
		Property property = new Property("deviceName12", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName12() throws BOException {
		return getNoNullString((String) getProperty("deviceName12").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName13(String deviceName13) {
		Property property = new Property("deviceName13", deviceName13);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName13() throws BOException {
		return getNoNullString((String) getProperty("deviceName13").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName14(String deviceName14) {
		Property property = new Property("deviceName14", deviceName14);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName14() throws BOException {
		return getNoNullString((String) getProperty("deviceName14").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName15(String deviceName15) {
		Property property = new Property("deviceName15", deviceName15);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName15() throws BOException {
		return getNoNullString((String) getProperty("deviceName15").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName16(String deviceName) {
		Property property = new Property("deviceName16", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName16() throws BOException {
		return getNoNullString((String) getProperty("deviceName16").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName17(String deviceName) {
		Property property = new Property("deviceName17", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName17() throws BOException {
		return getNoNullString((String) getProperty("deviceName17").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName18(String deviceName) {
		Property property = new Property("deviceName18", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName18() throws BOException {
		return getNoNullString((String) getProperty("deviceName18").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName19(String deviceName) {
		Property property = new Property("deviceName19", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName19() throws BOException {
		return getNoNullString((String) getProperty("deviceName19").getValue());
	}

	/**
	 * 设置游戏支持的手机型号列表字符串
	 * 
	 * @param deviceName
	 *            手机型号列表字符串，{}为边界符，逗号为分隔符
	 */
	public void setDeviceName20(String deviceName) {
		Property property = new Property("deviceName20", deviceName);
		this.setProperty(property);
	}

	/**
	 * 取游戏支持的手机型号列表字符串
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName20() throws BOException {
		return getNoNullString((String) getProperty("deviceName20").getValue());
	}

	/**
	 * 获取内容在产品供应管理系统中的所在分类目录ID
	 * 
	 * @return Returns the appCateID.
	 */
	public String getAppCateID() {
		return getNoNullString((String) this.getProperty("appCateID")
				.getValue());
	}

	/**
	 * 设置内容在产品供应管理系统中的所在分类目录ID
	 * 
	 * @param appCateID
	 *            The appCateID to set.
	 */
	public void setAppCateID(String appCateID) {
		Property pro = new Property("appCateID", appCateID);
		this.setProperty(pro);
	}

	/**
	 * 获取内容在产品供应管理系统中的所在分类目录名称
	 * 
	 * @return Returns the appCateName.
	 */
	public String getAppCateName() {
		return getNoNullString((String) this.getProperty("appCateName")
				.getValue());
	}

	/**
	 * 设置内容在产品供应管理系统中的所在分类目录名称
	 * 
	 * @param appCateName
	 *            The appCateName to set.
	 */
	public void setAppCateName(String appCateName) {
		Property pro = new Property("appCateName", appCateName);
		this.setProperty(pro);
	}

	/**
	 * 获取产品提供者
	 * 
	 * @return Returns the provider.
	 */
	public String getProvider() {
		return getNoNullString((String) this.getProperty("provider").getValue());
	}

	/**
	 * 设置产品提供者
	 * 
	 * @param provider
	 *            The provider to set.
	 */
	public void setProvider(String provider) {
		Property pro = new Property("provider", provider);
		this.setProperty(pro);
	}

	/**
	 * 获取软件支持的语言，1:简体中文、2:繁体中文 3:英文 0:其它；
	 * 
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return getNoNullString((String) this.getProperty("language").getValue());
	}

	/**
	 * 设置软件支持的语言，1:简体中文、2:繁体中文 3:英文 0:其它；
	 * 
	 * @param language
	 *            The language to set.
	 */
	public void setLanguage(String language) {
		Property pro = new Property("language", language);
		this.setProperty(pro);
	}

	/**
	 * 获取WWW宣传图1地址
	 * 
	 * @return Returns the WWWPropaPicture1.
	 */
	public String getWWWPropaPicture1() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture1")
				.getValue());
	}

	/**
	 * 设置WWW宣传图1地址
	 * 
	 * @param WWWPropaPicture1
	 */
	public void setWWWPropaPicture1(String WWWPropaPicture1) {
		Property pro = new Property("WWWPropaPicture1", WWWPropaPicture1);
		this.setProperty(pro);
	}

	/**
	 * 获取WWW宣传图2地址
	 * 
	 * @return Returns the WWWPropaPicture2.
	 */
	public String getWWWPropaPicture2() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture2")
				.getValue());
	}

	/**
	 * 设置WWW宣传图2地址
	 * 
	 * @param WWWPropaPicture2
	 */
	public void setWWWPropaPicture2(String WWWPropaPicture2) {
		Property pro = new Property("WWWPropaPicture2", WWWPropaPicture2);
		this.setProperty(pro);
	}

	/**
	 * 获取WWW宣传图3地址
	 * 
	 * @return Returns the WWWPropaPicture3.
	 */
	public String getWWWPropaPicture3() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture3")
				.getValue());
	}

	/**
	 * 设置WWW宣传图3地址
	 * 
	 * @param WWWPropaPicture3
	 */
	public void setWWWPropaPicture3(String WWWPropaPicture3) {
		Property pro = new Property("WWWPropaPicture3", WWWPropaPicture3);
		this.setProperty(pro);
	}

	/**
	 * 获取终端预览图1地址
	 * 
	 * @return Returns the clientPreviewPicture1.
	 */
	public String getClientPreviewPicture1() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture1").getValue());
	}

	/**
	 * 设置终端预览图1地址
	 * 
	 * @param clientPreviewPicture
	 */
	public void setClientPreviewPicture1(String clientPreviewPicture1) {
		Property pro = new Property("clientPreviewPicture1",
				clientPreviewPicture1);
		this.setProperty(pro);
	}

	/**
	 * 获取终端预览图2地址
	 * 
	 * @return Returns the clientPreviewPicture2.
	 */
	public String getClientPreviewPicture2() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture2").getValue());
	}

	/**
	 * 设置终端预览图2地址
	 * 
	 * @param clientPreviewPicture2
	 */
	public void setClientPreviewPicture2(String clientPreviewPicture2) {
		Property pro = new Property("clientPreviewPicture2",
				clientPreviewPicture2);
		this.setProperty(pro);
	}

	/**
	 * 获取终端预览图3地址
	 * 
	 * @return Returns the clientPreviewPicture3.
	 */
	public String getClientPreviewPicture3() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture3").getValue());
	}

	/**
	 * 设置终端预览图3地址
	 * 
	 * @param clientPreviewPicture3
	 */
	public void setClientPreviewPicture3(String clientPreviewPicture3) {
		Property pro = new Property("clientPreviewPicture3",
				clientPreviewPicture3);
		this.setProperty(pro);
	}

	/**
	 * 获取终端预览图4地址
	 * 
	 * @return Returns the clientPreviewPicture4.
	 */
	public String getClientPreviewPicture4() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture4").getValue());
	}

	/**
	 * 设置终端预览图4地址
	 * 
	 * @param clientPreviewPicture4
	 */
	public void setClientPreviewPicture4(String clientPreviewPicture4) {
		Property pro = new Property("clientPreviewPicture4",
				clientPreviewPicture4);
		this.setProperty(pro);
	}

	/**
	 * 获取LOGO1图片地址
	 * 
	 * @return Returns the LOGO1.
	 */
	public String getLOGO1() {
		return getNoNullString((String) this.getProperty("LOGO1").getValue());
	}

	/**
	 * 设置LOGO1图片地址
	 * 
	 * @param LOGO1
	 */
	public void setLOGO1(String LOGO1) {
		Property pro = new Property("LOGO1", LOGO1);
		this.setProperty(pro);
	}

	/**
	 * 获取LOGO2图片地址
	 * 
	 * @return Returns the LOGO2.
	 */
	public String getLOGO2() {
		return getNoNullString((String) this.getProperty("LOGO2").getValue());
	}

	/**
	 * 设置LOGO2图片地址
	 * 
	 * @param LOGO2
	 */
	public void setLOGO2(String LOGO2) {
		Property pro = new Property("LOGO2", LOGO2);
		this.setProperty(pro);
	}

	/**
	 * 获取LOGO3图片地址
	 * 
	 * @return Returns the LOGO3.
	 */
	public String getLOGO3() {
		return getNoNullString((String) this.getProperty("LOGO3").getValue());
	}

	/**
	 * 设置LOGO3图片地址
	 * 
	 * @param LOGO3
	 */
	public void setLOGO3(String LOGO3) {
		Property pro = new Property("LOGO3", LOGO3);
		this.setProperty(pro);
	}

	/**
	 * 获取LOGO4图片地址
	 * 
	 * @return Returns the LOGO4.
	 */
	public String getLOGO4() {
		return getNoNullString((String) this.getProperty("LOGO4").getValue());
	}

	/**
	 * 设置LOGO4图片地址
	 * 
	 * @param LOGO4
	 */
	public void setLOGO4(String LOGO4) {
		Property pro = new Property("LOGO4", LOGO4);
		this.setProperty(pro);
	}

	
	/**
	 * 获取LOGO5图片地址
	 * 
	 * @return Returns the LOGO5.
	 */
	public String getLOGO5() {
		return getNoNullString((String) this.getProperty("LOGO5").getValue());
	}

	/**
	 * 设置LOGO5图片地址
	 * 
	 * @param LOGO5
	 */
	public void setLOGO5(String LOGO5) {
		Property pro = new Property("LOGO5", LOGO5);
		this.setProperty(pro);
	}
	
	 /**
     * 获取LOGO6图片地址
     * 
     * @return Returns the LOGO6.
     * add fanqh 201310
     */
    public String getLOGO6() {
        return getNoNullString((String) this.getProperty("LOGO6").getValue());
    }

    /**
     * 设置LOGO6图片地址
     * 
     * @param LOGO6
     * add fanqh 201310
     */
    public void setLOGO6(String LOGO6) {
        Property pro = new Property("LOGO6", LOGO6);
        this.setProperty(pro);
    }
	/**
	 * 获取软件动画截图地址
	 * 
	 * @return Returns the cartoonPicture.
	 */
	public String getCartoonPicture() {
		return getNoNullString((String) this.getProperty("cartoonPicture")
				.getValue());
	}

	/**
	 * 设置软件动画截图地址
	 * 
	 * @param cartoonPicture
	 */
	public void setCartoonPicture(String cartoonPicture) {
		Property pro = new Property("cartoonPicture", cartoonPicture);
		this.setProperty(pro);
	}

	/**
	 * 获取内容是否支持点卡支持
	 * 
	 * @return Returns the name.
	 */
	public String getIsSupportDotcard() {
		return getNoNullString((String) this.getProperty("isSupportDotcard")
				.getValue());
	}

	/**
	 * 设置内容内容是否支持点卡支持
	 * 
	 * @param isSupportDotcard
	 *            The isSupportDotcard to set.
	 */
	public void setIsSupportDotcard(String isSupportDotcard) {
		Property pro = new Property("isSupportDotcard", isSupportDotcard);
		this.setProperty(pro);
	}

	/**
	 * 设置程序安装包大小，单位为K
	 */
	public void setProgramSize(int programSize) {
		Property pro = new Property("programSize", new Integer(programSize));
		this.setProperty(pro);
	}

	/**
	 * 获取程序安装包大小，单位为K
	 * 
	 * @return
	 */
	public int getProgramSize() {
		return ((Integer) this.getProperty("programSize").getValue())
				.intValue();
	}

	/**
	 * 获取软件程序ID
	 * 
	 * @return Returns the programID.
	 */
	public String getProgramID() {
		return getNoNullString((String) this.getProperty("programID")
				.getValue());
	}

	/**
	 * 设置软件程序ID
	 * 
	 * @param programID
	 */
	public void setProgramID(String programID) {
		Property pro = new Property("programID", programID);
		this.setProperty(pro);
	}

	/**
	 * 设置应用种类 0：离线应用，1：在线应用
	 */
	public void setOnlineType(int onlineType) {
		Property pro = new Property("onlineType", new Integer(onlineType));
		this.setProperty(pro);
	}

	/**
	 * 获取应用种类
	 * 
	 * @return
	 */
	public int getOnlineType() {
		return ((Integer) this.getProperty("onlineType").getValue()).intValue();
	}

	/**
	 * 获取软件应用版本号
	 * 
	 * @return Returns the version.
	 */
	public String getVersion() {
		return getNoNullString((String) this.getProperty("version").getValue());
	}

	/**
	 * 设置软件应用版本号
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		Property pro = new Property("version", version);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图1地址
	 * 
	 * @return Returns the picture1.
	 */
	public String getPicture1() {
		return getNoNullString((String) this.getProperty("picture1").getValue());
	}

	/**
	 * 设置软件使用截图1地址
	 * 
	 * @param picture1
	 */
	public void setPicture1(String picture1) {
		Property pro = new Property("picture1", picture1);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图2地址
	 * 
	 * @return Returns the picture2.
	 */
	public String getPicture2() {
		return getNoNullString((String) this.getProperty("picture2").getValue());
	}

	/**
	 * 设置软件使用截图2地址
	 * 
	 * @param picture1
	 */
	public void setPicture2(String picture2) {
		Property pro = new Property("picture2", picture2);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图3地址
	 * 
	 * @return Returns the picture3.
	 */
	public String getPicture3() {
		return getNoNullString((String) this.getProperty("picture3").getValue());
	}

	/**
	 * 设置软件使用截图3地址
	 * 
	 * @param picture3
	 */
	public void setPicture3(String picture3) {
		Property pro = new Property("picture3", picture3);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图4地址
	 * 
	 * @return Returns the picture4.
	 */
	public String getPicture4() {
		return getNoNullString((String) this.getProperty("picture4").getValue());
	}

	/**
	 * 设置软件使用截图4地址
	 * 
	 * @param picture4
	 */
	public void setPicture4(String picture4) {
		Property pro = new Property("picture4", picture4);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图5地址
	 * 
	 * @return Returns the picture5.
	 */
	public String getPicture5() {
		return getNoNullString((String) this.getProperty("picture5").getValue());
	}

	/**
	 * 设置软件使用截图5地址
	 * 
	 * @param picture5
	 */
	public void setPicture5(String picture5) {
		Property pro = new Property("picture5", picture5);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图6地址
	 * 
	 * @return Returns the picture6.
	 */
	public String getPicture6() {
		return getNoNullString((String) this.getProperty("picture6").getValue());
	}

	/**
	 * 设置软件使用截图6地址
	 * 
	 * @param picture6
	 */
	public void setPicture6(String picture6) {
		Property pro = new Property("picture6", picture6);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图7地址
	 * 
	 * @return Returns the picture7.
	 */
	public String getPicture7() {
		return getNoNullString((String) this.getProperty("picture7").getValue());
	}

	/**
	 * 设置软件使用截图7地址
	 * 
	 * @param picture7
	 */
	public void setPicture7(String picture7) {
		Property pro = new Property("picture7", picture7);
		this.setProperty(pro);
	}

	/**
	 * 获取软件使用截图8地址
	 * 
	 * @return Returns the picture8.
	 */
	public String getPicture8() {
		return getNoNullString((String) this.getProperty("picture8").getValue());
	}

	/**
	 * 设置软件使用截图8地址
	 * 
	 * @param picture8
	 */
	public void setPicture8(String picture8) {
		Property pro = new Property("picture8", picture8);
		this.setProperty(pro);
	}

	/**
	 * 获取程序包的平台类型：取值包括kjava、mobile、symbian等。
	 * 
	 * @return Returns the platform.
	 */
	public String getPlatform() {
		return getNoNullString((String) this.getProperty("platform").getValue());
	}

	/**
	 * 设置程序包的平台类型：取值包括kjava、mobile、symbian等。
	 * 
	 * @param platform
	 */
	public void setPlatform(String platform) {
		Property pro = new Property("platform", platform);
		this.setProperty(pro);
	}

	/**
	 * 获取计费时机 1：下载时计费 2：体验后计费。
	 * 
	 * @return Returns the chargeTime.
	 */
	public String getChargeTime() {
		return getNoNullString((String) this.getProperty("chargeTime")
				.getValue());
	}

	/**
	 * 设置计费时机 1：下载时计费 2：体验后计费。
	 * 
	 * @param chargeTime
	 */
	public void setChargeTime(String chargeTime) {
		Property pro = new Property("chargeTime", chargeTime);
		this.setProperty(pro);
	}

	/**
	 * 获取应用归属省的id。
	 * 
	 * @return Returns the pvcID.
	 */
	public String getPvcID() {
		return getNoNullString((String) this.getProperty("pvcID").getValue());
	}

	/**
	 * 设置应用归属省的id
	 * 
	 * @param pvcID
	 */
	public void setPvcID(String pvcID) {
		Property pro = new Property("pvcID", pvcID);
		this.setProperty(pro);
	}

	/**
	 * 获取应用归属省的id。
	 * 
	 * @return Returns the pvcID.
	 */
	public String getCityID() {
		return getNoNullString((String) this.getProperty("cityID").getValue());
	}

	/**
	 * 设置应用归属省的id
	 * 
	 * @param pvcID
	 */
	public void setCityID(String cityID) {
		Property pro = new Property("cityID", cityID);
		this.setProperty(pro);
	}
	
	/**
	 * 设置创业大赛年份--20110427 by dongke
	 */
	public void setContestYear(String contestYear) {
		Property pro = new Property("contestYear", contestYear);
		this.setProperty(pro);
	}

	/**
	 * 获取应用 创业大赛年份--20110427 by dongke
	 * 
	 * @return
	 */
	public String getContestYear() {
		return getNoNullString((String) this.getProperty("contestYear").getValue());
	}
	
	//在t_r_gcontent 增加 FUNCDESC varchar2(2000)， 为了是电子流在CM_CT_APPGAME加了个“新功能介绍”介绍的字段。
	//alter table t_r_gcontent add funcdesc varchar2(2000)
	//add by aiyan 2011-11-10
	public void setFuncdesc(String funcdesc){
		Property pro = new Property("funcdesc", funcdesc);
		this.setProperty(pro);
	}
	
	public String getFuncdesc(){
		return getNoNullString((String) this.getProperty("funcdesc").getValue());
	}

}
