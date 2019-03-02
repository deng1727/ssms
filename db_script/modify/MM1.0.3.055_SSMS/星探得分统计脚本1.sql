--存放最终统计结果表
create table t_total_score(
msisdn varchar2(11), --用户ID(手机号)
PKDATE varchar2(6),--入围作品月份，格式YYYYMM
score  number    --红人榜分数
)nologging;
-- Add comments to the table 
comment on table T_TOTAL_SCORE
  is '红人榜分数统计表';
-- Add comments to the columns 
comment on column T_TOTAL_SCORE.MSISDN
  is '用户ID(手机号)';
comment on column T_TOTAL_SCORE.PKDATE
  is '入围作品月份，格式YYYYMM';
comment on column T_TOTAL_SCORE.SCORE
  is '红人榜分数';
  
  
create or replace view v_total_comment_pkapps as
select a.msisdn, count(9) total
from T_CARVE_COMMENT@dl_portalwww a, CM_CONTENT_PKAPPS@dl_ppms_device b
where a.contentid = b.contentid
and a.createdate < trunc(add_months(sysdate, 1), 'mm')
and a.createdate >= trunc(add_months(sysdate, -1), 'mm')
group by msisdn;


create or replace view v_total_comment as
select a.msisdn,count(8) total from T_CARVE_COMMENT@dl_portalwww a group by a.msisdn;


------------------------------------------------------------------------------------------
--存放监控程序日志信息
create table SUBS_LOG
(
  LOGID         NUMBER,          -- 唯一ID
  RECORDNUM     NUMBER default 0,-- 程序运行产生的记录数
  TASKNAME      VARCHAR2(100), --  程序名称（过程或函数名）
  DESCRIPTION   VARCHAR2(100), -- 程序功能描述
  TASKBEGINTIME DATE,          --运行开始时间
  TASKENDTIME   DATE,          --运行结束时间
  STATUS        VARCHAR2(10),  --程序运行状态（RUNNING(正在运行),FINISHED（运行结束）,EXCEPTION（发生意外））
  ERRMSG        VARCHAR2(300) --程序发生意外的原因
);  

-- Create sequence 
create sequence LOG_SEQ
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 20;




-----------------------------------------------------------------------------------------------
--监控程序运行包
create or replace package pg_log_manage is

function f_startlog(
        --vc_programid varchar2,
        vc_programname varchar2,
        vc_progradesc varchar2
)return number;


function f_successlog(
         vn_logid       number,
       -- vc_programid varchar2,
        --vc_programname varchar2,
     --vd_enddate date :=null,
     vn_RECORDNUM number:=null
           )return number;


 function f_errorlog(

     vn_logid       number,
       -- vc_progname    varchar2,
        vc_errmsg      varchar2

 )return number;
 end;




