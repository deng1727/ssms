
-- Drop columns 
alter table T_A_CM_DEVICE_RESOURCE drop column clientid;
alter table T_A_CM_DEVICE_RESOURCE drop column pkgname;
alter table T_A_CM_DEVICE_RESOURCE drop column versiondesc;

alter table T_A_CM_DEVICE_RESOURCE add CDNURL;

alter table v_cm_device_resource add CDNURL;
alter table v_cm_device_resource_mid add CDNURL;

 update t_r_exportsql t set t.exportsql='SELECT APPID,ACCOUNT,NICKNAME,ONEWORDDESC,PORTALURL FROM CM_FEIXIN',t.exportline=5  where t.id=31;
  
--É¾³ý»üºË±í  
drop table t_dc_checklog;
drop table t_dc_checkdetail;

delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.129_SSMS' and LASTDBVERSION = 'MM2.0.0.0.125_SSMS';

commit;

