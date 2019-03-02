drop table t_cb_chapter;
create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(4000),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5),
  primary key(CHAPTERID)
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
  
  
drop table t_cb_content;
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



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000032', '����', '100000001', null, null, '0', null, null, null, null, '15008');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000033', '����', '100000001', null, null, '0', null, null, null, null, '15010');


-- Add/modify columns 
alter table T_VO_NODE modify LOGOPATH null;
alter table T_VO_NODE modify SORTID NUMBER(19);
alter table T_VO_NODE modify PRODUCTID null;


-- Add/modify columns 
alter table T_VO_VIDEO add downloadfilepath VARCHAR2(512) not null;
-- Add comments to the columns 
comment on column T_VO_VIDEO.FILEPATH
  is '�������ļ���ַ';
comment on column T_VO_VIDEO.downloadfilepath
  is '���������ļ���ַ';

alter table T_VO_VIDEO
  add constraint T_KEY_VO_VIDEOID primary key (VIDEOID, CODERATEID);

-- Add/modify columns 
alter table T_VO_CODERATE modify CODERATELEVEL number(6);


update t_vo_category c set c.parentid='0' where c.id='101' or c.id='202';

-----����-----
    create or replace view v_ssms_music_category as 
      select t.categoryid ,t.categoryname,t.categorydesc,t.sortid,t.delflag,t.createtime from T_MB_CATEGORY_NEW t;
      
  create or replace view   v_ssms_music_reference as  
         select t.musicid,t.categoryid,t.sortid,t.musicname,t.createtime from T_MB_REFERENCE_NEW t;    
      
       -----�Ķ�-----
      create or replace view  v_ssms_book_category    as
    select t.categoryid,t.categoryname,t.decrisption,t.sortid,t.type,t.parentcategoryid,t.lupdate from t_rb_category_new t;
    
    create or replace view   v_ssms_book_reference  as 
        select t.bookid,t.cid,t.categoryid,t.sortnumber as sortid from t_rb_reference_new t;
        
        
     -----����-----   
        create or replace view v_ssms_cat_category  as
 select t.categoryid,t.categoryname,t.categorydesc,t.sortid,t.type,t.delflag,t.parentcategoryid,t.lupdate from t_cb_category t;
 
  create or replace view  v_ssms_cat_reference as 
   select t.contentid,t.categoryid,t.sortid,t.portal from t_cb_reference t;
   
      -----��Ƶ-----   
     create or replace view  v_ssms_video_category as 
         select t.id,t.basename,t.cdesc,t.sortid,t.isshow,t.baseparentid from  t_vo_category t;
 
 
    create or replace view v_ssms_video_reference as 
          select t.programid,t.categoryid,t.sortid,t.exporttime from t_vo_reference t;
    
    

