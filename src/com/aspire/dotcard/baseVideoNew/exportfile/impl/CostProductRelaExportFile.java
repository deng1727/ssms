package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;
import com.aspire.dotcard.baseVideoNew.vo.NodeVO;

public class CostProductRelaExportFile extends BaseExportFile{


	
	public CostProductRelaExportFile()
	{
		this.tableName = "t_vo_cost";
		this.fileName = "i_v-costproductrelation_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-costproductrelation_~DyyyyMMdd~.verf";
		this.mailTitle = "向MM提供全量的产品打折信息";
		this.isDelMidTable = false;
		this.isCollect = true;
	}
	
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getCostProductIDMap();
	}
	
	@Override
	protected String getKey(String[] data) {
		return data[0];
	}
	
	@Override
	protected String checkData(String[] data,boolean flag) {
		String productID = data[0];
		String tmp = productID;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证内容集数据字段格式，productID=" + productID);
		}
		
		if (data.length != 3)
		{
			logger.error("字段数不等于3，字段长度:"+data.length);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("productID=" + productID + ",productID验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("costID=" + tmp
					+ ",costID验证出错，该字段是必填字段，长度不超过60个字符！costID="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		//description
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 10, true))
		{
			logger.error("depositRate=" + tmp
					+ ",depositrate验证出错，该字段是必填字段，长度不超过10个字符！depositrate="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		
		return BaseVideoNewConfig.CHECK_DATA_SUCCESS;
	}

	@Override
	protected Object[] getObject(String[] data) {
        Object[] object = new Object[3];
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[0];
		return object;
	}
	
	@Override
	protected String getInsertSqlCode() {
		// insert into t_vo_collect (collectname,description,sortid,lookflag,property,property1,property2,property3,property4,property5,property6,property7,image,image1,image2,image3,image4,image5,image6,exporttime, collectid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)
		return "baseVideoNew.exportfile.CostExportFile.getInsertSqlCode";
	}
	
	@Override
	protected String getUpdateSqlCode() {
		// update t_vo_collect set collectname = ?,description = ?,sortid = ?,lookflag = ?,property = ?,property1 = ?,property2 = ?,property3 = ?,property4 = ?,property5 = ?,property6 = ?,property7 = ?,image = ?,image1 = ?,image2 = ?,image3 = ?,image4 = ?,image5 = ?,image6 = ? where collectid = ?
		return "baseVideoNew.exportfile.CostExportFile.getUpdateSqlCode";
	}
	
	@Override
	protected String getDelSqlCode() {
		// delete from t_vo_collect where collectid = ?
		return "baseVideoNew.exportfile.CostExportFile.getDelSqlCode";
	}
	
	public static void main(String[] args){
		String str = "i_b-catalogContent_20140227_000001.txt|164|100|20140227|20140227135516";
		Object[] object = str.split("|",-1);
		for(int i=0;i<object.length;i++){
			System.out.println("---:"+object[i]+":"+object.length);
		}
	}
}
