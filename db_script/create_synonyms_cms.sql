--如果MM.PPMS和MM.SSMS在同一数据库实例下，在MM SSMS用户下执行如下创建同义词脚本：
 
ACCEPT ppms_db_user CHAR prompt 'input MM.PPMS DB username:' 

create synonym CM_CT_APPGAME        for &ppms_db_user .CM_CT_APPGAME       ;
create synonym CM_CT_APPSOFTWARE    for &ppms_db_user .CM_CT_APPSOFTWARE   ;
create synonym CM_CT_APPTHEME       for &ppms_db_user .CM_CT_APPTHEME      ;
create synonym CM_CT_PROGRAM        for &ppms_db_user .CM_CT_PROGRAM       ;
create synonym PPMS_CM_DEVICE_RESOURCE for &ppms_db_user .V_CM_DEVICE_RESOURCE;
create synonym CM_CONTENT_TYPE      for &ppms_db_user .CM_CONTENT_TYPE     ;
create synonym CM_CATALOG           for &ppms_db_user .CM_CATALOG          ;
create synonym CM_CONTENT           for &ppms_db_user .CM_CONTENT          ;
create synonym OM_PRODUCT_CONTENT   for &ppms_db_user .OM_PRODUCT_CONTENT  ;
create synonym OM_COMPANY           for &ppms_db_user .OM_COMPANY          ;
create synonym T_DEVICE             for &ppms_db_user .T_DEVICE            ;
create synonym T_DEVICE_OS          for &ppms_db_user .T_DEVICE_OS         ;
create synonym OM_DICTIONARY        for &ppms_db_user .OM_DICTIONARY       ;
create synonym CM_IMAGE_PROPERTIES  for &ppms_db_user .CM_IMAGE_PROPERTIES ;
create synonym CM_VIDEO_PROPERTIES  for &ppms_db_user .CM_VIDEO_PROPERTIES ;
create synonym CM_FILE_PROPERTIES   for &ppms_db_user .CM_FILE_PROPERTIES  ;
create synonym V_OM_PRODUCT         for &ppms_db_user .V_OM_PRODUCT        ;
create synonym CM_CT_DEVICE         for &ppms_db_user .CM_CT_DEVICE        ;
create synonym T_DEVICE_BRAND       for &ppms_db_user .T_DEVICE_BRAND        ;


--如果MM.PPMS和MM.SSMS不在同一数据库实例下，执行如下创建dblink以及同义词脚本，名称根据实际情况替换。
create database link PPMSTOSSMS connect to MM_CMS identified by MM_CMS using 'DB3_9';

create or replace synonym CM_CT_APPGAME        for CM_CT_APPGAME@PPMSTOSSMS;
create or replace synonym CM_CT_APPSOFTWARE    for CM_CT_APPSOFTWARE@PPMSTOSSMS;
create or replace synonym CM_CT_APPTHEME       for CM_CT_APPTHEME@PPMSTOSSMS;
create or replace synonym CM_CT_PROGRAM        for CM_CT_PROGRAM@PPMSTOSSMS;
create or replace synonym PPMS_CM_DEVICE_RESOURCE for V_CM_DEVICE_RESOURCE@PPMSTOSSMS;
create or replace synonym CM_CONTENT_TYPE      for CM_CONTENT_TYPE@PPMSTOSSMS;
create or replace synonym CM_CATALOG           for CM_CATALOG@PPMSTOSSMS;
create or replace synonym CM_CONTENT           for CM_CONTENT@PPMSTOSSMS;
create or replace synonym OM_PRODUCT_CONTENT   for OM_PRODUCT_CONTENT@PPMSTOSSMS;
create or replace synonym OM_COMPANY           for OM_COMPANY@PPMSTOSSMS;
create or replace synonym T_DEVICE             for T_DEVICE@PPMSTOSSMS;
create or replace synonym T_DEVICE_OS          for T_DEVICE_OS@PPMSTOSSMS;
create or replace synonym OM_DICTIONARY        for OM_DICTIONARY@PPMSTOSSMS;
create or replace synonym CM_IMAGE_PROPERTIES  for CM_IMAGE_PROPERTIES@PPMSTOSSMS;
create or replace synonym CM_VIDEO_PROPERTIES  for CM_VIDEO_PROPERTIES@PPMSTOSSMS;
create or replace synonym CM_FILE_PROPERTIES   for CM_FILE_PROPERTIES@PPMSTOSSMS;
create or replace synonym V_OM_PRODUCT         for V_OM_PRODUCT@PPMSTOSSMS;
create or replace synonym CM_CT_DEVICE         for CM_CT_DEVICE@PPMSTOSSMS;
create or replace synonym T_DEVICE_BRAND       for T_DEVICE_BRAND@PPMSTOSSMS;
----为ZCOM创建同义词
create or replace synonym ppms_v_cm_content_zcom        for v_cm_content_zcom@PPMSTOSSMS;
create or replace synonym ppms_v_cm_device_resource_zcom        for v_cm_device_resource_zcom@PPMSTOSSMS;
--如果PORTALCOMMON和MM.SSMS在同一数据库实例下，在MM SSMS用户下执行如下创建同义词脚本：
 
ACCEPT db_user CHAR prompt 'input PORTALCOMMON DB username:' 
create or replace synonym T_PPS_COMMENT_GRADE         for &db_user .T_PPS_COMMENT_GRADE      ;

--使用原来创建的dblink：PPMSTOSSMS创建同义词
create or replace synonym ppms_v_cm_content_recommend for v_cm_content_recommend@PPMSTOSSMS;
----ap过有效期过滤应用
--使用原来创建的dblink：PPMSTOSSMS创建同义词
create or replace synonym v_valid_company for v_valid_company@PPMSTOSSMS;

--- 标签信息接入 使用原来创建的dblink：DL_PPMS_DEVICE创建同义词，根据现网配置写的，
create or replace synonym ppms_v_cm_content_tag for v_cm_content_tag@DL_PPMS_DEVICE;
create or replace synonym ppms_v_om_tag for v_om_tag@DL_PPMS_DEVICE;