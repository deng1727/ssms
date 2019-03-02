----回滚动定时导出货架商品的货架表

-- drop table
drop table T_CATEGORY_EXPORT;


---------------end-----------------
--回滚版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.053_SSMS' and LASTDBVERSION = 'MM1.0.0.042_SSMS';

commit;