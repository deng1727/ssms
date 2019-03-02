---删除表
drop table T_MSTORE_CATEGORY;
alter table T_MB_MUSIC_NEW drop column PRODUCTMASK;

-- Add/modify columns 
alter table T_KEY_RESOURCE modify VALUE VARCHAR2(1000);
-- Add comments to the columns 
comment on column T_KEY_BASE.KEYTYPE
  is '域类型，1，普通文本；2，文件域;';
-- Drop columns 
alter table T_R_CATEGORY drop column STARTDATE;
alter table T_R_CATEGORY drop column ENDDATE;


----------------------------------------
----2011-10-15上线的脚本---------------------------------
----------------------------------------

-- Drop columns 
alter table T_CY_PRODUCTLIST drop column TOTALDOWNLOADUSERNUM;
alter table T_CY_PRODUCTLIST drop column TOTALTESTUSERNUM;
alter table T_CY_PRODUCTLIST drop column TOTALTESTSTAR;
alter table T_CY_PRODUCTLIST drop column TOTALSTARSCORECOUNT;
alter table T_CY_PRODUCTLIST drop column TOTALGLOBALSCORECOUNT;
-- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '下载用户数';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '测评用户数';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '测评星级';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '星探推荐得分';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '人气综合推荐指数';
  
  drop  synonym s_report_cy_productlist;
  drop  view v_report_cy_productlist;
  drop procedure p_refresh_cy_productlist;
----------------------------------------
----2011-10-15上线的脚本end--------------------
----------------------------------------  
  
----------------价格类型应用
alter table T_R_GCONTENT drop column priceType;

drop table T_R_PRICETYPE;



delete DBVERSION where PATCHVERSION = 'MM1.0.3.095_SSMS' and LASTDBVERSION = 'MM1.0.3.090_SSMS';
commit;