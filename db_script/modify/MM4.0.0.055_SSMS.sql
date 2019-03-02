-----安卓全量应用货架自动更新初始化数据开始-----
declare 
  cateruleid number(8);
begin

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(84,'安卓全量应用数据','select b.id from t_r_base b,t_r_gcontent g,v_service v where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.provider != ''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'安卓全量应用',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1225519609',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.icpcode != ''139123'' and g.companyid != ''278908''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,84);

insert into t_a_auto_category(id,categoryid,isnulltosync) values(92,'100038687','1');

commit;
end;

-----安卓全量应用货架自动更新初始化数据结束-----

-----------调整榜单干预t_bid_inter表的param值的精确度开始----------
-------备份t_bid_inter表------
create table t_bid_inter_bak as select * from t_bid_inter;
-------更新t_bid_inter表的param字段为null------
update t_bid_inter set param = null;
-------修改t_bid_inter表的param字段精确度------
alter table t_bid_inter modify param number(8,6);
-------把备份表中的param字段值更新回原表的字段值------
update t_bid_inter t set t.param = (select b.param from t_bid_inter_bak b where t.id = b.id);
-------删除备份表----
drop table t_bid_inter_bak;

-----------调整榜单干预t_bid_inter表的param值的精确度结束----------

------------创建客户端重点应用标签表---------
-- Create table
create table t_key_content_tag
(
  contentid    VARCHAR2(30) not null,
  tagname  VARCHAR2(50)
);
comment on table t_key_content_tag
  is '客户端重点应用标签表';
comment on column t_key_content_tag.contentid
  is '内容ID';
comment on column t_key_content_tag.tagname
  is '标签名称';
alter table t_key_content_tag
  add constraint PK_t_key_content_tag_ID primary key (contentid);


------------创建重点应用监控表---------
-- Create table
create table t_pivot_app_monitor
(
  type   VARCHAR2(2),
  appid  VARCHAR2(30),
  packagename VARCHAR2(255),
  name VARCHAR2(255)
);
comment on table t_pivot_app_monitor
  is '重点应用监控表';
comment on column t_pivot_app_monitor.type
  is '监控类型：1-MM应用,2-汇聚应用';
comment on column t_pivot_app_monitor.appid
  is 'Appid（MM应用必填、汇聚应用可选）';
comment on column t_pivot_app_monitor.packagename
  is 'Packagename（汇聚应用必填、MM应用可选）';
comment on column t_pivot_app_monitor.name
  is '应用名称';


------------创建重点应用监控结果表---------
-- Create table

create table t_pivot_app_monitor_result
(
  type   VARCHAR2(2),
  appid  VARCHAR2(30),
  packagename VARCHAR2(255),
  name VARCHAR2(255),
  versionname VARCHAR2(50),
  updatedate VARCHAR2(30),
  hj_state VARCHAR2(1) default '0',
  hj_state_updatedate date default sysdate,
  ss_state VARCHAR2(1) default '0',
  ss_state_updatedate date default sysdate,
  dc_state VARCHAR2(1) default '0',
  dc_state_updatedate date default sysdate
);
comment on table t_pivot_app_monitor_result
  is '重点应用监控结果表';
comment on column t_pivot_app_monitor_result.type
  is '监控类型：1-MM应用,2-汇聚应用';
comment on column t_pivot_app_monitor_result.appid
  is 'Appid（MM应用必填、汇聚应用可选）';
comment on column t_pivot_app_monitor_result.packagename
  is 'Packagename（汇聚应用必填、MM应用可选）';
comment on column t_pivot_app_monitor_result.name
  is '应用名称';
comment on column t_pivot_app_monitor_result.versionname
  is '版本version name';
comment on column t_pivot_app_monitor_result.updatedate
  is '应用更新时间';
comment on column t_pivot_app_monitor_result.hj_state
  is '是否已输出至货架（0-是，1-否）';
comment on column t_pivot_app_monitor_result.hj_state_updatedate
  is '货架状态更新时间';
comment on column t_pivot_app_monitor_result.dc_state
  is '是否已输出至客户端门户（0-是，1-否）';
comment on column t_pivot_app_monitor_result.dc_state_updatedate
  is '客户端门户状态更新时间';
comment on column t_pivot_app_monitor_result.ss_state
  is '是否已输出至搜索（0-是，1-否）';
comment on column t_pivot_app_monitor_result.ss_state_updatedate
  is '搜索状态更新时间';
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.049_SSMS','MM4.0.0.0.055_SSMS');

 
commit;

