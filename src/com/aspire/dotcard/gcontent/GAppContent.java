package com.aspire.dotcard.gcontent;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.Property;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * Ӧ����ص����ݻ���
 * 
 * @author bihui
 * 
 */
public class GAppContent extends GContent {
	// ORACLE���ж��varchar2(4000)�ֶ�ʱ��JDBCֻ�ܴ���4000/3�����ȣ�����ʱ�ᱨ��
	// ORA-01461 ��������Ϊ����LONG�е�LONGֵ��ֵ
	private final int MAX_LENGTH = 1333;

	private final int MAX_COLUMNS = 20;

	/**
	 * ��־��¼����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(GAppContent.class);

	/**
	 * ��Դ���ͣ�Ӧ�����ݣ���������
	 */
	public static final String TYPE = "nt:gcontent:app";

	/**
	 * ���췽��
	 */
	public GAppContent() {
		this.type = TYPE;
	}

	/**
	 * ���췽��
	 * 
	 * @param nodeID����Դid
	 */
	public GAppContent(String nodeID) {
		super(nodeID);
		this.type = TYPE;
	}

	/**
	 * ��ȡ��ǰӦ��֧�ֵĻ���
	 * 
	 * @return
	 */
	public String getBrand() {
		return getNoNullString((String) getProperty("brand").getValue());
	}

	/**
	 * ���õ�ǰӦ��֧�ֵĻ���
	 * 
	 * @return
	 */
	public void setBrand(String brand) {
		Property property = new Property("brand", brand);
		this.setProperty(property);
	}
	

	/**
	 * ��ҵ������־
	 * 
	 * @return
	 */
	public String getIsmmtoevent() {
		return getNoNullString((String) getProperty("ismmtoevent").getValue());
	}

	/**
	 * ��ҵ������־
	 * 
	 * @return
	 */
	public void setIsmmtoevent(String ismmtoevent) {
		Property property = new Property("ismmtoevent", ismmtoevent);
		this.setProperty(property);
	}
	/**
	 * �Ƿ������ʾ
	 * 
	 * @return
	 */
	public String getCopyrightFlag() {
		return getNoNullString((String) getProperty("copyrightFlag").getValue());
	}

	/**
	 * �Ƿ������ʾ
	 * 
	 * @return
	 */
	public void setCopyrightFlag(String CopyrightFlag) {
		Property property = new Property("copyrightFlag", CopyrightFlag);
		this.setProperty(property);
	}
	/**
	 * ��ȡ�ն�����
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
	 * �����ն�����
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
			// ���10���ֶ�
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
	 * ȡ��ʽ�������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���,����ʾ������Ϣ�ͱ�����Ϣ��ҳ����ʹ�� content_info.jsp,content_edit.jsp
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

		// ȥ�����Ļ��з�
		int j = 0;
		j = formatDeviceName.lastIndexOf("\r");
		if (j > 0) {
			formatDeviceName = formatDeviceName.substring(0, j);
		}

		return formatDeviceName;
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName01(String deviceName) {
		Property property = new Property("deviceName01", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName01() throws BOException {
		return getNoNullString((String) getProperty("deviceName01").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName02(String deviceName) {
		Property property = new Property("deviceName02", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName02() throws BOException {
		return getNoNullString((String) getProperty("deviceName02").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName03(String deviceName03) {
		Property property = new Property("deviceName03", deviceName03);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName03() throws BOException {
		return getNoNullString((String) getProperty("deviceName03").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName04(String deviceName04) {
		Property property = new Property("deviceName04", deviceName04);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName04() throws BOException {
		return getNoNullString((String) getProperty("deviceName04").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName05(String deviceName05) {
		Property property = new Property("deviceName05", deviceName05);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName05() throws BOException {
		return getNoNullString((String) getProperty("deviceName05").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName06(String deviceName06) {
		Property property = new Property("deviceName06", deviceName06);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName06() throws BOException {
		return getNoNullString((String) getProperty("deviceName06").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName07(String deviceName07) {
		Property property = new Property("deviceName07", deviceName07);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName07() throws BOException {
		return getNoNullString((String) getProperty("deviceName07").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName08(String deviceName08) {
		Property property = new Property("deviceName08", deviceName08);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName08() throws BOException {
		return getNoNullString((String) getProperty("deviceName08").getValue());
	}
	
	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setAppId(String appId) {
		Property property = new Property("appId", appId);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getAppId()  {
		return getNoNullString((String) getProperty("appId").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName09(String deviceName09) {
		Property property = new Property("deviceName09", deviceName09);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName09() throws BOException {
		return getNoNullString((String) getProperty("deviceName09").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName10(String deviceName10) {
		Property property = new Property("deviceName10", deviceName10);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName10() throws BOException {
		return getNoNullString((String) getProperty("deviceName10").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName11(String deviceName) {
		Property property = new Property("deviceName11", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName11() throws BOException {
		return getNoNullString((String) getProperty("deviceName11").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName12(String deviceName) {
		Property property = new Property("deviceName12", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName12() throws BOException {
		return getNoNullString((String) getProperty("deviceName12").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName13(String deviceName13) {
		Property property = new Property("deviceName13", deviceName13);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName13() throws BOException {
		return getNoNullString((String) getProperty("deviceName13").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName14(String deviceName14) {
		Property property = new Property("deviceName14", deviceName14);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName14() throws BOException {
		return getNoNullString((String) getProperty("deviceName14").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName15(String deviceName15) {
		Property property = new Property("deviceName15", deviceName15);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName15() throws BOException {
		return getNoNullString((String) getProperty("deviceName15").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName16(String deviceName) {
		Property property = new Property("deviceName16", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName16() throws BOException {
		return getNoNullString((String) getProperty("deviceName16").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName17(String deviceName) {
		Property property = new Property("deviceName17", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName17() throws BOException {
		return getNoNullString((String) getProperty("deviceName17").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName18(String deviceName) {
		Property property = new Property("deviceName18", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName18() throws BOException {
		return getNoNullString((String) getProperty("deviceName18").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName19(String deviceName) {
		Property property = new Property("deviceName19", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName19() throws BOException {
		return getNoNullString((String) getProperty("deviceName19").getValue());
	}

	/**
	 * ������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @param deviceName
	 *            �ֻ��ͺ��б��ַ�����{}Ϊ�߽��������Ϊ�ָ���
	 */
	public void setDeviceName20(String deviceName) {
		Property property = new Property("deviceName20", deviceName);
		this.setProperty(property);
	}

	/**
	 * ȡ��Ϸ֧�ֵ��ֻ��ͺ��б��ַ���
	 * 
	 * @return
	 * @throws BOException
	 */
	public String getDeviceName20() throws BOException {
		return getNoNullString((String) getProperty("deviceName20").getValue());
	}

	/**
	 * ��ȡ�����ڲ�Ʒ��Ӧ����ϵͳ�е����ڷ���Ŀ¼ID
	 * 
	 * @return Returns the appCateID.
	 */
	public String getAppCateID() {
		return getNoNullString((String) this.getProperty("appCateID")
				.getValue());
	}

	/**
	 * ���������ڲ�Ʒ��Ӧ����ϵͳ�е����ڷ���Ŀ¼ID
	 * 
	 * @param appCateID
	 *            The appCateID to set.
	 */
	public void setAppCateID(String appCateID) {
		Property pro = new Property("appCateID", appCateID);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�����ڲ�Ʒ��Ӧ����ϵͳ�е����ڷ���Ŀ¼����
	 * 
	 * @return Returns the appCateName.
	 */
	public String getAppCateName() {
		return getNoNullString((String) this.getProperty("appCateName")
				.getValue());
	}

	/**
	 * ���������ڲ�Ʒ��Ӧ����ϵͳ�е����ڷ���Ŀ¼����
	 * 
	 * @param appCateName
	 *            The appCateName to set.
	 */
	public void setAppCateName(String appCateName) {
		Property pro = new Property("appCateName", appCateName);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ��Ʒ�ṩ��
	 * 
	 * @return Returns the provider.
	 */
	public String getProvider() {
		return getNoNullString((String) this.getProperty("provider").getValue());
	}

	/**
	 * ���ò�Ʒ�ṩ��
	 * 
	 * @param provider
	 *            The provider to set.
	 */
	public void setProvider(String provider) {
		Property pro = new Property("provider", provider);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���֧�ֵ����ԣ�1:�������ġ�2:�������� 3:Ӣ�� 0:������
	 * 
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return getNoNullString((String) this.getProperty("language").getValue());
	}

	/**
	 * �������֧�ֵ����ԣ�1:�������ġ�2:�������� 3:Ӣ�� 0:������
	 * 
	 * @param language
	 *            The language to set.
	 */
	public void setLanguage(String language) {
		Property pro = new Property("language", language);
		this.setProperty(pro);
	}

	/**
	 * ��ȡWWW����ͼ1��ַ
	 * 
	 * @return Returns the WWWPropaPicture1.
	 */
	public String getWWWPropaPicture1() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture1")
				.getValue());
	}

	/**
	 * ����WWW����ͼ1��ַ
	 * 
	 * @param WWWPropaPicture1
	 */
	public void setWWWPropaPicture1(String WWWPropaPicture1) {
		Property pro = new Property("WWWPropaPicture1", WWWPropaPicture1);
		this.setProperty(pro);
	}

	/**
	 * ��ȡWWW����ͼ2��ַ
	 * 
	 * @return Returns the WWWPropaPicture2.
	 */
	public String getWWWPropaPicture2() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture2")
				.getValue());
	}

	/**
	 * ����WWW����ͼ2��ַ
	 * 
	 * @param WWWPropaPicture2
	 */
	public void setWWWPropaPicture2(String WWWPropaPicture2) {
		Property pro = new Property("WWWPropaPicture2", WWWPropaPicture2);
		this.setProperty(pro);
	}

	/**
	 * ��ȡWWW����ͼ3��ַ
	 * 
	 * @return Returns the WWWPropaPicture3.
	 */
	public String getWWWPropaPicture3() {
		return getNoNullString((String) this.getProperty("WWWPropaPicture3")
				.getValue());
	}

	/**
	 * ����WWW����ͼ3��ַ
	 * 
	 * @param WWWPropaPicture3
	 */
	public void setWWWPropaPicture3(String WWWPropaPicture3) {
		Property pro = new Property("WWWPropaPicture3", WWWPropaPicture3);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�ն�Ԥ��ͼ1��ַ
	 * 
	 * @return Returns the clientPreviewPicture1.
	 */
	public String getClientPreviewPicture1() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture1").getValue());
	}

	/**
	 * �����ն�Ԥ��ͼ1��ַ
	 * 
	 * @param clientPreviewPicture
	 */
	public void setClientPreviewPicture1(String clientPreviewPicture1) {
		Property pro = new Property("clientPreviewPicture1",
				clientPreviewPicture1);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�ն�Ԥ��ͼ2��ַ
	 * 
	 * @return Returns the clientPreviewPicture2.
	 */
	public String getClientPreviewPicture2() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture2").getValue());
	}

	/**
	 * �����ն�Ԥ��ͼ2��ַ
	 * 
	 * @param clientPreviewPicture2
	 */
	public void setClientPreviewPicture2(String clientPreviewPicture2) {
		Property pro = new Property("clientPreviewPicture2",
				clientPreviewPicture2);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�ն�Ԥ��ͼ3��ַ
	 * 
	 * @return Returns the clientPreviewPicture3.
	 */
	public String getClientPreviewPicture3() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture3").getValue());
	}

	/**
	 * �����ն�Ԥ��ͼ3��ַ
	 * 
	 * @param clientPreviewPicture3
	 */
	public void setClientPreviewPicture3(String clientPreviewPicture3) {
		Property pro = new Property("clientPreviewPicture3",
				clientPreviewPicture3);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�ն�Ԥ��ͼ4��ַ
	 * 
	 * @return Returns the clientPreviewPicture4.
	 */
	public String getClientPreviewPicture4() {
		return getNoNullString((String) this.getProperty(
				"clientPreviewPicture4").getValue());
	}

	/**
	 * �����ն�Ԥ��ͼ4��ַ
	 * 
	 * @param clientPreviewPicture4
	 */
	public void setClientPreviewPicture4(String clientPreviewPicture4) {
		Property pro = new Property("clientPreviewPicture4",
				clientPreviewPicture4);
		this.setProperty(pro);
	}

	/**
	 * ��ȡLOGO1ͼƬ��ַ
	 * 
	 * @return Returns the LOGO1.
	 */
	public String getLOGO1() {
		return getNoNullString((String) this.getProperty("LOGO1").getValue());
	}

	/**
	 * ����LOGO1ͼƬ��ַ
	 * 
	 * @param LOGO1
	 */
	public void setLOGO1(String LOGO1) {
		Property pro = new Property("LOGO1", LOGO1);
		this.setProperty(pro);
	}

	/**
	 * ��ȡLOGO2ͼƬ��ַ
	 * 
	 * @return Returns the LOGO2.
	 */
	public String getLOGO2() {
		return getNoNullString((String) this.getProperty("LOGO2").getValue());
	}

	/**
	 * ����LOGO2ͼƬ��ַ
	 * 
	 * @param LOGO2
	 */
	public void setLOGO2(String LOGO2) {
		Property pro = new Property("LOGO2", LOGO2);
		this.setProperty(pro);
	}

	/**
	 * ��ȡLOGO3ͼƬ��ַ
	 * 
	 * @return Returns the LOGO3.
	 */
	public String getLOGO3() {
		return getNoNullString((String) this.getProperty("LOGO3").getValue());
	}

	/**
	 * ����LOGO3ͼƬ��ַ
	 * 
	 * @param LOGO3
	 */
	public void setLOGO3(String LOGO3) {
		Property pro = new Property("LOGO3", LOGO3);
		this.setProperty(pro);
	}

	/**
	 * ��ȡLOGO4ͼƬ��ַ
	 * 
	 * @return Returns the LOGO4.
	 */
	public String getLOGO4() {
		return getNoNullString((String) this.getProperty("LOGO4").getValue());
	}

	/**
	 * ����LOGO4ͼƬ��ַ
	 * 
	 * @param LOGO4
	 */
	public void setLOGO4(String LOGO4) {
		Property pro = new Property("LOGO4", LOGO4);
		this.setProperty(pro);
	}

	
	/**
	 * ��ȡLOGO5ͼƬ��ַ
	 * 
	 * @return Returns the LOGO5.
	 */
	public String getLOGO5() {
		return getNoNullString((String) this.getProperty("LOGO5").getValue());
	}

	/**
	 * ����LOGO5ͼƬ��ַ
	 * 
	 * @param LOGO5
	 */
	public void setLOGO5(String LOGO5) {
		Property pro = new Property("LOGO5", LOGO5);
		this.setProperty(pro);
	}
	
	 /**
     * ��ȡLOGO6ͼƬ��ַ
     * 
     * @return Returns the LOGO6.
     * add fanqh 201310
     */
    public String getLOGO6() {
        return getNoNullString((String) this.getProperty("LOGO6").getValue());
    }

    /**
     * ����LOGO6ͼƬ��ַ
     * 
     * @param LOGO6
     * add fanqh 201310
     */
    public void setLOGO6(String LOGO6) {
        Property pro = new Property("LOGO6", LOGO6);
        this.setProperty(pro);
    }
	/**
	 * ��ȡ���������ͼ��ַ
	 * 
	 * @return Returns the cartoonPicture.
	 */
	public String getCartoonPicture() {
		return getNoNullString((String) this.getProperty("cartoonPicture")
				.getValue());
	}

	/**
	 * �������������ͼ��ַ
	 * 
	 * @param cartoonPicture
	 */
	public void setCartoonPicture(String cartoonPicture) {
		Property pro = new Property("cartoonPicture", cartoonPicture);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�����Ƿ�֧�ֵ㿨֧��
	 * 
	 * @return Returns the name.
	 */
	public String getIsSupportDotcard() {
		return getNoNullString((String) this.getProperty("isSupportDotcard")
				.getValue());
	}

	/**
	 * �������������Ƿ�֧�ֵ㿨֧��
	 * 
	 * @param isSupportDotcard
	 *            The isSupportDotcard to set.
	 */
	public void setIsSupportDotcard(String isSupportDotcard) {
		Property pro = new Property("isSupportDotcard", isSupportDotcard);
		this.setProperty(pro);
	}

	/**
	 * ���ó���װ����С����λΪK
	 */
	public void setProgramSize(int programSize) {
		Property pro = new Property("programSize", new Integer(programSize));
		this.setProperty(pro);
	}

	/**
	 * ��ȡ����װ����С����λΪK
	 * 
	 * @return
	 */
	public int getProgramSize() {
		return ((Integer) this.getProperty("programSize").getValue())
				.intValue();
	}

	/**
	 * ��ȡ�������ID
	 * 
	 * @return Returns the programID.
	 */
	public String getProgramID() {
		return getNoNullString((String) this.getProperty("programID")
				.getValue());
	}

	/**
	 * �����������ID
	 * 
	 * @param programID
	 */
	public void setProgramID(String programID) {
		Property pro = new Property("programID", programID);
		this.setProperty(pro);
	}

	/**
	 * ����Ӧ������ 0������Ӧ�ã�1������Ӧ��
	 */
	public void setOnlineType(int onlineType) {
		Property pro = new Property("onlineType", new Integer(onlineType));
		this.setProperty(pro);
	}

	/**
	 * ��ȡӦ������
	 * 
	 * @return
	 */
	public int getOnlineType() {
		return ((Integer) this.getProperty("onlineType").getValue()).intValue();
	}

	/**
	 * ��ȡ���Ӧ�ð汾��
	 * 
	 * @return Returns the version.
	 */
	public String getVersion() {
		return getNoNullString((String) this.getProperty("version").getValue());
	}

	/**
	 * �������Ӧ�ð汾��
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		Property pro = new Property("version", version);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ1��ַ
	 * 
	 * @return Returns the picture1.
	 */
	public String getPicture1() {
		return getNoNullString((String) this.getProperty("picture1").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ1��ַ
	 * 
	 * @param picture1
	 */
	public void setPicture1(String picture1) {
		Property pro = new Property("picture1", picture1);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ2��ַ
	 * 
	 * @return Returns the picture2.
	 */
	public String getPicture2() {
		return getNoNullString((String) this.getProperty("picture2").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ2��ַ
	 * 
	 * @param picture1
	 */
	public void setPicture2(String picture2) {
		Property pro = new Property("picture2", picture2);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ3��ַ
	 * 
	 * @return Returns the picture3.
	 */
	public String getPicture3() {
		return getNoNullString((String) this.getProperty("picture3").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ3��ַ
	 * 
	 * @param picture3
	 */
	public void setPicture3(String picture3) {
		Property pro = new Property("picture3", picture3);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ4��ַ
	 * 
	 * @return Returns the picture4.
	 */
	public String getPicture4() {
		return getNoNullString((String) this.getProperty("picture4").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ4��ַ
	 * 
	 * @param picture4
	 */
	public void setPicture4(String picture4) {
		Property pro = new Property("picture4", picture4);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ5��ַ
	 * 
	 * @return Returns the picture5.
	 */
	public String getPicture5() {
		return getNoNullString((String) this.getProperty("picture5").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ5��ַ
	 * 
	 * @param picture5
	 */
	public void setPicture5(String picture5) {
		Property pro = new Property("picture5", picture5);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ6��ַ
	 * 
	 * @return Returns the picture6.
	 */
	public String getPicture6() {
		return getNoNullString((String) this.getProperty("picture6").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ6��ַ
	 * 
	 * @param picture6
	 */
	public void setPicture6(String picture6) {
		Property pro = new Property("picture6", picture6);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ7��ַ
	 * 
	 * @return Returns the picture7.
	 */
	public String getPicture7() {
		return getNoNullString((String) this.getProperty("picture7").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ7��ַ
	 * 
	 * @param picture7
	 */
	public void setPicture7(String picture7) {
		Property pro = new Property("picture7", picture7);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ���ʹ�ý�ͼ8��ַ
	 * 
	 * @return Returns the picture8.
	 */
	public String getPicture8() {
		return getNoNullString((String) this.getProperty("picture8").getValue());
	}

	/**
	 * �������ʹ�ý�ͼ8��ַ
	 * 
	 * @param picture8
	 */
	public void setPicture8(String picture8) {
		Property pro = new Property("picture8", picture8);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�������ƽ̨���ͣ�ȡֵ����kjava��mobile��symbian�ȡ�
	 * 
	 * @return Returns the platform.
	 */
	public String getPlatform() {
		return getNoNullString((String) this.getProperty("platform").getValue());
	}

	/**
	 * ���ó������ƽ̨���ͣ�ȡֵ����kjava��mobile��symbian�ȡ�
	 * 
	 * @param platform
	 */
	public void setPlatform(String platform) {
		Property pro = new Property("platform", platform);
		this.setProperty(pro);
	}

	/**
	 * ��ȡ�Ʒ�ʱ�� 1������ʱ�Ʒ� 2�������Ʒѡ�
	 * 
	 * @return Returns the chargeTime.
	 */
	public String getChargeTime() {
		return getNoNullString((String) this.getProperty("chargeTime")
				.getValue());
	}

	/**
	 * ���üƷ�ʱ�� 1������ʱ�Ʒ� 2�������Ʒѡ�
	 * 
	 * @param chargeTime
	 */
	public void setChargeTime(String chargeTime) {
		Property pro = new Property("chargeTime", chargeTime);
		this.setProperty(pro);
	}

	/**
	 * ��ȡӦ�ù���ʡ��id��
	 * 
	 * @return Returns the pvcID.
	 */
	public String getPvcID() {
		return getNoNullString((String) this.getProperty("pvcID").getValue());
	}

	/**
	 * ����Ӧ�ù���ʡ��id
	 * 
	 * @param pvcID
	 */
	public void setPvcID(String pvcID) {
		Property pro = new Property("pvcID", pvcID);
		this.setProperty(pro);
	}

	/**
	 * ��ȡӦ�ù���ʡ��id��
	 * 
	 * @return Returns the pvcID.
	 */
	public String getCityID() {
		return getNoNullString((String) this.getProperty("cityID").getValue());
	}

	/**
	 * ����Ӧ�ù���ʡ��id
	 * 
	 * @param pvcID
	 */
	public void setCityID(String cityID) {
		Property pro = new Property("cityID", cityID);
		this.setProperty(pro);
	}
	
	/**
	 * ���ô�ҵ�������--20110427 by dongke
	 */
	public void setContestYear(String contestYear) {
		Property pro = new Property("contestYear", contestYear);
		this.setProperty(pro);
	}

	/**
	 * ��ȡӦ�� ��ҵ�������--20110427 by dongke
	 * 
	 * @return
	 */
	public String getContestYear() {
		return getNoNullString((String) this.getProperty("contestYear").getValue());
	}
	
	//��t_r_gcontent ���� FUNCDESC varchar2(2000)�� Ϊ���ǵ�������CM_CT_APPGAME���˸����¹��ܽ��ܡ����ܵ��ֶΡ�
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
