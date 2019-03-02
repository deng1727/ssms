insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.095_SSMS','MM1.0.3.100_SSMS');

create table t_category_single
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2),
  userId VARCHAR2(100),
  type             VARCHAR2(500)
);

comment on column t_category_single.ID is '货架内码';
comment on column t_category_single.NAME is '货架名称';
comment on column t_category_single.CATEGORYID is '货架ID';
comment on column t_category_single.PARENTCATEGORYID is '货架父ID';
comment on column t_category_single.CATEGORYTYPE is '搜索类型：0,只取本货架信息;1,取本货架信息以及一级子货架信息;9,取本货架信息以及所有子孙货架信息';
comment on column t_category_single.userId is '用户组编码，与t_category_usergroup表中的code对应';
comment on column t_category_single.type is '用户组类型,1：货架分类，2：货架商品';

create table t_category_usergroup
(
  code             VARCHAR2(100),
  NAME             VARCHAR2(500),
  type             VARCHAR2(500)
);
comment on column t_category_usergroup.code is '用户组编码';
comment on column t_category_usergroup.NAME is '用户组名称';
comment on column t_category_usergroup.type is '用户组类型,1：货架分类，2：货架商品';

insert into t_category_usergroup(code,name,type) values('all_category_class','所有货架分类用户组','1');
insert into t_category_usergroup(code,name,type) values('all_category_content','所有货架商品用户组','2');


insert into t_category_single(id,name,CATEGORYID,PARENTCATEGORYID,CATEGORYTYPE,userid,type) (select t0.id, t1.name,t1.categoryID,'0' PARENTCATEGORYID,'9' CATEGORYTYPE,'all_category_class' userid,'1' type from t_r_base t0, t_r_category t1 where t1.id = t0.id and t0.type like 'nt:category%' and t1.delFlag != '1' and t0.parentid = '701');
insert into t_category_single(id,name,CATEGORYID,PARENTCATEGORYID,CATEGORYTYPE,userid,type) (select t0.id, t1.name,t1.categoryID,'0' PARENTCATEGORYID,'9' CATEGORYTYPE,'all_category_content' userid,'2' type from t_r_base t0, t_r_category t1 where t1.id = t0.id and t0.type like 'nt:category%' and t1.delFlag != '1' and t0.parentid = '701');

-- Create table
create table t_r_SevenDaysCompared
(
  contentId VARCHAR2(30) not null,
  oldNumber number(10),
  newNumber number(10)
)
;
-- Add comments to the columns 
comment on column t_r_SevenDaysCompared.contentId
  is '内容内码，对应彩铃的铃音编码，对应资讯的媒体ID，对应全曲的歌曲ID。';
comment on column t_r_SevenDaysCompared.oldNumber
  is '上一次七天下载量统计';
comment on column t_r_SevenDaysCompared.newNumber
  is '当前七天下载量统计';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_r_SevenDaysCompared
  add constraint p_SevenDays_pk primary key (CONTENTID);

-- Add/modify columns 
alter table T_R_GCONTENT add comparedNumber NUMBER(10) default 0;
-- Add comments to the columns 
comment on column T_R_GCONTENT.comparedNumber
  is '七天下载量变化量';


--------------------------中间语句在现网中不用执行
create table t_r_sevenday
(
  contentId VARCHAR2(30) not null,
  orderById number(10)
)
;
-- Add comments to the columns 
comment on column t_r_sevenday.contentId
  is '内容内码，对应彩铃的铃音编码，对应资讯的媒体ID，对应全曲的歌曲ID。';
comment on column t_r_sevenday.orderById
  is '当前七天下载量统计';

--------------------------中间语句在现网中不用执行

commit;


