--���MM.PPMS��MM.SSMS��ͬһ���ݿ�ʵ���£���MM PPMS�û���ִ��������Ȩ�ű���
 
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

-- T_PPS_COMMENT_GRADE��ͼҪ��Ȩ��SSMS�û�ʹ�á�


--1.1 ���PORTALCOMMON��MM.SSMS��ͬһ���ݿ�ʵ���£���PORTALCOMMON�û���ִ��������Ȩ�ű���
 
ACCEPT ssms_db_user CHAR prompt 'input MM.SSMS DB username:' 

grant select on T_PPS_COMMENT_GRADE        to &pas_db_user with grant option;