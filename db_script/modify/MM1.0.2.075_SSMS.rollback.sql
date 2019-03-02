comment on column T_R_GCONTENT.PROGRAMSIZE
  is '程序安装包大小，单位为K';


drop view ap_warn_view;

drop table T_AP_WARN;


delete DBVERSION where PATCHVERSION = 'MM1.0.2.075_SSMS' and LASTDBVERSION = 'MM1.0.2.070_SSMS';
commit;