-- Add/modify columns 
alter table T_CATERULE modify RULENAME VARCHAR2(30);

drop table t_r_hotapp_order_tra;
drop table t_r_hotapp_order;

drop synonym REPORT_HOTAPP_ORDER_D;

delete from t_caterule_cond_base t where t.base_id in ('651', '620', '624');


delete DBVERSION where PATCHVERSION = 'MM1.1.1.125_SSMS' and LASTDBVERSION = 'MM1.1.1.119_SSMS';
commit;