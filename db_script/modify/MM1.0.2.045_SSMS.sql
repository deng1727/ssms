-- 同步结果表
create table T_SYN_RESULT
(
  ID          NUMBER(10) not null,
  CONTENTID   VARCHAR2(30) not null,
  CONTENTNAME VARCHAR2(300) not null,
  SYNTYPE     NUMBER(2) not null,
  SYNTIME     VARCHAR2(30) not null,
  constraint T_SYN_RESULT primary key (ID)
);

-- Add comments to the columns 
comment on column T_SYN_RESULT.CONTENTID
  is '内容ID';
comment on column T_SYN_RESULT.CONTENTNAME
  is '内容名称';
comment on column T_SYN_RESULT.SYNTYPE
  is '同步类型: 1上线;2更新; 3下线; 4失败 ';
comment on column T_SYN_RESULT.SYNTIME
  is '同步时间：YYYY-MM-DD hh24:mm:ss';

declare 
ruleid number(8);
begin
--本地化应用软件更新规则
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'本地化应用软件',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,10,'type=''nt:gcontent:appSoftWare'' and cityid <> ''0000''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('28731389',ruleid,null,sysdate);
--基地游戏精品单机更新规则
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'基地游戏精品单机',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,11,'chargetime=''1''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('28731388',ruleid,null,sysdate);
--基地游戏试玩专区更新规则
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'基地游戏试玩专区',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,11,'chargetime=''2''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('21665492',ruleid,null,sysdate);
commit;
end;
/
  
-- 同步结果表序列id  
create sequence SEQ_T_SYN_RESULT_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;

-- 添加 体验营销 榜单数据导出推荐、免费、月排行 货架categoryid和货架名称
create table T_CATEGORY_TRAIN
(
  CID   VARCHAR2(30),
  CNAME VARCHAR2(200)
);
-- Create/Recreate indexes 
create index CID_INDEX1 on T_CATEGORY_TRAIN (CID);
insert into t_category_train (CID, CNAME)
values ('100000585', '免费');

insert into t_category_train (CID, CNAME)
values ('100000584', '推荐');

insert into t_category_train (CID, CNAME)
values ('100000743', '月排行');



-------提供给搜索子系统视图
----mo
CREATE OR REPLACE VIEW V_MO_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%O%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;
   
----www
CREATE OR REPLACE VIEW V_WWW_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%W%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;

   
-----wap
CREATE OR REPLACE VIEW V_WAP_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%A%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;







insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.040_SSMS','MM1.0.2.045_SSMS');
commit;