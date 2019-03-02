
alter table T_A_CM_DEVICE_RESOURCE drop column cermd5;
alter table t_r_category drop column classify_status;
alter table t_r_category drop column goods_status;
alter table t_r_category drop column delpro_status;
alter table t_r_reference drop column delflag;
comment on column T_R_REFERENCE.VERIFY_STATUS
  is '审核状态-- 0 审核中；1 审核通过；2 审核不通过';
  
drop table T_R_CATEGORY_OPERATION;
drop table T_R_LOGGER;
drop table T_EMAIL_ADDRESS;
drop sequence SEQ_APPROVAL_ID;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.079_SSMS' and LASTDBVERSION = 'MM4.0.0.0.075_SSMS';

commit;