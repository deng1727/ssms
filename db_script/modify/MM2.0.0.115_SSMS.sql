-----����seq_t_free_dl_report_id�Զ���������
create sequence seq_t_free_dl_report_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-----����seq_t_free_dl_order_id�Զ���������
create sequence seq_t_free_dl_order_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;

-----���������Ӧ�ö�����¼�� t_free_dl_order
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
  is '���Ӧ�ö�����¼��';
  
 
-----���������Ӧ�����ػ�ִ��־�� t_free_dl_report
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
  is '���ػ�ִ��־��';
-- add comments to the columns 
comment on column t_free_dl_report.id
  is 'id��ʶ��������ɣ�';
comment on column t_free_dl_report.sequenceid
  is '��־��¼Ψһ���';
comment on column t_free_dl_report.pushid
  is 'Ӧ���������ͱ�ʶ';
comment on column t_free_dl_report.status
  is 'Ӧ�����ط�����0�����سɹ����������ش��󷵻���';
comment on column t_free_dl_report.outputtime
  is '��־��¼���ʱ��,��ʽΪ��yyyymmddhh24miss��';
comment on column t_free_dl_report.params
  is '�㲥���Ӳ���(֧�ֶ������,�����������߷ָ�)�����ݸ�ʽʾ����param1=value| param2=value2|����ǰ֧�ֵĲ�������softplat��acesstype��appname��clienttype��touchversion��browser�̶��softplat=mm';
comment on column t_free_dl_report.useraccesstype
  is '��Ϊ�գ�ȡֵ��cmmm cmnet cmwap wifi wlan�ַ��������';
comment on column t_free_dl_report.lupdatetime
  is '���ʱ��';
comment on column t_free_dl_report.flag
  is '�����ʶ��Ĭ��-1,�����������״̬Ϊ0��';




-- �����������ֶ�
alter table T_MB_MUSIC_NEW add dolbytype VARCHAR2(10);
-- Add comments to the columns 
comment on column T_MB_MUSIC_NEW.dolbytype
  is '�Ƿ�ű���Ч,0����֧�֣� 1��֧��';
  




-- ��Ƶ����ͬ���ű�
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
                                        '����ͬ�������������');
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
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;


-----------------������IDͬ���ӿڽű���ʼ------------------------------------

-----��PPMS���ݿ��û�����Ȩ������
--- ͬһ��ʵ������Ҫ��Ȩ ����ΪDBLINK ����Ҫgrant select on om_developer_company to &ssms;

---����ͬ���ppms_om_developer_company
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


--t_r_exportsql_por����� ������IDͬ������
INSERT INTO T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
VALUES (7, '������IDͬ���ӿ�', 'PPMS_OM_DEVELOPER_COMPANY', 'T_OM_DEVELOPER_COMPANY', 'T_OM_DEVELOPER_COMPANY_TRA', 'T_OM_DEVELOPER_COMPANY_BAK');

--t_r_exportsql����� ������idͬ������
INSERT INTO T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
VALUES (30, 'SELECT DEVELOPERID,COMPANYID FROM T_OM_DEVELOPER_COMPANY', '������IDͬ������', '1', 50000, ',', TO_DATE('11-11-2013 15:04:00', 'DD-MM-YYYY HH24:MI:SS'), 2, 'i_VDC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', '3023', 1);

-----------------������IDͬ���ӿڽű�����------------------------------------



-----------------����ͬ��������ű���ʼ------------------------------------
-----��PPMS���ݿ��û�����Ȩ������
--- ͬһ��ʵ������Ҫ��Ȩ ����ΪDBLINK ����Ҫgrant select on cm_feixin to &ssms;

---����ͬ���ppms_om_developer_company
-- Create the synonym 

create or replace synonym ppms_cm_feixin
  for cm_feixin@dl_ppms_device;
  
--���ű�
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
comment on table cm_feixin is '��ҵ�������Ų�����Ʒ��';
comment on column cm_feixin.appId is '���Ų���ID,����contentid��seq����';
comment on column cm_feixin.developerId is '������ID';
comment on column cm_feixin.account is '���Ź����˺�';
comment on column cm_feixin.password is '�˺����룬���ܴ洢';
comment on column cm_feixin.apptype is '������Ʒ����.1:������,  2:������,  3:������,  4:������';
comment on column cm_feixin.nickname is '�ǳ�';
comment on column cm_feixin.onewordDesc is 'һ�仰���';
comment on column cm_feixin.icon is 'ͷ��';
comment on column cm_feixin.portalUrl is '�����Ż���ַ';
comment on column cm_feixin.status is '״̬��0���ݴ棬 1�������ɹ�';
comment on column cm_feixin.createdate is '����ʱ��';
comment on column cm_feixin.lupddate is '������ʱ��';


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
comment on table cm_feixin_tra is '��ҵ�������Ų�����Ʒ��';
comment on column cm_feixin_tra.appId is '���Ų���ID,����contentid��seq����';
comment on column cm_feixin_tra.developerId is '������ID';
comment on column cm_feixin_tra.account is '���Ź����˺�';
comment on column cm_feixin_tra.password is '�˺����룬���ܴ洢';
comment on column cm_feixin_tra.apptype is '������Ʒ����.1:������,  2:������,  3:������,  4:������';
comment on column cm_feixin_tra.nickname is '�ǳ�';
comment on column cm_feixin_tra.onewordDesc is 'һ�仰���';
comment on column cm_feixin_tra.icon is 'ͷ��';
comment on column cm_feixin_tra.portalUrl is '�����Ż���ַ';
comment on column cm_feixin_tra.status is '״̬��0���ݴ棬 1�������ɹ�';
comment on column cm_feixin_tra.createdate is '����ʱ��';
comment on column cm_feixin_tra.lupddate is '������ʱ��';

---�޸Ĵ洢����
create or replace procedure p_pushandreport as
v_nstatus     number;
v_nrecod      number;
begin

  v_nstatus := pg_log_manage.f_startlog('p_pushandreport',
                                        'ANDROIDʵʱ���������ά��');
--del t_a_push_his -10 day data
--delete from t_a_push_his where trunc(lupdate)<trunc(sysdate-10);
--add push -6 day to t_a_push_his
--insert into t_a_push_his select * from t_a_push where trunc(lupdate)<trunc(sysdate-6);
--del t_a_push -6 day data
--delete from t_a_push where trunc(lupdate)<trunc(sysdate-6);


--del t_a_pushreport_his -4 day data
--delete from t_a_pushreport_his where trunc(lupdate)<trunc(sysdate-4);
--add pushreport status != -1 to t_a_pushreport_his
--insert into t_a_pushreport_his select  * from t_a_pushreport where handle_status=0; --  -1��δ����0���Ѿ�����
--del t_a_pushreport status != -1
--delete from t_a_pushreport where handle_status =0;

  delete from t_a_report r where r.status!=-1;
  
  --t_free_dl_report��t_free_dl_order
  delete from t_free_dl_report t where t.flag='0';
  delete from t_free_dl_order t where t.flag='0';
  
  v_nrecod:=SQL%ROWCOUNT;
  commit;
  --v_nstatus := pg_log_manage.f_successlog();
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;


--ý�廯�����ݱ�ṹ����
  --drop table v_article;
  --drop table v_article_reference;
  -- �������ݱ�
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
    is '�������ݱ�';
  -- add comments to the columns 
  comment on column v_article.contentid
    is '����id';
  comment on column v_article.title
    is '����';
  comment on column v_article.subtitle
    is '�̱���';
  comment on column v_article.author
    is '������';
  comment on column v_article.source
    is '1��ԭ����2��ת�أ�3�����룻4���ٷ�';
  comment on column v_article.label
    is '��ǩ�������ǩʱʹ�÷ֺš�;�������';
  comment on column v_article.cover
    is 'ͼƬ��Ե�ַ';
  comment on column v_article.brief
    is '���¼��';
  comment on column v_article.star
    is '��Ʒ���֣�1~5';
  comment on column v_article.pubtime
    is '����ʱ��';
  comment on column v_article.edittime
    is '�޸�ʱ��';
  comment on column v_article.type
    is '�������ͣ�1����Ϸ����2��С�����3����ѡר��4�����ڻ���';
  comment on column v_article.appid
    is '���¶�ӦӦ��id�����ʱʹ�÷ֺš�;�������';
  comment on column v_article.content
    is '��������';
  comment on column v_article.status
    is '״̬ 0�� �༭��1�� ����2�� ���أ�3�� ����4�� ���ߣ�5�� ���ߣ�';

  create table v_article_reference
  (
    appid varchar2(20 byte),
    contentid number(10),
    status number(1)
  );
  -- add comments to the table 
  comment on table v_article_reference
    is '���º�Ӧ�ù�����';
  comment on column v_article_reference.appid is 'Ӧ��id';
  comment on column v_article_reference.contentid is '����id';
  comment on column v_article_reference.status is '״̬ 0�� �༭��1�� ����2�� ���أ�3�� ����4�� ���ߣ�5�� ���ߣ�';

--��������������µ�����¼
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (29, 'SELECT CONTENTID,TITLE,LABEL,PUBTIME FROM V_ARTICLE', '����ϵͳ�ļ�-��������', '2', 50000, '0x01', to_date('23-10-2013 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'zhuanti', '/opt/aspire/product/chroot_panguso/panguso/mo', 'GB18030', '2', '1', '69', 3);



--T_R_EXPORTSQL_POR����� ������IDͬ������
INSERT INTO T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
VALUES (8, '����ͬ���ӿ�', 'PPMS_CM_FEIXIN', 'CM_FEIXIN', 'CM_FEIXIN_TRA', 'CM_FEIXIN_BAK', '', '');

--t_r_exportsql����� ������idͬ������
INSERT INTO T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
VALUES (31, 'SELECT APPID,ACCOUNT,NICKNAME,ONEWORDDESC,PORTALURL FROM CM_FEIXIN', '����ͬ������', '1', 50000, ',', TO_DATE('11-11-2013 15:04:00', 'DD-MM-YYYY HH24:MI:SS'), 5, 'i_FEIXIN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', '3023', 1);

----------------����ͬ��������ű�����------------------------------------



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.109_SSMS','MM2.0.0.0.115_SSMS');

commit;
