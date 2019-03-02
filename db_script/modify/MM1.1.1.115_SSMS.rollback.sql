drop view vr_mo_app_category;
drop table V_CATEGORY_SORTID;
drop table V_CATEGORY_SORTID_TRA;
drop procedure p_category_sortid;


delete DBVERSION where PATCHVERSION = 'MM1.1.1.115_SSMS' and LASTDBVERSION = 'MM1.1.1.109_SSMS';
commit;