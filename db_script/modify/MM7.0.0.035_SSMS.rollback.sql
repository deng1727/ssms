alter table t_r_gcontent drop column RiskTag;

alter table t_r_gcontent drop column AppType ;


alter table t_r_gcontent drop column CtrlDev;

alter table t_a_cm_device_resource drop column RISKTAG ;

delete from t_r_exportsql where id = '103';

delete from t_r_exportsql where id = '104';

delete from T_R_EXPORTSQL_GROUP where id ='20';

delete from T_R_EXPORTSQL_GROUP where id ='21';


commit ;