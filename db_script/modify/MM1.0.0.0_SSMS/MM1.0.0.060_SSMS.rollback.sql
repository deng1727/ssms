
-- drop view
drop view vr_a8_goods;

update t_resource set resourcevalue='货架名称只能包含汉字、字母、_ 和数字' ,remard='只能汉字、英文字母、_ 、数字' where resourcekey='RESOURCE_CATE_FIELD_CHECK_001';



---------------end-----------------
--回滚版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.060_SSMS' and LASTDBVERSION = 'MM1.0.0.053_SSMS';

commit;