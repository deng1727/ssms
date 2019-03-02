create table t_gametype(
id number(12),
name varchar2(40),
mmid number(12),
mmname varchar2(40),
primary key(id)
);

-- Create table
create table T_GAME_CONTENT
(
  CONTENTCODE     VARCHAR2(32) not null,
  NAME            VARCHAR2(400) not null,
  SHORTNAME       VARCHAR2(400),
  DESCR           VARCHAR2(1000) not null,
  CPID            VARCHAR2(16) not null,
  CPNAME          VARCHAR2(200) not null,
  OPERA           VARCHAR2(1000),
  EFFECTIVEDATE   VARCHAR2(14),
  INVALIDDATE     VARCHAR2(14),
  LOGO            VARCHAR2(200) not null,
  DEVICEUA        VARCHAR2(400),
  GAMETYPEID      NUMBER(4) not null,
  FILESIZE        NUMBER(12),
  CONTENTTYPE     VARCHAR2(14) not null,
  PKGTYPE         NUMBER(1),
  GAMEPOOL        NUMBER(1),
  FREEDOWNLOADNUM NUMBER(9),
  CHARGETYPE      NUMBER(2) not null,
  SCALE           VARCHAR2(20),
  LUPDATE         DATE default sysdate,
  primary key(CONTENTCODE)
);
-- Add comments to the columns 
comment on column T_GAME_CONTENT.CONTENTCODE
  is '���ݴ���/�ײͰ�';
comment on column T_GAME_CONTENT.NAME
  is '��������';
comment on column T_GAME_CONTENT.SHORTNAME
  is '���ݼ��';
comment on column T_GAME_CONTENT.DESCR
  is '���ݼ��';
comment on column T_GAME_CONTENT.CPID
  is 'CP����';
comment on column T_GAME_CONTENT.CPNAME
  is 'CP����';
comment on column T_GAME_CONTENT.OPERA
  is '�������';
comment on column T_GAME_CONTENT.EFFECTIVEDATE
  is '��Ʒ��Ч����';
comment on column T_GAME_CONTENT.INVALIDDATE
  is '��ƷʧЧ����';
comment on column T_GAME_CONTENT.LOGO
  is '��ƷLOGO';
comment on column T_GAME_CONTENT.DEVICEUA
  is '֧�ֵ��ն�����';
comment on column T_GAME_CONTENT.GAMETYPEID
  is '��Ϸ�����ʶ';
comment on column T_GAME_CONTENT.FILESIZE
  is '�ļ���С';
comment on column T_GAME_CONTENT.CONTENTTYPE
  is '��������';
comment on column T_GAME_CONTENT.PKGTYPE
  is '�ײ�ҵ������';
comment on column T_GAME_CONTENT.GAMEPOOL
  is '�Ƿ�������Ϸ��';
comment on column T_GAME_CONTENT.FREEDOWNLOADNUM
  is '�û�������ش���';
comment on column T_GAME_CONTENT.CHARGETYPE
  is '��������';
comment on column T_GAME_CONTENT.SCALE
  is '�ֳɱ���';
comment on column T_GAME_CONTENT.LUPDATE
  is '���ʱ��';
  
  
create table T_GAME_TW_NEW
(
  CPID             VARCHAR2(16) not null,
  CPNAME           VARCHAR2(200) not null,
  CPSERVICEID      VARCHAR2(16) not null,
  SERVICENAME      VARCHAR2(400) not null,
  SERVICESHORTNAME VARCHAR2(400) not null,
  SERVICEDESC      VARCHAR2(1000),
  OPERATIONDESC    VARCHAR2(1000),
  SERVICETYPE      NUMBER(2) not null,
  SERVICEPAYTYPE   NUMBER(2) not null,
  SERVICESTARTDATE VARCHAR2(10) not null,
  SERVICEENDDATE   VARCHAR2(10) not null,
  SERVICESTATUS    NUMBER(1) not null,
  FEE              NUMBER(10) not null,
  SERVICEFEEDESC   VARCHAR2(400) not null,
  SERVICE_URL      VARCHAR2(400) not null,
  SERVICEFEETYPE   NUMBER(2) not null,
  GAMETYPE         NUMBER(4) not null,
  MMGAMETYPE       VARCHAR2(100) not null,
  GAMETYPE_DESC    VARCHAR2(40) not null,
  SERVICEFLAG      NUMBER(1),
  PTYPEID          NUMBER(2) not null,
  primary key(CPSERVICEID)
);
-- Add comments to the columns 
comment on column T_GAME_TW_NEW.CPID
  is 'CP�ĺ�������';
comment on column T_GAME_TW_NEW.CPNAME
  is 'CP����';
comment on column T_GAME_TW_NEW.CPSERVICEID
  is '��Ʒ��ҵ�����';
comment on column T_GAME_TW_NEW.SERVICENAME
  is '��Ʒ����';
comment on column T_GAME_TW_NEW.SERVICESHORTNAME
  is '��Ʒ���';
comment on column T_GAME_TW_NEW.SERVICEDESC
  is '��Ʒ���';
comment on column T_GAME_TW_NEW.OPERATIONDESC
  is '�������';
comment on column T_GAME_TW_NEW.SERVICETYPE
  is 'ҵ������     1:�ͻ��˵���,2:�ͻ�������,3:WAP����,4:WAP����';
comment on column T_GAME_TW_NEW.SERVICEPAYTYPE
  is '֧����ʽ      1:����,2:����';
comment on column T_GAME_TW_NEW.SERVICESTARTDATE
  is '��Ʒ��Ч����   ҵ����Ч���ڣ���ʽ"yyyy-mm-dd"';
comment on column T_GAME_TW_NEW.SERVICEENDDATE
  is '��ƷʧЧ����   ����ҵ����ʧЧ���ں��Զ���Ϊ����ҵ��"yyyy-mm-dd"';
comment on column T_GAME_TW_NEW.SERVICESTATUS
  is 'ҵ��״̬    1:���ϴ���Ϸ����,2:������,3:����,4:��ͣ,5:������,0:������';
comment on column T_GAME_TW_NEW.FEE
  is '�ʷ�(��)';
comment on column T_GAME_TW_NEW.SERVICEFEEDESC
  is '�ʷ�����';
comment on column T_GAME_TW_NEW.SERVICE_URL
  is '���URL������url��  ͼ����Ϸ�����ӵ�ַ��';
comment on column T_GAME_TW_NEW.SERVICEFEETYPE
  is '�Ʒѷ�ʽ   0:���,
1:����,
2:�ͻ��˵�������(�״�ʹ��ʱ����),
3:���߰���,
4:�ײͰ���,
5:����ʱ���μƷ�,
6:Wap���Σ�ÿ��ʹ�ö��Ʒѣ�,
7:�ͻ��˵������żƷ�,
8:�ͻ������ζ��żƷѡ�
ֻ��0��3��6�ģ�
0�����
3��ͼ������
5��ͼ�ĵ���
';
comment on column T_GAME_TW_NEW.GAMETYPE
  is '��Ϸ����   ��Ϸ����Ŀ�ѡֵֻ�������µ�����һ����
MMORPG(����)
����
����ð��
����ð��
����
����
����
����
����
��ɫ����
���
����
��������
';
comment on column T_GAME_TW_NEW.GAMETYPE_DESC
  is '��Ϸ��������';
comment on column T_GAME_TW_NEW.SERVICEFLAG
  is '0:��ͨҵ��,1:�ײ���ҵ��';
comment on column T_GAME_TW_NEW.PTYPEID
  is 'ҵ���ƹ㷽ʽ';




create table T_GAME_PKG
(
  SERVICECODE     VARCHAR2(16) not null,
  PKGNAME         VARCHAR2(64) not null,
  PKGDESC         VARCHAR2(512) not null,
  CPNAME          VARCHAR2(64),
  FEE             NUMBER not null,
  PKGURL          VARCHAR2(300) not null,
  PICURL1         VARCHAR2(255) not null,
  PICURL2         VARCHAR2(255) not null,
  PICURL3         VARCHAR2(255) not null,
  PICURL4         VARCHAR2(255) not null,
  UPDATETIME      DATE default sysdate,
  PROVINCECTROL   VARCHAR2(50) not null,
  PKGTYPE         NUMBER(1),
  GAMEPOOL        NUMBER(1),
  FREEDOWNLOADNUM NUMBER(9),
  primary key(SERVICECODE)
)
-- Add comments to the columns 
comment on column T_GAME_PKG.SERVICECODE
  is 'ҵ�����';
comment on column T_GAME_PKG.PKGNAME
  is '��Ϸ������';
comment on column T_GAME_PKG.PKGDESC
  is '��Ϸ������';
comment on column T_GAME_PKG.CPNAME
  is '������';
comment on column T_GAME_PKG.FEE
  is '����';
comment on column T_GAME_PKG.PKGURL
  is '��Ϸ�����URL';
comment on column T_GAME_PKG.PICURL1
  is '��� 30x30 picture1';
comment on column T_GAME_PKG.PICURL2
  is '��� 34x34 picture2';
comment on column T_GAME_PKG.PICURL3
  is '��� 50x50 picture3';
comment on column T_GAME_PKG.PICURL4
  is '��� 65x65 picture4';
comment on column T_GAME_PKG.PROVINCECTROL
  is '�ֳɱ���';
comment on column T_GAME_PKG.PKGTYPE
  is '�ײ�ҵ������ 1����Ϸ���
2��������
3��������
';
comment on column T_GAME_PKG.GAMEPOOL
  is '����ײͰ� 0:����
1��������
';
comment on column T_GAME_PKG.FREEDOWNLOADNUM
  is '����������Ϸ���е�ҵ��������ش���';
  
create table T_GAME_PKG_REF
(
  PKGID       VARCHAR2(32) not null,
  SERVICECODE VARCHAR2(16) not null,
  primary key(PKGID,SERVICECODE)
);

comment on column T_GAME_PKG_REF.PKGID
  is '����ʶID';
comment on column T_GAME_PKG_REF.SERVICECODE
  is '�����������ID';
  
create table T_GAME_SERVICE_NEW
(
  ICPCODE     VARCHAR2(16),
  SPNAME      VARCHAR2(200) not null,
  ICPSERVID   VARCHAR2(16) not null,
  SERVNAME    VARCHAR2(50) not null,
  SERVDESC    VARCHAR2(200),
  CHARGETYPE  VARCHAR2(2),
  CHARGEDESC  VARCHAR2(200),
  MOBILEPRICE NUMBER(8),
  LUPDDATE    DATE not null,
  SERVTYPE    NUMBER(2) not null,
  SERVFLAG    NUMBER(1),
  PTYPEID     NUMBER(2) not null,
  CONTENTID   VARCHAR2(30),
  primary key(ICPSERVID)
);

comment on column T_GAME_SERVICE_NEW.ICPCODE
  is 'CP�ĺ�������';
comment on column T_GAME_SERVICE_NEW.SPNAME
  is 'CP����';
comment on column T_GAME_SERVICE_NEW.ICPSERVID
  is '��Ʒ��ҵ�����';
comment on column T_GAME_SERVICE_NEW.SERVNAME
  is '��Ʒ����';
comment on column T_GAME_SERVICE_NEW.SERVDESC
  is '��Ʒ���';
comment on column T_GAME_SERVICE_NEW.CHARGETYPE
  is '�Ʒ�����

1�����
2������/�����Ʒ�
3�����¼Ʒ�
5�����μƷ�
7������Ʒ�
9������Ŀ���¼Ʒ�
';
comment on column T_GAME_SERVICE_NEW.CHARGEDESC
  is '�ʷ�����  1 ����۸�2 ���ڼ۸�';
comment on column T_GAME_SERVICE_NEW.MOBILEPRICE
  is '��λ:�� ����ҵ����������';
comment on column T_GAME_SERVICE_NEW.LUPDDATE
  is 'ҵ��������ʱ��';
comment on column T_GAME_SERVICE_NEW.SERVTYPE
  is 'ҵ�����͡�1:�ͻ��˵���,2:�ͻ�������,3:WAP����,4:WAP����
ֻ��1�ͻ��˵����ġ�
';
comment on column T_GAME_SERVICE_NEW.SERVFLAG
  is 'ҵ���ʶ��0:��ͨҵ��,1:�ײ���ҵ��';
comment on column T_GAME_SERVICE_NEW.PTYPEID
  is '֧����ʽ��1:���� 2:����';
comment on column T_GAME_SERVICE_NEW.CONTENTID
  is '����id';
  

-- Create table
create table T_GAMESTOP
(
  SERVICECODE  VARCHAR2(16) not null,
  PKGID        VARCHAR2(32),
  CONTENTCODE  VARCHAR2(32) not null,
  ISSTOP       NUMBER(1) not null,
  PROVINCEID   VARCHAR2(12) not null,
  PROVINCENAME VARCHAR2(21) not null,
  STOPTIME     VARCHAR2(14),
  OPERATETYPE  NUMBER(2) not null,
  LUPDATE      DATE default sysdate not null
);
-- Add comments to the columns 
comment on column t_gamestop.serviceCode
  is 'ҵ�����/�ײͰ�ID';
comment on column t_gamestop.pkgid
  is '������';
comment on column t_gamestop.contentCode
  is '���ݴ���';
comment on column t_gamestop.isStop
  is '�Ƿ���ͣʡ������';
comment on column t_gamestop.provinceId
  is '����ͣʡ�ݵ�ID';
comment on column t_gamestop.provinceName
  is '����ͣʡ������';
comment on column t_gamestop.stopTime
  is '��ͣʱ��';
comment on column t_gamestop.operateType
  is '��������';
comment on column t_gamestop.lupdate
  is '������ʱ��';
  
  
alter table t_cb_content modify  INFO_PIC varchar2(800);


-- Add/modify columns 
alter table T_RB_CATEGORY_NEW add BUSINESSTIME VARCHAR2(14);
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.BUSINESSTIME
  is '����ʱ��';

-- Add/modify columns 
create table T_RB_COMMENT_NEW
(
  COMMENTID   VARCHAR2(60) not null,
  NICKNAME    VARCHAR2(60) not null,
  USERID      VARCHAR2(11) not null,
  COMMENTDESC VARCHAR2(4000) not null,
  TIME        VARCHAR2(14) not null
);

comment on column T_RB_COMMENT_NEW.COMMENTID
  is '����ID';
comment on column T_RB_COMMENT_NEW.NICKNAME
  is '�ǳ�';
comment on column T_RB_COMMENT_NEW.USERID
  is '�û���ʶ ������86�ֻ�����';
comment on column T_RB_COMMENT_NEW.COMMENTDESC
  is '��������';
comment on column T_RB_COMMENT_NEW.TIME
  is 'ʱ�� ��ʽ��YYYYMMDDHH24MISS';

alter table T_RB_COMMENT_NEW
  add constraint PK_COMMENT_ID primary key (COMMENTID);

-- Add/modify columns 
alter table T_RB_AUTHOR_NEW modify ISORIGINAL null;
alter table T_RB_AUTHOR_NEW modify ISPUBLISH null;

-- Add/modify columns 
alter table T_RB_BOOK_NEW modify LONGRECOMMEND null;



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.089_SSMS','MM1.1.1.095_SSMS');


commit;