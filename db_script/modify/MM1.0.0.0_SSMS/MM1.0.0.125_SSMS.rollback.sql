-- 删除新增字段
alter table T_CATERULE_COND drop column id;

--黑名单表字段
alter table T_CONTENT_BACKLIST drop column STARTDATE;
alter table T_CONTENT_BACKLIST drop column TYPE;
alter table T_CONTENT_BACKLIST rename column ENDDATE to indate;

drop table T_RB_AUTHOR cascade constraints;

drop table T_RB_BOOK cascade constraints;

drop table T_RB_CATEGORY cascade constraints;

drop table T_RB_MONTHLY cascade constraints;

drop table T_RB_RECOMMEND cascade constraints;

drop table T_RB_REFERENCE cascade constraints;

drop table T_RB_TYPE cascade constraints;

drop sequence SEQ_RB_CATEGORY_ID;

drop table T_VB_VIDEO;

drop table t_new_old_cate_mapping;


delete DBVERSION where PATCHVERSION = 'MM1.0.0.125_SSMS' and LASTDBVERSION = 'MM1.0.0.123_SSMS';
commit;