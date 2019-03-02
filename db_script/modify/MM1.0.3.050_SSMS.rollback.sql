-- rollback 
-- Drop columns 
alter table T_R_CATEGORY drop column PLATFORM;
alter table T_R_CATEGORY drop column CITYID;

----
drop table t_key_base;
drop table t_key_resource;
drop  sequence SEQ_key_ID;

drop synonym t_firstpage_recommend;
drop synonym t_tab_manage;

drop synonym mo_city;
drop synonym mo_province;

drop table T_GOODS_CHANGE_HIS;


delete t_category_carveout_rule t where t.id in ('5','6','7','8','9','10','11','12');

delete DBVERSION where PATCHVERSION = 'MM1.0.3.050_SSMS' and LASTDBVERSION = 'MM1.0.3.048_SSMS';
commit;