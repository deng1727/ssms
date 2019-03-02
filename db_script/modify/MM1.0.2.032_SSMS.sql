--添加人工干预应用----
create table T_INTERVENOR
(
  ID          VARCHAR2(30) not null,
  NAME        VARCHAR2(300) not null,
  STARTDATE   DATE not null,
  ENDDATE     DATE not null,
  STARTSORTID NUMBER(8) not null,
  ENDSORTID   NUMBER(8) not null
);

comment on column T_INTERVENOR.ID
  is '干预容器编号';
comment on column T_INTERVENOR.NAME
  is '干预容器名称';
comment on column T_INTERVENOR.STARTDATE
  is '干预开始时间';
comment on column T_INTERVENOR.ENDDATE
  is '干预结束时间';
comment on column T_INTERVENOR.STARTSORTID
  is '干预开始排名';
comment on column T_INTERVENOR.ENDSORTID
  is '干预结束排名，如与开始排名相同为固定排名，否则为浮动排名';
  
alter table T_INTERVENOR
  add primary key (ID);  

create table T_INTERVENOR_CATEGORY_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  CATEGORYID   VARCHAR2(30) not null,
  SORTID       NUMBER(3) default 1
);

comment on column T_INTERVENOR_CATEGORY_MAP.INTERVENORID
  is '容器id';
comment on column T_INTERVENOR_CATEGORY_MAP.CATEGORYID
  is '榜单id';
comment on column T_INTERVENOR_CATEGORY_MAP.SORTID
  is '容器排序号';


create table T_INTERVENOR_GCONTENT_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  GCONTENTID   VARCHAR2(30) not null,
  SORTID       NUMBER(8) default 1
);

comment on column T_INTERVENOR_GCONTENT_MAP.INTERVENORID
  is '容器id';
comment on column T_INTERVENOR_GCONTENT_MAP.GCONTENTID
  is '内容id';
comment on column T_INTERVENOR_GCONTENT_MAP.SORTID
  is '内容在容器中的排序';
  
  
create sequence SEQ_INTERVENOR_ID
minvalue 1
maxvalue 9999999999
start with 200
increment by 1
nocache
cycle;


insert into t_right t(t.rightid,t.name,t.descs,t.levels)values('2_1401_INTERVENOR','人工干预管理','人工干预管理',2);
insert into t_right t(t.rightid,t.name,t.descs,t.parentid,t.levels)values('0_1401_INTERVENOR','容器管理','容器管理','2_1401_INTERVENOR',0);
insert into t_right t(t.rightid,t.name,t.descs,t.parentid,t.levels)values('0_1401_CATEGORY','榜单干预管理','榜单干预管理','2_1401_INTERVENOR',0);
insert into T_ROLERIGHT (ROLEID, RIGHTID)values (1, '2_1401_INTERVENOR');


-- Create table
create table T_EXPORT_TOPLIST
(
  ID         NUMBER(2) not null,
  NAME       VARCHAR2(50),
  CATEGORYID VARCHAR2(30) not null,
  COUNT      NUMBER(5) default 30 not null,
  CONDITION  VARCHAR2(200)
);
-- Add comments to the columns 
comment on column T_EXPORT_TOPLIST.ID
  is '榜单专区ID';
comment on column T_EXPORT_TOPLIST.NAME
  is '榜单或专区性质';
comment on column T_EXPORT_TOPLIST.CATEGORYID
  is '货架编码';
comment on column T_EXPORT_TOPLIST.COUNT
  is '获取榜单下最大商品个数';
comment on column T_EXPORT_TOPLIST.CONDITION
  is '榜单获取条件';


insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (1, '免费榜单的软件产品', '100000346', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (2, '免费榜单的主题产品', '100000353', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (3, '免费榜单的游戏产品', '100000348', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (4, '推荐榜单的软件产品', '100000343', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (5, '推荐榜单的主题产品', '100000355', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (6, '推荐榜单的游戏产品', '100000350', 30, '');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (7, '月排行榜单的软件产品', '100000441', 30, 'catename=''软件''');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (8, '月排行榜单的主题产品', '100000441', 30, 'catename=''主题''');

insert into t_export_toplist (ID, NAME, CATEGORYID, COUNT, CONDITION)
values (9, '月排行榜单的游戏产品', '100000441', 30, 'catename=''游戏''');

---删除过度方案
drop table t_new_old_cate_mapping;

--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.125_SSMS','MM1.0.2.032_SSMS');
commit;