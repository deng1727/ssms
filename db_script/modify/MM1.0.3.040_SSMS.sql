-- Add/modify columns 
alter table T_R_CATEGORY add DEVICECATEGORY NUMBER(2) default 0;
-- Add comments to the columns 
comment on column T_R_CATEGORY.DEVICECATEGORY
  is '是否是机型货架（0：非，1：是）';


-- Create table
create table T_R_DEVICE_CATEGORY
(
  CATEGORYID VARCHAR2(20) not null,
  DEVICEID   NUMBER(8) not null,
  DEVICENAME VARCHAR2(100) not null,
  CREATETIME DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_R_DEVICE_CATEGORY.CATEGORYID
  is '货架id';
comment on column T_R_DEVICE_CATEGORY.DEVICEID
  is '机型id';
comment on column T_R_DEVICE_CATEGORY.DEVICENAME
  is '机型名称';
comment on column T_R_DEVICE_CATEGORY.CREATETIME
  is '新增时间';

-- Add/modify columns 
alter table T_SYNC_TACTIC add TACtYPE number default 0;
-- Add comments to the columns 
comment on column T_SYNC_TACTIC.TACtYPE
  is '策略类型，基础分类策略：1，普通机型分类策略，0';
----将已有的货架同步策略更新为基础分类策略类型
update t_sync_tactic t set t.tactype=1;




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.035_SSMS','MM1.0.3.040_SSMS');
commit;