


drop table   T_R_APPCATE_EN;



drop synonym PPMS_V_CM_CONTENT_PROMOTION;

drop procedure proc_V_CM_CONTENT_PROMOTION;

drop synonym PPMS_V_CM_CONTENT_CONLEVEL;

drop procedure proc_V_CM_CONTENT_CONLEVEL;

drop synonym REPORT_DOWN_D;

drop table t_portal_down_d ;


delete DBVERSION where PATCHVERSION = 'MM1.0.3.125_SSMS' and LASTDBVERSION = 'MM1.0.3.120_SSMS';
commit;