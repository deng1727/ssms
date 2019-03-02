

alter table t_cb_category drop column anime_status;
alter table t_cb_category drop column goods_status;
alter table t_cb_category drop column delpro_status;
  
drop table T_CB_CATEGORY_OPERATION;
drop sequence SEQ_CB_APPROVAL_ID;

alter table t_cb_reference drop column VERIFY_DATE;
alter table t_cb_reference drop column VERIFY_STATUS;
alter table t_cb_reference drop column DELFLAG;

alter table t_vo_category drop column video_status;
alter table t_vo_category drop column goods_status;
alter table t_vo_category drop column delpro_status;

drop table T_VO_CATEGORY_OPERATION;
drop sequence SEQ_VO_APPROVAL_ID;

alter table t_vo_reference drop column VERIFY_DATE;
alter table t_vo_reference drop column VERIFY_STATUS;
alter table t_vo_reference drop column DELFLAG;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.089_SSMS' and LASTDBVERSION = 'MM4.0.0.0.085_SSMS';

commit;