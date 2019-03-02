package com.aspire.dotcard.syncGoodsCenter.vo;

import java.sql.Date;

public class GcAppHotInfo {
	private String  CONTENT_ID ;
	private String  CONTENT_NAME ;
	private String   PRODUCT_TYPE_ID;
	private String   PRODUCT_SUBTYPE_ID;
	private Date  ONLINE_DATE ;
	private Date  OFFLINE_DATE ;
	private String  CHARGE_TYPE_ID ;
	private String   ICP_TYPE;
	private String   COMPANY_ID;
	private int   STATUS;
	private int   SUBSTATUS;
	private int   ISLOCK;
	private Date FEECOMMERCIALDATE  ;
	private String  CONTENTSTATUS ;
	private String   CONTATTR;
	private Date   FIRSTCOMMERCIALDATE;
	private String  GRADE ;
	private String  THIRDAPPTYPE ;
	private String  HATCHAPPID ;
	private String   FEE;
	private String   CONTENTCODE;
	private String   CONT_STAT;
	private Date  FLOW_TIME ;
	private String  PROGRAM_SIZE ;
	private String  CON_LEVEL ;
	private String  ISCONVERGE ;
	private Date  LUPDDATE ;
	private String  VERSION ;
	private String  VERSIONCODE;
	private String  AP_NAME ;
	private String  APPSIZE ;
	private String  PACKAGE_URL ;
	private String  DOWNLOAD_NUM ;
	private String  GRADE1_TYPE ;
	private String  GRADE2_TYPE;
	private String  USAGE_DESC;
	private String  UPDATEDATE;
	private String LOGO_URL;
	private String  SCREEN_URL1;
	private String  SCREEN_URL2;
	private String  SCREEN_URL3;
	private String  SCREEN_URL4;
	private String  CONTENT_DESC;
	private String  SOURCE;
	private String  PACKAGENAME;
	private String  CATEGORYID;//goodscenter 没有的数据
	private String  GOODSID;//goodscenter 没有的数据
	private String  ID;//goodscenter 没有的数据
	private String  COPYRIGHTFLAG ;// 官方标签
	private String  APPID;   //包id
	private String  APPREALNAME ; //白牌应用名
	private String  WASHPACKSTATUS ; //是否洗包标记
	
	public String getWASHPACKSTATUS() {
		return WASHPACKSTATUS;
	}
	public void setWASHPACKSTATUS(String wASHPACKSTATUS) {
		WASHPACKSTATUS = wASHPACKSTATUS;
	}
	public String getAPPREALNAME() {
		return APPREALNAME;
	}
	public void setAPPREALNAME(String aPPREALNAME) {
		APPREALNAME = aPPREALNAME;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public String getCOPYRIGHTFLAG() {
		return COPYRIGHTFLAG;
	}
	public void setCOPYRIGHTFLAG(String cOPYRIGHTFLAG) {
		COPYRIGHTFLAG = cOPYRIGHTFLAG;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getGOODSID() {
		return GOODSID;
	}
	public void setGOODSID(String gOODSID) {
		GOODSID = gOODSID;
	}
	public String getCATEGORYID() {
		return CATEGORYID;
	}
	public void setCATEGORYID(String cATEGORYID) {
		CATEGORYID = cATEGORYID;
	}
	public String getCONTENT_ID() {
		return CONTENT_ID;
	}
	public void setCONTENT_ID(String cONTENT_ID) {
		CONTENT_ID = cONTENT_ID;
	}
	public String getCONTENT_NAME() {
		return CONTENT_NAME;
	}
	public void setCONTENT_NAME(String cONTENT_NAME) {
		CONTENT_NAME = cONTENT_NAME;
	}
	public String getPRODUCT_TYPE_ID() {
		return PRODUCT_TYPE_ID;
	}
	public void setPRODUCT_TYPE_ID(String pRODUCT_TYPE_ID) {
		PRODUCT_TYPE_ID = pRODUCT_TYPE_ID;
	}
	public String getPRODUCT_SUBTYPE_ID() {
		return PRODUCT_SUBTYPE_ID;
	}
	public void setPRODUCT_SUBTYPE_ID(String pRODUCT_SUBTYPE_ID) {
		PRODUCT_SUBTYPE_ID = pRODUCT_SUBTYPE_ID;
	}
	public Date getONLINE_DATE() {
		return ONLINE_DATE;
	}
	public void setONLINE_DATE(Date oNLINE_DATE) {
		ONLINE_DATE = oNLINE_DATE;
	}
	public Date getOFFLINE_DATE() {
		return OFFLINE_DATE;
	}
	public void setOFFLINE_DATE(Date oFFLINE_DATE) {
		OFFLINE_DATE = oFFLINE_DATE;
	}
	public String getCHARGE_TYPE_ID() {
		return CHARGE_TYPE_ID;
	}
	public void setCHARGE_TYPE_ID(String cHARGE_TYPE_ID) {
		CHARGE_TYPE_ID = cHARGE_TYPE_ID;
	}
	public String getICP_TYPE() {
		return ICP_TYPE;
	}
	public void setICP_TYPE(String iCP_TYPE) {
		ICP_TYPE = iCP_TYPE;
	}
	public String getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(String cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public int getSUBSTATUS() {
		return SUBSTATUS;
	}
	public void setSUBSTATUS(int sUBSTATUS) {
		SUBSTATUS = sUBSTATUS;
	}
	public int getISLOCK() {
		return ISLOCK;
	}
	public void setISLOCK(int iSLOCK) {
		ISLOCK = iSLOCK;
	}
	public Date getFEECOMMERCIALDATE() {
		return FEECOMMERCIALDATE;
	}
	public void setFEECOMMERCIALDATE(Date fEECOMMERCIALDATE) {
		FEECOMMERCIALDATE = fEECOMMERCIALDATE;
	}
	public String getCONTENTSTATUS() {
		return CONTENTSTATUS;
	}
	public void setCONTENTSTATUS(String cONTENTSTATUS) {
		CONTENTSTATUS = cONTENTSTATUS;
	}
	public String getCONTATTR() {
		return CONTATTR;
	}
	public void setCONTATTR(String cONTATTR) {
		CONTATTR = cONTATTR;
	}
	public Date getFIRSTCOMMERCIALDATE() {
		return FIRSTCOMMERCIALDATE;
	}
	public void setFIRSTCOMMERCIALDATE(Date fIRSTCOMMERCIALDATE) {
		FIRSTCOMMERCIALDATE = fIRSTCOMMERCIALDATE;
	}
	public String getGRADE() {
		return GRADE;
	}
	public void setGRADE(String gRADE) {
		GRADE = gRADE;
	}
	public String getTHIRDAPPTYPE() {
		return THIRDAPPTYPE;
	}
	public void setTHIRDAPPTYPE(String tHIRDAPPTYPE) {
		THIRDAPPTYPE = tHIRDAPPTYPE;
	}
	public String getHATCHAPPID() {
		return HATCHAPPID;
	}
	public void setHATCHAPPID(String hATCHAPPID) {
		HATCHAPPID = hATCHAPPID;
	}
	public String getFEE() {
		return FEE;
	}
	public void setFEE(String fEE) {
		FEE = fEE;
	}
	public String getCONTENTCODE() {
		return CONTENTCODE;
	}
	public void setCONTENTCODE(String cONTENTCODE) {
		CONTENTCODE = cONTENTCODE;
	}
	public String getCONT_STAT() {
		return CONT_STAT;
	}
	public void setCONT_STAT(String cONT_STAT) {
		CONT_STAT = cONT_STAT;
	}
	public Date getFLOW_TIME() {
		return FLOW_TIME;
	}
	public void setFLOW_TIME(Date fLOW_TIME) {
		FLOW_TIME = fLOW_TIME;
	}
	public String getPROGRAM_SIZE() {
		return PROGRAM_SIZE;
	}
	public void setPROGRAM_SIZE(String pROGRAM_SIZE) {
		PROGRAM_SIZE = pROGRAM_SIZE;
	}
	public String getCON_LEVEL() {
		return CON_LEVEL;
	}
	public void setCON_LEVEL(String cON_LEVEL) {
		CON_LEVEL = cON_LEVEL;
	}
	public String getISCONVERGE() {
		return ISCONVERGE;
	}
	public void setISCONVERGE(String iSCONVERGE) {
		ISCONVERGE = iSCONVERGE;
	}
	public Date getLUPDDATE() {
		return LUPDDATE;
	}
	public void setLUPDDATE(Date lUPDDATE) {
		LUPDDATE = lUPDDATE;
	}
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}
	public String getVERSIONCODE() {
		return VERSIONCODE;
	}
	public void setVERSIONCODE(String vERSIONCODE) {
		VERSIONCODE = vERSIONCODE;
	}
	public String getAP_NAME() {
		return AP_NAME;
	}
	public void setAP_NAME(String aP_NAME) {
		AP_NAME = aP_NAME;
	}
	public String getAPPSIZE() {
		return APPSIZE;
	}
	public void setAPPSIZE(String aPPSIZE) {
		APPSIZE = aPPSIZE;
	}
	public String getPACKAGE_URL() {
		return PACKAGE_URL;
	}
	public void setPACKAGE_URL(String pACKAGE_URL) {
		PACKAGE_URL = pACKAGE_URL;
	}
	public String getDOWNLOAD_NUM() {
		return DOWNLOAD_NUM;
	}
	public void setDOWNLOAD_NUM(String dOWNLOAD_NUM) {
		DOWNLOAD_NUM = dOWNLOAD_NUM;
	}
	public String getGRADE1_TYPE() {
		return GRADE1_TYPE;
	}
	public void setGRADE1_TYPE(String gRADE1_TYPE) {
		GRADE1_TYPE = gRADE1_TYPE;
	}
	public String getGRADE2_TYPE() {
		return GRADE2_TYPE;
	}
	public void setGRADE2_TYPE(String gRADE2_TYPE) {
		GRADE2_TYPE = gRADE2_TYPE;
	}
	public String getUSAGE_DESC() {
		return USAGE_DESC;
	}
	public void setUSAGE_DESC(String uSAGE_DESC) {
		USAGE_DESC = uSAGE_DESC;
	}
	public String getUPDATEDATE() {
		return UPDATEDATE;
	}
	public void setUPDATEDATE(String uPDATEDATE) {
		UPDATEDATE = uPDATEDATE;
	}
	public String getLOGO_URL() {
		return LOGO_URL;
	}
	public void setLOGO_URL(String lOGO_URL) {
		LOGO_URL = lOGO_URL;
	}
	public String getSCREEN_URL1() {
		return SCREEN_URL1;
	}
	public void setSCREEN_URL1(String sCREEN_URL1) {
		SCREEN_URL1 = sCREEN_URL1;
	}
	public String getSCREEN_URL2() {
		return SCREEN_URL2;
	}
	public void setSCREEN_URL2(String sCREEN_URL2) {
		SCREEN_URL2 = sCREEN_URL2;
	}
	public String getSCREEN_URL3() {
		return SCREEN_URL3;
	}
	public void setSCREEN_URL3(String sCREEN_URL3) {
		SCREEN_URL3 = sCREEN_URL3;
	}
	public String getSCREEN_URL4() {
		return SCREEN_URL4;
	}
	public void setSCREEN_URL4(String sCREEN_URL4) {
		SCREEN_URL4 = sCREEN_URL4;
	}
	public String getCONTENT_DESC() {
		return CONTENT_DESC;
	}
	public void setCONTENT_DESC(String cONTENT_DESC) {
		CONTENT_DESC = cONTENT_DESC;
	}
	public String getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}
	public String getPACKAGENAME() {
		return PACKAGENAME;
	}
	public void setPACKAGENAME(String pACKAGENAME) {
		PACKAGENAME = pACKAGENAME;
	}
	
}
