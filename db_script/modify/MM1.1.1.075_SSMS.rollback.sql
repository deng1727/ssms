
drop table  T_VO_NODEEXT;



delete from t_right where rightid='2_1505_COMIC';
delete from t_roleright where roleid='1' and rightid='2_1505_COMIC'
drop synonym REPORT_V_VIDEO_STAT_D;
drop table v_video_stat_d;
drop table V_VIDEO_STAT_D_TRA;
drop procedure p_V_VIDEO_STAT_D;
drop table  T_VO_NODETYPE;
drop view  v_vo_category;


--------------------------------------------------------------------
-------回滚----------内容同步适配关系同步优化-------------------------
--------------------------------------------------------------------
drop  synonym S_PPMS_CM_DEVICE_PID;
drop  view v_ppms_cm_device_pid;
drop table T_PPMS_CM_DEVICE_PID;
drop  table T_PPMS_CM_DEVICE_PID_TRA;

drop  function f_ppms_cm_device_pid;







delete DBVERSION where PATCHVERSION = 'MM1.1.1.075_SSMS' and LASTDBVERSION = 'MM1.1.1.069_SSMS';
commit;