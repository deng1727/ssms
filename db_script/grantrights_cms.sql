--如果MM.PPMS和MM.SSMS在同一数据库实例下，在MM PPMS用户下执行如下授权脚本：
 
ACCEPT ssms_db_user CHAR prompt 'input MM.SSMS DB username:' 

grant select on CM_CT_APPGAME        to &ssms_db_user with grant option;
grant select on CM_CT_APPTHEME       to &ssms_db_user with grant option;
grant select on CM_CT_APPSOFTWARE    to &ssms_db_user with grant option;
grant select on CM_CT_PROGRAM        to &ssms_db_user with grant option;
grant select on V_CM_DEVICE_RESOURCE to &ssms_db_user with grant option;
grant select on CM_CONTENT_TYPE      to &ssms_db_user with grant option;
grant select on CM_CATALOG           to &ssms_db_user with grant option;
grant select on CM_CONTENT           to &ssms_db_user with grant option;
grant select on OM_COMPANY           to &ssms_db_user with grant option;
grant select on T_DEVICE_OS          to &ssms_db_user with grant option;
grant select on T_DEVICE             to &ssms_db_user with grant option;
grant select on OM_DICTIONARY        to &ssms_db_user with grant option;
grant select on CM_IMAGE_PROPERTIES  to &ssms_db_user with grant option;
grant select on CM_VIDEO_PROPERTIES  to &ssms_db_user with grant option;
grant select on CM_FILE_PROPERTIES   to &ssms_db_user with grant option;
grant select on V_OM_PRODUCT         to &ssms_db_user with grant option;
grant select on OM_PRODUCT_CONTENT   to &ssms_db_user with grant option;
grant select on CM_CT_DEVICE         to &ssms_db_user with grant option;
grant select on T_DEVICE_BRAND       to &ssms_db_user with grant option;

-- T_PPS_COMMENT_GRADE视图要授权给SSMS用户使用。


--1.1 如果PORTALCOMMON和MM.SSMS在同一数据库实例下，在PORTALCOMMON用户下执行如下授权脚本：
 
ACCEPT ssms_db_user CHAR prompt 'input MM.SSMS DB username:' 

grant select on T_PPS_COMMENT_GRADE        to &pas_db_user with grant option;