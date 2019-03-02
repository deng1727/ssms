drop table T_exigence_Lasttime;
drop table t_content_whitelist;
drop table t_rb_moDirectory_new;
drop table t_rb_comment_new;
drop table t_rb_comment_new_tra;
drop synonym REPORT_COMMENT;
drop synonym PPMS_V_CM_CONTENT_UPGRADE;

delete DBVERSION where PATCHVERSION = 'MM1.1.1.098_SSMS' and LASTDBVERSION = 'MM1.1.1.095_SSMS';
commit;