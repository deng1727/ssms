-- Drop columns 
drop table t_ap_warn_detail;

drop synonym report_ondemand_order;
drop synonym REPORT_province;
drop synonym REPORT_city;

drop view v_om_dictionary;
drop table t_v_om_dictionary;

delete DBVERSION where PATCHVERSION = 'MM1.0.2.100_SSMS' and LASTDBVERSION = 'MM1.0.2.095_SSMS';
commit;