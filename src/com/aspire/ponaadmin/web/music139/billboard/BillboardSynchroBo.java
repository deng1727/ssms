package com.aspire.ponaadmin.web.music139.billboard;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.music139.AbstractSynchroData;

public class BillboardSynchroBo extends AbstractSynchroData {

	public BillboardSynchroBo() {
		ftpName = getFileName(config.getBillboardFileName(), (Date) null);
	}

	public BillboardSynchroBo(Date date) {
		ftpName = getFileName(config.getBillboardFileName(), date);
	}

	public BillboardSynchroBo(String date) {
		ftpName = getFileName(config.getBillboardFileName(), date);
	}

	static final String INSERT_T_MB_CATEGORY = "insert into T_MB_CATEGORY(categoryid,categoryname,parentcategoryid,categorydesc,"
			+ "album_id,type,delflag,createtime,sortid,sum)values(?,?,?,?,?,'1','0',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),0,?)";
	//static final String UPDATE_T_MB_CATEGORY = "update T_MB_CATEGORY set categoryname=?,parentcategoryid=?,categorydesc=?,album_id=?,sum=? where categoryid = ?";
	static final String UPDATE_T_MB_CATEGORY = "update T_MB_CATEGORY set categoryname=?,categorydesc=?,album_id=?,sum=? where categoryid = ?";

	protected Object[] getInsertCategoryObjectParams(String categoryId,
			String[] line,int sum) {
		return new Object[] { categoryId, line[1], getParentCategoryId(),
				line[2], line[0] ,new Integer(sum)};
		
	}
	
	protected Object[] getUpdateCategoryParams(String categoryId,
			String[] line,int sum){
		//return new Object[] { line[1], getParentCategoryId(),
		//		line[2], line[0] ,new Integer(sum),categoryId};
		return new Object[] { line[1], 
				line[2], line[0] ,new Integer(sum),categoryId};
	}

	protected String getParentCategoryId() {
		return "100000445";
	}

	protected String getInsertCategorySQL() {
		return INSERT_T_MB_CATEGORY;
	}
	
	protected String getUpdateCategorySQL(){
		return UPDATE_T_MB_CATEGORY;
	}

	protected int indexOfOperation() {
		return 4;
	}

	public String getOperationName() {
		return "139���ֽӿ�--�񵥻�����Ϣͬ��";
	}

	private static final JLogger log = LoggerFactory
			.getLogger(BillboardSynchroBo.class);

	protected boolean validateLine(String[] line) {
		if ("2".equals(line[this.indexOfOperation()])) {// ɾ��������У�������ֶ�
			return true;
		}
		if (line[0].length() > 30) {
			log.warn("��:" + line[0] + ",���ȳ���30");
			this.addInvalidateMsg("��:" + line[0] + ",��ID���ȳ���30");
			return false;
		}
		if (line[1].getBytes().length > 40) {
			log.warn("��:" + line[0] + ",�����Ƴ��ȳ���40");
			this.addInvalidateMsg("��:" + line[0] + ",�����Ƴ��ȳ���40");
			return false;
		}
		if (line[2] != null && line[2].getBytes().length > 1000) {
			log.warn("��:" + line[0] + ",�񵥼�鳬��1000");
			this.addInvalidateMsg("��:" + line[0] + ",�񵥼�鳤�ȳ���1000");
			return false;
		}
		return true;
	}

    public void updateFileName() throws Exception
    {
        ftpName = getFileName(config.getBillboardFileName(), ( Date ) null);
    }
}
