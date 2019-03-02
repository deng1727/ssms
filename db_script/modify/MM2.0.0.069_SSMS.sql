-- Create table
create table T_RB_BOOKBAG_AREA_NEW
(
  BAGID       VARCHAR2(20) not null,
  BAGNAME     VARCHAR2(64) not null,
  VIEWBAGNAME VARCHAR2(64) not null,
  PROVINCE    VARCHAR2(256) not null,
  CITY        VARCHAR2(4000) not null,
  STATUS      VARCHAR2(5) default 0 not null,
  LUPDATE     DATE default SYSDATE
);
-- Add comments to the columns 
comment on column T_RB_BOOKBAG_AREA_NEW.BAGID
  is '书包标识';
comment on column T_RB_BOOKBAG_AREA_NEW.BAGNAME
  is '书包名称';
comment on column T_RB_BOOKBAG_AREA_NEW.VIEWBAGNAME
  is '书包展示名称';
comment on column T_RB_BOOKBAG_AREA_NEW.PROVINCE
  is '销售区域省份';
comment on column T_RB_BOOKBAG_AREA_NEW.CITY
  is '销售区域地市';
comment on column T_RB_BOOKBAG_AREA_NEW.STATUS
  is '当前数据状态';
comment on column T_RB_BOOKBAG_AREA_NEW.LUPDATE
  is '数据导入时间';



-- Add/modify columns 
alter table T_RB_REFERENCE_NEW add PROVINCE varchar2(256);
alter table T_RB_REFERENCE_NEW add CITY varchar2(4000);
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.PROVINCE
  is '所有的省份ID以分号;隔开，000代表全国';
comment on column T_RB_REFERENCE_NEW.CITY
  is '所有的城市ID以分号;隔开';

alter table T_RB_REFERENCE_NEW modify PROVINCE default -1;
alter table T_RB_REFERENCE_NEW modify CITY default -1;


-- Create table
create table T_RB_AREA_NEW
(
  PROVINCEID   VARCHAR2(16) not null,
  PROVINCENAME VARCHAR2(20) not null,
  CITYID       VARCHAR2(16) not null,
  CITYNAME     VARCHAR2(20) not null,
  MMPROVINCEID VARCHAR2(16) not null,
  MMCITYID     VARCHAR2(16) not null
);
-- Add comments to the columns 
comment on column T_RB_AREA_NEW.PROVINCEID
  is '省份id';
comment on column T_RB_AREA_NEW.PROVINCENAME
  is '省份名称';
comment on column T_RB_AREA_NEW.CITYID
  is '城市id';
comment on column T_RB_AREA_NEW.CITYNAME
  is '城市名称';
comment on column T_RB_AREA_NEW.MMPROVINCEID
  is 'MM省份id';
comment on column T_RB_AREA_NEW.MMCITYID
  is 'MM城市id';

-- Create table
create table T_R_HTCDOWNLOAD
(
  APCODE    VARCHAR2(6) not null,
  APPID     VARCHAR2(20) not null,
  CONTENTID VARCHAR2(30) not null,
  DOWNCOUNT NUMBER(10) not null,
  LUPDATE   DATE default sysdate
);
-- Add comments to the columns 
comment on column T_R_HTCDOWNLOAD.APCODE
  is '企业代码';
comment on column T_R_HTCDOWNLOAD.APPID
  is '业务平台应用编码';
comment on column T_R_HTCDOWNLOAD.CONTENTID
  is 'MM应用代码';
comment on column T_R_HTCDOWNLOAD.DOWNCOUNT
  is '下载次数';
comment on column T_R_HTCDOWNLOAD.LUPDATE
  is '最后修改时间';




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.065_SSMS','MM2.0.0.0.069_SSMS');

commit;