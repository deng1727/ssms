--专题权限
insert into t_right
  (rightid, name, descs, levels)
values
  ('2_0601_DISSERTATION', '专题信息管理权限', '专题信息管理权限', 2);
insert into t_right
  (rightid, name, descs, parentid, levels)
values
  ('0_0601_DISS_ADMIN',
   '专题信息CRUD操作权限',
   '专题信息CRUD操作权限',
   '2_0601_DISSERTATION',
   0);
   
--分配角色权限

insert into t_roleright
  (roleid, rightid)
values
  ('1', '2_0601_DISSERTATION');
  
insert into t_roleright 
 (roleid, rightid)
values
 ('1', '0_0601_DISS_ADMIN');
--新建专题表
-- Create table
create table T_DISSERTATION
(
  DISSID       NUMBER(10) not null,
  LOGOURL      VARCHAR2(400) not null,
  DESCR        VARCHAR2(400) not null,
  KEYWORDS     VARCHAR2(50),
  STARTDATE    VARCHAR2(30) not null,
  ENDDATE      VARCHAR2(30) not null,
  CATEGORYID   VARCHAR2(30),
  CATEGORYNAME VARCHAR2(200),
  STATUS       NUMBER(2) default 1 not null,
  DISSNAME     VARCHAR2(400) not null,
  DISSTYPE     VARCHAR2(50) not null,
  RELATION     VARCHAR2(50) not null,
  DISSURL      VARCHAR2(1200) not null
);
-- Add comments to the columns 
comment on column T_DISSERTATION.DISSID
  is '专题ID';
comment on column T_DISSERTATION.LOGOURL
  is 'LOGO图片的URL';
comment on column T_DISSERTATION.DESCR
  is '专题简介';
comment on column T_DISSERTATION.KEYWORDS
  is '专题标签,
多个标签之间用分号分隔，如：
迎春;MM
';
comment on column T_DISSERTATION.STARTDATE
  is '开始有效期，如2011-02-21';
comment on column T_DISSERTATION.ENDDATE
  is '结束有效期，如2011-02-21';
comment on column T_DISSERTATION.CATEGORYID
  is '关联货架ID';
comment on column T_DISSERTATION.CATEGORYNAME
  is '关联货架名称';
comment on column T_DISSERTATION.STATUS
  is '专题状态，
0，未生效
-1，过期
1， 有效';
comment on column T_DISSERTATION.DISSNAME
  is '专题名称';
comment on column T_DISSERTATION.DISSTYPE
  is '专题分类，
分：Theme，Game，Software，可复选多个，之间用半角冒号分隔，如：
theme:Game ';
comment on column T_DISSERTATION.RELATION
  is '主题关联的门户，
M:mo门户,
W:www门户,
P1:wap1.0门户,
P2:wap2.0门户,
P3:waptouch门户
';
comment on column T_DISSERTATION.DISSURL
  is '专题URL';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_DISSERTATION
  add constraint T_DOSS_PK primary key (DISSID);


-- Create sequence 
create sequence SEQ_T_DISSERTATION_ID
minvalue 1000
maxvalue 9999999999
start with 10280
increment by 1
cache 20
cycle;




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.020_SSMS','MM1.0.3.025_SSMS');
commit;