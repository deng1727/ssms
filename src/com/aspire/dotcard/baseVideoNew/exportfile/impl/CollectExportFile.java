package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import com.aspire.common.config.ConfigFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;
import com.aspire.dotcard.baseVideoNew.vo.NodeVO;

public class CollectExportFile extends BaseExportFile{

	private static final String IMAGE_210_141 = "VHDPI";
	private static final String IMAGE_140_94 = "VMDPI";
	private static final String IMAGE_93_63 = "VSDPI";
	private static final String IMAGE_162_231 = "HHDPI";
	private static final String IMAGE_108_154 = "HMDPI";
	private static final String IMAGE_72_103 = "HSDPI";
	private static final String IMAGE = "IMAGE";
	private static final String LTYPE = "|SORTTYPE=ASC";
	private static final String TYPE = "SORTTYPE=ASC";
	
	public CollectExportFile()
	{
		this.tableName = "t_vo_collect";
		this.fileName = "i_v-collect_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-collect_~DyyyyMMdd~.verf";
		this.mailTitle = "基地内容集数据导入结果";
		this.isDelMidTable = false;
		this.isCollect = true;
	}
	
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getCollectIDMap();
	}
	
	@Override
	protected String getKey(String[] data) {
		return data[0];
	}
	
	@Override
	protected String checkData(String[] data,boolean flag) {
		String collectID = data[0];
		String tmp = collectID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证内容集数据字段格式，collectID=" + collectID);
		}
		
		if (data.length != 8)
		{
			logger.error("字段数不等于8，字段长度:"+data.length);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("collectID=" + collectID + ",collectID验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		NodeVO vo = BaseVideoNewFileDAO.getInstance().getNodeDataByNodeId(collectID);
		if(vo == null){
			logger.error("collectID=" + collectID + ",collectID验证错误，collectID对应的栏目不存在");
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// collectname
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("collectID=" + collectID
					+ ",collectname验证出错，该字段是必填字段，长度不超过128个字符！collectname="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		//description
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, false))
		{
			logger.error("collectID=" + collectID
					+ ",description验证出错，该字段是必填字段，长度不超过4000个字符！description="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// sortID
		tmp = data[3];
		if (!BaseFileNewTools.checkIntegerField("排序号", tmp, 10, false))
		{
			logger.error("collectID=" + collectID + ",sortID验证出错，长度不超过10个数值！sortID="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		// lookflag
		tmp = data[4];
		if (!BaseFileNewTools.checkIntegerField("查看方式", tmp, 2, false))
		{
			logger.error("collectID=" + collectID + ",lookflag验证出错，长度不超过2个数值！lookflag="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		//property
		tmp = data[5];
		if (!BaseFileNewTools.checkFieldLength(tmp, 1024, false))
		{
			logger.error("collectID=" + collectID
					+ ",property验证错误，该字段非必填字段，且长度不超过1024个字符错误！property=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		//image
		tmp = data[6];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, false))
		{
			logger.error("collectID=" + collectID
					+ ",image验证错误，该字段非必填字段，且长度不超过4000个字符错误！image=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		//collectvid
		tmp = data[7];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, false))
		{
			logger.error("collectID=" + collectID
					+ ",collectvid验证错误，该字段非必填字段，且长度不超过60个字符错误！collectvid=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		return BaseVideoNewConfig.CHECK_DATA_SUCCESS;
	}

	@Override
	protected Object[] getObject(String[] data) {
        Object[] object = new Object[21];
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		String property = data[5];
		object[4] = property;
		//property = property.replaceAll(LTYPE, "");
		//property = property.replaceAll(TYPE, "");
		int start1 = property.toUpperCase().indexOf(LTYPE);
		if(start1 >= 0){
			 property = property.substring(0, start1) + property.substring(start1+13, property.length());
		}
		int start2 = property.toUpperCase().indexOf(TYPE);
		if(start2>=0){
			property = property.substring(0, start2) + property.substring(start2+12, property.length());
		}
		String[] pros = property.split("\\|");
		for(int i = 0 ; i < pros.length ;i++){
			object[5+i] = pros[i];
			if(i > 5)
				break;
		}
		String image = data[6];
		object[12] = image;
		String[]  images = image.split("\\|");
		for(int i = 0 ; i < images.length ;i++){
			setImageValue(object, images[i]);
		}
		object[19] = data[7];
		object[20] = data[0];
		return object;
	}
	
	@Override
	protected String getInsertSqlCode() {
		// insert into t_vo_collect (collectname,description,sortid,lookflag,property,property1,property2,property3,property4,property5,property6,property7,image,image1,image2,image3,image4,image5,image6,collectvid,exporttime, collectid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)
		return "baseVideoNew.exportfile.CollectExportFile.getInsertSqlCode";
	}
	
	@Override
	protected String getUpdateSqlCode() {
		// update t_vo_collect set collectname = ?,description = ?,sortid = ?,lookflag = ?,property = ?,property1 = ?,property2 = ?,property3 = ?,property4 = ?,property5 = ?,property6 = ?,property7 = ?,image = ?,image1 = ?,image2 = ?,image3 = ?,image4 = ?,image5 = ?,image6 = ?,collectvid = ? where collectid = ?
		return "baseVideoNew.exportfile.CollectExportFile.getUpdateSqlCode";
	}
	
	@Override
	protected String getDelSqlCode() {
		// delete from t_vo_collect where collectid = ?
		return "baseVideoNew.exportfile.CollectExportFile.getDelSqlCode";
	}

	private void setImageValue(Object[] object,String image){
		if(null == image || "".equals(image))
			return;
		if(image.contains(IMAGE_210_141)){
			object[13] = getImageAllPath(image);
		}else if(image.contains(IMAGE_140_94)){
			object[14] = getImageAllPath(image);
		}else if(image.contains(IMAGE_93_63)){
			object[15] = getImageAllPath(image);
		}else if(image.contains(IMAGE_162_231)){
			object[16] = getImageAllPath(image);
		}else if(image.contains(IMAGE_108_154)){
			object[17] = getImageAllPath(image);
		}else if(image.contains(IMAGE_72_103)){
			object[18] = getImageAllPath(image);
		}
	}
	
	private String getImageAllPath(String image){
		if(null == image || "".equals(image))
			return "";
		String str = image.substring(image.indexOf("@")+1);
		String strtemp = str.toUpperCase();
		str = str.substring(strtemp.indexOf(IMAGE));
		String collectImgUrl =  ConfigFactory.getSystemConfig()
        .getModuleConfig("BaseVideoFileConfig").getItemValue("CollectImgUrl").trim();


		
		if (str.startsWith("/"))
		{
			return collectImgUrl + str;
		}
		else
		{
			return collectImgUrl + "/" + str;
		}
	}
	
	public static void main(String[] args){
		/*String str = "i_b-catalogContent_20140227_000001.txt|164|100|20140227|20140227135516";
		Object[] object = str.split("|",-1);
		for(int i=0;i<object.length;i++){
			System.out.println("---:"+object[i]+":"+object.length);
		}*/
		/*CollectExportFile s = new CollectExportFile();
		System.out.println();
		String str = "aa_VMDPI@/depository/image/10/374/49.jpg";
		System.out.println(str.substring(str.indexOf("@")+1));
		String strtemp = str.toUpperCase();
		str = str.substring(strtemp.indexOf("IMAGE"));
		System.out.println(str);*/
		String string = "sdfds,ffdsfds,";
		String[] tempData = string.split(",",-1);
		System.out.println(tempData.length + tempData[2]);
	}
}
