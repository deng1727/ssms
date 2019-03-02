-----创建seq_t_free_dl_report_id自动生成序列
create sequence seq_t_free_dl_report_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-----创建seq_t_free_dl_order_id自动生成序列
create sequence seq_t_free_dl_order_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;

-----创建表免费应用订购记录表 t_free_dl_order
create table t_free_dl_order
(
id  varchar2(60),
ordernumber  varchar2(20),
destuserid  varchar2(32),
feeuserid  varchar2(32),
contentid  varchar2(20),
packagecode  varchar2(12),
channelid  varchar2(64),
subsplace  varchar2(20),
ua  varchar2(200),
spcode  varchar2(10),
servicecode  varchar2(20),
productcode  varchar2(40),
orderstarttime  varchar2(14),
downloadtime  varchar2(14),
accessmodeid  varchar2(2),
payway  number(11),
discountrate  number(11),
price  number(11),
fee  number(11),
actiontype  number(11),
downchannelid varchar2(20),
appname  varchar2(100),
usertype  number(11),
ondemandtype  number(11),
apn  varchar2(32),
apnaddr  varchar2(32),
useraccesstype  varchar2(24),
expiretime   varchar2(14),
orderflag  number(11),
acessplat  varchar2(20),
interchannel  varchar2(50),
touchversion  number(11),
browser  varchar2(20),
spm  varchar2(64),
mmsource  varchar2(64),
mminstallid  varchar2(64),
mmvisitorid  varchar2(200),
mmdeviceinfo  varchar2(80),
referrer  varchar2(1024),
fromarea  varchar2(200),
lupdate  varchar2(14),
flag  varchar2(20)
);
alter table t_free_dl_order
  add constraint t_free_dl_order_pk primary key (id);
-- add comments to the table 
comment on table t_free_dl_order
  is '免费应用订购记录表';
  
 
-----创建表免费应用下载回执日志表 t_free_dl_report
create table t_free_dl_report
(
  id             varchar2(60),
  sequenceid     varchar2(10),
  pushid         varchar2(20),
  status         varchar2(10),
  outputtime     varchar2(14),
  params         varchar2(14),
  useraccesstype varchar2(20),
  lupdatetime    varchar2(14),
  flag           varchar2(20)
);
alter table t_free_dl_report
  add constraint t_free_dl_report primary key (id);
-- add comments to the table 
comment on table t_free_dl_report
  is '下载回执日志表';
-- add comments to the columns 
comment on column t_free_dl_report.id
  is 'id标识（序号生成）';
comment on column t_free_dl_report.sequenceid
  is '日志记录唯一序号';
comment on column t_free_dl_report.pushid
  is '应用下载推送标识';
comment on column t_free_dl_report.status
  is '应用下载返回码0：下载成功其它：下载错误返回码';
comment on column t_free_dl_report.outputtime
  is '日志记录输出时间,格式为”yyyymmddhh24miss”';
comment on column t_free_dl_report.params
  is '点播附加参数(支持多个参数,参数间以竖线分割)；内容格式示例：param1=value| param2=value2|…当前支持的参数名：softplat、acesstype、appname，clienttype、touchversion、browser固定填：softplat=mm';
comment on column t_free_dl_report.useraccesstype
  is '可为空，取值：cmmm cmnet cmwap wifi wlan字符串；填空';
comment on column t_free_dl_report.lupdatetime
  is '入库时间';
comment on column t_free_dl_report.flag
  is '处理标识（默认-1,处理完成设置状态为0）';




-- 新音乐增加字段
alter table T_MB_MUSIC_NEW add dolbytype VARCHAR2(10);
-- Add comments to the columns 
comment on column T_MB_MUSIC_NEW.dolbytype
  is '是否杜比音效,0：不支持； 1：支持';
  




-- 视频增量同步脚本
create table t_vo_video_mid as select v.*,'A' as status from t_vo_video v where 1=2;
-- Create/Recreate indexes 
create index T_IDX_VO_VIDEOMid on T_VO_VIDEO_MID (videoid, coderateid);
-- Add/modify columns 
alter table T_VO_VIDEO_MID modify UPDATETIME default sysdate;


create table t_vo_live_mid as select v.*,'A' as status from t_vo_live v where 1=2;


create table t_vo_node_mid as select v.*,'A' as status from t_vo_node v where 1=2;
-- Create/Recreate indexes 
create index T_IDX_VO_NODEMid on T_VO_NODE_MID (nodeid);


create table t_vo_product_mid as select v.*,'A' as status from t_vo_product v where 1=2;


create table t_vo_program_mid as select v.*,'A' as status from t_vo_program v where 1=2;
-- Create/Recreate indexes 
create index T_IDX_VO_PROGRAMMid on T_VO_PROGRAM_MID (programid);


create table T_VO_VIDEODETAIL_MID as select v.*,'A' as status from T_VO_VIDEODETAIL v where 1=2;
-- Create/Recreate indexes 
create index T_IDX_VO_VIDEODETAILMid on T_VO_VIDEODETAIL_MID (programid);
-- Add/modify columns 
alter table T_VO_VIDEODETAIL_MID modify UPDATETIME default sysdate;






create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',
                                        '增量同步视屏相关数据');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.nodeid=b.nodeid and a.programid=b.programid
and a.starttime=b.starttime);
insert into T_VO_LIVE (NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME)
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME from T_VO_LIVE_MID b where status in ('A','U');


delete T_VO_NODE c
where exists(select 1 from T_VO_NODE_MID d where c.nodeid=d.nodeid);
insert into T_VO_NODE(NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME)
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME from T_VO_NODE_MID
 where status in ('A','U');

delete T_VO_PRODUCT e
where exists(select 1 from t_Vo_Product_Mid f where e.productid=f.productid );
insert into T_VO_PRODUCT(PRODUCTID, PRODUCTNAME, FEE, CPID, FEETYPE, STARTDATE, FEEDESC, FREETYPE, FREEEFFECTIME, FREETIMEFAIL)
select PRODUCTID, PRODUCTNAME, FEE, CPID, FEETYPE, STARTDATE, FEEDESC, FREETYPE, FREEEFFECTIME, FREETIMEFAIL from t_Vo_Product_Mid
 where status in ('A','U');


delete T_VO_PROGRAM g
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid);
insert into T_VO_PROGRAM(PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH)
select PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH from T_VO_PROGRAM_MID where status in ('A','U');

delete T_VO_VIDEO i
where exists(select 1 from T_VO_VIDEO_MID j where i.videoid=j.videoid and i.coderateid=j.coderateid);
insert into T_VO_VIDEO(VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME)
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME from T_VO_VIDEO_MID  where status in ('A','U');

delete  T_VO_VIDEODETAIL k
where exists(select 1 from T_VO_VIDEODETAIL_MID l where k.programid=l.programid);
insert into T_VO_VIDEODETAIL(PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME)
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME from T_VO_VIDEODETAIL_MID
 where status in ('A','U');
 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;


-----------------开发者ID同步接口脚本开始------------------------------------

-----在PPMS数据库用户下授权给货架
--- 同一个实例下需要授权 现网为DBLINK 不需要grant select on om_developer_company to &ssms;

---创建同义词ppms_om_developer_company
-- Create the synonym 

create or replace synonym ppms_om_developer_company
  for om_developer_company@dl_ppms_device;

-- create table
create table t_om_developer_company
(
  developerid   varchar2(50),
  developername varchar2(50),
  developertype number(1),
  companyid     varchar2(6),
  isauthorized  number(1) default 0 not null
);
create unique index idx_t_om_companyid on t_om_developer_company (companyid);
create unique index idx_t_om_developerid on t_om_developer_company (developerid);

-- create table
create table t_om_developer_company_tra
(
  developerid   varchar2(50),
  developername varchar2(50),
  developertype number(1),
  companyid     varchar2(6),
  isauthorized  number(1) default 0 not null
);
create unique index idx_t_om_tra_companyid on t_om_developer_company_tra (companyid);
create unique index idx_t_om_tra_developerid on t_om_developer_company_tra (developerid);


--t_r_exportsql_por表添加 开发者ID同步代码
INSERT INTO T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
VALUES (7, '开发者ID同步接口', 'PPMS_OM_DEVELOPER_COMPANY', 'T_OM_DEVELOPER_COMPANY', 'T_OM_DEVELOPER_COMPANY_TRA', 'T_OM_DEVELOPER_COMPANY_BAK');

--t_r_exportsql表添加 开发者id同步代码
INSERT INTO T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
VALUES (30, 'SELECT DEVELOPERID,COMPANYID FROM T_OM_DEVELOPER_COMPANY', '开发者ID同步数据', '1', 50000, ',', TO_DATE('11-11-2013 15:04:00', 'DD-MM-YYYY HH24:MI:SS'), 2, 'i_VDC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', '3023', 1);

-----------------开发者ID同步接口脚本结束------------------------------------



-----------------飞信同步导出表脚本开始------------------------------------
-----在PPMS数据库用户下授权给货架
--- 同一个实例下需要授权 现网为DBLINK 不需要grant select on cm_feixin to &ssms;

---创建同义词ppms_om_developer_company
-- Create the synonym 

create or replace synonym ppms_cm_feixin
  for cm_feixin@dl_ppms_device;
  
--飞信表
create table cm_feixin 
(
 appId varchar2(12),
 developerId varchar2(20),
 account varchar2(100),
 password varchar2(100),
 apptype number(1),
 nickname varchar2(100),
 onewordDesc varchar2(1000),
 icon varchar2(300),
 portalUrl varchar2(300),
 status number(1),
 createdate date default sysdate,
 lupddate date default sysdate
);
comment on table cm_feixin is '创业大赛飞信参赛作品表';
comment on column cm_feixin.appId is '飞信参赛ID,采用contentid的seq生成';
comment on column cm_feixin.developerId is '参赛者ID';
comment on column cm_feixin.account is '飞信公众账号';
comment on column cm_feixin.password is '账号密码，加密存储';
comment on column cm_feixin.apptype is '参赛作品类型.1:娱乐型,  2:服务型,  3:订阅型,  4:创意型';
comment on column cm_feixin.nickname is '昵称';
comment on column cm_feixin.onewordDesc is '一句话简介';
comment on column cm_feixin.icon is '头像';
comment on column cm_feixin.portalUrl is '公众门户地址';
comment on column cm_feixin.status is '状态。0：暂存， 1：参赛成功';
comment on column cm_feixin.createdate is '创建时间';
comment on column cm_feixin.lupddate is '最后更新时间';


create table cm_feixin_tra 
(
 appId varchar2(12),
 developerId varchar2(20),
 account varchar2(100),
 password varchar2(100),
 apptype number(1),
 nickname varchar2(100),
 onewordDesc varchar2(1000),
 icon varchar2(300),
 portalUrl varchar2(300),
 status number(1),
 createdate date default sysdate,
 lupddate date default sysdate
);
comment on table cm_feixin_tra is '创业大赛飞信参赛作品表';
comment on column cm_feixin_tra.appId is '飞信参赛ID,采用contentid的seq生成';
comment on column cm_feixin_tra.developerId is '参赛者ID';
comment on column cm_feixin_tra.account is '飞信公众账号';
comment on column cm_feixin_tra.password is '账号密码，加密存储';
comment on column cm_feixin_tra.apptype is '参赛作品类型.1:娱乐型,  2:服务型,  3:订阅型,  4:创意型';
comment on column cm_feixin_tra.nickname is '昵称';
comment on column cm_feixin_tra.onewordDesc is '一句话简介';
comment on column cm_feixin_tra.icon is '头像';
comment on column cm_feixin_tra.portalUrl is '公众门户地址';
comment on column cm_feixin_tra.status is '状态。0：暂存， 1：参赛成功';
comment on column cm_feixin_tra.createdate is '创建时间';
comment on column cm_feixin_tra.lupddate is '最后更新时间';

---修改存储过程
create or replace procedure p_pushandreport as
v_nstatus     number;
v_nrecod      number;
begin

  v_nstatus := pg_log_manage.f_startlog('p_pushandreport',
                                        'ANDROID实时下载量表的维护');
--del t_a_push_his -10 day data
--delete from t_a_push_his where trunc(lupdate)<trunc(sysdate-10);
--add push -6 day to t_a_push_his
--insert into t_a_push_his select * from t_a_push where trunc(lupdate)<trunc(sysdate-6);
--del t_a_push -6 day data
--delete from t_a_push where trunc(lupdate)<trunc(sysdate-6);


--del t_a_pushreport_his -4 day data
--delete from t_a_pushreport_his where trunc(lupdate)<trunc(sysdate-4);
--add pushreport status != -1 to t_a_pushreport_his
--insert into t_a_pushreport_his select  * from t_a_pushreport where handle_status=0; --  -1：未处理，0：已经处理
--del t_a_pushreport status != -1
--delete from t_a_pushreport where handle_status =0;

  delete from t_a_report r where r.status!=-1;
  
  --t_free_dl_report，t_free_dl_order
  delete from t_free_dl_report t where t.flag='0';
  delete from t_free_dl_order t where t.flag='0';
  
  v_nrecod:=SQL%ROWCOUNT;
  commit;
  --v_nstatus := pg_log_manage.f_successlog();
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;


--媒体化的数据表结构改了
  --drop table v_article;
  --drop table v_article_reference;
  -- 文章数据表
  create table v_article
  (
    contentid number(10),
    title varchar2(255 byte),
    subtitle varchar2(255 byte),
    author varchar2(25 byte),
    source number(1),
    label varchar2(255 byte),
    cover varchar2(200 byte),
    brief varchar2(500 byte),
    star number(1),
    pubtime date,
    edittime date,
    type number(2),
    appid varchar2(400 byte),
    content clob,
    status number(1)
  );
  -- add comments to the table 
  comment on table v_article
    is '文章数据表';
  -- add comments to the columns 
  comment on column v_article.contentid
    is '文章id';
  comment on column v_article.title
    is '标题';
  comment on column v_article.subtitle
    is '短标题';
  comment on column v_article.author
    is '发布者';
  comment on column v_article.source
    is '1，原创；2，转载；3，编译；4，官方';
  comment on column v_article.label
    is '标签，多个标签时使用分号‘;’间隔，';
  comment on column v_article.cover
    is '图片相对地址';
  comment on column v_article.brief
    is '文章简介';
  comment on column v_article.star
    is '商品评分，1~5';
  comment on column v_article.pubtime
    is '发布时间';
  comment on column v_article.edittime
    is '修改时间';
  comment on column v_article.type
    is '文章类型，1、游戏测评2、小众软件3、精选专题4、汇众话题';
  comment on column v_article.appid
    is '文章对应应用id，多个时使用分号‘;’间隔，';
  comment on column v_article.content
    is '文章内容';
  comment on column v_article.status
    is '状态 0， 编辑；1， 待审；2， 驳回；3， 已审；4， 上线；5， 下线；';

  create table v_article_reference
  (
    appid varchar2(20 byte),
    contentid number(10),
    status number(1)
  );
  -- add comments to the table 
  comment on table v_article_reference
    is '文章和应用关联表';
  comment on column v_article_reference.appid is '应用id';
  comment on column v_article_reference.contentid is '文章id';
  comment on column v_article_reference.status is '状态 0， 编辑；1， 待审；2， 驳回；3， 已审；4， 上线；5， 下线；';

--给导出表添加文章导出记录
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (29, 'SELECT CONTENTID,TITLE,LABEL,PUBTIME FROM V_ARTICLE', '搜索系统文件-文章数据', '2', 50000, '0x01', to_date('23-10-2013 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'zhuanti', '/opt/aspire/product/chroot_panguso/panguso/mo', 'GB18030', '2', '1', '69', 3);



--T_R_EXPORTSQL_POR表添加 开发者ID同步代码
INSERT INTO T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
VALUES (8, '飞信同步接口', 'PPMS_CM_FEIXIN', 'CM_FEIXIN', 'CM_FEIXIN_TRA', 'CM_FEIXIN_BAK', '', '');

--t_r_exportsql表添加 开发者id同步代码
INSERT INTO T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
VALUES (31, 'SELECT APPID,ACCOUNT,NICKNAME,ONEWORDDESC,PORTALURL FROM CM_FEIXIN', '飞信同步数据', '1', 50000, ',', TO_DATE('11-11-2013 15:04:00', 'DD-MM-YYYY HH24:MI:SS'), 5, 'i_FEIXIN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', '3023', 1);

----------------飞信同步导出表脚本结束------------------------------------



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.109_SSMS','MM2.0.0.0.115_SSMS');

commit;
