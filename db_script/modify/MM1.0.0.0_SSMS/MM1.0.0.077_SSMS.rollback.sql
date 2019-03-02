delete from t_r_base  where id='1020';
delete from t_r_category where id='1020';
update t_r_category c set c.relation='W,O,P' where c.id='701';
drop index index_t_goods_his_goodsid;
--»Ø¹ö°æ±¾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.077_SSMS' and LASTDBVERSION = 'MM1.0.0.073_SSMS';
commit;