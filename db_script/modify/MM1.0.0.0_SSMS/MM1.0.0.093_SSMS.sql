
-----修改了T_R_CATEGORY字段含义

comment on column T_R_CATEGORY.STATE is '货架是否在门户展示，1，展示；0，不展示';
update T_R_CATEGORY  set STATE ='1' where STATE='9';

--修改提供给报表的视图 vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     



-- 规则表增加随机因子
alter table T_CATERULE add randomfactor number(3) default 0 not null;
comment on column T_CATERULE.randomfactor
  is '随机上架因子。产品库上架前是否需要随即排序。0不随机，100大随即，1~99 机型货架小随机';



--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.092_SSMS','MM1.0.0.093_SSMS');
commit;