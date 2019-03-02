-- Create table
create table T_GAME_TW_SORT
(
  gameId VARCHAR2(16) not null,
  SORTID VARCHAR2(8) not null
)
;
-- Add comments to the columns 
comment on column T_GAME_TW_SORT.gameId
  is '产品的业务代码';
comment on column T_GAME_TW_SORT.sortid
  is '排序序号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_TW_SORT
  add constraint pk_T_GAME_TW_SORTID primary key (GAMEID);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (1, 'select t.goodsid,t.icpcode,t.icpservid,t.contentid,t.categoryid,t.goodsname,t.state,t.changedate,t.actiontype,t.laststate,'''' from T_GOODS_HIS t order by t.id', '商品历史全量数据', '1', 50000, ',', null, 11, 'i_GDS-HIS_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (2, 'select t.id,t.parentid,t.path,t.type from T_R_BASE t order by t.id', '基本信息数据', '1', 50000, ',', null, 4, 'i_RSC-BASE_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (12, 'select v.CONTENTID,v.PACKAGE,v.VERSION from V_CM_CONTENT_PACKAGE v order by v.CONTENTID', '应用PACKAGE信息数据', '1', 50000, ',', null, 3, 'i_CPG_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (13, 'select v.CONTENTID,v.PRODUCTNAME,v.FEE,v.CHARGETYPE,v.TBTYPE from V_CM_CONTENT_TB v order by v.CONTENTID', '' || chr(9) || '应用内计费信息数据', '1', 50000, ',', null, 5, 'i_CTB_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (8, 'select t.keyid,t.keyname,t.keytable,t.keydesc,t.keytype,t.keyremark,t.keyisrequired,t.keylength from t_key_base t order by t.keyid', '扩展基础数据', '1', 50000, ',', null, 8, 'i_KEY-BASE_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (9, 'select r.tid,r.keyid,r.value,r.lupdate from T_KEY_RESOURCE r order by r.tid', '扩展资源数据', '1', 50000, ',', null, 4, 'i_KEY-RSC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (11, 'select t.device_id,t.device_name,t.device_desc,t.os_id,t.os_detail,t.brand_id,b.brand_name,u.device_ua,u.device_os_ua,u.device_ua_header from T_device t,t_device_ua u,t_device_brand b where u.device_id = t.device_id and b.brand_id=t.brand_id order by t.device_id', '机型信息数据', '1', 50000, ',', null, 10, 'i_DVC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (14, 'select t.goodsid, t.icpcode, t.icpservid, t.contentid, t.categoryid, t.goodsname, t.state, t.changedate, t.actiontype, t.laststate, ''A'' from T_GOODS_HIS t where t.changedate >(select e.lasttime from t_r_exportsql e where e.id = ''14'') order by t.id', '商品历史增量数据', '1', 50000, ',', null, 11, 'a_GDS-HIS_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);



---商品库优化开始
create sequence SEQ_T_A_MESSAGES
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle; 

create table T_A_MESSAGES
(
  ID            NUMBER(10) not null,
  TYPE          VARCHAR2(50),
  MESSAGE       VARCHAR2(4000),
  STATUS        NUMBER(1),
  CREATEDATE    DATE default sysdate,
  LUPDATE       DATE,
  TRANSACTIONID VARCHAR2(15),
  DESTINATION   VARCHAR2(5),
  TRYTIME       NUMBER(1),
  primary key(id)
);


-- Add comments to the columns 
comment on column T_A_MESSAGES.ID
  is '自然键';
comment on column T_A_MESSAGES.TYPE
  is '消息类型（应用更新：ContentModifyReq，货架更新：CatogoryModifyReq，上下架：RefModifyReq，榜单变更：CountUpdateReq，事务提交：CommitReq）';
comment on column T_A_MESSAGES.MESSAGE
  is '消息体';
comment on column T_A_MESSAGES.STATUS
  is '-1：未发送，0：已经发送';
comment on column T_A_MESSAGES.CREATEDATE
  is '创建时间';
comment on column T_A_MESSAGES.LUPDATE
  is '更新时间';
comment on column T_A_MESSAGES.TRANSACTIONID
  is '事务号码';
comment on column T_A_MESSAGES.DESTINATION
  is '未使用';
comment on column T_A_MESSAGES.TRYTIME
  is '未使用';

create table T_A_PPMS_RECEIVE
(
  TRANSACTIONID VARCHAR2(15) not null,
  TYPE          VARCHAR2(50),
  MESSAGE       VARCHAR2(4000),
  STATUS        NUMBER(1),
  CREATEDATE    DATE default sysdate,
  LUPDATE       DATE,
  primary key(TransactionID)
);
-- Add comments to the columns 
comment on column T_A_PPMS_RECEIVE.TRANSACTIONID
  is '发起方交易流水号；在发起方唯一标识一个交易的流水号，系统内唯一';
comment on column T_A_PPMS_RECEIVE.TYPE
  is '1：内容变更，2：适配关系变更，3：内容和适配都变更';
comment on column T_A_PPMS_RECEIVE.MESSAGE
  is '内容ID用逗号分隔形式或者适配ID用逗号分隔形式';
comment on column T_A_PPMS_RECEIVE.STATUS
  is '-2：处理不成功，-1：未处理，0：已经处理';
comment on column T_A_PPMS_RECEIVE.CREATEDATE
  is '入库时间';
comment on column T_A_PPMS_RECEIVE.LUPDATE
  is '更新时间';
  
  
create sequence SEQ_T_A_PPMS_RECEIVE_CHANGE
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;

create table T_A_PPMS_RECEIVE_CHANGE
(
  ID            NUMBER(10) not null,
  TYPE          VARCHAR2(1),
  ENTITYID      VARCHAR2(50),
  TRANSACTIONID VARCHAR2(15),
  STATUS        NUMBER(1),
  CREATEDATE    DATE default sysdate,
  LUPDATE       DATE,
  primary key(id)
);
-- Add comments to the columns 
comment on column T_A_PPMS_RECEIVE_CHANGE.ID
  is '自然主键ID';
comment on column T_A_PPMS_RECEIVE_CHANGE.TYPE
  is '1：内容变更，2：适配关系变更，3：内容和适配都变更';
comment on column T_A_PPMS_RECEIVE_CHANGE.ENTITYID
  is '内容ID或适配关系ID';
comment on column T_A_PPMS_RECEIVE_CHANGE.TRANSACTIONID
  is '事务ID';
comment on column T_A_PPMS_RECEIVE_CHANGE.STATUS
  is '-2：处理失败，-1：未处理，0：已经处理';
comment on column T_A_PPMS_RECEIVE_CHANGE.CREATEDATE
  is '入库时间';
comment on column T_A_PPMS_RECEIVE_CHANGE.LUPDATE
  is '更新时间';
    
create table T_A_PUSH
(
  ID        NUMBER(10) not null,
  PUSHID    VARCHAR2(20),
  CONTENTID VARCHAR2(12),
  UA        VARCHAR2(50),
  LUPDATE   DATE default sysdate,
  FILENAME  VARCHAR2(50),
  primary key(id)
);
-- Add comments to the columns 
comment on column T_A_PUSH.ID
  is '自然主键ID';
comment on column T_A_PUSH.PUSHID
  is 'pushID';
comment on column T_A_PUSH.CONTENTID
  is '应用内容ID';
comment on column T_A_PUSH.UA
  is 'UA';
comment on column T_A_PUSH.LUPDATE
  is '入库时间';
comment on column T_A_PUSH.FILENAME
  is '文件名';    
  
  
create table T_A_PUSHREPORT
(
  ID            NUMBER(10) not null,
  PUSHID        VARCHAR2(20),
  STATUS        NUMBER(10),
  RECORD_TIME   VARCHAR2(14),
  LUPDATE       DATE default sysdate,
  FILENAME      VARCHAR2(50),
  HANDLE_STATUS NUMBER(1) default -1
);
-- Add comments to the columns 
comment on column T_A_PUSHREPORT.ID
  is '主键ID';
comment on column T_A_PUSHREPORT.PUSHID
  is 'pushid';
comment on column T_A_PUSHREPORT.STATUS
  is '状态，0：有效数据，1：无效数据';
comment on column T_A_PUSHREPORT.RECORD_TIME
  is '记录时间';
comment on column T_A_PUSHREPORT.LUPDATE
  is '入库时间';
comment on column T_A_PUSHREPORT.FILENAME
  is '处理的文件名称';
comment on column T_A_PUSHREPORT.HANDLE_STATUS
  is '-1：未处理，0：已经处理';  
  
create table T_A_CONTENT_DOWNCOUNT
(
  CONTENTID VARCHAR2(12),
  DOWNCOUNT NUMBER(10),
  LUPDATE   DATE default sysdate
);
-- Add comments to the columns 
comment on column T_A_CONTENT_DOWNCOUNT.CONTENTID
  is '应用内容ID';
comment on column T_A_CONTENT_DOWNCOUNT.DOWNCOUNT
  is '今天下载量';
comment on column T_A_CONTENT_DOWNCOUNT.LUPDATE
  is '入库时间';  

create table T_A_CM_DEVICE_RESOURCE
(
  PID           VARCHAR2(12),
  DEVICE_ID     NUMBER(8),
  DEVICE_NAME   VARCHAR2(100),
  CONTENTID     VARCHAR2(12),
  CONTENTNAME   VARCHAR2(60),
  RESOURCEID    VARCHAR2(10),
  ID            VARCHAR2(12),
  ABSOLUTEPATH  VARCHAR2(256),
  URL           VARCHAR2(256),
  PROGRAMSIZE   NUMBER(8),
  CREATEDATE    DATE,
  PROSUBMITDATE DATE,
  MATCH         VARCHAR2(40),
  VERSION       VARCHAR2(100),
  PERMISSION    VARCHAR2(4000),
  ISCDN         NUMBER(1)
);
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.PID
  is '程序包ID';
comment on column T_A_CM_DEVICE_RESOURCE.DEVICE_ID
  is '机型ID';
comment on column T_A_CM_DEVICE_RESOURCE.DEVICE_NAME
  is '机型名称';
comment on column T_A_CM_DEVICE_RESOURCE.CONTENTID
  is '应用ID';
comment on column T_A_CM_DEVICE_RESOURCE.CONTENTNAME
  is '应用名称';
comment on column T_A_CM_DEVICE_RESOURCE.RESOURCEID
  is '资源ID';
comment on column T_A_CM_DEVICE_RESOURCE.ID
  is '适配ID';
comment on column T_A_CM_DEVICE_RESOURCE.ABSOLUTEPATH
  is '资源绝对路径';
comment on column T_A_CM_DEVICE_RESOURCE.URL
  is '资源URL地址';
comment on column T_A_CM_DEVICE_RESOURCE.PROGRAMSIZE
  is '程序包大小';
comment on column T_A_CM_DEVICE_RESOURCE.CREATEDATE
  is '创建时间';
comment on column T_A_CM_DEVICE_RESOURCE.PROSUBMITDATE
  is '程序包提交时间';
comment on column T_A_CM_DEVICE_RESOURCE.MATCH
  is '适配程度';
comment on column T_A_CM_DEVICE_RESOURCE.VERSION
  is '版本信息';
comment on column T_A_CM_DEVICE_RESOURCE.PERMISSION
  is '权限信息';
comment on column T_A_CM_DEVICE_RESOURCE.ISCDN
  is '是否CDN';



create or replace synonym V_DATACENTER_CM_CONTENT
  for V_DATACENTER_CM_CONTENT@DL_PPMS_DEVICE;

--ppms_a_cm_device_resource  
create or replace synonym V_DC_CM_DEVICE_RESOURCE
  for V_DC_CM_DEVICE_RESOURCE@DL_PPMS_DEVICE;  
  
  
  
create or replace view v_android_list as 
   select g.contentid,
   
   decode(g.catename,'软件','appSoftWare','游戏','appGame','主题','appTheme','') as catename,
   
   
   g.name,a.ADD_7DAYS_DOWN_COUNT,a.add_order_count,v.mobileprice,
decode(v.mobileprice, 0, 0, 10) as mobileprice_alias,
trunc(l.createtime) as createtime_trunc,
decode(catename, '软件', 1,  '主题', 2, 10) as catename_alias,
to_char(l.createtime, 'hh24miss') as createtime_tochar,

 

c.scores,l.createtime
  from t_r_base             b,
       t_r_gcontent         g,
       v_service            v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c
       
 where l.contentid = g.contentid
   and b.id = g.id
   and b.type like 'nt:gcontent:app%'
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and g.subtype in ('1', '2', '3', '4', '5', '7', '8', '9', '10')
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9;
   
   
create table  t_a_android_list as select * from  v_android_list;


--这个地方在以前的试图上加了t.add_order_count字段
create or replace view t_r_servenday_temp_a as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT, t.add_order_count from
t_r_down_sort_old t where t.os_id=9

    
  



---商品库优化结束


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.159_SSMS','MM2.0.0.0.045_SSMS');


commit;