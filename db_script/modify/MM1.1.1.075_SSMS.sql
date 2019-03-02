--start有可能现网已经有下面两个数据了，出现唯一性错误哦。那样就不执行下面两个INSERT了。
insert into t_right values ('2_1505_COMIC','基地动漫管理','基地动漫管理','','2');
insert into t_roleright values ('1','2_1505_COMIC');
--end有可能

--动漫同步
alter table t_cb_content  modify  name varchar2(512);
alter table t_cb_content  modify  info_source varchar2(1024);

drop table T_CB_CONTENT;
-- Create table
create table T_CB_CONTENT
(
  ID            VARCHAR2(60) not null,
  NAME          VARCHAR2(512) not null,
  DESCRIPTION   VARCHAR2(4000),
  PROVIDER      VARCHAR2(60),
  PROVIDER_TYPE VARCHAR2(60),
  AUTHODID      VARCHAR2(60),
  TYPE          VARCHAR2(5),
  KEYWORDS      VARCHAR2(4000),
  EXPIRETIME    VARCHAR2(14),
  FEE           NUMBER(10),
  LOCATION      VARCHAR2(100),
  FIRST         VARCHAR2(5),
  URL1          VARCHAR2(512),
  URL2          VARCHAR2(512),
  URL3          VARCHAR2(512),
  URL4          VARCHAR2(512),
  INFO_CONTENT  VARCHAR2(4000),
  INFO_PIC      VARCHAR2(512),
  INFO_SOURCE   VARCHAR2(4000),
  FEE_CODE      VARCHAR2(60),
  DETAIL_URL1   VARCHAR2(512),
  DETAIL_URL2   VARCHAR2(512),
  DETAIL_URL3   VARCHAR2(512),
  BOOK_NUM      NUMBER(10),
  CLASSIFY      VARCHAR2(100),
  AUTHODS       VARCHAR2(1024),
  ACTOR         VARCHAR2(1024),
  OTHERS_ACTOR  VARCHAR2(4000),
  BOOK_TYPE     VARCHAR2(50),
  BOOK_STYLE    VARCHAR2(50),
  BOOK_COLOR    VARCHAR2(50),
  AREA          VARCHAR2(50),
  LANGUAGE      VARCHAR2(50),
  YEAR          VARCHAR2(14),
  STATUS        VARCHAR2(4),
  CHAPTER_TYPE  VARCHAR2(4),
  PORTAL        VARCHAR2(1) default '0',
  BUSINESSID    VARCHAR2(64),
  DOWNLOAD_NUM  NUMBER(10),
  AVERAGEMARK   NUMBER(1),
  FAVORITES_NUM NUMBER(10),
  BOOKED_NUM    NUMBER(10),
  CREATETIME    VARCHAR2(14),
  FLOW_TIME     DATE,
  USER_TYPE     VARCHAR2(50),
  LUPDATE       VARCHAR2(14),
  COMIC_IMAGE   VARCHAR2(512),
  ADAPTERDESK   VARCHAR2(50),
  SYNC_STATUS   NUMBER(1) default 1
);

-- Add comments to the table 
comment on table T_CB_CONTENT
  is '内容表';
-- Add comments to the columns 
comment on column T_CB_CONTENT.ID
  is '内容标识';
comment on column T_CB_CONTENT.NAME
  is '内容名称';
comment on column T_CB_CONTENT.DESCRIPTION
  is '内容描述';
comment on column T_CB_CONTENT.PROVIDER
  is '内容提供方(CP标识)';
comment on column T_CB_CONTENT.PROVIDER_TYPE
  is '内容提供者类型';
comment on column T_CB_CONTENT.AUTHODID
  is '作者标识';
comment on column T_CB_CONTENT.TYPE
  is '内容类型';
comment on column T_CB_CONTENT.KEYWORDS
  is '内容关键字';
comment on column T_CB_CONTENT.EXPIRETIME
  is '内容超期时间';
comment on column T_CB_CONTENT.FEE
  is '资费';
comment on column T_CB_CONTENT.LOCATION
  is '内容归属地';
comment on column T_CB_CONTENT.FIRST
  is '内容的首字母';
comment on column T_CB_CONTENT.URL1
  is '预览图1';
comment on column T_CB_CONTENT.URL2
  is '预览图2';
comment on column T_CB_CONTENT.URL3
  is '预览图3';
comment on column T_CB_CONTENT.URL4
  is '预览图4';
comment on column T_CB_CONTENT.INFO_CONTENT
  is '资讯内容';
comment on column T_CB_CONTENT.INFO_PIC
  is '资讯配套图片';
comment on column T_CB_CONTENT.INFO_SOURCE
  is '资讯来源';
comment on column T_CB_CONTENT.FEE_CODE
  is '计费代码';
comment on column T_CB_CONTENT.DETAIL_URL1
  is '内容详情页URL1';
comment on column T_CB_CONTENT.DETAIL_URL2
  is '内容详情页URL2';
comment on column T_CB_CONTENT.DETAIL_URL3
  is '内容详情页URL3';
comment on column T_CB_CONTENT.BOOK_NUM
  is '话(集)数';
comment on column T_CB_CONTENT.CLASSIFY
  is '漫（动、主、资）画书分类';
comment on column T_CB_CONTENT.AUTHODS
  is '作者';
comment on column T_CB_CONTENT.ACTOR
  is '主演';
comment on column T_CB_CONTENT.OTHERS_ACTOR
  is '其他演员';
comment on column T_CB_CONTENT.BOOK_TYPE
  is '漫（动）画书类型';
comment on column T_CB_CONTENT.BOOK_STYLE
  is '漫（动）画书风格';
comment on column T_CB_CONTENT.BOOK_COLOR
  is '漫（动）画书颜色';
comment on column T_CB_CONTENT.AREA
  is '出品地区';
comment on column T_CB_CONTENT.LANGUAGE
  is '语种';
comment on column T_CB_CONTENT.YEAR
  is '发行年份';
comment on column T_CB_CONTENT.STATUS
  is '连载状态';
comment on column T_CB_CONTENT.CHAPTER_TYPE
  is '篇章类型';
comment on column T_CB_CONTENT.PORTAL
  is '门户';
comment on column T_CB_CONTENT.BUSINESSID
  is '业务代码';
comment on column T_CB_CONTENT.DOWNLOAD_NUM
  is '下载次数（动漫统计）';
comment on column T_CB_CONTENT.AVERAGEMARK
  is '动漫星级（动漫统计）';
comment on column T_CB_CONTENT.FAVORITES_NUM
  is '收藏人数（动漫统计）';
comment on column T_CB_CONTENT.BOOKED_NUM
  is '预定人数（动漫统计）';
comment on column T_CB_CONTENT.CREATETIME
  is '创建时间';
comment on column T_CB_CONTENT.FLOW_TIME
  is '流水时间';
comment on column T_CB_CONTENT.USER_TYPE
  is '用户类型';
comment on column T_CB_CONTENT.LUPDATE
  is '修改时间';
comment on column T_CB_CONTENT.COMIC_IMAGE
  is '动漫形象';
comment on column T_CB_CONTENT.ADAPTERDESK
  is '适配平台';
comment on column T_CB_CONTENT.SYNC_STATUS
  is '记录状态（辅助字段）';

--视频自动更新
drop table T_SYNC_TACTIC_BASE;
create table T_SYNC_TACTIC_BASE
(
  ID             NUMBER(10) not null,
  DEL_SQL        VARCHAR2(512) not null,
  INSERT_SQL     VARCHAR2(1024) not null,
  EFFECTIVETIME  DATE,
  LUPTIME        DATE,
  TIME_CONSUMING NUMBER(12),
  primary key(id)
);
-- Add comments to the table 
comment on table T_SYNC_TACTIC_BASE
  is '基地自动上下架表';
-- Add comments to the columns 
comment on column T_SYNC_TACTIC_BASE.ID
  is 'ID主键';
comment on column T_SYNC_TACTIC_BASE.DEL_SQL
  is '删除数据SQL（下架）';
comment on column T_SYNC_TACTIC_BASE.INSERT_SQL
  is '增加数据SQL（上架）';
comment on column T_SYNC_TACTIC_BASE.EFFECTIVETIME
  is '生效时间';
comment on column T_SYNC_TACTIC_BASE.LUPTIME
  is '最后执行时间';
comment on column T_SYNC_TACTIC_BASE.TIME_CONSUMING
  is '最后一次执行的耗时（毫秒）';
  
  
  
  -- Alter table修改表缓冲空间为1%，原来的为10%---
----只适合很少update  大量Insert操作的表--------
alter table T_VO_VIDEO  pctfree 1 ;
alter table T_VO_PROGRAM  pctfree 1 ;
  alter table T_VO_VIDEO modify DOWNLOADFILEPATH null;
  ----视频栏目类型表
  -- Create table
create table T_VO_NODETYPE
(
  nodeid   VARCHAR2(50),
  nodename VARCHAR2(200),
  nodetype VARCHAR2(30)
);

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53208', '影视栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('70342', '影视栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10016350', '影视栏目二元', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53080', '娱乐栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('61839', '娱乐栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64319', '原创栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('72908', '原创栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('90761', '原创栏目两元区', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64299', '搞笑栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('75852', '搞笑栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('95968', '搞笑栏目2元区', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53745', '动漫栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('63622', '动漫栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('90488', '动漫栏目二元', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52323', '体育栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('65842', '体育栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10082417', '新版体育栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10038113', '体育栏目二元', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52953', '财经栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64922', '财经栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('63647', '军事栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('72524', '军事栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048885', '法制栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10100025', '法制栏目2元', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084815', '法制栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048884', '时尚栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10073076', '时尚栏目二元专区', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10073077', '时尚栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048886', '纪录片栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10087713', '纪录片免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048883', '新闻栏目', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10082297', '新闻栏目免费', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51682', 'CCTV手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52984', 'CCTV第五频道', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52205', 'CCTV足球频道', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51656', '东方手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('79168', '百事通NBA', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('42434', '东方高尔夫频道', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51695', 'CRI手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51704', '中国国家地理', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51721', '中国气象', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51659', '央广视讯手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51726', '视讯中国手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52151', '人民网手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('56137', '新华视讯', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('117524', '中青手机视讯', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('110084', '华数手机电视', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084088', 'CTV手机视频', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084132', '芒果TV手机视频', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084018', '乐视手机视频', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10097022', '手机视频V+精选包', '7');

create or replace view  v_vo_category as 
select c.id,
       c.parentid,
       c.baseid,
       decode(t.nodetype, null, c.basetype, t.nodetype) as basetype,c.basename,
       c.sortid,
       c.isshow,
       c.baseparentid,
       c.desca,
       c.cdesc
  from T_VO_NODETYPE t, t_vo_category c
 where c.baseid = t.nodeid(+);
 

  
  
  
  ------视频栏目说明表
create table T_VO_NODEEXT
(
  nodeid   VARCHAR2(60),
  nodedesc VARCHAR2(1000),
  lupdate  DATE default sysdate
);
-- Add comments to the columns 
comment on column T_VO_NODEEXT.nodeid
  is '栏目ID';
comment on column T_VO_NODEEXT.nodedesc
  is '栏目说明';
comment on column T_VO_NODEEXT.lupdate
  is '最后更新时间';
-- Create/Recreate indexes 
create unique index PK_NODEID_1 on T_VO_NODEEXT (NODEID);


----------视频自动上下架准备的报表统计数据
-- Create the synonym 
create or replace synonym REPORT_V_VIDEO_STAT_D for V_VIDEO_STAT_D@REPORT105.ORACLE.COM;

create table v_video_stat_d as select * from report_v_video_stat_d where 1=2;
create table V_VIDEO_STAT_D_TRA as select 　* from V_VIDEO_STAT_D where 1=2;

create or replace procedure p_V_VIDEO_STAT_D as
   v_nindnum    number;--记录数据是否存在
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('V_VIDEO_STAT_D',
                                        '获取报表V_VIDEO_STAT_D数据开始。。。');
  execute immediate 'truncate table V_VIDEO_STAT_D_TRA';
  --清空结果历史表数据
  insert into V_VIDEO_STAT_D_TRA
          select t.STAT_TIME,           
            t.CONTENT_ID,                     
            t.DOWN_COUNT,                        
            t.ADD_7DAYS_DOWN_COUNT,                         
            t.ADD_30DAYS_DOWN_COUNT,                         
            t.PLAY_COUNT,                        
            t.ADD_7DAYS_PLAY_COUNT,                         
            t.ADD_30DAYS_PLAY_COUNT 
          from report_v_video_stat_d t where t.stat_time = to_char(sysdate-1,'yyyyMMdd');
v_nrecod:=SQL%ROWCOUNT;
  --execute immediate v_sql_f;
select count(9) into v_nindnum  from V_VIDEO_STAT_D_TRA;

    if v_nindnum>0 then
  execute  immediate   'alter table V_VIDEO_STAT_D rename to V_VIDEO_STAT_D_BAK';
  execute  immediate   'alter table V_VIDEO_STAT_D_TRA rename to V_VIDEO_STAT_D';
  execute  immediate   'alter table V_VIDEO_STAT_D_BAK rename to V_VIDEO_STAT_D_TRA';
   --如果成功，将执行情况写入日志

  commit;
  else
     raise_application_error(-20088,'报表V_VIDEO_STAT_D提供数据为空');
  end if;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
/
----------------存储过程结束-------------

--这个JOB的启动时间是报表生成数据之后，我们视频自动上下架在system-config.xml里面的一个配置（basecommon-startTime）启动时间要在这个时间之后。痛苦的时间判断啊。。。待定。。。
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'p_v_video_stat_d;',
                      next_date => to_date('13-07-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/
  

--------------------------------------------------------------------
--------------------------------------------------------------------
--------------------内容同步适配关系同步优化-------------------------
--------------------------------------------------------------------
--------------------------------------------------------------------

-- Create the synonym 
--------同义词按照现网规范编写，测试时需要根据实际情况修改----
-------------------
create or replace synonym S_PPMS_CM_DEVICE_PID
  for V_CM_DEVICE_PID@DL_PPMS_DEVICE;
  
  
  create or replace view v_ppms_cm_device_pid as
select t.pid  from s_ppms_cm_device_pid t where t.lupddate >= trunc(sysdate-1);


-- Create table
create table T_PPMS_CM_DEVICE_PID
(
  PID VARCHAR2(12) not null
);
-- Create/Recreate indexes 
create index IDX_PID on T_PPMS_CM_DEVICE_PID (PID);

-- Create table
create table T_PPMS_CM_DEVICE_PID_TRA
(
  PID VARCHAR2(12) not null
);
-- Create/Recreate indexes 
create index IDX_PID3 on T_PPMS_CM_DEVICE_PID_TRA (PID);

-- Create/Recreate indexes 
create index idx_pid_devcie on V_CM_DEVICE_RESOURCE (pid);


  
  -------------------------------
  ---------存储过程----------------
   
create or replace function f_ppms_cm_device_pid (
          v_type   in   number,  ----1 增量;0，全量
          v_desc   in   varchar2)  return number as
 ---create or replace procedure p_ppms_cm_device_pid as
  v_sql_f varchar2(1200);
   v_nindnum    number;--记录数据是否存在
  v_nstatus     number;
  v_nrecod      number;

  v_nindnum1  number;
  v_nindnum2  number;
  v_nindnum3  number;
  v_nindnum4  number;
  v_nindnum5  number;

begin
  v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                        '重新同步变更程序包并增量更新适配关系表');
if v_type = 1 then
  -----增量同步
             execute immediate 'truncate table t_ppms_cm_device_pid_TRA';
                     --清空结果历史表数据

                      insert into t_ppms_cm_device_pid_TRA
                               select t.pid
                                       from v_ppms_cm_device_pid t;
                       v_nrecod:=SQL%ROWCOUNT;
                       --execute immediate v_sql_f;
                      select count(9) into v_nindnum  from t_ppms_cm_device_pid_TRA;

                      if v_nindnum>0 then
                                     execute  immediate   'alter table t_ppms_cm_device_pid rename to t_ppms_cm_device_pid_BAK';
                                      execute  immediate   'alter table t_ppms_cm_device_pid_TRA rename to t_ppms_cm_device_pid';
                                      execute  immediate   'alter table t_ppms_cm_device_pid_BAK rename to t_ppms_cm_device_pid_TRA';
                                       --如果成功，将执行情况写入日志

                                       commit;
                                       -------增量更新适配关系表-----
                                       delete from v_cm_device_resource r where exists (select 1 from t_ppms_cm_device_pid p where r.pid=p.pid );
                                       insert into  v_cm_device_resource  select * from PPMS_CM_DEVICE_RESOURCE r where exists (select 1 from v_ppms_cm_device_pid p where r.pid=p.pid );
                                          commit;

                      else
                                          raise_application_error(-20088,'电子流提供数据为空');
                     end if;
     else
                      -----全量同步
                       select count(9) into v_nindnum1 from user_tables a where a.TABLE_NAME= 'V_CM_DEVICE_RESOURCE_MID';
                        if v_nindnum1=1 then
                           execute immediate 'drop table  v_cm_device_resource_mid' ;
                         end if;
                        execute immediate 'create table  v_cm_device_resource_mid as select *　from PPMS_CM_DEVICE_RESOURCE' ;
                        select count(9) into v_nindnum2 from user_indexes a where a.INDEX_NAME= 'IDX_V_CM_DEVICE_RESOURCE00711';
                         if v_nindnum2=1 then
                             execute immediate 'drop index  IDX_V_CM_DEVICE_RESOURCE00711' ;
                         end if;
                        execute immediate 'create index IDX_V_CM_DEVICE_RESOURCE00711 on v_cm_device_resource_mid (CONTENTID, DEVICE_NAME)';
                        select count(9) into v_nindnum3 from user_indexes a where a.INDEX_NAME= 'IDX_PID_DEVCIE';
                         if v_nindnum3=1 then
                             execute immediate 'drop index  IDX_PID_DEVCIE' ;
                         end if;
                        execute immediate 'create index idx_pid_devcie on v_cm_device_resource_mid (pid)';
                        execute immediate 'alter table   v_cm_device_resource rename to     v_cm_device_resource_bak';
                        execute immediate 'alter table   v_cm_device_resource_mid rename to     v_cm_device_resource';
                        execute immediate 'alter table   v_cm_device_resource_bak rename to     v_cm_device_resource_mid';
    end if;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
   return (0);
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
    return (1);
end;
----------------存储过程结束-------------
--------------------------------------------------------------------
--------------------------------------------------------------------
--------------------内容同步适配关系同步优化-------------------------
--------------------------------------------------------------------
--------------------------------------------------------------------




alter table t_vo_program add  ftplogopath varchar2(512);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.1.1.0','MM1.1.1.069_SSMS','MM1.1.1.065_SSMS');


commit;