
---删除同步 模糊适配信息给门户的 v_match_device_resource 视图
drop view v_match_device_resource;


--删除版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.090_SSMS' and LASTDBVERSION = 'MM1.0.0.088_SSMS';
commit;