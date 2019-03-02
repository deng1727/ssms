--在货架管理系统数据库用户&SSMS下授权给各个门户
grant select,insert,delete,update on DBVERSION to &portalmo;--终端门户
grant select on SEQ_DB_VERSION to &portalmo;--终端门户
grant select on V_THIRD_SERVICE to &portalmo;--终端门户

grant select,insert,delete,update on DBVERSION to &portalwww;--www门户
grant select on SEQ_DB_VERSION to &portalwww;--www门户

grant select,insert,delete,update on DBVERSION to &portalpc;--pc门户
grant select on SEQ_DB_VERSION to &portalpc;--pc门户

--在门户数据库用户下创建同义词
create or replace synonym DBVERSION for &SSMS .DBVERSION;
create or replace synonym SEQ_DB_VERSION for &SSMS .SEQ_DB_VERSION;
create or replace synonym V_THIRD_SERVICE for &SSMS .V_THIRD_SERVICE; 

------zcom 数据接入如果ssms和ppms在同一数据库实例下则在PPMS数据库用户下执行
--------同一个实例情况：
-----在PPMS数据库用户下授权给货架
grant select on v_cm_content_zcom to &ssms;--货架系统
grant select on v_cm_device_resource_zcom to &ssms;--货架系统
---在SSMS用户下创建同义词
create synonym ppms_v_cm_content_zcom  for &mm_ppms.v_cm_content_zcom;
create synonym ppms_v_cm_device_resource_zcom  for &mm_ppms.v_cm_device_resource_zcom;
--------同一个实例情况end-------


--------同一个实例情况：
-----在SSMS数据库用户下授权给门户PAS
grant select on T_GAME_BASE to &MOPAS;--终端门户PAS
---在终端PAS用户下创建同义词
create synonym T_GAME_BASE  for &ssms.T_GAME_BASE;
--------同一个实例情况end-------

-------基地音乐独立出来---
------授权给终端门户使用---
grant select on T_MB_MUSIC to &portalmo;--终端门户
grant select on T_MB_CATEGORY to &portalmo;--终端门户
grant select on T_MB_REFERENCE to &portalmo;--终端门户
