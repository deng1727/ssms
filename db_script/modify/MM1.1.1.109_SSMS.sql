 
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (51, '�Ӳ�Ʒ���л������(���>�շ�)', 'select b.id from t_r_base b, t_r_gcontent g, v_content_last l, v_service v' || chr(10) || ' where b.id = g.id' || chr(10) || '   and b.type like ''nt:gcontent:app%''' || chr(10) || '   and g.provider != ''B''' || chr(10) || '   and (g.subtype is null or' || chr(10) || '       g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' || chr(10) || '   and g.contentid = l.contentid and g.icpcode=v.icpcode and g.icpservid=v.icpservid');


update t_caterule_cond c
   set c.osql = 'decode(v.mobileprice, 0, 0, 10), trunc(l.createtime), decode(catename, ''���'', 1, ''��Ϸ'', 1, ''����'', 2, 10), to_char(l.createtime, ''hh24miss''), g.name asc'
   , c.basecondid='51', c.condtype='51'
 where c.ruleid in ('282', '287');

-- Add/modify columns 
alter table T_RB_CATE modify CATETYPE default 1;

create sequence SEQ_BR_CATEGORY_NEW_CID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- Add/modify columns 
alter table T_RB_BOOKBAG_NEW modify BOOKBAGDESC null;
alter table T_RB_BOOKBAG_NEW modify BOOKBAGIMAGE null;
alter table T_RB_BOOKBAG_NEW modify ONLINETIME null;


--�����ֶ����ںϽű�---
-- Create table
create table t_mb_singer_new
(
  sid          varchar2(64),
  singerupcase varchar2(3),
  singername   varchar2(512),
  singerdesc         varchar2(1024),
  imgurl       varchar2(512),
  type         varchar2(512),
  upddate      DATE default sysdate not null,
  delflag     NUMBER default 0 not null
)
;
-- Add comments to the table 
comment on table t_mb_singer_new
  is '�����ָ��ֱ�';
-- Add comments to the columns 
comment on column t_mb_singer_new.sid
  is '����ID';
comment on column t_mb_singer_new.singerupcase
  is '����������ĸ';
comment on column t_mb_singer_new.singername
  is '������';
comment on column t_mb_singer_new.singerdesc
  is '���ֽ���';
comment on column t_mb_singer_new.imgurl
  is '����ͼƬ';
comment on column t_mb_singer_new.type
  is '11������ 12����Ů 13 �������
21ŷ���� 22ŷ��Ů 23ŷ�����
31�պ��� 32�պ�Ů 33�պ����
';
comment on column T_MB_SINGER_NEW.upddate
  is '����ʱ��';
 comment on column T_MB_SINGER_NEW.delflag
  is 'ɾ����ʶ:0,δɾ��.1,ɾ��'; 
  
  
  -- Add/modify columns 
alter table T_MB_MUSIC_NEW add singersid VARCHAR2(1024);
alter table T_MB_MUSIC_NEW add pubtime VARCHAR2(14);
alter table T_MB_MUSIC_NEW add onlinetype number;
alter table T_MB_MUSIC_NEW add colortype number;
alter table T_MB_MUSIC_NEW add ringtype number;
alter table T_MB_MUSIC_NEW add songtype number;
-- Add comments to the columns 
comment on column T_MB_MUSIC_NEW.singersid
  is '���ֱ�ʶ����ֵ��|���';
comment on column T_MB_MUSIC_NEW.pubtime
  is '����ʱ��';
comment on column T_MB_MUSIC_NEW.onlinetype
  is '��������,1:��ʾ֧��;0:��ʾ��֧��';
comment on column T_MB_MUSIC_NEW.colortype
  is '���� 1:��ʾ֧��;0:��ʾ��֧��';
comment on column T_MB_MUSIC_NEW.ringtype
  is '����  1:��ʾ֧��;0:��ʾ��֧��';
comment on column T_MB_MUSIC_NEW.songtype
  is 'ȫ�� 1:��ʾ֧��;0:��ʾ��֧��';
  
  
  
  -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add album_upcase varchar2(3);
alter table T_MB_CATEGORY_NEW add pubtime VARCHAR2(14);
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.album_upcase
  is 'ר������ĸ';
comment on column T_MB_CATEGORY_NEW.pubtime
  is '����ʱ��';
  -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add singersid VARCHAR2(512);
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.singersid
  is '����ID�б�';
 -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add catetype VARCHAR2(3)  default 0 not null;
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.catetype
  is '���ֻ�������:1,�������;0,��ͨ����';
  
  
insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, LUPDDATE, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER, PLATFORM, CITYID, ALBUM_UPCASE, PUBTIME, SINGERSID, CATETYPE)
values ('100000007', '��ר��', '', '1', '0', '2010-05-10 09:03:00', '2011-05-01 16:45:53', '��ר��', 0, 0, '', '', 1, '', '{0000}', '{0000}', '', '', '', '0');


-- Create table
create table T_MB_SINGERTYPE_NEW
(
  typeid     VARCHAR2(30),
  typename   VARCHAR2(500),
  typepicurl VARCHAR2(500),
  typedesc   VARCHAR2(1000),
  lupdate    DATE default sysdate,
  sortid     NUMBER(11) default 1
);
-- Add comments to the columns 
comment on column T_MB_SINGERTYPE_NEW.typeid
  is '����ID';
comment on column T_MB_SINGERTYPE_NEW.typename
  is '��������';
comment on column T_MB_SINGERTYPE_NEW.typepicurl
  is '����ͼƬ';
comment on column T_MB_SINGERTYPE_NEW.typedesc
  is '��������';
comment on column T_MB_SINGERTYPE_NEW.lupdate
  is '������ʱ��';
comment on column T_MB_SINGERTYPE_NEW.sortid
  is '���';
  
 insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('11', '�����и���', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('12', '����Ů����', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('13', '�������', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 3);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('21', 'ŷ���и���', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 4);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('22', 'ŷ��Ů����', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 5);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('23', 'ŷ�����', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 6);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('31', '�պ��и���', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 7);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('32', '�պ�Ů����', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 8);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('33', '�պ����', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 9);

-- Create table
create table T_MB_COLORURL_NEW
(
  musicid VARCHAR2(30),
  colorid VARCHAR2(30),
  url     VARCHAR2(500)
);
-- Add comments to the columns 
comment on column T_MB_COLORURL_NEW.musicid
  is '����ID';
comment on column T_MB_COLORURL_NEW.colorid
  is '����ID';
comment on column T_MB_COLORURL_NEW.url
  is '����URL';
-- Create/Recreate indexes 
create index IDX_MUSICID_COLOR_1 on T_MB_COLORURL_NEW (MUSICID) ;

  
  
----���ֽű�����----


--������һ�ֶ�baseType
alter table t_cb_content add baseType varchar2(5);


-- Add/modify columns 
alter table T_RB_BOOK_NEW add BOOKPIC VARCHAR2(500);
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.BOOKPIC
  is 'ͼ��ͼƬ·��';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.105_SSMS','MM1.1.1.109_SSMS');


commit;