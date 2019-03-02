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
  is '�����ʶ';
comment on column T_RB_BOOKBAG_AREA_NEW.BAGNAME
  is '�������';
comment on column T_RB_BOOKBAG_AREA_NEW.VIEWBAGNAME
  is '���չʾ����';
comment on column T_RB_BOOKBAG_AREA_NEW.PROVINCE
  is '��������ʡ��';
comment on column T_RB_BOOKBAG_AREA_NEW.CITY
  is '�����������';
comment on column T_RB_BOOKBAG_AREA_NEW.STATUS
  is '��ǰ����״̬';
comment on column T_RB_BOOKBAG_AREA_NEW.LUPDATE
  is '���ݵ���ʱ��';



-- Add/modify columns 
alter table T_RB_REFERENCE_NEW add PROVINCE varchar2(256);
alter table T_RB_REFERENCE_NEW add CITY varchar2(4000);
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.PROVINCE
  is '���е�ʡ��ID�Էֺ�;������000����ȫ��';
comment on column T_RB_REFERENCE_NEW.CITY
  is '���еĳ���ID�Էֺ�;����';

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
  is 'ʡ��id';
comment on column T_RB_AREA_NEW.PROVINCENAME
  is 'ʡ������';
comment on column T_RB_AREA_NEW.CITYID
  is '����id';
comment on column T_RB_AREA_NEW.CITYNAME
  is '��������';
comment on column T_RB_AREA_NEW.MMPROVINCEID
  is 'MMʡ��id';
comment on column T_RB_AREA_NEW.MMCITYID
  is 'MM����id';

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
  is '��ҵ����';
comment on column T_R_HTCDOWNLOAD.APPID
  is 'ҵ��ƽ̨Ӧ�ñ���';
comment on column T_R_HTCDOWNLOAD.CONTENTID
  is 'MMӦ�ô���';
comment on column T_R_HTCDOWNLOAD.DOWNCOUNT
  is '���ش���';
comment on column T_R_HTCDOWNLOAD.LUPDATE
  is '����޸�ʱ��';




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.065_SSMS','MM2.0.0.0.069_SSMS');

commit;