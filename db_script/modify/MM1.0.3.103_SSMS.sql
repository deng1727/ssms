-- 加两个字段。

alter table t_r_gcontent add funcdesc varchar2(2000);
alter table t_r_category add multiurl varchar2(1000);

-- Add comments to the columns 
comment on column t_r_gcontent.funcdesc
  is '新功能介绍';
comment on column t_r_category.multiurl
  is '货架URL为了支持终端门户混排url';



-- Create table
create table t_caterule_cond_base
(
  BASE_ID   NUMBER(8) not null,
  BASE_NAME VARCHAR2(100) not null,
  BASE_SQL  VARCHAR2(1000) not null
)
;
-- Add comments to the columns 
comment on column t_caterule_cond_base.base_id
  is '基础语句编号';
comment on column t_caterule_cond_base.base_name
  is '基础语句名称';
comment on column t_caterule_cond_base.base_sql
  is '基础语句';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CATERULE_COND_BASE
  add constraint P_COND_BASE_PK primary key (BASE_ID);


-- Add/modify columns 
alter table T_CATERULE_COND add baseCondId NUMBER(8) default 0 not null;
-- Add comments to the columns 
comment on column T_CATERULE_COND.baseCondId
  is '基础条件类型sql语句';


update t_caterule_cond t set t.basecondid = t.condtype;


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (1, '从精品库获取', 'select b.id from  t_r_base b,t_r_reference r,  t_r_gcontent g,v_service v where b.id=g.id and r.categoryid =? and r.refnodeid = g.id and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')  and g.icpcode = v.icpcode(+) and g.icpservid = v.icpservid(+)');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (10, '从产品库获取自有业务（软件，游戏，主题）不包含基地游戏', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider !=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (11, '从产品库中取基地游戏业务', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (12, '从产品库获取非自有业务', 'select id from t_r_gcontent g where g.subtype is null or  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (13, '从品牌店中取套餐业务', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+) and g.provider != ''B'' and g.subtype = ''11''');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (14, '从基础业务获取', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+)');



-- Create table
drop table T_R_SEVENDAYSCOMPARED;

create table T_R_SEVENDAYSCOMPARED
(
  CONTENTID  VARCHAR2(30) not null,
  AOLDNUMBER NUMBER(10),
  ANEWNUMBER NUMBER(10),
  OOLDNUMBER NUMBER(10),
  ONEWNUMBER NUMBER(10)
);

-- Add comments to the columns 
comment on column T_R_SEVENDAYSCOMPARED.CONTENTID
  is '内容内码，对应彩铃的铃音编码，对应资讯的媒体ID，对应全曲的歌曲ID。';
comment on column T_R_SEVENDAYSCOMPARED.AOLDNUMBER
  is '安卓操作系统上一次七天下载量统计';
comment on column T_R_SEVENDAYSCOMPARED.ANEWNUMBER
  is '安卓操作系统当前七天下载量统计';
comment on column T_R_SEVENDAYSCOMPARED.OOLDNUMBER
  is 'ophone操作系统上一次七天下载量统计';
comment on column T_R_SEVENDAYSCOMPARED.ONEWNUMBER
  is 'ophone操作系统当前七天下载量统计';


  -- Create the synonym 
create or replace synonym report_servenday
  for V_PPS_PLATFORM_DOWN_D@REPORT105.ORACLE.COM;

create table t_r_servenday_temp as
  select * from report_servenday where 1 = 2;


-- Create table
create table T_R_SEVENCOMPARED
(
  CONTENTID VARCHAR2(30) not null,
  ONUMBER   NUMBER(10),
  ANUMBER   NUMBER(10)
)
;
-- Add comments to the columns 
comment on column T_R_SEVENCOMPARED.CONTENTID
  is '内容内码，对应彩铃的铃音编码，对应资讯的媒体ID，对应全曲的歌曲ID。';
comment on column T_R_SEVENCOMPARED.ONUMBER
  is 'ophone操作系统当前七天下载量差值统计';
comment on column T_R_SEVENCOMPARED.ANUMBER
  is '安卓操作系统当前七天下载量差值统计';


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.100_SSMS','MM1.0.3.103_SSMS');
commit;


