
alter table t_rb_category_new drop column read_status;
alter table t_rb_category_new drop column goods_status;
alter table t_rb_category_new drop column delpro_status;
  
drop table t_rb_category_new_OPERATION;
drop sequence SEQ_RB_APPROVAL_ID;

alter table T_RB_REFERENCE_NEW drop column VERIFY_DATE;
alter table T_RB_REFERENCE_NEW drop column VERIFY_STATUS;
alter table T_RB_REFERENCE_NEW drop column DELFLAG;

alter table t_mb_category_new drop column music_status;
alter table t_mb_category_new drop column goods_status;
alter table t_mb_category_new drop column delpro_status;
  
drop table t_mb_category_new_OPERATION;
drop sequence SEQ_MB_APPROVAL_ID;

alter table T_MB_REFERENCE_NEW drop column VERIFY_DATE;
alter table T_MB_REFERENCE_NEW drop column VERIFY_STATUS;
alter table T_MB_REFERENCE_NEW drop column DELFLAG;



delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.095_SSMS' and LASTDBVERSION = 'MM4.0.0.0.089_SSMS';

commit;