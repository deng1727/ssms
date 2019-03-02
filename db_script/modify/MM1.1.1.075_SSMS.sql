--start�п��������Ѿ����������������ˣ�����Ψһ�Դ���Ŷ�������Ͳ�ִ����������INSERT�ˡ�
insert into t_right values ('2_1505_COMIC','���ض�������','���ض�������','','2');
insert into t_roleright values ('1','2_1505_COMIC');
--end�п���

--����ͬ��
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
  is '���ݱ�';
-- Add comments to the columns 
comment on column T_CB_CONTENT.ID
  is '���ݱ�ʶ';
comment on column T_CB_CONTENT.NAME
  is '��������';
comment on column T_CB_CONTENT.DESCRIPTION
  is '��������';
comment on column T_CB_CONTENT.PROVIDER
  is '�����ṩ��(CP��ʶ)';
comment on column T_CB_CONTENT.PROVIDER_TYPE
  is '�����ṩ������';
comment on column T_CB_CONTENT.AUTHODID
  is '���߱�ʶ';
comment on column T_CB_CONTENT.TYPE
  is '��������';
comment on column T_CB_CONTENT.KEYWORDS
  is '���ݹؼ���';
comment on column T_CB_CONTENT.EXPIRETIME
  is '���ݳ���ʱ��';
comment on column T_CB_CONTENT.FEE
  is '�ʷ�';
comment on column T_CB_CONTENT.LOCATION
  is '���ݹ�����';
comment on column T_CB_CONTENT.FIRST
  is '���ݵ�����ĸ';
comment on column T_CB_CONTENT.URL1
  is 'Ԥ��ͼ1';
comment on column T_CB_CONTENT.URL2
  is 'Ԥ��ͼ2';
comment on column T_CB_CONTENT.URL3
  is 'Ԥ��ͼ3';
comment on column T_CB_CONTENT.URL4
  is 'Ԥ��ͼ4';
comment on column T_CB_CONTENT.INFO_CONTENT
  is '��Ѷ����';
comment on column T_CB_CONTENT.INFO_PIC
  is '��Ѷ����ͼƬ';
comment on column T_CB_CONTENT.INFO_SOURCE
  is '��Ѷ��Դ';
comment on column T_CB_CONTENT.FEE_CODE
  is '�ƷѴ���';
comment on column T_CB_CONTENT.DETAIL_URL1
  is '��������ҳURL1';
comment on column T_CB_CONTENT.DETAIL_URL2
  is '��������ҳURL2';
comment on column T_CB_CONTENT.DETAIL_URL3
  is '��������ҳURL3';
comment on column T_CB_CONTENT.BOOK_NUM
  is '��(��)��';
comment on column T_CB_CONTENT.CLASSIFY
  is '�������������ʣ��������';
comment on column T_CB_CONTENT.AUTHODS
  is '����';
comment on column T_CB_CONTENT.ACTOR
  is '����';
comment on column T_CB_CONTENT.OTHERS_ACTOR
  is '������Ա';
comment on column T_CB_CONTENT.BOOK_TYPE
  is '����������������';
comment on column T_CB_CONTENT.BOOK_STYLE
  is '��������������';
comment on column T_CB_CONTENT.BOOK_COLOR
  is '��������������ɫ';
comment on column T_CB_CONTENT.AREA
  is '��Ʒ����';
comment on column T_CB_CONTENT.LANGUAGE
  is '����';
comment on column T_CB_CONTENT.YEAR
  is '�������';
comment on column T_CB_CONTENT.STATUS
  is '����״̬';
comment on column T_CB_CONTENT.CHAPTER_TYPE
  is 'ƪ������';
comment on column T_CB_CONTENT.PORTAL
  is '�Ż�';
comment on column T_CB_CONTENT.BUSINESSID
  is 'ҵ�����';
comment on column T_CB_CONTENT.DOWNLOAD_NUM
  is '���ش���������ͳ�ƣ�';
comment on column T_CB_CONTENT.AVERAGEMARK
  is '�����Ǽ�������ͳ�ƣ�';
comment on column T_CB_CONTENT.FAVORITES_NUM
  is '�ղ�����������ͳ�ƣ�';
comment on column T_CB_CONTENT.BOOKED_NUM
  is 'Ԥ������������ͳ�ƣ�';
comment on column T_CB_CONTENT.CREATETIME
  is '����ʱ��';
comment on column T_CB_CONTENT.FLOW_TIME
  is '��ˮʱ��';
comment on column T_CB_CONTENT.USER_TYPE
  is '�û�����';
comment on column T_CB_CONTENT.LUPDATE
  is '�޸�ʱ��';
comment on column T_CB_CONTENT.COMIC_IMAGE
  is '��������';
comment on column T_CB_CONTENT.ADAPTERDESK
  is '����ƽ̨';
comment on column T_CB_CONTENT.SYNC_STATUS
  is '��¼״̬�������ֶΣ�';

--��Ƶ�Զ�����
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
  is '�����Զ����¼ܱ�';
-- Add comments to the columns 
comment on column T_SYNC_TACTIC_BASE.ID
  is 'ID����';
comment on column T_SYNC_TACTIC_BASE.DEL_SQL
  is 'ɾ������SQL���¼ܣ�';
comment on column T_SYNC_TACTIC_BASE.INSERT_SQL
  is '��������SQL���ϼܣ�';
comment on column T_SYNC_TACTIC_BASE.EFFECTIVETIME
  is '��Чʱ��';
comment on column T_SYNC_TACTIC_BASE.LUPTIME
  is '���ִ��ʱ��';
comment on column T_SYNC_TACTIC_BASE.TIME_CONSUMING
  is '���һ��ִ�еĺ�ʱ�����룩';
  
  
  
  -- Alter table�޸ı���ռ�Ϊ1%��ԭ����Ϊ10%---
----ֻ�ʺϺ���update  ����Insert�����ı�--------
alter table T_VO_VIDEO  pctfree 1 ;
alter table T_VO_PROGRAM  pctfree 1 ;
  alter table T_VO_VIDEO modify DOWNLOADFILEPATH null;
  ----��Ƶ��Ŀ���ͱ�
  -- Create table
create table T_VO_NODETYPE
(
  nodeid   VARCHAR2(50),
  nodename VARCHAR2(200),
  nodetype VARCHAR2(30)
);

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53208', 'Ӱ����Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('70342', 'Ӱ����Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10016350', 'Ӱ����Ŀ��Ԫ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53080', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('61839', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64319', 'ԭ����Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('72908', 'ԭ����Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('90761', 'ԭ����Ŀ��Ԫ��', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64299', '��Ц��Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('75852', '��Ц��Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('95968', '��Ц��Ŀ2Ԫ��', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('53745', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('63622', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('90488', '������Ŀ��Ԫ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52323', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('65842', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10082417', '�°�������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10038113', '������Ŀ��Ԫ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52953', '�ƾ���Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('64922', '�ƾ���Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('63647', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('72524', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048885', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10100025', '������Ŀ2Ԫ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084815', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048884', 'ʱ����Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10073076', 'ʱ����Ŀ��Ԫר��', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10073077', 'ʱ����Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048886', '��¼Ƭ��Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10087713', '��¼Ƭ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10048883', '������Ŀ', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10082297', '������Ŀ���', '3');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51682', 'CCTV�ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52984', 'CCTV����Ƶ��', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52205', 'CCTV����Ƶ��', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51656', '�����ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('79168', '����ͨNBA', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('42434', '�����߶���Ƶ��', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51695', 'CRI�ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51704', '�й����ҵ���', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51721', '�й�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51659', '�����Ѷ�ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('51726', '��Ѷ�й��ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('52151', '�������ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('56137', '�»���Ѷ', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('117524', '�����ֻ���Ѷ', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('110084', '�����ֻ�����', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084088', 'CTV�ֻ���Ƶ', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084132', 'â��TV�ֻ���Ƶ', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10084018', '�����ֻ���Ƶ', '4');

insert into T_VO_NODETYPE (NODEID, NODENAME, NODETYPE)
values ('10097022', '�ֻ���ƵV+��ѡ��', '7');

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
 

  
  
  
  ------��Ƶ��Ŀ˵����
create table T_VO_NODEEXT
(
  nodeid   VARCHAR2(60),
  nodedesc VARCHAR2(1000),
  lupdate  DATE default sysdate
);
-- Add comments to the columns 
comment on column T_VO_NODEEXT.nodeid
  is '��ĿID';
comment on column T_VO_NODEEXT.nodedesc
  is '��Ŀ˵��';
comment on column T_VO_NODEEXT.lupdate
  is '������ʱ��';
-- Create/Recreate indexes 
create unique index PK_NODEID_1 on T_VO_NODEEXT (NODEID);


----------��Ƶ�Զ����¼�׼���ı���ͳ������
-- Create the synonym 
create or replace synonym REPORT_V_VIDEO_STAT_D for V_VIDEO_STAT_D@REPORT105.ORACLE.COM;

create table v_video_stat_d as select * from report_v_video_stat_d where 1=2;
create table V_VIDEO_STAT_D_TRA as select ��* from V_VIDEO_STAT_D where 1=2;

create or replace procedure p_V_VIDEO_STAT_D as
   v_nindnum    number;--��¼�����Ƿ����
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('V_VIDEO_STAT_D',
                                        '��ȡ����V_VIDEO_STAT_D���ݿ�ʼ������');
  execute immediate 'truncate table V_VIDEO_STAT_D_TRA';
  --��ս����ʷ������
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
   --����ɹ�����ִ�����д����־

  commit;
  else
     raise_application_error(-20088,'����V_VIDEO_STAT_D�ṩ����Ϊ��');
  end if;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;
/
----------------�洢���̽���-------------

--���JOB������ʱ���Ǳ�����������֮��������Ƶ�Զ����¼���system-config.xml�����һ�����ã�basecommon-startTime������ʱ��Ҫ�����ʱ��֮��ʹ���ʱ���жϰ�����������������
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
--------------------����ͬ�������ϵͬ���Ż�-------------------------
--------------------------------------------------------------------
--------------------------------------------------------------------

-- Create the synonym 
--------ͬ��ʰ��������淶��д������ʱ��Ҫ����ʵ������޸�----
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
  ---------�洢����----------------
   
create or replace function f_ppms_cm_device_pid (
          v_type   in   number,  ----1 ����;0��ȫ��
          v_desc   in   varchar2)  return number as
 ---create or replace procedure p_ppms_cm_device_pid as
  v_sql_f varchar2(1200);
   v_nindnum    number;--��¼�����Ƿ����
  v_nstatus     number;
  v_nrecod      number;

  v_nindnum1  number;
  v_nindnum2  number;
  v_nindnum3  number;
  v_nindnum4  number;
  v_nindnum5  number;

begin
  v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                        '����ͬ�������������������������ϵ��');
if v_type = 1 then
  -----����ͬ��
             execute immediate 'truncate table t_ppms_cm_device_pid_TRA';
                     --��ս����ʷ������

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
                                       --����ɹ�����ִ�����д����־

                                       commit;
                                       -------�������������ϵ��-----
                                       delete from v_cm_device_resource r where exists (select 1 from t_ppms_cm_device_pid p where r.pid=p.pid );
                                       insert into  v_cm_device_resource  select * from PPMS_CM_DEVICE_RESOURCE r where exists (select 1 from v_ppms_cm_device_pid p where r.pid=p.pid );
                                          commit;

                      else
                                          raise_application_error(-20088,'�������ṩ����Ϊ��');
                     end if;
     else
                      -----ȫ��ͬ��
                       select count(9) into v_nindnum1 from user_tables a where a.TABLE_NAME= 'V_CM_DEVICE_RESOURCE_MID';
                        if v_nindnum1=1 then
                           execute immediate 'drop table  v_cm_device_resource_mid' ;
                         end if;
                        execute immediate 'create table  v_cm_device_resource_mid as select *��from PPMS_CM_DEVICE_RESOURCE' ;
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
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
    return (1);
end;
----------------�洢���̽���-------------
--------------------------------------------------------------------
--------------------------------------------------------------------
--------------------����ͬ�������ϵͬ���Ż�-------------------------
--------------------------------------------------------------------
--------------------------------------------------------------------




alter table t_vo_program add  ftplogopath varchar2(512);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.1.1.0','MM1.1.1.069_SSMS','MM1.1.1.065_SSMS');


commit;