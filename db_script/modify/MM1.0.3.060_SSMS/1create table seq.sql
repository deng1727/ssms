drop table SUBS_LOG;

create table SUBS_LOG
(
  LOGID         NUMBER,          -- 唯一ID
  RECORDNUM     NUMBER default 0,-- 程序运行产生的记录数
  TASKNAME      VARCHAR2(100), --  程序名称（过程或函数名）
  DESCRIPTION   VARCHAR2(100), -- 程序功能描述
  TASKBEGINTIME DATE,          --运行开始时间
  TASKENDTIME   DATE,          --运行结束时间
  STATUS        VARCHAR2(10),  --程序运行状态（RUNNING(正在运行),FINISHED（运行结束）,EXCEPTION（发生意外））
  ERRMSG        VARCHAR2(300), --程序发生意外的原因
   REMARK        VARCHAR2(500)--描述信息
); 
-- Add comments to the columns 
comment on column SUBS_LOG.LOGID
  is '唯一ID';
comment on column SUBS_LOG.RECORDNUM
  is '程序运行产生的记录数';
comment on column SUBS_LOG.TASKNAME
  is '程序名称（过程或函数名）';
comment on column SUBS_LOG.DESCRIPTION
  is '程序功能描述';
comment on column SUBS_LOG.TASKBEGINTIME
  is '运行开始时间';
comment on column SUBS_LOG.TASKENDTIME
  is '运行结束时间';
comment on column SUBS_LOG.STATUS
  is '程序运行状态（RUNNING(正在运行),FINISHED（运行结束）,EXCEPTION（发生意外））';
comment on column SUBS_LOG.ERRMSG
  is '程序发生意外的原因';
comment on column SUBS_LOG.REMARK
  is '描述信息';

create table t_config_parentcategoryid(categoryid varchar2(20),
remark varchar2(50));
 comment on table t_config_parentcategoryid.categoryid
  is '根货架ID';
   comment on table t_config_parentcategoryid.remark
  is '根货架描述信息';

drop sequence LOG_SEQ;
create sequence LOG_SEQ
minvalue 1
maxvalue 99999999999
start with 41
increment by 1
cache 20;