create or replace package pg_log_manage is
 v_vglogid number:=0;

  function f_startlog(
                      --vc_programid varchar2,
                      vc_programname varchar2,--过程名
                      vc_progradesc  varchar2,--过程描述与执行程序块
                      vc_remark      varchar2 default null --备注信息，小于等于500个字符
                      ) return number;

  function f_successlog(vn_logid number default v_vglogid,--日志唯一ID，缺省值是序列
                        -- vc_programid varchar2,
                        --vc_programname varchar2,
                        --vd_enddate date :=null,
                        vn_RECORDNUM number default SQL%ROWCOUNT,--执行程序块执行的记录数
                        vc_remark    varchar2 default null --备注信息，小于等于500个字符
                        ) return number;

  function f_errorlog(

                      vn_logid number default v_vglogid,
                      -- vc_progname    varchar2,
                      vc_errmsg varchar2 default SUBSTR(SQLERRM, 1, 200), --异常信息
                      vc_remark varchar2 default null --备注信息，小于等于500个字符

                      ) return number;
end;
