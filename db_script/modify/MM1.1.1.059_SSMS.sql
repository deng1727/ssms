create table T_CB_ADAPTER
(
  ID        VARCHAR2(60),
  CHAPTERID VARCHAR2(60),
  GROUPS    VARCHAR2(4000),
  FILE_SIZE NUMBER(10),
  USE_TYPE  NUMBER(2),
  CLEAR     VARCHAR2(1),
  FLOW_TIME DATE default sysdate,
  TYPE      VARCHAR2(5)
);
-- Add comments to the table 
comment on table T_CB_ADAPTER
  is '���ֱ�';
-- Add comments to the columns 
comment on column T_CB_ADAPTER.ID
  is '���ֱ�ʶ';
comment on column T_CB_ADAPTER.CHAPTERID
  is '�½ڱ�ʶ';
comment on column T_CB_ADAPTER.GROUPS
  is '�������ʶ';
comment on column T_CB_ADAPTER.FILE_SIZE
  is '�ļ���С';
comment on column T_CB_ADAPTER.USE_TYPE
  is 'ʹ�÷�ʽ';
comment on column T_CB_ADAPTER.CLEAR
  is '��Ƶ�����ȣ�1:����
2:����
3:����
ֻ����Ƶ�����и�ֵ
��';
comment on column T_CB_ADAPTER.FLOW_TIME
  is '��ˮʱ�䣨���ܸ����ֶΣ�';
comment on column T_CB_ADAPTER.TYPE
  is '��������';

  
  
-- Create table
create table T_CB_CATEGORY
(
  CATEGORYID          VARCHAR2(30) not null,
  CATEGORYNAME        VARCHAR2(200) not null,
  CATEGORYVALUE       VARCHAR2(50),
  PARENTCATEGORYID    VARCHAR2(30),
  TYPE                VARCHAR2(10),
  PICTURE             VARCHAR2(512),
  DELFLAG             VARCHAR2(2),
  CATEGORYDESC        VARCHAR2(1000),
  SORTID              NUMBER(8),
  CREATETIME          DATE default sysdate,
  LUPDATE             DATE default sysdate,
  PARENTCATEGORYVALUE VARCHAR2(30),
  MO_REFERENCE_NUM    NUMBER(10),
  WAP_REFERENCE_NUM   NUMBER(10),
  LOGO1               VARCHAR2(512),
  LOGO2               VARCHAR2(512),
  LOGO3               VARCHAR2(512),
  primary key(CATEGORYID)
);

-- Add comments to the table 
comment on table T_CB_CATEGORY
  is '���ܱ�';
-- Add comments to the columns 
comment on column T_CB_CATEGORY.CATEGORYID
  is '����ID';
comment on column T_CB_CATEGORY.CATEGORYNAME
  is '��������';
comment on column T_CB_CATEGORY.CATEGORYVALUE
  is '�������ƶ�Ӧ��VALUE';
comment on column T_CB_CATEGORY.PARENTCATEGORYID
  is '������ID';
comment on column T_CB_CATEGORY.TYPE
  is '���ܵ�ҵ�����֣��׷���FIRST�����а�RANK��Ʒ�Ƶ꣺BRAND��';
comment on column T_CB_CATEGORY.PICTURE
  is '����ͼƬ';
comment on column T_CB_CATEGORY.DELFLAG
  is '�Ƿ�ɾ����0��δɾ����1��ɾ����';
comment on column T_CB_CATEGORY.CATEGORYDESC
  is '��������';
comment on column T_CB_CATEGORY.SORTID
  is '����';
comment on column T_CB_CATEGORY.CREATETIME
  is '����ʱ��';
comment on column T_CB_CATEGORY.LUPDATE
  is '������ʱ��';
comment on column T_CB_CATEGORY.PARENTCATEGORYVALUE
  is '���������ƶ�Ӧ��VALUE';
comment on column T_CB_CATEGORY.MO_REFERENCE_NUM
  is '�û����¹ҵ���Ʒ�������ͻ��ˣ�';
comment on column T_CB_CATEGORY.WAP_REFERENCE_NUM
  is '�û����¹ҵ���Ʒ������WAP��';
comment on column T_CB_CATEGORY.LOGO1
  is 'Ʒ�ƹݵ�logo����ͼ
';
comment on column T_CB_CATEGORY.LOGO2
  is 'Ʒ�ƹݵ�logo����ͼ
';
comment on column T_CB_CATEGORY.LOGO3
  is 'Ʒ�ƹݵ�logo��Сͼ
';


-- Create table
create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(512),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5)
);
-- Add comments to the table 
comment on table T_CB_CHAPTER
  is '�½ڱ�';
-- Add comments to the columns 
comment on column T_CB_CHAPTER.CHAPTERID
  is '�½ڣ���������ID';
comment on column T_CB_CHAPTER.CONTENTID
  is '�������ݱ�ʶ';
comment on column T_CB_CHAPTER.NAME
  is '�½�����';
comment on column T_CB_CHAPTER.DESCRIPTION
  is '�½�����';
comment on column T_CB_CHAPTER.FEE
  is '�ʷѣ��ʷ�����  ��λ�� �֣�';
comment on column T_CB_CHAPTER.UPDATE_FLAG
  is '���±�־��0: ���䣻1�������� 2���޸ģ�';
comment on column T_CB_CHAPTER.TYPE
  is '��������';
  
  
create table T_CB_CONTENT
(
  ID            VARCHAR2(60) not null,
  NAME          VARCHAR2(60) not null,
  DESCRIPTION   VARCHAR2(4000),
  PROVIDER      VARCHAR2(60),
  PROVIDER_TYPE VARCHAR2(60),
  AUTHODID      VARCHAR2(60),
  TYPE          VARCHAR2(5),
  KEYWORDS      VARCHAR2(4000),
  EXPIRETIME    VARCHAR2(14),
  FEE           NUMBER(10),
  LOCATION      VARCHAR2(5),
  FIRST         VARCHAR2(5),
  URL1          VARCHAR2(512),
  URL2          VARCHAR2(512),
  URL3          VARCHAR2(512),
  URL4          VARCHAR2(512),
  INFO_CONTENT  VARCHAR2(4000),
  INFO_PIC      VARCHAR2(512),
  INFO_SOURCE   VARCHAR2(30),
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
  LANGUAGE      VARCHAR2(20),
  YEAR          VARCHAR2(4),
  STATUS        VARCHAR2(4),
  CHAPTER_TYPE  VARCHAR2(4),
  PORTAL        VARCHAR2(1),
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
  primary key(id)
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
  
-- Create table
create table T_CB_CP
(
  CPID    VARCHAR2(60) not null,
  CPNAME  VARCHAR2(60) not null
);
-- Add comments to the table 
comment on table T_CB_CP
  is 'CP��';
-- Add comments to the columns 
comment on column T_CB_CP.CPID
  is 'CPID';
comment on column T_CB_CP.CPNAME
  is 'CP����';
  
-- Create table
create table T_CB_DEVICEGROUPITEM
(
  GROUPID   VARCHAR2(60),
  DEVICEID  VARCHAR2(60),
  FLOW_TIME DATE
);

create table T_CB_MASTER
(
  MASTERID VARCHAR2(50) not null,
  NAME     VARCHAR2(50),
  SORTID   NUMBER(5)
);
-- Add comments to the table 
comment on table T_CB_MASTER
  is '���ұ�';
-- Add comments to the columns 
comment on column T_CB_MASTER.MASTERID
  is '����Ŀ¼��ʶ';
comment on column T_CB_MASTER.NAME
  is '����Ŀ¼����';
comment on column T_CB_MASTER.SORTID
  is '�������';

-- Create table
create table T_CB_REFERENCE
(
  ID         NUMBER(10) not null,
  CONTENTID  VARCHAR2(50),
  CATEGORYID VARCHAR2(20),
  SORTID     NUMBER(8),
  FLOW_TIME  DATE default sysdate,
  TYPE       VARCHAR2(10),
  PORTAL     VARCHAR2(1)
);
-- Add comments to the table 
comment on table T_CB_REFERENCE
  is '��Ʒ��';
-- Add comments to the columns 
comment on column T_CB_REFERENCE.ID
  is '�������Զ����ɣ�';
comment on column T_CB_REFERENCE.CONTENTID
  is '����ID';
comment on column T_CB_REFERENCE.CATEGORYID
  is '����ID';
comment on column T_CB_REFERENCE.SORTID
  is '����';
comment on column T_CB_REFERENCE.FLOW_TIME
  is '��ˮʱ�䣬Ϊ������������ݻ�����ǰ����';
comment on column T_CB_REFERENCE.TYPE
  is '�������Ǹ��Զ���񵥵���Ʒ��FIRST,RANK,BRAND,SYSTEM��';
comment on column T_CB_REFERENCE.PORTAL
  is '1 �ͻ���
2 WAP�Ż�
3 ����
';



----�����������ݽ��뿪ʼ
create sequence SEQ_CB_CATEGORY_ID
minvalue 100000001
maxvalue 999999999
start with 100000060
increment by 1
nocache
cycle;

-- Create sequence 
create sequence SEQ_CB_ID
minvalue 1
maxvalue 999999999
start with 1
increment by 1
nocache
cycle;






insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000001', '����', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000002', '����', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000003', '����', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000004', '��Ѷ', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000005', '�׷�', null, null, 'FIRST', null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000006', '���а�', null, null, 'RANK', null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000007', 'Ʒ�ƹ�', '0', null, 'BRAND', null, '0', null, null, null, null, 'no_use...', null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000008', 'ר��', '1', null, 'TOPIC', null, '0', null, null, null, null, 'no_user...', null, null, null, null, null);



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000020', '��Ц', '100000001', null, null, '0', null, null, null, null, '15000');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000021', 'ð��', '100000001', null, null, '0', null, null, null, null, '15001');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000022', '�˶�', '100000001', null, null, '0', null, null, null, null, '15002');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000023', '���', '100000001', null, null, '0', null, null, null, null, '15003');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000024', '�ƻ�', '100000001', null, null, '0', null, null, null, null, '15004');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000025', '����', '100000001', null, null, '0', null, null, null, null, '15005');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000026', '��ѧ', '100000001', null, null, '0', null, null, null, null, '15006');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000027', '����', '100000001', null, null, '0', null, null, null, null, '15007');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000028', 'ʱѶ', '100000001', null, null, '0', null, null, null, null, '15009');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000029', '������', '100000001', null, null, '0', null, null, null, null, '15011');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000031', '����', '100000001', null, null, '0', null, null, null, null, '16017');



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000040', '��Ц', '100000002', null, null, '0', null, null, null, null, '15400');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000041', 'ð��', '100000002', null, null, '0', null, null, null, null, '15401');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000042', '����', '100000002', null, null, '0', null, null, null, null, '15403');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000043', '����', '100000002', null, null, '0', null, null, null, null, '15404');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000044', '����', '100000002', null, null, '0', null, null, null, null, '15405');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000045', '��ѧ', '100000002', null, null, '0', null, null, null, null, '15406');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000046', '����', '100000002', null, null, '0', null, null, null, null, '15407');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000047', '�·�', '100000002', null, null, '0', null, null, null, null, '15409');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000048', '����', '100000002', null, null, '0', null, null, null, null, '15410');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000049', 'У԰', '100000002', null, null, '0', null, null, null, null, '15411');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000050', '��־', '100000002', null, null, '0', null, null, null, null, '15412');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000051', '��Ů', '100000002', null, null, '0', null, null, null, null, '15413');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000052', '����', '100000002', null, null, '0', null, null, null, null, '15414');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000053', '����', '100000002', null, null, '0', null, null, null, null, '16017');

----�����������ݽ������


------��Ƶ���ݽ��뿪ʼ

create table T_VO_VIDEO
(
  videoID    varchar2(60) not null,
  codeRateID varchar2(6) not null,
  filePath   varchar2(512) not null,
  fileSize   number(12) not null
)
;
-- Add comments to the columns 
comment on column T_VO_VIDEO.videoID
  is '��Ƶ��ʶ';
comment on column T_VO_VIDEO.codeRateID
  is '���ʱ�ʶ';
comment on column T_VO_VIDEO.filePath
  is '�����ļ���ַ';
comment on column T_VO_VIDEO.fileSize
  is '�ļ���С:��λ��kbyte';


-- Create table
create table T_VO_DEVICE
(
  deviceID     varchar2(60) not null,
  UA           varchar2(1024) not null,
  deviceName   varchar2(60) not null,
  codeRateType varchar2(60) not null
)
;
-- Add comments to the columns 
comment on column T_VO_DEVICE.deviceID
  is '���ͱ��';
comment on column T_VO_DEVICE.UA
  is 'UA';
comment on column T_VO_DEVICE.deviceName
  is '��������';
comment on column T_VO_DEVICE.codeRateType
  is '���ʸ�ʽ���飺1: H.264 2: �ڴ�';

create table T_VO_CODERATE
(
  codeRateID         varchar2(60) not null,
  canonicalName      varchar2(512) not null,
  encodeFormat       varchar2(512) not null,
  containerFormat    varchar2(512) not null,
  codeRateLevel      varchar2(512) not null,
  netType            varchar2(512) not null,
  mediaMimeType      varchar2(512) not null,
  resolutionType     varchar2(512) not null,
  fileNameConvention varchar2(512) not null,
  codecName          varchar2(512) not null,
  videoEncode        varchar2(512) not null,
  netMilieu          varchar2(512) not null,
  samplingRate       varchar2(512) not null
)
;
-- Add comments to the columns 
comment on column T_VO_CODERATE.codeRateID
  is '���ʱ�ʶ';
comment on column T_VO_CODERATE.canonicalName
  is '�����淶������:�����_H264_WLAN_400*320';
comment on column T_VO_CODERATE.encodeFormat
  is 'ý���ļ������ʽ:04-H.264/AAC';
comment on column T_VO_CODERATE.containerFormat
  is '������װ��ʽ:02-.3gp';
comment on column T_VO_CODERATE.codeRateLevel
  is '���ʵȼ�:50';
comment on column T_VO_CODERATE.netType
  is '��������:03-WLAN';
comment on column T_VO_CODERATE.mediaMimeType
  is 'ý��MIME����:02-video/3gpp';
comment on column T_VO_CODERATE.resolutionType
  is '�ֱ�������:03-QVGA 320*240';
comment on column T_VO_CODERATE.fileNameConvention
  is '�ļ������淶:UDID_12_jl.mp4';
comment on column T_VO_CODERATE.codecName
  is 'ת��������:sim12';
comment on column T_VO_CODERATE.videoEncode
  is '��Ƶ���뷽ʽ:TIVC/H.264';
comment on column T_VO_CODERATE.netMilieu
  is '���绷��:90kbps/QVGA/8~12fps/5000ms';
comment on column T_VO_CODERATE.samplingRate
  is '��Ƶ����/�ֱ���/֡��/�ؼ�֡/Levelֵ	��Ƶ���뷽ʽ/����/����/������:TIAC-H/14kbps';



-- Create table
create table T_VO_PROGRAM
(
  programID   varchar2(60) not null,
  videoID     varchar2(60) not null,
  programName varchar2(128) not null,
  nodeID      varchar2(21) not null,
  description varchar2(4000) not null,
  logoPath    varchar2(512) not null,
  timeLength  number(12) not null,
  showTime    varchar2(20) not null,
  lastUpTime  varchar2(14) not null,
  programType number(2) not null
)
;
-- Add comments to the columns 
comment on column T_VO_PROGRAM.programID
  is '��Ŀ��ʶ';
comment on column T_VO_PROGRAM.videoID
  is '��Ƶ��ʶ';
comment on column T_VO_PROGRAM.programName
  is '��Ŀ��������';
comment on column T_VO_PROGRAM.nodeID
  is '��Ŀ��ʶ';
comment on column T_VO_PROGRAM.description
  is '���';
comment on column T_VO_PROGRAM.logoPath
  is '����accessType�ṩ��ӦLOGO���Ե�ַ.ͨ��mm�ͻ��˷���ʱ��ͼƬurlֻ��ͨ��mm�ͻ��˷��ʣ���cmwap����㣬��̨������������Ч';
comment on column T_VO_PROGRAM.timeLength
  is 'ʱ��(���ɺ���תΪ��)';
comment on column T_VO_PROGRAM.showTime
  is '��ʾʱ����00��00��00';
comment on column T_VO_PROGRAM.lastUpTime
  is '����޸�ʱ�䣺YYYYMMDD';
comment on column T_VO_PROGRAM.programType
  is '��Ŀ����:1: ֱ��(ֱ������ƵԴ) 2����ֱ��';


create table T_VO_NODE
(
  nodeID       varchar2(60) not null,
  nodeName     varchar2(128) not null,
  description  varchar2(4000) not null,
  parentNodeID varchar2(60),
  logoPath     varchar2(512),
  sortID       number(19),
  productID    varchar2(1024)
)
;
-- Add comments to the columns 
comment on column T_VO_NODE.nodeID
  is '��Ŀ��ʶ';
comment on column T_VO_NODE.nodeName
  is '��Ŀ����';
comment on column T_VO_NODE.description
  is '���';
comment on column T_VO_NODE.parentNodeID
  is '����Ŀ��ʶ:һ������������Ŀ�丸��ĿIDΪ0. 1��Ʒ�� 2��ר�� 3������';
comment on column T_VO_NODE.logoPath
  is 'ͼƬ·��';
comment on column T_VO_NODE.sortID
  is '�������:������š���С�������С�������������ȡֵ��Χ��-999999��999999֮�䡣';
comment on column T_VO_NODE.productID
  is '��Ʒ��ʶ:���ֶ���|���';



-- Create table
create table T_VO_LIVE
(
  nodeID    varchar2(60) not null,
  programID varchar2(60) not null,
  liveName  varchar2(200) not null,
  startTime varchar2(14) not null,
  endTime   varchar2(14) not null
)
;
-- Add comments to the columns 
comment on column T_VO_LIVE.nodeID
  is '��Ŀ��ʶ';
comment on column T_VO_LIVE.programID
  is '��Ŀ��ʶ';
comment on column T_VO_LIVE.liveName
  is 'ֱ����Ŀ����';
comment on column T_VO_LIVE.startTime
  is '����ʱ��:��ʽ��YYYYMMDDHH24MISS';
comment on column T_VO_LIVE.endTime
  is '����ʱ��:��ʽ��YYYYMMDDHH24MISS';



-- Create table
create table T_VO_RANK
(
  rankID    varchar2(60) not null,
  rankName  varchar2(60) not null,
  programID varchar2(60) not null,
  sortID    number(6)
)
;
-- Add comments to the columns 
comment on column T_VO_RANK.rankID
  is '���а��ʶ';
comment on column T_VO_RANK.rankName
  is '���а�����:�����С������С�������';
comment on column T_VO_RANK.programID
  is '��Ŀ��ʶ';
comment on column T_VO_RANK.sortID
  is '�������:������š���С�������С�������������ȡֵ��Χ��-999999��999999֮�䡣';




create table T_VO_PRODUCT
(
  productID   varchar2(60) not null,
  productName varchar2(100) not null,
  fee         number(12) not null,
  CPID        varchar2(20) not null,
  feeType     varchar2(2) not null,
  startDate   varchar2(14) not null
)
;
-- Add comments to the columns 
comment on column T_VO_PRODUCT.productID
  is '��Ʒ��ʶ';
comment on column T_VO_PRODUCT.productName
  is '��Ʒ����';
comment on column T_VO_PRODUCT.fee
  is '�ʷ�:��λ����';
comment on column T_VO_PRODUCT.CPID
  is 'CP��ʶ';
comment on column T_VO_PRODUCT.feeType
  is '�Ʒ�����: 01:���  02:����  03:����  04:����� ';
comment on column T_VO_PRODUCT.startDate
  is '��ʼ����: ��ʽ��YYYYMMDD';


-- Create table
create table T_VO_CATEGORY
(
  ID       varchar2(60) not null,
  parentID varchar2(60),
  baseID   varchar2(60) not null,
  baseType varchar2(2) not null,
  baseName varchar2(128) not null,
  sortID   number(6) default 0 not null,
  isShow   varchar2(2) default 1 not null
)
;
-- Add comments to the columns 
comment on column T_VO_CATEGORY.ID
  is '�������ڵ�ID';
comment on column T_VO_CATEGORY.parentID
  is '���ڵ�ID';
comment on column T_VO_CATEGORY.baseID
  is '����id����Ŀid�����а�id��δ��Ҫ�ӵĸ��ֲ�֪����id';
comment on column T_VO_CATEGORY.baseType
  is '�������ͣ�1����ĿID�� 2�����а�ID ����������ö��';
comment on column T_VO_CATEGORY.baseName
  is '�������ƣ���Ŀ���ƣ����а����ƣ�δ��Ҫ�ӵĸ��ֲ�֪��������';
comment on column T_VO_CATEGORY.sortID
  is '�����';
comment on column T_VO_CATEGORY.isShow
  is '�Ƿ����Ż���ʾ��1���� 2����';



-- Create table
create table T_VO_REFERENCE
(
  ID          varchar2(60) not null,
  cateGoryID  varchar2(60) not null,
  PROGRAMID   varchar2(60) not null,
  PROGRAMNAME varchar2(128) not null,
  sortID      number(6) default 0 not null
)
;
-- Add comments to the columns 
comment on column T_VO_REFERENCE.ID
  is '��������Ʒid';
comment on column T_VO_REFERENCE.cateGoryID
  is '��������';
comment on column T_VO_REFERENCE.PROGRAMID
  is '��Ŀ��ʶ';
comment on column T_VO_REFERENCE.PROGRAMNAME
  is '��Ŀ����';
comment on column T_VO_REFERENCE.sortID
  is '�����';



-- Add/modify columns 
alter table T_VO_REFERENCE add exportTime date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_REFERENCE.exportTime
  is '����ʱ��';


-- Add/modify columns 
alter table T_VO_NODE add EXPORTTIME date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_NODE.EXPORTTIME
  is '����ʱ��';

-- Add/modify columns 
alter table T_VO_PROGRAM add EXPORTTIME date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_PROGRAM.EXPORTTIME
  is '����ʱ��';

-- Add/modify columns 
alter table T_VO_PRODUCT add feeDesc varchar2(1024);
-- Add comments to the columns 
comment on column T_VO_PRODUCT.feeDesc
  is '�۸�����';

-- Add/modify columns 
alter table T_VO_CATEGORY add cdesc varchar2(1024);
-- Add comments to the columns 
comment on column T_VO_CATEGORY.cdesc
  is '������Ϣ����';


create sequence SEQ_VO_CATEGORY_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

create sequence SEQ_VO_REFERENCE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- Add/modify columns 
alter table T_VO_CATEGORY add BASEPARENTID VARCHAR2(60);
-- Add comments to the columns 
comment on column T_VO_CATEGORY.BASEPARENTID
  is '���ø�id';

-- Create table
create table T_VO_VIDEODETAIL
(
  programID        varchar2(20) not null,
  dayPlayNum       number(12) not null,
  totalPlayNum     number(20) not null,
  dayDownloadNum   number(12) not null,
  totalDownloadNum number(20) not null,
  updatetime       date default sysdate not null
)
;
-- Add comments to the columns 
comment on column T_VO_VIDEODETAIL.programID
  is '��Ŀid';
comment on column T_VO_VIDEODETAIL.dayPlayNum
  is '�ղ�������';
comment on column T_VO_VIDEODETAIL.totalPlayNum
  is '�ۼƲ�������';
comment on column T_VO_VIDEODETAIL.dayDownloadNum
  is '����������';
comment on column T_VO_VIDEODETAIL.totalDownloadNum
  is '�ۼ���������';
comment on column T_VO_VIDEODETAIL.updatetime
  is '��������';


create index IDX_VO_LIVE on t_vo_live (starttime, endtime);


insert into t_vo_category
  (id, parentid, baseid, baseparentid, basetype, basename)
values
  (101, null, 000, 000, 0, '��Ŀ����');

insert into t_vo_category
  (id, parentid, baseid, baseparentid, basetype, basename)
values
  (202, null, 000, 000, 0, '����');

------��Ƶ���ݽ������




-----------------------
----------���³�ʼ����չ�������������ֶε���
---------------------------

drop table CM_CT_APPGAME;
create table   CM_CT_APPGAME as select * from s_cm_ct_appgame;
create index IDX_GAME_CONTENTID_1 on CM_CT_APPGAME (CONTENTID);
drop table    CM_CT_APPGAME_TRA;
create table   CM_CT_APPGAME_TRA as select * from CM_CT_APPGAME WHERE 1=2;
create index IDX_GAME_CONTENTID_2 on CM_CT_APPGAME_TRA (CONTENTID);



drop table CM_CT_APPSOFTWARE;
create table   CM_CT_APPSOFTWARE as select * from s_cm_ct_APPSOFTWARE;
create index IDX_SOFT_CONTENTID_1 on CM_CT_APPSOFTWARE (CONTENTID);
drop table    CM_CT_APPSOFTWARE_TRA;
create table   CM_CT_APPSOFTWARE_TRA as select * from CM_CT_APPSOFTWARE WHERE 1=2;
create index IDX_SOFT_CONTENTID_2 on CM_CT_APPSOFTWARE_TRA (CONTENTID);


drop table CM_CT_APPTHEME;
create table   CM_CT_APPTHEME as select * from s_cm_ct_APPTHEME;
create index IDX_THEME_CONTENTID_1 on CM_CT_APPTHEME (CONTENTID);
drop table    CM_CT_APPTHEME_TRA;
create table   CM_CT_APPTHEME_TRA as select * from CM_CT_APPTHEME WHERE 1=2;
create index IDX_THEME_CONTENTID_2 on CM_CT_APPTHEME_TRA (CONTENTID);


-- Add/modify columns 
alter table T_R_GCONTENT add logo5 VARCHAR2(256);
-- Add comments to the columns 
comment on column T_R_GCONTENT.logo5
  is '�ն�PadչʾLOGO5��ַ��ͼƬ���140x140';
  
  

------------------------------�����Ķ���ʼ
-- Create table
create table T_RB_TYPE_NEW
(
  TYPEID   VARCHAR2(20) not null,
  TYPENAME VARCHAR2(100) not null
);
-- Add comments to the columns 
comment on column T_RB_TYPE_NEW.TYPEID
  is 'ͼ�����ID';
comment on column T_RB_TYPE_NEW.TYPENAME
  is 'ͼ���������';

-- Create table
create table T_RB_AUTHOR_NEW
(
  AUTHORID   VARCHAR2(25) not null,
  AUTHORNAME VARCHAR2(50) not null,
  AUTHORDESC VARCHAR2(1024),
  NAMELETTER VARCHAR2(3),
  ISORIGINAL VARCHAR2(3) default 0 not null,
  ISPUBLISH  VARCHAR2(3) default 0 not null,
  AUTHORPIC  VARCHAR2(512)
)
;
-- Add comments to the columns 
comment on column T_RB_AUTHOR_NEW.AUTHORID
  is '����ID';
comment on column T_RB_AUTHOR_NEW.AUTHORNAME
  is '����';
comment on column T_RB_AUTHOR_NEW.AUTHORDESC
  is '����';
comment on column T_RB_AUTHOR_NEW.NAMELETTER
  is '��������ĸ';
comment on column T_RB_AUTHOR_NEW.ISORIGINAL
  is '�Ƿ�ԭ������:1,��;0,��';
comment on column T_RB_AUTHOR_NEW.ISPUBLISH
  is '�Ƿ��������:1,��;0,��';
comment on column T_RB_AUTHOR_NEW.AUTHORPIC
  is '����ͼƬ';

-- Create table
create table T_RB_BOOK_NEW
(
  BOOKID         VARCHAR2(20) not null,
  BOOKNAME       VARCHAR2(100) not null,
  KEYWORD        VARCHAR2(1024),
  LONGRECOMMEND  VARCHAR2(200) not null,
  SHORTRECOMMEND VARCHAR2(100),
  DESCRIPTION    VARCHAR2(2048),
  AUTHORID       VARCHAR2(25) not null,
  TYPEID         VARCHAR2(20),
  INTIME         VARCHAR2(14) not null,
  WORDCOUNT      NUMBER(12) default 0 not null,
  CHAPTERCOUNT   NUMBER(12) default 0 not null,
  CHARGETYPE     VARCHAR2(2) not null,
  FEE            VARCHAR2(12) not null,
  ISFINISH       VARCHAR2(2) not null,
  DELFLAG        NUMBER(1) default 0 not null,
  LUPDATE        DATE default SYSDATE not null
)
;
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.BOOKID
  is 'ͼ��ID';
comment on column T_RB_BOOK_NEW.BOOKNAME
  is 'ͼ������';
comment on column T_RB_BOOK_NEW.KEYWORD
  is 'ͼ��ؼ���';
comment on column T_RB_BOOK_NEW.LONGRECOMMEND
  is '���Ƽ���';
comment on column T_RB_BOOK_NEW.SHORTRECOMMEND
  is '���Ƽ���';
comment on column T_RB_BOOK_NEW.DESCRIPTION
  is 'ͼ����';
comment on column T_RB_BOOK_NEW.AUTHORID
  is '����ID';
comment on column T_RB_BOOK_NEW.TYPEID
  is 'ͼ�����ID ����';
comment on column T_RB_BOOK_NEW.INTIME
  is '���ʱ�� YYYYMMDDHH24MISS';
comment on column T_RB_BOOK_NEW.WORDCOUNT
  is 'ͼ������';
comment on column T_RB_BOOK_NEW.CHAPTERCOUNT
  is 'ͼ���½���';
comment on column T_RB_BOOK_NEW.CHARGETYPE
  is '�������� �������ͣ�0��ѣ�1�����Ʒѣ�2�����¼Ʒѣ�3�����ּƷ�';
comment on column T_RB_BOOK_NEW.FEE
  is '���ʣ���λ����
��chargeType = 0ʱ��fee����Ϊ0';
comment on column T_RB_BOOK_NEW.ISFINISH
  is '�Ƿ��걾';
comment on column T_RB_BOOK_NEW.DELFLAG
  is '�Ƿ�ɾ�� 0��δɾ��  1����ɾ��';
comment on column T_RB_BOOK_NEW.LUPDATE
  is '������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOK_NEW
  add constraint PK_T_RB_BOOK_NEW primary key (BOOKID)
;


-- Create table
create table T_RB_RECOMMEND_NEW
(
  RECOMMENDID VARCHAR2(20) not null,
  TYPEID      VARCHAR2(20),
  BOOKID      VARCHAR2(20) not null,
  CREATETIME  DATE default SYSDATE not null
);
-- Add comments to the columns 
comment on column T_RB_RECOMMEND_NEW.RECOMMENDID
  is '�Ƽ�ID';
comment on column T_RB_RECOMMEND_NEW.TYPEID
  is 'ͼ�����ID';
comment on column T_RB_RECOMMEND_NEW.BOOKID
  is 'ͼ��ID';

-- Create table
create table T_RB_BOOKCONTENT_NEW
(
  BOOKBAGID VARCHAR2(20) not null,
  BOOKID    VARCHAR2(20) not null,
  SORTID    NUMBER(6)
);
-- Add comments to the columns 
comment on column T_RB_BOOKCONTENT_NEW.BOOKBAGID
  is '�����ʶ';
comment on column T_RB_BOOKCONTENT_NEW.BOOKID
  is 'ͼ���ʶ';
comment on column T_RB_BOOKCONTENT_NEW.SORTID
  is '������� ��������Ϊ�׷�ʱΪ���롣������š��Ӵ�С���С�������������ȡֵ��Χ��-999999��999999֮�䡣';



-- Create table
create table T_RB_UPDATEBOOK_NEW
(
  CONTENTID  VARCHAR2(60) not null,
  UPDATETIME VARCHAR2(14) not null
);
-- Add comments to the columns 
comment on column T_RB_UPDATEBOOK_NEW.CONTENTID
  is '���ݱ�ʶ';
comment on column T_RB_UPDATEBOOK_NEW.UPDATETIME
  is '����ʱ�� ��ʽΪYYYYMMDDHHMM';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_UPDATEBOOK_NEW
  add constraint T_KEY_UPDATEBOOK_ID primary key (CONTENTID);

-- Create table
create table T_RB_BOOKBAG_NEW
(
  BOOKBAGID    VARCHAR2(20) not null,
  BOOKBAGNAME  VARCHAR2(64) not null,
  BOOKBAGDESC  VARCHAR2(256) not null,
  BOOKBAGIMAGE VARCHAR2(512) not null,
  FEE          NUMBER(12) not null,
  ONLINETIME   VARCHAR2(14) not null,
  SORTID       NUMBER(6)
);
-- Add comments to the columns 
comment on column T_RB_BOOKBAG_NEW.BOOKBAGID
  is '���id';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGNAME
  is '�������';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGDESC
  is '������';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGIMAGE
  is '���ͼƬ';
comment on column T_RB_BOOKBAG_NEW.FEE
  is '��λ���֣�ֻ�а�����������ʷ�';
comment on column T_RB_BOOKBAG_NEW.ONLINETIME
  is '����ʱ�� ��ʽ��YYYYMMDDHH24MISS';
comment on column T_RB_BOOKBAG_NEW.SORTID
  is '������� ������š��Ӵ�С���С�������������ȡֵ��Χ��-999999��999999֮�䡣';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOKBAG_NEW
  add constraint T_KEY_BOOKBAG_ID primary key (BOOKBAGID);


-- Create table
create table T_RB_BOOKSCHEDULED_NEW
(
  CONTENTID   VARCHAR2(60) not null,
  SECTIONID   VARCHAR2(64) not null,
  SECTIONNAME VARCHAR2(64) not null
);
-- Add comments to the columns 
comment on column T_RB_BOOKSCHEDULED_NEW.CONTENTID
  is '���ݱ�ʶ';
comment on column T_RB_BOOKSCHEDULED_NEW.SECTIONID
  is '�½ڱ�ʶ';
comment on column T_RB_BOOKSCHEDULED_NEW.SECTIONNAME
  is '�½�����';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOKSCHEDULED_NEW
  add constraint T_KEY_BOOKSCHEDULED primary key (CONTENTID, SECTIONID);


-- Create table
create table T_RB_CATEGORY_NEW
(
  ID               NUMBER(10) not null,
  CATEGORYID       VARCHAR2(20),
  CATEGORYNAME     VARCHAR2(200) not null,
  CATALOGTYPE      VARCHAR2(20) not null,
  DECRISPTION      VARCHAR2(256),
  PARENTID         VARCHAR2(20),
  PICURL           VARCHAR2(1024),
  LUPDATE          DATE default SYSDATE not null,
  TOTAL            NUMBER(10),
  PLATFORM         VARCHAR2(200) default '{0000}',
  CITYID           VARCHAR2(4000) default '{0000}',
  TYPE             VARCHAR2(2) default 1,
  SORTID           NUMBER(8) default 0,
  PARENTCATEGORYID VARCHAR2(20)
) ;
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.ID
  is '��ID';
comment on column T_RB_CATEGORY_NEW.CATEGORYID
  is '����ID ��Ӧר��������
';
comment on column T_RB_CATEGORY_NEW.CATEGORYNAME
  is '��������';
comment on column T_RB_CATEGORY_NEW.CATALOGTYPE
  is '�������� 1���Ƽ�ר���������Ƽ���Ϊ�Ƽ���һ�֣�
2�����£���Ϊ������Ŀ��ֻ����һ������ר��
4���ػ�ר����һë�����֣�������ר�����乺�ȣ� wap�ľ���ͼ���Ƽ�����������ȷ��
5:���˹�ר��������Ϊ���߽��ܣ�
6��ר��������������ר�⣨���滹�����ӻ���ר���������»������ר�⣬���ڻ��
7�����ר��������Ķ�������
 8:����';
comment on column T_RB_CATEGORY_NEW.DECRISPTION
  is '���';
comment on column T_RB_CATEGORY_NEW.PARENTID
  is '������ID';
comment on column T_RB_CATEGORY_NEW.PICURL
  is 'ר��ͼƬ�����ڲ��ṩ';
comment on column T_RB_CATEGORY_NEW.LUPDATE
  is '������ʱ��';
comment on column T_RB_CATEGORY_NEW.TOTAL
  is '�û��������е���Ʒ����';
comment on column T_RB_CATEGORY_NEW.PLATFORM
  is 'ƽ̨�����ϵ';
comment on column T_RB_CATEGORY_NEW.CITYID
  is '���������ϵ';
comment on column T_RB_CATEGORY_NEW.TYPE
  is '�Ƿ����Ż�չʾ 1���� 0����';
comment on column T_RB_CATEGORY_NEW.SORTID
  is '��������';
comment on column T_RB_CATEGORY_NEW.PARENTCATEGORYID
  is '���ܸ�ID ��Ӧר��������
';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_CATEGORY_NEW
  add constraint PK_T_RB_CATEGORY_NEW primary key (ID) ;
-- Create/Recreate indexes 
create index IDX_T_RB_CATEGORY_NEW_ID on T_RB_CATEGORY_NEW (CATEGORYID) ;


-- Create table
create table T_RB_CATE
(
  CATEID       VARCHAR2(20) not null,
  CATENAME     VARCHAR2(64) not null,
  CATEDESC     VARCHAR2(256) not null,
  PARENTCATEID VARCHAR2(20),
  CATEPIC      VARCHAR2(512) not null,
  CATETYPE     VARCHAR2(20) not null,
  SORTID       NUMBER(6),
  BUSINESSTIME VARCHAR2(14) not null
);
-- Add comments to the columns 
comment on column T_RB_CATE.CATEID
  is 'ר����ʶ';
comment on column T_RB_CATE.CATENAME
  is 'ר������';
comment on column T_RB_CATE.CATEDESC
  is 'ר�����';
comment on column T_RB_CATE.PARENTCATEID
  is 'ר����id';
comment on column T_RB_CATE.CATEPIC
  is 'ר��ͼƬ';
comment on column T_RB_CATE.CATETYPE
  is 'ר������ 1���Ƽ�ר���������Ƽ���Ϊ�Ƽ���һ�֣�
2�����£���Ϊ������Ŀ��ֻ����һ������ר��
4���ػ�ר����һë�����֣�������ר�����乺�ȣ� wap�ľ���ͼ���Ƽ�����������ȷ��
5:���˹�ר��������Ϊ���߽��ܣ�
6��ר��������������ר�⣨���滹�����ӻ���ר���������»������ר�⣬���ڻ��
7�����ר��������Ķ�������
';
comment on column T_RB_CATE.SORTID
  is '������š��Ӵ�С���С�������������ȡֵ��Χ��-999999��999999֮�䡣';
comment on column T_RB_CATE.BUSINESSTIME
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_CATE
  add constraint T_KEY_CATA_ID primary key (CATEID);

-- Create table
create table T_RB_REFERENCE_NEW
(
  CID        NUMBER(10) not null,
  CATEGORYID VARCHAR2(20) not null,
  BOOKID     VARCHAR2(60) not null,
  SORTNUMBER NUMBER(6),
  RANKVALUE  NUMBER(10)
) ;
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.CID
  is '����ID';
comment on column T_RB_REFERENCE_NEW.CATEGORYID
  is '��Ӧ���¡�ר��������id';
comment on column T_RB_REFERENCE_NEW.BOOKID
  is 'ͼ��ID';
comment on column T_RB_REFERENCE_NEW.SORTNUMBER
  is '������š���С�������С�������������ȡֵ��Χ��-999999��999999֮��';
comment on column T_RB_REFERENCE_NEW.RANKVALUE
  is '��������ֵ';
-- Create/Recreate indexes 
create index IDX_T_RB_REFERENCE_NEW_BOOK_ID on T_RB_REFERENCE_NEW (BOOKID) ;
create index IDX_T_RB_REFERENCE_NEW_CATE_ID on T_RB_REFERENCE_NEW (CATEGORYID) ;



-- Drop columns 
-- Create table
create table T_RB_RANK_NEW
(
  RANKID     VARCHAR2(20) not null,
  RANKNAME   VARCHAR2(60) not null,
  BOOKID     VARCHAR2(20) not null,
  SORTNUMBER NUMBER(6) default 0
) ;
-- Add comments to the columns 
comment on column T_RB_RANK_NEW.RANKID
  is '��id';
comment on column T_RB_RANK_NEW.RANKNAME
  is '������';
comment on column T_RB_RANK_NEW.BOOKID
  is 'ͼ��id';
comment on column T_RB_RANK_NEW.SORTNUMBER
  is '�����';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_RANK_NEW
  add constraint T_KEY_RANK_ID primary key (RANKID, BOOKID) ;


-- Create sequence 
create sequence SEQ_BR_CATEGORY_NEW_ID
minvalue 1
maxvalue 9999999999
start with 10000
increment by 1
nocache
cycle;


insert into T_RB_CATEGORY_NEW (ID, CATEGORYNAME, CATALOGTYPE)
values (101,'ר����', '0');
insert into T_RB_CATEGORY_NEW (ID, CATEGORYNAME, CATALOGTYPE)
values (202, '���и�', '0');




-- Create table
create table T_RB_STATISTICS_NEW
(
  CONTENTID    VARCHAR2(60) not null,
  READERNUM    NUMBER(12) not null,
  FLOWERSNUM   NUMBER(12) not null,
  CLICKNUM     NUMBER(12) not null,
  FAVORITESNUM NUMBER(12) not null,
  ORDERNUM     NUMBER(12) not null,
  VOTENUM      NUMBER(12) not null
) ;
-- Add comments to the columns 
comment on column T_RB_STATISTICS_NEW.CONTENTID
  is '���ݱ�ʶ';
comment on column T_RB_STATISTICS_NEW.READERNUM
  is '����';
comment on column T_RB_STATISTICS_NEW.FLOWERSNUM
  is '�ʻ�';
comment on column T_RB_STATISTICS_NEW.CLICKNUM
  is '���';
comment on column T_RB_STATISTICS_NEW.FAVORITESNUM
  is '�ղ���';
comment on column T_RB_STATISTICS_NEW.ORDERNUM
  is 'Ԥ����';
comment on column T_RB_STATISTICS_NEW.VOTENUM
  is 'ͶƱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_STATISTICS_NEW
  add constraint T_KEY_STATISTICS_ID primary key (CONTENTID) ;


-- Add/modify columns 
alter table T_VO_VIDEO add downloadfilepath VARCHAR2(512) not null;
-- Add comments to the columns 
comment on column T_VO_VIDEO.FILEPATH
  is '�������ļ���ַ';
comment on column T_VO_VIDEO.downloadfilepath
  is '���������ļ���ַ';

alter table T_VO_VIDEO
  add constraint T_KEY_VO_VIDEOID primary key (VIDEOID, CODERATEID);


---------------------�����Ķ�����





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.049_SSMS','MM1.1.1.059_SSMS');


commit;