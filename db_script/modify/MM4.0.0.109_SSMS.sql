
----------------------------------------------在业务产品表T_V_PROPKG添加一个字段  开始------------------------------------------------------------------
alter  table T_V_PROPKG add propkg_parentid VARCHAR2(21);
comment on column T_V_PROPKG.propkg_parentid
  is '父产品包ID';
----------------------------------------------在业务产品表T_V_PROPKG添加一个字段  开始------------------------------------------------------------------
  
  
----------------------------------------------创建榜单发布T_V_LIST_PUBLISH 开始-------------------------------------------------------------------
create table T_V_LIST_PUBLISH
(
  platform    VARCHAR2(10),
  bankname    VARCHAR2(50) not null,
  sortid      VARCHAR2(20) not null,
  programid   VARCHAR2(21) not null,
  programname VARCHAR2(100),
  vauthor     VARCHAR2(50),
  detail      VARCHAR2(1024),
  wwwurl      VARCHAR2(100),
  wapurl      VARCHAR2(100),
  isfinish    VARCHAR2(20),
  updatetime  DATE default sysdate not null
);
-- Add comments to the table 
comment on table T_V_LIST_PUBLISH
  is '榜单发布';
-- Add comments to the columns 
comment on column T_V_LIST_PUBLISH.platform
  is 'VGOP管理的增值业务平台编码';
comment on column T_V_LIST_PUBLISH.bankname
  is '视频基地榜单的名称';
comment on column T_V_LIST_PUBLISH.sortid
  is '业务在视频基地榜单的排名';
comment on column T_V_LIST_PUBLISH.programid
  is '数据请求发起平台自己的产品信息编码';
comment on column T_V_LIST_PUBLISH.programname
  is '产品名称';
comment on column T_V_LIST_PUBLISH.vauthor
  is '产品内容的原创者、作者或表演者或内容提供商';
comment on column T_V_LIST_PUBLISH.detail
  is '对产品的简要说明';
comment on column T_V_LIST_PUBLISH.wwwurl
  is '获取产品的访问路径，如：URL地址或短信指令';
comment on column T_V_LIST_PUBLISH.wapurl
  is '获取产品的访问路径，如：URL地址或短信指令';
comment on column T_V_LIST_PUBLISH.isfinish
  is '完成情况';
comment on column T_V_LIST_PUBLISH.updatetime
  is '同步更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LIST_PUBLISH
  add constraint PK_T_V_LIST_PUBLISH_PID primary key (PROGRAMID);
----------------------------------------------创建榜单发布T_V_LIST_PUBLISH 结束-------------------------------------------------------------------

----------------------------------------------创建视频热点内容表T_V_HOTCONTENT_PROGRAM 开始---------------------------------------------------------
-- Create table
create table T_V_HOTCONTENT_PROGRAM
(
  id          VARCHAR2(32) not null,
  prdcontid   NUMBER(19),
  contentid   NUMBER(19),
  nodeid      NUMBER(19),
  contenttype NUMBER(19),
  title       VARCHAR2(2000),
  shorttitle  VARCHAR2(2000),
  description VARCHAR2(2000),
  image       VARCHAR2(2000),
  lupdate     DATE,
  location    VARCHAR2(20)
);
-- Add comments to the table 
comment on table T_V_HOTCONTENT_PROGRAM
  is '视频热点内容表';
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_PROGRAM.id
  is '主键ID';
comment on column T_V_HOTCONTENT_PROGRAM.prdcontid
  is '节目ID';
comment on column T_V_HOTCONTENT_PROGRAM.contentid
  is '内容ID';
comment on column T_V_HOTCONTENT_PROGRAM.nodeid
  is '专题栏目ID';
comment on column T_V_HOTCONTENT_PROGRAM.contenttype
  is '内容类型 枚举值：1-剧集、2-非剧集、3-直播，4-专题';
comment on column T_V_HOTCONTENT_PROGRAM.title
  is '内容长标题';
comment on column T_V_HOTCONTENT_PROGRAM.shorttitle
  is '内容短标题';
comment on column T_V_HOTCONTENT_PROGRAM.description
  is '内容描述';
comment on column T_V_HOTCONTENT_PROGRAM.image
  is '内容图片名称';
comment on column T_V_HOTCONTENT_PROGRAM.lupdate
  is '同步最后更新时间';
comment on column T_V_HOTCONTENT_PROGRAM.location
  is '位置信息';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_HOTCONTENT_PROGRAM
  add constraint PK_TVHOTCONTENTPRO_ID primary key (ID);
----------------------------------------------创建视频热点内容表T_V_HOTCONTENT_PROGRAM 结束---------------------------------------------------------

----------------------------------------------创建视频热点内容序列号SEQ_T_V_HOTCONTENTPRO_ID 开始-----------------------------------------------------
create sequence SEQ_T_V_HOTCONTENTPRO_ID
minvalue 1
maxvalue 999999999999
start with 10
increment by 1
nocache
cycle;
----------------------------------------------创建视频热点内容序列号SEQ_T_V_HOTCONTENTPRO_ID 开始-----------------------------------------------------

----------------------------------------------在T_V_LIVE中添加三个字段 开始--------------------------------------------------------------------------
alter table T_V_LIVE add ranking  VARCHAR2(21);
comment on column T_V_LIVE.ranking
  is '序号';
alter table T_V_LIVE add PlayShellID varchar2(21);
alter table T_V_LIVE add PlayVodID varchar2(21);
comment on column T_V_LIVE.PlayShellID
  is '节目对应剧集ID';
comment on column T_V_LIVE.PlayVodID
  is '节目对应点播内容ID';
----------------------------------------------在T_V_LIVE中添加三个字段  结束--------------------------------------------------------------------------

  ----------------------------------------------在t_v_dprogram中添加5个字段 开始---------------------------------------------------------------------
alter table t_v_dprogram add authorizationWay NUMBER(2);
alter table t_v_dprogram add miguPublish NUMBER(2);
alter table t_v_dprogram add bcLicense NUMBER(2);
alter table t_v_dprogram add Influence NUMBER(2);
alter table t_v_dprogram add oriPublish NUMBER(2);

comment on column t_v_dprogram.authorizationWay is '授权方式 枚举值：1-单片授权；2-集体授权';
comment on column t_v_dprogram.miguPublish is '咪咕发行 枚举值：1-非独家非首发；2-独家或首发';
comment on column t_v_dprogram.bcLicense is '播出许可 枚举值：1-非院线非电视台；2-院线或电视台';
comment on column t_v_dprogram.Influence is '受众影响 枚举值：1-非热播；2-热播';
comment on column t_v_dprogram.oriPublish is '原创发行 枚举值：1-工作室直签；2-代理发行3-非原创发行';
----------------------------------------------在t_v_dprogram中添加5个字段  结束----------------------------------------------------------------------

----------------------------------------------新建视频内容申报位置表T_V_HOTCONTENT_LOCATION  开始-----------------------------------------------------
create table T_V_HOTCONTENT_LOCATION
(
  locationid VARCHAR2(20)
);
-- Add comments to the table 
comment on table T_V_HOTCONTENT_LOCATION
  is '视频内容申报位置表';
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_LOCATION.locationid
  is '申报位置ID';

insert into t_v_hotcontent_location (locationId)values('10169');
insert into t_v_hotcontent_location (locationId)values('10170');
insert into t_v_hotcontent_location (locationId)values('10172');
insert into t_v_hotcontent_location (locationId)values('10173');
insert into t_v_hotcontent_location (locationId)values('10174');
insert into t_v_hotcontent_location (locationId)values('10175');
insert into t_v_hotcontent_location (locationId)values('10163');
insert into t_v_hotcontent_location (locationId)values('10168');
insert into t_v_hotcontent_location (locationId)values('10161');
insert into t_v_hotcontent_location (locationId)values('10105');
insert into t_v_hotcontent_location (locationId)values('10108');
insert into t_v_hotcontent_location (locationId)values('10162');
insert into t_v_hotcontent_location (locationId)values('10164');
insert into t_v_hotcontent_location (locationId)values('10165');
insert into t_v_hotcontent_location (locationId)values('10166');
insert into t_v_hotcontent_location (locationId)values('10171');
insert into t_v_hotcontent_location (locationId)values('10106');
insert into t_v_hotcontent_location (locationId)values('10103');
insert into t_v_hotcontent_location (locationId)values('10104');
insert into t_v_hotcontent_location (locationId)values('10107');
insert into t_v_hotcontent_location (locationId)values('10109');
insert into t_v_hotcontent_location (locationId)values('10110');
insert into t_v_hotcontent_location (locationId)values('10111');
insert into t_v_hotcontent_location (locationId)values('10113');
insert into t_v_hotcontent_location (locationId)values('10181');
insert into t_v_hotcontent_location (locationId)values('10176');
insert into t_v_hotcontent_location (locationId)values('10101');
insert into t_v_hotcontent_location (locationId)values('10102');
insert into t_v_hotcontent_location (locationId)values('10112');

----------------------------------------------新建视频内容申报位置表T_V_HOTCONTENT_LOCATION  结束-----------------------------------------------------

----------------------------------------------在t_v_dprogram中更改4个字段类型  开始-------------------------------------------------------------------
alter table t_v_dprogram add subserial_ids_1 Clob;
update t_v_dprogram set subserial_ids_1 = subserial_ids;
alter table t_v_dprogram drop column subserial_ids;
alter table t_v_dprogram rename column subserial_ids_1 to subserial_ids;

alter table t_v_dprogram modify Area VARCHAR2(50);
alter table t_v_dprogram modify Terminal VARCHAR2(50);
alter table t_v_dprogram modify SerialCount number(10);

----------------------------------------------在t_v_dprogram中更改4个字段类型  结束--------------------------------------------------------------------

----------------------------------------------在t_v_PRODUCT中更改1个字段类型  开始-------------------------------------------------------------------
alter table t_v_PRODUCT modify fee VARCHAR2(10);
----------------------------------------------在t_v_PRODUCT中更改1个字段类型  开始-------------------------------------------------------------------
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.099_SSMS','MM4.0.0.0.109_SSMS');

------ 新增导出任务给报表系统 ---------
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (83, 'SELECT CHANNELSID,CHANNELID FROM T_OPEN_CHANNEL_MO', '	渠道商与客户端渠道信息同步', '2', 0, '0x1F', null, 2, 'i-channels-mo_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (84, 'SELECT CHANNELSID,CHANNELID FROM T_OPEN_OPERATION_CHANNEL', '渠道商与开发运营渠道信息同步', '2', 0, '0x1F', null, 2, 'i-channels-operation_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (85, 'SELECT CHANNELSID,CATEGORYID FROM T_OPEN_CHANNELS_CATEGORY', '渠道商与货架信息同步', '2', 0, '0x1F', null, 2, 'i-channels-category_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (86, 'SELECT A.CHANNELSID, D.CONTENTID FROM T_OPEN_CHANNELS_CATEGORY A,T_R_CATEGORY B,T_R_REFERENCE C,T_R_GCONTENT D WHERE A.CATEGORYID = B.CATEGORYID AND B.CATEGORYID = C.CATEGORYID AND C.REFNODEID = D.ID AND C.VERIFY_STATUS=1 ', '渠道商与商品信息同步接口', '2', 0, '0x1F', null, 2, 'i-channels-content_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

----------------------
 
commit;
