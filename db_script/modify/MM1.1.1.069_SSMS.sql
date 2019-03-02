

insert into t_right values ('2_1505_COMIC','���ض�������','���ض�������','','2');
insert into t_roleright values ('1','2_1505_COMIC');

drop table T_CB_CHAPTER;

create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(4000),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5),
  SORTID      NUMBER(5),
  FEE_CODE    VARCHAR2(60),
  SMALL       VARCHAR2(5),
  MEDIUM      VARCHAR2(5),
  LARGE       VARCHAR2(5),
  FLOW_TIME   DATE default sysdate,
  STATUS      NUMBER(1) default 1
)

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
comment on column T_CB_CHAPTER.SORTID
  is '�������';
comment on column T_CB_CHAPTER.FEE_CODE
  is '�ƷѴ���';
comment on column T_CB_CHAPTER.SMALL
  is 'СͼƬ';
comment on column T_CB_CHAPTER.MEDIUM
  is '��ͼƬ';
comment on column T_CB_CHAPTER.LARGE
  is '��ͼƬ';
comment on column T_CB_CHAPTER.FLOW_TIME
  is '��ˮʱ�䣨�����ֶΣ�';
comment on column T_CB_CHAPTER.STATUS
  is '��ͬ�������ݣ������ֶΣ�';


drop table T_CB_ADAPTER;
create table T_CB_ADAPTER
(
  ID        VARCHAR2(60) not null,
  CHAPTERID VARCHAR2(60),
  GROUPS    VARCHAR2(4000),
  FILE_SIZE NUMBER(10),
  USE_TYPE  NUMBER(2),
  CLEAR     VARCHAR2(1),
  FLOW_TIME DATE default sysdate,
  URL       VARCHAR2(512),
  TYPE      VARCHAR2(5),
  STATUS    NUMBER(1) default 1
)

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
  is '��ˮʱ�䣨�����ֶΣ�';
comment on column T_CB_ADAPTER.URL
  is 'ʹ��URL';
comment on column T_CB_ADAPTER.TYPE
  is '��������';
comment on column T_CB_ADAPTER.STATUS
  is '��¼״̬�������ֶΣ�';


drop table t_cb_content;
-- Create table
create table T_CB_CONTENT
(
  ID            VARCHAR2(60) not null,
  NAME          VARCHAR2(256) not null,
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
  

create index id_index on t_cb_content(id);



create table t_sync_tactic_base(
id number(10),
sql varchar2(1024),
categoryId varchar2(50),
effectiveTime date,
lupTime date,
time_consuming number(12),

primary key(id));

-- Add/modify columns 
alter table T_VO_REFERENCE add BASEID VARCHAR2(60);
alter table T_VO_REFERENCE add BASETYPE VARCHAR2(2);
-- Add comments to the columns 
comment on column T_VO_REFERENCE.BASEID
  is '����id����Ŀid�����а�id��δ��Ҫ�ӵĸ��ֲ�֪����id';
comment on column T_VO_REFERENCE.BASETYPE
  is '�������ͣ�1����ĿID�� 2�����а�ID ����������ö��';

comment on column T_CONTENT_EXT.type
  is '�������ͣ�1�ۿۣ�2��ɱ(��δʹ��);3,��ʱ���;4,��Լ����';




-----Ӧ�ô������ܿ���-------
-----------------------------
alter  table  t_content_ext rename to t_content_ext_local;

  create or replace  synonym  ppms_v_t_content_ext for v_t_content_ext@dl_ppms_device;
 
  create or replace  view  t_content_ext as select * from  t_content_ext_local union all 
                     select * from ppms_v_t_content_ext;
                     
  create or replace  synonym  ppms_V_T_CON_EXT_SPECIALFREE for  V_T_CONTENT_EXT_SPECIALFREE@dl_ppms_device;


create  or replace view  v_content_pkapps as  select * from ppms_v_cm_content_pkapps t;



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.065_SSMS','MM1.1.1.069_SSMS');


commit;