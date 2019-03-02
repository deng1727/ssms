-- Create the synonym 
create or replace synonym  PPMS_V_CM_CONTENT_ZCOM_V2
  for mm_ppms.v_cm_content_zcom_v2  ;

create table v_cm_content_zcom_v2 as select * from PPMS_V_CM_CONTENT_ZCOM_v2;

-- Create table
create table T_SYNCTIME_TMP_ZCOM_V2
(
  NAME      VARCHAR2(60),
  CONTENTID VARCHAR2(12),
  LUPDDATE  DATE
);

-- Create table
create table T_LASTSYNCTIME_ZCOM_V2
(
  LASTTIME DATE not null
);

-- Create sequence 
create sequence SEQ_ZCOM_v2_ID
minvalue 1
maxvalue 99999999
start with 1000
increment by 1
nocache
cycle;

create table Z_PPS_MAGA_LS_V2
(
  ID             NUMBER not null,
  MAGA_NAME      VARCHAR2(100),
  MAGA_PERIODS   VARCHAR2(10),
  MAGA_OFFICE    VARCHAR2(50),
  MAGA_DATE      VARCHAR2(50),
  PERIOD         VARCHAR2(10),
  PRICE          NUMBER,
  CONTENTID      VARCHAR2(30),
  CHARGETYPE     VARCHAR2(2),
  UPTIME         VARCHAR2(50),
  CARTOONPIC     VARCHAR2(50),
  LOG1           VARCHAR2(50),
  LOG2           VARCHAR2(50),
  LOG3           VARCHAR2(50),
  LOG4           VARCHAR2(50),
  PARENT_ID      NUMBER,
  MAGA_FULL_NAME VARCHAR2(100),
  FULL_DEVICE_ID CLOB,
  ICPCODE        VARCHAR2(100),
  ICPSERVID      VARCHAR2(100),
  SIZES          VARCHAR2(20),
  PERFIX         VARCHAR2(50),
  PLATFORM       VARCHAR2(500),
  LUPDDATE       DATE,
  businessid     varchar2(10),
  isformal       varchar2(1),
  typename       varchar2(200)
);

-- Add comments to the columns 
comment on column Z_PPS_MAGA_LS_V2.ID
  is 'id';
comment on column Z_PPS_MAGA_LS_V2.MAGA_NAME
  is '��������';
comment on column Z_PPS_MAGA_LS_V2.MAGA_PERIODS
  is '��������';
comment on column Z_PPS_MAGA_LS_V2.MAGA_OFFICE
  is '����';
comment on column Z_PPS_MAGA_LS_V2.MAGA_DATE
  is '��������';
comment on column Z_PPS_MAGA_LS_V2.PERIOD
  is '��������';
comment on column Z_PPS_MAGA_LS_V2.PRICE
  is '�۸�λ:��';
comment on column Z_PPS_MAGA_LS_V2.CONTENTID
  is '����ID';
comment on column Z_PPS_MAGA_LS_V2.CHARGETYPE
  is '�Ʒ�����';
comment on column Z_PPS_MAGA_LS_V2.UPTIME
  is '�ϼ�ʱ��';
comment on column Z_PPS_MAGA_LS_V2.CARTOONPIC
  is 'Ԥ��ͼURL';
comment on column Z_PPS_MAGA_LS_V2.LOG1
  is 'log1';
comment on column Z_PPS_MAGA_LS_V2.LOG2
  is 'log2';
comment on column Z_PPS_MAGA_LS_V2.LOG3
  is 'log3';
comment on column Z_PPS_MAGA_LS_V2.LOG4
  is 'log4';
comment on column Z_PPS_MAGA_LS_V2.PARENT_ID
  is '��ID';
comment on column Z_PPS_MAGA_LS_V2.MAGA_FULL_NAME
  is 'ȫ��';
comment on column Z_PPS_MAGA_LS_V2.SIZES
  is '��С (��λ��)';
comment on column Z_PPS_MAGA_LS_V2.PERFIX
  is '�����׺��������,55.sis��';
comment on column Z_PPS_MAGA_LS_V2.PLATFORM
  is 'Ӧ������ƽ̨(kjava,s60)';
comment on column Z_PPS_MAGA_LS_V2.businessid
  is 'ҵ���־ID';
comment on column Z_PPS_MAGA_LS_V2.isformal
  is '0��Ԥ���棬1����ʽ��';
comment on column Z_PPS_MAGA_LS_V2.typename
  is '���������ʱ��Ů�ԣ����Խ��㣬�������Σ� IT���룬  �ƾ���ƣ����ְ��ԣ�����ǰ�ߣ�����';
alter table Z_PPS_MAGA_LS_V2
  add primary key (ID);

alter table Z_PPS_MAGA add KEYLETTER VARCHAR2(200);
comment on column Z_PPS_MAGA.KEYLETTER
  is '����ƴ��';

-- Add/modify columns 
alter table Z_PPS_MAGA_LS_V2 add typeid VARCHAR2(10);
-- Add comments to the columns 
comment on column Z_PPS_MAGA_LS_V2.typeid
  is '����id';

-- Add/modify columns 
alter table T_R_GCONTENT add match_deviceid clob;
-- Add comments to the columns 
comment on column T_R_GCONTENT.match_deviceid
  is 'ģ������deviceid';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.085_SSMS','MM1.0.2.090_SSMS');
commit;