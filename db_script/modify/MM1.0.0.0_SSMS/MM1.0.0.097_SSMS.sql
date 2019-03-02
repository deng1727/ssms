-- 基地游戏包信息表
-- Create table
create table T_GAME_BASE
(
  PKGID       VARCHAR2(25) not null,
  PKGNAME     VARCHAR2(64) not null,
  PKGDESC     VARCHAR2(512) not null,
  CPNAME      VARCHAR2(64),
  SERVICECODE VARCHAR2(30),
  FEE         NUMBER not null,
  PKGURL      VARCHAR2(300) not null,
  PICURL1     VARCHAR2(255) not null,
  PICURL2     VARCHAR2(255) not null,
  PICURL3     VARCHAR2(255) not null,
  PICURL4     VARCHAR2(255) not null,
  UPDATETIME  DATE default sysdate not null,
  CREATETIME  DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_GAME_BASE.PKGID
  is '游戏包id';
comment on column T_GAME_BASE.PKGNAME
  is '游戏包名称';
comment on column T_GAME_BASE.PKGDESC
  is '游戏包介绍';
comment on column T_GAME_BASE.CPNAME
  is '发行商';
comment on column T_GAME_BASE.SERVICECODE
  is '业务代码';
comment on column T_GAME_BASE.FEE
  is '费率';
comment on column T_GAME_BASE.PKGURL
  is '游戏包入口URL';
comment on column T_GAME_BASE.PICURL1
  is '规格 30x30 picture1';
comment on column T_GAME_BASE.PICURL2
  is '规格 34x34 picture2';
comment on column T_GAME_BASE.PICURL3
  is '规格 50x50 picture3';
comment on column T_GAME_BASE.PICURL4
  is '规格 65x65 picture4';
comment on column T_GAME_BASE.UPDATETIME
  is '最近操作时间';
comment on column T_GAME_BASE.CREATETIME
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_BASE
  add primary key (PKGID);

--------同一个实例情况：
-----在SSMS数据库用户下授权给门户PAS
grant select on T_GAME_BASE to &MOPAS;--终端门户PAS
---在终端PAS用户下创建同义词
create synonym T_GAME_BASE  for &ssms.T_GAME_BASE;
--------同一个实例情况end-------




-- Create table创建货架监控基础货架表
create table T_CATEGORY_MONITOR
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2)
);
create index INDEX_MONITOR_CATEGORYID on T_CATEGORY_MONITOR (CATEGORYID);
-- Add comments to the columns 
comment on column T_CATEGORY_MONITOR.ID
  is '货架内码ID';
comment on column T_CATEGORY_MONITOR.NAME
  is '货架名称';
comment on column T_CATEGORY_MONITOR.CATEGORYID
  is '货架ID';
comment on column T_CATEGORY_MONITOR.PARENTCATEGORYID
  is '货架父ID';
comment on column T_CATEGORY_MONITOR.CATEGORYTYPE
  is '搜索类型：0,只取本货架信息;1,取本货架信息以及一级子货架信息;9,取本货架信息以及所有子孙货架信息';

---初始化基础货架数据
insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257453', '终端集团根货架', '100000339', '0', '0');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1810801', 'WWW门户根货架', '100000434', '0', '0');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2001', '全部', '100000008', '100000341', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1782547', '热卖场', '100000384', '100000339', '9');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257454', '主题', '100000340', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257455', '软件', '100000341', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257456', '游戏', '100000342', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2003', '全部', '100000010', '100000342', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2002', '全部', '100000009', '100000340', '1');

--重点机型货架轮换率重复率统计
--建立货架目录临时表，保存货架目录 现网的结构，测试需要另外初始化
create table r_static_category_list (listname1 varchar2(100),listname2 varchar2(100),listname3 varchar2(100),listcat1 varchar2(20),listcat2 varchar2(20),categoryid varchar2(20));
--插入货架目录 初始化 榜单下面的机型货架
insert into r_static_category_list(listcat2,listname3,categoryid) select a.parentcategoryid,a.name,a.categoryid from t_r_category a where a.delflag=0 and a.state='1'  and  a.parentcategoryid in ('100000346',
'100000348',
'100000353',
'100000345',
'100000349',
'100000354',
'100000343',
'100000350',
'100000355',
'100000344',
'100000352',
'100000357',
'100000347',
'100000351',
'100000356');
--设置父货架与根货架名称
update r_static_category_list a set a.listname2=(select b.name from t_r_category b where a.listcat2=b.categoryid);
update r_static_category_list a set a.listcat1=(select b.parentcategoryid from t_r_category b where a.listcat2=b.categoryid);
update r_static_category_list a set a.listname1=(select b.name from t_r_category b where a.listcat1=b.categoryid);
--创建榜单历史表
create table r_charts_turnhis(phdate varchar2(10),id varchar2(20),categoryid varchar2(20),rowlist number);

--建立临时表1，前30 存储今天比昨天变化的应用数量
create table r_charts_turn01 as 
select a.categoryid,count(*) difcounts from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<31
and a.id not in (select b.id from r_charts_turnhis b 
where b.categoryid=a.categoryid and b.phdate=(to_char(sysdate-1,'yyyymmdd'))and b.rowlist<31) group by a.categoryid;
---建立临时表2，前30 统计今天所有榜单的应用数
create table r_charts_turn02 as select a.categoryid,0 difcounts,count(*) counts,0 allcounts 
from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<31
group by a.categoryid;
--建立临时表1，前15 存储今天比昨天变化的应用数量
create table r_charts_turn101 as 
select a.categoryid,count(*) difcounts from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<16
and a.id not in (select b.id from r_charts_turnhis b 
where b.categoryid=a.categoryid and b.phdate=(to_char(sysdate-1,'yyyymmdd'))and b.rowlist<16) group by a.categoryid;
---建立临时表2，前15 统计今天所有榜单的应用数
create table r_charts_turn102 as select a.categoryid,0 difcounts,count(*) counts,0 allcounts 
from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<16
group by a.categoryid;

-----5802数据统计表
--将今天的数据插入历史表
create table  r_charts01 as select t.sortid,t.refnodeid,t.categoryid,to_char(sysdate,'yyyymmdd') phdate from t_r_reference t where 1 =2;
insert into  r_charts01 
select t.sortid,t.refnodeid,t.categoryid,to_char(sysdate,'yyyymmdd') phdate from t_r_reference t where t.categoryid in ('100000346',
'100000348',
'100000353',
'100000345',
'100000349',
'100000354',
'100000343',
'100000350',
'100000355',
'100000344',
'100000352',
'100000357',
'100000347',
'100000351',
'100000356');

----将5802的榜单插入历史表
create table   r_charts02  as
select a.sortid,a.refnodeid,a.categoryid,a.phdate,b.catename,b.name,'未知类型' catname from r_charts01 a,t_r_gcontent b where a.refnodeid=b.id 
and (b.devicename like '%5802%' or b.devicename02 like '%5802%')  and a.phdate in (to_char(sysdate,'yyyymmdd'),to_char(sysdate-1,'yyyymmdd')) 
order by a.sortid desc;

--将5802的的榜单序号标注
create table r_charts03 as 
select  b.phdate,b.name,b.categoryid,b.catename,b.catname,
row_number() over (partition by b.categoryid,b.phdate order by b.sortid desc) rowlist
from  r_charts02 b;






--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.096_SSMS','MM1.0.0.097_SSMS');
commit;