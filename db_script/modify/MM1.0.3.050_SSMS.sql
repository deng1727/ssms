
-- Create table
create table T_GOODS_CHANGE_HIS
(
  GOODSID     VARCHAR2(50),
  TYPE        VARCHAR2(20),
  CID         VARCHAR2(30),
  DATAVERSION DATE,
  ACTION      VARCHAR2(2),
  SUBTYPE     VARCHAR2(3)
);
-- Add comments to the columns 
comment on column T_GOODS_CHANGE_HIS.GOODSID
  is '变动的商品ID';
comment on column T_GOODS_CHANGE_HIS.TYPE
  is 'W/O/A    W：www门户   O：mopps  A：wappps';
comment on column T_GOODS_CHANGE_HIS.CID
  is '对应的货架ID（parentid）';
comment on column T_GOODS_CHANGE_HIS.DATAVERSION
  is '更新时间';
comment on column T_GOODS_CHANGE_HIS.ACTION
  is '1  上架   2 下架';
comment on column T_GOODS_CHANGE_HIS.SUBTYPE
  is '内容类型1表示mm普通应用,2表示widget应用,3表示ZCOM应用,4表示FMM应用，5表示jil应用，6表示MM大赛应用，7表示孵化应用，8表示孵化厂商应用，9表示香港MM，10表示OVI应用，11表示套餐';

------添加字段
-- Add/modify columns 
alter table T_R_CATEGORY add PLATFORM VARCHAR2(200) default '{0000}';
alter table T_R_CATEGORY add CITYID VARCHAR2(4000) default '{0000}';


-- Add comments to the columns 
comment on column T_R_CATEGORY.PLATFORM
  is '程序包的平台类型，取值包括kjava，mobile，symbian等。以{}作为边界符，以逗号分隔';
comment on column T_R_CATEGORY.CITYID
  is '应用归属市的id， 0000表示全国通用，以{}作为边界符，以逗号分隔';

---------可扩展属性功能
-- Create table
create table t_key_base
(
  keyid    varchar2(30),
  keyname  varchar2(200),
  keytable varchar2(200),
  keydesc  varchar2(500)
)
;
-- Add comments to the table 
comment on table T_KEY_BASE
  is '资源基础表';
-- Add comments to the columns 
comment on column t_key_base.keyid
  is 'Key值ID';
comment on column t_key_base.keyname
  is 'Key值名称';
comment on column t_key_base.keytable
  is 'key关联表';
comment on column t_key_base.keydesc
  is 'Key值描述，该值会直接展示给用户查看';
  
  
  -- Create table
create table t_key_resource
(
  tid     varchar2(30),
  keyid   varchar2(30),
  value   varchar2(1000),
  lupdate date
);
comment on table T_KEY_RESOURCE
  is '扩展字段资源值表';
-- Add comments to the columns 
comment on column t_key_resource.tid
  is '关联表主键ID值';
comment on column t_key_resource.keyid
  is '对应key描述表中的ID';
comment on column t_key_resource.value
  is '具体的内容';
comment on column t_key_resource.lupdate
  is '最后更新时间';
  -- Create/Recreate indexes 
create index idx_tid_kid_1 on T_KEY_RESOURCE (tid, keyid);


  -- Create sequence 
create sequence SEQ_key_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
nocache
cycle;

insert into t_key_base (KEYID, KEYNAME, KEYTABLE, KEYDESC)
values ('1', 'category_pic', 't_r_category', '货架图片');

---------下线删除音乐首页动态区 ---
------以MOPAS数据库用户登录 授权给SSMS使用----
grant  select,delete on t_firstpage_recommend  to &ssms;
grant  select,delete on t_tab_manage  to &ssms;

grant  select on city  to &ssms;
grant  select on province  to &ssms;

---------创建同义词---
create synonym t_firstpage_recommend       for &mopas.t_firstpage_recommend;
create synonym t_tab_manage       for &mopas.t_tab_manage;

create synonym mo_city       for &mopas.city;
create synonym mo_province       for &mopas.province;


-----创业大赛分类货架自动更新规则-重排序；测试修改相应的cid-----
----------------------------------
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('5', '创业大赛分类搞笑重排序', '109990032', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('6', '创业大赛分类生活重排序', '109990033', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('7', '创业大赛分类视频重排序', '109990034', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('8', '创业大赛分类通讯重排序', '109990035', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('9', '创业大赛分类新闻重排序', '109990036', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('10', '创业大赛分类音乐重排序', '109990037', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('11', '创业大赛分类软件重排序', '109990030', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('12', '创业大赛分类游戏重排序', '109990031', 0, 1, 1, '', 'c.GLOBALSCORECOUNT desc  nulls  last', -1, null, to_date('26-05-2011 14:45:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-05-2011 14:18:09', 'dd-mm-yyyy hh24:mi:ss'), 1, '3');

-- 修复阅读基地重复上架问题 ,普通索引变为唯一索引;------
drop index IDX_T_RB_REFERENCE_ID;
create unique index IDX_T_RB_REFERENCE_ID on T_RB_REFERENCE (CATEGORYID, BOOKID);




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.048_SSMS','MM1.0.3.050_SSMS');
commit;