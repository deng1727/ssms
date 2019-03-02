
drop sequence SEQ_ZCOM_v2_ID;

drop table Z_PPS_MAGA_LS_V2;
drop table T_LASTSYNCTIME_ZCOM_V2;
drop table T_SYNCTIME_TMP_ZCOM_V2;
drop table v_cm_content_zcom_v2 ;

alter table Z_PPS_MAGA drop column KEYLETTER;
alter table T_R_GCONTENT drop column match_deviceid;



delete DBVERSION where PATCHVERSION = 'MM1.0.2.090_SSMS' and LASTDBVERSION = 'MM1.0.2.085_SSMS';
commit;