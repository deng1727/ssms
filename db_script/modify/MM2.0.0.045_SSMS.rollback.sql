drop sequence SEQ_T_A_MESSAGES;
drop table T_A_MESSAGES;
drop table T_A_PPMS_RECEIVE;
drop sequence SEQ_T_A_PPMS_RECEIVE_CHANGE;
drop table T_A_PPMS_RECEIVE_CHANGE;
drop table T_A_PUSH;
drop table T_A_PUSHREPORT;
drop table T_A_CONTENT_DOWNCOUNT;
drop table T_A_CM_DEVICE_RESOURCE;
drop synonym V_DATACENTER_CM_CONTENT;
drop synonym V_DC_CM_DEVICE_RESOURCE;
drop table t_a_android_list;



delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.045_SSMS' and LASTDBVERSION = 'MM1.1.1.159_SSMS';
commit;