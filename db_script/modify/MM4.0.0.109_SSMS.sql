
----------------------------------------------��ҵ���Ʒ��T_V_PROPKG���һ���ֶ�  ��ʼ------------------------------------------------------------------
alter  table T_V_PROPKG add propkg_parentid VARCHAR2(21);
comment on column T_V_PROPKG.propkg_parentid
  is '����Ʒ��ID';
----------------------------------------------��ҵ���Ʒ��T_V_PROPKG���һ���ֶ�  ��ʼ------------------------------------------------------------------
  
  
----------------------------------------------�����񵥷���T_V_LIST_PUBLISH ��ʼ-------------------------------------------------------------------
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
  is '�񵥷���';
-- Add comments to the columns 
comment on column T_V_LIST_PUBLISH.platform
  is 'VGOP�������ֵҵ��ƽ̨����';
comment on column T_V_LIST_PUBLISH.bankname
  is '��Ƶ���ذ񵥵�����';
comment on column T_V_LIST_PUBLISH.sortid
  is 'ҵ������Ƶ���ذ񵥵�����';
comment on column T_V_LIST_PUBLISH.programid
  is '����������ƽ̨�Լ��Ĳ�Ʒ��Ϣ����';
comment on column T_V_LIST_PUBLISH.programname
  is '��Ʒ����';
comment on column T_V_LIST_PUBLISH.vauthor
  is '��Ʒ���ݵ�ԭ���ߡ����߻�����߻������ṩ��';
comment on column T_V_LIST_PUBLISH.detail
  is '�Բ�Ʒ�ļ�Ҫ˵��';
comment on column T_V_LIST_PUBLISH.wwwurl
  is '��ȡ��Ʒ�ķ���·�����磺URL��ַ�����ָ��';
comment on column T_V_LIST_PUBLISH.wapurl
  is '��ȡ��Ʒ�ķ���·�����磺URL��ַ�����ָ��';
comment on column T_V_LIST_PUBLISH.isfinish
  is '������';
comment on column T_V_LIST_PUBLISH.updatetime
  is 'ͬ������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LIST_PUBLISH
  add constraint PK_T_V_LIST_PUBLISH_PID primary key (PROGRAMID);
----------------------------------------------�����񵥷���T_V_LIST_PUBLISH ����-------------------------------------------------------------------

----------------------------------------------������Ƶ�ȵ����ݱ�T_V_HOTCONTENT_PROGRAM ��ʼ---------------------------------------------------------
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
  is '��Ƶ�ȵ����ݱ�';
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_PROGRAM.id
  is '����ID';
comment on column T_V_HOTCONTENT_PROGRAM.prdcontid
  is '��ĿID';
comment on column T_V_HOTCONTENT_PROGRAM.contentid
  is '����ID';
comment on column T_V_HOTCONTENT_PROGRAM.nodeid
  is 'ר����ĿID';
comment on column T_V_HOTCONTENT_PROGRAM.contenttype
  is '�������� ö��ֵ��1-�缯��2-�Ǿ缯��3-ֱ����4-ר��';
comment on column T_V_HOTCONTENT_PROGRAM.title
  is '���ݳ�����';
comment on column T_V_HOTCONTENT_PROGRAM.shorttitle
  is '���ݶ̱���';
comment on column T_V_HOTCONTENT_PROGRAM.description
  is '��������';
comment on column T_V_HOTCONTENT_PROGRAM.image
  is '����ͼƬ����';
comment on column T_V_HOTCONTENT_PROGRAM.lupdate
  is 'ͬ��������ʱ��';
comment on column T_V_HOTCONTENT_PROGRAM.location
  is 'λ����Ϣ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_HOTCONTENT_PROGRAM
  add constraint PK_TVHOTCONTENTPRO_ID primary key (ID);
----------------------------------------------������Ƶ�ȵ����ݱ�T_V_HOTCONTENT_PROGRAM ����---------------------------------------------------------

----------------------------------------------������Ƶ�ȵ��������к�SEQ_T_V_HOTCONTENTPRO_ID ��ʼ-----------------------------------------------------
create sequence SEQ_T_V_HOTCONTENTPRO_ID
minvalue 1
maxvalue 999999999999
start with 10
increment by 1
nocache
cycle;
----------------------------------------------������Ƶ�ȵ��������к�SEQ_T_V_HOTCONTENTPRO_ID ��ʼ-----------------------------------------------------

----------------------------------------------��T_V_LIVE����������ֶ� ��ʼ--------------------------------------------------------------------------
alter table T_V_LIVE add ranking  VARCHAR2(21);
comment on column T_V_LIVE.ranking
  is '���';
alter table T_V_LIVE add PlayShellID varchar2(21);
alter table T_V_LIVE add PlayVodID varchar2(21);
comment on column T_V_LIVE.PlayShellID
  is '��Ŀ��Ӧ�缯ID';
comment on column T_V_LIVE.PlayVodID
  is '��Ŀ��Ӧ�㲥����ID';
----------------------------------------------��T_V_LIVE����������ֶ�  ����--------------------------------------------------------------------------

  ----------------------------------------------��t_v_dprogram�����5���ֶ� ��ʼ---------------------------------------------------------------------
alter table t_v_dprogram add authorizationWay NUMBER(2);
alter table t_v_dprogram add miguPublish NUMBER(2);
alter table t_v_dprogram add bcLicense NUMBER(2);
alter table t_v_dprogram add Influence NUMBER(2);
alter table t_v_dprogram add oriPublish NUMBER(2);

comment on column t_v_dprogram.authorizationWay is '��Ȩ��ʽ ö��ֵ��1-��Ƭ��Ȩ��2-������Ȩ';
comment on column t_v_dprogram.miguPublish is '�乾���� ö��ֵ��1-�Ƕ��ҷ��׷���2-���һ��׷�';
comment on column t_v_dprogram.bcLicense is '������� ö��ֵ��1-��Ժ�߷ǵ���̨��2-Ժ�߻����̨';
comment on column t_v_dprogram.Influence is '����Ӱ�� ö��ֵ��1-���Ȳ���2-�Ȳ�';
comment on column t_v_dprogram.oriPublish is 'ԭ������ ö��ֵ��1-������ֱǩ��2-������3-��ԭ������';
----------------------------------------------��t_v_dprogram�����5���ֶ�  ����----------------------------------------------------------------------

----------------------------------------------�½���Ƶ�����걨λ�ñ�T_V_HOTCONTENT_LOCATION  ��ʼ-----------------------------------------------------
create table T_V_HOTCONTENT_LOCATION
(
  locationid VARCHAR2(20)
);
-- Add comments to the table 
comment on table T_V_HOTCONTENT_LOCATION
  is '��Ƶ�����걨λ�ñ�';
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_LOCATION.locationid
  is '�걨λ��ID';

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

----------------------------------------------�½���Ƶ�����걨λ�ñ�T_V_HOTCONTENT_LOCATION  ����-----------------------------------------------------

----------------------------------------------��t_v_dprogram�и���4���ֶ�����  ��ʼ-------------------------------------------------------------------
alter table t_v_dprogram add subserial_ids_1 Clob;
update t_v_dprogram set subserial_ids_1 = subserial_ids;
alter table t_v_dprogram drop column subserial_ids;
alter table t_v_dprogram rename column subserial_ids_1 to subserial_ids;

alter table t_v_dprogram modify Area VARCHAR2(50);
alter table t_v_dprogram modify Terminal VARCHAR2(50);
alter table t_v_dprogram modify SerialCount number(10);

----------------------------------------------��t_v_dprogram�и���4���ֶ�����  ����--------------------------------------------------------------------

----------------------------------------------��t_v_PRODUCT�и���1���ֶ�����  ��ʼ-------------------------------------------------------------------
alter table t_v_PRODUCT modify fee VARCHAR2(10);
----------------------------------------------��t_v_PRODUCT�и���1���ֶ�����  ��ʼ-------------------------------------------------------------------
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.099_SSMS','MM4.0.0.0.109_SSMS');

------ �����������������ϵͳ ---------
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (83, 'SELECT CHANNELSID,CHANNELID FROM T_OPEN_CHANNEL_MO', '	��������ͻ���������Ϣͬ��', '2', 0, '0x1F', null, 2, 'i-channels-mo_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (84, 'SELECT CHANNELSID,CHANNELID FROM T_OPEN_OPERATION_CHANNEL', '�������뿪����Ӫ������Ϣͬ��', '2', 0, '0x1F', null, 2, 'i-channels-operation_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (85, 'SELECT CHANNELSID,CATEGORYID FROM T_OPEN_CHANNELS_CATEGORY', '�������������Ϣͬ��', '2', 0, '0x1F', null, 2, 'i-channels-category_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (86, 'SELECT A.CHANNELSID, D.CONTENTID FROM T_OPEN_CHANNELS_CATEGORY A,T_R_CATEGORY B,T_R_REFERENCE C,T_R_GCONTENT D WHERE A.CATEGORYID = B.CATEGORYID AND B.CATEGORYID = C.CATEGORYID AND C.REFNODEID = D.ID AND C.VERIFY_STATUS=1 ', '����������Ʒ��Ϣͬ���ӿ�', '2', 0, '0x1F', null, 2, 'i-channels-content_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'GBK', '2', '1', '', 15);

----------------------
 
commit;
