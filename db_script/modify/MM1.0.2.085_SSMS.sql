

-- Create table
create table T_R_COLORRING
(
  ID                VARCHAR2(30),
  NAME              VARCHAR2(300),
  TONENAMELETTER    VARCHAR2(10),
  SINGER            VARCHAR2(200),
  SINGERLETTER      VARCHAR2(10),
  INTRODUCTION      VARCHAR2(300),
  PRICE             VARCHAR2(10),
  LUPDDATE          VARCHAR2(30),
  DOWNLOADTIMES     NUMBER(10),
  SETTIMES          NUMBER(10),
  AUDITIONURL       VARCHAR2(400),
  TONEBIGTYPE       VARCHAR2(30),
  CATENAME          VARCHAR2(30),
  EXPIRE            VARCHAR2(20),
  CREATEDATE        VARCHAR2(30),
  MARKETDATE        VARCHAR2(30),
  AVERAGEMARK       NUMBER(15),
  CONTENTID         VARCHAR2(30),
  CLIENTAUDITIONURL VARCHAR2(400)
);
alter table T_R_COLORRING
  add constraint PK_T_R_COLORTTING primary key (ID)
  using index ;

-- Add comments to the columns 
comment on column T_R_COLORRING.ID
  is '����ID';
comment on column T_R_COLORRING.NAME
  is '��������';
comment on column T_R_COLORRING.TONENAMELETTER
  is '�������Ƽ�������ĸ';
comment on column T_R_COLORRING.SINGER
  is '������';
comment on column T_R_COLORRING.SINGERLETTER
  is '�������Ƽ�������ĸ';
comment on column T_R_COLORRING.INTRODUCTION
  is '����';
comment on column T_R_COLORRING.PRICE
  is '�۸�';
comment on column T_R_COLORRING.LUPDDATE
  is '������ʱ��';
comment on column T_R_COLORRING.DOWNLOADTIMES
  is '���������صĴ���';
comment on column T_R_COLORRING.SETTIMES
  is '���������ô���';
comment on column T_R_COLORRING.AUDITIONURL
  is '��ȡ������ַ';
comment on column T_R_COLORRING.TONEBIGTYPE
  is '��������';
comment on column T_R_COLORRING.CATENAME
  is '�����������';
comment on column T_R_COLORRING.EXPIRE
  is '���ݵ���Ч����';
comment on column T_R_COLORRING.CREATEDATE
  is '����ʱ��';
comment on column T_R_COLORRING.MARKETDATE
  is '����ʱ��';
comment on column T_R_COLORRING.AVERAGEMARK
  is '��Ӧ���塢ȫ���Ĳ���ʱ�������룩';
comment on column T_R_COLORRING.CONTENTID
  is '����ID';
comment on column T_R_COLORRING.CLIENTAUDITIONURL
  is '��ȡ�ն�������ַ';



-- Add/modify columns 
alter table T_R_GCONTENT add FULLDEVICENAME clob;
-- Add comments to the columns 
comment on column T_R_GCONTENT.FULLDEVICENAME
  is 'ȫ��������Ϣ';





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.075_SSMS','MM1.0.2.085_SSMS');
commit;