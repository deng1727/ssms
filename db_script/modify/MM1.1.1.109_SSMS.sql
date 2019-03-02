 
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (51, '从产品库中获得最新(免费>收费)', 'select b.id from t_r_base b, t_r_gcontent g, v_content_last l, v_service v' || chr(10) || ' where b.id = g.id' || chr(10) || '   and b.type like ''nt:gcontent:app%''' || chr(10) || '   and g.provider != ''B''' || chr(10) || '   and (g.subtype is null or' || chr(10) || '       g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' || chr(10) || '   and g.contentid = l.contentid and g.icpcode=v.icpcode and g.icpservid=v.icpservid');


update t_caterule_cond c
   set c.osql = 'decode(v.mobileprice, 0, 0, 10), trunc(l.createtime), decode(catename, ''软件'', 1, ''游戏'', 1, ''主题'', 2, 10), to_char(l.createtime, ''hh24miss''), g.name asc'
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


--新音乐二期融合脚本---
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
  is '新音乐歌手表';
-- Add comments to the columns 
comment on column t_mb_singer_new.sid
  is '歌手ID';
comment on column t_mb_singer_new.singerupcase
  is '歌手名首字母';
comment on column t_mb_singer_new.singername
  is '歌手名';
comment on column t_mb_singer_new.singerdesc
  is '歌手介绍';
comment on column t_mb_singer_new.imgurl
  is '歌手图片';
comment on column t_mb_singer_new.type
  is '11华语男 12华语女 13 华语组合
21欧美男 22欧美女 23欧美组合
31日韩男 32日韩女 33日韩组合
';
comment on column T_MB_SINGER_NEW.upddate
  is '更新时间';
 comment on column T_MB_SINGER_NEW.delflag
  is '删除标识:0,未删除.1,删除'; 
  
  
  -- Add/modify columns 
alter table T_MB_MUSIC_NEW add singersid VARCHAR2(1024);
alter table T_MB_MUSIC_NEW add pubtime VARCHAR2(14);
alter table T_MB_MUSIC_NEW add onlinetype number;
alter table T_MB_MUSIC_NEW add colortype number;
alter table T_MB_MUSIC_NEW add ringtype number;
alter table T_MB_MUSIC_NEW add songtype number;
-- Add comments to the columns 
comment on column T_MB_MUSIC_NEW.singersid
  is '歌手标识，多值用|间隔';
comment on column T_MB_MUSIC_NEW.pubtime
  is '发布时间';
comment on column T_MB_MUSIC_NEW.onlinetype
  is '在线听歌,1:表示支持;0:表示不支持';
comment on column T_MB_MUSIC_NEW.colortype
  is '彩铃 1:表示支持;0:表示不支持';
comment on column T_MB_MUSIC_NEW.ringtype
  is '震铃  1:表示支持;0:表示不支持';
comment on column T_MB_MUSIC_NEW.songtype
  is '全曲 1:表示支持;0:表示不支持';
  
  
  
  -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add album_upcase varchar2(3);
alter table T_MB_CATEGORY_NEW add pubtime VARCHAR2(14);
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.album_upcase
  is '专辑首字母';
comment on column T_MB_CATEGORY_NEW.pubtime
  is '发布时间';
  -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add singersid VARCHAR2(512);
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.singersid
  is '歌手ID列表';
 -- Add/modify columns 
alter table T_MB_CATEGORY_NEW add catetype VARCHAR2(3)  default 0 not null;
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.catetype
  is '音乐货架类型:1,彩铃货架;0,普通货架';
  
  
insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, LUPDDATE, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER, PLATFORM, CITYID, ALBUM_UPCASE, PUBTIME, SINGERSID, CATETYPE)
values ('100000007', '新专辑', '', '1', '0', '2010-05-10 09:03:00', '2011-05-01 16:45:53', '新专辑', 0, 0, '', '', 1, '', '{0000}', '{0000}', '', '', '', '0');


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
  is '分类ID';
comment on column T_MB_SINGERTYPE_NEW.typename
  is '分类名称';
comment on column T_MB_SINGERTYPE_NEW.typepicurl
  is '分类图片';
comment on column T_MB_SINGERTYPE_NEW.typedesc
  is '分类描述';
comment on column T_MB_SINGERTYPE_NEW.lupdate
  is '最后更新时间';
comment on column T_MB_SINGERTYPE_NEW.sortid
  is '序号';
  
 insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('11', '华语男歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('12', '华语女歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('13', '华语组合', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 3);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('21', '欧美男歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 4);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('22', '欧美女歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 5);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('23', '欧美组合', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 6);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('31', '日韩男歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 7);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('32', '日韩女歌手', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 8);

insert into T_MB_SINGERTYPE_NEW (TYPEID, TYPENAME, TYPEPICURL, TYPEDESC, LUPDATE, SORTID)
values ('33', '日韩组合', '', '', to_date('30-10-2012 18:44:20', 'dd-mm-yyyy hh24:mi:ss'), 9);

-- Create table
create table T_MB_COLORURL_NEW
(
  musicid VARCHAR2(30),
  colorid VARCHAR2(30),
  url     VARCHAR2(500)
);
-- Add comments to the columns 
comment on column T_MB_COLORURL_NEW.musicid
  is '音乐ID';
comment on column T_MB_COLORURL_NEW.colorid
  is '彩铃ID';
comment on column T_MB_COLORURL_NEW.url
  is '彩铃URL';
-- Create/Recreate indexes 
create index IDX_MUSICID_COLOR_1 on T_MB_COLORURL_NEW (MUSICID) ;

  
  
----音乐脚本结束----


--动漫加一字段baseType
alter table t_cb_content add baseType varchar2(5);


-- Add/modify columns 
alter table T_RB_BOOK_NEW add BOOKPIC VARCHAR2(500);
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.BOOKPIC
  is '图书图片路径';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.105_SSMS','MM1.1.1.109_SSMS');


commit;