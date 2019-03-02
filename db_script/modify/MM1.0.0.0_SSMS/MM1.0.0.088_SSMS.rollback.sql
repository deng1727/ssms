
-- Create table
drop table T_CONTENT_COUNT;


----最新
update t_caterule_cond set osql='createdate desc,dayOrderTimes desc,mobilePrice desc,name asc' where condtype ='10' and   ruleid in ('88','98','93','3');
----推荐
update t_caterule_cond set osql='dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc' where condtype ='10' and   ruleid in ('86','91','96','4');

drop table t_category_name_mapping;
--还原以前的规则
update t_caterule t set t.ruletype=0 where t.ruleid between 84 and 98;
comment on column T_CATERULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序
；5：处理基地图书运营推荐图书。';
--删除版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.088_SSMS' and LASTDBVERSION = 'MM1.0.0.080_SSMS';
commit;